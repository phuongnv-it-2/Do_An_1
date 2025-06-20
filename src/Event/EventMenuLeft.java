package Event;


import java.util.List;
import model.ModelUser;

public interface EventMenuLeft {

    public void newUser(List<ModelUser> users);

    public void userConnect(int userID);

    public void userDisconnect(int userID);
}
