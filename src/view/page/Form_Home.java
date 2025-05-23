package view.page;

import Component.Card;
import Swing.ScrollBar;
import Swing.WrapLayout;
import javax.swing.ImageIcon;
import model.Model_Card;
import Service.CardService;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class Form_Home extends javax.swing.JPanel {

    private Service.CardService cardService;

    public Form_Home() {
        initComponents();
        cardService = new CardService();
        init();
    }

    private void init() {
        panel.setLayout(new WrapLayout(WrapLayout.LEADING));
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        List<Model_Card> cards = cardService.getAllCards();
        for (Model_Card card : cards) {
            Card productCard = new Card(card);
            panel.add(productCard);
        }
      panel.revalidate();
        panel.repaint();
    }
        void reloadProducts() {
        panel.removeAll(); 
          List<Model_Card> cards = cardService.getAllCards();
        for (Model_Card card : cards) {
            Card productCard = new Card(card);
            panel.add(productCard);
        }
      panel.revalidate();
        panel.repaint();
            System.out.println("view.page.Form_Home.reloadProducts()");
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(250, 250, 250));

        jScrollPane1.setBorder(null);

        panel.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 895, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
