package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.UserDAO;
import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the userDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
	private UserDAO userDao;

	@Autowired
	public UserServiceImpl(UserDAO userDao) {
		this.userDao = userDao;
	}

	/*
	 * This method should be used to save a new user.
	 */

	public boolean registerUser(User user) throws UserAlreadyExistException {
		User oldUser = userDao.getUserById(user.getUserId());
		if (oldUser == null) {
			return userDao.registerUser(user);
		} else {
			throw new UserAlreadyExistException(
					"User already availble in the database, Please check the details" + user.getUserName());
		}

	}

	/*
	 * This method should be used to update a existing user.
	 */

	public User updateUser(User user, String userId) throws Exception {
		userDao.updateUser(user);
		User updatedUser = userDao.getUserById(userId);
		if (updatedUser == null) {
			throw new Exception();
		}
		return updatedUser;

	}

	/*
	 * This method should be used to get a user by userId.
	 */

	public User getUserById(String UserId) throws UserNotFoundException {
		User user = null;
		user = userDao.getUserById(UserId);
		if (user == null)
			throw new UserNotFoundException("Unable to find the user with user id : " + UserId);
		else
			return user;

	}

	/*
	 * This method should be used to validate a user using userId and password.
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		
		boolean isValid = userDao.validateUser(userId, password);
		if(isValid) 
			return true;
		else 
			throw new UserNotFoundException("unable to find the user ");

	}

	/* This method should be used to delete an existing user. */
	public boolean deleteUser(String UserId) {
		return userDao.deleteUser(UserId);

	}

}
