package view.page;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Timer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class GlowingChartPanel extends ChartPanel {
    private float glowPosition = 0f;
    private Timer timer;

    public GlowingChartPanel(JFreeChart chart) {
        super(chart);
        setOpaque(false);

        // Cập nhật glow mỗi 40ms, với bước nhỏ
        timer = new Timer(40, e -> {
            glowPosition += 0.02f;
            if (glowPosition > 1f) glowPosition = 0f;
            repaint();
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ ánh sáng trắng mờ theo đường cong
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int x = (int) (width * glowPosition);
        int y = (int) (height / 2 * (Math.sin(glowPosition * 2 * Math.PI) + 1)); 

        // Hiệu ứng mờ trắng
        GradientPaint gradient = new GradientPaint(
                x - 50, y, new Color(255, 255, 255, 0),
                x, y, new Color(255, 255, 255, 160),
                true
        );

        g2.setPaint(gradient);
        g2.fillRect(x - 50, 0, 100, height);

        g2.dispose();
    }
}
