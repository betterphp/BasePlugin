package uk.co.jacekk.bukkit.baseplugin.conversation;

import org.bukkit.command.CommandSender;

/**
 * Represents a point in the conversation.
 * 
 * @param <T> The type of object being conversed with
 */
public abstract class Node<C extends Conversation<? extends CommandSender>, T extends CommandSender> {
	
	private C convo;
	private boolean recurring;
	
	protected Node(C convo){
		this.convo = convo;
		this.recurring = true;
	}
	
	/**
	 * Gets the conversation
	 * 
	 * @return The conversation
	 */
	protected C getConversation(){
		return this.convo;
	}
	
	/**
	 * A recurring conversation node will not send it's prompt text multiple times if looped, defaults to true
	 * 
	 * @return The flag
	 */
	public boolean isRecurring(){
		return this.recurring;
	}
	
	/**
	 * Sets the recurring flag.
	 * 
	 * @param recurring The flag
	 */
	protected void setRecurring(boolean recurring){
		this.recurring = recurring;
	}
	
	/**
	 * Gets the text that will be sent to the user.
	 * 
	 * <p>
	 * 	Note that returning null will result in no text being sent.
	 * </p>
	 * 
	 * @return The text
	 */
	public abstract String getPromptText();
	
	/**
	 * Processes the user's response
	 * 
	 * @param input The text the user entered
	 * @return The next Node in the conversation or null to end here.
	 */
	public abstract Node<C, T> processInput(String input);
	
}
