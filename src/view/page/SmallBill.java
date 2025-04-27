package view.page;

import Database.JDBCuntil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class SmallBill {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Hóa Đơn Thanh Toán");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header label
        JLabel headerLabel = new JLabel("Cafe Acoustic", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Table model setup
        String[] columnNames = {"Mã", "Tên", "Đơn giá", "Số Lượng", "Tổng Giá", "Bàn"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Footer panel with total and button
        JPanel footerPanel = new JPanel(new GridLayout(2, 1));

        // Total label
        JLabel totalLabel = new JLabel("Tổng cộng: 0 VND", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        footerPanel.add(totalLabel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton payButton = new JButton("Thanh Toán");
        payButton.addActionListener(e -> {
            // Ask for the table number (Ban) using an input dialog
            String tableNumber = JOptionPane.showInputDialog(frame, "Nhập số bàn (Ban):");

            if (tableNumber != null && !tableNumber.trim().isEmpty()) {
                // Insert data into the database
                insertDataToDatabase(tableModel, tableNumber);
                JOptionPane.showMessageDialog(frame, "Thanh toán thành công và dữ liệu đã được lưu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Close the window after successful payment
            } else {
                JOptionPane.showMessageDialog(frame, "Bạn phải nhập số bàn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(payButton);
        footerPanel.add(buttonPanel);

        frame.add(footerPanel, BorderLayout.SOUTH);

        // Fetch and display data from the database
        fetchDataFromDatabase(tableModel);

        // Show the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Fetch data from the database and populate the table
    private static void fetchDataFromDatabase(DefaultTableModel tableModel) {
        try (Connection conn = JDBCuntil.getconection()) {
            // Query to get the menu items: "Tên", "Giá", and "Số Lượng"
            String sql = "SELECT tenSP, gia, soluong FROM menu_items"; // Modify this to match your table
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // Process the result set and add rows to the table model
            while (rs.next()) {
                String tenSP = rs.getString("tenSP"); // Tên
                int gia = rs.getInt("gia"); // Giá
                int soluong = rs.getInt("soluong"); // Số Lượng
                int tongGia = gia * soluong; // Tổng Giá = Giá * Số Lượng

                // Add row to the table model without using Vector
                Object[] rowData = new Object[] {
                    tableModel.getRowCount() + 1, // Mã (auto-generated based on row count)
                    tenSP,                         // Tên
                    gia,                           // Đơn giá
                    soluong,                       // Số Lượng
                    tongGia,                       // Tổng Giá
                    ""                              // Bàn (empty for now, will be filled during payment)
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Insert data into the database during payment
    private static void insertDataToDatabase(DefaultTableModel tableModel, String tableNumber) {
        try (Connection conn = JDBCuntil.getconection()) {
            String sql = "INSERT INTO bill (maDonMua, tenSP, soluong, gia, ngayMua, Ban) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                pstmt.setInt(1, (int) tableModel.getValueAt(i, 0)); // Mã (auto-generated)
                pstmt.setString(2, tableModel.getValueAt(i, 1).toString()); // Tên
                pstmt.setInt(3, (int) tableModel.getValueAt(i, 3)); // Số Lượng
                pstmt.setInt(4, (int) tableModel.getValueAt(i, 2)); // Đơn giá
                pstmt.setDate(5, java.sql.Date.valueOf(LocalDate.now())); // Ngày mua
                pstmt.setString(6, tableNumber); // Số bàn (Ban)
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
