package com.bhatt.multicast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Holds content to be shared by threads and base class
 * @author bhatt
 *
 */
public class MesssageContainer {
	public Queue<Message> sendingQueue = new LinkedList<Message>();
	//receiving queue needs to be synchronized as being accessed by multiple threads
	private Queue<Message> receivingQueue = new LinkedList<Message>();
	public static int numberOfMessages = 5;
	enum QUEUE_OPERATION {INSERT, PEEK, POLL, SHUFFLE, FETCH, REMOVE};

	
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
	
	public synchronized List<Message> receieveQueueOP(QUEUE_OPERATION OP, Message msg){
		List<Message> msgList = new ArrayList<Message>();
		switch(OP){
		case INSERT: receivingQueue.add(msg);
					break;
		case PEEK:	msg = receivingQueue.peek();
					break;
		case POLL: 	msg = receivingQueue.poll();
					break;
		case SHUFFLE: Collections.shuffle((List) receivingQueue);
					break;
		case FETCH:	msgList = new ArrayList<Message>(Arrays.asList(new Message[receivingQueue.size()])); 
					Collections.copy(msgList, (List)receivingQueue);
					break;
		case REMOVE: receivingQueue.remove(msg);
					
		}
		
		if(msg!=null)
			msgList.add(msg);
	
		return msgList;
	}

}
