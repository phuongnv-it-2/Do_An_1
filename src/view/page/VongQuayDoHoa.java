package view.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.sql.Date;
import java.util.ArrayList;
import model.Discount;

public class VongQuayDoHoa extends JPanel implements ActionListener {

    private final String[] giaiThuong = {
        "Không trúng", "Giảm 5%", "Giảm 10%", "Giảm 20%",
        "Giảm 50%", "Giảm 50k", "Giảm 100k", "Giảm 10k"
    };

    private double angle = 0; // góc quay hiện tại
    private double targetAngle = 0; // góc quay cần đạt
    private Timer timer;
    private JButton btnQuay;
    private boolean isSpinning = false;
    private JLabel lblKetQua;
    private ArrayList<Discount> discountList = new ArrayList<>(); // Danh sách mã giảm giá

    // Mảng 8 màu sắc tươi sáng
    Color[] colors = {
        new Color(255, 102, 102), // đỏ nhạt
        new Color(255, 178, 102), // cam nhạt
        new Color(255, 255, 102), // vàng nhạt
        new Color(102, 255, 102), // xanh lá nhạt
        new Color(102, 255, 255), // xanh dương nhạt
        new Color(102, 178, 255), // xanh da trời
        new Color(178, 102, 255), // tím nhạt
        new Color(255, 200, 102) // vàng cam
    };

    public VongQuayDoHoa() {
        super(true); // Đảm bảo double buffering
        setPreferredSize(new Dimension(500, 600));
        setBackground(Color.WHITE);

        btnQuay = new JButton("QUAY");
        btnQuay.setFont(new Font("Arial", Font.BOLD, 24));
        btnQuay.addActionListener(e -> quayVong());
        lblKetQua = new JLabel("Ấn QUAY để bắt đầu", SwingConstants.CENTER);
        lblKetQua.setFont(new Font("Arial", Font.BOLD, 18));

        setLayout(new BorderLayout());
        add(lblKetQua, BorderLayout.NORTH);
        add(btnQuay, BorderLayout.SOUTH);

        timer = new Timer(20, this); // Tăng thời gian cập nhật lên 20ms để giảm nháy

        // Tải danh sách mã giảm giá từ dữ liệu đã cung cấp
        loadDiscounts();
    }

    private void loadDiscounts() {
        discountList.clear();
        discountList.add(new Discount("KHONGTRUNG", "Không trúng", 0, 0, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 1000, 0));
        discountList.add(new Discount("GIAM5", "Giảm 5%", 5, 0, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 500, 0));
        discountList.add(new Discount("GIAM10", "Giảm 10%", 10, 0, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 500, 0));
        discountList.add(new Discount("GIAM20", "Giảm 20%", 20, 0, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 300, 0));
        discountList.add(new Discount("GIAM50", "Giảm 50%", 50, 0, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 200, 0));
        discountList.add(new Discount("GIAM50K", "Giảm 50k", 0, 50000, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 400, 0));
        discountList.add(new Discount("GIAM100K", "Giảm 100k", 0, 100000, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 100, 0));
        discountList.add(new Discount("GIAM10K", "Giảm 10k", 0, 10000, Date.valueOf("2025-05-01"), Date.valueOf("2025-12-31"), 1000, 0));
    }

    private void quayVong() {
        if (isSpinning) {
            return;
        }

        isSpinning = true;
        btnQuay.setEnabled(false);

        Random rand = new Random();
        int diemTrung = rand.nextInt(giaiThuong.length);
        double anglePer = 360.0 / giaiThuong.length;

        double minAngle = diemTrung * anglePer;
        double maxAngle = minAngle + anglePer;

        // Quay 3–5 vòng (đã tối ưu cho quay nhanh)
        double randomAngleInSection = minAngle + rand.nextDouble() * anglePer;
        double angleTarget = 360 * (3 + rand.nextInt(3)) + (360 - randomAngleInSection);

        double delta = angleTarget - (angle % 360);
        if (delta < 0) {
            delta += 360;
        }

        speed = delta / 15.0; // Tốc độ ban đầu nhanh
        targetAngle = angleTarget;
        timer.start();
    }

