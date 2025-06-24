package Swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SquarePanel extends JPanel {

    private final Color baseColor1 = Color.decode("#FF6FD8");
    private final Color baseColor2 = Color.decode("#915EFF");
    private final Color baseColor3 = Color.decode("#5B247A");

    private Color color1 = baseColor1;
    private Color color2 = baseColor2;

    private float blend1 = 0f;
    private float blend2 = 0f;
    private int phase = 0;
    private Timer timer;
    private boolean isAnimating = false;

    public SquarePanel() {
        setPreferredSize(new Dimension(200, 200));
        startColorAnimation();
    }

    private void startColorAnimation() {
        if (timer != null && timer.isRunning()) {
            return; // Đã chạy rồi thì không cần chạy lại
        }

        timer = new Timer(60, new ActionListener() {
            private Color fromColor1 = baseColor1;
            private Color toColor1 = baseColor2;

            private Color fromColor2 = baseColor2;
            private Color toColor2 = baseColor3;

            private float progress = 0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 0.02f;
                if (progress >= 1f) {
                    progress = 0f;

                    fromColor1 = toColor1;
                    toColor1 = toColor2;

                    fromColor2 = toColor2;
                    toColor2 = getNextColor(toColor2);
                }

                color1 = blendColors(fromColor1, toColor1, progress);
                color2 = blendColors(fromColor2, toColor2, progress);
                repaint();
            }
        });

        timer.start();
        isAnimating = true;
    }

private void stopColorAnimation() {
    if (timer != null) {
          System.out.println("→ Đang gọi stop timer...");
        timer.stop();
        timer = null;
        isAnimating = false;

        // Reset về màu ban đầu (hoặc chọn màu tĩnh tùy ý)
        color1 = baseColor1;
        color2 = baseColor1;  // hoặc baseColor2
        repaint(); // bắt buộc để áp dụng lại ngay màu dừng
    }
}


public void toggleColorAnimation() {
    if (isAnimating) {
        stopColorAnimation();
    } else {
        startColorAnimation();
    }
}


    private Color getNextColor(Color current) {
        if (current.equals(baseColor1)) {
            return baseColor2;
        }
        if (current.equals(baseColor2)) {
            return baseColor3;
        }
        return baseColor1;
    }

    private Color blendColors(Color c1, Color c2, float ratio) {
        float ir = 1.0f - ratio;
        int red = (int) (c1.getRed() * ir + c2.getRed() * ratio);
        int green = (int) (c1.getGreen() * ir + c2.getGreen() * ratio);
        int blue = (int) (c1.getBlue() * ir + c2.getBlue() * ratio);
        return new Color(red, green, blue);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Gradient từ color1 đến color2 theo chiều dọc
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
