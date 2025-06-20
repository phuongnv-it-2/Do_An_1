
package Main;

import Component.Menu;
import Component.Message;

import java.sql.SQLException;
import Component.PanelCover;
import Component.PanelForgetPassword;
import Component.PanelLoading;
import Component.PanelLoginAndRegister;
import Component.PanelPassword;
import Component.PanelVerifyCode;
import Database.JDBCuntil;

import Service.ServiceMail;
import Service.ServiceUser;
//import com.sun.jdi.connect.spi.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JLayeredPane;
import model.ModelLogin;
import model.ModelMessage;
import model.ModelUser;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import Service.AESUtil;
import Service.Service;
import io.socket.client.Ack;
import java.util.Arrays;
import javax.swing.SwingUtilities;
import view.page.Body;


public class main extends javax.swing.JFrame {

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private PanelLoginAndRegister loginAndRegister;
    private PanelForgetPassword panelForgetPassword;
    private ServiceUser service;
    private Animator animator;
    private TimingTarget target;
    private AESUtil AESUtil;
    private Body body;
    private boolean isLogin;
    private PanelPassword panelPassword;

    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;

    public main() {

        initComponents();
        init();
    }

    private void init() {
        service = new ServiceUser();
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        panelForgetPassword = new PanelForgetPassword();
        panelPassword = new PanelPassword();

        ActionListener eventRegister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                register();

            }
        };
        ActionListener eventLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                login();
            }
        };
        loginAndRegister = new PanelLoginAndRegister(eventRegister, eventLogin);
        Menu menu = new Menu();
        loginAndRegister.setMenu(menu);
        loginAndRegister.addForgetPasswordEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                panelForgetPassword.setVisible(true);
            }
        });

        target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }
                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };
        animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
        bg.setLayer(panelPassword, JLayeredPane.DEFAULT_LAYER);

        bg.add(loading, "pos 0.5al 0.5al");
        bg.add(verifyCode, "pos 0.5al 0.5al");
        bg.add(panelPassword, "pos 0.5al 0.5al");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%");
        panelForgetPassword.setVisible(false);
        verifyCode.setVisible(false);
        panelPassword.setVisible(false);

        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startAnimation();
            }
        });

        verifyCode.addEventButtonOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ModelUser user = panelForgetPassword.getUser();
                    if (service.verifyCodeWithUser(user.getUserName(), verifyCode.getInputCode())) {
                        service.doneVerify(user.getUserID());

                        panelPassword.setVisible(true);
                        bg.revalidate();
                        bg.repaint();

                        verifyCode.setVisible(false);

                    } else {
                        showMessage(Message.MessageType.ERROR, "Verify code incorrect");
                    }
                } catch (SQLException e) {
                    showMessage(Message.MessageType.ERROR, "Error");
                }
            }
        });
        panelForgetPassword.addEventButtonOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ModelUser user = panelForgetPassword.getUser();
                    String userName = user.getUserName();
                    String email = user.getEmail();

                    if (!service.checkDuplicateUser(userName)) {
                        showMessage(Message.MessageType.ERROR, "Tên người dùng không tồn tại");
                    } else if (!service.checkEmailMatchUser(userName, email)) {
                        showMessage(Message.MessageType.ERROR, "Email không khớp với người dùng");
                    } else {
                        panelForgetPassword.dispose();
                        checkAndSendResetEmail();
                        showMessage(Message.MessageType.SUCCESS, "Đã gửi mã xác minh đến email: " + email);
                        verifyCode.setVisible(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showMessage(Message.MessageType.ERROR, "Lỗi truy vấn CSDL: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(Message.MessageType.ERROR, "Lỗi hệ thống: " + e.getMessage());
                }
            }
        });
        panelPassword.addEventButtonOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ModelUser user = panelForgetPassword.getUser();
                    String newPassword = panelPassword.getInputPass1();
                    String encryptPassword = AESUtil.encrypt(newPassword);

                    String confirmPassword = panelPassword.getInputPass2();

                    if (newPassword.equals(confirmPassword)) {
                        service.updatePasswordByUserName(user.getUserName(), encryptPassword);
                        showMessage(Message.MessageType.SUCCESS, "Password updated successfully!");
                        panelPassword.setVisible(false);
                    } else {
                        showMessage(Message.MessageType.ERROR, "Passwords do not match!");
                    }
                } catch (SQLException e) {
                    showMessage(Message.MessageType.ERROR, "Error updating password: " + e.getMessage());
                }
            }
        });
        cover.addEventButtonOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        });

    }

    private void register() {
        ModelUser user = loginAndRegister.getUser();
        try {
            if (user == null || user.getUserName() == null || user.getUserName().trim().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Tên người dùng không được để trống");
                return;
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Email không được để trống");
                return;
            }
            if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                showMessage(Message.MessageType.ERROR, "Email phải có định dạng @gmail.com");
                return;
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Mật khẩu không được để trống");
                return;
            }
            String password = user.getPassword();
            if (password.length() < 6) {
                showMessage(Message.MessageType.ERROR, "Mật khẩu phải có ít nhất 6 ký tự");
                return;
            }
            if (!password.matches(".*[A-Z].*")) {
                showMessage(Message.MessageType.ERROR, "Mật khẩu phải chứa ít nhất một chữ cái viết hoa");
                return;
            }

            if (service.checkDuplicateUser(user.getUserName())) {
                showMessage(Message.MessageType.ERROR, "Tên người dùng đã tồn tại");
            } else if (service.checkDuplicateEmail(user.getEmail())) {
                showMessage(Message.MessageType.ERROR, "Email đã tồn tại");
            } else {
                service.insertUser(user);
                startAnimation();
                showMessage(Message.MessageType.SUCCESS, "Đăng ký thành công!");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage(Message.MessageType.ERROR, "Lỗi đăng ký: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(Message.MessageType.ERROR, "Lỗi gửi email: " + e.getMessage());
        }
    }

    private void checkAndSendResetEmail() {
        try {
            ModelUser user = panelForgetPassword.getUser();

            if (!service.checkDuplicateUser(user.getUserName())) {
                showMessage(Message.MessageType.ERROR, "Tên người dùng không tồn tại");
            } else if (!service.checkEmailMatchUser(user.getUserName(), user.getEmail())) {
                showMessage(Message.MessageType.ERROR, "Email không khớp với người dùng");
            } else {
                service.insertVerifyCodeByUserName(user);
                sendMain(user);
                showMessage(Message.MessageType.SUCCESS, "Đã gửi mã xác minh đến email: " + user.getEmail());
            }

        } catch (SQLException e) {
            showMessage(Message.MessageType.ERROR, "Lỗi truy vấn CSDL: " + e.getMessage());
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Lỗi gửi email: " + e.getMessage());
        }
    }


    private void login() {
        ModelLogin data = loginAndRegister.getDataLogin();
        if (data == null || data.getEmail() == null || data.getPassword() == null) {
            showMessage(Message.MessageType.ERROR, "Email và Mật khẩu không được để trống");
            return;
        }
        try {
            ModelUser user = service.login(data);
            if (user != null) {
                this.dispose();
                SystemMain a = new SystemMain();
                a.setVisible(true);

            } else {
                showMessage(Message.MessageType.ERROR, "Email hoặc Mật khẩu không đúng");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage(Message.MessageType.ERROR, "Lỗi đăng nhập: " + e.getMessage());
        }
    }



    private void sendMain(ModelUser user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setVisible(true);
                ModelMessage ms = new ServiceMail().sendMain(user.getEmail(), user.getVerifyCode());
                if (ms.isSuccess()) {
                    loading.setVisible(false);
                    verifyCode.setVisible(true);
                } else {
                    loading.setVisible(false);
                    showMessage(Message.MessageType.ERROR, ms.getMessage());
                }
            }
        }).start();
    }

    public void showMessage(Message.MessageType messageType, String message) {
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!ms.isShow()) {
                    bg.add(ms, "pos 0.5al -30", 0);
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (ms.isShow()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = 40 * fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if (ms.isShow()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                } else {
                    ms.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    animator.start();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    public void startAnimation() {
        if (animator != null && !animator.isRunning()) {
            animator.start();
        }
    }

    public PanelLoading getLoadingPanel() {
        return loading;
    }

    public PanelVerifyCode getVerifyCodePanel() {
        return verifyCode;
    }

    public Body getBody() {
        return body;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(237, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 929, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(bg)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
    try {
        JDBCuntil.getconection();

    } catch (Exception e) {
    }
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new main().setVisible(true);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
