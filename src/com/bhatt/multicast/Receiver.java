package com.bhatt.multicast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * checks buffer for any recieved messages
 * 
 * @author bhatt
 * 
 */
public class Receiver implements Runnable {

	// manages count
	long[] casualCounter = new long[Simulator.numberOfNodes];

	List<Message> receiverBuffer = new ArrayList<Message>();
	// Holds data delivered to this node
	List<Message> receivedData = new ArrayList<Message>();
	private MesssageContainer msgContainer;

	public Receiver(MesssageContainer msgContainer) {
		this.msgContainer = msgContainer;
	}

	public void run() {
		while (true) {
			// is it good to keep polling on this one
			// if the size of receive buffer is greater than zero
			// then check if any value is next in sequence of what is present in
			// receivedData
			// edge condition, for first - id should be zero
			// also remove the element which has been added to delivered list
			// from buffer
			// lets shuffle this data to preserve random order in incoming
			// content
			Collections.shuffle((List) msgContainer.receieveQueueOP(
					MesssageContainer.QUEUE_OPERATION.SHUFFLE, null));
			List<Message> msgList = msgContainer.receieveQueueOP(
					MesssageContainer.QUEUE_OPERATION.FETCH, null);
			if (msgList.size() > 0) {
				// iterate through the list
				for (Message msg : msgList) {
					System.out.println("### Buffered message:" + msg + "@"
							+ msgContainer.sequence);
					// check if this is next in sequence
					if (msg.getId() == (getCount(msg.getSender()))) {
						receivedData.add(msg);
						incrementCount(msg.getSender());
						System.out.println("### received message:" + msg + "@"
								+ msgContainer.sequence);
						msgContainer.receieveQueueOP(
								MesssageContainer.QUEUE_OPERATION.REMOVE, msg);
						break;
					}

				}
			}

			try {
				System.out.println("receiver sleeping");
				Thread.sleep(1000 * (int) (Math.random() * 6));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

		}

	}

	/**
	 * increment corresponding vector counter
	 * column is the sender
	 * @param column
	 */
	private void incrementCount(int column) {
		this.casualCounter[column] = this.casualCounter[column] + 1;
	}

	/**
	 * return counter value last received data
	 * @param column
	 * @return
	 */
	private long getCount(int column) {
		return this.casualCounter[column];
	}

}
