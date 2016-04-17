package app.glintcarwash.com.glintworkerapp;

public class ChatMessage {
	public boolean left;
	public boolean isImage;
	public String message;

	public ChatMessage(boolean left, String message) {
		super();
		this.left = left;
		this.message = message;
	}
}