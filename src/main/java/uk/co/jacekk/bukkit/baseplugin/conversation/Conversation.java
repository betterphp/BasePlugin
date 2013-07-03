package uk.co.jacekk.bukkit.baseplugin.conversation;

import org.bukkit.command.CommandSender;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;

/**
 * Represents a two-way communication with a user
 *
 * @param <T> The type of object being conversed with
 */
public abstract class Conversation<T extends CommandSender> {
	
	private BasePlugin plugin;
	private T with;
	
	private Node<? extends Conversation<? extends CommandSender>, T> nextNode;
	private boolean suppressChat;
	
	/**
	 * @param with The object being conversed with
	 */
	public Conversation(BasePlugin plugin, T with){
		this.plugin = plugin;
		this.with = with;
		
		this.nextNode = null;
		this.suppressChat = false;
	}
	
	/**
	 * Gets the object being conversed with
	 * 
	 * <p>
	 * 	Note that this object has to be a {@link CommandSender}
	 * </p>
	 * 
	 * @return The object
	 */
	public T getWith(){
		return this.with;
	}
	
	/**
	 * Sets the first node in the conversation, this is the first thing the user will see.
	 * 
	 * @param node The node.
	 */
	public void setStartNode(Node<? extends Conversation<? extends CommandSender>, T> node){
		if (this.nextNode != null){
			throw new IllegalStateException("Start node already set");
		}
		
		this.nextNode = node;
	}
	
	/**
	 * If true the user will not receive chat message while in the conversation.
	 * 
	 * @return True if the conversation is currently suppressing chat messages false if not. 
	 */
	public boolean isSuppressingChat(){
		return this.suppressChat;
	}
	
	/**
	 * Sets the suppress chat flag for the conversation.
	 * 
	 * @param suppress The flag.
	 */
	public void setSuppressChat(boolean suppress){
		this.suppressChat = suppress;
	}
	
	/**
	 * The conversation has ended if there are no more nodes.
	 * 
	 * @return The flag
	 */
	public boolean isEnded(){
		return (this.nextNode == null);
	}
	
	/**
	 * Starts the conversation.
	 * 
	 * @throws IllegalStateException If the start node is not set.
	 */
	public void start() throws IllegalStateException {
		if (this.nextNode == null){
			throw new IllegalStateException("Start node not set");
		}
		
		String prompt = this.nextNode.getPromptText();
		
		if (prompt != null){
			this.getWith().sendMessage(prompt);
		}
		
		this.plugin.conversationManager.addConversation(this);
	}
	
	/**
	 * Aborts the conversation
	 */
	public void abort(){
		this.onAbort();
		
		this.plugin.conversationManager.removeConversation(this);
	}
	
	/**
	 * Called when the conversation is aborted
	 */
	public abstract void onAbort();
	
	/**
	 * Called when the conversations receives input from the user.
	 * 
	 * <p>
	 * 	By default this passes the input to the current node and updates the next node from that.
	 * </p>
	 * 
	 * @param input The input
	 */
	public void onInput(String input){
		Node<? extends Conversation<? extends CommandSender>, T> node = this.nextNode.processInput(input);
		
		if (node != null && (!node.isRecurring() || !node.getClass().equals(this.nextNode.getClass()))){
			String prompt = node.getPromptText();
			
			if (prompt != null){
				this.getWith().sendMessage(prompt);
			}
		}
		
		this.nextNode = node;
	}
	
}
