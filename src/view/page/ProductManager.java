package view.page;

import Database.JDBCuntil;
import Service.ImageRenderer;
import Swing.Button;
import Swing.MyTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import model.ModelProducts;
import model.NguyenLieu;
import net.miginfocom.swing.MigLayout;
import Service.*;

public class ProductManager extends JPanel {

    private ArrayList<ModelProducts> list = new ArrayList<>();
    private ArrayList<NguyenLieu> danhSachNguyenLieu = new ArrayList<>();
    private List<String> danhSachDonVi = new ArrayList<>();
    private ModelProducts products;
    private int index = -1;

    private Form_Home form_Home;
    private JLabel jLabel2, jLabel3, jLabel4, jLabel6, jLabel7, lblNguyenLieu;
    private Button JbtSua, JbtThem, JbtXoa, JbtLuu, JbtHuy, btnThemNguyenLieu, btnBrowse;
    private JTable jTable1, tableNguyenLieu;
    private JScrollPane jScrollPane1, scrollPaneNguyenLieu;
    private int check = 0; // 0: bình thường, 1: thêm, -1: sửa
    private MyTextField txtname, txtprice, txtmota, txtpath, txtfind;
    private JComboBox<String> txtcategory;
    private Service service;

    public ProductManager() {
        // Thiết lập giao diện chính
        setSize(1240, 842);
        setBackground(new Color(62, 167, 136));
        setLayout(new BorderLayout());

        // Tải danh sách nguyên liệu và đơn vị từ CSDL
        loadDanhSachNguyenLieu();
        loadDanhSachDonVi();

        // Tạo panel bên trái với MigLayout
        JPanel leftPanel = new JPanel(new MigLayout("fill", "[grow, fill]", "[]10[]10[]10[]10[]10[]10[]10[]10[]"));
        leftPanel.setBackground(new Color(206, 157, 255));

        // Khởi tạo các thành phần giao diện
        jLabel2 = new JLabel("Tên:");
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setPreferredSize(new Dimension(100, 30));

        txtname = new MyTextField();
        txtname.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/product.png")));
        txtname.setHint("Name");
        txtname.setEditable(false);
        txtname.setPreferredSize(new Dimension(200, 30));

        jLabel3 = new JLabel("Path:");
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel3.setPreferredSize(new Dimension(100, 30));

        txtpath = new MyTextField();
        txtpath.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/image.png")));
        txtpath.setHint("Image Path");
        txtpath.setEditable(false);
        txtpath.setPreferredSize(new Dimension(200, 30));
        txtpath.setMinimumSize(new Dimension(200, 30));
        txtpath.setMaximumSize(new Dimension(200, 30));

        btnBrowse = new Button();
        btnBrowse.setPreferredSize(new Dimension(100, 30));
        btnBrowse.setBackground(new Color(210, 30, 179));
        btnBrowse.setForeground(new Color(255, 255, 255));
        btnBrowse.setText("Browse");
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 18));

        jLabel4 = new JLabel("Giá:");
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel4.setPreferredSize(new Dimension(100, 30));

        txtprice = new MyTextField();
        txtprice.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/price.png")));
        txtprice.setHint("Price");
        txtprice.setEditable(false);
        txtprice.setPreferredSize(new Dimension(200, 30));

        jLabel6 = new JLabel("Danh mục:");
        jLabel6.setForeground(new Color(255, 255, 255));
        jLabel6.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel6.setPreferredSize(new Dimension(100, 30));

        String[] dishCategories = {"Món chính", "Món khai vị", "Món tráng miệng", "Đồ uống", "Khác"};
        txtcategory = new JComboBox<>(dishCategories);
        txtcategory.setEnabled(false);
        txtcategory.setPreferredSize(new Dimension(200, 30));
        txtcategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtcategory.setToolTipText("Chọn danh mục món ăn");

        jLabel7 = new JLabel("Mô tả:");
        jLabel7.setForeground(new Color(255, 255, 255));
        jLabel7.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel7.setPreferredSize(new Dimension(100, 30));

        txtmota = new MyTextField();
        txtmota.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/description.png")));
        txtmota.setHint("Description");
        txtmota.setEditable(false);
        txtmota.setPreferredSize(new Dimension(200, 30));

        txtfind = new MyTextField();
        txtfind.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));
        txtfind.setHint("Find");
        txtfind.setPreferredSize(new Dimension(300, 30));

        lblNguyenLieu = new JLabel("Công thức nguyên liệu:");
        lblNguyenLieu.setForeground(new Color(255, 255, 255));
        lblNguyenLieu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tạo bảng nguyên liệu với các cột mới
        String[] columnNames = {"Tên nguyên liệu", "Loại", "Số lượng", "Đơn vị"};
        DefaultTableModel tableModelNguyenLieu = new DefaultTableModel(columnNames, 0);
        tableNguyenLieu = new JTable(tableModelNguyenLieu);

        // Tùy chỉnh giao diện bảng nguyên liệu
        tableNguyenLieu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableNguyenLieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableNguyenLieu.setRowHeight(30);
        tableNguyenLieu.getColumnModel().getColumn(0).setPreferredWidth(150); // Tên nguyên liệu
        tableNguyenLieu.getColumnModel().getColumn(1).setPreferredWidth(100); // Loại
        tableNguyenLieu.getColumnModel().getColumn(2).setPreferredWidth(80);  // Số lượng
        tableNguyenLieu.getColumnModel().getColumn(3).setPreferredWidth(80);  // Đơn vị

        // Định dạng cột Số lượng hiển thị 2 chữ số thập phân
        DecimalFormat df = new DecimalFormat("#.##");
        tableNguyenLieu.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Number) {
                    value = df.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        scrollPaneNguyenLieu = new JScrollPane(tableNguyenLieu);
        scrollPaneNguyenLieu.setPreferredSize(new Dimension(400, 150));

        // Cell editor autocomplete cho cột Tên nguyên liệu
        List<String> tenNguyenLieu = danhSachNguyenLieu.stream().map(NguyenLieu::getTen).collect(Collectors.toList());
        tableNguyenLieu.getColumnModel().getColumn(0).setCellEditor(new AutoCompleteCellEditor(tenNguyenLieu));

        // Cell editor combobox cho cột Đơn vị
        JComboBox<String> comboDonVi = new JComboBox<>(danhSachDonVi.toArray(new String[0]));
        tableNguyenLieu.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboDonVi));

        btnThemNguyenLieu = new Button();
        btnThemNguyenLieu.setPreferredSize(new Dimension(150, 30));
        btnThemNguyenLieu.setBackground(new Color(210, 30, 179));
        btnThemNguyenLieu.setForeground(new Color(255, 255, 255));
        btnThemNguyenLieu.setText("+ Thêm nguyên liệu");
        btnThemNguyenLieu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThemNguyenLieu.setEnabled(false);
        btnThemNguyenLieu.addActionListener(e -> tableModelNguyenLieu.addRow(new Object[]{"", "", 0, ""}));

        // Khởi tạo các nút chức năng
        JbtSua = new Button();
        JbtSua.setPreferredSize(new Dimension(100, 30));
        JbtSua.setBackground(new Color(210, 30, 179));
        JbtSua.setForeground(new Color(255, 255, 255));
        JbtSua.setText("Sửa");
        JbtSua.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JbtThem = new Button();
        JbtThem.setPreferredSize(new Dimension(100, 30));
        JbtThem.setBackground(new Color(210, 30, 179));
        JbtThem.setForeground(new Color(255, 255, 255));
        JbtThem.setText("Thêm");
        JbtThem.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JbtXoa = new Button();
        JbtXoa.setPreferredSize(new Dimension(100, 30));
        JbtXoa.setBackground(new Color(210, 30, 179));
        JbtXoa.setForeground(new Color(255, 255, 255));
        JbtXoa.setText("Xóa");
        JbtXoa.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JbtLuu = new Button();
        JbtLuu.setPreferredSize(new Dimension(100, 30));
        JbtLuu.setBackground(new Color(210, 30, 179));
        JbtLuu.setForeground(new Color(255, 255, 255));
        JbtLuu.setText("Lưu");
        JbtLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JbtLuu.setVisible(false);

        JbtHuy = new Button();
        JbtHuy.setPreferredSize(new Dimension(100, 30));
        JbtHuy.setBackground(new Color(210, 30, 179));
        JbtHuy.setForeground(new Color(255, 255, 255));
        JbtHuy.setText("Hủy bỏ");
        JbtHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JbtHuy.setVisible(false);

        // Thêm các thành phần vào leftPanel
        leftPanel.setPreferredSize(new Dimension(500, 842));
        leftPanel.add(jLabel2, "cell 0 0");
        leftPanel.add(txtname, "cell 1 0");
        leftPanel.add(jLabel3, "cell 0 1");
        JPanel imagePanel = new JPanel(new MigLayout("fill", "[grow,fill][fill]", ""));
        imagePanel.add(txtpath, "grow");
        imagePanel.add(btnBrowse, "wrap");
        leftPanel.add(imagePanel, "cell 1 1");
        leftPanel.add(jLabel4, "cell 0 2");
        leftPanel.add(txtprice, "cell 1 2");
        leftPanel.add(jLabel6, "cell 0 3");
        leftPanel.add(txtcategory, "cell 1 3");
        leftPanel.add(jLabel7, "cell 0 4");
        leftPanel.add(txtmota, "cell 1 4");
        leftPanel.add(lblNguyenLieu, "cell 0 5, span 2");
        leftPanel.add(scrollPaneNguyenLieu, "cell 0 6, span 2");
        leftPanel.add(btnThemNguyenLieu, "cell 0 7");
        leftPanel.add(txtfind, "cell 0 8, span 2");
        leftPanel.add(JbtSua, "cell 0 9");
        leftPanel.add(JbtThem, "cell 1 9");
        leftPanel.add(JbtXoa, "cell 2 9");
        leftPanel.add(JbtLuu, "cell 0 10");
        leftPanel.add(JbtHuy, "cell 1 10");

        // Khởi tạo bảng JTable sản phẩm
        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT", "ID", "Tên", "Giá", "Danh mục", "Mô tả", "Path"}
        ));
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 26));
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        jTable1.setRowHeight(75);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(75);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new ImageRenderer());
        jTable1.setForeground(Color.BLACK);
        jTable1.setSelectionBackground(new Color(22, 216, 160));
        jTable1.setSelectionForeground(Color.WHITE);

        jScrollPane1 = new JScrollPane(jTable1);
        jScrollPane1.setPreferredSize(new Dimension(1000, 500));
        add(leftPanel, BorderLayout.WEST);
        add(jScrollPane1, BorderLayout.CENTER);

        // Sự kiện nút Sửa
        JbtSua.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (index >= 0) {
                    check = -1;
                    txtname.setEditable(true);
                    txtpath.setEditable(true);
                    txtprice.setEditable(true);
                    txtcategory.setEnabled(true);
                    txtmota.setEditable(true);
                    btnThemNguyenLieu.setEnabled(true);
                    loadNguyenLieuForProduct(list.get(index).getId());
                    onOff(false, true);
                }
            }
        });

        // Sự kiện nút Thêm
        JbtThem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtname.setEditable(true);
                txtprice.setEditable(true);
                txtpath.setEditable(true);
                txtcategory.setEnabled(true);
                txtmota.setEditable(true);
                btnThemNguyenLieu.setEnabled(true);
                txtname.setText("");
                txtprice.setText("");
                txtpath.setText("");
                txtcategory.setSelectedIndex(-1);
                txtmota.setText("");
                ((DefaultTableModel) tableNguyenLieu.getModel()).setRowCount(0);
                onOff(false, true);
                check = 1;
            }
        });

        // Sự kiện nút Xóa
        JbtXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int confirm = JOptionPane.showConfirmDialog(
                        JbtXoa,
                        "Bạn có chắc chắn muốn xóa sản phẩm này?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION && index >= 0 && index < list.size()) {
                    ModelProducts productToRemove = list.get(index);

                    try (Connection conn = JDBCuntil.getconection()) {
                        if (conn == null) {
                            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        // Xóa nguyên liệu của sản phẩm
                        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM congthuc WHERE MaSanPham = ?")) {
                            pstmt.setInt(1, productToRemove.getId());
                            pstmt.executeUpdate();
                        }
                        // Xóa sản phẩm
                        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM sanpham WHERE MaSanPham = ?")) {
                            pstmt.setInt(1, productToRemove.getId());
                            pstmt.executeUpdate();
                        }

                        list.remove(index);
                        if (index >= list.size()) {
                            index = list.size() - 1;
                        }
                        view();
                        viewtable();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
                service.getInstance().getMain().getBody().getForm_Home().reloadProducts();
            }
        });

        // Sự kiện nút Lưu
        JbtLuu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    String ten = txtname.getText().trim();
                    String priceText = txtprice.getText().trim();
                    String danhmuc = (String) txtcategory.getSelectedItem();
                    String mota = txtmota.getText().trim();
                    String path = txtpath.getText().trim();

                    // Kiểm tra các trường bắt buộc
                    if (ten.isEmpty() || priceText.isEmpty() || danhmuc == null || mota.isEmpty() || path.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng số
                    double gia;
                    try {
                        gia = Double.parseDouble(priceText);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra giá trị hợp lệ
                    if (gia <= 0) {
                        JOptionPane.showMessageDialog(null, "Giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra nguyên liệu
                    DefaultTableModel modelNguyenLieu = (DefaultTableModel) tableNguyenLieu.getModel();
                    if (modelNguyenLieu.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một nguyên liệu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try (Connection conn = JDBCuntil.getconection()) {
                        if (conn == null) {
                            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        int productId;
                        if (check == 1) { // Thêm mới
                            String sql = "INSERT INTO sanpham (TenSanPham, LoaiSanPham, Gia, MoTa, Path) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                                pstmt.setString(1, ten);
                                pstmt.setString(2, danhmuc);
                                pstmt.setDouble(3, gia);
                                pstmt.setString(4, mota);
                                pstmt.setString(5, path);
                                pstmt.executeUpdate();

                                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                                    if (rs.next()) {
                                        productId = rs.getInt(1);
                                    } else {
                                        throw new SQLException("Không lấy được ID sản phẩm mới.");
                                    }
                                }
                                list.add(new ModelProducts(productId, ten, danhmuc, gia, mota, path));
                            }
                        } else if (check == -1 && index >= 0 && index < list.size()) { // Sửa
                            productId = list.get(index).getId();
                            String sql = "UPDATE sanpham SET TenSanPham = ?, LoaiSanPham = ?, Gia = ?, MoTa = ?, Path = ? WHERE MaSanPham = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                pstmt.setString(1, ten);
                                pstmt.setString(2, danhmuc);
                                pstmt.setDouble(3, gia);
                                pstmt.setString(4, mota);
                                pstmt.setString(5, path);
                                pstmt.setInt(6, productId);
                                pstmt.executeUpdate();
                            }
                            list.set(index, new ModelProducts(productId, ten, danhmuc, gia, mota, path));
                        } else {
                            return;
                        }

                        // Xóa công thức cũ
                        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM congthuc WHERE MaSanPham = ?")) {
                            pstmt.setInt(1, productId);
                            pstmt.executeUpdate();
                        }

                        // Lưu công thức mới
                        String sqlCongThuc = "INSERT INTO congthuc (MaSanPham, NguyenLieuID, SoLuong, DonVi) VALUES (?, ?, ?, ?)";
                        for (int i = 0; i < modelNguyenLieu.getRowCount(); i++) {
                            String tenNguyenLieu = (String) modelNguyenLieu.getValueAt(i, 0);
                            Object soLuongObj = modelNguyenLieu.getValueAt(i, 2);
                            String donVi = (String) modelNguyenLieu.getValueAt(i, 3);

                            if (tenNguyenLieu == null || tenNguyenLieu.isEmpty() || soLuongObj == null || donVi == null || donVi.isEmpty()) {
                                continue;
                            }

                            double soLuong;
                            try {
                                soLuong = Double.parseDouble(soLuongObj.toString());
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Số lượng nguyên liệu phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (soLuong <= 0) {
                                JOptionPane.showMessageDialog(null, "Số lượng nguyên liệu phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Tìm MaNguyenLieu
                            int maNguyenLieu = -1;
                            for (NguyenLieu nl : danhSachNguyenLieu) {
                                if (nl.getTen().equals(tenNguyenLieu)) {
                                    maNguyenLieu = nl.getId();
                                    break;
                                }
                            }

                            if (maNguyenLieu == -1) {
                                JOptionPane.showMessageDialog(null, "Nguyên liệu '" + tenNguyenLieu + "' không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Kiểm tra độ dài DonVi (VARCHAR(20))
                            if (donVi.length() > 20) {
                                donVi = donVi.substring(0, 20);
                                JOptionPane.showMessageDialog(null, "Đơn vị '" + donVi + "' đã bị cắt ngắn vì vượt quá 20 ký tự!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                            }

                            try (PreparedStatement pstmt = conn.prepareStatement(sqlCongThuc)) {
                                pstmt.setInt(1, productId); // MaSanPham
                                pstmt.setInt(2, maNguyenLieu); // MaNguyenLieu
                                pstmt.setDouble(3, soLuong); // SoLuong
                                pstmt.setString(4, donVi); // DonVi
                                pstmt.executeUpdate();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, "Lỗi khi lưu công thức: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                                e.printStackTrace();
                                return;
                            }
                        }

                    }

                    viewtable();
                    txtname.setEditable(false);
                    txtprice.setEditable(false);
                    txtpath.setEditable(false);
                    txtcategory.setEnabled(false);
                    txtmota.setEditable(false);
                    btnThemNguyenLieu.setEnabled(false);
                    check = 0;

                    service.getInstance().getMain().getBody().getForm_Home().reloadProducts();

                    JOptionPane.showMessageDialog(null, "Lưu sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thao tác với cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                onOff(true, false);
            }
        });

        // Sự kiện nút Hủy
        JbtHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onOff(true, false);
                txtname.setEditable(false);
                txtprice.setEditable(false);
                txtpath.setEditable(false);
                txtcategory.setEnabled(false);
                txtmota.setEditable(false);
                btnThemNguyenLieu.setEnabled(false);
                ((DefaultTableModel) tableNguyenLieu.getModel()).setRowCount(0);
                view();
                service.getInstance().getMain().getBody().getForm_Home().reloadProducts();
            }
        });

        // Sự kiện chọn dòng trong bảng
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                index = jTable1.getSelectedRow();
                view();
            }
        });

        // Sự kiện tìm kiếm
        txtfind.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterProduct();
            }
        });

        // Sự kiện nút Browse
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String absolutePath = selectedFile.getAbsolutePath();
                    absolutePath = absolutePath.replace("\\", "/");
                    txtpath.setText(absolutePath);
                }
            }
        });

        loadProduct();
        viewtable();
    }

    private void loadDanhSachNguyenLieu() {
        danhSachNguyenLieu.clear();
        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement("SELECT * FROM nguyenlieu"); ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    NguyenLieu nl = new NguyenLieu(
                            rs.getInt("id"), // Sửa từ MaNguyenLieu thành id
                            rs.getString("ten"), // Sửa từ TenNguyenLieu thành ten
                            rs.getString("loai"),
                            rs.getString("donvi"),
                            rs.getDouble("soluong"),
                            rs.getDate("hansudung"),
                            rs.getDate("ngaynhap"),
                            rs.getDouble("gianhap"),
                            rs.getString("nhacungcap"),
                            rs.getString("anh")
                    );
                    danhSachNguyenLieu.add(nl);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách nguyên liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Tải danh sách đơn vị từ CSDL
    private void loadDanhSachDonVi() {
        danhSachDonVi.clear();
        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement("SELECT DISTINCT DonVi FROM nguyenlieu"); ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String donVi = rs.getString("DonVi");
                    if (donVi != null && !donVi.isEmpty()) {
                        danhSachDonVi.add(donVi);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách đơn vị: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Tải nguyên liệu của sản phẩm
    private void loadNguyenLieuForProduct(int maSanPham) {
        DefaultTableModel model = (DefaultTableModel) tableNguyenLieu.getModel();
        model.setRowCount(0);

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement(
                    "SELECT snl.SoLuong, snl.DonVi, nl.ten, nl.loai "
                    + "FROM congthuc snl "
                    + "JOIN nguyenlieu nl ON snl.id = nl.id "
                    + "WHERE snl.MaSanPham = ?")) {
                st.setInt(1, maSanPham);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("ten"),
                            rs.getString("loai"),
                            rs.getDouble("SoLuong"),
                            rs.getString("DonVi")
                        });
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải nguyên liệu của sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Tải danh sách sản phẩm từ cơ sở dữ liệu
    public void loadProduct() {
        list.clear();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement("SELECT MaSanPham, TenSanPham, LoaiSanPham, Gia, MoTa, Path FROM sanpham"); ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("MaSanPham");
                    String ten = rs.getString("TenSanPham");
                    String danhmuc = rs.getString("LoaiSanPham");
                    double gia = rs.getDouble("Gia");
                    String mota = rs.getString("MoTa");
                    String path = rs.getString("Path");
                    list.add(new ModelProducts(id, ten, danhmuc, gia, mota, path));
                    model.addRow(new Object[]{list.size(), id, ten, gia, danhmuc, mota, path});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Lọc sản phẩm theo từ khóa tìm kiếm
    private void filterProduct() {
        String searchTerm = txtfind.getText().toLowerCase().trim();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement st = con.prepareStatement("SELECT MaSanPham, TenSanPham, LoaiSanPham, Gia, MoTa, Path FROM sanpham WHERE TenSanPham LIKE ?")) {
                st.setString(1, "%" + searchTerm + "%");
                try (ResultSet rs = st.executeQuery()) {
                    int stt = 1;
                    while (rs.next()) {
                        int id = rs.getInt("MaSanPham");
                        String ten = rs.getString("TenSanPham");
                        String danhmuc = rs.getString("LoaiSanPham");
                        double gia = rs.getDouble("Gia");
                        String mota = rs.getString("MoTa");
                        String path = rs.getString("Path");
                        model.addRow(new Object[]{stt++, id, ten, gia, danhmuc, mota, path});
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Cập nhật bảng hiển thị
    public void viewtable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (ModelProducts product : list) {
            model.addRow(new Object[]{
                stt++,
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getMoTa(),
                product.getPath()
            });
        }
    }

    // Hiển thị thông tin sản phẩm được chọn
    public void view() {
        if (index < 0 || index >= list.size()) {
            txtname.setText("");
            txtprice.setText("");
            txtcategory.setSelectedIndex(-1);
            txtmota.setText("");
            txtpath.setText("");
            ((DefaultTableModel) tableNguyenLieu.getModel()).setRowCount(0);
            return;
        }

        products = list.get(index);
        txtname.setText(products.getName() != null ? products.getName() : "");
        txtprice.setText(products.getPrice() > 0 ? String.valueOf(products.getPrice()) : "");
        txtmota.setText(products.getMoTa() != null ? products.getMoTa() : "");
        txtpath.setText(products.getPath() != null ? products.getPath() : "");

        // Xử lý danh mục
        String category = products.getCategory();
        if (category == null || category.trim().isEmpty()) {
            txtcategory.setSelectedItem("Khác");
        } else {
            txtcategory.setSelectedItem(category);
            if (txtcategory.getSelectedIndex() == -1) {
                txtcategory.setSelectedItem("Khác");
                JOptionPane.showMessageDialog(null, "Danh mục '" + category + "' không tồn tại trong danh sách, đã đặt thành 'Khác'.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        }

        loadNguyenLieuForProduct(products.getId());
        onOff(true, false);
    }

    // Bật/tắt các nút chức năng
    public void onOff(boolean a, boolean b) {
        JbtSua.setVisible(a);
        JbtThem.setVisible(a);
        JbtXoa.setVisible(a);
        JbtLuu.setVisible(b);
        JbtHuy.setVisible(b);
    }

    // Cell editor dùng combobox autocomplete custom
    private static class AutoCompleteCellEditor extends AbstractCellEditor implements TableCellEditor {

        private final AutoCompleteComboBox comboBox;

        public AutoCompleteCellEditor(List<String> items) {
            comboBox = new AutoCompleteComboBox(items);
        }

        @Override
        public Object getCellEditorValue() {
            return comboBox.getEditor().getItem();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            comboBox.setSelectedItem(value);
            return comboBox;
        }
    }

    // JComboBox autocomplete tìm kiếm contains
    private static class AutoCompleteComboBox extends JComboBox<String> {

        private final List<String> items;
        private boolean isAdjusting = false;

        public AutoCompleteComboBox(List<String> items) {
            super(new Vector<>(items));
            this.items = items;
            setEditable(true);

            JTextField editor = (JTextField) getEditor().getEditorComponent();

            editor.getDocument().addDocumentListener(new DocumentListener() {
                private void updateList() {
                    if (isAdjusting) {
                        return;
                    }
                    SwingUtilities.invokeLater(() -> {
                        String text = editor.getText();
                        isAdjusting = true;

                        int pos = editor.getCaretPosition();

                        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) getModel();
                        model.removeAllElements();

                        if (text.isEmpty()) {
                            items.forEach(model::addElement);
                            hidePopup();
                        } else {
                            List<String> filtered = items.stream()
                                    .filter(i -> i.toLowerCase().contains(text.toLowerCase()))
                                    .collect(Collectors.toList());

                            if (filtered.isEmpty()) {
                                hidePopup();
                            } else {
                                filtered.forEach(model::addElement);
                                setPopupVisible(true);
                            }
                        }

                        editor.setText(text);
                        editor.setCaretPosition(Math.min(pos, editor.getText().length()));

                        isAdjusting = false;
                    });
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateList();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateList();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateList();
                }
            });

            addActionListener(e -> {
                if (!isAdjusting) {
                    Object selected = getSelectedItem();
                    if (selected != null) {
                        editor.setText(selected.toString());
                    }
                }
            });
        }
    }

    // Điểm khởi chạy chương trình
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Sản Phẩm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1240, 842);
            frame.setResizable(false);
            frame.getContentPane().add(new ProductManager());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
