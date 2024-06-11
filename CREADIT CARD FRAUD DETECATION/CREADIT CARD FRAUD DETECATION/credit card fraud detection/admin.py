from flask import Flask,Blueprint,render_template,request,redirect,url_for,flash 
from database import*

import random 


admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():
	return render_template('admin_home.html')

@admin.route('/admin_viewrequest')
def admin_viewrequest():
	data={}
	q="select * from request inner join user using (user_id)"
	res=select(q)
	data['req']=res

	if "action" in request.args:
		action=request.args['action']
		rid=request.args['rid']
		uid=request.args['uid']

	else:action=None


	if action=='accept':
		q="update request set status='accept' where request_id='%s'"%(rid)
		update(q)
		flash(' successfully')
		n=random.randint(10000000000,999900000000)
		print(n)
		q="insert into account values(null,'%s','%s',0,'pending')"%(uid,n)
		insert(q)
		flash(' successfully')
		
		return redirect(url_for('admin.admin_viewrequest'))

	if action=='reject':
		q="update request set status='reject' where request_id='%s'"%(rid)
		update(q)
		flash(' successfully')
		
		
	return render_template('admin_viewrequest.html',data=data)

@admin.route('/admin_viewtransaction')	
def admin_viewtransaction():
	data={}
	q="select * from transaction"
	res=select(q)
	data['tra']=res
	return render_template('admin_viewtransaction.html',data=data)
	
			