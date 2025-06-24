package Component;

import Event.PublicEvent;
import Swing.JIMSendTextPane;
import Swing.ScrollBar;
import form.Chat;
import form.LoginForm;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.ModelUser;
import net.miginfocom.swing.MigLayout;

public class Chat_Bottom extends javax.swing.JPanel {

    private LoginForm loginForm;
    private ModelUser currentUser;
    private Chat chat;

    public Chat_Bottom() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[fill]0[]0[]2", "2[fill]2"));
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                refresh();
            }
        });
        txt.setHintText("Write Message Here ...");
        scroll.setViewportView(txt);
        ScrollBar sb = new ScrollBar();
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        add(sb);
        add(scroll, "w 100%");
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("filly", "0[]0", "0[bottom]0"));
        panel.setPreferredSize(new Dimension(30, 28));
        panel.setBackground(Color.WHITE);
        JButton cmd = new JButton();
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setIcon(new ImageIcon(getClass().getResource("/Icon/send.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String text = txt.getText().trim();
                if (!text.isEmpty()) {
//                    if (loginForm != null && currentUser != null) {
                  PublicEvent.getInstance().getEventChat().sendMessage(text);
//                   PublicEvent.getInstance().getEventChat().receiveMessage(text);
                        txt.setText("");
                        txt.grabFocus();
                        refresh();
//                    } else {
//                        JOptionPane.showMessageDialog(Chat_Bottom.this,
//                                "Không thể gửi tin nhắn: Chưa đăng nhập!",
//                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    
                } else {
                    txt.grabFocus();
                }
            }
        });
        panel.add(cmd);
        add(panel);
    }

    public void sendMessage(String text) {
        if (loginForm != null && currentUser != null) {
            loginForm.sendMessage(text); // Gửi qua LoginForm
        } else {
            JOptionPane.showMessageDialog(this,
                    "Lỗi: Chưa đăng nhập hoặc kết nối không hợp lệ!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }

    public void setCurrentUser(ModelUser currentUser) {
        this.currentUser = currentUser;
    }

    private void refresh() {
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(229, 229, 229));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
