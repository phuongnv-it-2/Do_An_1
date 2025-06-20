package form;

import Database.JDBCuntil;
import Event.PublicEvent;
import Service.AESUtil;
import model.ModelLogin;
import model.ModelUser;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import Client.Client;

public class LoginForm extends JPanel {

    private Swing.MyTextField txtUsername;
    private Swing.MyPasswordField txtPassword;
    private Swing.Button cmdOK;
    private Swing.Button cmdCancel;
    private final Home home;
    private Client client;
    private ModelUser currentUser;
    private Menu_Left menuLeft;

    public LoginForm(Home home, Menu_Left menuLeft) {
        this.home = home;
        this.menuLeft = menuLeft;
        initComponents();
    }

    public Client getClient() {
        return client;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        Swing.SquarePanel squarePanel1 = new Swing.SquarePanel();
        cmdOK = new Swing.Button();
        JLabel jLabel1 = new JLabel();
        txtUsername = new Swing.MyTextField();
        txtPassword = new Swing.MyPasswordField();
        cmdCancel = new Swing.Button();

        setOpaque(true);
        setBackground(Color.WHITE);
        setLayout(new MigLayout("fill"));

        squarePanel1.setLayout(new MigLayout("fill, insets 20", "[center]", "[center]"));
        squarePanel1.setPreferredSize(new Dimension(444, 312));

        cmdOK.setBackground(new Color(0, 255, 204));
        cmdOK.setText("OK");
        cmdOK.addActionListener(evt -> cmdOKActionPerformed());

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setText("Đăng nhập");

        txtUsername.setHint("Tên người dùng");

        txtPassword.setHint("Mật khẩu của bạn");

        cmdCancel.setBackground(new Color(255, 51, 51));
        cmdCancel.setText("Hủy");
        cmdCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmdCancel.addActionListener(evt -> cmdCancelActionPerformed(evt));

        squarePanel1.add(jLabel1, "span, center, wrap 20");
        squarePanel1.add(txtUsername, "w 200, wrap 20");
        squarePanel1.add(txtPassword, "w 200, wrap 20");
        squarePanel1.add(cmdOK, "split 2, w 70");
        squarePanel1.add(cmdCancel, "w 70");

        add(squarePanel1, "pos 0.5al 0.5al");
    }

