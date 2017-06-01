USE uvlive;

INSERT INTO Student (user,password)
VALUES ('alumno','1006');

INSERT INTO Teacher (user,password)
VALUES ('profesor','1006');

INSERT INTO Administrator (user,password)
VALUES ('admin','1006');

INSERT INTO Conversation (name)
VALUES ('Tutoria de Prueba');

INSERT INTO Conversation (name)
VALUES ('Tutoria de Prueba 2');

INSERT INTO Conversation_Student (ConversationidConversation,StudentidStudent)
VALUES (/*SELECT idConversation
		FROM Conversation
		WHERE name='Tutoria de Prueba'*/1,
		/*SELECT idStudent
		FROM Student
		WHERE user='alumno'*/1);

INSERT INTO Conversation_Teacher (ConversationidConversation,TeacheridTeacher)
VALUES (/*SELECT idConversation
		FROM Conversation
		WHERE name='Tutoria de Prueba'*/1,
		/*SELECT idStudent
		FROM Student
		WHERE user='profesor'*/1);