import java.sql.*;

public class Transaction {
    public static void main(String[] args) throws SQLException {
//        basic();
        savePoint();
    }

    private static void savePoint() throws SQLException {
        String[] name = {"羊", "猴", "雞"};
        String[] enName = {"sheep", "monkey", "chicken"};
        String[] weapon = {"撞擊", "咬", "啄"};
        int[] attackPoint = {65, 80, 50};

        final String SQL = "insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) values (?, ?, ? ,? ,?)";
        PreparedStatement pstmt = null;
        Connection conn = getConnection();
        Savepoint savepoint = null;

        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL);

            for (int i = 0; i < name.length; i++) {
                if (i == 2) {
                    int j = 1 / 0;
                }
                pstmt.setInt(1, i + 8);
                pstmt.setString(2, name[i]);
                pstmt.setString(3, enName[i]);
                pstmt.setString(4, weapon[i]);
                pstmt.setInt(5, attackPoint[i]);
                pstmt.executeUpdate();
                // 設定保存點，注意是 executeUpdate() 之後才有效，否則當下的迴圈沒有執行到 sql，
                // 此時保存的還沒有被執行，變成存的是上一次迴圈的結果
                savepoint = conn.setSavepoint();
            }

            System.out.println("commit~~");
            conn.commit();
        } catch (Exception e) {
            System.out.println("rollback and commit ~~");
            conn.rollback(savepoint);
            // rollback 保存點之後，還是要 commit
            conn.commit();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            conn.close();
        }
    }

    private static void basic() throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(
                    "insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) " +
                            "values (3, '虎', 'tiger', '牙齒', 95)");
            int result = pstmt.executeUpdate();
            System.out.println("新增成功筆數=" + result);
            int i = 1 / 0; // 測試
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
