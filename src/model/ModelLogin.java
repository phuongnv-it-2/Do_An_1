package model;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class ModelLogin implements Serializable {
    private static final long serialVersionUID = 1L; // Khuyến nghị cho lớp Serializable

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public ModelLogin(String user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public ModelLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
       public ModelLogin(String user) {
        this.user = user;
    }

    public ModelLogin() {
    }

    private String user;
    private String email;
    private String password;

    public JSONObject toJsonObject() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("user", user);
            obj.put("password", password);
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }
}