package Component;

import Database.JDBCuntil;
import Main.SystemMain;
import Main.main;
import Service.AESUtil;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import Swing.Button;
import Swing.MyPasswordField;
import Swing.MyTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import model.ModelLogin;
import model.ModelUser;
import net.miginfocom.swing.MigLayout;
import Service.ServiceUser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Component.Menu;
import Service.Service;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    private ActionListener forgetPasswordListener;
    private Menu menu;
    private ServiceUser serviceUser;
    private Service service;

    public ModelLogin getDataLogin() {
        return dataLogin;
    }

    public ModelUser getUser() {
        return user;
    }

    private ModelUser user;
    private ModelLogin dataLogin;

    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin) {
        this.menu = new Menu();
        initComponents();
        initRegister(eventRegister);
        initLogin(eventLogin);
        login.setVisible(false);
        register.setVisible(true);
        serviceUser = new ServiceUser();
    }

    private void initRegister(ActionListener eventRegister) {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        register.add(label);
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/user.png")));
        txtUser.setHint("Name");
        register.add(txtUser, "w 70%, h 50");
        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/mail.png")));
        txtEmail.setHint("Email");
        register.add(txtEmail, "w 70%, h 50");
        javax.swing.JPanel passwordPanel = new javax.swing.JPanel();
        passwordPanel.setLayout(new MigLayout("insets 0", "[grow]5[]", "[]"));
        passwordPanel.setOpaque(false);

        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/pass.png")));
        txtPass.setHint("Password");
        passwordPanel.add(txtPass, "grow, w 90%, h 50"); // Cố định chiều cao

        JLabel eyeLabel = new JLabel();
        ImageIcon eyeClosedIcon = new ImageIcon(getClass().getResource("/icon/eye_closed.png"));
        ImageIcon eyeOpenIcon = new ImageIcon(getClass().getResource("/icon/eye_open.png"));
        eyeLabel.setIcon(eyeClosedIcon);
        eyeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boolean[] isPasswordVisible = {false};
        eyeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible[0] = !isPasswordVisible[0];
                if (isPasswordVisible[0]) {
                    txtPass.setEchoChar((char) 0);
                    eyeLabel.setIcon(eyeOpenIcon);
                } else {
                    txtPass.setEchoChar('•');
                    eyeLabel.setIcon(eyeClosedIcon);
                }
            }
        });
        passwordPanel.add(eyeLabel, "w 10, h 10");
        register.add(passwordPanel, "w 70%");
        Button cmd = new Button();
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventRegister);
        cmd.setText("SIGN UP");
        register.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String userName = txtUser.getText().trim();
                String email = txtEmail.getText().trim();
                String password = String.valueOf(txtPass.getPassword());
                String encryptedPassword = AESUtil.encrypt(password);
                user = new ModelUser(0, userName, email, encryptedPassword);
            }
        });
    }

    private void initLogin(ActionListener eventLogin) {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Sign In");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        login.add(label);
        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/mail.png")));
        txtEmail.setHint("Email");
        login.add(txtEmail, "w 60%, h 50");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/pass.png")));
        txtPass.setHint("Password");
        login.add(txtPass, "w 60%, h 50");

        login.remove(txtPass);

        javax.swing.JPanel passwordPanel = new javax.swing.JPanel();
        passwordPanel.setLayout(new MigLayout("insets 0", "[grow]5[]", "[]"));
        passwordPanel.setOpaque(false);

        passwordPanel.add(txtPass, "grow, w 90%");

        JLabel eyeLabel = new JLabel();
        ImageIcon eyeClosedIcon = new ImageIcon(getClass().getResource("/icon/eye_closed.png"));
        ImageIcon eyeOpenIcon = new ImageIcon(getClass().getResource("/icon/eye_open.png"));
        eyeLabel.setIcon(eyeClosedIcon);
        eyeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boolean[] isPasswordVisible = {false};
        eyeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible[0] = !isPasswordVisible[0];
                if (isPasswordVisible[0]) {
                    txtPass.setEchoChar((char) 0);
                    eyeLabel.setIcon(eyeOpenIcon);
                } else {
                    txtPass.setEchoChar('•');
                    eyeLabel.setIcon(eyeClosedIcon);
                }
            }
        });
        passwordPanel.add(eyeLabel, "w 10, h 10");
        login.add(passwordPanel, "w 60%");
        JButton cmdForget = new JButton("Forgot your password ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cmdForget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (forgetPasswordListener != null) {
                    forgetPasswordListener.actionPerformed(e);
                }
            }
        });

        login.add(cmdForget);
        Button cmd = new Button();
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventLogin);
        cmd.setText("SIGN IN");
        login.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String email = txtEmail.getText().trim();
                String password = String.valueOf(txtPass.getPassword());
                String username = serviceUser.getUsernameByEmail(email);
                dataLogin = new ModelLogin(email, password);
                SystemMain main = new SystemMain();

                menu.revalidate();
                menu.repaint();

//            Service.getInstance(main);
//            Menu menu = service.getInstance(main).getMain().getBody().getMenu();
            }
        });
    }

    public void showRegister(boolean show) {
        if (show) {
            register.setVisible(true);
            login.setVisible(false);
        } else {
            register.setVisible(false);
            login.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        register = new javax.swing.JPanel();
        login = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        register.setBackground(new java.awt.Color(237, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables

    private boolean checkLogin(String username, String password) {

        String encryptedPassword = AESUtil.encrypt(password);

        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection connection = JDBCuntil.getconection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, encryptedPassword);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean registerUser(String username, String password, String email) {
        if (isUsernameTaken(username)) {
            JOptionPane.showMessageDialog(null,
                    "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Mã hóa mật khẩu
        String encryptedPassword = AESUtil.encrypt(password);

        String sql = "INSERT INTO account(username, password, email) VALUES (?, ?, ?)";

        try (Connection connection = JDBCuntil.getconection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            statement.setString(3, email);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isUsernameTaken(String username) {
        String sql = "SELECT 1 FROM account WHERE username = ? LIMIT 1";

        try (Connection connection = JDBCuntil.getconection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void addForgetPasswordEvent(ActionListener listener) {
        this.forgetPasswordListener = listener;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}
