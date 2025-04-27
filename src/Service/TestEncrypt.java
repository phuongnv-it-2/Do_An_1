package Service;
public class TestEncrypt {
    public static void main(String[] args) {
        String plain = "0533860271Az";
        String encrypted = AESUtil.encrypt(plain);
        System.out.println("Mật khẩu đã mã hóa: " + encrypted);
    }
}
