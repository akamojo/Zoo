package zoo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.*;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
public class CachingResultSetTableModel extends ResultSetTableModel {
    //private static final Log log = LogFactory.getLog(CachingResultSetTableModel.class);

    private ArrayList cache;
    private int columnCount;
    private Class[] columnClasses;
    private String sortedColumnName;
    private String originalOrderBy = "";

    public CachingResultSetTableModel(String[] columnsNames) {
        super(columnsNames);
        columnCount = columnsNames.length;
        cache = new ArrayList();
    }

    public CachingResultSetTableModel(String query, String[] columnsNames, String orderBy) {
        super(query, columnsNames, orderBy);
        this.originalOrderBy = orderBy;
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
            //RoadSupport.showMessage(roadpartner.resources.Messages.getMessage("dbError"), e);
        }
        if (orderBy.length() > 0) {
            String orderedField = orderBy.substring(9);
            if (query != null && query.startsWith("select")) {
                sortedColumnName = orderedField;
            }
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void refresh() {
        if (query != null && query.length() > 0) {
            clearCache();
            appendRows(query);
            System.out.println(query);
        }
    }

    public void clearCache() {
        cache.clear();
    }

    public void appendRows(Object[][] rows) {
        if (rows.length > 0) {
            columnClasses = new Class[columnCount];
            for (int j = 0; j < columnCount; j++) {
                columnClasses[j] = rows[0][j].getClass();
            }
            for (int i = 0; i < rows.length; i++) {
                Object[] row = rows[i];
                appendRow(row, false);
            }
            super.fireTableDataChanged();
        }
    }

    public boolean appendRows(String query) {
        if (super.appendRows(query)) {
            try {
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
                return false;
            }
        } else {
            return false;
        }
        return true;
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
    
    //moje
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

    public Object[] getRows() {
        Object[][] row = new Object[0][getColumnCount()];
        return cache.toArray(row);
    }

    public void appendRow(Object[] row) {
        appendRow(row, true);
    }

    public void appendRow(int index, Object[] row) {
        appendRow(index, row, true);
    }

    public void appendRow(Object[] row, boolean fireChangeEvent) {
        cache.add(row);
        if (fireChangeEvent) {
            super.fireTableDataChanged();
        }
    }

    public void appendRow(int index, Object[] row, boolean FireChangeEvent) {
        cache.add(index, row);
        if (FireChangeEvent) {
            super.fireTableDataChanged();
        }
    }

    public void deleteRow(Object[] row) {
        cache.remove(row);
        super.fireTableDataChanged();
    }

}

abstract class ResultSetTableModel extends AbstractTableModel {

    private ResultSet rs;
    private ResultSetMetaData rsmd;
    protected String[] columnsNames;
    protected String query;
    private Statement statement = null;
    protected String orderBy;

    public ResultSetTableModel(String[] columnsNames) {
        this.columnsNames = columnsNames;
    }

    public ResultSetTableModel(String query, String[] columnsNames, String orderBy) {
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

    public boolean appendRows(String query) {
        this.query = query;
        Connection dbConnection = DBSupport.getConn();
        try {
            statement = dbConnection.createStatement();
            rs = statement.executeQuery(query + " " + orderBy);
            rsmd = rs.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    protected void closeStatement() {
        if (statement != null) {
            try {
                rs.close();
                statement.close();
            } catch (SQLException ex) {
            }
            statement = null;
        }
    }

    public String getColumnName(int c) {
        return columnsNames[c];
    }

    public Class getColumnClass(int c) {
        int sqlType;
        Integer integer = new Integer(0);
        String string = "String";
        Boolean bool = new Boolean(true);
        Double doub = new Double(1.2);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        try {
            sqlType = rsmd.getColumnType(c + 1);
        } catch (SQLException ex) {
            Logger.getLogger(DBSupport.class.getName()).log(Level.SEVERE, null, ex);
            return string.getClass();
        }
        if (sqlType == Types.INTEGER) {
            return integer.getClass();
        } else if (sqlType == Types.FLOAT) {
            return doub.getClass();
        } else if (sqlType == 93) {
            return date.getClass();
        } else {
            return string.getClass();
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
