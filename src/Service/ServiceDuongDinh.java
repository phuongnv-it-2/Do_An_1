package Service;

import Main.SystemMain;
import Main.main;
import com.sun.tools.javac.Main;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ServiceDuongDinh {
	private static ServiceDuongDinh instance;
	private Socket client;
	private final int PORT_NUMBER = 1610;
	private final String IP = "localhost";
	private BufferedReader in;
                   private SystemMain main;
	
	
        private OutputStream out;

	
	public static ServiceDuongDinh getInstance(SystemMain main) {
		if(instance == null) {
			instance = new ServiceDuongDinh(main);
		}
		return instance;
	}
	
    public static ServiceDuongDinh getInstance() {
    	return instance;
    }
	
	private ServiceDuongDinh(SystemMain main) {
		
	}
	
    public void startClient(){
    	try {
        	client = new Socket(IP, PORT_NUMBER);
            in = new BufferedReader(new InputStreamReader(client.getInputStream() , StandardCharsets.UTF_8));
            out = new DataOutputStream(client.getOutputStream());
		} catch (Exception e2) {
			
		}
        new Thread(() -> {
            while (true) {
                try {
                    String message;
                    synchronized (in) {
                        message = in.readLine();
                    }                  
                    if (message != null) {
                        System.out.println("client: " + message + "\n");
                     
                    } else {
                        System.out.println("Client disconnected");
                        break;
                    }
                } catch (Exception e) {

                }
            }
        }).start();    
    }
    

    
public void register(JSONObject jsonData) {
        try {
            if (out == null) {
                out = new FileOutputStream("output.txt", true); // Append mode
            }
            new Thread(() -> {
                try (OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
                    writer.write(jsonData.toString() + "\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void login(JSONObject jsonData, int quay) {
    	try {
			jsonData.put("quay", quay);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        new Thread(() -> {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                writer.write(jsonData.toString() + "\n");
                writer.flush();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }).start();  
    }
    
    public void themNhanVien(JSONObject jsonData) {
        new Thread(() -> {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                writer.write(jsonData.toString() + "\n");
                writer.flush();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }).start();  
    }
    
    public void tracuu(String sdt) {
    	JSONObject json = new JSONObject();
		try {
			json.put("type", "tracuu");
			json.put("sdt", sdt);
		} catch (Exception e) {
			e.printStackTrace();
		}
        new Thread(() -> {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                writer.write(json.toString() + "\n");
                writer.flush();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }).start(); 
    }
    
    public void themThanhVien(JSONObject jsonData) {
        new Thread(() -> {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                writer.write(jsonData.toString() + "\n");
                writer.flush();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }).start();  
    }
    
    public void xuatHoaDonKhachHang(JSONObject jsonData) {
        new Thread(() -> {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                writer.write(jsonData.toString() + "\n");
                writer.flush();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }).start();  
    }
    


	public Socket getClient() {
		return client;
	}

	public SystemMain getMain() {
		return main;
	}
	
}
