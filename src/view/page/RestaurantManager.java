package view.page;

import Database.JDBCuntil;
import Service.HoverInfoHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ModelProducts;

public class RestaurantManager extends JPanel {
    private JPanel productPanel;
    private JScrollPane scrollPane;
    private JTextArea cartTextArea;
    private List<ModelProducts> cart = new ArrayList<>();

    public RestaurantManager() {
        initComponents();
        loadProductsFromDatabase();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Create JTextArea for cart display
        cartTextArea = new JTextArea();
        cartTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        cartTextArea.setEditable(false);
        cartTextArea.setLineWrap(true); // Enable line wrapping
        cartTextArea.setWrapStyleWord(true); // Wrap at word boundaries
        JScrollPane cartScrollPane = new JScrollPane(cartTextArea);
        cartScrollPane.setPreferredSize(new Dimension(300, getHeight())); 
        add(cartScrollPane, BorderLayout.EAST);

        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3, 10, 10)); 
        productPanel.setBackground(new Color(62, 167, 136));

        scrollPane = new JScrollPane(productPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadProductsFromDatabase() {
        String query = "SELECT TenSanPham, LoaiSanPham, Gia, SoLuong, MoTa, Path FROM sanpham";

        try (Connection connection = JDBCuntil.getconection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            // Clear the product panel to avoid displaying old data
            productPanel.removeAll();

            while (resultSet.next()) {
                String name = resultSet.getString("TenSanPham");
                String category = resultSet.getString("LoaiSanPham");
                double price = resultSet.getDouble("Gia");
                int quantity = resultSet.getInt("SoLuong");
                String dietaryInfo = resultSet.getString("MoTa");
                String imagePath = resultSet.getString("Path");

                JPanel itemPanel = new JPanel(new BorderLayout());
                itemPanel.setPreferredSize(new Dimension(220, 300));
                itemPanel.setBackground(new Color(84, 196, 164));
                itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Product name at the top
                JLabel nameLabel = new JLabel(name, JLabel.CENTER);
                nameLabel.setForeground(Color.WHITE);
                nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                itemPanel.add(nameLabel, BorderLayout.NORTH);

                // Product image in the center
                JLabel imageLabel = new JLabel();
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                if (imagePath != null && !imagePath.trim().isEmpty()) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaledImage));
                    } catch (Exception e) {
                        imageLabel.setText("Không tải được ảnh");
                        imageLabel.setForeground(Color.RED);
                    }
                } else {
                    imageLabel.setText("Không có ảnh");
                    imageLabel.setForeground(Color.WHITE);
                }
                itemPanel.add(imageLabel, BorderLayout.CENTER);

                // Category and price at the bottom
                JPanel infoPanel = new JPanel(new GridLayout(2, 1));
                infoPanel.setBackground(new Color(84, 196, 164));
                JLabel categoryLabel = new JLabel("Danh mục: " + (category != null ? category : "N/A"), JLabel.CENTER);
                categoryLabel.setForeground(Color.WHITE);
                JLabel priceLabel = new JLabel(String.format("%.1f VNĐ", price), JLabel.CENTER);
                priceLabel.setForeground(Color.WHITE);
                infoPanel.add(categoryLabel);
                infoPanel.add(priceLabel);
                itemPanel.add(infoPanel, BorderLayout.SOUTH);

                itemPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        addToCart(name, category, price);
                    }
                });

                HoverInfoHandler hover = new HoverInfoHandler(name, price, category, 0, dietaryInfo);
                itemPanel.addMouseMotionListener(hover);
                itemPanel.addMouseListener(hover);

                productPanel.add(itemPanel);
            }

            revalidate();
            repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToCart(String name, String category, double price) {

        if (!cart.isEmpty() && !cart.get(0).getName().equals(name)) {
            cart.clear();
        }

        for (ModelProducts product : cart) {
            if (product.getName().equals(name)) {
                product.setQuantity(product.getQuantity() + 1);
                updateCartTextArea();
                return;
            }
        }
       
     
        updateCartTextArea();
    }

    private void updateCartTextArea() {
        StringBuilder cartContent = new StringBuilder();

        for (ModelProducts product : cart) {
            double totalItemPrice = product.getPrice() * product.getQuantity();

            cartContent.append("Tên: ").append(product.getName()).append("\n")
                       .append("Danh Mục: ").append(product.getCategory()).append("\n")
                       .append("Giá: ").append(String.format("%.1f VNĐ", product.getPrice())).append("\n")
                       .append("Số Lượng: ").append(product.getQuantity()).append("\n")
                       .append("-------------------\n");
        }

        cartTextArea.setText(cartContent.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Restaurant Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 600);
            frame.setLocationRelativeTo(null);

            RestaurantManager restaurantManager = new RestaurantManager();
            frame.add(restaurantManager);

            frame.setVisible(true);
        });
    }
}
