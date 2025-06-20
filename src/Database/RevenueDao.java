package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Revenue;

public class RevenueDao {

    public List<Revenue> getListRevenue() {
        Connection cons = JDBCuntil.getconection();
        String sql = "SELECT MONTH(h.ThoiGian) AS month, SUM(ct.ThanhTien) AS totalAmount " + // Replace 'ThoiGian' with your actual column name
                     "FROM HoaDon h JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " +
                     "WHERE MONTH(h.ThoiGian) = MONTH(CURDATE()) AND YEAR(h.ThoiGian) = YEAR(CURDATE()) " +
                     "GROUP BY MONTH(h.ThoiGian)";

        List<Revenue> list = new ArrayList<>();
        try (PreparedStatement st = cons.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            double[] revenuesByMonth = new double[12]; // Initialize array for all 12 months
            while (rs.next()) {
                int month = rs.getInt("month");
                double totalRevenue = rs.getDouble("totalAmount");
                revenuesByMonth[month - 1] = totalRevenue; // Store revenue for the current month
            }

            // Populate list with all 12 months, using query data for the current month
            int currentMonth = java.time.LocalDate.now().getMonthValue();
            for (int i = 1; i <= 12; i++) {
                Revenue revenue = new Revenue(i, i == currentMonth ? revenuesByMonth[i - 1] : 0, true);
                list.add(revenue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Revenue> getListDayRevenue() {
        Connection cons = JDBCuntil.getconection();
        String sql = "SELECT DAYOFWEEK(h.ThoiGian) AS dayOfWeek, SUM(ct.ThanhTien) AS totalAmount " + // Replace 'ThoiGian' with your actual column name
                     "FROM HoaDon h JOIN ChiTietHoaDon ct ON h.MaHoaDon = ct.MaHoaDon " +
                     "WHERE WEEK(h.ThoiGian) = WEEK(CURDATE()) AND YEAR(h.ThoiGian) = YEAR(CURDATE()) " +
                     "GROUP BY DAYOFWEEK(h.ThoiGian) ORDER BY dayOfWeek";

        List<Revenue> list = new ArrayList<>();
        try (PreparedStatement st = cons.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            double[] revenuesByDay = new double[7]; // Initialize array for all 7 days
            while (rs.next()) {
                int dayOfWeek = rs.getInt("dayOfWeek");
                double totalRevenue = rs.getDouble("totalAmount");
                revenuesByDay[dayOfWeek - 1] = totalRevenue; // Store revenue for each day in the current week
            }

            // Populate list with all 7 days, using 0 for days with no data
            for (int i = 1; i <= 7; i++) {
                Revenue revenue = new Revenue(i, revenuesByDay[i - 1], false);
                list.add(revenue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}