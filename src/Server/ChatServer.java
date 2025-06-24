package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer {

    private static final int PORT = 12345;
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                synchronized (clients) {
                    clients.add(clientHandler);
                }
                clientHandler.setChatServer(ChatServer::broadcastMessage);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Lỗi server: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            String senderUsername = sender != null ? sender.getUsername().trim() : "Unknown";
            Iterator<ClientHandler> iterator = clients.iterator();
            while (iterator.hasNext()) {
                ClientHandler client = iterator.next();
                if (client == sender) {
                    continue;
                }

                if (!client.isSocketClosed()) {
                    try {
                        client.sendMessage(message);
                    } catch (Exception e) {
                        System.err.println("❌ Lỗi gửi tin nhắn tới " + client.getUsername().trim() + ": " + e.getMessage());
                        iterator.remove();
                        System.out.println("🗑️ Đã xóa client do lỗi: " + client.getUsername().trim());
                    }
                } else {
                    System.out.println("🗑️ Client đã ngắt kết nối, xóa: " + client.getUsername().trim());
                    iterator.remove();
                }
            }
        }
    }

    public static void removeClient(ClientHandler client) {
        synchronized (clients) {
            if (clients.remove(client)) {
                if (!client.isSocketClosed()) {
                    broadcastMessage(client.getUsername() + " đã rời chat!", client);
                }
            } else {
                System.out.println("⚠️ Không tìm thấy client để xóa: " + client.getUsername());
            }
        }
    }
}
