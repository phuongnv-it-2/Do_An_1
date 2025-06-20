package Client;

import Event.PublicEvent;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.SwingUtilities;
import model.ModelLogin;

public class Client {
    private static Client instance;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
            System.out.println("🔷 Tạo instance mới của Client");
        }
        return instance;
    }

    private Client() {}

    public void connect(String host, int port, String username) throws IOException {
        if (username == null || username.trim().isEmpty()) {
            System.err.println("❌ Username không hợp lệ: " + username);
            throw new IllegalArgumentException("Username không được rỗng hoặc null");
        }
        try {
            System.out.println("🔗 Đang kết nối tới " + host + ":" + port + " với username: " + username);
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            sendLogin(new ModelLogin(username));
//            listenFromServer();
        } catch (IOException e) {
            System.err.println("❌ Lỗi kết nối tới server " + host + ":" + port + ": " + e.getMessage());
            throw e;
        }
    }

    private void sendLogin(ModelLogin login) throws IOException {
        System.out.println("📤 Gửi ModelLogin: " + login.getUser());
        out.writeObject(login);
        out.flush();
    }

    public void sendMessageToServer(String message) throws IOException {
        if (out == null) {
            throw new IOException("Chưa kết nối tới server");
        }
        System.out.println("📤 Gửi tin nhắn: " + message);
        out.writeObject(message);
        out.flush();
    }

//    private void listenFromServer() {
//        new Thread(() -> {
//            try {
//                while (true) {
//                    Object data = in.readObject();
//                    if (data instanceof String) {
//                        String message = (String) data;
//                        System.out.println("📥 Nhận tin nhắn: " + message);
//                        SwingUtilities.invokeLater(() -> {
//                            PublicEvent.getInstance().getEventChat().receiveMessage(message);
//                        });
//                    }
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                SwingUtilities.invokeLater(() -> {
////                    PublicEvent.getInstance().getEventChat().receiveMessage("Lỗi: Mất kết nối với server!");
//                });
//                System.err.println("Lỗi client: " + e.getMessage());
//            }
//        }).start();
//    }

    // Getter cho ObjectInputStream
    public ObjectInputStream getInputStream() {
        return in;
    }

    // Đóng kết nối
    public void closeConnection() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (socket != null) socket.close();
        System.out.println("🔌 Đã đóng kết nối client");
    }
}