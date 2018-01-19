/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author akamojo
 */
public class BiletyFrame extends javax.swing.JFrame {

    /**
     * Creates new form BiletyFrame
     */
    public BiletyFrame() {
        initComponents();
        setIconImage(Zoo.getIcon());
        String[] columns = new String[]{"Numer", "Wiek klienta", "Czas sprzedaży", "Id sprzedawcy", "Typ biletu"};
        CacheSqlTableModel model = new CacheSqlTableModel("select * from bilety", columns, "ORDER BY NR");
        biletyTable.setModel(model);
        biletyTable.removeColumn(biletyTable.getColumnModel().getColumn(0));
    }

    public void refresh() {
        String[] columns = new String[]{"Numer", "Wiek klienta", "Czas sprzedaży", "Id sprzedawcy", "Typ biletu"};
        CacheSqlTableModel model = new CacheSqlTableModel("select * from bilety", columns, "ORDER BY NR");
        biletyTable.setModel(model);
        biletyTable.removeColumn(biletyTable.getColumnModel().getColumn(0));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        kupBiletButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        biletyPanel = new javax.swing.JPanel();
        biletyScrollPane = new javax.swing.JScrollPane();
        biletyTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Zoo Bilety");

        searchTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyPressed(evt);
            }
        });
        buttonPanel.add(searchTextField);

        kupBiletButton.setText("Kup bilet");
        kupBiletButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kupBiletButtonMouseClicked(evt);
            }
        });
        buttonPanel.add(kupBiletButton);

        delButton.setText("Usuń bilet");
        delButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                delButtonKeyTyped(evt);
            }
        });
        buttonPanel.add(delButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_START);

        biletyPanel.setLayout(new java.awt.BorderLayout());

        biletyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        biletyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                biletyTableMouseClicked(evt);
            }
        });
        biletyScrollPane.setViewportView(biletyTable);

        biletyPanel.add(biletyScrollPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(biletyPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kupBiletButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kupBiletButtonMouseClicked
        BiletDialog bilet = new BiletDialog(this, rootPaneCheckingEnabled);
        bilet.setLocation(Zoo.getShowPosition2D(bilet));
        bilet.setVisible(true);
    }//GEN-LAST:event_kupBiletButtonMouseClicked

    private void biletyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_biletyTableMouseClicked
        if (evt.getClickCount() == 2) {
            int selectionIndex = biletyTable.getSelectionModel().getMinSelectionIndex();
            if (selectionIndex >= 0) {
                CacheSqlTableModel tableModel = (CacheSqlTableModel) biletyTable.getModel();
                int selectedId = tableModel.getSelectedId(biletyTable.getSelectedRow());
                OcenyFrame oceny = new OcenyFrame();
                oceny.setNumer(selectedId);
                oceny.refresh();
                oceny.setLocation(Zoo.getShowPosition2(oceny));
                oceny.setVisible(true);
            }
        }
    }//GEN-LAST:event_biletyTableMouseClicked

    private void delButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_delButtonKeyTyped
        int selectionIndex = biletyTable.getSelectionModel().getMinSelectionIndex();
        if (selectionIndex >= 0) {
            CacheSqlTableModel tableModel = (CacheSqlTableModel) biletyTable.getModel();
            int selectedId = tableModel.getSelectedId(biletyTable.getSelectedRow());

            int n = JOptionPane.showOptionDialog(JOptionPane.getRootFrame(), "Jesteś pewnien, że chcesz usunąć bilet?\n"
                    + "Razem z nim zostaną usuniente również wszystkie przypisane do niego oceny!", "Usuń raport",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

            if (n == 0) {
                Execute up = new Execute();
                up.ExecuteUpdate("DELETE FROM BILETY WHERE NR = " + Integer.toString(selectedId));
                refresh();
            }
        }
    }//GEN-LAST:event_delButtonKeyTyped

    private void searchTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (searchTextField.getText().isEmpty()) {
                refresh();
            } else {
                String[] where = new String[]{searchTextField.getText().toLowerCase()};
                String[] columns = new String[]{"Numer", "Wiek klienta", "Czas sprzedaży", "Id sprzedawcy", "Typ biletu"};
                CacheSqlTableModel model = new CacheSqlTableModel("select * from BILETY where TO_CHAR(CZAS_SPRZEDAZY, 'YYYY-MM-DD') LIKE ?", columns, "ORDER BY NR", where);
                biletyTable.setModel(model);
                biletyTable.removeColumn(biletyTable.getColumnModel().getColumn(0));
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
            java.util.logging.Logger.getLogger(BiletyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BiletyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BiletyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BiletyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BiletyFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel biletyPanel;
    private javax.swing.JScrollPane biletyScrollPane;
    private javax.swing.JTable biletyTable;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton delButton;
    private javax.swing.JButton kupBiletButton;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables
}
