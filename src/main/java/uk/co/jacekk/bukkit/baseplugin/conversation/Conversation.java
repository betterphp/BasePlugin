package uk.co.jacekk.bukkit.baseplugin.conversation;

import java.util.Iterator;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;

/**
 * Represents a two-way communication with a user
 *
 * @param <T> The type of object being conversed with
 */
public abstract class Conversation<T extends CommandSender> implements Listener {
	
	private BasePlugin plugin;
	private T with;
	
	private Node<? extends Conversation<? extends CommandSender>, T> nextNode;
	private boolean suppressChat;
	private String abortMessage;
	
	/**
	 * @param with The object being conversed with
	 */
	public Conversation(BasePlugin plugin, T with){
		this.plugin = plugin;
		this.with = with;
		
		this.nextNode = null;
		this.suppressChat = false;
		this.abortMessage = null;
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
	 * Sets the abort message for the conversation, will end the conversation when received.
	 * 
	 * @param message The message
	 */
	public void setAbortMessage(String message){
		this.abortMessage = message;
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
		
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
		
		String prompt = this.nextNode.getPromptText();
		
		if (prompt != null){
			this.getWith().sendMessage(prompt);
		}
	}
	
	/**
	 * Aborts the conversation
	 */
	public void abort(){
		this.onAbort();
		
		HandlerList.unregisterAll(this);
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
		if (this.abortMessage != null && this.abortMessage.equalsIgnoreCase(input)){
			this.abort();
			return;
		}
		
		Node<? extends Conversation<? extends CommandSender>, T> node = this.nextNode.processInput(input);
		
		if (node != null && (!node.isRecurring() || !node.getClass().equals(this.nextNode.getClass()))){
			String prompt = node.getPromptText();
			
			if (prompt != null){
				this.getWith().sendMessage(prompt);
			}
		}
		
		this.nextNode = node;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		synchronized (this){
			CommandSender sender = event.getPlayer();
			String message = event.getMessage();
			
			if (this.getWith().equals(sender)){
				this.onInput(message);
				event.setCancelled(true);
				
				if (this.isEnded()){
					HandlerList.unregisterAll(this);
				}
			}else if (this.isSuppressingChat()){
				Iterator<Player> recipients = event.getRecipients().iterator();
				
				while (recipients.hasNext()){
					Player recipient = recipients.next();
					
					if (this.getWith().equals(recipient)){
						recipient.remove();
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		CommandSender sender = event.getPlayer();
		String message = event.getMessage();
		
		if (this.getWith().equals(sender)){
			this.onInput(message);
			event.setCancelled(true);
		}
		
		if (this.isEnded()){
			HandlerList.unregisterAll(this);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onServerCommand(ServerCommandEvent event){
		CommandSender sender = event.getSender();
		String message = event.getCommand();
		
		if (this.getWith().equals(sender)){
			this.onInput(message);
		}
		
		if (this.isEnded()){
			HandlerList.unregisterAll(this);
		}
	}
	
}
