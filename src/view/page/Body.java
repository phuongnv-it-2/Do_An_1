
package view.page;

import Component.Form_Home;
import java.awt.CardLayout;
import javax.swing.JPanel;


public class Body extends JPanel {

    private final CardLayout CardLayout;
    private final Form_Home form_home ;
        private final TableManager  tableManager ;
//    private final AddProduct addProduct;
//      private final RevenuePanel revenuePanel;
//     private final AddEmployee addEmployee;
//       private final BillPanel billPanel;
//       	public boolean[] tang;

       

    public Body() {
        initComponents();
  
        CardLayout = new CardLayout();
        setLayout(CardLayout);
        
      
       form_home = new Form_Home();  
       tableManager =new TableManager();
//        addProduct = new AddProduct();  
//         revenuePanel = new RevenuePanel();
//        addEmployee = new AddEmployee();
//          billPanel = new BillPanel();

 
        add(form_home, "Form_Home");  
//           add(tableManager, "TableManager");  
//        add(addProduct, "AddProduct");
//         add(revenuePanel, "RevenuePanel");
//           add(addEmployee, "AddEmployee");
//            add(billPanel, "BillPanel");
   
     
        
    }
     public CardLayout getCardLayout() {
        return CardLayout;
    }

    public Form_Home getForm_Home() {
        return form_home;
    }

//    public AddProduct getAddProduct() {
//        return addProduct;
//    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 153, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1240, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 842, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
