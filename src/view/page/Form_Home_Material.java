package view.page;

import Component.CardMaterial;
import Swing.ScrollBar;
import Swing.WrapLayout;
import javax.swing.ImageIcon;
import model.NguyenLieu;
import Service.MaterialService;
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
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form_Home_Material extends javax.swing.JPanel {

    private Service.MaterialService materialService;
    private JPanel categoryPanel;
    private JComboBox<String> categoryComboBox;
    private static final String[] MATERIAL_TYPES = {"Tất cả", "Nông sản", "Thủy sản", "Gia vị", "Khác"};

    public Form_Home_Material() {
        initComponents();
        materialService = new MaterialService();
        init();
    }

    private void init() {
        // Khởi tạo panel phân loại
        categoryPanel = new JPanel();
        categoryPanel.setBackground(new Color(250, 250, 250));
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.X_AXIS));

        // Thêm label và combobox cho phân loại
        JLabel categoryLabel = new JLabel("Phân loại: ");
        categoryComboBox = new JComboBox<>(MATERIAL_TYPES);
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterMaterialsByCategory((String) categoryComboBox.getSelectedItem());
            }
        });

        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);
        categoryPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thiết lập layout cho panel chính
        panel.setLayout(new WrapLayout(WrapLayout.LEADING));
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        loadMaterials("Tất cả");

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

    private void loadMaterials(String category) {
        panel.removeAll();
        List<NguyenLieu> materials = category.equals("Tất cả") 
            ? materialService.getAllMaterials() 
            : materialService.getMaterialsByCategory(category);
        for (NguyenLieu material : materials) {
            CardMaterial materialCard = new CardMaterial(material);
            panel.add(materialCard);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void filterMaterialsByCategory(String category) {
        loadMaterials(category);
    }

    void reloadMaterials() {
        loadMaterials((String) categoryComboBox.getSelectedItem());
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
