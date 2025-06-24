package server;

import java.io.*;
import java.net.Socket;
import model.ModelLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.JDBCuntil;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MessageBroadcaster broadcaster;
    private String username;
    private int userId; // Thêm để lưu userID
    

    public interface MessageBroadcaster {
        void broadcast(String message, ClientHandler sender);
    }

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void setChatServer(MessageBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void sendMessage(String message) {
        if (!socket.isClosed()) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.err.println("Lỗi gửi tin nhắn tới " + username + ": " + e.getMessage());
            }
        }
    }

    public String getUsername() {
        return username != null ? username : "Unknown";
    }

    public boolean isSocketClosed() {
        return socket.isClosed();
    }

    @Override
    public void run() {
        String clientInfo = "Unknown@" + socket.getInetAddress().getHostAddress();
        Connection conn = null;
        try {
            // Nhận ModelLogin từ client
            Object obj = in.readObject();
            if (obj instanceof ModelLogin) {
                ModelLogin login = (ModelLogin) obj;
                this.username = login.getUser().trim();
                this.userId = getUserIdFromDatabase(username); // Lấy userID từ cơ sở dữ liệu
                clientInfo = username;
                if (broadcaster != null) {
                }
            } else {
                System.err.println("❌ Dữ liệu đăng nhập không hợp lệ từ: " + clientInfo);
                return;
            }

            // Tiếp tục nhận tin nhắn
            while (true) {
                Object data = in.readObject();
                if (data instanceof String) {
                    String message = (String) data;
                    // Lưu tin nhắn vào cơ sở dữ liệu
                    saveMessageToDatabase(userId, message);
                    if (broadcaster != null) {
                        broadcaster.broadcast(username + ": " + message, this); // Thêm username ở server
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("🔌 Mất kết nối từ client " + clientInfo + ": " + e.getMessage());
        } finally {
            try {
                socket.close();
                ChatServer.removeClient(this);
            } catch (IOException e) {
                System.err.println("Lỗi đóng socket: " + e.getMessage());
            }
            JDBCuntil.closeconection(conn);
        }
    }

    // Lấy userID từ cơ sở dữ liệu dựa trên username
    private int getUserIdFromDatabase(String username) {
        Connection conn = JDBCuntil.getconection();
        if (conn == null) {
            System.err.println("Không thể kết nối cơ sở dữ liệu!");
            return -1;
        }
        String query = "SELECT UserID FROM account WHERE UserName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy userID: " + e.getMessage());
        } finally {
            JDBCuntil.closeconection(conn);
        }
        return -1;
    }

    // Lưu tin nhắn vào cơ sở dữ liệu
    private void saveMessageToDatabase(int senderId, String message) {
        if (senderId == -1) {
            System.err.println("Không thể lưu tin nhắn: userId không hợp lệ!");
            return;
        }
        Connection conn = JDBCuntil.getconection();
        if (conn == null) {
            System.err.println("Không thể kết nối cơ sở dữ liệu!");
            return;
        }
        String query = "INSERT INTO messages (sender_id, message_text) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setString(2, message);
            stmt.executeUpdate();
            System.out.println("💾 Đã lưu tin nhắn từ userID " + senderId + ": " + message);
        } catch (SQLException e) {
            System.err.println("Lỗi lưu tin nhắn: " + e.getMessage());
        } finally {
            JDBCuntil.closeconection(conn);
        }
    }
    public List<String> getRecentMessages(int limit) {
    List<String> messages = new ArrayList<>();
    Connection conn = JDBCuntil.getconection();
    if (conn == null) {
        System.err.println("Không thể kết nối cơ sở dữ liệu!");
        return messages;
    }

    String query = "SELECT a.UserName, m.message_text, m.sent_at " +
                   "FROM messages m " +
                   "JOIN account a ON m.sender_id = a.UserID " +
                   "ORDER BY m.sent_at DESC " +
                   "LIMIT ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, limit);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String username = rs.getString("UserName");
            String messageText = rs.getString("message_text");
            String sentTime = rs.getTimestamp("sent_at").toString(); // Lấy thời gian gửi
            messages.add(username + " (" + sentTime + "): " + messageText); // Thêm thời gian vào tin nhắn
        }
    } catch (SQLException e) {
        System.err.println("Lỗi lấy tin nhắn gần nhất: " + e.getMessage());
    } finally {
        JDBCuntil.closeconection(conn);
    }
    return messages;
}
}