package es.uvlive.eda;

import java.util.ArrayList;
import java.util.List;

import es.uvlive.model.Message;

public class ElasticListTest {
	
	private static final int NUM_ELEMENTS = 20;
	
	private static ElasticList<Message> testList;
	
	public static void main(String args[]) {
		
		ArrayList<Message> messageArrayList = new ArrayList<>();
		
		testList = new ElasticList<>(new ElasticList.OnFillBufferCallback<Message>() {

			@Override
			public void onBufferFilled(List<Message> elements) {
				for (Message message: elements) {
					System.out.println(message);
				}
				
			}
		});
		
		for (int i=0 ; i<20 ; i++) {
			Message message = new Message();
			
			message.setIdMessage(i);
			message.setText("Message "+i);
			message.setTimestamp(i);
			
			messageArrayList.add(message);
		}
		
		for (Message message: messageArrayList) {
			testList.add(message);
		}
		
	}

}
