package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO=messageDAO;
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return this.messageDAO.getMessageById(message_id);
    }

    public List<Message> getMessagesFromUser(int user_id) {
        return this.messageDAO.getMessagesFromUser(user_id);
    }
 
    public void deleteMessageById(int message_id) {
        this.messageDAO.deleteMessageById(message_id);
    }

    public void updateMessageById(String message_text, int message_id) {
        this.messageDAO.updateMessageById(message_text, message_id);
    }

    public Message addMessage(Message message) {
        return this.messageDAO.addMessage(message);
    }

    public Boolean personExists(int posted_by) {
        return this.messageDAO.personExists(posted_by);
    }
}
