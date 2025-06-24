package Swing;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.event.*;

public class ThemeSwitcher extends JFrame {
    private JToggleButton toggle;

    public ThemeSwitcher() {
        super("Toggle Animation");

        toggle = new JToggleButton("Animation");
        toggle.addItemListener(e -> {
            try {
                if (toggle.isSelected()) {
                    // Bật animation
                    UIManager.put("Component.arrowType", "chevron");  // chỉ để minh họa
                    UIManager.put("Component.focusWidth", 2);
                    UIManager.put("Component.arc", 10);
                    UIManager.put("Component.showMnemonics", true);
                    toggle.setText("No Animation");
                } else {
                    // Tắt animation (set các thuộc tính “tĩnh” hơn)
                    UIManager.put("Component.arrowType", "triangle");
                    UIManager.put("Component.focusWidth", 0);
                    UIManager.put("Component.arc", 0);
                    UIManager.put("Component.showMnemonics", false);
                    toggle.setText("Animation");
                }
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add(toggle);
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup(); // dùng FlatLaf nền sáng
            new ThemeSwitcher().setVisible(true);
        });
    }
}
