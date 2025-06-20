package Event;


import model.ModelUser;

public interface EventMain {

    public void showLoading(boolean show);

    public void initChat();

    public void selectUser(ModelUser user);

    public void updateUser(ModelUser user);
}
