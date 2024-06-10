from flask import * 
from database import* 
import uuid

api=Blueprint('api',__name__)

@api.route('/logins')
def logins():
	data={}
	u=request.args['username']
	p=request.args['password']
	q1="select * from login where username='%s' and `password`='%s'"%(u,p)
	print(q1)
	res=select(q1)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	return str(data)

@api.route('/Viewclass')
def Viewclass():
	data={}
	q="select * from class"
	res=select(q)
	if res:
		data['data']=res
		data['status']="success"
		data['method']="Viewclass"
	return str(data)


@api.route('/Viewsubject')
def Viewsubject():
	data={}
	cid=request.args['cid']
	q="select * from subject where class_id='%s'"%(cid)
	res=select(q)
	if res:
		data['data']=res
		data['status']="success"
		
	return str(data)

@api.route('/Viewnotes')
def Viewnotes():
	data={}
	sid=request.args['sid']
	
	q="select * from note where subject_id='%s'"%(sid)
	res=select(q)
	if res:
		data['data']=res
		data['status']="success"
		
	return str(data)

@api.route('/Viewarticle')
def Viewarticle():
	data={}

	sid=request.args['sid']
	
	q="select * from article where subject_id='%s'"%(sid)
	res=select(q)
	if res:
		data['data']=res
		data['status']="success"
		
	return str(data)
