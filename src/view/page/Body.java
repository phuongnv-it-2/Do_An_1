package view.page;

import Component.Menu;
import Swing.Setting;
import Swing.SquarePanel;
import form.Home;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class Body extends JPanel {

    private final CardLayout CardLayout;
    private final Form_Home form_home;
    private final TableManager tableManager;

    private RevenuePanel revenuePanel;
    private AddEmployee addEmployee;
    private ProductManager addProduct;
//     private BillPanel billPanel;
    private Form_Home_Material form_Home_Material;
    private AddMaterial addMaterial;
    private Menu menu;
    private Home home;
    private VongQuayDoHoa vongQuayDoHoa;
    private BillManager billManager;
    private DiscountManager discountManager;
    private Setting setting;
       private SquarePanel squarePanel;


    public Body() {
        initComponents();

        CardLayout = new CardLayout();
        setLayout(CardLayout);

        form_home = new Form_Home();
        tableManager = new TableManager();
        addEmployee = new AddEmployee();
        addProduct = new ProductManager();
        revenuePanel = new RevenuePanel();
        form_Home_Material = new Form_Home_Material();
        addMaterial = new AddMaterial();
        menu = new Menu();
        home = new Home();
        vongQuayDoHoa = new VongQuayDoHoa();
        billManager = new BillManager();
        discountManager = new DiscountManager();
        setting = new Setting(this);
        squarePanel= new SquarePanel();

        add(form_Home_Material, "Form_Home_Material");
        add(form_home, "Form_Home");
        add(tableManager, "TableManager");
        add(addProduct, "AddProduct");
        add(revenuePanel, "RevenuePanel");
        add(addEmployee, "AddEmployee");
        add(vongQuayDoHoa, "VongQuayDoHoa");
        add(addMaterial, "AddMaterial");
        add(home, "Home");
        add(billManager, "BillManager");
        add(discountManager, "DiscountManager");
        add(setting, "Setting");
        add(squarePanel, "SquarePanel");
        add(menu, "Menu");



    }

    public CardLayout getCardLayout() {
        return CardLayout;
    }

    public Form_Home getForm_Home() {
        return form_home;
    }

    public Form_Home_Material getForm_Home_Material() {
        return form_Home_Material;
    }

    public ProductManager getAddProduct() {
        return addProduct;
    }

    public Menu getMenu() {
        return menu;
    }

    public Home getHome() {
        return home;
    }
      public SquarePanel getSquarePanel() {
        return squarePanel;
    }

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
