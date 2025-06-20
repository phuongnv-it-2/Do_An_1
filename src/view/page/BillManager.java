package view.page;

import Database.JDBCuntil;
import Swing.Button;
import Swing.MyTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.ModelBill;
import net.miginfocom.swing.MigLayout;

public class BillManager extends JPanel {

    private ArrayList<ModelBill> list = new ArrayList<>();
    private int index = -1;
    private int check = 0; // 0: bình thường, 1: thêm, -1: sửa
    private JLabel lblMaHoaDon, lblTenMon, lblSoLuong, lblDonGia, lblThanhTien, lblThoiGianThucTe, lblFind;
    private Button btnSua, btnThem, btnXoa, btnLuu, btnHuy;
    private MyTextField txtMaHoaDon, txtTenMon, txtSoLuong, txtDonGia, txtThanhTien, txtThoiGianThucTe, txtFind;
    private JTable table;
    private JScrollPane scrollPane;

    public BillManager() {
        // Thiết lập giao diện chính
        setSize(1240, 842);
        setBackground(new Color(62, 167, 136));
        setLayout(new BorderLayout());

        // Tạo panel bên trái với MigLayout
        JPanel leftPanel = new JPanel(new MigLayout("fill", "[grow, fill]", "[]10[]10[]10[]10[]10[]10[]10[]"));
        leftPanel.setBackground(new Color(206, 157, 255));

        // Khởi tạo các thành phần giao diện
        lblMaHoaDon = new JLabel("Mã hóa đơn:");
        lblMaHoaDon.setForeground(Color.WHITE);
        lblMaHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMaHoaDon.setPreferredSize(new Dimension(100, 30));

        txtMaHoaDon = new MyTextField();
        txtMaHoaDon.setHint("Mã hóa đơn");
        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setPreferredSize(new Dimension(200, 30));

        lblTenMon = new JLabel("Tên món:");
        lblTenMon.setForeground(Color.WHITE);
        lblTenMon.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTenMon.setPreferredSize(new Dimension(100, 30));

        txtTenMon = new MyTextField();
        txtTenMon.setHint("Tên món");
        txtTenMon.setEditable(false);
        txtTenMon.setPreferredSize(new Dimension(200, 30));

        lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setForeground(Color.WHITE);
        lblSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSoLuong.setPreferredSize(new Dimension(100, 30));

        txtSoLuong = new MyTextField();
        txtSoLuong.setHint("Số lượng");
        txtSoLuong.setEditable(false);
        txtSoLuong.setPreferredSize(new Dimension(200, 30));

        lblDonGia = new JLabel("Đơn giá:");
        lblDonGia.setForeground(Color.WHITE);
        lblDonGia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDonGia.setPreferredSize(new Dimension(100, 30));

        txtDonGia = new MyTextField();
        txtDonGia.setHint("Đơn giá");
        txtDonGia.setEditable(false);
        txtDonGia.setPreferredSize(new Dimension(200, 30));

        lblThanhTien = new JLabel("Thành tiền:");
        lblThanhTien.setForeground(Color.WHITE);
        lblThanhTien.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblThanhTien.setPreferredSize(new Dimension(100, 30));

        txtThanhTien = new MyTextField();
        txtThanhTien.setHint("Thành tiền");
        txtThanhTien.setEditable(false);
        txtThanhTien.setPreferredSize(new Dimension(200, 30));

        lblThoiGianThucTe = new JLabel("Thời gian thực tế:");
        lblThoiGianThucTe.setForeground(Color.WHITE);
        lblThoiGianThucTe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblThoiGianThucTe.setPreferredSize(new Dimension(100, 30));

        txtThoiGianThucTe = new MyTextField();
        txtThoiGianThucTe.setHint("Thời gian (yyyy-MM-dd HH:mm:ss)");
        txtThoiGianThucTe.setEditable(false);
        txtThoiGianThucTe.setPreferredSize(new Dimension(200, 30));

        lblFind = new JLabel("Tìm kiếm:");
        lblFind.setForeground(Color.WHITE);
        lblFind.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFind.setPreferredSize(new Dimension(100, 30));

        txtFind = new MyTextField();
        txtFind.setHint("Tìm kiếm theo mã hóa đơn hoặc tên món");
        txtFind.setPreferredSize(new Dimension(300, 30));

        // Khởi tạo các nút chức năng
        btnSua = new Button();
        btnSua.setPreferredSize(new Dimension(100, 30));
        btnSua.setBackground(new Color(210, 30, 179));
        btnSua.setForeground(Color.WHITE);
        btnSua.setText("Sửa");
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnThem = new Button();
        btnThem.setPreferredSize(new Dimension(100, 30));
        btnThem.setBackground(new Color(210, 30, 179));
        btnThem.setForeground(Color.WHITE);
        btnThem.setText("Thêm");
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnXoa = new Button();
        btnXoa.setPreferredSize(new Dimension(100, 30));
        btnXoa.setBackground(new Color(210, 30, 179));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setText("Xóa");
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnLuu = new Button();
        btnLuu.setPreferredSize(new Dimension(100, 30));
        btnLuu.setBackground(new Color(210, 30, 179));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setText("Lưu");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLuu.setVisible(false);

        btnHuy = new Button();
        btnHuy.setPreferredSize(new Dimension(100, 30));
        btnHuy.setBackground(new Color(210, 30, 179));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setText("Hủy bỏ");
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnHuy.setVisible(false);

        // Thêm các thành phần vào leftPanel
        leftPanel.setPreferredSize(new Dimension(500, 842));
        leftPanel.add(lblMaHoaDon, "cell 0 0");
        leftPanel.add(txtMaHoaDon, "cell 1 0");
        leftPanel.add(lblTenMon, "cell 0 1");
        leftPanel.add(txtTenMon, "cell 1 1");
        leftPanel.add(lblSoLuong, "cell 0 2");
        leftPanel.add(txtSoLuong, "cell 1 2");
        leftPanel.add(lblDonGia, "cell 0 3");
        leftPanel.add(txtDonGia, "cell 1 3");
        leftPanel.add(lblThanhTien, "cell 0 4");
        leftPanel.add(txtThanhTien, "cell 1 4");
        leftPanel.add(lblThoiGianThucTe, "cell 0 5");
        leftPanel.add(txtThoiGianThucTe, "cell 1 5");
        leftPanel.add(lblFind, "cell 0 6");
        leftPanel.add(txtFind, "cell 1 6");
        leftPanel.add(btnSua, "cell 0 7");
        leftPanel.add(btnThem, "cell 1 7");
        leftPanel.add(btnXoa, "cell 2 7");
        leftPanel.add(btnLuu, "cell 0 8");
        leftPanel.add(btnHuy, "cell 1 8");

        // Khởi tạo bảng JTable hóa đơn
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT", "Mã hóa đơn", "Tên món", "Số lượng", "Đơn giá", "Thành tiền", "Thời gian thực tế"}
        ));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 26));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        table.setRowHeight(75);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(22, 216, 160));
        table.setSelectionForeground(Color.WHITE);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 500));
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Sự kiện nút Sửa
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (index >= 0) {
                    check = -1;
                    txtMaHoaDon.setEditable(true);
                    txtTenMon.setEditable(true);
                    txtSoLuong.setEditable(true);
                    txtDonGia.setEditable(true);
                    txtThanhTien.setEditable(false); // ThanhTien tự tính
                    txtThoiGianThucTe.setEditable(true);
                    onOff(false, true);
                    calculateThanhTien();
                }
            }
        });

        // Sự kiện nút Thêm
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check = 1;
                txtMaHoaDon.setEditable(true);
                txtTenMon.setEditable(true);
                txtSoLuong.setEditable(true);
                txtDonGia.setEditable(true);
                txtThanhTien.setEditable(false); // ThanhTien tự tính
                txtThoiGianThucTe.setEditable(true);
                txtMaHoaDon.setText("");
                txtTenMon.setText("");
                txtSoLuong.setText("");
                txtDonGia.setText("");
                txtThanhTien.setText("");
                txtThoiGianThucTe.setText("");
                onOff(false, true);
            }
        });

        // Sự kiện nút Xoa
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int confirm = JOptionPane.showConfirmDialog(
                        btnXoa,
                        "Bạn có chắc chắn muốn xóa hóa đơn này?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION && index >= 0 && index < list.size()) {
                    ModelBill billToRemove = list.get(index);

                    try (Connection conn = JDBCuntil.getconection()) {
                        if (conn == null) {
                            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ChiTietHoaDon WHERE ID = ?")) {
                            pstmt.setInt(1, getIdFromMaHoaDon(billToRemove.getMaDonMua()));
                            pstmt.executeUpdate();
                        }

                        list.remove(index);
                        if (index >= list.size()) {
                            index = list.size() - 1;
                        }
                        view();
                        viewTable();
                        JOptionPane.showMessageDialog(null, "Xóa hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Sự kiện nút Lưu
        btnLuu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    String maHoaDon = txtMaHoaDon.getText().trim();
                    String tenMon = txtTenMon.getText().trim();
                    String soLuongText = txtSoLuong.getText().trim();
                    String donGiaText = txtDonGia.getText().trim();
                    String thoiGianThucTeText = txtThoiGianThucTe.getText().trim();

                    // Kiểm tra các trường bắt buộc
                    if (maHoaDon.isEmpty() || tenMon.isEmpty() || soLuongText.isEmpty() || donGiaText.isEmpty() || thoiGianThucTeText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng số lượng
                    int soLuong;
                    try {
                        soLuong = Integer.parseInt(soLuongText);
                        if (soLuong <= 0) {
                            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng đơn giá
                    int donGia;
                    try {
                        donGia = Integer.parseInt(donGiaText);
                        if (donGia <= 0) {
                            JOptionPane.showMessageDialog(null, "Đơn giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Đơn giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tính thành tiền
                    int thanhTien = soLuong * donGia;
                    txtThanhTien.setText(String.valueOf(thanhTien));

                    // Kiểm tra định dạng thời gian
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.setLenient(false);
                    java.util.Date thoiGianThucTe;
                    try {
                        thoiGianThucTe = sdf.parse(thoiGianThucTeText);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thời gian phải có định dạng yyyy-MM-dd HH:mm:ss!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try (Connection conn = JDBCuntil.getconection()) {
                        if (conn == null) {
                            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (check == 1) { // Thêm mới
                            String sql = "INSERT INTO ChiTietHoaDon (MaHoaDon, TenMon, SoLuong, DonGia, ThanhTien, ThoiGianThucTe) VALUES (?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                                pstmt.setString(1, maHoaDon);
                                pstmt.setString(2, tenMon);
                                pstmt.setInt(3, soLuong);
                                pstmt.setInt(4, donGia);
                                pstmt.setInt(5, thanhTien);
                                pstmt.setTimestamp(6, new java.sql.Timestamp(thoiGianThucTe.getTime()));
                                pstmt.executeUpdate();
                            }
                        } else if (check == -1 && index >= 0 && index < list.size()) { // Sửa
                            int id = getIdFromMaHoaDon(maHoaDon);
                            String sql = "UPDATE ChiTietHoaDon SET TenMon = ?, SoLuong = ?, DonGia = ?, ThanhTien = ?, ThoiGianThucTe = ? WHERE ID = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                pstmt.setString(1, tenMon);
                                pstmt.setInt(2, soLuong);
                                pstmt.setInt(3, donGia);
                                pstmt.setInt(4, thanhTien);
                                pstmt.setTimestamp(5, new java.sql.Timestamp(thoiGianThucTe.getTime()));
                                pstmt.setInt(6, id);
                                pstmt.executeUpdate();
                                list.set(index, new ModelBill(maHoaDon, tenMon, soLuong, donGia, thoiGianThucTe));
                            }
                        } else {
                            return;
                        }

                        viewTable();
                        txtMaHoaDon.setEditable(false);
                        txtTenMon.setEditable(false);
                        txtSoLuong.setEditable(false);
                        txtDonGia.setEditable(false);
                        txtThanhTien.setEditable(false);
                        txtThoiGianThucTe.setEditable(false);
                        check = 0;
                        onOff(true, false);
                        JOptionPane.showMessageDialog(null, "Lưu hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi thao tác với cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Sự kiện nút Hủy
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaHoaDon.setEditable(false);
                txtTenMon.setEditable(false);
                txtSoLuong.setEditable(false);
                txtDonGia.setEditable(false);
                txtThanhTien.setEditable(false);
                txtThoiGianThucTe.setEditable(false);
                check = 0;
                onOff(true, false);
                view();
            }
        });

        // Sự kiện chọn dòng trong bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                index = table.getSelectedRow();
                view();
            }
        });

        // Sự kiện tìm kiếm
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterBill();
            }
        });

        // Sự kiện thay đổi số lượng hoặc đơn giá để tính thành tiền
        txtSoLuong.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
        });
        txtDonGia.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateThanhTien(); }
        });

        loadBills();
        viewTable();
    }

    // Tải danh sách hóa đơn từ cơ sở dữ liệu
    private void loadBills() {
        list.clear();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement("SELECT ID, MaHoaDon, TenMon, SoLuong, DonGia, ThanhTien, ThoiGianThucTe FROM ChiTietHoaDon");
                 ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String maHoaDon = rs.getString("MaHoaDon");
                    String tenMon = rs.getString("TenMon");
                    int soLuong = rs.getInt("SoLuong");
                    int donGia = rs.getInt("DonGia");
                    int thanhTien = rs.getInt("ThanhTien");
                    java.util.Date thoiGianThucTe = rs.getTimestamp("ThoiGianThucTe");
                    list.add(new ModelBill(maHoaDon, tenMon, soLuong, donGia, thoiGianThucTe));
                    model.addRow(new Object[]{list.size(), maHoaDon, tenMon, soLuong, donGia, thanhTien, formatDate(thoiGianThucTe)});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Lọc hóa đơn theo từ khóa tìm kiếm
    private void filterBill() {
        String searchTerm = txtFind.getText().toLowerCase().trim();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement(
                    "SELECT ID, MaHoaDon, TenMon, SoLuong, DonGia, ThanhTien, ThoiGianThucTe FROM ChiTietHoaDon WHERE MaHoaDon LIKE ? OR TenMon LIKE ?")) {
                st.setString(1, "%" + searchTerm + "%");
                st.setString(2, "%" + searchTerm + "%");
                try (ResultSet rs = st.executeQuery()) {
                    int stt = 1;
                    while (rs.next()) {
                        String maHoaDon = rs.getString("MaHoaDon");
                        String tenMon = rs.getString("TenMon");
                        int soLuong = rs.getInt("SoLuong");
                        int donGia = rs.getInt("DonGia");
                        int thanhTien = rs.getInt("ThanhTien");
                        java.util.Date thoiGianThucTe = rs.getTimestamp("ThoiGianThucTe");
                        model.addRow(new Object[]{stt++, maHoaDon, tenMon, soLuong, donGia, thanhTien, formatDate(thoiGianThucTe)});
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Cập nhật bảng hiển thị
    private void viewTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (ModelBill bill : list) {
            model.addRow(new Object[]{
                stt++,
                bill.getMaDonMua(),
                bill.getTenSP(),
                bill.getSoluong(),
                bill.getGia(),
                bill.getSoluong() * bill.getGia(), // Tính lại ThanhTien
                formatDate(bill.getNgayMua())
            });
        }
    }

    // Hiển thị thông tin hóa đơn được chọn
    private void view() {
        if (index < 0 || index >= list.size()) {
            txtMaHoaDon.setText("");
            txtTenMon.setText("");
            txtSoLuong.setText("");
            txtDonGia.setText("");
            txtThanhTien.setText("");
            txtThoiGianThucTe.setText("");
            return;
        }

        ModelBill bill = list.get(index);
        txtMaHoaDon.setText(bill.getMaDonMua() != null ? bill.getMaDonMua() : "");
        txtTenMon.setText(bill.getTenSP() != null ? bill.getTenSP() : "");
        txtSoLuong.setText(bill.getSoluong() > 0 ? String.valueOf(bill.getSoluong()) : "");
        txtDonGia.setText(bill.getGia() > 0 ? String.valueOf(bill.getGia()) : "");
        txtThanhTien.setText(String.valueOf(bill.getSoluong() * bill.getGia()));
        txtThoiGianThucTe.setText(bill.getNgayMua() != null ? formatDate(bill.getNgayMua()) : "");
        onOff(true, false);
    }

    // Định dạng ngày
    private String formatDate(java.util.Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    // Bật/tắt các nút chức năng
    private void onOff(boolean a, boolean b) {
        btnSua.setVisible(a);
        btnThem.setVisible(a);
        btnXoa.setVisible(a);
        btnLuu.setVisible(b);
        btnHuy.setVisible(b);
    }

    // Tính thành tiền khi thay đổi số lượng hoặc đơn giá
    private void calculateThanhTien() {
        if (check != 0) {
            try {
                int soLuong = txtSoLuong.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtSoLuong.getText().trim());
                int donGia = txtDonGia.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtDonGia.getText().trim());
                int thanhTien = soLuong * donGia;
                txtThanhTien.setText(String.valueOf(thanhTien));
            } catch (NumberFormatException e) {
                txtThanhTien.setText("0");
            }
        }
    }

    // Lấy ID từ MaHoaDon (giả định duy nhất)
    private int getIdFromMaHoaDon(String maHoaDon) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaDonMua().equals(maHoaDon)) {
                return i + 1; // Giả định ID tăng từ 1
            }
        }
        return -1;
    }

    // Điểm khởi chạy chương trình
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Hóa Đơn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1240, 842);
            frame.setResizable(false);
            frame.getContentPane().add(new BillManager());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}