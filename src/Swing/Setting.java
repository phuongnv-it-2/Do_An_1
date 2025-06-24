package Swing;

import javax.swing.*;
import java.awt.*;
import Swing.SquarePanel;
import Component.Menu;
import Main.main;
import com.sun.tools.javac.Main;
import view.page.Body;

public class Setting extends JPanel {

    private boolean darkMode = false;
    private final Body body;
    private Image backgroundImage;

    public Setting(Body body) {
        this.body = body;
        setSize(400, 250);

        // Load ảnh nền
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/icon/violetdoll.png")).getImage();
        } catch (Exception e) {
            System.err.println("❌ Không thể tải ảnh nền");
            e.printStackTrace();
        }

        DarkModeToggleButton toggle = new DarkModeToggleButton();
        toggle.addActionListener(e -> {
            darkMode = toggle.isSelected();
            body.getSquarePanel().toggleColorAnimation();
            body.getMenu().toggleColorAnimation();
        });

        JPanel center = new JPanel();
        center.setOpaque(false);  // Rất quan trọng để ảnh nền hiển thị
        center.add(toggle);

        setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);
        // Nút đăng xuất
        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.setFocusPainted(false);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setPreferredSize(new Dimension(120, 40));

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                SwingUtilities.getWindowAncestor(this).dispose();
                   new main().setVisible(true);

            }
        });

// Đưa vào giao diện
        center.add(Box.createVerticalStrut(20));
        center.add(logoutButton);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
