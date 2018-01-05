/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import static zoo.Zoo.getShowPosition2;

/**
 *
 * @author akamojo
 */
public class WybiegiFrame extends javax.swing.JFrame {

    public String[] columnsWybiegi = {"Numer wybiegu", "Typ wybiegu", "Powierzchnia (m2)"};
    public String[] columnsZwierzeta = {"Gatunek", "Plec", "Chip"};
    
    public WybiegiFrame() {
        initComponents();
        setIconImage(Zoo.getIcon());
        
        this.refreshAll();
        wybiegiTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = wybiegiTable.getSelectedRow();
                if (row != -1) 
                    refreshZwierzeta(wybiegiTable.getValueAt(row, 0).toString());
            }
        });
    }
    
    public void refreshAll() {
        //((CacheSqlTableModel) pracownicyTable.getModel()).fireTableDataChanged();
        CacheSqlTableModel model = new CacheSqlTableModel("select nr, typy_wybiegu_nazwa, powierzchnia from wybiegi", columnsWybiegi, "ORDER BY NR");
        wybiegiTable.setModel(model);
                
        model = new CacheSqlTableModel("select gatunki_nazwa, plec, chip from zwierzeta", columnsZwierzeta, "ORDER BY CHIP");
        zwierzetaTable.setModel(model);
    }
    
    public void refreshZwierzeta(String numerWybiegu) {
        CacheSqlTableModel model = new CacheSqlTableModel("select gatunki_nazwa, plec, chip from zwierzeta"
                + " where wybiegi_nr = " + numerWybiegu, columnsZwierzeta, "ORDER BY CHIP");
        zwierzetaTable.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonPanel = new javax.swing.JPanel();
        showAllButton = new javax.swing.JButton();
        addWybiegButton = new javax.swing.JButton();
        removeWybiegButton = new javax.swing.JButton();
        addZwierzeButton = new javax.swing.JButton();
        removeZwierzeButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        tableScrollPaneWybiegi = new javax.swing.JScrollPane();
        wybiegiTable = new javax.swing.JTable();
        tableScrollPaneZwierzeta = new javax.swing.JScrollPane();
        zwierzetaTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Zoo Wybiegi i zwierzęta");
        setMinimumSize(new java.awt.Dimension(250, 250));

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        showAllButton.setText("Pokaż wszystkie");
        showAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(showAllButton, gridBagConstraints);

        addWybiegButton.setText("Dodaj wybieg");
        addWybiegButton.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        addWybiegButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addWybiegButtonMouseClicked(evt);
            }
        });
        addWybiegButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWybiegButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(addWybiegButton, gridBagConstraints);

        removeWybiegButton.setText("Usuń wybieg");
        removeWybiegButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWybiegButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(removeWybiegButton, gridBagConstraints);

        addZwierzeButton.setText("Dodaj zwierzę");
        addZwierzeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addZwierzeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(addZwierzeButton, gridBagConstraints);

        removeZwierzeButton.setText("Usuń zwierzę");
        removeZwierzeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeZwierzeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(removeZwierzeButton, gridBagConstraints);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_START);

        tablePanel.setLayout(new java.awt.GridBagLayout());

        wybiegiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        wybiegiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wybiegiTableMouseClicked(evt);
            }
        });
        tableScrollPaneWybiegi.setViewportView(wybiegiTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        tablePanel.add(tableScrollPaneWybiegi, gridBagConstraints);

        tableScrollPaneZwierzeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableScrollPaneZwierzetaMouseClicked(evt);
            }
        });

        zwierzetaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        zwierzetaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                zwierzetaTableMouseClicked(evt);
            }
        });
        tableScrollPaneZwierzeta.setViewportView(zwierzetaTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        tablePanel.add(tableScrollPaneZwierzeta, gridBagConstraints);

        getContentPane().add(tablePanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void wybiegiTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wybiegiTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = wybiegiTable.getSelectedRow();
            if (row != -1) {
                try {
                    Execute q = new Execute();
                    String wNr = wybiegiTable.getValueAt(row, 0).toString();
                    q.ExecuteQuery("SELECT OPIS_WYBIEGU FROM WYBIEGI WHERE NR = " + wNr);
                    
                    q.getRs().next();
                    String description = q.getRs().getString(1);;
                    if (q.getRs().wasNull()) {
                        description = new String("Brak opisu");
                    }
                    
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), description, "Opis wybiegu numer " + wNr, JOptionPane.PLAIN_MESSAGE);
				
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
			}
		}
    }//GEN-LAST:event_wybiegiTableMouseClicked

    private void showAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllButtonActionPerformed
        this.refreshAll();
    }//GEN-LAST:event_showAllButtonActionPerformed

    private void zwierzetaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zwierzetaTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = zwierzetaTable.getSelectedRow();
            if (row != -1) {
                try {
                    ZwierzeFrame z = new ZwierzeFrame(this, new Integer(zwierzetaTable.getValueAt(row, 2).toString()));
                    z.setLocation(getShowPosition2(z));
                    z.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }        
    }//GEN-LAST:event_zwierzetaTableMouseClicked

    private void addWybiegButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWybiegButtonActionPerformed
        try {
            AddWybiegFrame w = new AddWybiegFrame(this);
            w.setLocation(getShowPosition2(w));
            w.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addWybiegButtonActionPerformed

    private void removeWybiegButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeWybiegButtonActionPerformed
        try {
            Execute exec = new Execute();
            exec.ExecutePreparedQuery("DELETE FROM WYBIEGI WHERE NR = ?");
            int row = wybiegiTable.getSelectedRow();
            if (row != -1)
                ((PreparedStatement) exec.getStatement()).setInt(1, new Integer(wybiegiTable.getValueAt(row, 0).toString()));
            int result = exec.firePreparedUpdate_getCount();
            if (result > 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Wybieg usunięty!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                this.refreshAll();
            }
            else {
                // err...
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removeWybiegButtonActionPerformed

    private void addZwierzeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addZwierzeButtonActionPerformed
        try {
            ZwierzeFrame z = new ZwierzeFrame(this);
            z.setLocation(getShowPosition2(z));
            z.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addZwierzeButtonActionPerformed

    private void removeZwierzeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeZwierzeButtonActionPerformed
        // co z zwierzętami?
        // taki sam problem jak pracownik - zwierzę zostaje w raporcie
        // i w ocenie klienta! 
        int row = zwierzetaTable.getSelectedRow();
        if (row != -1) {
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            Execute exec = new Execute();
            String chip = zwierzetaTable.getValueAt(row, 2).toString();
            int result = exec.ExecuteUpdate_getChanges("UPDATE ZWIERZETA SET DATA_OPUSZCZENIA_ZOO = DATE '" + date.toString() + "' WHERE CHIP = " + chip);
            if (result > 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Zwierzę zostało usunięte z ZOO; nadal istnieje w raportach i ocenach.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_removeZwierzeButtonActionPerformed

    private void addWybiegButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addWybiegButtonMouseClicked
        // nie chcę tego i nie umiem usunąć :<
    }//GEN-LAST:event_addWybiegButtonMouseClicked

    private void tableScrollPaneZwierzetaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableScrollPaneZwierzetaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableScrollPaneZwierzetaMouseClicked

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
    private javax.swing.JButton addWybiegButton;
    private javax.swing.JButton addZwierzeButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton removeWybiegButton;
    private javax.swing.JButton removeZwierzeButton;
    private javax.swing.JButton showAllButton;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JScrollPane tableScrollPaneWybiegi;
    private javax.swing.JScrollPane tableScrollPaneZwierzeta;
    private javax.swing.JTable wybiegiTable;
    private javax.swing.JTable zwierzetaTable;
    // End of variables declaration//GEN-END:variables

}
