package com.smart.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;



import com.smart.Entity.User;
import com.smart.utils.MD5;

@Repository
@Transactional
public class UserDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	public void SaveUser(User user){
		getSession().save(user);
	}
	

	//student_id
	public boolean findByStudentIdExist(String student_id) {
		boolean exist = true;
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("student_id").eq(student_id));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		if (criteria.list().size() == 0) exist = false;
		return exist;
	}
	public boolean findByUserNameExist(String student_id) {
		boolean exist = true;
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("student_id").eq(student_id));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		if (criteria.list().size() == 0) exist = false;
		return exist;
	}
	
	public boolean findByEmailExist(String email) {
		boolean exist = true;
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("mail").eq(email));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		if (criteria.list().size() == 0) exist = false;
		return exist;
	}
	public User checkPasswd(String student_id,String passwd) {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("student_id").eq(student_id));
		dc.add(Property.forName("passwd").eq(MD5.stringMD5(passwd)));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		List<User> list = criteria.list();
		User user = new User();
		if (list.size() > 0) user = list.get(0);
		return user;	
	}
	//
	public User checkSafePassword(String student_id,String safe_password) {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Property.forName("student_id").eq(student_id));
		dc.add(Property.forName("safe_password").eq(MD5.stringMD5(safe_password)));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		List<User> list = criteria.list();
		User user = new User();
		if (list.size() > 0) user = list.get(0);
		return user;	
	}
	


	


		
}
