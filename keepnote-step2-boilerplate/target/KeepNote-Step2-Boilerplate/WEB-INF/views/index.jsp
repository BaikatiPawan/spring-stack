<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Keep-Board</title>
</head>

<body>
	<!-- Create a form which will have text boxes for Note title, content and status along with a Add 
		 button. Handle errors like empty fields.  (Use dropdown-list for NoteStatus) -->
	<form action="add" method="post">
		Note Title:<input type="text" name="noteTitle"><br>
		Note Content :<input type="text" name="noteContent"><br>
		<label for="noteStatus">Note Status :</label>
		<select name="noteStatus" id="noteStatus">
						<option value="not decided">select</option>
						<option value="completed">completed</option>
						<option value="pending">pending</option>
					</select><br>
		<input type="submit"  value="Add"/><br>
	</form>
	<!-- display all existing notes in a tabular structure with Title,Content,Status, Created Date and Action -->
	<table border="1">
		<thead>
			<td>Note Id</td>
			<td>Title</td>
			<td>Content</td>
			<td>Status</td>
			<td>CreatedAt</td>
			<td>Action</td>
		</thead>
		<c:forEach var="note" items="${notes}">
			<tr>
				<td>${note.noteId}</td>
				<td>${note.noteTitle}</td>
				<td>${note.noteContent}</td>
				<td>${note.noteStatus}</td>
				<td>${note.createdAt}</td>
				<td>
						<form action="delete" method="get">
							<input type="text" name="noteId" value="${note.noteId}">
							<input type="submit" value="delete">
						</form>
						<form action="updateNote" method="post">
							<input type="text" name="noteId" value="${note.noteId}">
							<input type="submit" value="update">
						</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>

</html>