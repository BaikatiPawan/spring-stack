package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */
	private SessionFactory sessionFactory;
	
	@Autowired
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note) {
		Object obj =sessionFactory.getCurrentSession().save(note);
		if(obj== null) {
			System.out.println("false");
			return false;
		}
		else {
			System.out.println("true");
			return true;
		}

	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from Note where noteId= :noteId");
		query.setParameter("noteId", noteId);
		int result = query.executeUpdate();
		if(result == 0)
			return false;
		else
			return true;

	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	@SuppressWarnings("unchecked")
	public List<Note> getAllNotes() {
		return sessionFactory.getCurrentSession().createQuery("from Note").getResultList();
	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(int noteId) {
		 Session session = sessionFactory.getCurrentSession();
		 Note note = session.get(Note.class, noteId);
		 session.flush();
		 return note;

	}

	/* Update existing note */

	public boolean UpdateNote(Note note) {
		if(getNoteById(note.getNoteId()) == null){
			return false;
		}else {
			sessionFactory.getCurrentSession().clear();
			sessionFactory.getCurrentSession().update(note);
			return true;
		}

	}

}
