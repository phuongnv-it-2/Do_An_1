package Service;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;

public class ImageRenderer extends DefaultTableCellRenderer {
     private final String TABLE_IMAGE_PATH = "/icon/restaurant.png";
    private final ImageIcon tableIcon = new ImageIcon(getClass().getResource(TABLE_IMAGE_PATH));
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        JLabel lbl = new JLabel();
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setOpaque(true); // Đảm bảo JLabel có nền để hiển thị màu khi được chọn

        if (value != null && value instanceof String) {
            String imagePath = (String) value;
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                try {
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                    lbl.setIcon(new ImageIcon(img));
                    lbl.setText("");
                } catch (Exception e) {
                    lbl.setIcon(null);
                    lbl.setText("Lỗi tải ảnh");
                    System.out.println("Lỗi tải ảnh từ: " + imagePath + " - " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                lbl.setIcon(null);
                lbl.setText("No Image");
                System.out.println("File ảnh không tồn tại: " + imagePath);
            }
        } else {
            lbl.setIcon(null);
            lbl.setText("No Image");
        }

        // Xử lý màu nền và màu chữ khi ô được chọn
        if (isSelected) {
            lbl.setBackground(table.getSelectionBackground());
            lbl.setForeground(table.getSelectionForeground());
        } else {
            lbl.setBackground(table.getBackground());
            lbl.setForeground(table.getForeground());
        }

        return lbl;
    }
    
    public Icon getTableIcon() {
        return tableIcon;
    }

}