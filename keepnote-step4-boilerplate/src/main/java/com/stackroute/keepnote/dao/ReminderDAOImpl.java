package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

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
public class ReminderDAOImpl implements ReminderDAO {
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private SessionFactory sessionFactory;

	@Autowired
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}


	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) {
		Object obj = getSession().save(reminder);
		if (obj == null)
			return false;
		else
			return true;

	}
	
	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		Reminder oldReminder = getSession().get(Reminder.class, reminder.getReminderId());
		if (oldReminder == null) {
			return false;
		} else {
			getSession().update(reminder);
			return true;
		}

	}

	/*
	 * Remove an existing reminder
	 */
	
	public boolean deleteReminder(int reminderId) {
		Reminder reminder = getSession().get(Reminder.class, reminderId);
		if (reminder != null) {
			getSession().delete(reminder);
			return true;
		} else {
			return false;
		}

	}

	/*
	 * Retrieve details of a specific reminder
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Reminder reminder = getSession().get(Reminder.class, reminderId);
		if (reminder == null) {
			throw new ReminderNotFoundException("Unable to find Reminder id in the database : " + reminderId);
		}
		return reminder;

	}

	/*
	 * Retrieve details of all reminders by userId
	 */
	
	@SuppressWarnings("unchecked")
	public List<Reminder> getAllReminderByUserId(String userId) {
		Query query = getSession().createQuery("from Reminder where reminderCreatedBy= :reminderCreatedBy");
		query.setParameter("reminderCreatedBy", userId);
		List<Reminder> reminders =query.getResultList();
		return reminders;

	}

}
