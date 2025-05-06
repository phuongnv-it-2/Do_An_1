package view.page;


import Database.JDBCuntil;
import Service.ImageRenderer;
import Swing.Button;
import Swing.MyTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.NguyenLieu;
import net.miginfocom.swing.MigLayout;

public class AddMaterial extends JPanel {

    private ArrayList<NguyenLieu> list = new ArrayList<>();
    private NguyenLieu material;
    private int index = -1;

    private JLabel jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, jLabel10;
    private Button JbtSua, JbtThem, JbtXoa, JbtLuu, JbtHuy;
    private JTable jTable1;
    private JScrollPane jScrollPane1;
    private int check = 0; // 0: bình thường, 1: thêm, -1: sửa
    private Button btnBrowse;
    private MyTextField txtName, txtLoai, txtDonVi, txtSoLuong, txtHanSuDung, txtNgayNhap, txtGiaNhap, txtNhaCungCap, txtAnh, txtFind;

    public AddMaterial() {
        setSize(1240, 842);
        setBackground(new Color(62, 167, 136));
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new MigLayout("fill", "[grow, fill]", "[]10[]10[]10[]10[]10[]10[]10[]10[]10[]"));
        leftPanel.setBackground(new Color(62, 167, 136));

        jLabel2 = new JLabel("Tên:");
        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setPreferredSize(new Dimension(100, 30));

        txtName = new MyTextField();
        txtName.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/product.png")));
        txtName.setHint("Name");
        txtName.setEditable(false);
        txtName.setPreferredSize(new Dimension(200, 30));

        jLabel3 = new JLabel("Loại:");
        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel3.setPreferredSize(new Dimension(100, 30));

        txtLoai = new MyTextField();
        txtLoai.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/category.png")));
        txtLoai.setHint("Loại");
        txtLoai.setEditable(false);
        txtLoai.setPreferredSize(new Dimension(200, 30));

        jLabel4 = new JLabel("Đơn vị:");
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel4.setPreferredSize(new Dimension(100, 30));

        txtDonVi = new MyTextField();
        txtDonVi.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/unit.png")));
        txtDonVi.setHint("Đơn vị");
        txtDonVi.setEditable(false);
        txtDonVi.setPreferredSize(new Dimension(200, 30));

        jLabel5 = new JLabel("Số lượng:");
        jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel5.setPreferredSize(new Dimension(100, 30));

        txtSoLuong = new MyTextField();
        txtSoLuong.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/quantity.png")));
        txtSoLuong.setHint("Số lượng");
        txtSoLuong.setEditable(false);
        txtSoLuong.setPreferredSize(new Dimension(200, 30));

        jLabel6 = new JLabel("Hạn sử dụng:");
        jLabel6.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel6.setPreferredSize(new Dimension(100, 30));

        txtHanSuDung = new MyTextField();
        txtHanSuDung.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/date.png")));
        txtHanSuDung.setHint("Hạn sử dụng (yyyy-mm-dd)");
        txtHanSuDung.setEditable(false);
        txtHanSuDung.setPreferredSize(new Dimension(200, 30));

        jLabel7 = new JLabel("Ngày nhập:");
        jLabel7.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel7.setPreferredSize(new Dimension(100, 30));

        txtNgayNhap = new MyTextField();
        txtNgayNhap.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/date.png")));
        txtNgayNhap.setHint("Ngày nhập (yyyy-mm-dd)");
        txtNgayNhap.setEditable(false);
        txtNgayNhap.setPreferredSize(new Dimension(200, 30));

        jLabel8 = new JLabel("Giá nhập:");
        jLabel8.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel8.setPreferredSize(new Dimension(100, 30));

        txtGiaNhap = new MyTextField();
        txtGiaNhap.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/price.png")));
        txtGiaNhap.setHint("Giá nhập");
        txtGiaNhap.setEditable(false);
        txtGiaNhap.setPreferredSize(new Dimension(200, 30));

