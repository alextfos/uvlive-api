package es.uvlive.api.requests;

public class NewMessagesOperation extends Operation {

    private int idConversation;

    public NewMessagesOperation(String type, String operation, int idConversation) {
        super(type, operation);
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }
}
