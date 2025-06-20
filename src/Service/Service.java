package Service;

import Database.JDBCuntil;
import Main.SystemMain;

public class Service {
    private SystemMain main;
    private static Service instance;

    public static Service getInstance(SystemMain main) {
        if (instance == null) {
            instance = new Service(main);
        }
        return instance;
    }

    // Constructor riêng tư
    private Service(SystemMain main) {
        this.main = main;
        JDBCuntil.getInstance().getconection(); 
    }
      public static Service getInstance() {
        return instance;
    }

    // Phương thức getMain duy nhất
    public SystemMain getMain() {
        return main;
    }
}