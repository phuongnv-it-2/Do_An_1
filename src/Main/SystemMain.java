package Main;


import Service.Service;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import Main.*;
import model.ModelUser;

import view.page.Body;
import view.page.TableManager;
import Component.Menu;
import Event.EventMenu;
import view.page.BillPanel;

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
                        cardLayout.show(body, "Form_Home");
                        break;
                    case 7: 
                        cardLayout.show(body, "Form_Home");
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

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SystemMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SystemMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SystemMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SystemMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SystemMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
