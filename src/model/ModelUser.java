package model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Objects;

public class ModelUser  implements Serializable{
    private static final long serialVersionUID = 1L;

    private int userID;
    private String userName;
    private String gender;
    private String image;
    private int status;
    private String email;
    private String password;
    private String verifyCode;
    private byte[] avatar;

    // Constructor đầy đủ
    public ModelUser(int userID, String userName, String gender, String image, int status,
            String email, String password, String verifyCode, byte[] avatar) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
        this.avatar = avatar;
    }

    public ModelUser(int userID, String userName, String gender, String image, int status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
    }

    public ModelUser() {
    }

    public ModelUser(int userID, String userName, String email) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
    }

    public ModelUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ModelUser(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public ModelUser(int userID, String userName, String email, String password, String verifyCode) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
    }

    public ModelUser(int userID, String userName, String email, String password, String verifyCode, byte[] avatar) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.verifyCode = verifyCode;
        this.avatar = avatar;
    }

    // Constructor từ JSONObject
    public ModelUser(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            userID = obj.getInt("userID");
            userName = obj.getString("userName");
            gender = obj.getString("gender");
            image = obj.getString("image");
            status = obj.getInt("status");
            email = obj.optString("email", null);
            password = obj.optString("password", null);
            verifyCode = obj.optString("verifyCode", null);
            // avatar không truyền qua JSON nên không parse ở đây
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }
    }

    // Getters & Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Phương thức kiểm tra trạng thái hoạt động
    public boolean isActive() {
        return status != 0;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    // Triển khai equals và hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelUser modelUser = (ModelUser) o;
        return userID == modelUser.userID || Objects.equals(userName, modelUser.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userName);
    }
}
