package form;

import Component.Chat_Body;
import Component.Chat_Bottom;
import Component.Chat_Title;
import Event.EventChat;
import Event.PublicEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model_Receive_Message;
import model.Model_Send_Message;


import net.miginfocom.swing.MigLayout;

public class Chat extends javax.swing.JPanel {
    private Chat_Title chatTitle;
        private Chat_Body chatBody;
            private Chat_Bottom chatBottom;
    
    public Chat() {
        initComponents();
        init();
    }

   private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[100%, fill]0[shrink 0]0"));
        chatTitle = new Chat_Title();
        chatBody = new Chat_Body();
        chatBottom = new Chat_Bottom();
      PublicEvent.getInstance().addEventChat(new EventChat() {
            public void sendMessage(String text) {
                chatBody.addItemRight(text);
                try {
                    Client.Client.getInstance().sendMessageToServer(text);
                } catch (IOException ex) {
                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            @Override
            public void receiveMessage(String data) {
                 chatBody.addItemLeft(data);
            }
        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::50%");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 681, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
