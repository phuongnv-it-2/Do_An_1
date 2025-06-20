    package view.page;

import Database.JDBCuntil;
import Swing.Button;
import Swing.MyTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.ModelBill;
import net.miginfocom.swing.MigLayout;

public class BillPanel extends JPanel {
      private ArrayList<ModelBill> list = new ArrayList<>();
        private int index = -1;
    private int check = 0; 
    private JTable tblBill;
    private DefaultTableModel tableModel;
    private MyTextField txtMaDonMua, txtTenSP, txtSoluong, txtGia,txtFind;
    private JDateChooser dateChooserNgayMua;
    private Button btnAdd, btnEdit, btnDelete, btnSave, btnCancel;

    public BillPanel(JTable tblBill, DefaultTableModel tableModel, MyTextField txtMaDonMua, MyTextField txtTenSP, MyTextField txtSoluong, MyTextField txtGia, JDateChooser dateChooserNgayMua, Button btnAdd, Button btnEdit, Button btnDelete, Button btnSave, Button btnCancel) {
        this.tblBill = tblBill;
        this.tableModel = tableModel;
        this.txtMaDonMua = txtMaDonMua;
        this.txtTenSP = txtTenSP;
        this.txtSoluong = txtSoluong;
        this.txtGia = txtGia;
        this.dateChooserNgayMua = dateChooserNgayMua;
        this.btnAdd = btnAdd;
        this.btnEdit = btnEdit;
        this.btnDelete = btnDelete;
        this.btnSave = btnSave;
        this.btnCancel = btnCancel;
        initComponents();
    }

    public BillPanel() {
       initComponents();
    }

