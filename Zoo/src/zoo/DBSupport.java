/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akamojo
 */
public class DBSupport {

    static private Connection conn;

    public static Connection getConn() {
        return conn;
    }

    public static void connect() {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", "inf127216");
            connectionProps.put("password", "lubieciastka");
            DBSupport.conn = DriverManager.getConnection("jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl", connectionProps);
            System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
