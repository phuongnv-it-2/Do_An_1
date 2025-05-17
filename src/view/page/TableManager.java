package view.page;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;
import Service.ImageRenderer;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TableManager extends JPanel implements OrderPanel.OrderListener {

    private JPanel tablePanel;
    private JPanel infoPanel;
    private JButton inputButton;
    private JButton orderButton;
    private JButton paymentButton;
    private int numberOfTables = 20;
   
    private Map<Integer, TableInfo> tableStates = new HashMap<>();
    private Timer timer;
    private JTextArea infoArea;
    private int selectedTable = 0;
    private Map<Integer, JLabel> tableTimeLabels = new HashMap<>();

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static class OrderItem {

        String tenMon;
        int soLuong;
        int donGia;
        int thanhTien;

        OrderItem(String tenMon, int soLuong, int donGia) {
            this.tenMon = tenMon;
            this.soLuong = soLuong;
            this.donGia = donGia;
            this.thanhTien = soLuong * donGia;
        }

        @Override
        public String toString() {
            return String.format("Món: %s - Số lượng: %d - Đơn giá: %d", tenMon, soLuong, donGia);
        }
    }

    private static class TableInfo {

        String status;
        StringBuilder orderDetails;
        long startTime;
        int numberOfGuests;

        TableInfo(String status, String orderDetails, long startTime, int numberOfGuests) {
            this.status = status;
            this.orderDetails = new StringBuilder(orderDetails);
            this.startTime = startTime;
            this.numberOfGuests = numberOfGuests;
        }
    }

    public TableManager() {
        initComponents();
        loadTables();
        loadInfoPanel();
        startTimer();
    }

    private void initComponents() {
        setLayout(new MigLayout("fill", "[60%][40%]", "[grow]"));

        infoPanel = new JPanel(new MigLayout("fill, wrap 1", "[grow]", "[][][grow][]"));
        infoPanel.setBackground(new Color(242, 242, 242));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(250, 250, 250));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputButton = new JButton("Nhập Số Bàn");
        inputButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        inputButton.setPreferredSize(new Dimension(150, 40));
        inputButton.setBackground(new Color(22, 216, 160));
        inputButton.setForeground(Color.BLACK);
        inputButton.setFocusPainted(false);

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(TableManager.this, "Nhập số lượng bàn (20-40):", "Số Bàn", JOptionPane.QUESTION_MESSAGE);
                try {
                    int newNumberOfTables = Integer.parseInt(input);
                    if (newNumberOfTables < 20 || newNumberOfTables > 40) {
                        JOptionPane.showMessageDialog(TableManager.this, "Số bàn phải từ 20 đến 40!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        numberOfTables = newNumberOfTables;
                        tableTimeLabels.clear();
                        loadTables();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TableManager.this, "Vui lòng nhập một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        topPanel.add(inputButton);
        leftPanel.add(topPanel, BorderLayout.NORTH);

        tablePanel = new JPanel();
        tablePanel.setLayout(new MigLayout(
                "wrap 5, gap 15 15",
                "[120!][120!][120!][120!][120!]",
                "[]"
        ));
        tablePanel.setBackground(Color.decode("#CBAACB"));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        add(leftPanel, "cell 0 0, grow");   
        add(infoPanel, "cell 1 0, grow");
    }

    private void loadTables() {
        tablePanel.removeAll();
        tableTimeLabels.clear();

        for (int i = 1; i <= numberOfTables; i++) {
            JPanel tableItemPanel = new JPanel(new BorderLayout());
            tableItemPanel.setBackground(Color.decode("#CBAACB"));
            tableItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            JLabel tableNumberLabel = new JLabel("Bàn " + i, JLabel.CENTER);
            tableNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            tableNumberLabel.setForeground(Color.BLACK);
            tableItemPanel.add(tableNumberLabel, BorderLayout.NORTH);

            JLabel tableImageLabel = new JLabel();
            tableImageLabel.setHorizontalAlignment(JLabel.CENTER);
            tableImageLabel.setOpaque(true);
            tableImageLabel.setBackground(tableStates.containsKey(i) && "On".equals(tableStates.get(i).status) ? Color.GREEN : Color.decode("#FFC0CB"));
            ImageRenderer renderer = new ImageRenderer();
            Icon icon = renderer.getTableIcon();

            if (icon != null) {
                tableImageLabel.setIcon(icon);
            } else {
                tableImageLabel.setText("No Icon");
            }

            tableItemPanel.add(tableImageLabel, BorderLayout.CENTER);

            JLabel timeLabel = new JLabel("", JLabel.CENTER);
            timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            timeLabel.setForeground(Color.BLACK);
            tableItemPanel.add(timeLabel, BorderLayout.SOUTH);
            tableTimeLabels.put(i, timeLabel);

            final int tableNumber = i;
            tableItemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    selectedTable = tableNumber;
                    updateInfoPanel(tableNumber);
                }
            });

            tablePanel.add(tableItemPanel, "w 120!, h 200!");
        }

        updateTableTimeLabels();
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private void loadInfoPanel() {
        infoPanel.removeAll();

        JLabel titleLabel = new JLabel("Thông Tin Bàn", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        infoPanel.add(titleLabel, "growx, wrap");

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoPanel.add(searchField, "growx, wrap");

        infoArea = new JTextArea("Vui lòng chọn một bàn để xem thông tin.");
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        JScrollPane infoScrollPane = new JScrollPane(infoArea);
        infoPanel.add(infoScrollPane, "grow, wrap");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        paymentButton = new JButton("Thanh Toán");
        paymentButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        paymentButton.setBackground(new Color(255, 69, 58));
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setFocusPainted(false);
        paymentButton.addActionListener(e -> {
            if (selectedTable > 0) {
                TableInfo tableInfo = tableStates.get(selectedTable);
                if (tableInfo != null && "On".equals(tableInfo.status)) {
                    generateBill(selectedTable);
                    tableStates.remove(selectedTable);
                    infoArea.setText("Vui lòng chọn một bàn để xem thông tin.");
                    selectedTable = 0;
                    loadTables();
                    updateOrderButtonVisibility();
                } else {
                    JOptionPane.showMessageDialog(this, "Bàn này chưa có đơn hàng để thanh toán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(paymentButton);

        orderButton = new JButton("Order");
        orderButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderButton.setBackground(new Color(22, 216, 160));
        orderButton.setForeground(Color.BLACK);
        orderButton.setFocusPainted(false);
        orderButton.addActionListener(e -> {
            if (selectedTable > 0) {
                JFrame orderFrame = new JFrame("Đặt món - Bàn " + selectedTable);
                orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                orderFrame.setSize(800, 600);
                orderFrame.setLocationRelativeTo(null);
                orderFrame.add(new OrderPanel(selectedTable, TableManager.this));
                orderFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một bàn trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(orderButton);

        infoPanel.add(buttonPanel, "center");

        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void updateTableTimeLabels() {
        for (int i = 1; i <= numberOfTables; i++) {
            JLabel timeLabel = tableTimeLabels.get(i);
            if (timeLabel != null) {
                TableInfo tableInfo = tableStates.get(i);
                if (tableInfo != null && "On".equals(tableInfo.status) && tableInfo.startTime > 0) {
                    long elapsedTime = (System.currentTimeMillis() - tableInfo.startTime) / 1000;
                    String timeFormatted = String.format("%d:%02d", elapsedTime / 60, elapsedTime % 60);
                    timeLabel.setText(timeFormatted);
                } else {
                    timeLabel.setText("");
                }
            }
        }
    }

    private void insertIntoHoadon(Connection conn, String billCode, int tableNumber, TableInfo tableInfo, String timeFormatted) throws SQLException {
        String hoadonSql = "INSERT INTO hoadon (MaHoaDon, Ban, TrangThai, SoKhach, ThoiGian, TongCong) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(hoadonSql)) {
            pstmt.setString(1, billCode);
            pstmt.setInt(2, tableNumber);
            pstmt.setString(3, tableInfo.status);
            pstmt.setInt(4, tableInfo.numberOfGuests);
            pstmt.setString(5, "00:" + timeFormatted);
            pstmt.setInt(6, 0);
            pstmt.executeUpdate();
        }
    }

    private int insertIntoChitiethoadon(Connection conn, String billCode, String orderDetails) throws Exception {
        int totalAmount = 0;
        String[] orderLines = orderDetails.split("\n");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());

        String chitiethoadonSql = "INSERT INTO chitiethoadon (MaHoaDon, TenMon, SoLuong, DonGia, ThanhTien, ThoiGianThucTe) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(chitiethoadonSql)) {
            int insertedCount = 0;

            for (String line : orderLines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--- Đơn hàng mới ---") || !line.startsWith("Món: ")) {
                    continue;
                }

                String[] parts = line.split(" - ");
                if (parts.length != 3) {
                    continue;
                }

                String tenMon = parts[0].replace("Món: ", "").trim();
                int soLuong;
                int donGia;

                try {
                    soLuong = Integer.parseInt(parts[1].replace("Số lượng: ", "").trim());
                    donGia = Integer.parseInt(parts[2].replace("Đơn giá: ", "").trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                int thanhTien = soLuong * donGia;
                totalAmount += thanhTien;

                pstmt.setString(1, billCode);
                pstmt.setString(2, tenMon);
                pstmt.setInt(3, soLuong);
                pstmt.setInt(4, donGia);
                pstmt.setInt(5, thanhTien);
                pstmt.setString(6, currentTime); // Thêm thời gian thực tế
                pstmt.executeUpdate();
                insertedCount++;
            }
        }

        return totalAmount;
    }

    private void generateBill(int tableNumber) {
        TableInfo tableInfo = tableStates.get(tableNumber);
        long elapsedTime = tableInfo.startTime > 0 ? (System.currentTimeMillis() - tableInfo.startTime) / 1000 : 0;
        String timeFormatted = String.format("%d:%02d", elapsedTime / 60, elapsedTime % 60);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String billCode = "BILL-T" + tableNumber + "-" + sdf.format(new java.util.Date());

        int width = 40;
        String separator = "-".repeat(width);
        String header = centerText("HÓA ĐƠN BÀN " + tableNumber, width);
        String billCodeText = centerText("Mã hóa đơn: " + billCode, width);
        String status = centerText("Trạng thái: " + tableInfo.status, width);
        String guests = centerText("Số khách: " + tableInfo.numberOfGuests, width);
        String time = centerText("Thời gian: " + timeFormatted, width);
        String detailsHeader = centerText("CHI TIẾT ĐƠN HÀNG", width);
        String footer = centerText("CẢM ƠN QUÝ KHÁCH!", width);

        String orderDetails = tableInfo.orderDetails.toString().trim();
        int totalAmount = 0;

        String[] lines = orderDetails.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("--- Đơn hàng mới ---")) {
                continue;
            }
            String[] parts = line.split(" - ");
            if (parts.length != 3) {
                continue;
            }
            try {
                int soLuong = Integer.parseInt(parts[1].replace("Số lượng: ", "").trim());
                int donGia = Integer.parseInt(parts[2].replace("Đơn giá: ", "").trim());
                totalAmount += soLuong * donGia;
            } catch (NumberFormatException e) {
                System.out.println("Lỗi tính tổng tiền ở dòng: " + line);
            }
        }

        String totalText = centerText("TỔNG CỘNG: " + totalAmount + " VNĐ", width);

        StringBuilder txtBillContent = new StringBuilder();
        txtBillContent.append(header).append("\n")
                .append(billCodeText).append("\n")
                .append(separator).append("\n")
                .append(status).append("\n")
                .append(guests).append("\n")
                .append(time).append("\n")
                .append(detailsHeader).append("\n")
                .append(orderDetails.isEmpty() ? "Không có đơn hàng\n" : orderDetails + "\n")
                .append(separator).append("\n")
                .append(totalText).append("\n")
                .append(separator).append("\n")
                .append(footer).append("\n");

        String fileName = "Bill_Table" + tableNumber + "_" + billCode + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(txtBillContent.toString());
            JOptionPane.showMessageDialog(this, "Hóa đơn đã được lưu tại: " + fileName, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean hasValidItems = false;
        if (!orderDetails.isEmpty()) {
            for (String line : lines) {
                line = line.trim();
                if (!line.isEmpty() && line.startsWith("Món: ")) {
                    hasValidItems = true;
                    break;
                }
            }
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false);
            try {
                insertIntoHoadon(conn, billCode, tableNumber, tableInfo, timeFormatted);
                int dbTotalAmount = insertIntoChitiethoadon(conn, billCode, tableInfo.orderDetails.toString());

                String updateHoadonSql = "UPDATE hoadon SET TongCong = ? WHERE MaHoaDon = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateHoadonSql)) {
                    pstmt.setInt(1, dbTotalAmount);
                    pstmt.setString(2, billCode);
                    pstmt.executeUpdate();
                }
                conn.commit();

            } catch (Exception ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn vào cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame billFrame = new JFrame("Hóa Đơn Bàn " + tableNumber);
        billFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        billFrame.setSize(400, 500);
        billFrame.setLocationRelativeTo(null);

        JTextPane billArea = new JTextPane();
        billArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        billArea.setEditable(false);
        billArea.setBackground(new Color(242, 242, 242));

        StyledDocument doc = billArea.getStyledDocument();
        SimpleAttributeSet boldCenter = new SimpleAttributeSet();
        StyleConstants.setBold(boldCenter, true);
        StyleConstants.setAlignment(boldCenter, StyleConstants.ALIGN_CENTER);
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        try {
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
            doc.insertString(doc.getLength(), "HÓA ĐƠN BÀN " + tableNumber + "\n", boldCenter);
            doc.insertString(doc.getLength(), "Mã hóa đơn: " + billCode + "\n", center);
            doc.insertString(doc.getLength(), separator + "\n", center);
            doc.insertString(doc.getLength(), "Trạng thái: " + tableInfo.status + "\n", center);
            doc.insertString(doc.getLength(), "Số khách: " + tableInfo.numberOfGuests + "\n", center);
            doc.insertString(doc.getLength(), "Thời gian: " + timeFormatted + "\n", center);
            doc.insertString(doc.getLength(), "CHI TIẾT ĐƠN HÀNG\n", boldCenter);
            doc.insertString(doc.getLength(), orderDetails.isEmpty() ? "Không có đơn hàng\n" : orderDetails + "\n", center);
            doc.insertString(doc.getLength(), separator + "\n", center);
            doc.insertString(doc.getLength(), "TỔNG CỘNG: " + totalAmount + " VNĐ\n", boldCenter);
            doc.insertString(doc.getLength(), separator + "\n", center);
            doc.insertString(doc.getLength(), "CẢM ƠN QUÝ KHÁCH!\n", boldCenter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        JScrollPane billScrollPane = new JScrollPane(billArea);
        billFrame.add(billScrollPane, BorderLayout.CENTER);

        billFrame.setVisible(true);
    }

    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        if (padding < 0) {
            padding = 0;
        }
        return " ".repeat(padding) + text + " ".repeat(padding);
    }

    private void updateInfoPanel(int tableNumber) {
        TableInfo tableInfo = tableStates.getOrDefault(tableNumber, new TableInfo("Off", "Không có đơn hàng", 0, 0));
        long elapsedTime = tableInfo.startTime > 0 ? (System.currentTimeMillis() - tableInfo.startTime) / 1000 : 0;
        String timeFormatted = String.format("%d:%02d", elapsedTime / 60, elapsedTime % 60);

        infoArea.setText(String.format("Thông tin bàn %d:\n"
                + "- Trạng thái: %s\n"
                + "- Số khách: %d\n"
                + "- Thời gian: %s\n"
                + "- Chi tiết đơn hàng:\n%s",
                tableNumber, tableInfo.status, tableInfo.numberOfGuests, timeFormatted, tableInfo.orderDetails.toString()));

        updateOrderButtonVisibility();
    }

    private void updateOrderButtonVisibility() {
        orderButton.setVisible(selectedTable > 0);
    }

    private int getSelectedTable() {
        return selectedTable;
    }

    @Override
    public void onOrderConfirmed(int tableNumber, String orderDetails, long startTime) {
        StringBuilder filteredOrderDetails = new StringBuilder();
        String[] lines = orderDetails.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("Đơn hàng:") || line.startsWith("Tổng cộng:")) {
                continue;
            }
            if (line.startsWith("- ")) {
                line = line.substring(2).trim();
                String[] parts = line.split(" x | VNĐ = ");
                if (parts.length != 3) {
                    continue;
                }
                String[] nameAndQuantity = parts[0].split(": ");
                if (nameAndQuantity.length != 2) {
                    continue;
                }
                String tenMon = nameAndQuantity[0].trim();
                String soLuong = nameAndQuantity[1].trim();
                String donGia = parts[1].replace(" VNĐ", "").trim();
                filteredOrderDetails.append(String.format("Món: %s - Số lượng: %s - Đơn giá: %s\n", tenMon, soLuong, donGia));
            }
        }

        TableInfo existingInfo = tableStates.get(tableNumber);
        if (existingInfo != null && "On".equals(existingInfo.status)) {
            if (filteredOrderDetails.length() > 0) {
                existingInfo.orderDetails.append("\n--- Đơn hàng mới ---\n").append(filteredOrderDetails);
            }
        } else {
            tableStates.put(tableNumber, new TableInfo("On", filteredOrderDetails.toString(), startTime, 0));
        }
        if (selectedTable == tableNumber) {
            updateInfoPanel(tableNumber);
        }
        loadTables();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    updateTableTimeLabels();
                    if (selectedTable > 0) {
                        updateInfoPanel(selectedTable);
                    }
                });
            }
        }, 0, 1000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Quản Lý Bàn Ăn");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 700);
                frame.setLocationRelativeTo(null);

                TableManager tableManager = new TableManager();
                frame.add(tableManager);

                frame.setVisible(true);
            }
        });
    }
}
