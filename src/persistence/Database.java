 
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private final String tableName = "result";
    private final Connection conn;
    private ArrayList<Result> highScores;
    
    public Database(){
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost/tron?"
                    + "serverTimezone=UTC&user=root");
        } catch (Exception ex) {
            System.out.println("No connection");
        }
        this.conn = c;
        loadHighScores();
    }

    private void loadHighScores(){
        try (Statement stmt = conn.createStatement()) {
            highScores = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " ORDER BY won DESC LIMIT 10");
            while (rs.next()){
                String name = rs.getString("name");
                int cntWin = rs.getInt("won");
                Result res = new Result(name, cntWin);
                highScores.add(res);
            }
        } catch (Exception e){ System.out.println("loadHighScores error: " + e.getMessage());}
    }
    
    public ArrayList<Result> getHighScores(){
        return highScores;
    }
    
    public void storeResult(String name) {
        try (Statement stmt = conn.createStatement()) {
            if (countRowsByName(name) > 0) {
                stmt.executeUpdate("UPDATE result SET won = won + 1 WHERE name = '" + name + "'");
            } else {
                stmt.executeUpdate("INSERT INTO result (`name`, `won`) value ('" + name +"', 1)");
            }
        } catch (Exception e){ System.out.println("storeResult error: " + e.getMessage());}
    }
    
    private int countRowsByName(String name) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS CNT FROM " 
                                            + tableName + " WHERE "
                                            + "name = '" + name + "'");
            rs.next();
            return rs.getInt("CNT");
        } catch (Exception e){ 
            System.out.println("countRowsByName error: " + e.getMessage());
            return 0;
        }
    }
}
