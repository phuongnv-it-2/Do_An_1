package Service;

import Database.JDBCuntil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Random;
import model.ModelLogin;
import model.ModelUser;

public class ServiceUser {
   public static List<String> myList = new ArrayList<>();


    private final Connection con;

    public ServiceUser() {
        con = JDBCuntil.getconection();
    }
public ModelUser getUserByUsername(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return null; // Tránh truy vấn với dữ liệu không hợp lệ
        }

        PreparedStatement p = null;
        ResultSet rs = null;
        try {
            p = con.prepareStatement("SELECT * FROM account WHERE UserName = ? LIMIT 1");
            p.setString(1, username.trim());
            rs = p.executeQuery();

            if (rs.next()) {
                ModelUser user = new ModelUser();
                user.setUserID(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setVerifyCode(rs.getString("VerifyCode"));
//                user.setStatus(rs.getString("Status")); 
                return user;
            }
            return null; // Không tìm thấy người dùng
        } finally {
            if (rs != null) rs.close();
            if (p != null) p.close();
        }
    }

 public ModelUser login(ModelLogin data) throws SQLException {
    if (data == null || data.getEmail() == null || data.getPassword() == null) {
        return null; 
    }
    PreparedStatement p = null;
    ResultSet rs = null;
    try {
        // Truy vấn để lấy thông tin người dùng và mật khẩu đã mã hóa từ cơ sở dữ liệu
        p = con.prepareStatement(
            "SELECT UserID, Email, Password FROM account WHERE Email = ?"
        );
        p.setString(1, data.getEmail().trim());
        rs = p.executeQuery();
        
        // Kiểm tra nếu có kết quả trả về
        if (rs.next()) {
            // Giải mã mật khẩu ngay sau khi lấy ra từ cơ sở dữ liệu
            String encryptedPassword = rs.getString("Password");
            String decryptedPassword = AESUtil.decrypt(encryptedPassword);
     
            if (decryptedPassword.equals(data.getPassword().trim())) {
                ModelUser user = new ModelUser();
                user.setUserID(rs.getInt("UserID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(decryptedPassword);  
                
                return user;
            }
        }
        
     
        return null;
    } finally {
        if (rs != null) rs.close();
        if (p != null) p.close();
    }
}


   public void insertUser(ModelUser user) throws SQLException {
    // Kiểm tra đầu vào
    if (user == null) {
        throw new IllegalArgumentException("Thông tin người dùng không được để trống.");
    }
    if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
        throw new IllegalArgumentException("Tên người dùng không được để trống.");
    }
    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
        throw new IllegalArgumentException("Email không được để trống.");
    }
    if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        throw new IllegalArgumentException("Email không hợp lệ.");
    }
    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
        throw new IllegalArgumentException("Mật khẩu không được để trống.");
    }

    PreparedStatement p = con.prepareStatement("insert into account (UserName, Email, Password, VerifyCode) values (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
    String code = generateVerifyCode();
    try {
        p.setString(1, user.getUserName().trim());
        p.setString(2, user.getEmail().trim());
        p.setString(3, user.getPassword());
        p.setString(4, code);
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        if (r.next()) {
            int userID = r.getInt(1);
            user.setUserID(userID);
            user.setVerifyCode(code); 
        }
        r.close();
    } finally {
        p.close();
    }
}
   public void insertVerifyCodeByUserName(ModelUser user) throws SQLException {
    if (user == null || user.getUserName() == null || user.getUserName().trim().isEmpty()) {
        throw new IllegalArgumentException("UserName không được để trống.");
    }
    String code = generateVerifyCode();
    user.setVerifyCode(code);
    String sql = "UPDATE `account` SET VerifyCode = ? WHERE UserName = ?";
    PreparedStatement p = con.prepareStatement(sql);
    try {
        p.setString(1, code);
        p.setString(2, user.getUserName());
        p.executeUpdate();
    } finally {
        p.close();
    }
}

 public String generateVerifyCode() throws SQLException {
    DecimalFormat df = new DecimalFormat("000000");
    Random ran = new Random();
    String code;
    int maxAttempts = 10; // Giới hạn số lần thử
    int attempt = 0;

    do {
        code = df.format(ran.nextInt(1000000));
        attempt++;
        if (attempt >= maxAttempts) {
            throw new SQLException("Không thể tạo mã xác minh duy nhất sau " + maxAttempts + " lần thử.");
        }
    } while (checkDuplicateCode(code));
    myList.add(code);

    return code;
}
    private boolean checkDuplicateCode(String code) throws SQLException {
        boolean duplicate = false;
        PreparedStatement p = con.prepareStatement("select UserID from account where VerifyCode=? limit 1");
        p.setString(1, code);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }
    public void updateVerifyCodeByUserName(String userName, String newCode) throws SQLException {
    String sql = "UPDATE `account` SET VerifyCode = ? WHERE UserName = ?";
    PreparedStatement p = con.prepareStatement(sql);
    p.setString(1, newCode);
    p.setString(2, userName);
    p.executeUpdate();
    p.close();
}


   public boolean checkDuplicateUser(String user) throws SQLException {
    if (user == null || user.trim().isEmpty()) {
        return true; 
    }
    try (PreparedStatement p = con.prepareStatement("SELECT UserID FROM account WHERE UserName=? LIMIT 1")) {
        p.setString(1, user.trim());
        try (ResultSet r = p.executeQuery()) {
            return r.next();
        }
    }
}

public boolean checkDuplicateEmail(String user) throws SQLException {
    if (user == null || user.trim().isEmpty()) {
        return true; 
    }
    try (PreparedStatement p = con.prepareStatement("SELECT UserID FROM account WHERE Email=? LIMIT 1")) {
        p.setString(1, user.trim());
        try (ResultSet r = p.executeQuery()) {
            return r.next();
        }
    }
}
    public void doneVerify(int userID) throws SQLException {
        PreparedStatement p = con.prepareStatement("update `account` set VerifyCode='', `Status`='Verified' where UserID=? limit 1");
        p.setInt(1, userID);
        p.execute();
        p.close();
    }

    public boolean verifyCodeWithUser(String userName, String code) throws SQLException {
        boolean verify = false;
        PreparedStatement p = con.prepareStatement("select UserID from `account` where userName=? and VerifyCode=? limit 1");
        p.setString(1, userName);
        p.setString(2, code);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            verify = true;
        }
        r.close();
        p.close();
        return verify;
    }
    public void updatePasswordByUserName(String userName, String newPassword) throws SQLException {
    String sql = "UPDATE `account` SET Password = ? WHERE UserName = ?";
    PreparedStatement p = con.prepareStatement(sql);
    try {
        p.setString(1, newPassword);
        p.setString(2, userName);
        p.executeUpdate();
    } finally {
        p.close();
    }
}

    public boolean checkEmailMatchUser(String username, String email) throws SQLException {
    String sql = "SELECT COUNT(*) FROM account WHERE UserName = ? AND Email = ?";
    try (
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);
        ps.setString(2, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;  
        }
    }
    return false;
}

   
}   