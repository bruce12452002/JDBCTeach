import java.sql.*;

public class AdvanceStatement {
    public static void main(String[] args) throws SQLException {
        // 只能為欄位值設定?，其他不是撈不到正確的值就是報錯
//        questionOfWhereValue();
        questionOfWhereKey();
//        questionOfColumnOrTable();
    }

    private static void questionOfWhereValue() throws SQLException {
        ResultSet rs = null;
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                "select * from zodiac where EN_NAME = ? and ATTACK_POINT > ?")
        ) {
            // 由左到右找到問號，問號是從 1 開始的
            pstmt.setString(1, "ox");
            pstmt.setInt(2, 60);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("ID"));
            }
        } finally {
            rs.close();
        }
    }

    private static void questionOfWhereKey() throws SQLException {
        PreparedStatement pstmt = getConnection().prepareStatement(
                "select * from zodiac where ? = 'ox'");
        // select * from my_db.zodiac where 'EN_NAME' = 'ox';
        pstmt.setObject(1, "EN_NAME");
//        pstmt.setString(1, "EN_NAME");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("ID"));
        }
    }

    public static void questionOfColumnOrTable() throws SQLException {
        PreparedStatement pstmt = getConnection().prepareStatement(
                "select ?, ? from zodiac where EN_NAME = 'ox'");
        // select 'WEAPON', 'ID' from zodiac where EN_NAME = 'ox' // 會變成寫死的字串
        pstmt.setString(1, "WEAPON");
        pstmt.setString(2, "ID");
        // select 4, 1 from zodiac where EN_NAME = 'ox' // 會變成寫死的字串
//        pstmt.setInt(1, 4);
//        pstmt.setInt(2, 1);

/*      直接報錯
        PreparedStatement pstmt = getConnection().prepareStatement(
                "select * from ? where EN_NAME = 'ox'");
        pstmt.setString(1, "zodiac");
*/

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("ID"));
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/my_db", "root", "123456");
    }
}
