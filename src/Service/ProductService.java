//package Service;
//
//
//import Database.JDBCuntil;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import javax.swing.JOptionPane;
//import model.Products;
//public class ProductService {
//   private static ProductService instance;
//   public static ProductService getInstance() {
//		if(instance == null) {
//			instance = new ProductService();
//		}
//		return instance;
//	}
//    private Products Products;
//               public ArrayList<Products> loadProducts() {
//    ArrayList<Products> list = new ArrayList<>();
//		try {
//			// Bước 1: tạo kết nối đến CSDL
//			Connection connection = JDBCuntil.getconection();
//			
//			// Bước 2: tạo ra đối tượng statement
//			String sql = "SELECT * FROM products";
//			PreparedStatement st = connection.prepareStatement(sql);
//			
//			// Bước 3: thực thi câu lệnh SQL
//			System.out.println(sql);
//			ResultSet rs = st.executeQuery(); //
//			
//			// Bước 4:
//			while(rs.next()) {
//				String ten = rs.getString(1);
//				double gia = rs.getDouble(2);
//				String anh = rs.getString(3);
//				 Products = new Products(ten, gia, anh);
//				
//				list.add(Products);
//			}
//			
//			// Bước 5:
//			JDBCuntil.closeconection(connection);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return list;
//	}
//               public boolean addProducts(Products product) {
//		int ketQua = 0;
//		try {
//			// Bước 1: tạo kết nối đến CSDL
//			Connection con = JDBCuntil.getconection();
//			
//			// Bước 2: tạo ra đối tượng statement
//			String sql = "INSERT INTO products (name, price, image) "+
//					" VALUES (?,?,?)";
//			
//			PreparedStatement st = con.prepareStatement(sql);
//			
//			st.setString(1, Products.getName());
//			st.setDouble(2, Products.getPrice());
//			st.setString(3, Products.getImage());
//			
//			
//			// Bước 3: thực thi câu lệnh SQL
//			ketQua = st.executeUpdate(); // trả về số lượng dòng thay đổi trong database
//			
//			JOptionPane.showMessageDialog(null, "Đã thêm sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//			
//			// Bước 4: ngắt kết nối
//			JDBCuntil.closeconection(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại XXX", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//		}
//		
//		return ketQua > 0;
//	}
//	
//	public Products getProductByName(String name) {
//		Products product = null;
//		try {
//			// Bước 1: tạo kết nối đến CSDL
//			Connection con = JDBCuntil.getconection();
//			
//			// Bước 2: tạo ra đối tượng statement
//			String sql = "SELECT * FROM products Where name=?";
//			PreparedStatement st = con.prepareStatement(sql);
//			st.setString(1, name);
//			
//			// Bước 3: thực thi câu lệnh SQL
//			System.out.println(sql);
//			ResultSet rs = st.executeQuery(); //
//			
//			// Bước 4:
//			while(rs.next()) {
//                            String ten = rs.getString(1);
//				double gia = rs.getDouble(2);
//				String anh = rs.getString(3);
//				
//				
//				product = new Products(ten, gia, anh);
//			}
//			JDBCuntil.closeconection(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//        return product;
//	}
//	
//	public boolean updateProduct(Products products) {
//		int ketQua = 0;
//		try {
//			Connection con = JDBCuntil.getconection();
//			String sql = "UPDATE products SET "
//					+ "name=?, price=?, image=?"
//					+ " Where name=?";
//			
//			PreparedStatement st = con.prepareStatement(sql);
//			st.setString(1, products.getName());
//			st.setDouble(2, products.getPrice());
//			st.setString(3, products.getImage());
//			
//			ketQua = st.executeUpdate(); 
//			
//			JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//			
//			JDBCuntil.closeconection(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thất bại XXX", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//		}
//		
//		return ketQua > 0;
//	}
//	
//	public boolean deleteProduct(String name) {
//		int ketQua = 0;
//		try {
//			Connection con = JDBCuntil.getconection();
//			String sql = "DELETE FROM products "
//					+ " Where name=?";
//			
//			PreparedStatement st = con.prepareStatement(sql);
//			st.setString(1, name);
//			ketQua = st.executeUpdate(); 
//			
//			JOptionPane.showMessageDialog(null, "Xoa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//			JDBCuntil.closeconection(con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Xoa sản phẩm thất bại XXX", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//		}
//		
//		return ketQua > 0;
//	}
//}
