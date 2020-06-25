package com.stackroute.keepnote.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.repository.NoteRepository;

/*Annotate the class with @Controller annotation. @Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 * */
@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application
	 * requires us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the collection. Each note 
	 *    should contain Note Id, title, content, status and created date.
	 * 2. Add a new note which should contain the note id, title, content and status.
	 * 3. Delete an existing note.
	 * 4. Update an existing note.
	 */
	
	/* 
	 * Get the application context from resources/beans.xml file using ClassPathXmlApplicationContext() class.
	 * Retrieve the Note object from the context.
	 * Retrieve the NoteRepository object from the context.
	 */
	 ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	 Note note = context.getBean("note",Note.class);
	 NoteRepository noteRepository = context.getBean("noteRepository",NoteRepository.class);
	
	/*Define a handler method to read the existing notes by calling the getAllNotes() method 
	 * of the NoteRepository class and add it to the ModelMap which is an implementation of Map 
	 * for use when building model data for use with views. it should map to the default URL i.e. "/" */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String getAllNotes(ModelMap model) {
		 List<Note> notes = noteRepository.getAllNotes();
		 model.addAttribute("notes", notes);
		 return "index";
	 }
	
	/*Define a handler method which will read the Note data from request parameters and
	 * save the note by calling the addNote() method of NoteRepository class. Please note 
	 * that the createdAt field should always be auto populated with system time and should not be accepted 
	 * from the user. Also, after saving the note, it should show the same along with existing 
	 * notes. Hence, reading notes has to be done here again and the retrieved notes object 
	 * should be sent back to the view using ModelMap.
	 * This handler method should map to the URL "/saveNote". 
	*/
	@RequestMapping(value="/saveNote", method = RequestMethod.POST)
	public String addNote(@RequestParam("noteId") int noteId,@RequestParam("noteTitle") String noteTitle,
			@RequestParam("noteContent") String noteContent,@RequestParam("noteStatus") String noteStatus,Model model) {
		LocalDateTime date =null;
		List<Note> notes = null;
		Note note = null;
		if(noteTitle.isEmpty()||noteContent.isEmpty()||noteStatus.isEmpty()) {
			return "index";
		}
		date = LocalDateTime.now();
		note = new Note();
		note.setNoteId(noteId);
		note.setNoteTitle(noteTitle);
		note.setNoteContent(noteContent);
		note.setNoteStatus(noteStatus);
		note.setCreatedAt(date);
		noteRepository.addNote(note);
		notes = noteRepository.getAllNotes();
		model.addAttribute("notes",notes);
		return "index";
	}
	
	/* Define a handler method to delete an existing note by calling the deleteNote() method 
	 * of the NoteRepository class
	 * This handler method should map to the URL "/deleteNote" 
	*/
	@RequestMapping(value = "/deleteNote", method= RequestMethod.GET)
	public String deleteNotes(@RequestParam(value = "noteId") int noteId,ModelMap model) {
		noteRepository.deleteNote(noteId);
		return "redirect:/";
	}
}