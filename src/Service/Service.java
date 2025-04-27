package Service;

import Database.JDBCuntil;
import Main.SystemMain;
import Main.main;
import java.net.ServerSocket;

/**
 *
 * @author ASUS
 */
public class Service {
    private SystemMain Main;
    private static Service instance; 
    private ServerSocket serverSocket;
    private main main;

   
    public static Service getInstance(SystemMain main) {
        if (instance == null) {
            instance = new Service(main);  
        }
        return instance; 
    }
     public static Service getInstance() {
        return instance;
    }

    private Service(SystemMain main) {
        this.Main = Main;
        JDBCuntil.getInstance().getconection(); 
        main.getBody();
//.getAddProduct().loadProduct();  
    }

  
    public SystemMain getmain() {
        return Main;
    }
    public main Getmain()
    {
    return main;
            }
}
