package com.bhatt.multicast;

/**
 * Sends a message from input buffer
 * 
 * @author bhatt
 * 
 */
public class Sender implements Runnable {

	MesssageContainer msgContainer;

	public Sender(MesssageContainer msgContainer) {
		this.msgContainer = msgContainer;
	}

	public void run() {
		// is it good to spin on this for ever.
		// bad that my cpu is wasting cycles on it
		while (true) {
			if (!this.msgContainer.sendingQueue.isEmpty()) {
				System.out.println("-- sending message:("+this.msgContainer.sendingQueue.peek().getId()+
						"/" + (MesssageContainer.numberOfMessages-1) + " ~~~~~~"
						+ this.msgContainer.sendingQueue.peek());
				Simulator.sendMessage(this.msgContainer.sequence,
						this.msgContainer.sendingQueue.poll());
				
			}

			try {
				System.out.println("sender sleeping");
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

	}
}
