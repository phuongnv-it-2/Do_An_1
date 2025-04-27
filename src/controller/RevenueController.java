package controller;

import Service.RevenueService;
import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JPanel;
import model.Revenue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class RevenueController {

    private RevenueService revenueService = null;

    public RevenueController() {
        this.revenueService = new RevenueService();
    }

public void setDataToChart1(JPanel jpnItem) {
    List<Revenue> listItem = revenueService.getListRevenue();

    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    DefaultCategoryDataset lineDataset = new DefaultCategoryDataset(); // Dataset riêng cho đường xu hướng

    if (listItem != null) {
        for (Revenue item : listItem) {
            String monthLabel = "Tháng " + item.getMonth();
            barDataset.addValue(item.getRevenue(), "Doanh thu", monthLabel);
            lineDataset.addValue(item.getRevenue(), "Xu hướng", monthLabel); // Dữ liệu riêng cho đường xu hướng
        }
    }

    JFreeChart barChart = ChartFactory.createBarChart(
            "Biểu đồ doanh thu theo tháng".toUpperCase(),
            "Tháng", "Doanh thu (VNĐ)",
            barDataset, PlotOrientation.VERTICAL, true, true, false);

    CategoryPlot plot = barChart.getCategoryPlot();

    // Cấu hình cho cột
    BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
    barRenderer.setSeriesPaint(0, new Color(22, 216, 160)); 
    barRenderer.setSeriesItemLabelGenerator(0, new StandardCategoryItemLabelGenerator());
    barRenderer.setSeriesItemLabelsVisible(0, true);

    // Tạo dataset riêng cho đường xu hướng
    LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
    lineRenderer.setSeriesPaint(0, Color.BLACK);
    lineRenderer.setSeriesStroke(0, new BasicStroke(2.0f));

    barChart.setAntiAlias(true);

    // Gán dataset riêng cho đường xu hướng
    plot.setDataset(1, lineDataset);  
    plot.setRenderer(1, lineRenderer);
    plot.mapDatasetToRangeAxis(1, 0);

    // Cập nhật JPanel để hiển thị biểu đồ
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

    for (Revenue item : listItem) {
        Date ngayMua = item.getNgayMua();

        if (ngayMua == null) {
            continue;  
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ngayMua);

        int day = calendar.get(Calendar.DAY_OF_MONTH); 
        int month = calendar.get(Calendar.MONTH) + 1; 
        int year = calendar.get(Calendar.YEAR);
        double revenue = item.getRevenue();

        String label = String.format("%02d/%02d", day, month);

        dataset.addValue(revenue, "Doanh thu", label);
    }

    JFreeChart barChart = ChartFactory.createBarChart(
            "BIỂU ĐỒ DOANH THU THEO NGÀY",
            "Ngày", "Doanh thu (VND)", dataset, 
            PlotOrientation.VERTICAL, true, true, false
    );
    
    
     CategoryPlot plot = barChart.getCategoryPlot();
         BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(22,216,160)); 
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


}
