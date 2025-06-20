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
import model.Discount;
import net.miginfocom.swing.MigLayout;

public class DiscountManager extends JPanel {

    private ArrayList<Discount> list = new ArrayList<>();
    private int index = -1;
    private int check = 0; // 0: bình thường, 1: thêm, -1: sửa
    private JLabel lblMaGiamGia, lblTenGiamGia, lblPhanTramGiam, lblGiamToiDa, lblNgayBatDau, lblNgayKetThuc, lblSoLanSuDung, lblSoLanDaSuDung, lblFind;
    private Button btnSua, btnThem, btnXoa, btnLuu, btnHuy;
    private MyTextField txtMaGiamGia, txtTenGiamGia, txtPhanTramGiam, txtGiamToiDa, txtNgayBatDau, txtNgayKetThuc, txtSoLanSuDung, txtSoLanDaSuDung, txtFind;
    private JTable table;
    private JScrollPane scrollPane;

    public DiscountManager() {
        // Thiết lập giao diện chính
        setSize(1240, 842);
        setBackground(new Color(62, 167, 136));
        setLayout(new BorderLayout());

        // Tạo panel bên trái với MigLayout
        JPanel leftPanel = new JPanel(new MigLayout("fill", "[grow, fill]", "[]10[]10[]10[]10[]10[]10[]10[]10[]10[]"));
        leftPanel.setBackground(new Color(206, 157, 255));

        // Khởi tạo các thành phần giao diện
        lblMaGiamGia = new JLabel("Mã giảm giá:");
        lblMaGiamGia.setForeground(Color.WHITE);
        lblMaGiamGia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMaGiamGia.setPreferredSize(new Dimension(100, 30));

        txtMaGiamGia = new MyTextField();
        txtMaGiamGia.setHint("Mã giảm giá");
        txtMaGiamGia.setEditable(false);
        txtMaGiamGia.setPreferredSize(new Dimension(200, 30));

        lblTenGiamGia = new JLabel("Tên giảm giá:");
        lblTenGiamGia.setForeground(Color.WHITE);
        lblTenGiamGia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTenGiamGia.setPreferredSize(new Dimension(100, 30));

        txtTenGiamGia = new MyTextField();
        txtTenGiamGia.setHint("Tên giảm giá");
        txtTenGiamGia.setEditable(false);
        txtTenGiamGia.setPreferredSize(new Dimension(200, 30));

        lblPhanTramGiam = new JLabel("Phần trăm giảm:");
        lblPhanTramGiam.setForeground(Color.WHITE);
        lblPhanTramGiam.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPhanTramGiam.setPreferredSize(new Dimension(100, 30));

        txtPhanTramGiam = new MyTextField();
        txtPhanTramGiam.setHint("Phần trăm giảm (%)");
        txtPhanTramGiam.setEditable(false);
        txtPhanTramGiam.setPreferredSize(new Dimension(200, 30));

        lblGiamToiDa = new JLabel("Giảm tối đa:");
        lblGiamToiDa.setForeground(Color.WHITE);
        lblGiamToiDa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGiamToiDa.setPreferredSize(new Dimension(100, 30));

        txtGiamToiDa = new MyTextField();
        txtGiamToiDa.setHint("Giảm tối đa (VNĐ)");
        txtGiamToiDa.setEditable(false);
        txtGiamToiDa.setPreferredSize(new Dimension(200, 30));

        lblNgayBatDau = new JLabel("Ngày bắt đầu:");
        lblNgayBatDau.setForeground(Color.WHITE);
        lblNgayBatDau.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNgayBatDau.setPreferredSize(new Dimension(100, 30));

        txtNgayBatDau = new MyTextField();
        txtNgayBatDau.setHint("Ngày bắt đầu (yyyy-MM-dd)");
        txtNgayBatDau.setEditable(false);
        txtNgayBatDau.setPreferredSize(new Dimension(200, 30));

        lblNgayKetThuc = new JLabel("Ngày kết thúc:");
        lblNgayKetThuc.setForeground(Color.WHITE);
        lblNgayKetThuc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNgayKetThuc.setPreferredSize(new Dimension(100, 30));

        txtNgayKetThuc = new MyTextField();
        txtNgayKetThuc.setHint("Ngày kết thúc (yyyy-MM-dd)");
        txtNgayKetThuc.setEditable(false);
        txtNgayKetThuc.setPreferredSize(new Dimension(200, 30));

        lblSoLanSuDung = new JLabel("Số lần sử dụng:");
        lblSoLanSuDung.setForeground(Color.WHITE);
        lblSoLanSuDung.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSoLanSuDung.setPreferredSize(new Dimension(100, 30));

        txtSoLanSuDung = new MyTextField();
        txtSoLanSuDung.setHint("Số lần sử dụng tối đa");
        txtSoLanSuDung.setEditable(false);
        txtSoLanSuDung.setPreferredSize(new Dimension(200, 30));

        lblSoLanDaSuDung = new JLabel("Số lần đã dùng:");
        lblSoLanDaSuDung.setForeground(Color.WHITE);
        lblSoLanDaSuDung.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSoLanDaSuDung.setPreferredSize(new Dimension(100, 30));

        txtSoLanDaSuDung = new MyTextField();
        txtSoLanDaSuDung.setHint("Số lần đã sử dụng");
        txtSoLanDaSuDung.setEditable(false); // Không cho phép chỉnh sửa trực tiếp
        txtSoLanDaSuDung.setPreferredSize(new Dimension(200, 30));

        lblFind = new JLabel("Tìm kiếm:");
        lblFind.setForeground(Color.WHITE);
        lblFind.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFind.setPreferredSize(new Dimension(100, 30));

        txtFind = new MyTextField();
        txtFind.setHint("Tìm kiếm theo mã hoặc tên giảm giá");
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
        leftPanel.add(lblMaGiamGia, "cell 0 0");
        leftPanel.add(txtMaGiamGia, "cell 1 0");
        leftPanel.add(lblTenGiamGia, "cell 0 1");
        leftPanel.add(txtTenGiamGia, "cell 1 1");
        leftPanel.add(lblPhanTramGiam, "cell 0 2");
        leftPanel.add(txtPhanTramGiam, "cell 1 2");
        leftPanel.add(lblGiamToiDa, "cell 0 3");
        leftPanel.add(txtGiamToiDa, "cell 1 3");
        leftPanel.add(lblNgayBatDau, "cell 0 4");
        leftPanel.add(txtNgayBatDau, "cell 1 4");
        leftPanel.add(lblNgayKetThuc, "cell 0 5");
        leftPanel.add(txtNgayKetThuc, "cell 1 5");
        leftPanel.add(lblSoLanSuDung, "cell 0 6");
        leftPanel.add(txtSoLanSuDung, "cell 1 6");
        leftPanel.add(lblSoLanDaSuDung, "cell 0 7");
        leftPanel.add(txtSoLanDaSuDung, "cell 1 7");
        leftPanel.add(lblFind, "cell 0 8");
        leftPanel.add(txtFind, "cell 1 8");
        leftPanel.add(btnSua, "cell 0 9");
        leftPanel.add(btnThem, "cell 1 9");
        leftPanel.add(btnXoa, "cell 2 9");
        leftPanel.add(btnLuu, "cell 0 10");
        leftPanel.add(btnHuy, "cell 1 10");

        // Khởi tạo bảng JTable mã giảm giá
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT", "Mã giảm giá", "Tên giảm giá", "Phần trăm giảm", "Giảm tối đa", "Ngày bắt đầu", "Ngày kết thúc", "Số lần sử dụng", "Số lần đã dùng"}
        ));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 26));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        table.setRowHeight(75);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(8).setPreferredWidth(80);
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
                    txtMaGiamGia.setEditable(true);
                    txtTenGiamGia.setEditable(true);
                    txtPhanTramGiam.setEditable(true);
                    txtGiamToiDa.setEditable(true);
                    txtNgayBatDau.setEditable(true);
                    txtNgayKetThuc.setEditable(true);
                    txtSoLanSuDung.setEditable(true);
                    txtSoLanDaSuDung.setEditable(true);
                    onOff(false, true);
                }
            }
        });

        // Sự kiện nút Thêm
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check = 1;
                txtMaGiamGia.setEditable(true);
                txtTenGiamGia.setEditable(true);
                txtPhanTramGiam.setEditable(true);
                txtGiamToiDa.setEditable(true);
                txtNgayBatDau.setEditable(true);
                txtNgayKetThuc.setEditable(true);
                txtSoLanSuDung.setEditable(true);
                txtSoLanDaSuDung.setEditable(true);
                txtMaGiamGia.setText("");
                txtTenGiamGia.setText("");
                txtPhanTramGiam.setText("");
                txtGiamToiDa.setText("");
                txtNgayBatDau.setText("");
                txtNgayKetThuc.setText("");
                txtSoLanSuDung.setText("");
                txtSoLanDaSuDung.setText("");
                onOff(false, true);
            }
        });

        // Sự kiện nút Xóa
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int confirm = JOptionPane.showConfirmDialog(
                        btnXoa,
                        "Bạn có chắc chắn muốn xóa mã giảm giá này?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION && index >= 0 && index < list.size()) {
                    Discount discountToRemove = list.get(index);

                    try (Connection conn = JDBCuntil.getconection()) {
                        if (conn == null) {
                            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM giamgia WHERE maGiamGia = ?")) {
                            pstmt.setString(1, discountToRemove.getMaGiamGia());
                            pstmt.executeUpdate();
                        }

                        list.remove(index);
                        if (index >= list.size()) {
                            index = list.size() - 1;
                        }
                        view();
                        viewTable();
                        JOptionPane.showMessageDialog(null, "Xóa mã giảm giá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa mã giảm giá: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                    String maGiamGia = txtMaGiamGia.getText().trim();
                    String tenGiamGia = txtTenGiamGia.getText().trim();
                    String phanTramGiamText = txtPhanTramGiam.getText().trim();
                    String giamToiDaText = txtGiamToiDa.getText().trim();
                    String ngayBatDauText = txtNgayBatDau.getText().trim();
                    String ngayKetThucText = txtNgayKetThuc.getText().trim();
                    String soLanSuDungText = txtSoLanSuDung.getText().trim();
                    String soLanDaSuDungText = txtSoLanDaSuDung.getText().trim();

                    // Kiểm tra các trường bắt buộc
                    if (maGiamGia.isEmpty() || tenGiamGia.isEmpty() || phanTramGiamText.isEmpty() || giamToiDaText.isEmpty() || ngayBatDauText.isEmpty() || ngayKetThucText.isEmpty() || soLanSuDungText.isEmpty() || soLanDaSuDungText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng phần trăm giảm
                    double phanTramGiam;
                    try {
                        phanTramGiam = Double.parseDouble(phanTramGiamText);
                        if (phanTramGiam <= 0 || phanTramGiam > 100) {
                            JOptionPane.showMessageDialog(null, "Phần trăm giảm phải từ 0% đến 100%!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Phần trăm giảm phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng giảm tối đa
                    double giamToiDa;
                    try {
                        giamToiDa = Double.parseDouble(giamToiDaText);
                        if (giamToiDa < 0) {
                            JOptionPane.showMessageDialog(null, "Số tiền giảm tối đa không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Số tiền giảm tối đa phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng ngày
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    java.util.Date ngayBatDauUtil, ngayKetThucUtil;
                    java.sql.Date ngayBatDau, ngayKetThuc;
                    try {
                        ngayBatDauUtil = sdf.parse(ngayBatDauText);
                        ngayKetThucUtil = sdf.parse(ngayKetThucText);
                        ngayBatDau = new java.sql.Date(ngayBatDauUtil.getTime());
                        ngayKetThuc = new java.sql.Date(ngayKetThucUtil.getTime());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ngày phải có định dạng yyyy-MM-dd!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
                    if (ngayKetThuc.before(ngayBatDau)) {
                        JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày bắt đầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra ngày hiện tại nằm trong khoảng hiệu lực của mã giảm giá
                    java.util.Date today = new java.util.Date(); // Ngày hiện tại: 28/05/2025
                    if (today.before(ngayBatDau) || today.after(ngayKetThuc)) {
                        JOptionPane.showMessageDialog(null, "Mã giảm giá không nằm trong khoảng thời gian hiệu lực (hôm nay: 28/05/2025)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    }

                    // Kiểm tra số lần sử dụng
                    int soLanSuDung;
                    try {
                        soLanSuDung = Integer.parseInt(soLanSuDungText);
                        if (soLanSuDung <= 0) {
                            JOptionPane.showMessageDialog(null, "Số lần sử dụng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Số lần sử dụng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra số lần đã sử dụng
                    int soLanDaSuDung;
                    try {
                        soLanDaSuDung = Integer.parseInt(soLanDaSuDungText);
                        if (soLanDaSuDung < 0) {
                            JOptionPane.showMessageDialog(null, "Số lần đã sử dụng không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (soLanDaSuDung > soLanSuDung) {
                            JOptionPane.showMessageDialog(null, "Số lần đã sử dụng không được vượt quá số lần sử dụng tối đa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Số lần đã sử dụng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try (Connection conn = JDBCuntil.getconection()) {
                        if (conn == null) {
                            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (check == 1) { // Thêm mới
                            String sql = "INSERT INTO giamgia (maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                pstmt.setString(1, maGiamGia);
                                pstmt.setString(2, tenGiamGia);
                                pstmt.setDouble(3, phanTramGiam);
                                pstmt.setDouble(4, giamToiDa);
                                pstmt.setDate(5, ngayBatDau);
                                pstmt.setDate(6, ngayKetThuc);
                                pstmt.setInt(7, soLanSuDung);
                                pstmt.setInt(8, soLanDaSuDung);
                                pstmt.executeUpdate();
                                list.add(new Discount(maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung));
                            }
                        } else if (check == -1 && index >= 0 && index < list.size()) { // Sửa
                            String sql = "UPDATE giamgia SET tenGiamGia = ?, phanTramGiam = ?, giamToiDa = ?, ngayBatDau = ?, ngayKetThuc = ?, soLanSuDung = ?, soLanDaSuDung = ? WHERE maGiamGia = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                pstmt.setString(1, tenGiamGia);
                                pstmt.setDouble(2, phanTramGiam);
                                pstmt.setDouble(3, giamToiDa);
                                pstmt.setDate(4, ngayBatDau);
                                pstmt.setDate(5, ngayKetThuc);
                                pstmt.setInt(6, soLanSuDung);
                                pstmt.setInt(7, soLanDaSuDung);
                                pstmt.setString(8, maGiamGia);
                                pstmt.executeUpdate();
                                list.set(index, new Discount(maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung));
                            }
                        } else {
                            return;
                        }

                        viewTable();
                        txtMaGiamGia.setEditable(false);
                        txtTenGiamGia.setEditable(false);
                        txtPhanTramGiam.setEditable(false);
                        txtGiamToiDa.setEditable(false);
                        txtNgayBatDau.setEditable(false);
                        txtNgayKetThuc.setEditable(false);
                        txtSoLanSuDung.setEditable(false);
                        txtSoLanDaSuDung.setEditable(false);
                        check = 0;
                        onOff(true, false);
                        JOptionPane.showMessageDialog(null, "Lưu mã giảm giá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
                txtMaGiamGia.setEditable(false);
                txtTenGiamGia.setEditable(false);
                txtPhanTramGiam.setEditable(false);
                txtGiamToiDa.setEditable(false);
                txtNgayBatDau.setEditable(false);
                txtNgayKetThuc.setEditable(false);
                txtSoLanSuDung.setEditable(false);
                txtSoLanDaSuDung.setEditable(false);
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
                filterDiscount();
            }
        });

        loadDiscounts();
        viewTable();
    }

    // Tải danh sách mã giảm giá từ cơ sở dữ liệu
    private void loadDiscounts() {
        list.clear();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement("SELECT maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung FROM giamgia");
                 ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String maGiamGia = rs.getString("maGiamGia");
                    String tenGiamGia = rs.getString("tenGiamGia");
                    double phanTramGiam = rs.getDouble("phanTramGiam");
                    double giamToiDa = rs.getDouble("giamToiDa");
                    java.sql.Date ngayBatDau = rs.getDate("ngayBatDau");
                    java.sql.Date ngayKetThuc = rs.getDate("ngayKetThuc");
                    int soLanSuDung = rs.getInt("soLanSuDung");
                    int soLanDaSuDung = rs.getInt("soLanDaSuDung");
                    list.add(new Discount(maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung));
                    model.addRow(new Object[]{list.size(), maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu mã giảm giá: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Lọc mã giảm giá theo từ khóa tìm kiếm
    private void filterDiscount() {
        String searchTerm = txtFind.getText().toLowerCase().trim();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement(
                    "SELECT maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung FROM giamgia WHERE maGiamGia LIKE ? OR tenGiamGia LIKE ?")) {
                st.setString(1, "%" + searchTerm + "%");
                st.setString(2, "%" + searchTerm + "%");
                try (ResultSet rs = st.executeQuery()) {
                    int stt = 1;
                    while (rs.next()) {
                        String maGiamGia = rs.getString("maGiamGia");
                        String tenGiamGia = rs.getString("tenGiamGia");
                        double phanTramGiam = rs.getDouble("phanTramGiam");
                        double giamToiDa = rs.getDouble("giamToiDa");
                        java.sql.Date ngayBatDau = rs.getDate("ngayBatDau");
                        java.sql.Date ngayKetThuc = rs.getDate("ngayKetThuc");
                        int soLanSuDung = rs.getInt("soLanSuDung");
                        int soLanDaSuDung = rs.getInt("soLanDaSuDung");
                        model.addRow(new Object[]{stt++, maGiamGia, tenGiamGia, phanTramGiam, giamToiDa, ngayBatDau, ngayKetThuc, soLanSuDung, soLanDaSuDung});
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
        for (Discount discount : list) {
            model.addRow(new Object[]{
                stt++,
                discount.getMaGiamGia(),
                discount.getTenGiamGia(),
                discount.getPhanTramGiam(),
                discount.getGiamToiDa(),
                discount.getNgayBatDau(),
                discount.getNgayKetThuc(),
                discount.getSoLanSuDung(),
                discount.getSoLanDaSuDung()
            });
        }
    }

    // Hiển thị thông tin mã giảm giá được chọn
    private void view() {
        if (index < 0 || index >= list.size()) {
            txtMaGiamGia.setText("");
            txtTenGiamGia.setText("");
            txtPhanTramGiam.setText("");
            txtGiamToiDa.setText("");
            txtNgayBatDau.setText("");
            txtNgayKetThuc.setText("");
            txtSoLanSuDung.setText("");
            txtSoLanDaSuDung.setText("");
            return;
        }

        Discount discount = list.get(index);
        txtMaGiamGia.setText(discount.getMaGiamGia() != null ? discount.getMaGiamGia() : "");
        txtTenGiamGia.setText(discount.getTenGiamGia() != null ? discount.getTenGiamGia() : "");
        txtPhanTramGiam.setText(discount.getPhanTramGiam() > 0 ? String.valueOf(discount.getPhanTramGiam()) : "");
        txtGiamToiDa.setText(discount.getGiamToiDa() >= 0 ? String.valueOf(discount.getGiamToiDa()) : "");
        txtNgayBatDau.setText(discount.getNgayBatDau() != null ? discount.getNgayBatDau().toString() : "");
        txtNgayKetThuc.setText(discount.getNgayKetThuc() != null ? discount.getNgayKetThuc().toString() : "");
        txtSoLanSuDung.setText(discount.getSoLanSuDung() > 0 ? String.valueOf(discount.getSoLanSuDung()) : "");
        txtSoLanDaSuDung.setText(discount.getSoLanDaSuDung() >= 0 ? String.valueOf(discount.getSoLanDaSuDung()) : "");
        onOff(true, false);
    }

    // Bật/tắt các nút chức năng
    private void onOff(boolean a, boolean b) {
        btnSua.setVisible(a);
        btnThem.setVisible(a);
        btnXoa.setVisible(a);
        btnLuu.setVisible(b);
        btnHuy.setVisible(b);
    }

    // Điểm khởi chạy chương trình
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Mã Giảm Giá");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1240, 842);
            frame.setResizable(false);
            frame.getContentPane().add(new DiscountManager());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}