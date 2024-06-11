from flask import Flask,Blueprint,render_template,request,url_for,redirect,session,flash
from database import*
import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail

import random 


user=Blueprint('user',__name__)



@user.route('/user_home')
def user_home():

	return render_template('user_home.html')

@user.route('/user_managecreditcard',methods=['post','get'])
def user_managecreditcard():

	data={}
	uid=session['user_id']
	q="select * from creditcard inner join user using (user_id) where user_id='%s'"%(uid)
	res=select(q)
	data['cre']=res

	if "action" in request.args:
		action=request.args['action']
		cid=request.args['cid']


	else:
		action=None


	if action=='delete':
		q="delete from creditcard where creditcard_id='%s'"%(cid)
		delete(q)
		flash(' successfully')
		return redirect(url_for('user.user_managecreditcard'))

	if action=='update':
		q="select * from creditcard where creditcard_id='%s'"%(cid)
		res=select(q)
		data['cred']=res


	if "update" in request.form:
		c=request.form['card']
		m=request.form['month']
		p=request.form['pin_no']
		i=request.form['ifsc']
		b=request.form['bank']
		q="update creditcard set cardnum='%s',month='%s',pin_no='%s',ifsc_code='%s',bank_num='%s' where creditcard_id='%s'"%(c,m,p,i,b,cid)
		update(q)
		flash(' successfully')
		return redirect(url_for('user.user_managecreditcard'))



	if "credit" in request.form:
		uid=session['user_id']
		c=request.form['card']
		m=request.form['month']
		p=request.form['pin_no']
		i=request.form['ifsc']
		b=request.form['bank']

		q="insert into creditcard values(null,'%s','%s','%s','%s',curdate(),'0','%s','%s')"%(uid,c,m,p,i,b)
		insert(q)
		flash(' successfully')
		return redirect(url_for('user.user_managecreditcard'))
	return render_template('user_managecreditcard.html',data=data)


@user.route('/user_requestacc',methods=['post','get'])
def user_requestacc():
	data={}
	uid=session['user_id']

	q="select * from request inner join user using (user_id) where user_id='%s'"%(uid)
	res=select(q)
	data['req']=res

	if "request" in request.form:
			uid=session['user_id']
			d=request.form['det']
			da=request.form['date']
			q="insert into request values(null,'%s','%s','%s','pending')"%(uid,d,da)
			insert(q)
			flash(' successfully')
			return redirect(url_for('user.user_requestacc'))				
	
	return render_template('user_requestacc.html',data=data)

@user.route('/user_viewtransaction')	
def user_viewtransaction():
	data={}
	q="select * from transaction"
	res=select(q)
	data['tra']=res
	return render_template('user_viewtransaction.html',data=data)

@user.route('/user_managedeposit',methods=['post','get'])
def user_managedeposit():
	data={}
	uid=session['user_id']
	q="select * from account inner join user using (user_id) where user_id='%s'"%(uid)
	res=select(q)
	data['acc']=res

	if "acc" in request.form:
		uid=request.args['uid']
		b=request.form['bal']
		q="update account set balance='%s' where user_id='%s'"%(b,uid)
		update(q)
		q="update account set status='amount added' where user_id='%s'"%(uid)
		update(q)
		flash(' successfully')
		return redirect(url_for('user.user_managedeposit'))
		

	return render_template('user_managedeposit.html',data=data)

@user.route('/user_transfer',methods=['post','get'])
def user_transfer():
	data={}
	uid=session['user_id']

	q="select * from account where user_id='%s'"%(uid)
	res=select(q)
	acc=res[0]['accountnumber']
	data['acco']=acc

	amt=res[0]['balance']
	data['amo']=amt
	


	if "transfer" in request.form:
		f=request.form['facc']
		t=request.form['toacc']
		a=request.form['amo']


		if int(amt)>=int(a):
			
			q="select * from account where accountnumber='%s'"%(t)
			res=select(q)
			if res:
				return redirect(url_for('user.user_makepayment',f=f,t=t,a=a))
			else:
				flash("invalid")

		else:
			flash('enter less amount')		

		
	return render_template('user_transfer.html',data=data)	
@user.route('/user_makepayment',methods=['post','get'])
def user_makepayment():
	data={}
	uid=session['user_id']

	n=random.randint(1000,9999)
	n1=str(n)
	print(n)
	
	q="select * from creditcard where user_id='%s'"%(uid)
	res=select(q)
	if res:
		ca=res[0]['cardnum']
		

	if "payment" in request.form:
		f=request.args['f']
		t=request.args['t']
		a=request.args['a']

		c=request.form['cnum']
		if ca==c:
			q="select * from user where user_id='%s'"%(uid)
			res=select(q)
			if res:
				email=res[0]['email']

				
				email=email
				print(email)
				pwd="YOUR OTP :"+n1
				print(pwd)
				try:
					gmail = smtplib.SMTP('smtp.gmail.com', 587)
					gmail.ehlo()
					gmail.starttls()
					gmail.login('hariharan0987pp@gmail.com','rjcbcumvkpqynpep')
				except Exception as e:
					print("Couldn't setup email!!"+str(e))

				pwd = MIMEText(pwd)

				pwd['Subject'] = 'OTP'

				pwd['To'] = email

				pwd['From'] = 'hariharan0987pp@gmail.com'

				try:
					gmail.send_message(pwd)
					print(pwd)
					flash("EMAIL SENED SUCCESFULLY")
						


				except Exception as e:
					print("COULDN'T SEND EMAIL", str(e))
				else:
					flash("Added successfully")


		

			return redirect(url_for('user.user_otp',n=n,f=f,t=t,a=a))

		else:
			flash('invalid creditcard')

	return render_template('user_makepayment.html',data=data)

@user.route('user_otp',methods=['post','get'])	
def user_otp():
	otp=request.args['n']

	if "otp" in request.form:
		o=request.form['number']
		uid=session['user_id']
		f=request.args['f']
		t=request.args['t']
		a=request.args['a']


		if o==otp:
			q="insert into transaction values(null,'%s','%s','%s',curdate(),'pending')"%(f,t,a)
			insert(q)
			
			q="update account set balance=balance-'%s' where user_id='%s'"%(a,uid)
			update(q)
			flash(' successfully')
			return redirect(url_for('user.user_transfer'))
	return render_template('user_otp.html')



			

	
			