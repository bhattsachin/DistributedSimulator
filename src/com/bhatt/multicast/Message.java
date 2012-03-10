package com.bhatt.multicast;

public class Message {

	//so this puts a limit on what is the longest 
	//sequence number it can hold
	private long id;
	private String message;
	
	public Message(){
		
	}
	
	public Message(long id, String message){
		this.id = id;
		this.message = message;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + "]";
	}
	
	
	
	
}
