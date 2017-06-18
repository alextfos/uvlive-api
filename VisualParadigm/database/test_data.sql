USE uvlive;
/* Students  */
INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (1, 'alagon2', 'alagon2', 'Alberto', 'Ávila');

INSERT INTO RolUV (idUser)
VALUES (1);

INSERT INTO Student (idUser, blocked)
VALUES (1, 0);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (2, 'joanalmo', 'joanalmo', 'Jose', 'Albaladejo');

INSERT INTO RolUV (idUser)
VALUES (2);

INSERT INTO Student (idUser, blocked)
VALUES (2, 0);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (3, 'chdera', 'chdera', 'Christian', 'Degroote');

INSERT INTO RolUV (idUser)
VALUES (3);

INSERT INTO Student (idUser, blocked)
VALUES (3, 0);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (4, 'atraifos', 'atraifos', 'Alejandro', 'Traver');

INSERT INTO RolUV (idUser)
VALUES (4);

INSERT INTO Student (idUser, blocked)
VALUES (4, 0);


/* Teachers */
INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (20, 'morillo','morillo', 'Pedro', 'Morillo');

INSERT INTO RolUV (idUser)
VALUES (20);

INSERT INTO Teacher (idUser)
VALUES (20);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (21, 'claver','claver', 'Jose Manuel', 'Claver');

INSERT INTO RolUV (idUser)
VALUES (21);

INSERT INTO Teacher (idUser)
VALUES (21);

INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (22, 'rueda','rueda', 'Silvia', 'Rueda');

INSERT INTO RolUV (idUser)
VALUES (22);

INSERT INTO Teacher (idUser)
VALUES (22);

/* Merchant */
INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (30, 'manolo','1006', 'Manuel', 'Rodriguez');

INSERT INTO Merchant (idUser, dni)
VALUES (30, '12345678P');

/* Admin */
INSERT INTO User (idUser, userName, password, firstName, lastName)
VALUES (40, 'admin','1006', 'nombre', 'apellido');

INSERT INTO Admin (idUser)
VALUES (40);

/* Conversations */

INSERT INTO Conversation (idConversation,name)
VALUES (1,'Gestión de Proyectos');

INSERT INTO Conversation (idConversation,name)
VALUES (2,'Arquitectura de Computadores');

INSERT INTO Conversation (idConversation,name)
VALUES (3,'Ingeniería del Software');

INSERT INTO Conversation (idConversation,name)
VALUES (4,'Trabajo de Fin de Grado');

/* Gestión de Proyectos */
INSERT INTO Conversation_RolUV (ConversationidConversation,RolUVidUser)
VALUES (1,1);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (1,2);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (1,3);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (1,4);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (1,20);

/* Arquitectura de Computadores */
INSERT INTO Conversation_RolUV (ConversationidConversation,RolUVidUser)
VALUES (2,1);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (2,2);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (2,3);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (2,4);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (2,21);

/* Ingeniería del software */
INSERT INTO Conversation_RolUV (ConversationidConversation,RolUVidUser)
VALUES (3,1);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (3,2);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (3,3);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (3,4);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (3,22);

/* Trabajo de Fin de Grado*/
INSERT INTO Conversation_RolUV (ConversationidConversation,RolUVidUser)
VALUES (4,1);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (4,2);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (4,3);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (4,4);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (4,20);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (4,21);

INSERT INTO Conversation_RolUV (ConversationidConversation, RolUVidUser)
VALUES (4,22);
