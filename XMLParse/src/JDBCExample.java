import java.sql.*;
import java.util.List;

public class JDBCExample {

    public static void main(String[] args) {

        // https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html#package.description
        // auto java.sql.Driver discovery -- no longer need to load a java.sql.Driver class via Class.forName

        // register JDBC driver, optional since java 1.6
        /*try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        // auto close connection
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/CS7340_Lab1?useOldAliasMetadataBehavior=true&&serverTimezone=UTC", "root", "Barn5.snowed")) {
            List<Employee> employees = DomParser.getParserResult();

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

            Statement stmt = conn.createStatement();
            List<Employee> temp = DomParser.getParserResult();
            for(Employee e : temp){
                String sql = "insert into CS7340_Lab1.employees values ('"+e.id+"', '"+e.firstName+"', '"+e.lastName+"', '"+e.location+"')";
                stmt.execute(sql);
                System.out.println(sql);
            }

            String select_sql = "select * from CS7340_Lab1.employees";
            ResultSet rs = stmt.executeQuery(select_sql);
            while(rs.next())
            {
                String em_id = rs.getString("employee_id");
                String em_lastname = rs.getString("employee_lname");
                String em_firstname = rs.getString("employee_fname");
                String em_location = rs.getString("employee_location");
                System.out.println("id: " +em_id+ " last name: " +em_lastname+ " first name:" +em_firstname+ " location: " +em_location);
            }


        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
