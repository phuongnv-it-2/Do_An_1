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
import model.ModelProducts;
import net.miginfocom.swing.MigLayout;

public class AddProduct extends JPanel {

    private ArrayList<ModelProducts> list = new ArrayList<>();
    private ModelProducts products;
    private int index = -1; 

    private JLabel jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7;
    private Button JbtSua, JbtThem, JbtXoa, JbtLuu, JbtHuy;
    private JTable jTable1;
    private JScrollPane jScrollPane1;
    private int check = 0; // 0: bình thường, 1: thêm, -1: sửa
    private Button btnBrowse;
    private MyTextField txtname, txtprice, txtquantity, txtcategory, txtmota, txtpath, txtfind;

    public AddProduct() {
        // Thiết lập giao diện
        setSize(1240, 842);
        setBackground(new Color(62, 167, 136));
        setLayout(new BorderLayout());

        // Tạo JPanel cho khu vực bên trái với MigLayout
        JPanel leftPanel = new JPanel(new MigLayout("fill", "[grow, fill]", "[]10[]10[]10[]10[]10[]10[]10[]"));
        leftPanel.setBackground(new Color(62, 167, 136));

        // Khởi tạo các thành phần giao diện
        jLabel2 = new JLabel("Tên:");
        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setPreferredSize(new Dimension(100, 30));

        txtname = new MyTextField();
        txtname.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/product.png")));
        txtname.setHint("Name");
        txtname.setEditable(false);
        txtname.setPreferredSize(new Dimension(200, 30));

        jLabel3 = new JLabel("Path:");
        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel3.setPreferredSize(new Dimension(100, 30));

        txtpath = new MyTextField();
        txtpath.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/image.png")));
        txtpath.setHint("Image Path");
        txtpath.setEditable(false);
        txtpath.setPreferredSize(new Dimension(200, 30));

        btnBrowse = new Button();
        btnBrowse.setPreferredSize(new Dimension(100, 30));
        btnBrowse.setBackground(new Color(22, 216, 160));
        btnBrowse.setForeground(new Color(0, 0, 0));
        btnBrowse.setText("Browse");
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 18));

        jLabel4 = new JLabel("Giá:");
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel4.setPreferredSize(new Dimension(100, 30));

        txtprice = new MyTextField();
        txtprice.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/price.png")));
        txtprice.setHint("Price");
        txtprice.setEditable(false);
        txtprice.setPreferredSize(new Dimension(200, 30));

        jLabel5 = new JLabel("Số lượng:");
        jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel5.setPreferredSize(new Dimension(100, 30));

        txtquantity = new MyTextField();
        txtquantity.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/quantity.png")));
        txtquantity.setHint("Quantity");
        txtquantity.setEditable(false);
        txtquantity.setPreferredSize(new Dimension(200, 30));

        jLabel6 = new JLabel("Danh mục:");
        jLabel6.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel6.setPreferredSize(new Dimension(100, 30));

        txtcategory = new MyTextField();
        txtcategory.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/category.png")));
        txtcategory.setHint("Category");
        txtcategory.setEditable(false);
        txtcategory.setPreferredSize(new Dimension(200, 30));

        jLabel7 = new JLabel("Mô tả:");
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

        // Các nút chức năng
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

        // Thêm các thành phần vào leftPanel
        leftPanel.setPreferredSize(new Dimension(450, 842));

        leftPanel.add(jLabel2, "cell 0 0");
        leftPanel.add(txtname, "cell 1 0");

        leftPanel.add(jLabel3, "cell 0 1");
        JPanel imagePanel = new JPanel(new MigLayout("fill", "[grow,fill][fill]", ""));
        imagePanel.add(txtpath, "grow");
        imagePanel.add(btnBrowse, "wrap");
        leftPanel.add(imagePanel, "cell 1 1");

        leftPanel.add(jLabel4, "cell 0 2");
        leftPanel.add(txtprice, "cell 1 2");

        leftPanel.add(jLabel5, "cell 0 3");
        leftPanel.add(txtquantity, "cell 1 3");

        leftPanel.add(jLabel6, "cell 0 4");
        leftPanel.add(txtcategory, "cell 1 4");

        leftPanel.add(jLabel7, "cell 0 5");
        leftPanel.add(txtmota, "cell 1 5");

        leftPanel.add(txtfind, "cell 0 6, span 2");

        leftPanel.add(JbtSua, "cell 0 7");
        leftPanel.add(JbtThem, "cell 1 7");
        leftPanel.add(JbtXoa, "cell 2 7");

        leftPanel.add(JbtLuu, "cell 0 8");
        leftPanel.add(JbtHuy, "cell 1 8");

        // Khởi tạo bảng JTable
        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT", "ID", "Tên", "Giá", "Số lượng", "Danh mục", "Mô tả", "Path"}
        ));
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 26));
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        jTable1.setRowHeight(75);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(75);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(75);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new ImageRenderer());
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
                    txtquantity.setEditable(true);
                    txtcategory.setEditable(true);
                    txtmota.setEditable(true);
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
                txtquantity.setEditable(true);
                txtcategory.setEditable(true);
                txtmota.setEditable(true);
                txtname.setText("");
                txtprice.setText("");
                txtpath.setText("");
                txtquantity.setText("");
                txtcategory.setText("");
                txtmota.setText("");
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
                        "Are you sure you want to remove?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION && index >= 0 && index < list.size()) {
                    ModelProducts productToRemove = list.get(index);

                    try {
                        Connection conn = JDBCuntil.getconection();
                        String sql = "DELETE FROM sanpham WHERE MaSanPham = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, productToRemove.getId());
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
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Sự kiện nút Lưu
        JbtLuu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    String ten = txtname.getText().trim();
                    String giaText = txtprice.getText().trim();
                    String soluongText = txtquantity.getText().trim();
                    String danhmuc = txtcategory.getText().trim();
                    String mota = txtmota.getText().trim();
                    String path = txtpath.getText().trim();

                    if (ten.isEmpty() || giaText.isEmpty() || soluongText.isEmpty() || danhmuc.isEmpty() || mota.isEmpty() || path.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double gia = Double.parseDouble(giaText);
                    int soluong = Integer.parseInt(soluongText);

                    Connection conn = JDBCuntil.getconection();
                    PreparedStatement pstmt = null;

                    if (check == 1) { // Thêm mới
                        String sql = "INSERT INTO sanpham (TenSanPham, LoaiSanPham, Gia, SoLuong, MoTa, Path) VALUES (?, ?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                        pstmt.setString(1, ten);
                        pstmt.setString(2, danhmuc);
                        pstmt.setDouble(3, gia);
                        pstmt.setInt(4, soluong);
                        pstmt.setString(5, mota);
                        pstmt.setString(6, path);
                        pstmt.executeUpdate();

                        // Lấy ID được tạo tự động
                        ResultSet rs = pstmt.getGeneratedKeys();
                        int newId = 0;
                        if (rs.next()) {
                            newId = rs.getInt(1);
                        }
                        list.add(new ModelProducts(newId, ten, danhmuc, gia, soluong, mota, path));
                    } else if (check == -1 && index >= 0 && index < list.size()) { // Sửa
                        String sql = "UPDATE sanpham SET TenSanPham = ?, LoaiSanPham = ?, Gia = ?, SoLuong = ?, MoTa = ?, Path = ? WHERE MaSanPham = ?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, ten);
                        pstmt.setString(2, danhmuc);
                        pstmt.setDouble(3, gia);
                        pstmt.setInt(4, soluong);
                        pstmt.setString(5, mota);
                        pstmt.setString(6, path);
                        pstmt.setInt(7, list.get(index).getId());
                        pstmt.executeUpdate();
                        list.set(index, new ModelProducts(list.get(index).getId(), ten, danhmuc, gia, soluong, mota, path));
                    }

                    if (pstmt != null) pstmt.close();
                    conn.close();
                    viewtable();
                    txtname.setEditable(false);
                    txtprice.setEditable(false);
                    txtpath.setEditable(false);
                    txtquantity.setEditable(false);
                    txtcategory.setEditable(false);
                    txtmota.setEditable(false);
                    check = 0;

                    JOptionPane.showMessageDialog(null, "Lưu sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Giá và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                txtquantity.setEditable(false);
                txtcategory.setEditable(false);
                txtmota.setEditable(false);
                view();
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

 
    public void loadProduct() {
        list.clear();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {
            Connection con = JDBCuntil.getconection();
            String sql = "SELECT MaSanPham, TenSanPham, LoaiSanPham, Gia, SoLuong, MoTa, Path FROM sanpham";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("MaSanPham");
                String ten = rs.getString("TenSanPham");
                String danhmuc = rs.getString("LoaiSanPham");
                double gia = rs.getDouble("Gia");
                int soluong = rs.getInt("SoLuong");
                String mota = rs.getString("MoTa");
                String path = rs.getString("Path");
                System.out.println("Đường dẫn ảnh: " + path);
                list.add(new ModelProducts(id, ten, danhmuc, gia, soluong, mota, path));
                model.addRow(new Object[]{list.size(), id, ten, gia, soluong, danhmuc, mota, path});
            }
            JDBCuntil.closeconection(con);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Lọc sản phẩm theo ô tìm kiếm
    private void filterProduct() {
        String searchTerm = txtfind.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {
            Connection con = JDBCuntil.getconection();
            String sql = "SELECT MaSanPham, TenSanPham, LoaiSanPham, Gia, SoLuong, MoTa, Path FROM sanpham WHERE TenSanPham LIKE ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + searchTerm + "%");
            ResultSet rs = st.executeQuery();

            int stt = 1;
            while (rs.next()) {
                int id = rs.getInt("MaSanPham");
                String ten = rs.getString("TenSanPham");
                String danhmuc = rs.getString("LoaiSanPham");
                double gia = rs.getDouble("Gia");
                int soluong = rs.getInt("SoLuong");
                String mota = rs.getString("MoTa");
                String path = rs.getString("Path");
                model.addRow(new Object[]{stt++, id, ten, gia, soluong, danhmuc, mota, path});
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
        for (ModelProducts product : list) {
            model.addRow(new Object[]{
                    stt++,
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategory(),
                    product.getMoTa(),
                    product.getPath()
            });
        }
    }

  
    public void view() {
        if (index < 0 || index >= list.size()) {
            txtname.setText("");
            txtprice.setText("");
            txtquantity.setText("");
            txtcategory.setText("");
            txtmota.setText("");
            txtpath.setText("");
            return;
        }

        products = list.get(index);
        txtname.setText(products.getName());
        txtprice.setText(String.valueOf(products.getPrice()));
        txtquantity.setText(String.valueOf(products.getQuantity()));
        txtcategory.setText(products.getCategory());
        txtmota.setText(products.getMoTa());
        txtpath.setText(products.getPath());
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
            JFrame frame = new JFrame("Quản Lý Sản Phẩm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1240, 842);
            frame.add(new AddProduct());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}