    private void initComponents() {
     setLayout(new BorderLayout());
        setSize(1240, 842);
        setBackground(new Color(62, 167, 136));
        dateChooserNgayMua = new JDateChooser();
        dateChooserNgayMua.setEnabled(true);

        JPanel leftPanel = new JPanel(new MigLayout("fill", "[grow,fill]", "[]10[]10[]10[]10[]10[]"));
        leftPanel.setPreferredSize(new Dimension(450, 842));
        leftPanel.setBackground(new Color(206, 157, 255));

        // Labels and Inputs
        JLabel lblMaDonMua = new JLabel("Mã Đơn Mua:");
        lblMaDonMua.setForeground(new Color(255, 255, 255));
        JLabel lblTenSP = new JLabel("Tên Sản Phẩm:");
        lblTenSP.setForeground(new Color(255, 255, 255));
        JLabel lblSoluong = new JLabel("Số Lượng:");
        lblSoluong.setForeground(new Color(255, 255, 255));
        JLabel lblGia = new JLabel("Giá:");
        lblGia.setForeground(new Color(255, 255, 255));
        JLabel lblNgayMua = new JLabel("Ngày Mua:");
        lblNgayMua.setForeground(new Color(255, 255, 255));

        txtMaDonMua = new MyTextField();
        txtMaDonMua.setHint("Mã Đơn Mua");
        txtMaDonMua.setEditable(false);
        txtMaDonMua.setPreferredSize(new Dimension(200, 30));
        txtMaDonMua.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/ma.png")));
        
        txtTenSP = new MyTextField();
        txtTenSP.setHint("Tên Sản Phẩm");
        txtTenSP.setPreferredSize(new Dimension(200, 30));
        txtTenSP.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/name.png")));

        txtSoluong = new MyTextField();
        txtSoluong.setHint("Số Lượng");
        txtSoluong.setPreferredSize(new Dimension(200, 30));
        txtSoluong.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/So Luong.png")));

        txtGia = new MyTextField();
        txtGia.setHint("Giá");
        txtGia.setPreferredSize(new Dimension(200, 30));
        txtGia.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/price.png")));
        
         txtFind = new MyTextField();
        txtFind.setHint("Tìm kiếm");
        txtFind.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));

        
        txtMaDonMua.setEditable(false); 
        txtTenSP.setEditable(false);  
        txtSoluong.setEditable(false);  
        txtGia.setEditable(false);  
        
        dateChooserNgayMua.setEnabled(false); 
        dateChooserNgayMua = new JDateChooser();
        dateChooserNgayMua.setDateFormatString("yyyy-MM-dd");
        dateChooserNgayMua.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooserNgayMua.setPreferredSize(new Dimension(200, 30));

        btnAdd = new Button();
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnAdd.setBackground(new Color(210, 30, 179));
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnEdit = new Button();
        btnEdit.setPreferredSize(new Dimension(100, 30));
        btnEdit.setBackground(new Color(210, 30, 179));
        btnEdit.setForeground(new Color(255, 255, 255));
        btnEdit.setText("Sửa");
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnDelete = new Button();
        btnDelete.setPreferredSize(new Dimension(100, 30));
        btnDelete.setBackground(new Color(210, 30, 179));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnSave = new Button();
        btnSave.setPreferredSize(new Dimension(100, 30));
        btnSave.setBackground(new Color(210, 30, 179));
        btnSave.setForeground(new Color(255, 255, 255));
        btnSave.setText("Lưu");
        btnSave.setVisible(false);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnCancel = new Button();
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.setBackground(new Color(210, 30, 179));
        btnCancel.setForeground(new Color(255, 255, 255));
        btnCancel.setText("Hủy");
        btnCancel.setVisible(false);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
       

        // Add components to the left panel
        leftPanel.add(lblMaDonMua, "cell 0 0");
        leftPanel.add(txtMaDonMua, "cell 1 0");
        leftPanel.add(lblTenSP, "cell 0 1");
        leftPanel.add(txtTenSP, "cell 1 1");
        leftPanel.add(lblSoluong, "cell 0 2");
        leftPanel.add(txtSoluong, "cell 1 2");
         leftPanel.add(txtFind, "cell 0 8, span 2");
        leftPanel.add(lblGia, "cell 0 3");
        leftPanel.add(txtGia, "cell 1 3");
        leftPanel.add(lblNgayMua, "cell 0 4");
        leftPanel.add(dateChooserNgayMua, "cell 1 4");
        leftPanel.add(btnAdd, "cell 0 5");
        leftPanel.add(btnEdit, "cell 1 5");
        leftPanel.add(btnDelete, "cell 2 5");
        leftPanel.add(btnSave, "cell 0 6");
        leftPanel.add(btnCancel, "cell 1 6");
       

        add(leftPanel, BorderLayout.WEST);

        // Table
        tblBill = new JTable();
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT", "Mã Đơn Mua", "Tên Sản Phẩm", "Số Lượng", "Giá", "Ngày Mua"}
        );
        tblBill.setModel(tableModel);
        tblBill.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblBill.setRowHeight(40);

        // Adjust column widths
        tblBill.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblBill.getColumnModel().getColumn(1).setPreferredWidth(175);
        tblBill.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblBill.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblBill.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblBill.getColumnModel().getColumn(5).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tblBill);
        scrollPane.setPreferredSize(new Dimension(1000, 842));
        add(scrollPane, BorderLayout.CENTER);
        
btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        prepareToAdd();
          onOff(false, true);
    }
});


btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        prepareToEdit();
        txtMaDonMua.setEditable(false); 
             onOff(false, true);
            
    }
});

// Thêm hành động cho nút Xóa
btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
       
        deleteBill();
    }
});


btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        saveBill();
    }
});

btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        onOff(true, false);
    }
});

