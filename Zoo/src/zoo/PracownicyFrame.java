/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import static zoo.Zoo.getShowPosition2;

/**
 *
 * @author akamojo
 */
public class PracownicyFrame extends javax.swing.JFrame {

    /**
     * Creates new form PracownicyFrame
     */
    public PracownicyFrame() {
        initComponents();
        setIconImage(Zoo.getIcon());
        String[] columns = new String[]{"Id", "Nazwisko", "Pensja", "Premia", "Etat", "Godziny", "Zatrudniony"};
        CacheSqlTableModel model = new CacheSqlTableModel("select ID, NAZWISKO, PENSJA, PREMIA, ETATY_NAZWA, GODZIN_TYGODNIOWO, DATA_ZATRUDNIENIA"
                + " from pracownicy WHERE DATA_ZWOLNIENIA IS NULL", columns, "ORDER BY NAZWISKO");
        pracownicyTable.setModel(model);
        pracownicyTable.removeColumn(pracownicyTable.getColumnModel().getColumn(0));
    }

    public void refresh() {
        //((CacheSqlTableModel) pracownicyTable.getModel()).fireTableDataChanged();
        String[] columns = new String[]{"Id", "Nazwisko", "Pensja", "Premia", "Etat", "Godziny", "Zatrudniony"};
        CacheSqlTableModel model = new CacheSqlTableModel("select ID, NAZWISKO, PENSJA, PREMIA, ETATY_NAZWA, GODZIN_TYGODNIOWO, DATA_ZATRUDNIENIA "
                + "from pracownicy WHERE DATA_ZWOLNIENIA IS NULL", columns, "ORDER BY NAZWISKO");
        pracownicyTable.setModel(model);
        pracownicyTable.removeColumn(pracownicyTable.getColumnModel().getColumn(0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        buttonPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        tableScrollPane = new javax.swing.JScrollPane();
        pracownicyTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Zoo Pracownicy");
        setMinimumSize(new java.awt.Dimension(250, 250));
        setPreferredSize(new java.awt.Dimension(600, 500));

        searchLabel.setText("Wyszukaj pracownika:");
        searchPanel.add(searchLabel);

        searchTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyPressed(evt);
            }
        });
        searchPanel.add(searchTextField);

        getContentPane().add(searchPanel, java.awt.BorderLayout.PAGE_START);

        addButton.setText("Dodaj nowego pracownika");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(addButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);

        tablePanel.setLayout(new java.awt.BorderLayout());

        pracownicyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pracownicyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pracownicyTableMouseClicked(evt);
            }
        });
        tableScrollPane.setViewportView(pracownicyTable);

        tablePanel.add(tableScrollPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(tablePanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pracownicyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pracownicyTableMouseClicked
        if (evt.getClickCount() == 2) {
            int selectionIndex = pracownicyTable.getSelectionModel().getMinSelectionIndex();
            if (selectionIndex >= 0) {
                CacheSqlTableModel tableModel = (CacheSqlTableModel) pracownicyTable.getModel();
                int selectedId = tableModel.getSelectedId(pracownicyTable.getSelectedRow());
                PracownikFrame prac = new PracownikFrame(this);
                prac.setLocation(getShowPosition2(prac));
                prac.fill(selectedId);
                prac.setVisible(true);
            }
        }
    }//GEN-LAST:event_pracownicyTableMouseClicked

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        PracownikFrame prac = new PracownikFrame(this);
        prac.setLocation(getShowPosition2(prac));
        prac.setVisible(true);
        prac.visibleButton(false);
        prac.emptyView();
    }//GEN-LAST:event_addButtonActionPerformed

    private void searchTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (searchTextField.getText().isEmpty()) {
                refresh();
            } else {
                String[] where = new String[] {searchTextField.getText().toLowerCase(), searchTextField.getText().toLowerCase(), searchTextField.getText().toLowerCase(), searchTextField.getText().toLowerCase(), searchTextField.getText().toLowerCase(), searchTextField.getText().toLowerCase()};
                String[] columns = new String[]{"Id", "Nazwisko", "Pensja", "Premia", "Etat", "Godziny", "Zatrudniony"};
                CacheSqlTableModel model = new CacheSqlTableModel("select ID, NAZWISKO, PENSJA, PREMIA, ETATY_NAZWA, GODZIN_TYGODNIOWO, DATA_ZATRUDNIENIA "
                        + "from pracownicy WHERE DATA_ZWOLNIENIA IS NULL AND (LOWER(NAZWISKO) LIKE ? OR TO_CHAR(PENSJA) LIKE ? OR TO_CHAR(PREMIA) LIKE ?"
                        + " OR LOWER(ETATY_NAZWA) LIKE ? OR TO_CHAR(GODZIN_TYGODNIOWO) LIKE ? OR TO_CHAR(DATA_ZATRUDNIENIA, 'YYYY-MM-DD') LIKE ?)", columns, "ORDER BY NAZWISKO", where);
                pracownicyTable.setModel(model);
                pracownicyTable.removeColumn(pracownicyTable.getColumnModel().getColumn(0));
            }
        }
    }//GEN-LAST:event_searchTextFieldKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PracownicyFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JTable pracownicyTable;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables

}
