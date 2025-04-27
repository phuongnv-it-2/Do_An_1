
package Component;

import Main.main;
import Service.ServiceMail;
import Service.ServiceUser;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import model.ModelLogin;
import model.ModelMessage;
import model.ModelUser;


    public class PanelForgetPassword extends javax.swing.JFrame {
     private ServiceUser serviceUser; 
        private ServiceMail serviceMail; 
         private main parentFrame;
         ModelUser user;
        private ModelLogin dataLogin;

        public PanelForgetPassword() {

            this.serviceUser = new ServiceUser();
            this.serviceMail = new ServiceMail();
            initComponents();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            addMouseListener(new MouseAdapter() {
            });
            
        }
        public ModelUser getUser() {
            String userName = UserText.getText().trim();
            String email = emailText.getText().trim();
            user = new ModelUser(0, userName, email);
            return user;
        }
        public ModelLogin getDataLogin() {
            return dataLogin;
        }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        squarePanel2 = new Swing.SquarePanel();
        panelRound1 = new Swing.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        UserText = new Swing.MyTextField();
        emailText = new Swing.MyTextField();
        cmdOK = new Swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(63, 63, 63));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Forget Password");

        UserText.setHint("Email");
        UserText.setHint("Your UserName");
        UserText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserTextActionPerformed(evt);
            }
        });

        emailText.setHint("Your Email");

        cmdOK.setBackground(new java.awt.Color(126, 234, 172));
        cmdOK.setText("Ok");
        cmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UserText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(UserText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmdOK, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout squarePanel2Layout = new javax.swing.GroupLayout(squarePanel2);
        squarePanel2.setLayout(squarePanel2Layout);
        squarePanel2Layout.setHorizontalGroup(
            squarePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, squarePanel2Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );
        squarePanel2Layout.setVerticalGroup(
            squarePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(squarePanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(squarePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(squarePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGap(3, 3, 3)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UserTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserTextActionPerformed

    private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOKActionPerformed
//    String userName = UserText.getText().trim();
//                String email = emailText.getText().trim();
//                  user = new ModelUser(0, userName, email);
//                  System.out.println("Component.PanelForgetPassword.cmdOKActionPerformed()");
    }//GEN-LAST:event_cmdOKActionPerformed
    public void addEventButtonOK(ActionListener event) {
        cmdOK.addActionListener(event);
    }
    
    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelForgetPassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Swing.MyTextField UserText;
    private Swing.Button cmdOK;
    private Swing.MyTextField emailText;
    private javax.swing.JLabel jLabel1;
    private Swing.PanelRound panelRound1;
    private Swing.SquarePanel squarePanel2;
    // End of variables declaration//GEN-END:variables
}
