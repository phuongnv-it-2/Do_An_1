package Service;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;

public class HoverInfoHandler extends MouseMotionAdapter implements MouseListener {
    private String name;
    private double price;
    private String category;
    private int prepTime;
    private String dietaryInfo;

    public HoverInfoHandler(String name, double price, String category, int prepTime, String dietaryInfo) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.prepTime = prepTime;
        this.dietaryInfo = dietaryInfo;
    }

    public HoverInfoHandler(String name, double price) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        JLabel label = new JLabel("<html>Tên: " + name + "<br>Danh mục: " + (category != null ? category : "N/A") +
                "<br>Giá: " + String.format("%.1f VNĐ", price) +
                "<br>Thời gian chuẩn bị: " + (prepTime > 0 ? prepTime + " phút" : "N/A") +
                "<br>Thông tin dinh dưỡng: " + (dietaryInfo != null ? dietaryInfo : "N/A") + "</html>");
        label.setBounds(e.getXOnScreen(), e.getYOnScreen(), 200, 100);
        // Add tooltip or popup logic here if needed
    }

    // Implement MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
      
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // No action needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No action needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // No action needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // No action needed
    }
}