<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Update Note</title>
	</head>
	<body>
		<form action="update" method="post" modelAttribute="note">
			Note Id : <input type="text" name="noteId" value="${note.noteId}"><br>
			Note Title : <input type="text" name="noteTitle"><br>
			Note Content : <input type="text" name="noteContent"><br>
			Note Status : <select name="noteStatus" id="noteStatus">
							<option value="not decided">select</option>
							<option value="completed">completed</option>
							<option value="pending">pending</option>
						</select><br>
			<input type="submit" value="update">
			
		</form>
	</body>
</html>