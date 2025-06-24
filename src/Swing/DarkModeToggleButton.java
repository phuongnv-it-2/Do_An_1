package Swing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DarkModeToggleButton extends JToggleButton {
    private Image sunIcon;
    private Image moonIcon;

    public DarkModeToggleButton() {
        try {
            sunIcon = new ImageIcon(getClass().getResource("/icon/user.png")).getImage();
            moonIcon = new ImageIcon(getClass().getResource("/icon/find.png")).getImage();
        } catch (Exception e) {
            System.out.println("❌ Không tìm thấy ảnh icon.");
        }

        setPreferredSize(new Dimension(100, 50));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Nền
        if (isSelected()) {
            g2.setColor(new Color(30, 30, 60));
        } else {
            g2.setColor(new Color(200, 230, 255));
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

        // Icon
        Image icon = isSelected() ? moonIcon : sunIcon;
        int iconSize = 32;
        int x = isSelected() ? getWidth() - iconSize - 10 : 10;
        int y = (getHeight() - iconSize) / 2;
        if (icon != null)
            g2.drawImage(icon, x, y, iconSize, iconSize, this);

        g2.dispose();
    }
}
