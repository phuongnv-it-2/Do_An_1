package view.page;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
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
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.CongThuc;
import model.ModelProducts;
import model.NguyenLieu;
import java.util.List;
import java.util.Map;
import model.Discount;

public class TableManager extends JPanel implements OrderPanel.OrderListener {

    private JPanel tablePanel;
    private JPanel infoPanel;
    private JButton inputButton;
    private JButton orderButton;
    private JButton paymentButton;
    private int numberOfTables = 20;
    private boolean isUpdatingNoteField = false;

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
        String note;

        TableInfo(String status, String orderDetails, long startTime, int numberOfGuests, String note) {
            this.status = status;
            this.orderDetails = new StringBuilder(orderDetails);
            this.startTime = startTime;
            this.numberOfGuests = numberOfGuests;
            this.note = note != null ? note : "";
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
                    JTextField noteField = findNoteTextField();
                    if (noteField != null && selectedTable > 0) {
                        TableInfo tableInfo = tableStates.get(selectedTable);
                        if (tableInfo != null && !noteField.getText().trim().isEmpty()) {
                            tableInfo.note = noteField.getText().trim();
                        }
                    }
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

        JTextField noteField = new JTextField();
        noteField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noteField.setPreferredSize(new Dimension(200, 40));
        noteField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        noteField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        noteField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                saveNote();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saveNote();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saveNote();
            }

