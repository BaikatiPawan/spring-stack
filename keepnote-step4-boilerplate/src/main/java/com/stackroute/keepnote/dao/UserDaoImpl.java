package com.stackroute.keepnote.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private SessionFactory sessionFactory;

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		Object obj = sessionFactory.getCurrentSession().save(user);
		if (obj == null)
			return true;
		else
			return false;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		User oldUser = sessionFactory.getCurrentSession().get(User.class, user.getUserId());
		if (oldUser != null) {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} else {
			return false;
		}

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String UserId) {
		return sessionFactory.getCurrentSession().get(User.class, UserId);
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		boolean isValid = true;
		User user = sessionFactory.getCurrentSession().get(User.class, userId);
		if (user == null || !(user.getUserPassword().equals(password))) {
			isValid = false;
			throw new UserNotFoundException("user is note available : " + userId);
		}
		return isValid;

	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		User user = sessionFactory.getCurrentSession().get(User.class, userId);
		if (user == null) {
			return false;
		} else {
			sessionFactory.getCurrentSession().delete(user);
			return true;
		}

	}

}
