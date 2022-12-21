import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class MyRowSetFactory {
    public static void main(String[] args) throws SQLException {
        RowSetFactory rowSetFactory = RowSetProvider.newFactory();
        try (JdbcRowSet jdbcRowSet = rowSetFactory.createJdbcRowSet()) {
            jdbcRowSet.setUrl("jdbc:mysql://localhost:3306/my_db");
            jdbcRowSet.setUsername("root");
            jdbcRowSet.setPassword("123456");
            jdbcRowSet.setCommand("select * from zodiac");
            jdbcRowSet.execute();
            while (jdbcRowSet.next()) {
                int id = jdbcRowSet.getInt(1);
                String name = jdbcRowSet.getString("NAME");
                String enName = jdbcRowSet.getString("EN_NAME");
                String weapon = jdbcRowSet.getString("WEAPON");
                int attackPoint = jdbcRowSet.getInt("ATTACK_POINT");
                System.out.println(id);
                System.out.println(name);
                System.out.println(enName);
                System.out.println(weapon);
                System.out.println(attackPoint);
                System.out.println("====================");
            }
//            jdbcRowSet.setAutoCommit(false);
//            jdbcRowSet.commit();
//            jdbcRowSet.rollback();
        }
    }
}
