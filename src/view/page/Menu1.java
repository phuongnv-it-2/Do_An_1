package view.page;

import Swing.ListMenu;
import Event.EventMenu;
import Event.EventMenuCallBack;
import Event.EventMenuSelected;
import Main.SystemMain;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import static org.apache.poi.sl.usermodel.PresetColor.Menu;

public class Menu1 extends javax.swing.JPanel {

    private int selectedIndex = -1; // Giá trị mặc định để không vẽ menu item ban đầu
    private int menuY = 50; // Tọa độ Y của menu item, đặt mặc định là 50 (có thể điều chỉnh)

    private int x;
    private int y;

    private Color baseColor1 = Color.decode("#1a2a6c");
    private Color baseColor2 = Color.decode("#b21f1f");
    private Color baseColor3 = Color.decode("#fdbb2d");
    private Color color1 = baseColor1;
    private Color color2 = baseColor2;
    private float blend1 = 0.0f;
    private float blend2 = 0.0f;
    private int phase = 0;
    private boolean increasing = true;
    private Timer timer;
    private int selectedIndex1= -1;
//    private final Timer timera;
    private boolean toUp;
    private int menuYTarget;
    private int menuY1;
    private int speed = 2;
      public EventMenuCallBack callBack;
    private EventMenu event;
    private ListMenu listMenu;

    public Menu1() {
        setPreferredSize(new Dimension(300, 0));
        setMinimumSize(new Dimension(300, 0));
        setMaximumSize(new Dimension(300, Integer.MAX_VALUE));

        setLayout(new BorderLayout());
        initComponents();
        startColorAnimation();
        setOpaque(false);
        listMenu.setOpaque(false);
        listMenu.addEventSelectedMenu(new EventMenuSelected() {
     ;   public EventMenuCallBack callBack;
            public void menuSelected(int index, EventMenuCallBack callBack) {
               
                if (index != selectedIndex) {
                    this.callBack = callBack;
                    toUp = selectedIndex > index;
                    if (selectedIndex == -1) {
                        speed = 20;
                    } else {
                        speed = selectedIndex - index;
                        if (speed < 0) {
                            speed *= -1;
                        
                        }
                    }
                    speed++;    //  Add 1 speed
                    selectedIndex = index;
                    menuYTarget = selectedIndex * 35 + listMenu.getY(); //  menuYTarget is location y
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
                        callBack.call(selectedIndex);
                        if (event != null) {
                            event.menuIndexChange(selectedIndex);
                        }
                    } else {
                        menuY -= speed;
                        repaint();
                    }
                } else {
                    if (menuY >= menuYTarget + 5) { //  Add style
                        menuY = menuYTarget;
                        repaint();
                        timer.stop();
                        callBack.call(selectedIndex);
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

    }
        private void initData() {
      
    }

    private void startColorAnimation() {
        timer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (phase == 0) {
                    blend1 += 0.02f;
                    if (blend1 >= 1.0f) {
                        blend1 = 1.0f;
                        phase = 1;
                    }
                    color1 = blendColors(baseColor1, baseColor2, blend1);
                    color2 = blendColors(baseColor2, baseColor3, blend1);
                } else if (phase == 1) {
                    blend2 += 0.02f;
                    if (blend2 >= 1.0f) {
                        blend2 = 1.0f;
                        phase = 2;
                    }
                    color1 = blendColors(baseColor2, baseColor3, blend2);
                    color2 = blendColors(baseColor3, baseColor1, blend2);
                } else {
                    blend1 -= 0.02f;
                    if (blend1 <= 0.0f) {
                        blend1 = 0.0f;
                        blend2 = 0.0f;
                        phase = 0;
                    }
                    color1 = blendColors(baseColor3, baseColor1, blend1);
                    color2 = blendColors(baseColor1, baseColor2, blend1);
                }

                repaint();
            }
        });
        timer.start();
    }

    private Color blendColors(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(r, g, b);
    }

    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//       GradientPaint g = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#000046"), true);
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2, true);

        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grphcs);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ gradient động (từ đoạn mã cũ)
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2, true);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Vẽ hình chữ nhật và đường cong nếu có selectedIndex (từ đoạn mã mới)
        if (selectedIndex >= 0) {
            int menuX = 10;
            int height = 35;
            int width = getWidth() - 20; // Điều chỉnh để phù hợp với panel
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
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Quản Lý Bàn Ăn");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 700);
                frame.setLocationRelativeTo(null);

                Menu1 tableManager = new Menu1();
                frame.add(tableManager);

                frame.setVisible(true);
            }
        });
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();
        jColorChooser2 = new javax.swing.JColorChooser();
        panelMoving = new javax.swing.JPanel();

        panelMoving.setOpaque(false);

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 331, Short.MAX_VALUE)
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(617, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JColorChooser jColorChooser2;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables

}
