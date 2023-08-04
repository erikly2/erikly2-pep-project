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
 
    public Message addMessage(Message message) {
        return this.messageDAO.addMessage(message);
    }

    public Boolean personExists(Message message) {
        return this.messageDAO.personExists(message.posted_by);
    }
}
