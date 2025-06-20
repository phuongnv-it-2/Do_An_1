package Component;

import Swing.ButtonOutLine;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;

public class PanelCover extends javax.swing.JPanel {

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    private ButtonOutLine button;
    private ButtonOutLine bottomRightButton;

    private boolean isLogin;
    private Color color1 = new Color(77, 211, 243);
    private Color color2 = new Color(105, 244, 20);
    private float t = 0;
    private boolean forward = true;

    public PanelCover() {
        initComponents();
        changecolor();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(layout);
        init();
    }

    private void init() {
        title = new JLabel("Welcome Back!");
        title.setFont(new Font("sansserif", 1, 30));
        title.setForeground(new Color(245, 245, 245));
        add(title);
        description = new JLabel("");
        description.setForeground(new Color(245, 245, 245));
        add(description);
        description1 = new JLabel("");
        description1.setForeground(new Color(245, 245, 245));
        add(description1);
        button = new ButtonOutLine();
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(255, 255, 255));
        button.setText("SIGN IN");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.actionPerformed(ae);
            }
        });
        add(button, "w 60%, h 40");
        bottomRightButton = new ButtonOutLine();
        bottomRightButton.setBackground(new Color(255, 255, 255));
        bottomRightButton.setForeground(new Color(255, 255, 255));
        bottomRightButton.setText("Change Color");
        bottomRightButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent ae) {

                if (!isLogin) {
                    isLogin = true;
                    color1 = Color.decode("#a8ff78");
                    color2 = Color.decode("#78ffd6");
                } else {
                    isLogin = false;
                    color1 = new Color(77, 211, 243);
                    color2 = new Color(105, 244, 20);
                }
                repaint();
            }
        });
        add(bottomRightButton, "pos 0.9al 0.9al, w 20%, h 30");
    }

    public void addEventButtonOK(ActionListener event) {
        bottomRightButton.addActionListener(event);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;

        int r = (int) (color1.getRed() * (1 - t) + color2.getRed() * t);
        int gCol = (int) (color1.getGreen() * (1 - t) + color2.getGreen() * t);
        int b = (int) (color1.getBlue() * (1 - t) + color2.getBlue() * t);
        Color middleColor = new Color(r, gCol, b);

        GradientPaint gra = new GradientPaint(0, 0, color1, 0, getHeight(), middleColor);
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    private void changecolor() {
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (forward) {
                    t += 0.01f;
                    if (t >= 1f) {
                        t = 1f;
                        forward = false;
                    }
                } else {
                    t -= 0.01f;
                    if (t <= 0f) {
                        t = 0f;
                        forward = true;
                    }
                }
                repaint(); // gọi lại paintComponent
            }
        });
        timer.start();
    }

    public void addEvent(ActionListener event) {
        this.event = event;
    }

    public void registerLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
    }

    public void registerRight(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
    }

    public void loginLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }

    public void loginRight(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }

    public void login(boolean login) {
        if (this.isLogin != login) {
            if (login) {

                title.setText("Violet Restaurant!");
                title.setFont(new Font("Roboto", Font.BOLD, 30));
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/newlogo.png"));
                description.setIcon(icon);
                description1.setText("nvphuongaz@gmail.com");
                description1.setForeground(Color.BLACK);
                description1.setFont(new Font("Roboto", Font.ITALIC, 12));
                button.setText("SIGN UP");
            } else {
                title.setText("Wellcome Back!");
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/newlogo.png"));
                description.setIcon(icon);
                description1.setForeground(Color.BLACK);
                description1.setFont(new Font("Roboto", Font.ITALIC, 12));
                button.setText("SIGN IN");
            }
            this.isLogin = login;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