    private double speed = 0;
    private double friction = 0.98; // Ma sát để dừng nhanh

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Math.abs(speed) > 0.5) { // Ngưỡng dừng cao để dừng sớm
            angle += speed;
            speed *= friction;
            // Chỉ repaint nếu góc thay đổi đáng kể
            if (Math.abs(speed) > 0.1) { // Giảm repaint khi tốc độ thấp
                repaint();
            }
        } else {
            repaint(); // Repaint lần cuối để đảm bảo hiển thị đúng
            timer.stop();
            isSpinning = false;
            btnQuay.setEnabled(true);

            double anglePer = 360.0 / giaiThuong.length;
            double adjustedAngle = (360 - (angle % 360)) % 360;
            int trungIndex = (int) (adjustedAngle / anglePer) % giaiThuong.length;
            String ketQua = giaiThuong[trungIndex];

            Discount selectedDiscount = findMatchingDiscount(ketQua);
            if (selectedDiscount != null) {
                java.util.Date today = new java.util.Date();
                if (today.before(selectedDiscount.getNgayBatDau()) || today.after(selectedDiscount.getNgayKetThuc())) {
                    lblKetQua.setText("Kết quả: " + ketQua + " (Mã " + selectedDiscount.getMaGiamGia() + " - Hết hạn)");
                } else if (selectedDiscount.getSoLanDaSuDung() >= selectedDiscount.getSoLanSuDung()) {
                    lblKetQua.setText("Kết quả: " + ketQua + " (Mã " + selectedDiscount.getMaGiamGia() + " - Hết lượt sử dụng)");
                } else {
                    updateSoLanDaSuDung(selectedDiscount);
                    lblKetQua.setText("Kết quả: " + ketQua + " (Mã: " + selectedDiscount.getMaGiamGia() + ")");
                }
            } else {
                lblKetQua.setText("Kết quả: " + ketQua + " (Không tìm thấy mã giảm giá phù hợp)");
            }
        }
    }

    private Discount findMatchingDiscount(String ketQua) {
        String tenGiamGia = ketQua.toLowerCase();
        for (Discount discount : discountList) {
            String discountTenGiamGia = discount.getTenGiamGia().toLowerCase();
            if (discountTenGiamGia.equals(tenGiamGia)) {
                return discount;
            }
        }
        return null;
    }

    private void updateSoLanDaSuDung(Discount discount) {
        int newSoLanDaSuDung = discount.getSoLanDaSuDung() + 1;
        discount.setSoLanDaSuDung(newSoLanDaSuDung);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Bật anti-aliasing để làm mịn văn bản và hình vẽ
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        drawWheel(g2d);
    }

    private void drawWheel(Graphics2D g2d) {
        int size = 400;
        int y = 100;
        double anglePer = 360.0 / giaiThuong.length;

        AffineTransform old = g2d.getTransform();
        g2d.translate(getWidth() / 2, y + size / 2);
        g2d.rotate(Math.toRadians(angle));

        for (int i = 0; i < giaiThuong.length; i++) {
            g2d.setColor(colors[i % colors.length]);
            g2d.fillArc(-size / 2, -size / 2, size, size, 0, (int) anglePer);

            g2d.setColor(Color.BLACK);
            g2d.drawArc(-size / 2, -size / 2, size, size, 0, (int) anglePer);

            String text = giaiThuong[i];
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            AffineTransform textTransform = g2d.getTransform();
            g2d.rotate(Math.toRadians(anglePer / 2));
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, -textWidth / 2, -size / 2 + 60);
            g2d.setTransform(textTransform);

            g2d.rotate(Math.toRadians(anglePer));
        }

        g2d.setTransform(old);

        // Vẽ mũi tên
        int[] xPoints = {getWidth() / 2 - 10, getWidth() / 2 + 10, getWidth() / 2};
        int[] yPoints = {y - 10, y - 10, y + 10};
        g2d.setColor(Color.RED);
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Vòng Quay May Mắn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new VongQuayDoHoa());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
