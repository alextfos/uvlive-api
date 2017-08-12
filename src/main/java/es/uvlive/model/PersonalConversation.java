package es.uvlive.model;

public class PersonalConversation extends Conversation {

    private String participant1;
    private String participant2;

    public PersonalConversation(int idConversation) {
        super(idConversation);
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }
}
