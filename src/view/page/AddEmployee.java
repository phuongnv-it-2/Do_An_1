package view.page;

import Service.EmployeeService;
import Service.ImageAvatar;
import Swing.Button;
import Swing.MyTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import net.miginfocom.swing.MigLayout;
import model.ModelEmployee;
import javax.imageio.ImageIO;

public class AddEmployee extends JPanel {

    private ArrayList<ModelEmployee> danhSachNhanVien = new ArrayList<>();
    private EmployeeService employeeService;
    private int index = -1;
    private int check = 0;
    private JComboBox<String> comboGioiTinh;
    private JLabel lblManv, lblTennv, lblGioiTinh, lblSdt, lblDiachi, lblEmail, lblNgaySinh, lblAnh, lblLuong, lblChucVu;
    private JLabel lblDisplayTennv, lblDisplayChucVu, lblDisplayLuong;
    private MyTextField txtManv, txtTennv, txtSdt, txtDiachi, txtEmail, txtAnh, txtFind, txtLuong, txtChucVu;
    private Button btnAdd, btnEdit, btnDelete, btnSave, btnCancel, btnChooseImage;
    private JTable tblNhanVien;
    private JScrollPane scrollPane;
    private JDateChooser dateChooserNgaySinh;
    private ImageAvatar imageAvatar;
    private BufferedImage defaultImage;

