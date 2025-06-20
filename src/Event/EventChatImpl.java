package Event;

import Client.Client;
import Component.Chat_Body;
import Component.Chat_Left;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model_Receive_Message;

public class EventChatImpl implements EventChat {

    private Chat_Body Chat_Body;

    @Override
    public void sendMessage(String text) {
        try {
            Client.getInstance().sendMessageToServer(text);
        } catch (IOException ex) {
            Logger.getLogger(EventChatImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void receiveMessage(String data) {
        Chat_Left left = new Chat_Left();
        left.setText(data);
        left.setTime();
    }
}
