import java.sql.*;

public class ScrollStatement {
    public static void main(String[] args) throws SQLException {
        scroll();
    }

    private static void scroll() throws SQLException {
        /**
         * ResultSet.TYPE_FORWARD_ONLY 和 ResultSet.CONCUR_READ_ONLY 是預設的，也就是只能 next()，也只能讀
         * ResultSet.TYPE_SCROLL_SENSITIVE 和 ResultSet.TYPE_SCROLL_INSENSITIVE 都是可滾動，差別在是否取得改變後的資料
         * ResultSet.CONCUR_UPDATABLE 可寫
         */
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                "select * from zodiac",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pstmt.executeQuery()) {
            afterLastAndBeforeFirst(rs);
            absoluteAndRelative(rs);
        }
    }

    private static void afterLastAndBeforeFirst(ResultSet rs) throws SQLException {
        rs.afterLast(); // 取最後一筆
        rs.previous();
        display(rs);

        rs.beforeFirst(); // 取第 1 筆
        rs.next();
        display(rs);
    }

    private static void absoluteAndRelative(ResultSet rs) throws SQLException {
        rs.absolute(2); // 取第 2 筆，從 1 開始算，超過筆數執行期錯誤
        display(rs);

        rs.relative(-1); // 以 ResultSet 目前的位置開始算，超過筆數執行期錯誤
        display(rs);

        rs.relative(2); // 以 ResultSet 目前的位置開始算，超過筆數執行期錯誤
        display(rs);
    }

    private static void display(ResultSet rs) throws SQLException {
        System.out.println(rs.getString("ID") + "=" + rs.getString("NAME"));
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/my_db", "root", "123456");
    }
}
