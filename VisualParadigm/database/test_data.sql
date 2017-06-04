USE uvlive;

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (1, 'alumno', '1006', 'nombre', 'apellido');

INSERT INTO RolUV (idUser)
VALUES (1);

INSERT INTO Student (idUser, blocked)
VALUES (1, 0);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (2, 'profesor','1006', 'nombre', 'apellido');

INSERT INTO RolUV (idUser)
VALUES (2);

INSERT INTO Teacher (idUser)
VALUES (2);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (3, 'admin','1006', 'nombre', 'apellido');

INSERT INTO Admin (idUser)
VALUES (3);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (4, 'manolo','1006', 'nombre', 'apellido');

INSERT INTO Businessman (idUser, dni)
VALUES (4, '12345678P');

INSERT INTO Conversation (name)
VALUES ('Tutoria de Prueba');

INSERT INTO Conversation (name)
VALUES ('Tutoria de Prueba 2');

INSERT INTO Conversation_RolUV (ConversationidTutorial,RolUVidUser)
VALUES (/*SELECT idConversation
		FROM Conversation
		WHERE name='Tutoria de Prueba'*/1,
		/*SELECT idStudent
		FROM Student
		WHERE user='alumno'*/1);

INSERT INTO Conversation_RolUV (ConversationidTutorial, RolUVidUser)
VALUES (/*SELECT idConversation
		FROM Conversation
		WHERE name='Tutoria de Prueba'*/1,
		/*SELECT idStudent
		FROM Student
		WHERE user='profesor'*/2);