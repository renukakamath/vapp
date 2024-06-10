from flask import *
from database import*


public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template('home.html')


@public.route('/login',methods=['post','get'])	
def login():
	if "login" in request.form:
		u=request.form['uname']
		pa=request.form['pwd']
		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			lid=session['login_id']
			if res[0]['user_type']=="admin":
				return redirect(url_for('admin.admin_home'))

			elif res[0]['user_type']=="teacher":
				q="select * from teacher where login_id='%s'"%(lid)
				res=select(q)
				if res:
					session['teacher_id']=res[0]['teacher_id']
					cid=session['teacher_id']
				return redirect(url_for('teacher.teacher_home'))


		else:
			flash('invalid username and password')

	return render_template('login.html')