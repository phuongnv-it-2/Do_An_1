package Service;


import Database.JDBCuntil;
import model.ModelEmployee;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeService {

    private ArrayList<ModelEmployee> danhSachNhanVien;

    public EmployeeService(ArrayList<ModelEmployee> danhSachNhanVien) {
        this.danhSachNhanVien = danhSachNhanVien;
    }

    public void loadNhanVien(DefaultTableModel model) {
        danhSachNhanVien.clear();
        model.setRowCount(0);

        try (Connection conn = JDBCuntil.getconection()) {
            String sql = "SELECT * FROM nhanvien";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int i = 1;
            while (rs.next()) {
                ModelEmployee nv = new ModelEmployee(
                    rs.getInt("manv"),
                    rs.getString("tennv"),
                    rs.getString("gioitinh"),
                    rs.getDate("ngaysinh"),
                    rs.getString("diachi"),
                    rs.getString("sdt"),
                    rs.getString("email"),
                    rs.getString("anh"),
                    rs.getDouble("luong"),
                    rs.getString("chucvu")
                );

                danhSachNhanVien.add(nv);
                model.addRow(new Object[]{i++, nv.getManv(), nv.getTennv(), nv.getGioitinh(), nv.getSdt(), nv.getDiachi(), nv.getEmail(), nv.getNgaysinh(), nv.getAnh()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveNhanVien(ModelEmployee nv, int check, int index, DefaultTableModel model) throws SQLException {
        Connection conn = JDBCuntil.getconection();
        PreparedStatement pstmt = null;
        int manv = 0;

        if (check == 1) {
            String sql = "INSERT INTO nhanvien (tennv, gioitinh, ngaysinh, diachi, sdt, email, anh, luong, chucvu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nv.getTennv());
            pstmt.setString(2, nv.getGioitinh());
            pstmt.setDate(3, new java.sql.Date(nv.getNgaysinh().getTime()));
            pstmt.setString(4, nv.getDiachi());
            pstmt.setString(5, nv.getSdt());
            pstmt.setString(6, nv.getEmail());
            pstmt.setString(7, nv.getAnh() != null && !nv.getAnh().isEmpty() ? nv.getAnh() : null);
            pstmt.setDouble(8, nv.getLuong());
            pstmt.setString(9, nv.getChucvu());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                manv = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Không thể lấy mã nhân viên được tạo tự động.");
            }

            nv.setManv(manv);
            danhSachNhanVien.add(nv);

        } else if (check == -1 && index >= 0 && index < danhSachNhanVien.size()) {
            manv = danhSachNhanVien.get(index).getManv();

            String checkSQL = "SELECT COUNT(*) FROM nhanvien WHERE manv = ? AND manv != ?";
            pstmt = conn.prepareStatement(checkSQL);
            pstmt.setInt(1, manv);
            pstmt.setInt(2, danhSachNhanVien.get(index).getManv());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Mã nhân viên này đã tồn tại!");
            }

            String sql = "UPDATE nhanvien SET tennv = ?, gioitinh = ?, ngaysinh = ?, diachi = ?, sdt = ?, email = ?, anh = ?, luong = ?, chucvu = ? WHERE manv = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nv.getTennv());
            pstmt.setString(2, nv.getGioitinh());
            pstmt.setDate(3, new java.sql.Date(nv.getNgaysinh().getTime()));
            pstmt.setString(4, nv.getDiachi());
            pstmt.setString(5, nv.getSdt());
            pstmt.setString(6, nv.getEmail());
            pstmt.setString(7, nv.getAnh() != null && !nv.getAnh().isEmpty() ? nv.getAnh() : null);
            pstmt.setDouble(8, nv.getLuong());
            pstmt.setString(9, nv.getChucvu());
            pstmt.setInt(10, manv);
            pstmt.executeUpdate();

            danhSachNhanVien.set(index, nv);
        }

        if (pstmt != null) pstmt.close();
        conn.close();
        loadNhanVien(model);
    }

    public void deleteNhanVien(int index) throws SQLException {
        if (index >= 0 && index < danhSachNhanVien.size()) {
            ModelEmployee nhanVienToRemove = danhSachNhanVien.get(index);

            try (Connection conn = JDBCuntil.getconection()) {
                String sql = "DELETE FROM nhanvien WHERE manv = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, nhanVienToRemove.getManv());
                pstmt.executeUpdate();

                danhSachNhanVien.remove(index);
            }
        }
    }

    public void searchNhanVien(String keyword, DefaultTableModel model) {
        danhSachNhanVien.clear();
        model.setRowCount(0);

        try (Connection conn = JDBCuntil.getconection()) {
            String sql = "SELECT * FROM nhanvien WHERE LOWER(tennv) LIKE ? OR manv LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            int i = 1;
            while (rs.next()) {
                ModelEmployee nv = new ModelEmployee(
                    rs.getInt("manv"),
                    rs.getString("tennv"),
                    rs.getString("gioitinh"),
                    rs.getDate("ngaysinh"),
                    rs.getString("diachi"),
                    rs.getString("sdt"),
                    rs.getString("email"),
                    rs.getString("anh"),
                    rs.getDouble("luong"),
                    rs.getString("chucvu")
                );

                danhSachNhanVien.add(nv);
                model.addRow(new Object[]{i++, nv.getManv(), nv.getTennv(), nv.getGioitinh(), nv.getSdt(), nv.getDiachi(), nv.getEmail(), nv.getNgaysinh(), nv.getAnh()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}