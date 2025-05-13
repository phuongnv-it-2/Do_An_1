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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

    private float blend1 = 0f;
    private float blend2 = 0f;
    private int phase = 0;
    private Timer timer1;

    public Menu() {
        this.serviceUser = new ServiceUser();
        initComponents();
        listMenu.setOpaque(false);
        setOpaque(false);
        setPreferredSize(new Dimension(276, 669));

        System.out.println("imageAvatar is visible: " + imageAvatar.isVisible());
        System.out.println("imageAvatar size after init: " + imageAvatar.getWidth() + "x" + imageAvatar.getHeight());
        System.out.println("Menu is visible: " + this.isVisible());
        System.out.println("Menu size: " + this.getWidth() + "x" + this.getHeight());
        
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
                            speed *= -1;
                            //  If speed valus <0 change it to <0   Ex : -1 to 1
                        }
                    }
                    speed++;    //  Add 1 speed
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
                    toColor2 = getNextColor(toColor2); // Lấy màu tiếp theo
                }

                color1 = blendColors(fromColor1, toColor1, progress);
                color2 = blendColors(fromColor2, toColor2, progress);
                repaint();
            }
        });
        timer1.start();
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
                listMenu.addItem(new Model_Menu("9", "Setting", Model_Menu.MenuType.MENU));
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
            int height =50; // do cao cac dong menu
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

        lbUsername = new javax.swing.JLabel();
        imageAvatar = new Service.ImageAvatar();
        panelMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMenu = new Swing.ListMenu<>();

        lbUsername.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        javax.swing.GroupLayout imageAvatarLayout = new javax.swing.GroupLayout(imageAvatar);
        imageAvatar.setLayout(imageAvatarLayout);
        imageAvatarLayout.setHorizontalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 135, Short.MAX_VALUE)
        );
        imageAvatarLayout.setVerticalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

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
            .addGap(0, 0, Short.MAX_VALUE)
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
                .addContainerGap()
                .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(listMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(lbUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(listMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
private int x;
    private int y;

    public void initMoving(JFrame fram) {
        panelMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX() + 6;
                y = me.getY() + 6;
            }

        });
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                fram.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
            }
        });

    }
 public void setImageAvatarFromAccount(String email) {
    BufferedImage img = null;
    String avatarPath = serviceUser.getImagePathByEmail(email); 

    try {
        if (avatarPath != null && !avatarPath.isEmpty()) {
            File file = new File(avatarPath);
            if (file.exists()) {
                img = ImageIO.read(file);
                System.out.println("Đã tải ảnh đại diện từ file: " + avatarPath);
            } else {
                System.out.println("File ảnh không tồn tại: " + avatarPath);
            }
        } else {
            System.out.println("Không có đường dẫn ảnh trong CSDL.");
        }

        // Nếu không có ảnh, tải ảnh mặc định
        if (img == null) {
            InputStream defaultImageStream = getClass().getResourceAsStream("/icon/user.png");
            if (defaultImageStream != null) {
                img = ImageIO.read(defaultImageStream);
                System.out.println("Đã tải ảnh mặc định.");
            } else {
                System.out.println("Không tìm thấy ảnh mặc định.");
            }
        }

        // Set ảnh vào imageAvatar nếu ảnh hợp lệ
        if (img != null) {
            imageAvatar.setImage(img);
            imageAvatar.revalidate(); // Kiểm tra lại kích thước và cấu trúc
            imageAvatar.repaint(); // Vẽ lại ảnh
        } else {
            System.out.println("Không có ảnh hợp lệ để đặt vào ImageAvatar.");
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Lỗi khi tải ảnh từ đường dẫn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        // Nếu có lỗi trong quá trình tải ảnh, bạn có thể đặt ảnh mặc định tại đây:
        setDefaultImage();
    }
}

private void setDefaultImage() {
    try {
        InputStream defaultImageStream = getClass().getResourceAsStream("/icon/user.png");
        if (defaultImageStream != null) {
            BufferedImage defaultImage = ImageIO.read(defaultImageStream);
            imageAvatar.setImage(defaultImage);
            imageAvatar.repaint();
            System.out.println("Đã đặt ảnh mặc định vào ImageAvatar");
        } else {
            System.out.println("Không tìm thấy ảnh mặc định để đặt!");
        }
    } catch (IOException ex) {
        System.out.println("Lỗi khi tải ảnh mặc định: " + ex.getMessage());
    }
}


    public void setUserEmail(String email) {
    this.userEmail = email;
    setImageAvatarFromAccount(email);
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Service.ImageAvatar imageAvatar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbUsername;
    private Swing.ListMenu<String> listMenu;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables
public JLabel getLbUserName(){
    return lbUsername;
}
public ImageAvatar getImageAvatar(){
    return imageAvatar;
}

}