            private void saveNote() {
                if (isUpdatingNoteField) {
                    return;
                }
                if (selectedTable > 0) {
                    TableInfo tableInfo = tableStates.get(selectedTable);
                    String text = noteField.getText().trim();
                    if (tableInfo != null) {
                        tableInfo.note = text.isEmpty() ? "" : text;
                        SwingUtilities.invokeLater(() -> updateInfoPanel(selectedTable));
                    }
                }
            }
        });

        noteField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (selectedTable > 0) {
                        TableInfo tableInfo = tableStates.get(selectedTable);
                        if (tableInfo != null) {
                            tableInfo.note = noteField.getText().trim();
                            updateInfoPanel(selectedTable);
                        }
                        evt.consume();
                    }
                }
            }
        });

        infoPanel.add(noteField, "growx, wrap");

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
            if (selectedTable <= 0) {
                JOptionPane.showMessageDialog(TableManager.this, "Vui lòng chọn một bàn trước!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            TableInfo tableInfo = tableStates.get(selectedTable);
            if (tableInfo == null || !"On".equals(tableInfo.status)) {
                JOptionPane.showMessageDialog(TableManager.this, "Bàn này chưa có đơn hàng để thanh toán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Show discount dialog
            JTextField discountCodeField = new JTextField(10);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nhập mã giảm giá (nếu có):"));
            panel.add(discountCodeField);

            int result = JOptionPane.showConfirmDialog(TableManager.this, panel, "Nhập Mã Giảm Giá", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            String discountCode = result == JOptionPane.OK_OPTION ? discountCodeField.getText().trim() : "";
            processPayment(selectedTable, tableInfo, discountCode);
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
                pstmt.setString(6, currentTime);
                pstmt.executeUpdate();
                insertedCount++;
            }
        }

        return totalAmount;
    }

    private void deductIngredients(Connection conn, String orderDetails) throws SQLException {
        Map<Integer, Integer> ingredientQuantities = new HashMap<>(); // Map để lưu tổng số lượng cần trừ cho mỗi nguyên liệu

        // Phân tích chi tiết đơn hàng để lấy số lượng của từng món
        String[] orderLines = orderDetails.split("\n");
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
            int soLuong = Integer.parseInt(parts[1].replace("Số lượng: ", "").trim());

            // Tra cứu MaSanPham từ bảng sanpham dựa trên TenMon
            String sanphamSql = "SELECT MaSanPham FROM sanpham WHERE TenSanPham = ?";
            String maSanPham = null;
            try (PreparedStatement pstmt = conn.prepareStatement(sanphamSql)) {
                pstmt.setString(1, tenMon);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        maSanPham = rs.getString("MaSanPham");
                        System.out.println("Found MaSanPham: " + maSanPham + " for TenMon: " + tenMon);
                    } else {
                        throw new SQLException("Không tìm thấy sản phẩm: " + tenMon + " trong bảng sanpham");
                    }
                }
            }

            // Tra cứu congthuc để lấy nguyên liệu cho sản phẩm này
            String congthucSql = "SELECT NguyenLieuID, SoLuong FROM congthuc WHERE MaSanPham = ?";
            boolean hasIngredients = false;
            try (PreparedStatement pstmt = conn.prepareStatement(congthucSql)) {
                pstmt.setString(1, maSanPham);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        hasIngredients = true;
                        int nguyenLieuID = rs.getInt("NguyenLieuID");
                        int requiredQuantity = rs.getInt("SoLuong");
                        if (requiredQuantity == 0) {
                            System.out.println("Cảnh báo: Số lượng nguyên liệu trong congthuc cho MaSanPham: " + maSanPham + ", NguyenLieuID: " + nguyenLieuID + " là 0. Không có gì để trừ.");
                            continue;
                        }
                        int totalToDeduct = requiredQuantity * soLuong;
                        System.out.println("Will deduct " + totalToDeduct + " from NguyenLieuID: " + nguyenLieuID);
                        ingredientQuantities.merge(nguyenLieuID, totalToDeduct, Integer::sum);
                    }
                }
                if (!hasIngredients) {
                    System.out.println("Không tìm thấy công thức cho MaSanPham: " + maSanPham + " trong bảng congthuc.");
                }
            }
        }

        // Kiểm tra và trừ nguyên liệu từ nguyenlieu
        String checkSql = "SELECT SoLuong FROM nguyenlieu WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE nguyenlieu SET SoLuong = SoLuong - ? WHERE id = ?";
        for (Map.Entry<Integer, Integer> entry : ingredientQuantities.entrySet()) {
            int nguyenLieuID = entry.getKey();
            int quantityToDeduct = entry.getValue();

            // Kiểm tra tồn kho trước khi trừ
            int currentStock = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, nguyenLieuID);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        currentStock = rs.getInt("SoLuong");
                        if (currentStock < quantityToDeduct) {
                            throw new SQLException("Không đủ nguyên liệu (ID: " + nguyenLieuID + "), Hiện có: " + currentStock + ", Cần: " + quantityToDeduct);
                        }
                    } else {
                        throw new SQLException("Không tìm thấy nguyên liệu với ID: " + nguyenLieuID + " trong bảng nguyenlieu");
                    }
                }
            }

            // Thực hiện trừ
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, quantityToDeduct);
                pstmt.setInt(2, nguyenLieuID);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Cập nhật thất bại cho NguyenLieuID: " + nguyenLieuID);
                }
                System.out.println("Đã trừ " + quantityToDeduct + " từ NguyenLieuID: " + nguyenLieuID + ". Số lượng còn lại: " + (currentStock - quantityToDeduct));
            }
        }
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

                if (hasValidItems) {
                    System.out.println("Starting ingredient deduction for order: " + orderDetails);
                    deductIngredients(conn, tableInfo.orderDetails.toString());
                    System.out.println("Ingredient deduction completed.");
                } else {
                    System.out.println("No valid items to deduct ingredients for.");
                }

                conn.commit();
                System.out.println("Transaction committed successfully.");
            } catch (Exception ex) {
                conn.rollback();
                System.out.println("Transaction rolled back due to: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Lỗi khi xử lý thanh toán: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        TableInfo tableInfo = tableStates.getOrDefault(tableNumber, new TableInfo("Off", "Không có đơn hàng", 0, 0, ""));
        long elapsedTime = tableInfo.startTime > 0 ? (System.currentTimeMillis() - tableInfo.startTime) / 1000 : 0;
        String timeFormatted = String.format("%d:%02d", elapsedTime / 60, elapsedTime % 60);

        infoArea.setText(String.format("Thông tin bàn %d:\n"
                + "- Trạng thái: %s\n"
                + "- Số khách: %d\n"
                + "- Thời gian: %s\n"
                + "- Ghi chú: %s\n"
                + "- Chi tiết đơn hàng:\n%s",
                tableNumber, tableInfo.status, tableInfo.numberOfGuests, timeFormatted,
                tableInfo.note.isEmpty() ? "Không có ghi chú" : tableInfo.note,
                tableInfo.orderDetails.toString()));

        JTextField noteField = findNoteTextField();
        if (noteField != null) {
            isUpdatingNoteField = true;
            noteField.setText(tableInfo.note);
            isUpdatingNoteField = false;
            noteField.setForeground(Color.BLACK);
        }

        updateOrderButtonVisibility();
    }

    private void updateOrderButtonVisibility() {
        orderButton.setVisible(selectedTable > 0);
    }

    private int getSelectedTable() {
        return selectedTable;
    }

    private JTextField findNoteTextField() {
        for (Component comp : infoPanel.getComponents()) {
            if (comp instanceof JTextField) {
                return (JTextField) comp;
            }
        }
        return null;
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
            tableStates.put(tableNumber, new TableInfo("On", filteredOrderDetails.toString(), startTime, 0, ""));
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

    private void showDiscountDialog(TableInfo tableInfo) {
        JTextField discountCodeField = new JTextField(10);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nhập mã giảm giá:"));
        panel.add(discountCodeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Nhập Mã Giảm Giá", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String discountCode = discountCodeField.getText().trim();
            if (!discountCode.isEmpty()) {
                applyDiscountAndGenerateBill(selectedTable, tableInfo, discountCode);
            } else {
                generateBill(selectedTable); // Thanh toán không dùng mã giảm giá
            }
        } else {
            generateBill(selectedTable); // Thanh toán không dùng mã giảm giá
        }
    }
    private void processPayment(int tableNumber, TableInfo tableInfo, String discountCode) {
    // Calculate total amount
    int originalTotal = calculateTotal(tableInfo.orderDetails.toString());
    int totalAfterDiscount = originalTotal;
    String discountInfo = "";
    Discount discount = null;

    // Validate discount if provided
    if (!discountCode.isEmpty()) {
        discount = validateDiscount(discountCode);
        if (discount != null) {
            if (new Date().before(discount.getNgayBatDau()) || new Date().after(discount.getNgayKetThuc())) {
                JOptionPane.showMessageDialog(this, "Mã giảm giá đã hết hạn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                discount = null;
            } else if (discount.getSoLanDaSuDung() >= discount.getSoLanSuDung()) {
                JOptionPane.showMessageDialog(this, "Mã giảm giá đã hết lượt sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                discount = null;
            } else {
                if (discount.getPhanTramGiam() > 0) {
                    double discountAmount = originalTotal * (discount.getPhanTramGiam() / 100.0);
                    if (discount.getGiamToiDa() > 0 && discountAmount > discount.getGiamToiDa()) {
                        discountAmount = discount.getGiamToiDa();
                    }
                    totalAfterDiscount = (int) (originalTotal - discountAmount);
                    discountInfo = String.format(" (Giảm %d%% - %d VNĐ, Mã: %s)", (int) discount.getPhanTramGiam(), (int) discountAmount, discount.getMaGiamGia());
                } else if (discount.getGiamToiDa() > 0) {
                    totalAfterDiscount = originalTotal - (int) discount.getGiamToiDa();
                    if (totalAfterDiscount < 0) totalAfterDiscount = 0;
                    discountInfo = String.format(" (Giảm %d VNĐ, Mã: %s)", (int) discount.getGiamToiDa(), discount.getMaGiamGia());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mã giảm giá không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Generate bill code and time
    long elapsedTime = tableInfo.startTime > 0 ? (System.currentTimeMillis() - tableInfo.startTime) / 1000 : 0;
    String timeFormatted = String.format("%d:%02d", elapsedTime / 60, elapsedTime % 60);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String billCode = "BILL-T" + tableNumber + "-" + sdf.format(new Date());

    // Generate bill content
    int width = 40;
    String separator = "-".repeat(width);
    String header = centerText("HÓA ĐƠN BÀN " + tableNumber, width);
    String billCodeText = centerText("Mã hóa đơn: " + billCode, width);
    String status = centerText("Trạng thái: " + tableInfo.status, width);
    String guests = centerText("Số khách: " + tableInfo.numberOfGuests, width);
    String time = centerText("Thời gian: " + timeFormatted, width);
    String detailsHeader = centerText("CHI TIẾT ĐƠN HÀNG", width);
    String discountText = discountInfo.isEmpty() ? "" : centerText("Giảm giá:" + discountInfo, width);
    String totalText = centerText("TỔNG CỘNG: " + totalAfterDiscount + " VNĐ", width);
    String footer = centerText("CẢM ƠN QUÝ KHÁCH!", width);

    String orderDetails = tableInfo.orderDetails.toString().trim();
    StringBuilder txtBillContent = new StringBuilder();
    txtBillContent.append(header).append("\n")
            .append(billCodeText).append("\n")
            .append(separator).append("\n")
            .append(status).append("\n")
            .append(guests).append("\n")
            .append(time).append("\n")
            .append(detailsHeader).append("\n")
            .append(orderDetails.isEmpty() ? "Không có đơn hàng\n" : orderDetails + "\n")
            .append(separator).append("\n");
    if (!discountInfo.isEmpty()) {
        txtBillContent.append(discountText).append("\n");
    }
    txtBillContent.append(totalText).append("\n")
            .append(separator).append("\n")
            .append(footer).append("\n");

    // Save bill to file
    String fileName = "Bill_Table" + tableNumber + "_" + billCode + ".txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        writer.write(txtBillContent.toString());
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Database operations
    boolean hasValidItems = !orderDetails.isEmpty();
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        conn.setAutoCommit(false);
        try {
            // Insert into hoadon with totalAfterDiscount
            insertIntoHoadon(conn, billCode, tableNumber, tableInfo, timeFormatted, totalAfterDiscount);
            int dbTotalAmount = insertIntoChitiethoadon(conn, billCode, orderDetails);

            // Update discount usage if applicable
            if (discount != null) {
                updateDiscountUsage(discount);
            }

            // Deduct ingredients if there are valid items
            if (hasValidItems) {
                deductIngredients(conn, orderDetails);
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Thanh toán thành công! Hóa đơn lưu tại: " + fileName, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            conn.rollback();
            JOptionPane.showMessageDialog(this, "Lỗi khi xử lý thanh toán: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            conn.setAutoCommit(true);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Display bill in UI
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
        if (!discountInfo.isEmpty()) {
            doc.insertString(doc.getLength(), "Giảm giá:" + discountInfo + "\n", center);
        }
        doc.insertString(doc.getLength(), "TỔNG CỘNG: " + totalAfterDiscount + " VNĐ\n", boldCenter);
        doc.insertString(doc.getLength(), separator + "\n", center);
        doc.insertString(doc.getLength(), "CẢM ƠN QUÝ KHÁCH!\n", boldCenter);
    } catch (BadLocationException e) {
        e.printStackTrace();
    }

    JScrollPane billScrollPane = new JScrollPane(billArea);
    billFrame.add(billScrollPane, BorderLayout.CENTER);
    billFrame.setVisible(true);

    // Clear table state
    tableStates.remove(tableNumber);
    infoArea.setText("Vui lòng chọn một bàn để xem thông tin.");
    JTextField noteField = findNoteTextField();
    if (noteField != null) noteField.setText("");
    selectedTable = 0;
    loadTables();
    updateOrderButtonVisibility();
}
    private int calculateTotal(String orderDetails) {
    int total = 0;
    String[] lines = orderDetails.split("\n");
    for (String line : lines) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("--- Đơn hàng mới ---") || !line.startsWith("Món: ")) {
            continue;
        }
        String[] parts = line.split(" - ");
        if (parts.length != 3) continue;
        try {
            int soLuong = Integer.parseInt(parts[1].replace("Số lượng: ", "").trim());
            int donGia = Integer.parseInt(parts[2].replace("Đơn giá: ", "").trim());
            total += soLuong * donGia;
        } catch (NumberFormatException e) {
            System.out.println("Lỗi tính tổng tiền ở dòng: " + line);
        }
    }
    return total;
}

    private Discount validateDiscount(String discountCode) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM giamgia WHERE maGiamGia = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, discountCode);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String maGiamGia = rs.getString("maGiamGia");
                        String tenGiamGia = rs.getString("tenGiamGia");
                        double phanTramGiam = rs.getDouble("phanTramGiam");
                        double giamToiDa = rs.getDouble("giamToiDa");
                        java.sql.Date ngayBatDau = rs.getDate("ngayBatDau");
                        java.sql.Date ngayKetThuc = rs.getDate("ngayKetThuc");
                        int soLanSuDung = rs.getInt("soLanSuDung");
                        int soLanDaSuDung = rs.getInt("soLanDaSuDung");
                        return new Discount(maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi kiểm tra mã giảm giá: " + ex.getMessage());
        }
        return null;
    }

    private void applyDiscountAndGenerateBill(int tableNumber, TableInfo tableInfo, String discountCode) {
        Discount discount = validateDiscount(discountCode);
        int originalTotal = 0;
        String[] lines = tableInfo.orderDetails.toString().split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("--- Đơn hàng mới ---") || !line.startsWith("Món: ")) {
                continue;
            }
            String[] parts = line.split(" - ");
            if (parts.length != 3) {
                continue;
            }
            try {
                int soLuong = Integer.parseInt(parts[1].replace("Số lượng: ", "").trim());
                int donGia = Integer.parseInt(parts[2].replace("Đơn giá: ", "").trim());
                originalTotal += soLuong * donGia;
            } catch (NumberFormatException e) {
                System.out.println("Lỗi tính tổng tiền ở dòng: " + line);
            }
        }

        int totalAfterDiscount = originalTotal;
        String discountInfo = "";
        if (discount != null) {
            if (new Date().before(discount.getNgayBatDau()) || new Date().after(discount.getNgayKetThuc())) {
                JOptionPane.showMessageDialog(this, "Mã giảm giá đã hết hạn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else if (discount.getSoLanDaSuDung() >= discount.getSoLanSuDung()) {
                JOptionPane.showMessageDialog(this, "Mã giảm giá đã hết lượt sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                if (discount.getPhanTramGiam() > 0) {
                    double discountAmount = originalTotal * (discount.getPhanTramGiam() / 100.0);
                    if (discount.getGiamToiDa() > 0 && discountAmount > discount.getGiamToiDa()) {
                        discountAmount = discount.getGiamToiDa();
                    }
                    totalAfterDiscount = (int) (originalTotal - discountAmount);
                    discountInfo = String.format(" (Giảm %d%% - %d VNĐ, Mã: %s)", (int) discount.getPhanTramGiam(), (int) discountAmount, discount.getMaGiamGia());
                    updateDiscountUsage(discount);
                } else if (discount.getGiamToiDa() > 0) {
                    totalAfterDiscount = originalTotal - (int) discount.getGiamToiDa();
                    if (totalAfterDiscount < 0) {
                        totalAfterDiscount = 0;
                    }
                    discountInfo = String.format(" (Giảm %d VNĐ, Mã: %s)", (int) discount.getGiamToiDa(), discount.getMaGiamGia());
                    updateDiscountUsage(discount);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mã giảm giá không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }

        if (totalAfterDiscount == originalTotal) {
            generateBill(tableNumber); // Nếu không áp dụng được giảm giá, sử dụng logic cũ
            return;
        }

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
        String discountText = centerText("Giảm giá:" + discountInfo, width);
        String totalText = centerText("TỔNG CỘNG: " + totalAfterDiscount + " VNĐ", width);
        String footer = centerText("CẢM ƠN QUÝ KHÁCH!", width);

        StringBuilder txtBillContent = new StringBuilder();
        txtBillContent.append(header).append("\n")
                .append(billCodeText).append("\n")
                .append(separator).append("\n")
                .append(status).append("\n")
                .append(guests).append("\n")
                .append(time).append("\n")
                .append(detailsHeader).append("\n")
                .append(tableInfo.orderDetails.toString().trim().isEmpty() ? "Không có đơn hàng\n" : tableInfo.orderDetails + "\n")
                .append(separator).append("\n")
                .append(discountText).append("\n")
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

        boolean hasValidItems = !tableInfo.orderDetails.toString().trim().isEmpty();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false);
            try {
                insertIntoHoadon(conn, billCode, tableNumber, tableInfo, timeFormatted);
                int dbTotalAmount = insertIntoChitiethoadon(conn, billCode, tableInfo.orderDetails.toString());

                String updateHoadonSql = "UPDATE hoadon SET TongCong = ? WHERE MaHoaDon = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateHoadonSql)) {
                    pstmt.setInt(1, totalAfterDiscount);
                    pstmt.setString(2, billCode);
                    pstmt.executeUpdate();
                }

                if (hasValidItems) {
                    deductIngredients(conn, tableInfo.orderDetails.toString());
                }

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Lỗi khi xử lý thanh toán: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableStates.remove(tableNumber);
        infoArea.setText("Vui lòng chọn một bàn để xem thông tin.");
        JTextField noteField = findNoteTextField();
        if (noteField != null) {
            noteField.setText("");
        }
        selectedTable = 0;
        loadTables();
        updateOrderButtonVisibility();

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
            doc.insertString(doc.getLength(), tableInfo.orderDetails.toString().trim().isEmpty() ? "Không có đơn hàng\n" : tableInfo.orderDetails + "\n", center);
            doc.insertString(doc.getLength(), separator + "\n", center);
            doc.insertString(doc.getLength(), "Giảm giá:" + discountInfo + "\n", center);
            doc.insertString(doc.getLength(), "TỔNG CỘNG: " + totalAfterDiscount + " VNĐ\n", boldCenter);
            doc.insertString(doc.getLength(), separator + "\n", center);
            doc.insertString(doc.getLength(), "CẢM ƠN QUÝ KHÁCH!\n", boldCenter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        JScrollPane billScrollPane = new JScrollPane(billArea);
        billFrame.add(billScrollPane, BorderLayout.CENTER);

        billFrame.setVisible(true);
    }

    private void updateDiscountUsage(Discount discount) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE giamgia SET soLanDaSuDung = soLanDaSuDung + 1 WHERE maGiamGia = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, discount.getMaGiamGia());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi cập nhật số lần sử dụng mã giảm giá: " + ex.getMessage());
        }
    }

    private void insertIntoHoadon(Connection conn, String billCode, int tableNumber, TableInfo tableInfo, String timeFormatted, int totalAmount) throws SQLException {
        String hoadonSql = "INSERT INTO hoadon (MaHoaDon, Ban, TrangThai, SoKhach, ThoiGian, TongCong) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(hoadonSql)) {
            pstmt.setString(1, billCode);
            pstmt.setInt(2, tableNumber);
            pstmt.setString(3, tableInfo.status);
            pstmt.setInt(4, tableInfo.numberOfGuests);
            pstmt.setString(5, "00:" + timeFormatted);
            pstmt.setInt(6, totalAmount); // Sử dụng totalAmount được truyền vào
            pstmt.executeUpdate();
        }
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
