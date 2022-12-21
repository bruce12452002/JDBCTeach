import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {
    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(
                    "insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) " +
                            "values (3, '虎', 'tiger', '牙齒', 95)");
            int result = pstmt.executeUpdate();
            System.out.println("新增成功筆數=" + result);
            int i = 1 / 0;
            System.out.println("commit~~");
            conn.commit();
        } catch (Exception e) {
            System.out.println("rollback~~");
            conn.rollback();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            conn.close();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/my_db", "root", "123456");
    }
}
