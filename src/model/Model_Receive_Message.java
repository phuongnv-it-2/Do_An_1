package model;

import app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class Model_Receive_Message implements Serializable {
    private MessageType messageType;
    private int fromUserID;
    private String userName; // Thêm trường userName
    private String text;
    private Model_Receive_Image dataImage;
    private String gender;
    private String image;
    private String avatarPath;

    // Hàm tạo cho sendMessage
    public Model_Receive_Message(int messageType, int fromUserID, String userName, String text, Model_Receive_Image dataImage) {
        this.messageType = MessageType.toMessageType(messageType);
        this.fromUserID = fromUserID;
        this.userName = userName != null ? userName : "";
        this.text = text != null ? text : "";
        this.dataImage = dataImage;
    }

    // Hàm tạo JSON
    public Model_Receive_Message(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
            fromUserID = obj.getInt("fromUserID");
            userName = obj.optString("userName", "");
            text = obj.optString("text", null);
            if (!obj.isNull("dataImage")) {
                dataImage = new Model_Receive_Image(obj.get("dataImage"));
            }
            gender = obj.optString("gender", null);
            image = obj.optString("image", null);
            avatarPath = obj.optString("avatarPath", null);
        } catch (JSONException e) {
            System.err.println("Lỗi phân tích JSON trong Model_Receive_Message: " + e.getMessage());
        }
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("userName", userName);
            json.put("text", text);
            if (dataImage != null) {
                json.put("dataImage", dataImage.toJsonObject());
            }
            if (gender != null) {
                json.put("gender", gender);
            }
            if (image != null) {
                json.put("image", image);
            }
            if (avatarPath != null) {
                json.put("avatarPath", avatarPath);
            }
            return json;
        } catch (JSONException e) {
            System.err.println("Lỗi chuyển đổi sang JSON trong Model_Receive_Message: " + e.getMessage());
            return null;
        }
    }

    // Getters và setters
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Model_Receive_Image getDataImage() {
        return dataImage;
    }

    public void setDataImage(Model_Receive_Image dataImage) {
        this.dataImage = dataImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    // Thêm phương thức để hỗ trợ ChatServer
    public void setSenderID(int senderID) {
        this.fromUserID = senderID; // Ánh xạ tới setFromUserID
    }

    public void setSenderName(String senderName) {
        this.userName = senderName; // Ánh xạ tới setUserName
    }
}