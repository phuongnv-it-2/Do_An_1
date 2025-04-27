
package view.page;


import Service.Service;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class DashBoard extends javax.swing.JPanel {

     public DashBoard( ) {
        initComponents();
        this.body = new Body();
    }
     

   private Body body;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_adduser = new javax.swing.JLabel();
        BillManager = new javax.swing.JLabel();
        lb_Addproducts = new javax.swing.JLabel();
        lb_statistical = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lb_caffemanager = new javax.swing.JLabel();

        setBackground(new java.awt.Color(22, 216, 160));

        lb_adduser.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lb_adduser.setForeground(new java.awt.Color(255, 255, 255));
        lb_adduser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_adduser.setText("Add Employee");
        lb_adduser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_adduserMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_adduserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_adduserMouseExited(evt);
            }
        });

        BillManager.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        BillManager.setForeground(new java.awt.Color(255, 255, 255));
        BillManager.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BillManager.setText("Bill Manager");
        BillManager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BillManagerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BillManagerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BillManagerMouseExited(evt);
            }
        });

        lb_Addproducts.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lb_Addproducts.setForeground(new java.awt.Color(255, 255, 255));
        lb_Addproducts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_Addproducts.setText("Add Products");
        lb_Addproducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_AddproductsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_AddproductsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_AddproductsMouseExited(evt);
            }
        });

        lb_statistical.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lb_statistical.setForeground(new java.awt.Color(255, 255, 255));
        lb_statistical.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_statistical.setText("Statistical");
        lb_statistical.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_statisticalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_statisticalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_statisticalMouseExited(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 102));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("DashBoard");
        jLabel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lb_caffemanager.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lb_caffemanager.setForeground(new java.awt.Color(255, 255, 255));
        lb_caffemanager.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_caffemanager.setText("Caffe Manager");
        lb_caffemanager.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lb_caffemanager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_caffemanagerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_caffemanagerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lb_caffemanagerMouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BillManager, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_statistical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(lb_Addproducts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_adduser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_caffemanager, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(lb_caffemanager, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lb_Addproducts, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(BillManager, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lb_adduser, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lb_statistical, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lb_caffemanagerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_caffemanagerMouseEntered
        // TODO add your handling code here:
        lb_caffemanager.setBackground(new Color(90, 241, 197));
        lb_caffemanager.setOpaque(true);
     
    }//GEN-LAST:event_lb_caffemanagerMouseEntered

    private void lb_caffemanagerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_caffemanagerMouseExited
        // TODO add your handling code here:
        lb_caffemanager.setBackground(new Color(22, 216, 160 ));
        lb_caffemanager.setOpaque(true);
    }//GEN-LAST:event_lb_caffemanagerMouseExited

    private void lb_adduserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_adduserMouseEntered
        // TODO add your handling code here:
         lb_adduser.setBackground(new Color(90, 241, 197));
          lb_adduser.setOpaque(true);
    }//GEN-LAST:event_lb_adduserMouseEntered

    private void lb_adduserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_adduserMouseExited
        // TODO add your handling code here:
        lb_adduser.setBackground(new Color(22, 216, 160 ));
        lb_adduser.setOpaque(true);
    }//GEN-LAST:event_lb_adduserMouseExited

    private void BillManagerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BillManagerMouseEntered
 BillManager.setBackground(new Color(90, 241, 197));    
    BillManager.setOpaque(true);
    }//GEN-LAST:event_BillManagerMouseEntered

    private void BillManagerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BillManagerMouseExited
        // TODO add your handling code here:
        BillManager.setBackground(new Color(22, 216, 160 ));
         BillManager.setOpaque(true);
    }//GEN-LAST:event_BillManagerMouseExited

    private void lb_AddproductsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_AddproductsMouseEntered
        // TODO add your handling code here:
          lb_Addproducts.setBackground(new Color(90, 241, 197));
          lb_Addproducts.setOpaque(true);
    }//GEN-LAST:event_lb_AddproductsMouseEntered

    private void lb_AddproductsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_AddproductsMouseExited
        // TODO add your handling code here:
      lb_Addproducts.setBackground(new Color(22, 216, 160 ));
      lb_Addproducts.setOpaque(true);
    }//GEN-LAST:event_lb_AddproductsMouseExited

    private void lb_statisticalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_statisticalMouseEntered
        // TODO add your handling code here:
          lb_statistical.setBackground(new Color(90, 241, 197));
          lb_statistical.setOpaque(true);
    }//GEN-LAST:event_lb_statisticalMouseEntered

    private void lb_statisticalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_statisticalMouseExited
        // TODO add your handling code here:
            lb_statistical.setBackground(new Color(22, 216, 160 ));
            lb_statistical.setOpaque(true);

    }//GEN-LAST:event_lb_statisticalMouseExited

    private void lb_caffemanagerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_caffemanagerMouseClicked
         Service.getInstance().getmain().getBody().getCardLayout().show(Service.getInstance().getmain().getBody(), "CaffeManager");
				reset();
                               
    }//GEN-LAST:event_lb_caffemanagerMouseClicked

    private void lb_AddproductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_AddproductsMouseClicked
    Service.getInstance().getmain().getBody().getCardLayout().show(Service.getInstance().getmain().getBody(), "AddProduct");
				reset();
    }//GEN-LAST:event_lb_AddproductsMouseClicked

    private void lb_statisticalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_statisticalMouseClicked
       Service.getInstance().getmain().getBody().getCardLayout().show(Service.getInstance().getmain().getBody(), "RevenuePanel");
				reset();
    }//GEN-LAST:event_lb_statisticalMouseClicked

    private void lb_adduserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_adduserMouseClicked
        Service.getInstance().getmain().getBody().getCardLayout().show(Service.getInstance().getmain().getBody(), "AddEmployee");
				reset();
    }//GEN-LAST:event_lb_adduserMouseClicked

    private void BillManagerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BillManagerMouseClicked
        Service.getInstance().getmain().getBody().getCardLayout().show(Service.getInstance().getmain().getBody(), "BillPanel");
				reset();
    }//GEN-LAST:event_BillManagerMouseClicked
    public void reset() {
	}
    





    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BillManager;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lb_Addproducts;
    private javax.swing.JLabel lb_adduser;
    private javax.swing.JLabel lb_caffemanager;
    private javax.swing.JLabel lb_statistical;
    // End of variables declaration//GEN-END:variables

}