        jLabel9 = new JLabel("Nhà cung cấp:");
        jLabel9.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel9.setPreferredSize(new Dimension(100, 30));

        txtNhaCungCap = new MyTextField();
        txtNhaCungCap.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/supplier.png")));
        txtNhaCungCap.setHint("Nhà cung cấp");
        txtNhaCungCap.setEditable(false);
        txtNhaCungCap.setPreferredSize(new Dimension(200, 30));

        jLabel10 = new JLabel("Ảnh:");
        jLabel10.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel10.setPreferredSize(new Dimension(100, 30));

        txtAnh = new MyTextField();
        txtAnh.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/image.png")));
        txtAnh.setHint("Đường dẫn ảnh");
        txtAnh.setEditable(false);
        txtAnh.setPreferredSize(new Dimension(200, 30));

        btnBrowse = new Button();
        btnBrowse.setPreferredSize(new Dimension(100, 30));
        btnBrowse.setBackground(new Color(22, 216, 160));
        btnBrowse.setForeground(new Color(0, 0, 0));
        btnBrowse.setText("Browse");
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 18));

        txtFind = new MyTextField();
        txtFind.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));
        txtFind.setHint("Tìm kiếm");
        txtFind.setPreferredSize(new Dimension(300, 30));

        JbtSua = new Button();
        JbtSua.setPreferredSize(new Dimension(100, 30));
        JbtSua.setBackground(new Color(22, 216, 160));
        JbtSua.setForeground(new Color(0, 0, 0));
        JbtSua.setText("Sửa");
        JbtSua.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JbtThem = new Button();
        JbtThem.setPreferredSize(new Dimension(100, 30));
        JbtThem.setBackground(new Color(22, 216, 160));
        JbtThem.setForeground(new Color(0, 0, 0));
        JbtThem.setText("Thêm");
        JbtThem.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JbtXoa = new Button();
        JbtXoa.setPreferredSize(new Dimension(100, 30));
        JbtXoa.setBackground(new Color(22, 216, 160));
        JbtXoa.setForeground(new Color(0, 0, 0));
        JbtXoa.setText("Xóa");
        JbtXoa.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JbtLuu = new Button();
        JbtLuu.setPreferredSize(new Dimension(100, 30));
        JbtLuu.setBackground(new Color(22, 216, 160));
        JbtLuu.setForeground(new Color(0, 0, 0));
        JbtLuu.setText("Lưu");
        JbtLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JbtHuy = new Button();
        JbtHuy.setPreferredSize(new Dimension(100, 30));
        JbtHuy.setBackground(new Color(22, 216, 160));
        JbtHuy.setForeground(new Color(0, 0, 0));
        JbtHuy.setText("Hủy bỏ");
        JbtHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));

        leftPanel.setPreferredSize(new Dimension(450, 842));
        leftPanel.add(jLabel2, "cell 0 0");
        leftPanel.add(txtName, "cell 1 0");

        leftPanel.add(jLabel3, "cell 0 1");
        leftPanel.add(txtLoai, "cell 1 1");

        leftPanel.add(jLabel4, "cell 0 2");
        leftPanel.add(txtDonVi, "cell 1 2");

        leftPanel.add(jLabel5, "cell 0 3");
        leftPanel.add(txtSoLuong, "cell 1 3");

        leftPanel.add(jLabel6, "cell 0 4");
        leftPanel.add(txtHanSuDung, "cell 1 4");

        leftPanel.add(jLabel7, "cell 0 5");
        leftPanel.add(txtNgayNhap, "cell 1 5");

        leftPanel.add(jLabel8, "cell 0 6");
        leftPanel.add(txtGiaNhap, "cell 1 6");

        leftPanel.add(jLabel9, "cell 0 7");
        leftPanel.add(txtNhaCungCap, "cell 1 7");

        leftPanel.add(jLabel10, "cell 0 8");
        JPanel imagePanel = new JPanel(new MigLayout("fill", "[grow,fill][fill]", ""));
        imagePanel.add(txtAnh, "grow");
        imagePanel.add(btnBrowse, "wrap");
        leftPanel.add(imagePanel, "cell 1 8");

        leftPanel.add(txtFind, "cell 0 9, span 2");

        leftPanel.add(JbtSua, "cell 0 10");
        leftPanel.add(JbtThem, "cell 1 10");
        leftPanel.add(JbtXoa, "cell 2 10");

        leftPanel.add(JbtLuu, "cell 0 11");
        leftPanel.add(JbtHuy, "cell 1 11");

        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"STT", "ID", "Tên", "Loại", "Đơn vị", "Số lượng", "Hạn sử dụng", "Ngày nhập", "Giá nhập", "Nhà cung cấp", "Ảnh"}
        ));
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTable1.setRowHeight(75);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(10).setCellRenderer(new ImageRenderer());
        jTable1.setForeground(Color.BLACK);
        jTable1.setSelectionBackground(new Color(22, 216, 160));
        jTable1.setSelectionForeground(Color.WHITE);

        jScrollPane1 = new JScrollPane(jTable1);
        jScrollPane1.setPreferredSize(new Dimension(1000, 500));
        add(leftPanel, BorderLayout.WEST);
        add(jScrollPane1, BorderLayout.CENTER);

        JbtSua.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (index >= 0) {
                    check = -1;
                    txtName.setEditable(true);
                    txtLoai.setEditable(true);
                    txtDonVi.setEditable(true);
                    txtSoLuong.setEditable(true);
                    txtHanSuDung.setEditable(true);
                    txtNgayNhap.setEditable(true);
                    txtGiaNhap.setEditable(true);
                    txtNhaCungCap.setEditable(true);
                    txtAnh.setEditable(true);
                    onOff(false, true);
                }
            }
        });

        JbtThem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtName.setEditable(true);
                txtLoai.setEditable(true);
                txtDonVi.setEditable(true);
                txtSoLuong.setEditable(true);
                txtHanSuDung.setEditable(true);
                txtNgayNhap.setEditable(true);
                txtGiaNhap.setEditable(true);
                txtNhaCungCap.setEditable(true);
                txtAnh.setEditable(true);
                txtName.setText("");
                txtLoai.setText("");
                txtDonVi.setText("");
                txtSoLuong.setText("");
                txtHanSuDung.setText("");
                txtNgayNhap.setText("");
                txtGiaNhap.setText("");
                txtNhaCungCap.setText("");
                txtAnh.setText("");
                onOff(false, true);
                check = 1;
            }
        });

        JbtXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int confirm = JOptionPane.showConfirmDialog(
                    JbtXoa,
                    "Are you sure you want to remove?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION && index >= 0 && index < list.size()) {
                    NguyenLieu materialToRemove = list.get(index);

                    try {
                        Connection conn = JDBCuntil.getconection();
                        String sql = "DELETE FROM nguyenlieu WHERE id = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, materialToRemove.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.close();

                        list.remove(index);
                        if (index >= list.size()) {
                            index = list.size() - 1;
                        }
                        view();
                        viewtable();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa nguyên liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        JbtLuu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    String ten = txtName.getText().trim();
                    String loai = txtLoai.getText().trim();
                    String donVi = txtDonVi.getText().trim();
                    String soLuongText = txtSoLuong.getText().trim();
                    String hanSuDungText = txtHanSuDung.getText().trim();
                    String ngayNhapText = txtNgayNhap.getText().trim();
                    String giaNhapText = txtGiaNhap.getText().trim();
                    String nhaCungCap = txtNhaCungCap.getText().trim();
                    String anh = txtAnh.getText().trim();

                    if (ten.isEmpty() || loai.isEmpty() || donVi.isEmpty() || soLuongText.isEmpty() || 
                        hanSuDungText.isEmpty() || ngayNhapText.isEmpty() || giaNhapText.isEmpty() || 
                        nhaCungCap.isEmpty() || anh.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double soLuong = Double.parseDouble(soLuongText);
                    java.sql.Date hanSuDung = java.sql.Date.valueOf(hanSuDungText);
                    java.sql.Date ngayNhap = java.sql.Date.valueOf(ngayNhapText);
                    double giaNhap = Double.parseDouble(giaNhapText);

                    Connection conn = JDBCuntil.getconection();
                    PreparedStatement pstmt = null;

                    if (check == 1) {
                        String sql = "INSERT INTO nguyenlieu (ten, loai, donvi, soluong, hansudung, ngaynhap, gianhap, nhacungcap, anh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                        pstmt.setString(1, ten);
                        pstmt.setString(2, loai);
                        pstmt.setString(3, donVi);
                        pstmt.setDouble(4, soLuong);
                        pstmt.setDate(5, hanSuDung);
                        pstmt.setDate(6, ngayNhap);
                        pstmt.setDouble(7, giaNhap);
                        pstmt.setString(8, nhaCungCap);
                        pstmt.setString(9, anh);
                        pstmt.executeUpdate();

                        ResultSet rs = pstmt.getGeneratedKeys();
                        int newId = 0;
                        if (rs.next()) {
                            newId = rs.getInt(1);
                        }
                        list.add(new NguyenLieu(newId, ten, loai, donVi, soLuong, hanSuDung, ngayNhap, giaNhap, nhaCungCap, anh));
                    } else if (check == -1 && index >= 0 && index < list.size()) {
                        String sql = "UPDATE nguyenlieu SET ten = ?, loai = ?, donvi = ?, soluong = ?, hansudung = ?, ngaynhap = ?, gianhap = ?, nhacungcap = ?, anh = ? WHERE id = ?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, ten);
                        pstmt.setString(2, loai);
                        pstmt.setString(3, donVi);
                        pstmt.setDouble(4, soLuong);
                        pstmt.setDate(5, hanSuDung);
                        pstmt.setDate(6, ngayNhap);
                        pstmt.setDouble(7, giaNhap);
                        pstmt.setString(8, nhaCungCap);
                        pstmt.setString(9, anh);
                        pstmt.setInt(10, list.get(index).getId());
                        pstmt.executeUpdate();
                        list.set(index, new NguyenLieu(list.get(index).getId(), ten, loai, donVi, soLuong, hanSuDung, ngayNhap, giaNhap, nhaCungCap, anh));
                    }

                    if (pstmt != null) pstmt.close();
                    conn.close();
                    viewtable();
                    txtName.setEditable(false);
                    txtLoai.setEditable(false);
                    txtDonVi.setEditable(false);
                    txtSoLuong.setEditable(false);
                    txtHanSuDung.setEditable(false);
                    txtNgayNhap.setEditable(false);
                    txtGiaNhap.setEditable(false);
                    txtNhaCungCap.setEditable(false);
                    txtAnh.setEditable(false);
                    check = 0;

                    JOptionPane.showMessageDialog(null, "Lưu nguyên liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Số lượng và giá nhập phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Ngày phải có định dạng yyyy-mm-dd!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thao tác với cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                onOff(true, false);
            }
        });

        JbtHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onOff(true, false);
                txtName.setEditable(false);
                txtLoai.setEditable(false);
                txtDonVi.setEditable(false);
                txtSoLuong.setEditable(false);
                txtHanSuDung.setEditable(false);
                txtNgayNhap.setEditable(false);
                txtGiaNhap.setEditable(false);
                txtNhaCungCap.setEditable(false);
                txtAnh.setEditable(false);
                view();
            }
        });

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                index = jTable1.getSelectedRow();
                view();
            }
        });

        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterMaterial();
            }
        });

        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String absolutePath = selectedFile.getAbsolutePath();
                    absolutePath = absolutePath.replace("\\", "/");
                    txtAnh.setText(absolutePath);
                }
            }
        });

        loadMaterial();
        viewtable();
    }

    public void loadMaterial() {
        list.clear();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {
            Connection con = JDBCuntil.getconection();
            String sql = "SELECT id, ten, loai, donvi, soluong, hansudung, ngaynhap, gianhap, nhacungcap, anh FROM nguyenlieu";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String loai = rs.getString("loai");
                String donVi = rs.getString("donvi");
                double soLuong = rs.getDouble("soluong");
                java.sql.Date hanSuDung = rs.getDate("hansudung");
                java.sql.Date ngayNhap = rs.getDate("ngaynhap");
                double giaNhap = rs.getDouble("gianhap");
                String nhaCungCap = rs.getString("nhacungcap");
                String anh = rs.getString("anh");
                list.add(new NguyenLieu(id, ten, loai, donVi, soLuong, hanSuDung, ngayNhap, giaNhap, nhaCungCap, anh));
                model.addRow(new Object[]{list.size(), id, ten, loai, donVi, soLuong, hanSuDung, ngayNhap, giaNhap, nhaCungCap, anh});
            }
            JDBCuntil.closeconection(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void filterMaterial() {
        String searchTerm = txtFind.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {
            Connection con = JDBCuntil.getconection();
            String sql = "SELECT id, ten, loai, donvi, soluong, hansudung, ngaynhap, gianhap, nhacungcap, anh FROM nguyenlieu WHERE ten LIKE ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + searchTerm + "%");
            ResultSet rs = st.executeQuery();

            int stt = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String loai = rs.getString("loai");
                String donVi = rs.getString("donvi");
                double soLuong = rs.getDouble("soluong");
                java.sql.Date hanSuDung = rs.getDate("hansudung");
                java.sql.Date ngayNhap = rs.getDate("ngaynhap");
                double giaNhap = rs.getDouble("gianhap");
                String nhaCungCap = rs.getString("nhacungcap");
                String anh = rs.getString("anh");
                model.addRow(new Object[]{stt++, id, ten, loai, donVi, soLuong, hanSuDung, ngayNhap, giaNhap, nhaCungCap, anh});
            }
            JDBCuntil.closeconection(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void viewtable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        int stt = 1;
        for (NguyenLieu material : list) {
            model.addRow(new Object[]{
                stt++,
                material.getId(),
                material.getTen(),
                material.getLoai(),
                material.getDonVi(),
                material.getSoLuong(),
                material.getHanSuDung(),
                material.getNgayNhap(),
                material.getGiaNhap(),
                material.getNhaCungCap(),
                material.getAnh()
            });
        }
    }

    public void view() {
        if (index < 0 || index >= list.size()) {
            txtName.setText("");
            txtLoai.setText("");
            txtDonVi.setText("");
            txtSoLuong.setText("");
            txtHanSuDung.setText("");
            txtNgayNhap.setText("");
            txtGiaNhap.setText("");
            txtNhaCungCap.setText("");
            txtAnh.setText("");
            return;
        }

        material = list.get(index);
        txtName.setText(material.getTen());
        txtLoai.setText(material.getLoai());
        txtDonVi.setText(material.getDonVi());
        txtSoLuong.setText(String.valueOf(material.getSoLuong()));
        txtHanSuDung.setText(material.getHanSuDung() != null ? material.getHanSuDung().toString() : "");
        txtNgayNhap.setText(material.getNgayNhap() != null ? material.getNgayNhap().toString() : "");
        txtGiaNhap.setText(String.valueOf(material.getGiaNhap()));
        txtNhaCungCap.setText(material.getNhaCungCap());
        txtAnh.setText(material.getAnh());
        onOff(true, false);
    }

    public void onOff(boolean a, boolean b) {
        JbtSua.setVisible(a);
        JbtThem.setVisible(a);
        JbtXoa.setVisible(a);
        JbtLuu.setVisible(b);
        JbtHuy.setVisible(b);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Nguyên Liệu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1240, 842);
            frame.add(new AddMaterial());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}