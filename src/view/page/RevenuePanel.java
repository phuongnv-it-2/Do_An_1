package view.page;

import controller.RevenueController;
import java.awt.BorderLayout;
import javax.swing.*;

public class RevenuePanel extends javax.swing.JPanel {

    public RevenuePanel() {
        initComponents(); 

        RevenueController controller = new RevenueController();
        controller.setDataToChart1(jpnChart1);
        controller.setDataToChart2(jpnChart2);
    }

    private void initComponents() {
        jpnChart1 = new JPanel();
        jpnChart2 = new JPanel();

        // Thiết lập kích thước mặc định
        jpnChart1.setPreferredSize(new java.awt.Dimension(800, 400));
        jpnChart2.setPreferredSize(new java.awt.Dimension(800, 400));

        JScrollPane scrollPane1 = new JScrollPane(jpnChart1);
        JScrollPane scrollPane2 = new JScrollPane(jpnChart2);

        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane1, scrollPane2);
        splitPane.setResizeWeight(0.5); 

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel jpnChart1;
    private JPanel jpnChart2;
}
