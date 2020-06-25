
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KeepNote</title>
</head>
<body>
	<!-- Create a form which will have text boxes for Note ID, title, content and status along with a Send 
		 button. Handle errors like empty fields -->
	<form action="saveNote" method="post">
		Note Id : <input type="text" name="noteId"><br>
		Title : <input type="text" name="noteTitle"><br>
		Content : <input type="text" name="noteContent"><br>
		Status : <input type="text" name="noteStatus"><br><br>
		<input type="submit" value="save"/>
	</form>
	<!-- display all existing notes in a tabular structure with Id, Title,Content,Status, Created Date and Action -->
	
	<table border="1"> 
		<thead>
			<td>Note Id</td>
			<td>Title</td>
			<td>Content</td>
			<td>Status</td>
			<td>CreateAt</td>
			<td>Action</td>
		</thead>
		
			<c:forEach var="note" items="${notes}">
				<tr>
					<td>${note.noteId}</td><br>
					<td>${note.noteTitle}</td><br>
					<td>${note.noteContent}</td><br>
					<td>${note.noteStatus}</td><br>
					<td>${note.createdAt}</td>
					<td>
						<form action="deleteNote" method="get">
							<input type="text" name="noteId" value="${note.noteId}">
							<input type="submit" value="delete">
						</form>
					</td>
				</tr>
			</c:forEach>
	</table>
</body>
</html>