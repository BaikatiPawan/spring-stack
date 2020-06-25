package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesnot currently 
* provide any additional behavior over the @Component annotation, but it is a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private NoteDAO noteDao;
	private CategoryDAO categoryDao;
	private ReminderDAO reminderDao;

	@Autowired
	public NoteServiceImpl(NoteDAO noteDao, CategoryDAO categoryDao, ReminderDAO reminderDao) {
		this.noteDao = noteDao;
		this.categoryDao = categoryDao;
		this.reminderDao = reminderDao;
	}

	/*
	 * This method should be used to save a new note.
	 */

	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException {
		Reminder reminder = note.getReminder();
		Category category = note.getCategory();
		note.setNoteCreatedAt(new Date());

		try {
			if (reminder != null) {
				reminderDao.getReminderById(reminder.getReminderId());
			}
			if (category != null) {
				categoryDao.getCategoryById(category.getCategoryId());
			}
		} catch (ReminderNotFoundException rnfe) {
			throw new ReminderNotFoundException("Reminder not found");
		} catch (CategoryNotFoundException cnfe) {
			throw new CategoryNotFoundException("Category not found");
		}

		return noteDao.createNote(note);

	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(int noteId) throws NoteNotFoundException {
		return noteDao.deleteNote(noteId);

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		return noteDao.getAllNotesByUserId(userId);

	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note = noteDao.getNoteById(noteId);
		if (note == null) {
			throw new NoteNotFoundException("Note not found");
		}
		return note;

	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException {
		if (note.getReminder() != null) {
			Reminder reminder = reminderDao.getReminderById(note.getReminder().getReminderId());
			if (reminder == null) {
				throw new ReminderNotFoundException(note.getReminder().getReminderName() + " not found");
			}
		}
		if (note.getCategory() != null) {
			Category category = categoryDao.getCategoryById(note.getCategory().getCategoryId());
			if (category == null) {
				throw new CategoryNotFoundException(note.getCategory().getCategoryName() + " not found");
			}
		}
		noteDao.UpdateNote(note);
		return noteDao.getNoteById(id);

	}

}
