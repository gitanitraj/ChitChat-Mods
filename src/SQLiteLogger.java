import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SQLiteLogger {

    private static final String DB_PATH = "data/chat.db";

    public SQLiteLogger() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH)) {
            String sql = "CREATE TABLE IF NOT EXISTS logs (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                         "event TEXT NOT NULL)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("SQLite init error: " + e.getMessage());
        }
    }

    public synchronized void log(String event) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH)) {
            String sql = "INSERT INTO logs(event) VALUES(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, event);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("SQLite log error: " + e.getMessage());
        }
    }
}
