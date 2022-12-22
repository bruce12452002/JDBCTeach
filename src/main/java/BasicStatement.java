import java.sql.*;

// https://docs.oracle.com/javase/tutorial/jdbc/overview/index.html
// https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
/*
CREATE TABLE `my_db`.`zodiac` (
  `id` INT NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `en_name` VARCHAR(20) NULL,
  `weapon` VARCHAR(20) NULL,
  `attack_point` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

  insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) values (1, '鼠', 'rat', '牙齒', 50)
  insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) values (2, '牛', 'ox', '牛角', 80)
 */
public class BasicStatement {
    public static void main(String[] args) throws SQLException {
        BasicStatement bs = new BasicStatement();
        bs.queryData();
//        bs.insertData();
//        bs.updateData();
//        bs.deleteData();
//        bs.resultSetMetaData();
    }

    public void databaseMetaData() throws SQLException {
        try (Connection conn = getConnection()
        ) {
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println(metaData.supportsANSI92EntryLevelSQL());
            System.out.println(metaData.getDatabaseProductName());
            System.out.println(metaData.getDatabaseProductVersion());
            System.out.println(metaData.getDatabaseMajorVersion());
            System.out.println(metaData.getDatabaseMinorVersion());
            System.out.println(metaData.getDriverVersion());
            System.out.println(metaData.getDriverMajorVersion());
            System.out.println(metaData.getDriverMinorVersion());
            System.out.println(metaData.getJDBCMajorVersion());
            System.out.println(metaData.getJDBCMinorVersion());
        }
    }

    public void queryData() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from zodiac")
        ) {
            while (rs.next()) {
//            int id = rs.getInt("ID");
                int id = rs.getInt(1);
                String name = rs.getString("NAME");
                String enName = rs.getString("EN_NAME");
                String weapon = rs.getString("WEAPON");
                int attackPoint = rs.getInt("ATTACK_POINT");
                System.out.println(id);
                System.out.println(name);
                System.out.println(enName);
                System.out.println(weapon);
                System.out.println(attackPoint);
                System.out.println("====================");
            }
        }
    }

    public void insertData() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()
        ) {
            int result = stmt.executeUpdate(
                    "insert into zodiac (ID, NAME, EN_NAME, WEAPON, ATTACK_POINT) " +
                            "values (3, '虎', 'taiger', '牙齒', 100)");
            System.out.println("新增成功筆數=" + result);
            queryData();
        }
    }

    public void updateData() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()
        ) {
            int result = stmt.executeUpdate(
                    "update zodiac set EN_NAME = 'tiger', ATTACK_POINT = 95 where ID = 3");
            System.out.println("修改成功筆數=" + result);
            queryData();
        }
    }

    public void deleteData() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()
        ) {
            int result = stmt.executeUpdate(
                    "delete from zodiac where ID = 3");
            System.out.println("刪除成功筆數=" + result);
            queryData();
        }
    }

    public void resultSetMetaData() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from zodiac")
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            // 從 1 開始，0 會報範圍錯誤
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.println(metaData.getColumnName(i));
                System.out.println(metaData.getColumnLabel(i));
                System.out.println(metaData.getColumnType(i));
                System.out.println(metaData.getColumnTypeName(i));
                System.out.println(metaData.getColumnClassName(i));
                System.out.println(metaData.getColumnDisplaySize(i));
                System.out.println("=======================");
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        /*
        JDBC 4.0 之後不需要用 Class.forName 了，用 SPI 機制解決了
        DriverManager 靜態塊有個 loadInitialDrivers 方法，裡面有 ServiceLoader
         */
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/my_db", "root", "123456");
    }
}
