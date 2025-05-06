package Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import model.NguyenLieu;

public class CardMaterial extends javax.swing.JPanel {


    private final NguyenLieu data;
    private final Timer timer;
    private final Timer timerStop;
    private final CardDescriptionMaterial cardDescriptionMaterial;
    private int y = 140;
    private int speed = 2;
    private boolean showing = false;

    public CardMaterial(NguyenLieu data) {
        this.data = data;
        initComponents();
        setOpaque(false);
        cardDescriptionMaterial = new CardDescriptionMaterial(data);
        cardDescriptionMaterial.setLocation(0, y);
        setPreferredSize(new Dimension(150, 200));
        cardDescriptionMaterial.setSize(new Dimension(150, 150));
        add(cardDescriptionMaterial);
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (showing) {
                    y -= speed;
                    if (y <= 50) {
                        y = 50;
                        cardDescriptionMaterial.setLocation(0, y);
                        timer.stop();
                    } else {
                        cardDescriptionMaterial.setLocation(0, y);
                    }
                } else {
                    y += speed;
                    if (y >= 140) {
                        y = 140;
                        cardDescriptionMaterial.setLocation(0, y);
                        timer.stop();
                    } else {
                        cardDescriptionMaterial.setLocation(0, y);
                    }
                }
            }
        });

        timerStop = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                showing = false;
                timerStop.stop();
                timer.start();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                showing = true;
                timerStop.stop();
                timer.start();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                timerStop.start();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents
 @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        Icon icon = getIcon();
        Rectangle size = getAutoSize(icon);
        g2.drawImage(toImage(icon), size.x, size.y, size.width, size.height, null);
        super.paintComponent(grphcs);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, getHeight(), new Color(64, 67, 186, 200), 0, getHeight() - 50, new Color(0, 0, 0, 0));
        g2.setPaint(g);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private Icon getIcon() {
        String imagePath = data.getAnh();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                return new ImageIcon(imagePath);
            } catch (Exception e) {
                System.err.println("Error loading image: " + imagePath);
            }
        }
        // Fallback to a default image if anh is null or invalid
        return new ImageIcon(getClass().getResource("/path/to/default/image.png"));
    }

    private Rectangle getAutoSize(Icon image) {
        int w = 150;
        int h = 200;
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        int x = (w - width) / 2;
        int y = (h - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
