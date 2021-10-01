import java.sql.*;
import java.util.*;

public class DBConnector {

    public static void main(String[] args) {

        // auto close connection
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/CS7340_Lab1?useLegacyDatetimeCode=false&serverTimezone=UTC",
                "root", "Barn5.snowed")) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

            List<Paper> XMLcontent = DomParser.getParserResult();
            for(Paper p : XMLcontent) {
                String S1 = "insert into CS7340_Lab1.papers values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                String S2 = "insert ignore into CS7340_Lab1.authors (`authorName`) values(?)";
                String S3 = "select authorId from CS7340_Lab1.authors where authorName = ?";
                String S4 = "insert into CS7340_Lab1.coauthors values(?,?)";

                // insert paper into papers table
                try (PreparedStatement insertPaper = conn.prepareStatement(S1))
                {
                    conn.setAutoCommit(false);
                    insertPaper.setString(1, p.mdate);
                    insertPaper.setString(2, p.key);
                    insertPaper.setString(3, p.editor);
                    insertPaper.setString(4, p.title);
                    insertPaper.setString(5, p.booktitle);
                    insertPaper.setString(6, p.pages);
                    insertPaper.setString(7, p.year);
                    insertPaper.setString(8, p.address);
                    insertPaper.setString(9, p.journal);
                    insertPaper.setString(10, p.volume);
                    insertPaper.setString(11, p.number);
                    insertPaper.setString(12, p.month);
                    insertPaper.setString(13, p.url);
                    insertPaper.setString(14, p.ee);
                    insertPaper.setString(15, p.cdrom);
                    insertPaper.setString(16, p.cite);
                    insertPaper.setString(17, p.publisher);
                    insertPaper.setString(18, p.note);
                    insertPaper.setString(19, p.crossref);
                    insertPaper.setString(20, p.isbn);
                    insertPaper.setString(21, p.series);
                    insertPaper.setString(22, p.school);
                    insertPaper.setString(23, p.chapter);
                    insertPaper.setString(24, p.publnr);
                    System.out.format( "%d row(s) inserted into papers table.\n", insertPaper.executeUpdate() );
                    conn.commit();

                } catch (SQLException e) {
                    System.err.format("paper\tSQL State: %s\t%s\t", e.getSQLState(), e.getMessage());
                    try {
                        System.err.print("Transaction is being rolled back.\n");
                        conn.rollback();
                    } catch (SQLException excep) {
                        System.err.format("SQL State: %s\t%s\t", e.getSQLState(), e.getMessage());
                    }
                }

                try (PreparedStatement insertAuthor = conn.prepareStatement(S2))
                {
                    // loop through authors for current paper
                    for(String authorName : p.authors) {
                        // inserts author into authors table
                        insertAuthor.setString(1, authorName);
                    }

                    System.out.format( "%d row(s) inserted into authors table.\n", insertAuthor.executeUpdate() );
                    conn.commit();

                } catch (SQLException e) {
                    System.err.format("authr\tSQL State: %s\t%s\t", e.getSQLState(), e.getMessage());
                    try {
                        System.err.print("Transaction is being rolled back.\n");
                        conn.rollback();
                    } catch (SQLException excep) {
                        System.err.format("SQL State: %s\t%s\t", e.getSQLState(), e.getMessage());
                    }
                }

                try (PreparedStatement selectAuthorId = conn.prepareStatement(S3);
                     PreparedStatement insertCoauthor = conn.prepareStatement(S4))
                {
                    // loop through authors for current paper
                    for(String authorName : p.authors) {
                        // gets Id of author
                        selectAuthorId.setString(1, authorName);
                        ResultSet rs = selectAuthorId.executeQuery();
                        rs.next();
                        int authorId = rs.getInt("authorId");
                        // inserts author/paperKey pairs into the coauthors table
                        insertCoauthor.setInt(1, authorId);
                        insertCoauthor.setString(2, p.key);
                        System.out.format( "%d row(s) inserted into coauthors table.\n", insertCoauthor.executeUpdate() );
                    }

                    conn.commit();

                } catch (SQLException e) {
                    System.err.format("cAuth\tSQL State: %s\t%s\t", e.getSQLState(), e.getMessage());
                    try {
                        System.err.print("Transaction is being rolled back.\n");
                        conn.rollback();
                    } catch (SQLException excep) {
                        System.err.format("SQL State: %s\t%s\t", e.getSQLState(), e.getMessage());
                    }
                }
            }



        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}