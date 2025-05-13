    package Service;
    import java.awt.*;
    import java.awt.geom.Ellipse2D;
    import java.awt.image.BufferedImage;
    import javax.swing.*;

    public class ImageAvatar extends JComponent {
        private BufferedImage image;
        private int shadowSize = 5;
        private Color shadowColor = new Color(0, 0, 0, 80); 

        public ImageAvatar() {
            setPreferredSize(new Dimension(150, 150));
            setMinimumSize(new Dimension(100, 100)); 
            setOpaque(false); // Để nền trong suốt
        }


        public void setImage(BufferedImage img) {
            this.image = img;
            repaint();
        }


        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int width = getWidth() - shadowSize * 2;
            int height = getHeight() - shadowSize * 2;
            System.out.println("Vẽ ImageAvatar, kích thước vẽ: " + width + "x" + height);
            Ellipse2D circle = new Ellipse2D.Double(shadowSize, shadowSize, width, height);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(shadowColor);
            g2.fill(new Ellipse2D.Double(shadowSize + 2, shadowSize + 2, width, height));
            g2.setClip(circle);
            g2.drawImage(image, shadowSize, shadowSize, width, height, null);
            g2.dispose();
        } else {

        }
    }

        public void setShadowSize(int size) {
            this.shadowSize = size;
            repaint();
        }


        public void setShadowColor(Color color) {
            this.shadowColor = color;
            repaint();
        }

    }
