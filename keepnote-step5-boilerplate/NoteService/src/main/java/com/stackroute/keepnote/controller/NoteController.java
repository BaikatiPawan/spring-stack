package com.stackroute.keepnote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class NoteController {

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	private NoteService noteService;

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the note created
	 * successfully. 2. 409(CONFLICT) - If the noteId conflicts with any existing
	 * user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST
	 * method
	 */

	@PostMapping("/api/v1/note")
	public ResponseEntity<String> createNote(@RequestBody Note note) {
		boolean create;
		try {

			create = noteService.createNote(note);
			if (!create) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);

			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		}

		return new ResponseEntity<String>("success", HttpStatus.CREATED);

	}

	/*
	 * Define a handler method which will delete a note from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}"
	 * using HTTP Delete method" where "id" should be replaced by a valid noteId
	 * without {}
	 */
	@DeleteMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId,
			@PathVariable("noteId") int noteId) {

		if (noteService.deleteNote(userId, noteId)) {
			return new ResponseEntity<String>("success", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("error", HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}"
	 * using HTTP PUT method.
	 */

	@PutMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<String> updateNote(@RequestBody Note note, @PathVariable("userId") String userId,
			@PathVariable("noteId") int noteId) {
		try {

			Note noteDb = noteService.updateNote(note, noteId, userId);
			if (null == noteDb) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/*
	 * Define a handler method which will get us the all notes by a userId. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{userId}" using HTTP
	 * GET method
	 */

	@RequestMapping(value = "/api/v1/note/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<Note>> getAllNotesByUserId(@PathVariable("userId") String userId) {

		List<Note> noteList = noteService.getAllNoteByUserId(userId);
			return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);
	}

	/*
	 * Define a handler method which will show details of a specific note created by
	 * specific user. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the note found
	 * successfully. 2. 404(NOT FOUND) - If the note with specified noteId is not
	 * found. This handler method should map to the URL
	 * "/api/v1/note/{userId}/{noteId}" using HTTP GET method where "id" should be
	 * replaced by a valid reminderId without {}
	 * 
	 */
	@RequestMapping(value = "/api/v1/note/{userId}/{noteId}", method = RequestMethod.GET)
	public ResponseEntity<Note> getNoteByUserId(@PathVariable("userId") String userId,
			@PathVariable("noteId") int noteId) {

		Note note = null;
		try {
			note = noteService.getNoteByNoteId(userId, noteId);
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/v1/note/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAll(@PathVariable("userId") String userId) {

		try {
			if (noteService.deleteAllNotes(userId)) {
				return new ResponseEntity<String>("success", HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("error", HttpStatus.NOT_FOUND);
			}
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<String>("error", HttpStatus.NOT_FOUND);

		}
	}

}
