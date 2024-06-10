from flask import * 
from database import*



teacher=Blueprint('teacher',__name__)

@teacher.route('/teacher_home')
def teacher_home():

	return render_template('teacher_home.html')



@teacher.route('/teacher_manageclass',methods=['post','get'])
def teacher_manageclass():
	data={}
	cid=session['teacher_id']
	q="select * from class "
	res=select(q)
	data['annnoo']=res



	if "action" in  request.args:
		action=request.args['action']
		cid=request.args['cid']
	else:
		action=None

	if action=='delete':
		q="delete from class where class_id='%s'"%(cid)
		delete(q)
		return redirect(url_for('teacher.teacher_manageclass'))


	if action=='update':
		q="select * from class where class_id='%s'"%(cid)
		res=select(q)
		data['classup']=res

	if "update" in request.form:
		classs=request.form['classs']
		
		
		q="update class set class='%s' where class_id='%s'"%(classs,cid)
		update(q)
		return redirect(url_for('teacher.teacher_manageclass'))
		

	if "addgallery" in request.form:
		classs=request.form['classs']
	
	
		q="insert into class values(null,'%s')"%(classs)

		insert(q)
		return redirect(url_for('teacher.teacher_manageclass'))

	return render_template('teacher_manageclass.html',data=data)



@teacher.route('/teacher_managesubject',methods=['post','get'])
def teacher_managesubject():
	data={}
	cid=request.args['cid']
	q="select * from subject  where class_id='%s'"%(cid)
	res=select(q)
	data['annnoo']=res



	if "action" in  request.args:
		action=request.args['action']
		sid=request.args['sid']
		cid=request.args['cid']
	else:
		action=None

	if action=='delete':
		q="delete from subject where subject_id='%s'"%(sid)
		delete(q)
		return redirect(url_for('teacher.teacher_managesubject',cid=cid))


	if action=='update':
		q="select * from subject where subject_id='%s'"%(sid)
		res=select(q)
		data['subjectup']=res

	if "update" in request.form:
		subject=request.form['subject']
		
		
		q="update subject set subject='%s' where subject_id='%s'"%(subject,sid)
		update(q)
		return redirect(url_for('teacher.teacher_managesubject',cid=cid))
		

	if "addgallery" in request.form:
		subject=request.form['subject']
		cid=request.args['cid']
	
	
		q="insert into subject values(null,'%s','%s')"%(cid,subject)

		insert(q)
		return redirect(url_for('teacher.teacher_managesubject',cid=cid))

	return render_template('teacher_managesubject.html',data=data,cid=cid)




@teacher.route('/teacher_managenotes',methods=['post','get'])
def teacher_managenotes():
	data={}
	sid=request.args['sid']
	q="select * from note  where subject_id='%s'"%(sid)
	res=select(q)
	data['annnoo']=res



	if "action" in  request.args:
		action=request.args['action']
		nid=request.args['nid']
		sid=request.args['sid']
	else:
		action=None

	if action=='delete':
		q="delete from note where note_id='%s'"%(nid)
		delete(q)
		return redirect(url_for('teacher.teacher_managenotes',sid=sid))


	if action=='update':
		q="select * from note where note_id='%s'"%(nid)
		res=select(q)
		data['noteup']=res

	if "update" in request.form:
		note=request.form['note']
		details=request.form['details']
		
		
		q="update note set note='%s',details='%s' where note_id='%s'"%(note,details,nid)
		update(q)
		return redirect(url_for('teacher.teacher_managenotes',sid=sid))
		

	if "addgallery" in request.form:
		note=request.form['note']
		details=request.form['details']
		sid=request.args['sid']
	
	
		q="insert into note values(null,'%s','%s','%s')"%(sid,note,details)

		insert(q)
		return redirect(url_for('teacher.teacher_managenotes',sid=sid))

	return render_template('teacher_managenotes.html',data=data,sid=sid)




@teacher.route('/teacher_managearticle',methods=['post','get'])
def teacher_managearticle():
	data={}
	sid=request.args['sid']
	q="select * from article where subject_id='%s'"%(sid)
	res=select(q)
	data['annnoo']=res



	if "action" in  request.args:
		action=request.args['action']
		aid=request.args['aid']
		sid=request.args['sid']
	else:
		action=None

	if action=='delete':
		q="delete from article where article_id='%s'"%(aid)
		delete(q)
		return redirect(url_for('teacher.teacher_managearticle',sid=sid))


	if action=='update':
		q="select * from article where article_id='%s'"%(aid)
		res=select(q)
		data['articleup']=res

	if "update" in request.form:
		article=request.form['article']

		details=request.form['details']

		
		
		q="update article set article='%s',details='%s' where article_id='%s'"%(article,details,aid)
		update(q)
		return redirect(url_for('teacher.teacher_managearticle',sid=sid))
		

	if "addgallery" in request.form:
		article=request.form['article']
		details=request.form['details']
		sid=request.args['sid']
	
	
		q="insert into article values(null,'%s','%s','%s')"%(sid,article,details)

		insert(q)
		return redirect(url_for('teacher.teacher_managearticle',sid=sid))

	return render_template('teacher_managearticle.html',data=data)



@teacher.route('/teacher_managestudents',methods=['post','get'])
def teacher_managestudents():
	data={}
	
	q="select * from blind "
	res=select(q)
	data['teacherfview']=res



	if "action" in  request.args:
		action=request.args['action']
		tid=request.args['tid']
	else:
		action=None

	if action=='delete':
		q="delete from blind where blind_id='%s'"%(tid)
		delete(q)
		return redirect(url_for('teacher.teacher_managestudents'))


	if action=='update':
		q="select * from blind where blind_id='%s'"%(tid)
		res=select(q)
		data['teacher']=res

	if "update" in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		IMEI=request.form['IMEI']
		
		num=request.form['num']
		
		q="update blind set first_name='%s',last_name='%s',phone='%s',IMEI='%s' where blind_id='%s'"%(fname,lname,num,IMEI,tid)
		update(q)
		return redirect(url_for('teacher.teacher_managestudents'))
		

	if "teacher" in request.form:
		fname=request.form['fname']
		lname=request.form['lname']
		IMEI=request.form['IMEI']
	
		num=request.form['num']
		uname=request.form['uname']
		pwd=request.form['pwd']
		q="insert into login values (null,'%s','%s','blind')"%(uname,pwd)
		id=insert(q)
		
		q="insert into blind values(null,'%s','%s','%s','%s','%s')"%(id,fname,lname,num,IMEI)

		insert(q)
		return redirect(url_for('teacher.teacher_managestudents'))

	return render_template('teacher_managestudents.html',data=data)