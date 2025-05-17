package view.page;

import Database.JDBCuntil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.miginfocom.swing.MigLayout;

public class OrderPanel extends JPanel {
    private JPanel productPanel;
    private JScrollPane scrollPane;
    private JPanel cartPanelContent;
    private JButton orderButton;
    private List<Product> cart = new ArrayList<>();
    private OrderListener orderListener;
    private int tableNumber;

    public interface OrderListener {
        void onOrderConfirmed(int tableNumber, String orderDetails, long startTime);
    }

    public OrderPanel(int tableNumber, OrderListener listener) {
        this.tableNumber = tableNumber;
        this.orderListener = listener;
        initComponents();
        loadProductsFromDatabase();
    }

    private static class Product {
        String name;
        double price;
        int quantity;

        Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    private void initComponents() {
        setLayout(new MigLayout("fill", "[60%][40%]", "[grow]"));

        productPanel = new JPanel();
        productPanel.setForeground(new Color(255, 255, 255));
        productPanel.setLayout(new GridLayout(0, 4, 10, 10));
        productPanel.setBackground(Color.decode("#CBAACB"));

        scrollPane = new JScrollPane(productPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, "cell 0 0, grow");

        cartPanelContent = new JPanel();
        cartPanelContent.setLayout(new MigLayout("wrap 1, fillx", "[grow]", "[]"));
        cartPanelContent.setBackground(Color.WHITE);

        orderButton = new JButton("Order");
        orderButton.setPreferredSize(new Dimension(150, 40));
        orderButton.setForeground(new Color(255, 255, 255));
        orderButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        orderButton.setFocusPainted(false);
        orderButton.setBorderPainted(false);
        orderButton.setBackground(new Color(210, 30, 179));

        orderButton.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng trống! Vui lòng thêm sản phẩm.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                StringBuilder orderSummary = new StringBuilder("Đơn hàng:\n");
                double totalPrice = 0;
                for (Product product : cart) {
                    double itemTotal = product.price * product.quantity;
                    totalPrice += itemTotal;
                    orderSummary.append(String.format("- %s: %d x %.0f VNĐ = %.0f VNĐ\n", 
                        product.name, product.quantity, product.price, itemTotal));
                }
                orderSummary.append(String.format("Tổng cộng: %.0f VNĐ", totalPrice));

                int response = JOptionPane.showConfirmDialog(
                    this,
                    orderSummary.toString(),
                    "Xác nhận đơn hàng",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
                );

                if (response == JOptionPane.OK_OPTION) {
                 
                    if (orderListener != null) {
                        orderListener.onOrderConfirmed(tableNumber, orderSummary.toString(), System.currentTimeMillis());
                    }
                    resetCart();
                    // Close the OrderPanel's JFrame
                    Window frame = SwingUtilities.getWindowAncestor(this);
                    if (frame != null) {
                        frame.dispose();
                    }
                } 
            }
        });

        JPanel cartPanel = new JPanel(new MigLayout("fill", "[grow]", "[grow][]"));
        cartPanel.add(new JScrollPane(cartPanelContent), "grow, wrap");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(orderButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(150, 40));
        resetButton.setForeground(Color.BLACK);
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resetButton.setFocusPainted(false);
        resetButton.setBorderPainted(false);
        resetButton.setBackground(new Color(255, 69, 58));
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                resetCart();
            }
        });
        buttonPanel.add(resetButton);

        cartPanel.add(buttonPanel, "center");
        add(cartPanel, "cell 1 0, grow");
    }

    private void loadProductsFromDatabase() {
        String query = "SELECT TenSanPham, Gia, Path FROM SanPham";

        try (Connection connection = JDBCuntil.getconection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("TenSanPham");
                double price = resultSet.getDouble("Gia");
                String imagePath = resultSet.getString("Path");

                JPanel itemPanel = new JPanel(new BorderLayout());
                itemPanel.setPreferredSize(new Dimension(170, 250));
                itemPanel.setBackground( Color.decode("#FFC0CB"));
                itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                ImageIcon icon = new ImageIcon(imagePath);
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(scaledImage));
                itemPanel.add(imgLabel, BorderLayout.CENTER);

                JLabel nameLabel = new JLabel(name, JLabel.CENTER);
                nameLabel.setForeground(Color.WHITE);
                nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
                itemPanel.add(nameLabel, BorderLayout.NORTH);

                JLabel priceLabel = new JLabel(String.format("%.0f VNĐ", price), JLabel.CENTER);
                priceLabel.setForeground(Color.WHITE);
                priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
                itemPanel.add(priceLabel, BorderLayout.SOUTH);

                itemPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        addToCart(name, price);
                    }
                });

                productPanel.add(itemPanel);
            }

            revalidate();
            repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToCart(String name, double price) {
        for (Product product : cart) {
            if (product.name.equals(name)) {
                product.quantity++;
                updateCart();
                return;
            }
        }
        cart.add(new Product(name, price, 1));
        updateCart();
    }

    private void configureComponent(JComponent component) {
        component.setFont(new Font("Segoe UI", Font.BOLD, 12));
        if (component instanceof JButton || component instanceof JTextField) {
            component.setPreferredSize(new Dimension(30, 30));
            component.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        }
    }

    private void updateCart() {
        cartPanelContent.removeAll();
        double totalPrice = 0;

        for (Product product : cart) {
            if (product.quantity <= 0) continue;

            double totalItemPrice = product.price * product.quantity;
            totalPrice += totalItemPrice;

            JPanel itemCartPanel = new JPanel(new MigLayout("insets 0", "[grow][80px][120px][80px]", "[40px]"));
            itemCartPanel.setPreferredSize(new Dimension(320, 40));
            itemCartPanel.setBackground(Color.WHITE);
            itemCartPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            JLabel nameLabel = new JLabel(product.name);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            nameLabel.setHorizontalAlignment(JLabel.LEFT);
            itemCartPanel.add(nameLabel, "cell 0 0, growx");

            JLabel priceLabel = new JLabel(String.format("%.0f VNĐ", product.price));
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            priceLabel.setHorizontalAlignment(JLabel.CENTER);
            itemCartPanel.add(priceLabel, "cell 1 0");

            JPanel quantityPanel = new JPanel(new MigLayout("insets 0", "[][][]", "[center]"));
            quantityPanel.setBackground(Color.WHITE);

            JButton minusButton = new JButton("-");
            configureComponent(minusButton);
            minusButton.setBackground(new Color(255, 69, 58));
            minusButton.setForeground(Color.WHITE);
            minusButton.setFocusPainted(false);
            minusButton.addActionListener(e -> {
                product.quantity--;
                if (product.quantity <= 0) cart.remove(product);
                updateCart();
            });
            quantityPanel.add(minusButton, "gapright 4");

            JTextField quantityField = new JTextField(String.valueOf(product.quantity));
            configureComponent(quantityField);
            quantityField.setHorizontalAlignment(JTextField.CENTER);
            quantityField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    try {
                        String text = quantityField.getText();
                        if (text.isEmpty()) {
                            product.quantity = 0;
                            cart.remove(product);
                        } else {
                            int val = Integer.parseInt(text);
                            product.quantity = val <= 0 ? 0 : val;
                            if (product.quantity <= 0) cart.remove(product);
                        }
                        updateCart();
                    } catch (NumberFormatException ex) {
                        quantityField.setText(String.valueOf(product.quantity));
                    }
                }
            });
            quantityPanel.add(quantityField, "gapright 4");

            JButton plusButton = new JButton("+");
            configureComponent(plusButton);
            plusButton.setBackground(new Color(22, 216, 160));
            plusButton.setForeground(Color.WHITE);
            plusButton.setFocusPainted(false);
            plusButton.addActionListener(e -> {
                product.quantity++;
                updateCart();
            });
            quantityPanel.add(plusButton);

            itemCartPanel.add(quantityPanel, "cell 2 0, align center");

            JLabel totalItemPriceLabel = new JLabel(String.format("%.0f VNĐ", totalItemPrice));
            totalItemPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            totalItemPriceLabel.setHorizontalAlignment(JLabel.RIGHT);
            itemCartPanel.add(totalItemPriceLabel, "cell 3 0");

            cartPanelContent.add(itemCartPanel, "growx");
        }

        cartPanelContent.revalidate();
        cartPanelContent.repaint();
    }

    private void resetCart() {
        cart.clear();
        cartPanelContent.removeAll();
        cartPanelContent.revalidate();
        cartPanelContent.repaint();
    }
}