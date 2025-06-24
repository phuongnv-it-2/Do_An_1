package Component;

import Event.EventMenu;
import Event.EventMenuCallBack;
import Event.EventMenuSelected;
import Service.ImageAvatar;
import Service.ServiceUser;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.Timer;
import Swing.ListMenu;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Model_Menu;

public class Menu extends javax.swing.JPanel {

    public void addEventMenu(EventMenu event) {
        this.event = event;
    }
    private String userEmail;
    private int selectedIndex = -1;
    private final Timer timer;
    private boolean toUp;
    private int menuYTarget;
    private int menuY;
    private int speed = 2;
    private EventMenuCallBack callBack;
    private EventMenu event;
    private final Color baseColor1 = Color.decode("#FF6FD8");
    private final Color baseColor2 = Color.decode("#915EFF");
    private final Color baseColor3 = Color.decode("#5B247A");
    ;
    private Color color1 = baseColor1;
    private Color color2 = baseColor2;
    private ServiceUser serviceUser;
    private BufferedImage defaultImage;

    private float blend1 = 0f;
    private float blend2 = 0f;
    private int phase = 0;
    private Timer timer1;
    private boolean isAnimating= false;
    private Menu menu;

    public Menu() {

        try {
            defaultImage = ImageIO.read(getClass().getResource("/icon/user_account.png"));
        } catch (IOException e) {
            e.printStackTrace();
            defaultImage = null;
        }

        this.serviceUser = new ServiceUser();
        initComponents();
        listMenu.setOpaque(false);
        setOpaque(false);
        setPreferredSize(new Dimension(276, 669));
        listMenu.addEventSelectedMenu(new EventMenuSelected() {
            @Override
            public void menuSelected(int index, EventMenuCallBack callBack) {
                if (index != selectedIndex) {
                    Menu.this.callBack = callBack;
                    toUp = selectedIndex > index;
                    if (selectedIndex == -1) {
                        speed = 20;
                    } else {
                        speed = selectedIndex - index;
                        if (speed < 0) {
                            speed *= -2;
                            //  If speed valus <0 change it to <0   Ex : -1 to 1
                        }
                    }
                    speed+=2;    //  Add 1 speed
                    selectedIndex = index;
                    menuYTarget = selectedIndex * 50 + listMenu.getY(); //  menuYTarget is location y
                    if (!timer.isRunning()) {
                        timer.start();
                    }
                }
            }
        });
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (toUp) {
                    if (menuY <= menuYTarget - 5) {
                        menuY = menuYTarget;
                        repaint();
                        timer.stop();
                        if (callBack != null) {
                            callBack.call(selectedIndex);
                        }
                        if (event != null) {
                            event.menuIndexChange(selectedIndex);
                        }
                    } else {
                        menuY -= speed;
                        repaint();
                    }
                } else {
                    if (menuY >= menuYTarget + 5) {
                        menuY = menuYTarget;
                        repaint();
                        timer.stop();
                        if (callBack != null) {
                            callBack.call(selectedIndex);
                        }
                        if (event != null) {
                            event.menuIndexChange(selectedIndex);
                        }
                    } else {
                        menuY += speed;
                        repaint();
                    }
                }

            }
        });
        initData();
        startColorAnimation();

    }

    private void startColorAnimation() {
        if (timer1 != null && timer1.isRunning()) {
            return; // Đã chạy rồi thì không cần chạy lại
        }

        timer1 = new Timer(60, new ActionListener() {
            private Color fromColor1 = baseColor1;
            private Color toColor1 = baseColor2;

            private Color fromColor2 = baseColor2;
            private Color toColor2 = baseColor3;

            private float progress = 0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 0.02f;
                if (progress >= 1f) {
                    progress = 0f;

                    fromColor1 = toColor1;
                    toColor1 = toColor2;

                    fromColor2 = toColor2;
                    toColor2 = getNextColor(toColor2);
                }

                color1 = blendColors(fromColor1, toColor1, progress);
                color2 = blendColors(fromColor2, toColor2, progress);
                repaint();
            }
        });

        timer1.start();
        isAnimating = true;
    }
   private void stopColorAnimation() {
    if (timer1 != null) {
        timer1.stop();
        timer1 = null;
        isAnimating = false;

        // Reset về màu ban đầu (hoặc chọn màu tĩnh tùy ý)
        color1 = baseColor1;
        color2 = baseColor1;  // hoặc baseColor2
        repaint(); // bắt buộc để áp dụng lại ngay màu dừng
    }
}


   public void toggleColorAnimation() {
    if (isAnimating) {
        stopColorAnimation();
    } else {
        startColorAnimation();
    }
}


    private Color getNextColor(Color current) {
        if (current.equals(baseColor1)) {
            return baseColor2;
        }
        if (current.equals(baseColor2)) {
            return baseColor3;
        }
        return baseColor1;
    }

    private Color blendColors(Color c1, Color c2, float ratio) {
        float ir = 1.0f - ratio;
        int red = (int) (c1.getRed() * ir + c2.getRed() * ratio);
        int green = (int) (c1.getGreen() * ir + c2.getGreen() * ratio);
        int blue = (int) (c1.getBlue() * ir + c2.getBlue() * ratio);
        return new Color(red, green, blue);
    }

    private void initData() {
        listMenu.addItem(new Model_Menu("1", "Raw Material WareHouse ", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("2", "Material Management", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("3", "Dish", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("4", "Dish Management ", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("5", "Table Management", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("6", "Employee Management", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("7", "Revenue", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("8", "Message", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("9", "Bill Management", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("10", "Discount Management", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("11", "Lucky Wheel", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("12", "Setting", Model_Menu.MenuType.MENU));
        listMenu.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        if (selectedIndex >= 0) {
            int menuX = 10;
            int height = 50; // do cao cac dong menu
            int width = getWidth();
            g2.setColor(new Color(242, 242, 242));
            g2.fillRoundRect(menuX, menuY, width, height, 35, 35);
            Path2D.Float f = new Path2D.Float();
            f.moveTo(width - 30, menuY);
            f.curveTo(width - 10, menuY, width, menuY, width, menuY - 30);
            f.lineTo(width, menuY + height + 30);
            f.curveTo(width, menuY + height, width - 10, menuY + height, width - 30, menuY + height);

            g2.fill(f);
        }
        super.paintComponent(grphcs);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMenu = new Swing.ListMenu<>();

        panelMoving.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconMenu/violet (1).png"))); // NOI18N
        jLabel1.setText("Violet Restaurant");

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
            .addGroup(panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMovingLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 76, Short.MAX_VALUE)
            .addGroup(panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelMovingLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(listMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
private int x;
    private int y;

    public void initMoving(JFrame fram) {
        panelMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX() + 20;
                y = me.getY() + 20;
            }

        });
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                fram.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private Swing.ListMenu<String> listMenu;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables

    public Menu getMeu() {
        return menu;
    }

}
