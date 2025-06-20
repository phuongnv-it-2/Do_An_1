package server;

import java.io.*;
import java.net.Socket;
import model.ModelLogin;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MessageBroadcaster broadcaster;
    private String username;

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
                System.out.println("📤 Gửi tin nhắn tới " + username + ": " + message);
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
    try {
        // Nhận ModelLogin từ client
        Object obj = in.readObject();
        if (obj instanceof ModelLogin) {
            ModelLogin login = (ModelLogin) obj;
            this.username = login.getUser().trim();
            clientInfo = username;
            System.out.println("✅ Nhận ModelLogin: " + username);
            if (broadcaster != null) {
                broadcaster.broadcast("SYSTEM: " + username + " đã tham gia chat!", this);
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
                System.out.println("📥 Nhận tin nhắn từ " + username + ": " + message);
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
    }
}
}