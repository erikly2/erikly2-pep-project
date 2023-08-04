package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    // No arg contructor
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    // Constructor with args
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO=messageDAO;
    }

    /** 
     * Gets all the messages from the Message table.
     * @return all Messages.
     */
    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    /** 
     * Gets messages from the Message table given a message_id.
     * @param message_id the id of the message to get.
     * @return Message correlating to message_id.
     */
    public Message getMessageById(int message_id) {
        return this.messageDAO.getMessageById(message_id);
    }

    /** 
     * Gets all the messages from the Message table given a account_id.
     * @param user_id the id of the account pull all Messages from.
     * @return all Messages from a user.
     */
    public List<Message> getMessagesFromUser(int user_id) {
        return this.messageDAO.getMessagesFromUser(user_id);
    }
 
    /** 
     * Deletes the messages from the Message table given a message_id.
     * @param message_id The id of the message to delete.
     */
    public void deleteMessageById(int message_id) {
        this.messageDAO.deleteMessageById(message_id);
    }

    /** 
     * Updates the message at the message_id with message_text.
     * @param message_text The updated text.
     * @param message_id The id of the message to update.
     */
    public void updateMessageById(String message_text, int message_id) {
        this.messageDAO.updateMessageById(message_text, message_id);
    }

    /** 
     * Adds a message to the Message table. (no message_id)
     * @param message The message to add.
     * @return The message added in the table.
     */
    public Message addMessage(Message message) {
        return this.messageDAO.addMessage(message);
    }
    
    /** 
     * Finds if a user exists given an id.
     * @param id the account_id of the user in question.
     * @return If the user exists in the database.
     */
    public Boolean personExists(int posted_by) {
        return this.messageDAO.personExists(posted_by);
    }
}
