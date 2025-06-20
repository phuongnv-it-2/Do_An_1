package Event;

import model.Model_Receive_Message;
import model.Model_Send_Message;




public interface EventChat {

    public void sendMessage(String  text);

    public void receiveMessage(String data);
}
