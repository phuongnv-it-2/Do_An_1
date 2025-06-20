package Component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import model.NguyenLieu;

public class CardDescriptionMaterial extends javax.swing.JPanel {

   
    public CardDescriptionMaterial(NguyenLieu nguyenLieu) {
        initComponents();
        setOpaque(false);
        txt.setBackground(new Color(0, 0, 0, 0));
        lbTitle.setText(nguyenLieu.getTen());
        txt.setText(
            "Số lượng: " + nguyenLieu.getSoLuong() + " " + nguyenLieu.getDonVi() + "\n" +
            "Nhà cung cấp: " + nguyenLieu.getNhaCungCap() + "\n" +
            "Hạn sử dụng: " + nguyenLieu.getHanSuDung() + "\n" +
            "Ngày nhập: " + nguyenLieu.getNgayNhap()
        );
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        txt = new javax.swing.JTextArea();

        lbTitle.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(77, 77, 77));
        lbTitle.setText("Title");
        lbTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));

        txt.setColumns(20);
        txt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt.setLineWrap(true);
        txt.setRows(5);
        txt.setWrapStyleWord(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 200));
        Path2D.Float f = new Path2D.Float();
        f.moveTo(0, 30);
        f.curveTo(50, 0, 100, 40, 150, 0);
        f.lineTo(150, getHeight());
        f.lineTo(0, getHeight());
        g2.fill(f);
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTextArea txt;
    // End of variables declaration//GEN-END:variables
}
