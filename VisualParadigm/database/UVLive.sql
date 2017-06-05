CREATE TABLE Message (idMessage int(11) NOT NULL AUTO_INCREMENT, text varchar(1023), timestamp int(13), ConversationidConversation int(11) NOT NULL, RolUVidUser int(11) NOT NULL, PRIMARY KEY (idMessage));
CREATE TABLE Businessman (idUser int(11) NOT NULL AUTO_INCREMENT, dni varchar(9), PRIMARY KEY (idUser));
CREATE TABLE Broadcast (idBroadcast int(11) NOT NULL AUTO_INCREMENT, text varchar(255), BusinessmanidBusinessman int(11), expiration_date int(13), PRIMARY KEY (idBroadcast));
CREATE TABLE Teacher (idUser int(11) NOT NULL AUTO_INCREMENT, PRIMARY KEY (idUser));
CREATE TABLE Student (idUser int(11) NOT NULL AUTO_INCREMENT, blocked int(1), PRIMARY KEY (idUser));
CREATE TABLE Conversation (idTutorial int(11) NOT NULL AUTO_INCREMENT, name varchar(52), PRIMARY KEY (idTutorial));
CREATE TABLE User (idUser int(11) NOT NULL AUTO_INCREMENT, userName varchar(8), password varchar(18), firstName varchar(63), lastName varchar(63), PRIMARY KEY (idUser));
CREATE TABLE RolUV (idUser int(11) NOT NULL AUTO_INCREMENT, pushToken varchar(255), PRIMARY KEY (idUser));
CREATE TABLE Admin (idUser int(11) NOT NULL AUTO_INCREMENT, PRIMARY KEY (idUser));
CREATE TABLE Conversation_RolUV (ConversationidTutorial int(11) NOT NULL, RolUVidUser int(11) NOT NULL, PRIMARY KEY (ConversationidTutorial, RolUVidUser));
ALTER TABLE Broadcast ADD INDEX send (ComercianteidComerciante), ADD CONSTRAINT send FOREIGN KEY (ComercianteidComerciante) REFERENCES Businessman (idUser);
ALTER TABLE Message ADD INDEX userWrites (RolUVidUser), ADD CONSTRAINT userWrites FOREIGN KEY (RolUVidUser) REFERENCES RolUV (idUser);
ALTER TABLE Message ADD INDEX had (ConversationidConversation), ADD CONSTRAINT had FOREIGN KEY (ConversationidConversation) REFERENCES Conversation (idTutorial);
ALTER TABLE Conversation_RolUV ADD INDEX FKConversati32130 (ConversationidTutorial), ADD CONSTRAINT FKConversati32130 FOREIGN KEY (ConversationidTutorial) REFERENCES Conversation (idTutorial);
ALTER TABLE Conversation_RolUV ADD INDEX FKConversati597337 (RolUVidUser), ADD CONSTRAINT FKConversati597337 FOREIGN KEY (RolUVidUser) REFERENCES RolUV (idUser);
