/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controller.conversations;

import es.uvlive.controller.BaseResponse;
import es.uvlive.model.Conversation;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conversations", propOrder = {
    "conversations"
})
@XmlRootElement(name = "response")
public class ConversationsListResponse extends BaseResponse {

    @XmlElement(name = "conversations")
    private ArrayList<ConversationResponse> conversations;
    
    public ConversationsListResponse () {
        conversations = new ArrayList();
    }
    
    public ConversationsListResponse (ArrayList<Conversation> conversations) {
        this();
        ConversationResponse cTmp;
        for (Conversation c:conversations) {
            cTmp = new ConversationResponse();
            cTmp.setId(c.getIdConversation());
            cTmp.setName(c.getName());
            this.conversations.add(cTmp);
        }
    }
    
    public ArrayList<ConversationResponse> getConversations() {
        return conversations;
    }
    
    public void add(ConversationResponse conversation ) {
        conversations.add(conversation);
    }
}

