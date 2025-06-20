package Main;

import Service.Service;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import model.ModelUser;
import java.util.List;
import java.util.ArrayList;
import view.page.Body;
import Component.Menu;
import Event.EventMenu;
import com.formdev.flatlaf.FlatLightLaf;
import io.socket.client.Ack;
import java.util.ArrayList;
import java.util.Arrays;
//
public class SystemMain extends javax.swing.JFrame {

    private JPanel contentPane;
    private final Menu menu;
    private Body body;
    private CardLayout cardLayout;
    private Service service;

//    private BillPanel billPanel;
    public SystemMain() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Violet Resturant");
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new MigLayout("fillx, filly", "0[300!]0[fill]0", "0[fill]0"));
        body = new Body();
        menu = new Menu();

//        billPanel=new BillPanel();
        menu.addEventMenu(new EventMenu() {
            @Override
            public void menuIndexChange(int index) {
                CardLayout cardLayout = body.getCardLayout();
                if (cardLayout == null || body == null) {
                    System.err.println("CardLayout or Body is null. Cannot switch panel.");
                    return;
                }

                switch (index) {
                    case 0: // Kho nguyên liệu thô
                        cardLayout.show(body, "Form_Home_Material");

                        break;
                    case 1:
//                        cardLayout.show(billPanel, "BillPanel");
                        cardLayout.show(body, "AddMaterial");
                        reset();
                        break;
                    case 2:
                        cardLayout.show(body, "Form_Home");
                        reset();
                        break;
                    case 3:
                        cardLayout.show(body, "AddProduct");
                        reset();
                        break;
                    case 4:
                        cardLayout.show(body, "TableManager");
                        reset();
                        break;
                    case 5:
                        cardLayout.show(body, "AddEmployee");
                        reset();
                        break;
                    case 6:
                        cardLayout.show(body, "RevenuePanel");
                        break;
                    case 7:
                        cardLayout.show(body, "Home");
                        break;
                    case 8:
                        cardLayout.show(body, "BillManager");
                        break;
                           case 9:
                        cardLayout.show(body, "DiscountManager");
                        break;
                           case 10:
                        cardLayout.show(body, "VongQuayDoHoa");
                        break;
                           case 11:
                        cardLayout.show(body, "Home");
                        break;
                    default:
                        cardLayout.show(body, "Form_Home");
                        break;
                }
            }
        });

        contentPane.add(menu, "cell 0 0,grow,push");
        contentPane.add(body, "cell 1 0, grow, push");

        setContentPane(contentPane);

        // Không khởi tạo lại body ở đây
        // body = new Body(); // Xóa dòng này
        service = Service.getInstance(this);

    }

    public Body getBody() {
        return body;
    }

    public void reset() {
        // Thêm logic đặt lại trạng thái nếu cần
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1554, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

 public static void main(String args[]) throws UnsupportedLookAndFeelException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SystemMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        UIManager.setLookAndFeel(new FlatLightLaf());

        java.awt.EventQueue.invokeLater(() -> new SystemMain().setVisible(true));
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

