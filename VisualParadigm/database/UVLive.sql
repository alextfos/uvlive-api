CREATE TABLE Message (idMessage int(11) NOT NULL AUTO_INCREMENT, text varchar(1023), timestamp int(13), ConversationidConversation int(11) NOT NULL, RolUVidUser int(11) NOT NULL, PRIMARY KEY (idMessage));
CREATE TABLE Merchant (idUser int(11) NOT NULL AUTO_INCREMENT, dni varchar(9), PRIMARY KEY (idUser));
CREATE TABLE Broadcast (idBroadcast int(11) NOT NULL AUTO_INCREMENT, text int(255), MerchantIdUser int(11), PRIMARY KEY (idBroadcast));
CREATE TABLE Teacher (idUser int(11) NOT NULL AUTO_INCREMENT, PRIMARY KEY (idUser));
CREATE TABLE Student (idUser int(11) NOT NULL AUTO_INCREMENT, blocked int(1), PRIMARY KEY (idUser));
CREATE TABLE Conversation (idConversation int(11) NOT NULL AUTO_INCREMENT, name varchar(52), PRIMARY KEY (idConversation));
CREATE TABLE `User` (idUser int(11) NOT NULL AUTO_INCREMENT, userName varchar(8), password varchar(18), firstName varchar(63), lastName varchar(63), PRIMARY KEY (idUser));
CREATE TABLE RolUV (idUser int(11) NOT NULL AUTO_INCREMENT, pushToken varchar(255), PRIMARY KEY (idUser));
CREATE TABLE Admin (idUser int(11) NOT NULL AUTO_INCREMENT, PRIMARY KEY (idUser));
CREATE TABLE Conversation_RolUV (ConversationidConversation int(11) NOT NULL, RolUVidUser int(11) NOT NULL, PRIMARY KEY (ConversationidConversation, RolUVidUser));
ALTER TABLE Broadcast ADD INDEX send (MerchantIdUser), ADD CONSTRAINT send FOREIGN KEY (MerchantIdUser) REFERENCES Merchant (idUser);
ALTER TABLE Message ADD INDEX userWrites (RolUVidUser), ADD CONSTRAINT userWrites FOREIGN KEY (RolUVidUser) REFERENCES RolUV (idUser);
ALTER TABLE Message ADD INDEX had (ConversationidConversation), ADD CONSTRAINT had FOREIGN KEY (ConversationidConversation) REFERENCES Conversation (idConversation);
ALTER TABLE Conversation_RolUV ADD INDEX FKConversati824453 (ConversationidConversation), ADD CONSTRAINT FKConversati824453 FOREIGN KEY (ConversationidConversation) REFERENCES Conversation (idConversation);
ALTER TABLE Conversation_RolUV ADD INDEX FKConversati597337 (RolUVidUser), ADD CONSTRAINT FKConversati597337 FOREIGN KEY (RolUVidUser) REFERENCES RolUV (idUser);
