package Service;

import Component.Message;
import com.mysql.cj.Session;

import java.net.PasswordAuthentication;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.ModelMessage;

public class ServiceMail {

    public ModelMessage sendMain(String toEmail, String code) {
        ModelMessage ms = new ModelMessage(false, "");

        String from = "phuongnv.24itb@vku.udn.vn";
        String username = "phuongnv.24itb@vku.udn.vn";
//        String encryptedPassword = "0533860271Az";
                String password = "j d t t l m w l b f b g r u t h";
//        String password = AESUtil.decrypt(encryptedPassword);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Mã xác nhận và Thông tin về Nhà Hàng");

            String emailContent = "Mã xác nhận của bạn là: " + code + "\n\n";
            emailContent += "Chào bạn, đây là thông tin về Nhà Hàng  của chúng tôi:\n";
            emailContent += "Tên nhà hàng: Nhà Violet \n";
            emailContent += "Địa chỉ: 123 Đường XYZ, Thành phố Da Nang\n";
            emailContent += "Giờ mở cửa: 10:00 AM - 10:00 PM\n";
            emailContent += "Đặt bàn ngay để thưởng thức món ăn tuyệt vời!";

            message.setText(emailContent);

            Transport.send(message);

            ms.setSuccess(true);
            ms.setMessage("Mã xác nhận đã được gửi thành công!");

        } catch (MessagingException e) {

            ms.setSuccess(false);
            if (e.getMessage().contains("Invalid Addresses")) {
                ms.setMessage("Địa chỉ email không hợp lệ.");
            } else if (e.getMessage().contains("Could not connect to SMTP host")) {
                ms.setMessage("Không thể kết nối với máy chủ email. Vui lòng thử lại sau.");
            } else {
                ms.setMessage("Đã có lỗi xảy ra khi gửi email. Vui lòng thử lại.");
            }
            e.printStackTrace();
        }

        return ms;
    }
}
