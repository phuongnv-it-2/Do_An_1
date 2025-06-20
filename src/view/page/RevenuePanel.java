package view.page;

import controller.RevenueController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class RevenuePanel extends JPanel {

    private JPanel jpnChart1;
    private JPanel jpnChart2;
    private JPanel jpnLineChart1;
    private JPanel jpnLineChart2;
    private JPanel jpnBarChartContainer;
    private JPanel jpnLineChartContainer;
    private RevenueController controller;

    public RevenuePanel() {
        initComponents();

        controller = new RevenueController();
        controller.setDataToChart1(jpnChart1);
        controller.setDataToChart2(jpnChart2);
        controller.setDataToLineChart1(jpnLineChart1);
        controller.setDataToLineChart2(jpnLineChart2);
    }

    private void initComponents() {
        // Khởi tạo các panel con
        jpnChart1 = new JPanel();
        jpnChart2 = new JPanel();
        jpnLineChart1 = new JPanel();
        jpnLineChart2 = new JPanel();


        jpnBarChartContainer = new JPanel(new GridLayout(1, 2, 10, 10));
        jpnBarChartContainer.add(jpnChart1);
        jpnBarChartContainer.add(jpnChart2);

 
        jpnLineChartContainer = new JPanel(new GridLayout(1, 2, 10, 10));
        jpnLineChartContainer.add(jpnLineChart1);
        jpnLineChartContainer.add(jpnLineChart2);

        // Tổng container chứa cả 2 loại biểu đồ
        JPanel jpnMainChartContainer = new JPanel(new GridLayout(2, 1, 10, 10));
        jpnMainChartContainer.add(jpnBarChartContainer);
        jpnMainChartContainer.add(jpnLineChartContainer);
        jpnMainChartContainer.setPreferredSize(new Dimension(900, 700));

        // Scroll pane nếu nội dung quá lớn
        JScrollPane scrollPane = new JScrollPane(jpnMainChartContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Nút reset biểu đồ
        JButton btnReset = new JButton("Reset Biểu Đồ");
        btnReset.addActionListener(e -> {
            controller.setDataToChart1(jpnChart1);
            controller.setDataToChart2(jpnChart2);
            controller.setDataToLineChart1(jpnLineChart1);
            controller.setDataToLineChart2(jpnLineChart2);
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnReset);

        // Layout chính của RevenuePanel
        setLayout(new BorderLayout(10, 10));
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Revenue Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 800);
            frame.setLocationRelativeTo(null);
            frame.add(new RevenuePanel());
            frame.setVisible(true);
        });
    }
}
