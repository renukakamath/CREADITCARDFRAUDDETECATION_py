from flask import Flask,Blueprint,render_template,request,url_for,redirect,session,flash
from database import*
import smtplib
from email.mime.text import MIMEText
from flask_mail import Mail
from core import *

import random 

api=Blueprint('api',__name__)



@api.route('/logins')
def logins():
	data={}
	u=request.args['username']
	pw=request.args['password']

	q="select * from login where username='%s' and password='%s'"%(u,pw)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/Registration')	
def Registration():
	data={}
	f=request.args['fname']
	l=request.args['lname']
	
	pl=request.args['place']
	

	ph=request.args['phone']
	e=request.args['email']
	u=request.args['username']
	p=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(NULL,'%s','%s','user')"%(u,p)
		lid=insert(q)
		r="insert into user values(NULL,'%s','%s','%s','%s','%s','%s')"%(lid,f,l,pl,ph,e)
		insert(r)
		data['status']="success"
	return str(data)

@api.route('/user_managecreditcard')
def user_managecreditcard():
	data={}
	uid=request.args['login_id']
	c=request.args['card']

	m=request.args['month']
	p=request.args['pin_no']
	i=request.args['ifsc']
	b=request.args['bank']
	

	q="insert into creditcard values(null,(select user_id from user where login_id='%s'),'%s','%s','%s',curdate(),'0','%s','%s')"%(uid,c,m,p,i,b)
	insert(q)
	data['status']="success"
	data['method']="user_managecreditcard"
	return str(data)


@api.route('/Viewuser_managecreditcard')
def Viewuser_managecreditcard():
	data={}
	uid=request.args['login_id']
	q="select * from creditcard inner join user using (user_id) where user_id=(select user_id from user where login_id='%s')"%(uid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewuser_managecreditcard"
	return str(data)


@api.route('/user_requestacc')
def user_requestacc():
	data={}
	uid=request.args['login_id']
	d=request.args['det']
	da=request.args['date']
	q="insert into request values(null,(select user_id from user where login_id='%s'),'%s','%s','pending')"%(uid,d,da)
	insert(q)
	data['status']="success"
	data['method']="user_requestacc"
	return str(data)


@api.route('/viewuser_requestacc')
def viewuser_requestacc():
	data={}
	uid=request.args['login_id']
	

	q="select * from request inner join user using (user_id) where user_id=(select user_id from user where login_id='%s')"%(uid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="viewuser_requestacc"
	return str(data)



@api.route('/user_viewtransaction')	
def user_viewtransaction():
	data={}
	q="select * from transaction"
	res=select(q)
	data['data']=res
	data['status']="success"
	return str(data)


@api.route('/user_managedeposit')
def user_managedeposit():
	data={}
	uid=request.args['login_id']
	cid=request.args['cid']
	
	b=request.args['balance']
	q="update account set balance='%s' where user_id=(select user_id from user where login_id='%s')"%(b,uid)
	update(q)
	q="update creditcard set balance='%s' where user_id=(select user_id from user where login_id='%s')"%(b,uid)
	update(q)
	q="update account set status='amount added' where user_id=(select user_id from user where login_id='%s')"%(uid)
	update(q)
	data['status']="success"
	data['method']="user_managedeposit"
	return str(data)

@api.route('/viewuser_managedeposit')
def viewuser_managedeposit():
	data={}
	uid=request.args['login_id']
	

	q="select * from request inner join user using (user_id) where user_id=(select user_id from user where login_id='%s')"%(uid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="viewuser_managedeposit"
	return str(data)


@api.route('/user_transfer')
def user_transfer():
	data={}
	uid=request.args['login_id']
	f=request.args['facc']
	t=request.args['toacc']
	a=request.args['amo']
	lat=request.args['latitude']
	lon=request.args['longitude']


	q="select * from transaction where  faccount='%s' "%(f)


	res=(select(q))
	print("totalllllllllllllllkkkkkkkkkkkkkkkkkkkkkkkkkkkkk",len(res))
	reslat = []
	reslong = []
	resamount = []
	if len(res)!=0:
		for i in res:
			print("i",i['latitude'])
			print("f")
			if i['status'] =="success":
				reslat.append(["0", i['latitude'], i['longitude']])
				reslong.append(["0", i['longitude']])
				resamount.append(["0", i['amount']])
				print("hello")
				reslat.append(["0", lat, lon])
				reslong.append(["0", lon])
				print("reslat",reslat)
				resamount.append(["0", bal])
				print("location")
				resloc = outlier(reslat)
				print("Amount",resloc)
				# resmt = outlier(resamount)
				resll = resloc[len(resloc) - 1]
				print("resl1",resll)


	q="select * from account where user_id=(select user_id from user where login_id='%s')  and accountnumber='%s' and balance>='%s'"%(uid,f,a)

	res=select(q)
	print(q)	

	if res:







		# q="insert into transaction values(null,'%s','%s','%s',curdate(),'pending','%s','%s')"%(f,t,a,la,lo)
		# insert(q)


		data['method']="user_transfer"



		data['status']="success"


	else:
		data['status']="failed"
		data['method']="user_transfer"
	
	return str(data)
@api.route('/Makepayment')
def Makepayment():
	data={}
	data={}
	uid=request.args['log_id']
	account=request.args['account']


	n=random.randint(1000,9999)
	n1=str(n)
	print(n)
	
	q="select * from account where user_id=(select user_id from user where login_id='%s') and accountnumber='%s'"%(uid,account)
	res=select(q)
	print(q)
	if res:
		
		q="select * from user where user_id=(select user_id from user where login_id='%s')"%(uid)
		res=select(q)
		print(q)
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
			data['status']="success"
			data['method']="user_payment"



	else:
		data['status']="failed"
		data['method']="user_payment"
	return str(data)

@api.route('/user_otp')
def user_otp():
	data={}
	otp=request.args['otp']

		
		
	uid=request.args['login_id']
	f=request.args['facc']
	t=request.args['toacc']
	a=request.args['amo']
	la=request.args['Latitude']
	lo=request.args['longitude']


	
	q="insert into transaction values(null,'%s','%s','%s',curdate(),'pending','%s','%s')"%(f,t,a,la,lo)
	insert(q)
	print(q)
	
	q="update account set balance=balance-'%s' where user_id=(select user_id from user where login_id='%s')"%(a,uid)
	update(q)
	print(q)


	q="update account set balance=balance+'%s' where accountnumber='%s'"%(a,t)
	update(q)


	data['status']="success"
	data['method']="user_otp"


	return str(data)


@api.route('/addcard')
def addcard():
	data={}
	ss=[]
	sss=[]
	login_id=request.args['login_id']
	accountno=request.args['accountno']
	cvv=request.args['cvv']
	balance=request.args['balance']
	banknumber=request.args['banknumber']

	ss.append(accountno)
	ss.append(cvv)
	ss.append(balance)
	ss.append(banknumber)
	sss.append(ss)

	

	
	out=vals(sss)
	print (out)
	if sss[0]==0:
		outs="No Need For New creditcard "
	else:
		outs="Need For New creditcard "



	data['status']='success'
	data['method']="addcard"
	data['data']=outs
	return str(data)



		
