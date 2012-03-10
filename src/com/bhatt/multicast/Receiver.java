package com.bhatt.multicast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * checks buffer for any recieved messages
 * @author bhatt
 *
 */
public class Receiver implements Runnable {
	
	List<Message> receiverBuffer = new ArrayList<Message>();
	//Holds data delivered to this node
	List<Message> receivedData = new ArrayList<Message>();
	private MesssageContainer msgContainer;
	
	public Receiver(MesssageContainer msgContainer){
		this.msgContainer = msgContainer;
	}
	
	public void run(){
		while(true){
			//is it good to keep polling on this one
			//if the size of receive buffer is greater than zero
			//then check if any value is next in sequence of what is present in receivedData
			//edge condition, for first - id should be zero
			//also remove the element which has been added to delivered list from buffer
			//lets shuffle this data to preserve random order in incoming content
			Collections.shuffle((List)msgContainer.receieveQueueOP(MesssageContainer.QUEUE_OPERATION.SHUFFLE, null));
			List<Message> msgList = msgContainer.receieveQueueOP(MesssageContainer.QUEUE_OPERATION.FETCH, null);
			if(msgList.size()>0){
				//iterate through the list
				for(Message msg: msgList){
					System.out.println("### Buffered message:" + msg + "@" + msgContainer.sequence);
					if(receivedData.size()==0){
						//if this is the first message we received
						if(msg.getId()==0){
							receivedData.add(msg);
							System.out.println("### received message:" + msg + "@" + msgContainer.sequence);
							msgContainer.receieveQueueOP(MesssageContainer.QUEUE_OPERATION.REMOVE, msg);
							break;
						}
					}else{
						//check if this is next in sequence
						if(msg.getId() == (receivedData.get(receivedData.size()-1).getId() + 1)){
							receivedData.add(msg);
							System.out.println("### received message:" + msg + "@" + msgContainer.sequence);
							msgContainer.receieveQueueOP(MesssageContainer.QUEUE_OPERATION.REMOVE, msg);
							break;
						}
					}
					
				}
			}
			
			try {
				System.out.println("receiver sleeping");
				Thread.sleep(1000 * (int)(Math.random()*6));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			
			
		}
		
	}

}
