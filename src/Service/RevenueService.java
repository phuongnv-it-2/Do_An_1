package Service;

import Database.RevenueDao;
import java.util.List;
import model.Revenue;

public class RevenueService {
    private RevenueDao revenueDao;

   
    public RevenueService() {
        this.revenueDao = new RevenueDao();
    }

    public List<Revenue> getListRevenue() {
        if (revenueDao == null) {
            throw new NullPointerException("RevenueDao chưa được khởi tạo.");
        }
        return revenueDao.getListRevenue();
    } 
    public List<Revenue> getListDayRevenue() {
        if (revenueDao == null) {
            throw new NullPointerException("RevenueDao chưa được khởi tạo.");
        }
        return revenueDao.getListDayRevenue();
    }
}
