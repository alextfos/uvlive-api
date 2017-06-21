package es.uvlive.eda;

import java.util.ArrayList;
import java.util.List;

import es.uvlive.model.Message;

public class ElasticListTest {
	
	private static final int NUM_ELEMENTS = 30;
	private static final int MAX_SIZE = 20;
	private static final int BUFFER_SIZE = 2;
	
	public static void main(String args[]) {
		ElasticList<Message> testList;
		
		ArrayList<Message> messageArrayList = new ArrayList<>();
		ArrayList<Message> previousMessages = new ArrayList<>();

		testList = new ElasticList<>(MAX_SIZE,BUFFER_SIZE,new ElasticList.OnFillBufferCallback<Message>() {

			@Override
			public void onBufferFilled(List<Message> elements) {
				for (Message message: elements) {
					System.out.println(message);
				}
				
			}
		});
		int timestamp = 1;
		for (int i=0 ; i<NUM_ELEMENTS ; i++) {
			Message previousMessage = new Message();

			previousMessage.setIdMessage(i);
			previousMessage.setText("Previous message " + (i));
			previousMessage.setTimestamp(timestamp++);

			previousMessages.add(previousMessage);
		}

		for (int i=0 ; i<NUM_ELEMENTS ; i++) {
			Message message = new Message();
			
			message.setIdMessage(i+NUM_ELEMENTS);
			message.setText("Message "+(i+NUM_ELEMENTS));
			message.setTimestamp(timestamp++);
			
			messageArrayList.add(message);
		}
		testList.addAll(previousMessages);
		
		for (Message message: messageArrayList) {
			testList.add(message);
		}
		
		System.out.println("List content: \n\n" + testList);
		
	}

}
