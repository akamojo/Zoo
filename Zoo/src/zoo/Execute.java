/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author akamojo
 */
public class Execute {

    private ResultSet rs;
    private ResultSetMetaData rsmd;
    protected String query;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    protected String where;

    public Execute() {

    }

    public void ExecuteQuery(String query) {
        this.query = query;
        Connection dbConnection = DBSupport.getConn();
        try {
            this.stmt = dbConnection.createStatement();
            rs = stmt.executeQuery(query);
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), query + ":\n" + ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ExecuteCall(String fun) {
        try {
            CallableStatement stmt = DBSupport.getConn().prepareCall("{call " + fun + "}");
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), query + ":\n" + ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(Execute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ExecutePreparedQuery(String query) {
        this.query = query;
        Connection dbConnection = DBSupport.getConn();
        try {
            this.pstmt = dbConnection.prepareStatement(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), query + ":\n" + ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ExecuteUpdate(String query) {
        this.query = query;
        Connection dbConnection = DBSupport.getConn();
        try {
            this.stmt = dbConnection.createStatement();
            int changes = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), query + ":\n" + ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void firePreparedQuery() {
        try {
            rs = pstmt.executeQuery();
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), query + ":\n" + ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(Execute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void firePreparedUpdate() throws SQLException {
        pstmt.executeUpdate();
    }

    /*public ExecutePreparedQuery(String query, String where) {
        this.query = query;
        this.where = where;
        Connection dbConnection = DBSupport.getConn();
        try {
            pstmt = dbConnection.createStatement();
            rs = pstmt.executeQuery(query + " where " + where);
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public ResultSetMetaData getRsmd() {
        return rsmd;
    }

    public void setRsmd(ResultSetMetaData rsmd) {
        this.rsmd = rsmd;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Statement getStatement() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

}