    private void cmdOKActionPerformed() {
        if (client != null) {
            JOptionPane.showMessageDialog(this, "Bạn đã đăng nhập! Vui lòng đăng xuất trước.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên người dùng và mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new Thread(() -> {
            Connection conn = null;
            try {
                System.out.println("Bắt đầu xác thực người dùng: " + username);
                conn = JDBCuntil.getconection();
                if (conn == null) {
                    System.err.println("Không thể kết nối cơ sở dữ liệu!");
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE));
                    return;
                }
                ModelUser user = authenticateUser(conn, username, password);
                if (user != null) {
                    System.out.println("Xác thực thành công: " + user.getUserName());
                    currentUser = user;
                    SwingUtilities.invokeLater(() -> {
                        System.out.println("Kết nối tới server...");
                        connectToServer(user, username, password);
                        System.out.println("Gọi onLoginSuccess...");
                        home.onLoginSuccess(user);
                    });
                } else {
                    System.err.println("Xác thực thất bại: Tên hoặc mật khẩu không đúng!");
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Tên người dùng hoặc mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi SQL: " + e.getMessage());
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Lỗi khi xác thực: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE));
            } finally {
                JDBCuntil.closeconection(conn);
            }
        }).start();
    }

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {
        clearFields();
    }

    private ModelUser authenticateUser(Connection conn, String userName, String password) throws SQLException {
        String query = "SELECT * FROM account WHERE UserName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedEncryptedPassword = rs.getString("password");
                String encryptedInputPassword = AESUtil.encrypt(password);
                if (encryptedInputPassword != null && encryptedInputPassword.equals(storedEncryptedPassword)) {
                    ModelUser user = new ModelUser(
                            rs.getInt("userID"),
                            rs.getString("userName"),
                            rs.getString("gender"),
                            rs.getString("image"),
                            rs.getInt("status"),
                            rs.getString("email"),
                            storedEncryptedPassword,
                            rs.getString("verifyCode"),
                            rs.getBytes("avatarPath")
                    );
                    user.setStatus(1);
                    updateUserStatus(conn, user.getUserID(), 1);
                    return user;
                }
            }
        }
        return null;
    }

    private void updateUserStatus(Connection conn, int userID, int status) throws SQLException {
        String query = "UPDATE account SET status = ? WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, status);
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        }
    }

    private void connectToServer(ModelUser user, String username, String password) {
        try {
            client = Client.getInstance();
            System.out.println("🟢 Đang kết nối tới server...");
            client.connect("localhost", 12345, username);
            this.currentUser = user;
            new Thread(this::listenForMessages).start();
            System.out.println("✅ Kết nối server thành công");
        } catch (IOException e) {
            System.err.println("❌ Lỗi kết nối tới server: " + e.getMessage());
            SwingUtilities.invokeLater(()
                    -> JOptionPane.showMessageDialog(this, "Lỗi khi kết nối tới server: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
            );
        }
    }

    public void sendMessage(String content) {
        if (client == null || currentUser == null) {
            SwingUtilities.invokeLater(()
                    -> JOptionPane.showMessageDialog(this, "Không thể gửi tin nhắn: Chưa kết nối hoặc người dùng không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE)
            );
            System.err.println("Không thể gửi tin nhắn: client hoặc currentUser là null");
            return;
        }
        try {
            client.sendMessageToServer(content); // Gửi nội dung thô
            System.out.println("📤 Gửi tin nhắn: " + content);
        } catch (IOException e) {
            SwingUtilities.invokeLater(()
                    -> JOptionPane.showMessageDialog(this, "Lỗi khi gửi tin nhắn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE)
            );
            System.err.println("Lỗi gửi tin nhắn: " + e.getMessage());
        }
    }

    private void listenForMessages() {
        int maxRetries = 5;
        int retryDelay = 1000; // 3 giây
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                while (true) {
                    Object data = client.getInputStream().readObject();
                    System.out.println("📥 Nhận dữ liệu từ server: " + data);
                    if (data instanceof String) {
                        String message = (String) data;
                        String currentUsername = currentUser != null ? currentUser.getUserName().trim() : "";

                        if (message.startsWith("SYSTEM: ") || !message.startsWith(currentUsername + ": ")) {
                            SwingUtilities.invokeLater(()
                                    -> PublicEvent.getInstance().getEventChat().receiveMessage(message)
                            );
                        }
                    } else if (data instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<ModelUser> users = (List<ModelUser>) data;
                        System.out.println("Cập nhật danh sách người dùng: " + users.size());
                        SwingUtilities.invokeLater(() -> menuLeft.updateUserList(users));
                    } else {
                        System.err.println("Dữ liệu không hợp lệ: " + data.getClass().getName());
                    }
                }
            } catch (IOException e) {
                retryCount++;
                System.err.println("Lỗi khi nhận dữ liệu (IOException): " + e.getMessage() + " (Lần thử " + retryCount + "/" + maxRetries + ")");
                if (retryCount < maxRetries) {
//                SwingUtilities.invokeLater(() -> 
//                    JOptionPane.showMessageDialog(this, "Mất kết nối với server, đang thử lại... (Lần " + 8 + "/" + maxRetries + ")", "Cảnh báo", JOptionPane.WARNING_MESSAGE)
//                );
                    try {
                        Thread.sleep(retryDelay);
                        client.closeConnection();
                        client = Client.getInstance();
                        client.connect("localhost", 12345, currentUser.getUserName());
                        System.out.println("🔗 Kết nối lại thành công");
                    } catch (IOException ex) {
                        System.err.println("Lỗi khi thử kết nối lại: " + ex.getMessage());
                    } catch (InterruptedException ex) {
                        System.err.println("Lỗi khi đợi thử lại: " + ex.getMessage());
                    }
                } else {
                    System.err.println("Đã hết số lần thử kết nối lại");
                    SwingUtilities.invokeLater(()
                            -> JOptionPane.showMessageDialog(this, "Mất kết nối với server sau " + maxRetries + " lần thử!", "Lỗi", JOptionPane.ERROR_MESSAGE)
                    );
                    break;
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Lỗi khi nhận dữ liệu (ClassNotFoundException): " + e.getMessage());
                SwingUtilities.invokeLater(()
                        -> JOptionPane.showMessageDialog(this, "Lỗi dữ liệu từ server!", "Lỗi", JOptionPane.ERROR_MESSAGE)
                );
                break;
            }
        }

        try {
            if (client != null) {
                client.closeConnection();
                client = null;
                System.out.println("🔌 Đã đóng kết nối client trong listenForMessages");
            }
        } catch (IOException ex) {
            System.err.println("Lỗi khi đóng socket: " + ex.getMessage());
        }
    }

    public void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void closeConnection() {
        if (client != null) {
            try {
                client.closeConnection();
                client = null;
                System.out.println("🔌 Đã đóng kết nối client");
            } catch (IOException e) {
                System.err.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
