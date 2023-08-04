package Controller;

import org.mockito.internal.matchers.Null;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAddAccountHandler);
        app.post("/login", this::postLoginAccountHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessageHandler);

        return app;
    }
    /**
     * Handler to process a new User registration (no account_id)
     * The registration will be successful if 
     *  - username is not blank
     *  - password is at least 4 characters long 
     *  - an Account with that username does not already exist
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postAddAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mapper.readValue(context.body(), Account.class);
        // check username not blank
        // check password.length >= 4
        // check username != exist
        // if all met -> 200
        // else -> 400
        Boolean exist = accountService.accountExist(newAccount.getUsername()); 
        if (newAccount.username.isBlank() || newAccount.getPassword().length() < 4 || !exist) {
            context.status(400);
        } else {
            Account addedAccount = accountService.insertAccount(newAccount);
            context.json(mapper.writeValueAsString(addedAccount));
        }
    }
    /**
     * Handler to process User Logins
     * Login is successful if
     *  - username && password match a real account in database
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postLoginAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account login = mapper.readValue(context.body(), Account.class);
        Account person = accountService.matchLogin(login);
        if (person != null) {
            context.json(mapper.writeValueAsString(person));
        } else {
            context.status(401);
        }
    }

    /**
     * Handler to put a message onto the database without a given message_id. 
     * The creation of the message will be successful if and only if the 
     * message_text is 
     *  - not blank
     *  - under 255 characters
     *  - posted_by refers to a real, existing user
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(context.body(), Message.class);
        Boolean exist = messageService.personExists(newMessage.getPosted_by()); 
        if (newMessage.getMessage_text().isBlank() || newMessage.getMessage_text().length() >= 255 || !exist) {
            context.status(400);
        } else {
            Message addedMessage = messageService.addMessage(newMessage); 
            context.json(mapper.writeValueAsString(addedMessage));
        }
    }

    /**
     * Handler to get all the messages.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * Handler to get a message given a message id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageIDHandler(Context context) {
        String messageId = context.pathParam("message_id");
        if (!messageService.personExists(Integer.valueOf(messageId))) {
            context.json("");
            return;
        }
        Message message = messageService.getMessageById(Integer.valueOf(messageId));
        context.json(message);
    }

    /**
     * Handler to delete a message at a message id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context context) {
        String messageId = context.pathParam("message_id");
        if (!messageService.personExists(Integer.valueOf(messageId))) {
            context.json("");
            return;
        }
        Message message = messageService.getMessageById(Integer.valueOf(messageId));
        messageService.deleteMessageById(Integer.valueOf(messageId));
        context.json(message);
    }

    /**
     * Handler to update a message at a given message id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void patchMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(context.body(), Message.class);
        
        String messageId = context.pathParam("message_id");
        if (!messageService.personExists(Integer.valueOf(messageId)) || newMessage.message_text.isBlank() || newMessage.message_text.length() >= 255) {
            context.status(400);
        } else {
            messageService.updateMessageById(newMessage.message_text, Integer.valueOf(messageId));
            Message message = messageService.getMessageById(Integer.valueOf(messageId));
            context.json(message);
        }
    }

    /**
     * Handler to retrieve all messages from an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessageHandler(Context context) {
        String accountId = context.pathParam("account_id");

        List<Message> messages = messageService.getMessagesFromUser(Integer.valueOf(accountId));
        context.json(messages);
    }
}