tblBill.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        viewBill();
    }
});
txtFind.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        searchBill();
    }

    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        searchBill();
    }

    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        searchBill();
    }
});



        loadBill();
    }
    
  private void loadBill() {
    list.clear();
    DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
    model.setRowCount(0);  // Xóa các dòng hiện tại trong bảng

    try (Connection conn = JDBCuntil.getconection()) {
        // Truy vấn dữ liệu từ bảng Bill
        String sql = "SELECT * FROM bill";  // Giả sử bạn có bảng 'bill' trong CSDL
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        int i = 1;
        while (rs.next()) {
            // Tạo đối tượng Bill từ kết quả truy vấn
            ModelBill bill = new ModelBill(
                rs.getString("maDonMua"),   // Giả sử "maDonMua" là mã đơn mua
                rs.getString("tenSP"),      // "tenSP" là tên sản phẩm
                rs.getInt("soLuong"),       // "soLuong" là số lượng
                rs.getInt("gia"),        // "gia" là giá
                rs.getDate("ngayMua")       // "ngayMua" là ngày mua
            );

            list.add(bill);  // Thêm Bill vào danh sách
            model.addRow(new Object[]{
                i++, bill.getMaDonMua(), bill.getTenSP(), bill.getSoluong(), 
                bill.getGia(), bill.getNgayMua()});
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void prepareToAdd() {
        check = 1;
        clearFields();
        enableFields(true);
    }

    private void prepareToEdit() {
        txtMaDonMua.setEditable(false);
        check = -1;
        enableFields(true);
    }
private void deleteBill() {
    int confirm = JOptionPane.showConfirmDialog(
            btnDelete, 
            "Are you sure you want to remove?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        if (index >= 0 && index < list.size()) { 
            ModelBill billToRemove = list.get(index);  // Lấy đối tượng Bill tại vị trí index

            try (Connection conn = JDBCuntil.getconection()) {
                String sql = "DELETE FROM bill WHERE maDonMua = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, billToRemove.getMaDonMua());  // Nếu maDonMua là kiểu int
                pstmt.executeUpdate(); 

                pstmt.close();
                list.remove(index);

                // Cập nhật lại index nếu cần thiết
                if (index >= list.size()) {
                    index = list.size() - 1;
                }
                if (index < 0) {
                    index = 0;
                }

                loadBill();  // Tải lại dữ liệu bảng
                resetForm();  // Đặt lại form

            } catch (SQLException ex) {
                ex.printStackTrace(); 
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
private void saveBill() {
    try {
        // Lấy mã đơn mua, mã bây giờ là String
        String maDonMua = txtMaDonMua.getText().trim(); 

        // Kiểm tra nếu mã đơn mua trống
        if (maDonMua.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã đơn mua không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ten = txtTenSP.getText().trim();
        int gia = 0;
        int soLuong = 0;

        // Chuyển giá sang số nguyên
        try {
            gia = Integer.parseInt(txtGia.getText().trim());  
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Giá phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chuyển số lượng sang số nguyên
        try {
            soLuong = Integer.parseInt(txtSoluong.getText().trim());  
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date NgayMua = dateChooserNgayMua.getDate();  

        if (ten.isEmpty() || gia <= 0 || soLuong <= 0 || NgayMua == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = JDBCuntil.getconection();
        PreparedStatement pstmt = null;

        // Kiểm tra nếu đây là trường hợp thêm hoá đơn mới
        if (check == 1) {
            String checkSQL = "SELECT COUNT(*) FROM bill WHERE maDonMua = ?";
            pstmt = conn.prepareStatement(checkSQL);
            pstmt.setString(1, maDonMua);  // Kiểm tra mã đơn mua
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã đơn mua này đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            // Thêm hoá đơn mới vào cơ sở dữ liệu
            String sql = "INSERT INTO bill (maDonMua, ten, gia, soLuong, ngayMua) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maDonMua);  // Lưu mã đơn mua dưới dạng String
            pstmt.setString(2, ten);
            pstmt.setInt(3, gia);
            pstmt.setInt(4, soLuong);  
            pstmt.setDate(5, new java.sql.Date(NgayMua.getTime()));  
            pstmt.executeUpdate();

            // Thêm hoá đơn vào danh sách
            ModelBill bill = new ModelBill(maDonMua, ten, gia, soLuong, NgayMua);
            list.add(bill);

        } else if (check == -1 && index >= 0 && index < list.size()) {  // Trường hợp cập nhật hoá đơn
            String checkSQL = "SELECT COUNT(*) FROM bill WHERE maDonMua = ? AND maDonMua != ?";
            pstmt = conn.prepareStatement(checkSQL);
            pstmt.setString(1, maDonMua);  // Kiểm tra mã đơn mua
            pstmt.setString(2, list.get(index).getMaDonMua());  
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Mã đơn mua này đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;  
            }

            // Cập nhật thông tin hoá đơn
            String sql = "UPDATE bill SET ten = ?, gia = ?, soLuong = ?, ngayMua = ? WHERE maDonMua = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ten);
            pstmt.setInt(2, gia);
            pstmt.setInt(3, soLuong);  
            pstmt.setDate(4, new java.sql.Date(NgayMua.getTime()));  
            pstmt.setString(5, maDonMua);  
            pstmt.executeUpdate();

            // Cập nhật trong danh sách
            list.set(index, new ModelBill(maDonMua, ten, gia, soLuong, NgayMua));
        }

        if (pstmt != null) pstmt.close();
        conn.close();
        loadBill();  

        // Reset trạng thái form
        onOff(true, false);

        if (check != 1) {
            txtTenSP.setEditable(false);
            txtGia.setEditable(false);
            txtSoluong.setEditable(false);  
            dateChooserNgayMua.setEnabled(false);
            txtMaDonMua.setEditable(false);  
        }

        check = 0;  

        JOptionPane.showMessageDialog(null, "Lưu thông tin hoá đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Lỗi khi thao tác với cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Giá và số lượng phải là các số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}



private boolean isBillFormInvalid() {
    return txtTenSP.getText().trim().isEmpty() ||
           txtGia.getText().trim().isEmpty() ||
           txtSoluong.getText().trim().isEmpty() ||
           dateChooserNgayMua.getDate() == null;
}

private void setBillStatement(PreparedStatement stmt) throws SQLException {
    stmt.setString(1, txtMaDonMua.getText()); // Mã đơn mua
    stmt.setString(2, txtTenSP.getText());   // Tên sản phẩm
    stmt.setInt(3, Integer.parseInt(txtGia.getText()));  // Giá sản phẩm
    stmt.setInt(4, Integer.parseInt(txtSoluong.getText()));  // Số lượng sản phẩm
    Date ngayMua = dateChooserNgayMua.getDate(); // Ngày mua
    stmt.setDate(5, new java.sql.Date(ngayMua.getTime()));  // Chuyển Date sang java.sql.Date
}


private void insertBill(Connection conn) throws SQLException {
    String sql = "INSERT INTO bill (maDonMua, ten, gia, soLuong, ngayMua) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        int maDonMua = Integer.parseInt(txtMaDonMua.getText()); // Lấy mã đơn mua
        stmt.setInt(1, maDonMua);
        setBillStatement(stmt); // Gọi hàm để set các tham số còn lại (tên, giá, số lượng, ngày mua)
        stmt.executeUpdate();
        loadBill();  // Tải lại danh sách hoá đơn
        resetForm();  // Reset lại form
    }
}

private void updateBill() throws SQLException {
    Connection conn = JDBCuntil.getconection(); 
    String maDonMuaText = txtMaDonMua.getText().trim();
    if (maDonMuaText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Mã đơn mua không thể để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int maDonMua;
    try {
        maDonMua = Integer.parseInt(maDonMuaText); 
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Mã đơn mua phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String sql = "UPDATE bill SET ten = ?, gia = ?, soLuong = ?, ngayMua = ? WHERE maDonMua = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        setBillStatement(stmt);  // Gọi hàm để set các tham số cho hoá đơn

        stmt.setInt(5, maDonMua); // Cập nhật theo mã đơn mua
        
        stmt.executeUpdate(); // Thực thi câu lệnh cập nhật
        loadBill();  // Tải lại danh sách hoá đơn
        resetForm();  // Reset lại form
    } finally {
        conn.close();
    }
}

private void viewBill() {
    index = tblBill.getSelectedRow();
    if (index >= 0) {
        ModelBill bill = list.get(index);
        txtMaDonMua.setText(String.valueOf(bill.getMaDonMua()));  // Mã đơn mua
        txtTenSP.setText(bill.getTenSP());  // Tên sản phẩm
        txtGia.setText(String.valueOf(bill.getGia()));  // Giá sản phẩm
        txtSoluong.setText(String.valueOf(bill.getSoluong()));  // Số lượng sản phẩm
        dateChooserNgayMua.setDate(bill.getNgayMua());  // Ngày mua
    }
}

    private void resetForm() {
        clearFields();
        enableFields(false);
        check = 0;
    }
private void clearFields() {
    txtMaDonMua.setText("");  // Xóa mã đơn mua
    txtTenSP.setText("");  // Xóa tên sản phẩm
    txtGia.setText("");  // Xóa giá sản phẩm
    txtSoluong.setText("");  // Xóa số lượng sản phẩm
    dateChooserNgayMua.setDate(new java.util.Date());  // Xóa ngày mua (đặt lại ngày hiện tại)
}



 private void enableFields(boolean enable) {
    txtMaDonMua.setEditable(enable);  // Cho phép hoặc không cho phép sửa mã đơn mua
    txtTenSP.setEditable(enable);  // Cho phép hoặc không cho phép sửa tên sản phẩm
    txtGia.setEditable(enable);  // Cho phép hoặc không cho phép sửa giá sản phẩm
    txtSoluong.setEditable(enable);  // Cho phép hoặc không cho phép sửa số lượng sản phẩm
    dateChooserNgayMua.setEnabled(enable);  // Cho phép hoặc không cho phép chọn ngày mua
}

private void searchBill() {
    String keyword = txtFind.getText().toLowerCase().trim();  
    if (keyword.isEmpty()) {
        loadBill();  // Tải lại danh sách hoá đơn nếu không nhập từ khoá
        return;
    }

    list.clear();  // Xóa danh sách hiện tại
    DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
    model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

    try (Connection conn = JDBCuntil.getconection()) {
        // Câu lệnh SQL tìm kiếm hoá đơn theo mã đơn mua hoặc tên sản phẩm
        String sql = "SELECT * FROM bill WHERE LOWER(maDonMua) LIKE ? OR LOWER(TenSP) LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Thêm ký tự `%` vào trước và sau từ khoá để tìm kiếm theo kiểu chuỗi
        String searchKeyword = "%" + keyword + "%";
        stmt.setString(1, searchKeyword); // Tìm theo `maDonMua` (kiểu String)
        stmt.setString(2, searchKeyword); // Tìm theo `TenSP` (tên sản phẩm)
        
        ResultSet rs = stmt.executeQuery();

        int i = 1;  // Dùng để đánh số thứ tự
        while (rs.next()) {
            // Tạo đối tượng Bill từ kết quả truy vấn
            ModelBill bill = new ModelBill(
                rs.getString("maDonMua"),
                rs.getString("TenSP"),
                rs.getInt("gia"),
                rs.getInt("soLuong"),
                rs.getDate("ngayMua")
            );

            // Thêm vào danh sách và bảng hiển thị
            list.add(bill);
            model.addRow(new Object[]{
                i++, 
                bill.getMaDonMua(), 
                bill.getTenSP(), 
                bill.getGia(), 
                bill.getSoluong(), 
                bill.getNgayMua()
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Ghi log lỗi nếu có
    }
}

  public void onOff(boolean a, boolean b) {
    btnAdd.setVisible(a);  
    btnEdit.setVisible(a); 
    btnDelete.setVisible(a);  
    btnSave.setVisible(b);  
    btnCancel.setVisible(b); 
}


}