    public AddEmployee() {
        setLayout(new BorderLayout());
        setSize(1240, 842);
        setBackground(new Color(62, 167, 136));

        employeeService = new EmployeeService(danhSachNhanVien);

        try {
            defaultImage = ImageIO.read(getClass().getResource("/icon/user_account.png"));
        } catch (IOException e) {
            e.printStackTrace();
            defaultImage = null;
        }

        JPanel leftPanel = new JPanel(new MigLayout("fill", "[grow,fill]", "[]10[]10[]10[]10[]10[]10[]10[]10[]10[]10[]"));
        leftPanel.setPreferredSize(new Dimension(450, 842));
        leftPanel.setBackground(new Color(206, 157, 255));

        lblManv = new JLabel("Mã Nhân Viên:");
        lblManv.setForeground(new Color(255, 255, 255));
        lblTennv = new JLabel("Tên Nhân Viên:");
        lblTennv.setForeground(new Color(255, 255, 255));
        lblGioiTinh = new JLabel("Giới Tính:");
        lblGioiTinh.setForeground(new Color(255, 255, 255));
        lblSdt = new JLabel("SĐT:");
        lblSdt.setForeground(new Color(255, 255, 255));
        lblDiachi = new JLabel("Địa Chỉ:");
        lblDiachi.setForeground(new Color(255, 255, 255));
        lblEmail = new JLabel("Email:");
        lblEmail.setForeground(new Color(255, 255, 255));
        lblNgaySinh = new JLabel("Ngày Sinh:");
        lblNgaySinh.setForeground(new Color(255, 255, 255));
        lblAnh = new JLabel("Ảnh:");
        lblAnh.setForeground(new Color(255, 255, 255));
        lblLuong = new JLabel("Lương:");
        lblLuong.setForeground(new Color(255, 255, 255));
        lblChucVu = new JLabel("Chức Vụ:");
        lblChucVu.setForeground(new Color(255, 255, 255));

        txtManv = new MyTextField();
        txtManv.setHint("Mã tự động");
        txtManv.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/ma.png")));

        txtTennv = new MyTextField();
        txtTennv.setHint("Nhập tên nhân viên");
        txtTennv.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/name.png")));

        comboGioiTinh = new JComboBox<>(new String[]{"", "Nam", "Nữ"});
        comboGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboGioiTinh.setBackground(new Color(255, 255, 255));
        comboGioiTinh.setForeground(new Color(0, 0, 0));
        comboGioiTinh.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        comboGioiTinh.setPreferredSize(new Dimension(200, 30));

        txtSdt = new MyTextField();
        txtSdt.setHint("Nhập số điện thoại");
        txtSdt.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/sdt.png")));

        txtDiachi = new MyTextField();
        txtDiachi.setHint("Nhập địa chỉ");
        txtDiachi.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/diachi.png")));

        txtEmail = new MyTextField();
        txtEmail.setHint("Nhập email");
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/mail.png")));

        dateChooserNgaySinh = new JDateChooser();
        dateChooserNgaySinh.setDateFormatString("yyyy-MM-dd");
        dateChooserNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooserNgaySinh.setPreferredSize(new Dimension(200, 30));
        dateChooserNgaySinh.setBackground(new Color(255, 255, 255));
        dateChooserNgaySinh.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));

        txtAnh = new MyTextField();
        txtAnh.setHint("Đường dẫn ảnh");
        txtAnh.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/image.png")));
        txtAnh.setEditable(false);
        txtAnh.setPreferredSize(new Dimension(200, 30));
        txtAnh.setMinimumSize(new Dimension(200, 30));
        txtAnh.setMaximumSize(new Dimension(200, 30));

        txtLuong = new MyTextField();
        txtLuong.setHint("Nhập lương");
        txtLuong.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/money.png")));

        txtChucVu = new MyTextField();
        txtChucVu.setHint("Nhập chức vụ");
        txtChucVu.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/role.png")));

        btnChooseImage = new Button();
        btnChooseImage.setPreferredSize(new Dimension(100, 30));
        btnChooseImage.setBackground(new Color(210, 30, 179));
        btnChooseImage.setForeground(new Color(255, 255, 255));
        btnChooseImage.setText("Chọn Ảnh");
        btnChooseImage.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnChooseImage.setEnabled(false);

        txtManv.setEditable(false);
        txtTennv.setEditable(false);
        txtSdt.setEditable(false);
        txtDiachi.setEditable(false);
        txtEmail.setEditable(false);
        txtAnh.setEditable(false);
        txtLuong.setEditable(false);
        txtChucVu.setEditable(false);
        dateChooserNgaySinh.setEnabled(false);
        comboGioiTinh.setEnabled(false);

        txtFind = new MyTextField();
        txtFind.setHint("Tìm kiếm");
        txtFind.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));

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

        leftPanel.add(lblManv, "cell 0 0");
        leftPanel.add(txtManv, "cell 1 0");
        leftPanel.add(lblTennv, "cell 0 1");
        leftPanel.add(txtTennv, "cell 1 1");
        leftPanel.add(lblGioiTinh, "cell 0 2");
        leftPanel.add(comboGioiTinh, "cell 1 2");
        leftPanel.add(lblSdt, "cell 0 3");
        leftPanel.add(txtSdt, "cell 1 3");
        leftPanel.add(lblDiachi, "cell 0 4");
        leftPanel.add(txtDiachi, "cell 1 4");
        leftPanel.add(lblEmail, "cell 0 5");
        leftPanel.add(txtEmail, "cell 1 5");
        leftPanel.add(lblNgaySinh, "cell 0 6");
        leftPanel.add(dateChooserNgaySinh, "cell 1 6");
        leftPanel.add(lblAnh, "cell 0 7");
        leftPanel.add(txtAnh, "cell 1 7");
        leftPanel.add(btnChooseImage, "cell 2 7");
        leftPanel.add(lblLuong, "cell 0 8");
        leftPanel.add(txtLuong, "cell 1 8");
        leftPanel.add(lblChucVu, "cell 0 9");
        leftPanel.add(txtChucVu, "cell 1 9");

        leftPanel.add(txtFind, "cell 0 10, span 2");
        leftPanel.add(btnAdd, "cell 0 11");
        leftPanel.add(btnEdit, "cell 1 11");
        leftPanel.add(btnDelete, "cell 2 11");
        leftPanel.add(btnSave, "cell 0 12");
        leftPanel.add(btnCancel, "cell 1 12");

        add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(62, 167, 136));

        // Sửa lỗi cú pháp MigLayout: đưa "gap 10" vào layoutConstraints
        JPanel imagePanel = new JPanel(new MigLayout("fill, gap 10", "[grow][300px]", "[grow]"));
        imagePanel.setBackground(new Color(206, 157, 255));
        imagePanel.setPreferredSize(new Dimension(790, 400));

        JPanel infoPanel = new JPanel(new MigLayout("wrap", "[grow]", "[]10[]10[]"));
        infoPanel.setBackground(new Color(255, 255, 255));

        lblDisplayTennv = new JLabel("Tên Nhân Viên: ");
        lblDisplayTennv.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblDisplayTennv.setForeground(Color.BLACK);

        lblDisplayChucVu = new JLabel("Chức Vụ: ");
        lblDisplayChucVu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblDisplayChucVu.setForeground(Color.BLACK);

        lblDisplayLuong = new JLabel("Lương: ");
        lblDisplayLuong.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblDisplayLuong.setForeground(Color.BLACK);

        infoPanel.add(lblDisplayTennv, "growx");
        infoPanel.add(lblDisplayChucVu, "growx");
        infoPanel.add(lblDisplayLuong, "growx");

        imageAvatar = new ImageAvatar();
        imageAvatar.setPreferredSize(new Dimension(300, 300));
        if (defaultImage != null) {
            imageAvatar.setImage(defaultImage);
        }

        imagePanel.add(infoPanel, "cell 0 0, aligny center");
        imagePanel.add(imageAvatar, "cell 1 0, aligny center");

        centerPanel.add(imagePanel, BorderLayout.NORTH);

        tblNhanVien = new JTable();
        tblNhanVien.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"STT", "Mã NV", "Tên NV", "Giới Tính", "SĐT", "Địa Chỉ", "Email", "Ngày Sinh", "Ảnh"}));

        tblNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblNhanVien.setRowHeight(40);

        tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblNhanVien.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblNhanVien.getColumnModel().getColumn(3).setPreferredWidth(70);
        tblNhanVien.getColumnModel().getColumn(4).setPreferredWidth(120);
        tblNhanVien.getColumnModel().getColumn(5).setPreferredWidth(150);
        tblNhanVien.getColumnModel().getColumn(6).setPreferredWidth(150);
        tblNhanVien.getColumnModel().getColumn(7).setPreferredWidth(120);
        tblNhanVien.getColumnModel().getColumn(8).setPreferredWidth(150);

        scrollPane = new JScrollPane(tblNhanVien);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

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
                txtManv.setEditable(false);
                onOff(false, true);
            }
        });

        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteNhanVien();
            }
        });

        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNhanVien();
            }
        });

        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onOff(true, false);
                setDefaultImage();
                clearDisplayLabels();
            }
        });

        btnChooseImage.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseImage();
            }
        });

        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewNhanVien();
            }
        });

        txtFind.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchNhanVien();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchNhanVien();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchNhanVien();
            }
        });

        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        employeeService.loadNhanVien(model);
    }

    private void prepareToAdd() {
        check = 1;
        clearFields();
        enableFields(true);
        txtManv.setEditable(false);
        setDefaultImage();
        clearDisplayLabels();
    }

    private void prepareToEdit() {
        txtManv.setEditable(false);
        check = -1;
        enableFields(true);
    }

    private void deleteNhanVien() {
        int confirm = JOptionPane.showConfirmDialog(
                btnDelete,
                "Bạn có chắc chắn muốn xóa nhân viên này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                employeeService.deleteNhanVien(index);

                if (index >= danhSachNhanVien.size()) {
                    index = danhSachNhanVien.size() - 1;
                }
                if (index < 0) {
                    index = 0;
                }

                DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
                employeeService.loadNhanVien(model);
                resetForm();
                setDefaultImage();
                clearDisplayLabels();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveNhanVien() {
        try {
            String tennv = txtTennv.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diachi = txtDiachi.getText().trim();
            String email = txtEmail.getText().trim();
            String anh = txtAnh.getText().trim();
            String gioiTinh = comboGioiTinh.getSelectedItem().toString();
            Date ngaySinh = dateChooserNgaySinh.getDate();
            String luongText = txtLuong.getText().trim();
            String chucVu = txtChucVu.getText().trim();

            if (tennv.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || email.isEmpty() || gioiTinh.isEmpty() || ngaySinh == null || luongText.isEmpty() || chucVu.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin (trừ ảnh có thể để trống)!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double luong;
            try {
                luong = Double.parseDouble(luongText);
                if (luong < 0) {
                    JOptionPane.showMessageDialog(null, "Lương phải là số không âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Lương phải là một số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ModelEmployee nv = new ModelEmployee(0, tennv, gioiTinh, ngaySinh, diachi, sdt, email, anh, luong, chucVu);
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            employeeService.saveNhanVien(nv, check, index, model);

            if (!anh.isEmpty()) {
                try {
                    BufferedImage img = ImageIO.read(new File(anh));
                    imageAvatar.setImage(img);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Không thể tải ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    setDefaultImage();
                }
            } else {
                setDefaultImage();
            }

            onOff(true, false);

            if (check != 1) {
                txtTennv.setEditable(false);
                txtSdt.setEditable(false);
                txtDiachi.setEditable(false);
                txtEmail.setEditable(false);
                txtAnh.setEditable(false);
                txtLuong.setEditable(false);
                txtChucVu.setEditable(false);
                dateChooserNgaySinh.setEnabled(false);
                txtManv.setEditable(false);
                btnChooseImage.setEnabled(false);
            }

            check = 0;

            JOptionPane.showMessageDialog(null, "Lưu thông tin nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thao tác với cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void viewNhanVien() {
        index = tblNhanVien.getSelectedRow();
        if (index >= 0) {
            ModelEmployee nv = danhSachNhanVien.get(index);
            txtManv.setText(String.valueOf(nv.getManv()));
            txtTennv.setText(nv.getTennv());
            comboGioiTinh.setSelectedItem(nv.getGioitinh());
            txtSdt.setText(nv.getSdt());
            txtDiachi.setText(nv.getDiachi());
            txtEmail.setText(nv.getEmail());
            txtAnh.setText(nv.getAnh() != null ? nv.getAnh() : "");
            dateChooserNgaySinh.setDate(nv.getNgaysinh());
            txtLuong.setText(String.valueOf(nv.getLuong()));
            txtChucVu.setText(nv.getChucvu() != null ? nv.getChucvu() : "");

            lblDisplayTennv.setText("Tên NV: " + nv.getTennv());
            lblDisplayChucVu.setText("Chức Vụ: " + (nv.getChucvu() != null ? nv.getChucvu() : "Không có"));
            lblDisplayLuong.setText("Lương: " + nv.getLuong());

            String anh = nv.getAnh();
            if (anh != null && !anh.isEmpty()) {
                try {
                    BufferedImage img = ImageIO.read(new File(anh));
                    imageAvatar.setImage(img);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Không thể tải ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    setDefaultImage();
                }
            } else {
                setDefaultImage();
            }
        }
    }

    private void resetForm() {
        clearFields();
        enableFields(false);
        check = 0;
        setDefaultImage();
        clearDisplayLabels();
    }

    private void clearFields() {
        txtManv.setText("");
        txtTennv.setText("");
        comboGioiTinh.setSelectedItem("");
        txtSdt.setText("");
        txtDiachi.setText("");
        txtEmail.setText("");
        txtAnh.setText("");
        txtLuong.setText("");
        txtChucVu.setText("");
        dateChooserNgaySinh.setDate(new java.util.Date());
        setDefaultImage();
    }

    private void enableFields(boolean enable) {
        txtManv.setEditable(false);
        txtTennv.setEditable(enable);
        txtSdt.setEditable(enable);
        txtDiachi.setEditable(enable);
        txtEmail.setEditable(enable);
        txtAnh.setEditable(false);
        txtLuong.setEditable(enable);
        txtChucVu.setEditable(enable);
        comboGioiTinh.setEnabled(enable);
        dateChooserNgaySinh.setEnabled(enable);
        btnChooseImage.setEnabled(enable);
    }

    private void searchNhanVien() {
        String keyword = txtFind.getText().toLowerCase().trim();
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        if (keyword.isEmpty()) {
            employeeService.loadNhanVien(model);
            return;
        }
        employeeService.searchNhanVien(keyword, model);
    }

    public void onOff(boolean a, boolean b) {
        btnAdd.setVisible(a);
        btnEdit.setVisible(a);
        btnDelete.setVisible(a);
        btnSave.setVisible(b);
        btnCancel.setVisible(b);
    }

    private void setDefaultImage() {
        if (defaultImage != null) {
            imageAvatar.setImage(defaultImage);
        } else {
            imageAvatar.setImage(null);
        }
    }

    private void clearDisplayLabels() {
        lblDisplayTennv.setText("Tên NV: ");
        lblDisplayChucVu.setText("Chức Vụ: ");
        lblDisplayLuong.setText("Lương: ");
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                txtAnh.setText(selectedFile.getAbsolutePath());
                imageAvatar.setImage(img);
                JOptionPane.showMessageDialog(this, "Đã chọn ảnh: " + selectedFile.getName(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Không thể tải ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                setDefaultImage();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Nhân Viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1240, 842);
            frame.getContentPane().add(new AddEmployee());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
