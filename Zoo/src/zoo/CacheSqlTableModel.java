package zoo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.*;

public class CacheSqlTableModel extends SqlTableModel {

    private ArrayList cache;
    private int columnCount;
    private Class[] columnClasses;

    public CacheSqlTableModel(String query, String[] columnsNames, String orderBy) {
        super(query, columnsNames, orderBy);
        try {
            cache = new ArrayList();
            columnCount = super.getColumnCount();
            columnClasses = new Class[columnCount];
            for (int j = 0; j < columnCount; j++) {
                columnClasses[j] = super.getColumnClass(j);
            }
            ResultSet rs = getResultSet();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    row[j] = rs.getObject(j + 1);
                }
                appendRow(row, false);
            }
            closeStatement();
            super.fireTableDataChanged();
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    CacheSqlTableModel(String query, String[] columnsNames, String orderBy, String[] where) {
        super(query, columnsNames, orderBy, where);
        try {
            cache = new ArrayList();
            columnCount = super.getColumnCount();
            columnClasses = new Class[columnCount];
            for (int j = 0; j < columnCount; j++) {
                columnClasses[j] = super.getColumnClass(j);
            }
            ResultSet rs = getResultSet();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    row[j] = rs.getObject(j + 1);
                }
                appendRow(row, false);
            }
            closeStatement();
            super.fireTableDataChanged();
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillPreparedCacheSqlTableModel() {
        try {
            cache = new ArrayList();
            columnCount = super.getColumnCount();
            columnClasses = new Class[columnCount];
            for (int j = 0; j < columnCount; j++) {
                columnClasses[j] = super.getColumnClass(j);
            }
            ResultSet rs = getResultSet();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    row[j] = rs.getObject(j + 1);
                }
                appendRow(row, false);
            }
            closeStatement();
            super.fireTableDataChanged();
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void clearCache() {
        cache.clear();
    }

    public Object getValueAt(int r, int c) {
        if (r < cache.size()) {
            return ((Object[]) cache.get(r))[c];
        } else {
            return null;
        }
    }

    public void setValueAt(Object aValue, int r, int c) {
        ((Object[]) cache.get(r))[c] = aValue;
        super.fireTableCellUpdated(r, c);
    }

    public int getSelectedId(int r) {
        if (r < cache.size()) {
            return ((BigDecimal) ((Object[]) cache.get(r))[0]).intValue();
        } else {
            return -1;
        }
    }

    public int getRowCount() {
        return cache.size();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Class getColumnClass(int c) {
        return columnClasses[c];
    }

    public Object[] getRow(int rowNumber) {
        return (Object[]) cache.get(rowNumber);
    }

    public void appendRow(Object[] row) {
        appendRow(row, true);
    }

    public void appendRow(int index, Object[] row) {
        appendRow(index, row, true);
    }

    public void appendRow(Object[] row, boolean change) {
        cache.add(row);
        if (change) {
            super.fireTableDataChanged();
        }
    }

    public void appendRow(int index, Object[] row, boolean change) {
        cache.add(index, row);
        if (change) {
            super.fireTableDataChanged();
        }
    }

    public void deleteRow(Object[] row) {
        cache.remove(row);
        super.fireTableDataChanged();
    }

}

abstract class SqlTableModel extends AbstractTableModel {

    private ResultSet rs;
    private ResultSetMetaData rsmd;
    protected String[] columnsNames;
    protected String query;
    private Statement statement = null;
    protected String orderBy;
    private PreparedStatement pstmt = null;

    public SqlTableModel(String query, String[] columnsNames, String orderBy) {
        this.columnsNames = columnsNames;
        this.query = query;
        this.orderBy = orderBy;
        Connection dbConnection = DBSupport.getConn();
        try {
            statement = dbConnection.createStatement();
            rs = statement.executeQuery(query + " " + orderBy);
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SqlTableModel(String query, String[] columnsNames, String orderBy, String[] where) {
        try {
            this.columnsNames = columnsNames;
            Connection dbConnection = DBSupport.getConn();
            this.pstmt = dbConnection.prepareStatement(query + " " + orderBy);
            this.orderBy = orderBy;
            for (int i = 1; i <= where.length; i++) {
                this.pstmt.setString(i, where[i - 1] + "%");
            }
            this.rs = pstmt.executeQuery();
            this.rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(SqlTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeStatement() {
        if (statement != null) {
            try {
                rs.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
            }
            statement = null;
        }
    }

    public String getColumnName(int c) {
        return columnsNames[c];
    }

    public Class getColumnClass(int c) {
        int sqlType;
        try {
            sqlType = rsmd.getColumnType(c + 1);
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
            return String.class;
        }
        if (sqlType == Types.INTEGER) {
            return Integer.class;
        } else if (sqlType == Types.TIMESTAMP) {
            return JTextField.class;
        } else if (sqlType == Types.FLOAT) {
            return Double.class;
        } else if (sqlType == 93) {
            return java.sql.Date.class;
        } else {
            return String.class;
        }
    }

    public int getColumnCount() {
        try {
            return rsmd.getColumnCount();
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public String[] getColumnNames() {
        return columnsNames;
    }

    protected ResultSet getResultSet() {
        return rs;
    }

}
