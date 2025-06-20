package form;

import Swing.SquarePanel;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import model.ModelUser;
import net.miginfocom.swing.MigLayout;

public class Home extends JLayeredPane {

    private LoginForm loginForm;
    private JPanel mainPanel;
    private Menu_Left menuLeft;

    public Home() {
        initComponents();
        init();
    }

    private void init() {
        mainPanel = new JPanel(new MigLayout("fillx, filly", "0[200!]5[fill, 100%]5[200!]0", "0[fill]0"));
        menuLeft = new Menu_Left();
        if (menuLeft == null) {
            throw new IllegalStateException("Failed to initialize Menu_Left");
        }
        mainPanel.add(menuLeft, "cell 0 0");
        mainPanel.add(new Chat(), "cell 1 0");
        mainPanel.add(new SquarePanel(), "cell 2 0");
        mainPanel.setOpaque(false);
        add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        loginForm = new LoginForm(this,menuLeft);
        add(loginForm, JLayeredPane.POPUP_LAYER);
        setComponentBounds();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setComponentBounds();
            }
        });
    }
    private void setComponentBounds() {
        mainPanel.setBounds(0, 0, getWidth(), getHeight());
        loginForm.setBounds(0, 0, getWidth(), getHeight());
    }
    public void showMainInterface() {
        SwingUtilities.invokeLater(() -> {
            remove(loginForm);
            loginForm = null; // Giải phóng tham chiếu
            revalidate();
            repaint();
        });
    }
    public void onLoginSuccess(ModelUser user) {
        if (menuLeft == null) {
            throw new IllegalStateException("Menu_Left is not initialized");
        }
        menuLeft.addLoggedInUser(user);
        showMainInterface();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1007, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 551, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chat Application - Home");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 600); // hoặc pack() nếu muốn tự động co
            frame.setLocationRelativeTo(null); // căn giữa màn hình
            frame.setContentPane(new Home());
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
