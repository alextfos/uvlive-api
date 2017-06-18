package es.uvlive.model;

import java.sql.SQLException;
import java.util.*;

import es.uvlive.exceptions.ConversationNotCreatedException;
import es.uvlive.model.dao.TutorialDAO;

public class TutorialCatalog {
	private Collection<Tutorial> tutorials;
	
	// TODO @Non-generated 
	public TutorialCatalog() {
		try {
			tutorials = new TutorialDAO().getTutorials();
		} catch (Exception e) {
			System.out.println("Error intializing TutorialCatalog: " + e.getMessage());
		}
	}

	public Collection<es.uvlive.model.Tutorial> getTutorials(User user) throws SQLException, ClassNotFoundException {
		// TODO @Non-generated
		Collection<es.uvlive.model.Tutorial> resultTutorials = new ArrayList<es.uvlive.model.Tutorial>();
		ArrayList<Integer> idsOfTutorialsCollection = new TutorialDAO().getIdsOfUserTutorials(user);
		for (Tutorial tutorial : tutorials) {
			for (Integer idConversation : idsOfTutorialsCollection ){
				if (tutorial.getIdTutorial() == idConversation) {
					resultTutorials.add(tutorial);
				}
			}
		}
		
		return resultTutorials;
	}
	
	public Tutorial addAndGetConversation(int idConversation) throws ClassNotFoundException, SQLException, ConversationNotCreatedException {
		Tutorial tutorial = new TutorialDAO().getTutorial(idConversation);
		if (tutorial != null) {
			tutorials.add(tutorial);
		} else {
			throw new ConversationNotCreatedException();
		}
		return tutorial;
	}
	
}