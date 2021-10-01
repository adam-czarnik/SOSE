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
            List<Paper> papers = DomParser.getParserResult();

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

            Statement stmt = conn.createStatement();
            List<Paper> temp = DomParser.getParserResult();
            for(Paper e : temp){
                String sql = "insert into CS7340_Lab1.papers values ('"+e.key+"', '"+e.pubType+"', '"+e.editor+"', '"+e.title+"', '"+e.booktitle+"', '"+e.pages+"', '"+e.year+"', '"+e.address+"', " +
                        "'"+e.journal+"', '"+e.volume+"','"+e.number+"', '"+e.month+"', '"+e.url+"', '"+e.ee+"', '"+e.cdrom+"', '"+e.cite+"', '"+e.publisher+"', '"+e.note+"', " +
                        "'"+e.crossref+"', '"+e.isbn+"', '"+e.series+"', '"+e.school+"', '"+e.chapter+"'  )";

                stmt.execute(sql);
                System.out.println(sql);
            }

            String select_sql = "select * from CS7340_Lab1.papers";
            ResultSet rs = stmt.executeQuery(select_sql);
            while(rs.next())
            {
                String doc_id = rs.getString("key");
                String doc_pubType = rs.getString("pubType");
                String doc_editor = rs.getString("editor");
                String doc_title = rs.getString("title");
                String doc_booktitle = rs.getString("booktitle");
                String doc_pages = rs.getString("pages");
                String doc_publish_year = rs.getString("publish_year");
                String doc_address = rs.getString("address");
                String doc_journal = rs.getString("journal");
                String doc_volume = rs.getString("volume");
                String doc_num = rs.getString("num");
                String doc_mon = rs.getString("mon");
                String doc_url = rs.getString("url");
                String doc_ee = rs.getString("ee");
                String doc_cdrom = rs.getString("cdrom");
                String doc_cite = rs.getString("cite");
                String doc_publisher = rs.getString("publisher");
                String doc_note = rs.getString("note");
                String doc_crossref = rs.getString("crossref");
                String doc_isbn = rs.getString("isbn");
                String doc_series = rs.getString("series");
                String doc_school = rs.getString("school");
                String doc_chapter = rs.getString("chapter");
                System.out.println("id: " +doc_id+ " pubType: " +doc_pubType+ " title:" +doc_title+ " journal: " +doc_journal);
            }


        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
