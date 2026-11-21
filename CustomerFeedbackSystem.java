enum MessageType {
    COMPENSATION_CLAIM,
    CONTACT_REQUEST,
    DEVELOPMENT_SUGGESTION,
    GENERAL_FEEDBACK
}

class Message {
    private MessageType messageType;
    private String messageContent;
    private String senderEmail;

    public Message(MessageType messageType, String messageContent, String senderEmail) {
        this.messageType = messageType;
        this.messageContent = messageContent;
        this.senderEmail = senderEmail;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getSenderEmail() {
        return senderEmail;
    }
}

abstract class FeedbackHandler {
    protected FeedbackHandler nextHandler;
    
    public void setNextHandler(FeedbackHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleMessage(Message message);
}

class CompensationHandler extends FeedbackHandler {
    @Override
    public void handleMessage(Message message) {
        if (message.getMessageType() == MessageType.COMPENSATION_CLAIM) {
            System.out.println("Handling compensation claim...");
            System.out.println("From: " + message.getSenderEmail());
            System.out.println("Claim: " + message.getMessageContent());
            System.out.println("Result: Compensation claim has been "+ "reviewed and approved.");

        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }
    }
}

class ContactHandler extends FeedbackHandler {
    @Override
    public void handleMessage(Message message) {
        if (message.getMessageType() == MessageType.CONTACT_REQUEST) {
            System.out.println("Handling contact request...");
            System.out.println("From: " + message.getSenderEmail());
            System.out.println("Request: " + message.getMessageContent());
            System.out.println("Result: Request forwarded to the "+ "customer service department.");

        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }
    }
}

class DevelopmentHandler extends FeedbackHandler {

    @Override
    public void handleMessage(Message message) {
        if (message.getMessageType() == MessageType.DEVELOPMENT_SUGGESTION) {
            System.out.println("Handling development suggestion...");
            System.out.println("From: " + message.getSenderEmail());
            System.out.println("Suggestion: " + message.getMessageContent());
            System.out.println("Result: Suggestion logged and " + "added to the development priority list.");
        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }
    }
}

class GeneralFeedbackHandler extends FeedbackHandler {
    @Override
    public void handleMessage(Message message) {
        if (message.getMessageType() == MessageType.GENERAL_FEEDBACK) {
            System.out.println("Handling general feedback...");
            System.out.println("From: " + message.getSenderEmail());
            System.out.println("Feedback: " + message.getMessageContent());
            System.out.println("Result: Feedback analyzed. " + "Thank you for your feedback!");

        } else if (nextHandler != null) {
            nextHandler.handleMessage(message);
        }
    }
}

public class CustomerFeedbackSystem {
    public static void main(String[] args) {
        FeedbackHandler compensationHandler = new CompensationHandler();

        FeedbackHandler contactHandler = new ContactHandler();
        FeedbackHandler developmentHandler = new DevelopmentHandler();
        FeedbackHandler generalFeedbackHandler = new GeneralFeedbackHandler();

        compensationHandler.setNextHandler(contactHandler);
        contactHandler.setNextHandler(developmentHandler);
        developmentHandler.setNextHandler(generalFeedbackHandler);

        FeedbackHandler firstHandler = compensationHandler;

        Message message1 = new Message(
            MessageType.COMPENSATION_CLAIM,
            "product is bad",
            "customer1@email.com"
        );

        Message message2 = new Message(
            MessageType.CONTACT_REQUEST,
            "can i speak to manager",
            "customer2@email.com"
        );

        Message message3 = new Message(
            MessageType.DEVELOPMENT_SUGGESTION,
            "please add dark mode",
             "customer3@email.com"
        );

        Message message4 = new Message(
             MessageType.GENERAL_FEEDBACK,
            "good customer service!!!!1!!1!!!!!!1!1",
             "customer4@email.com"
        );

        Message[] messages = {message1, message2, message3, message4};

        for (Message message : messages) {
            System.out.println("\n-----------------------------");
            firstHandler.handleMessage(message);
            System.out.println("-----------------------------");
        }
    }
}
