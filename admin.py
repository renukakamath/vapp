from flask import * 
from database import*



admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():

	return render_template('admin_home.html')

@admin.route('/admin_manageteacher',methods=['post','get'])
def admin_manageteacher():
	data={}
	
	q="select * from teacher "
	res=select(q)
	data['teacherfview']=res



	if "action" in  request.args:
		action=request.args['action']
		tid=request.args['tid']
	else:
		action=None

	if action=='delete':
		q="delete from teacher where teacher_id='%s'"%(tid)
		delete(q)
		return redirect(url_for('admin.admin_manageteacher'))


	if action=='update':
		q="select * from teacher where teacher_id='%s'"%(tid)
		res=select(q)
		data['teacher']=res

	if "update" in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		email=request.form['email']
		
		num=request.form['num']
		
		q="update teacher set first_name='%s',last_name='%s',phone='%s',email='%s' where teacher_id='%s'"%(fname,lname,num,email,tid)
		update(q)
		return redirect(url_for('admin.admin_manageteacher'))
		

	if "teacher" in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		email=request.form['email']
	
		num=request.form['num']
		uname=request.form['uname']
		pwd=request.form['pwd']
		q="insert into login values (null,'%s','%s','teacher')"%(uname,pwd)
		id=insert(q)
		
		q="insert into teacher values(null,'%s','%s','%s','%s','%s')"%(id,fname,lname,num,email)

		insert(q)
		return redirect(url_for('admin.admin_manageteacher'))

	return render_template('admin_manageteacher.html',data=data)


@admin.route('/admin_viewstudents',methods=['post','get'])
def admin_viewstudents():
	data={}
	
	q="select * from blind "
	res=select(q)
	data['stuuu']=res
	return render_template('admin_viewstudents.html',data=data)
