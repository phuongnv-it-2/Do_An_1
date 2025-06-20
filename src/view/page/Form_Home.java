package view.page;

import Component.Card;
import Swing.ScrollBar;
import Swing.WrapLayout;
import javax.swing.ImageIcon;
import model.Model_Card;
import Service.CardService;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_Home extends javax.swing.JPanel {

    private Service.CardService cardService;
    private JPanel categoryPanel;
    private JComboBox<String> categoryComboBox;
    private static final String[] DISH_CATEGORIES = {"Tất cả", "Món chính", "Món khai vị", "Món tráng miệng", "Đồ uống", "Khác"};

    public Form_Home() {
        initComponents();
        cardService = new CardService();
        init();
    }

    private void init() {
        // Khởi tạo panel phân loại
        categoryPanel = new JPanel();
        categoryPanel.setBackground(new Color(250, 250, 250));
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.X_AXIS));

        // Thêm label và combobox cho phân loại
        JLabel categoryLabel = new JLabel("Phân loại: ");
        categoryComboBox = new JComboBox<>(DISH_CATEGORIES);
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterProductsByCategory((String) categoryComboBox.getSelectedItem());
            }
        });

        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);
        categoryPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thiết lập layout cho panel chính
        panel.setLayout(new WrapLayout(WrapLayout.LEADING));
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        loadProducts("Tất cả");

        // Cập nhật layout để thêm categoryPanel
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(categoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }

    private void loadProducts(String category) {
        panel.removeAll();
        List<Model_Card> cards = category.equals("Tất cả") 
            ? cardService.getAllCards() 
            : cardService.getProductsByCategory(category);
        for (Model_Card card : cards) {
            Card productCard = new Card(card);
            panel.add(productCard);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void filterProductsByCategory(String category) {
        loadProducts(category);
    }

    void reloadProducts() {
        loadProducts((String) categoryComboBox.getSelectedItem());
        panel.revalidate();
        panel.repaint();
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(250, 250, 250));

        jScrollPane1.setBorder(null);

        panel.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 895, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
