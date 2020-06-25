package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private NoteRepository noteRepository;

	@Autowired
	public NoteServiceImpl(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		NoteUser noteUser = new NoteUser();
		List<Note> notes = new ArrayList<>();
		notes.add(note);
		noteUser.setNotes(notes);
		NoteUser createdNoteUser = noteRepository.insert(noteUser);
		if (createdNoteUser != null)
			return true;
		else
			return false;
	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(String userId, int noteId) {
		try {
			NoteUser noteUser = new NoteUser();
			Note note = new Note();
			NoteUser existingNoteUser = noteRepository.findById(userId).get();
			List<Note> notes = existingNoteUser.getNotes();
			Iterator<Note> iterator = notes.listIterator();
			while (iterator.hasNext()) {
				note = iterator.next();
				if (note.getNoteId() == noteId)
					iterator.remove();
			}
			noteUser.setUserId(userId);
			noteUser.setNotes(notes);
			noteRepository.save(noteUser);
			return true;
		} catch (Exception ex) {
			throw ex;
		}

	}

	/* This method should be used to delete all notes with specific userId. */

	public boolean deleteAllNotes(String userId) {

		try {
			NoteUser noteUser = new NoteUser();
			List<Note> notes = noteRepository.findById(userId).get().getNotes();
			Iterator<Note> iterator = notes.listIterator();
			while (iterator.hasNext()) {
				iterator.next();
				iterator.remove();
			}
			noteUser.setUserId(userId);
			noteUser.setNotes(notes);
			noteRepository.save(noteUser);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {

		try {
			NoteUser noteUser = new NoteUser();
			List<Note> notes = noteRepository.findById(userId).get().getNotes();
			Iterator<Note> iterator = notes.listIterator();
			while (iterator.hasNext()) {
				note = (Note) iterator.next();
				if (note.getNoteId() == id)
					iterator.remove();
			}
			notes.add(note);
			noteUser.setUserId(userId);
			noteUser.setNotes(notes);
			noteRepository.save(noteUser);
			return note;
		} catch (Exception e) {
			throw new NoteNotFoundExeption("Note not Found");
		}
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		Note note = null;
		NoteUser noteUser = null;
		try {
			noteUser = noteRepository.findById(userId).orElse(null);

			if (null == noteUser) {
				throw new NoteNotFoundExeption("Note not found");
			} else {
				List<Note> notes = noteUser.getNotes();
				for (Note note1 : notes) {
					if (note1.getNoteId() == noteId) {
						note = note1;
					}
				}
			}

			return note;
		} catch (Exception e) {
			throw new NoteNotFoundExeption("");

		}
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		NoteUser noteUser = noteRepository.findById(userId).orElse(null);
		return noteUser.getNotes();
	}

}
