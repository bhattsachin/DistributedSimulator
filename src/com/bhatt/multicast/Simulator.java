package com.bhatt.multicast;

import java.util.HashMap;
import java.util.Map;

public class Simulator {

	//total number of nodes for this simulation
	public static int numberOfNodes = 3;

	public static Map<String, MessengerNode> messengerMap = new HashMap<String, MessengerNode>();

	public Simulator() {

	}

	public void createNodes() {

		for (int i = 0; i < numberOfNodes; i++) {
			messengerMap.put(String.valueOf(i), new MessengerNode(i));
		}
	}

	/**
	 * Send this message to all nodes in system
	 * except to myself
	 * @param myId
	 * @param msg
	 */
	public static void sendMessage(int myId, Message msg) {

		for (int i = 0; i < numberOfNodes; i++) {
			if (myId != i) {
				MessengerNode node = messengerMap.get(String.valueOf(i));
				// simulate the network that message is received here
				node.msgContainer.receieveQueueOP(MesssageContainer.QUEUE_OPERATION.INSERT, msg);
			}
		}

	}
	
	//start all threads

	public static void main(String args[]) {
		Simulator simulator = new Simulator();
		simulator.createNodes();
		MessengerNode node;

		for (Map.Entry<String, MessengerNode> mapval : simulator.messengerMap
				.entrySet()) {
			
			node = mapval.getValue();
			//on next entry these might get eaten up by garbage collector
			//so need to hold them
			
			node.senderThread = new Thread(node.sender);
			node.senderThread.start();
			
			node.receiverThread = new Thread(node.receiver);
			node.receiverThread.start();
			

		}

		// simulator.sendMessage(myId, sequenceId)
	}

}
