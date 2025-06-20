
package Component;

import java.awt.event.ActionListener;
import javax.swing.JFrame;


public class PanelPassword extends javax.swing.JPanel {

    public PanelPassword() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        squarePanel1 = new Swing.SquarePanel();
        cmdOK = new Swing.Button();
        jLabel1 = new javax.swing.JLabel();
        txtPassword1 = new Swing.MyPasswordField();
        txtPassword2 = new Swing.MyPasswordField();
        cmdCancell = new Swing.Button();

        cmdOK.setBackground(new java.awt.Color(0, 255, 204));
        cmdOK.setText("Ok");
        cmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOKActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Entter Your New Password");

        txtPassword1.setHint("Your Password");

        txtPassword2.setHint("Your Password Again");

        cmdCancell.setBackground(new java.awt.Color(255, 51, 51));
        cmdCancell.setText("cancel");
        cmdCancell.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmdCancell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancellActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout squarePanel1Layout = new javax.swing.GroupLayout(squarePanel1);
        squarePanel1.setLayout(squarePanel1Layout);
        squarePanel1Layout.setHorizontalGroup(
            squarePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(squarePanel1Layout.createSequentialGroup()
                .addGroup(squarePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(squarePanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel1))
                    .addGroup(squarePanel1Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addGroup(squarePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(squarePanel1Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdCancell, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        squarePanel1Layout.setVerticalGroup(
            squarePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(squarePanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(squarePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdCancell, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(squarePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(squarePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOKActionPerformed

    }//GEN-LAST:event_cmdOKActionPerformed

    private void cmdCancellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancellActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cmdCancellActionPerformed
  
  
           public String getInputPass1() {
        return txtPassword1.getText().trim();
    }
                 public String getInputPass2() {
        return txtPassword2.getText().trim();
    }

    public void addEventButtonOK(ActionListener event) {
        cmdOK.addActionListener(event);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Swing.Button cmdCancell;
    private Swing.Button cmdOK;
    private javax.swing.JLabel jLabel1;
    private Swing.SquarePanel squarePanel1;
    private Swing.MyPasswordField txtPassword1;
    private Swing.MyPasswordField txtPassword2;
    // End of variables declaration//GEN-END:variables
}
