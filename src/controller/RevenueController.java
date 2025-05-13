package controller;

import Service.RevenueService;
import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Revenue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import view.page.GlowingChartPanel;
public class RevenueController {

    private RevenueService revenueService = null;

    public RevenueController() {
        this.revenueService = new RevenueService();
    }

  public void setDataToChart1(JPanel jpnItem) {
    List<Revenue> listItem = revenueService.getListRevenue();

    if (listItem == null || listItem.isEmpty()) {
        System.out.println("Không có dữ liệu doanh thu theo tháng để hiển thị.");
        return;
    }

    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

    for (Revenue item : listItem) {
        String monthLabel = "Tháng " + item.getMonth();
        barDataset.addValue(item.getRevenue(), "Doanh thu", monthLabel);
    }

    JFreeChart barChart = ChartFactory.createBarChart(
            "BIỂU ĐỒ DOANH THU THEO THÁNG",
            "Tháng", "Doanh thu (VNĐ)",
            barDataset, PlotOrientation.VERTICAL, true, true, false);

    CategoryPlot plot = barChart.getCategoryPlot();

    BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
    barRenderer.setSeriesPaint(0, new Color(22, 216, 160));
    barRenderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());
    barRenderer.setSeriesItemLabelsVisible(0, true);

    barChart.setAntiAlias(true);

    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));

    jpnItem.removeAll();
    jpnItem.setLayout(new CardLayout());
    jpnItem.add(chartPanel);
    jpnItem.validate();
    jpnItem.repaint();
}

    public void setDataToChart2(JPanel jpnItem) {
        List<Revenue> listItem = revenueService.getListDayRevenue();

        if (listItem == null || listItem.isEmpty()) {
            System.out.println("Không có dữ liệu doanh thu theo ngày để hiển thị.");
            return;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String[] dayNames = {"", "Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"};

        for (Revenue item : listItem) {
            int dayOfWeek = item.getDayOfWeek();
            double revenue = item.getRevenue();

            if (dayOfWeek < 1 || dayOfWeek > 7) {
                continue;
            }

            String label = dayNames[dayOfWeek];
            dataset.addValue(revenue, "Doanh thu", label);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "BIỂU ĐỒ DOANH THU THEO NGÀY TRONG TUẦN",
                "Ngày trong tuần", "Doanh thu (VND)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(22, 216, 160));
        renderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());
        renderer.setSeriesItemLabelsVisible(0, true);
        plot.setRenderer(renderer);

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));
        chartPanel.setBackground(new Color(144, 238, 144));

        jpnItem.removeAll();
        jpnItem.setLayout(new CardLayout());
        jpnItem.add(chartPanel);
        jpnItem.validate();
        jpnItem.repaint();
    }


public void setDataToLineChart1(JPanel jpnItem) {
    List<Revenue> listItem = revenueService.getListRevenue();

    if (listItem == null || listItem.isEmpty()) {
        System.out.println("Không có dữ liệu doanh thu theo tháng để hiển thị.");
        return;
    }

    XYSeries series = new XYSeries("Doanh thu");
    for (Revenue item : listItem) {
        series.add(item.getMonth(), item.getRevenue());
    }

    XYSeriesCollection dataset = new XYSeriesCollection(series);

    NumberAxis xAxis = new NumberAxis("Tháng");
    NumberAxis yAxis = new NumberAxis("Doanh thu (VNĐ)");
    xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    // Format tiền: 1,000,000 → 1.000.000₫
    NumberFormat currencyFormat = NumberFormat.getInstance();
    yAxis.setNumberFormatOverride(currencyFormat);

    XYSplineRenderer renderer = new XYSplineRenderer();
    renderer.setSeriesPaint(0, new Color(22, 216, 160));
    renderer.setSeriesStroke(0, new BasicStroke(2.5f));
    renderer.setSeriesShapesVisible(0, true);
    renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));
    renderer.setPrecision(12);

    XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
    plot.setBackgroundPaint(Color.WHITE);
    plot.setRangeGridlinePaint(new Color(192, 192, 192));
    plot.setDomainGridlinePaint(new Color(192, 192, 192));

    JFreeChart chart = new JFreeChart(
        "📈 BIỂU ĐỒ ĐƯỜNG CONG - DOANH THU THEO THÁNG",
        new Font("SansSerif", Font.BOLD, 16),
        plot,
        true
    );

    // Tuỳ chỉnh thêm phần title phụ
    chart.addSubtitle(new TextTitle("Nguồn: Hệ thống quản lý bán hàng", new Font("SansSerif", Font.ITALIC, 12)));

    chart.setBackgroundPaint(new Color(245, 245, 245)); // nền ngoài sáng
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 360));
    chartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    chartPanel.setBackground(Color.WHITE);

    jpnItem.removeAll();
    jpnItem.setLayout(new CardLayout());
    jpnItem.add(chartPanel);
    jpnItem.validate();
    jpnItem.repaint();
}
public void setDataToLineChart2(JPanel jpnItem) {
    List<Revenue> listItem = revenueService.getListDayRevenue();

    if (listItem == null || listItem.isEmpty()) {
        System.out.println("Không có dữ liệu doanh thu theo ngày để hiển thị.");
        return;
    }

    // Tạo dữ liệu XY với trục X là thứ trong tuần (1-7)
    XYSeries series = new XYSeries("Doanh thu");
    for (Revenue item : listItem) {
        int day = item.getDayOfWeek(); // 1 = Chủ Nhật, 7 = Thứ Bảy
        if (day >= 1 && day <= 7) {
            series.add(day, item.getRevenue());
        }
    }

    XYSeriesCollection dataset = new XYSeriesCollection(series);

    // Tạo trục X có nhãn tương ứng ngày trong tuần
    SymbolAxis xAxis = new SymbolAxis("Ngày", new String[]{"", "Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"});
    xAxis.setTickUnit(new NumberTickUnit(1));
    xAxis.setRange(0.5, 7.5);
    xAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));

    NumberAxis yAxis = new NumberAxis("Doanh thu (VNĐ)");
    yAxis.setNumberFormatOverride(NumberFormat.getInstance());
    yAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));

    XYSplineRenderer renderer = new XYSplineRenderer();
    renderer.setSeriesPaint(0, new Color(255, 102, 102));
    renderer.setSeriesStroke(0, new BasicStroke(2.5f));
    renderer.setSeriesShapesVisible(0, true);
    renderer.setSeriesShape(0, new Ellipse2D.Double(-4, -4, 8, 8));
    renderer.setPrecision(10);

    XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
    plot.setBackgroundPaint(Color.WHITE);
    plot.setRangeGridlinePaint(new Color(192, 192, 192));
    plot.setDomainGridlinePaint(new Color(192, 192, 192));

    JFreeChart chart = new JFreeChart(
        "📈 BIỂU ĐỒ CONG - DOANH THU THEO NGÀY TRONG TUẦN",
        new Font("SansSerif", Font.BOLD, 16),
        plot,
        true
    );

    chart.setBackgroundPaint(new Color(245, 245, 245));
    chart.addSubtitle(new TextTitle("Nguồn: Báo cáo hệ thống", new Font("SansSerif", Font.ITALIC, 12)));

    ChartPanel chartPanel = new GlowingChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 360));
    chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    chartPanel.setBackground(Color.WHITE);

    jpnItem.removeAll();
    jpnItem.setLayout(new CardLayout());
    jpnItem.add(chartPanel);
    jpnItem.validate();
    jpnItem.repaint();
}

}
