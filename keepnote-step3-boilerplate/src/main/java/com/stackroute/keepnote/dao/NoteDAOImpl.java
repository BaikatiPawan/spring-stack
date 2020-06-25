package com.stackroute.keepnote.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

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
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private SessionFactory sessionFactory;

	@Autowired
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/*
	 * Create a new note
	 */

	public boolean createNote(Note note) {
		note.setCreatedAt(new Date());
		Object obj = getSession().save(note);
		if (obj != null) {
			return true;
		}
		return false;
	}

	/*
	 * Remove an existing note
	 */

	public boolean deleteNote(int noteId) {
		Note note = getSession().get(Note.class, noteId);
		if (note != null) {
			getSession().delete(note);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	@SuppressWarnings("unchecked")
	public List<Note> getAllNotesByUserId(String userId) {
		Query query = getSession().createQuery("from Note where createdBy= :createdBy");
		query.setParameter("createdBy", userId);
		
		List<Note>  notes = query.getResultList();
		return notes;

	}

	/*
	 * Retrieve details of a specific note
	 */

	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note = getSession().get(Note.class, noteId);
		if (note == null) {
			throw new NoteNotFoundException("Note is not available in the Database : " + noteId);
		} else {
			return note;
		}

	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		Note oldNote = getSession().get(Note.class, note.getNoteId());
		if (oldNote == null) {
			return false;
		} else {
			getSession().update(note);
			return true;
		}

	}

}
