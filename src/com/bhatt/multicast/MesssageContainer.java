package com.bhatt.multicast;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Holds content to be shared by threads and base class
 * @author bhatt
 *
 */
public class MesssageContainer {
	public Queue<Message> sendingQueue = new LinkedList<Message>();
	public Queue<Message> receivingQueue = new LinkedList<Message>();
	public static int numberOfMessages = 5;
	
	public int sequence;
	
	public MesssageContainer(int sequence){
		this.sequence = sequence;
		init();
	}
	
	public void init(){
		for(int i=0;i<MesssageContainer.numberOfMessages;i++){
			sendingQueue.add(new Message(i,i+"  Message of " + this.sequence));
		}
	}

}
