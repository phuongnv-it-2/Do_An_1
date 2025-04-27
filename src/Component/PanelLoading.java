package Component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PanelLoading extends javax.swing.JPanel {

    public PanelLoading() {
    
        initComponents();
        setOpaque(false);
        setFocusCycleRoot(true);
        setVisible(false);
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/loadingcat.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(255, 255, 255));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
    }
    public static void main(String[] args) {
        // Set Look and Feel (optional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Create JFrame to display the panel
        JFrame frame = new JFrame("Panel Loading Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create an instance of PanelLoading
        PanelLoading loadingPanel = new PanelLoading();

        // Add the loading panel to the frame
        frame.add(loadingPanel);

        // Set frame visibility
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        // Show the loading panel for testing
        loadingPanel.setVisible(true);

        // Optionally, hide after some time
        try {
            Thread.sleep(3000); // Show loading for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Hide loading panel
        loadingPanel.setVisible(false);
    }

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
