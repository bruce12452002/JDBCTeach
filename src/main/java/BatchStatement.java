import java.sql.*;

public class BatchStatement {
    public static void main(String[] args) throws SQLException {
//        batch1();
        batch2();
    }

    private static void batch1() throws SQLException {
        String[] name = {"龍", "蛇", "馬"};
        String[] enName = {"dragon", "snake", "horse"};
        String[] weapon = {"爪", "吐信", "後踢"};
        int[] attackPoint = {100, 60, 70};

        final String SQL = "insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) values (?, ?, ? ,? ,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            for (int i = 0; i < name.length; i++) {
                pstmt.setInt(1, i + 5);
                pstmt.setString(2, name[i]);
                pstmt.setString(3, enName[i]);
                pstmt.setString(4, weapon[i]);
                pstmt.setInt(5, attackPoint[i]);
                pstmt.addBatch(); // 必須在同一個 Connection，否則會被最後一個 Connection 覆蓋
            }

            int[] result = pstmt.executeBatch();
            System.out.println("新增了 " + result.length + " 條資料");
        }
    }

    private static void batch2() throws SQLException {
        String[] name = {"'龍'", "'蛇'", "'馬'"};
        String[] enName = {"'dragon'", "'snake'", "'horse'"};
        String[] weapon = {"'爪'", "'吐信'", "'後踢'"};
        int[] attackPoint = {100, 60, 70};

        final String BASE = "insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) values (";
        final String COMMA = ",";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            for (int i = 0; i < name.length; i++) {
                final String sql = BASE + (i + 5) + COMMA + name[i] + COMMA + enName[i] + COMMA +
                        weapon[i] + COMMA + attackPoint[i] + ")";
                stmt.addBatch(sql); // 必須在同一個 Connection，否則會被最後一個 Connection 覆蓋
            }

            int[] result = stmt.executeBatch();
            System.out.println("新增了 " + result.length + " 條資料");
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/my_db", "root", "123456");
    }
}
