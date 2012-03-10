package com.bhatt.multicast;


/**
 * Represents a node. Each node has two threads
 * one for listening & other for sending
 * @author bhatt
 *
 */

public class MessengerNode{
	
	public Sender sender;
	public Receiver receiver;
	public MesssageContainer msgContainer;
	private int messengerId;
	public Thread senderThread;
	public Thread receiverThread;
	
	public MessengerNode(int messengerId){
		msgContainer = new MesssageContainer(messengerId);
		this.messengerId = messengerId;
		sender = new Sender(msgContainer);
		receiver = new Receiver(msgContainer);
	}
	
}
