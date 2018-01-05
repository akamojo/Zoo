/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.Image;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;
import static zoo.Zoo.getShowPosition2;

/**
 *
 * @author akamojo
 */
public class PracownikFrame extends javax.swing.JFrame {

    private int id = -1;
    private PracownicyFrame parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Creates new form PracownikFrame
     */
    public PracownikFrame() {
        initComponents();
        setIconImage(Zoo.getIcon());
    }

    public PracownikFrame(PracownicyFrame parent) {
        this.parent = parent;
        initComponents();
        setIconImage(Zoo.getIcon());
    }

    public void visibleButton(boolean czy) {
        deleteButton.setVisible(czy);
    }

    public void emptyView() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        zatrudnionyTextField.setText(date.toString());
        addRaportButton.setVisible(false);
        addBiletButton.setVisible(false);
    }

    public void fill(int id) {
        try {
            Execute q = new Execute();
            q.ExecutePreparedQuery("select id, nazwisko, pensja, nvl(premia, 0), etaty_nazwa, godzin_tygodniowo, data_zatrudnienia from pracownicy where id = ?");
            ((PreparedStatement) q.getStatement()).setInt(1, id);
            q.firePreparedQuery();
            q.getRs().next();

            idTextField.setText(q.getRs().getString(1));
            nazwiskoTextField.setText(q.getRs().getString(2));
            pensjaTextField.setText(q.getRs().getString(3));
            premiaTextField.setText(q.getRs().getString(4));
            etatComboBox.setSelectedItem(q.getRs().getString(5));
            godzinyTextField.setText(q.getRs().getString(6));
            zatrudnionyTextField.setText(q.getRs().getDate(7).toString());

            if (((String) etatComboBox.getSelectedItem()).compareTo("SPRZEDAWCA") == 0) {
                raportyPanel.setVisible(false);
                biletyPanel.setVisible(true);
                String[] columns = new String[]{"Numer", "Wiek klienta", "Czas sprzedaży", "Id sprzedawcy", "Typ biletu"};
                CacheSqlTableModel model = new CacheSqlTableModel("select * from bilety where pracownicy_id = " + Integer.toString(id), columns, "ORDER BY nr");
                biletyTable.setModel(model);
                biletyTable.removeColumn(biletyTable.getColumnModel().getColumn(0));
            } else {
                biletyPanel.setVisible(false);
                raportyPanel.setVisible(true);
                String[] columns = new String[]{"Numer", "Czas wystawienia", "Id pracownika", "Uwagi", "Chip zwierzęcia", "Numer wybiegu"};
                CacheSqlTableModel model = new CacheSqlTableModel("select * from raporty where pracownicy_id = " + Integer.toString(id), columns, "ORDER BY numer");
                raportyTable.setModel(model);
                raportyTable.removeColumn(raportyTable.getColumnModel().getColumn(0));
            }

            this.id = id;
            addBiletButton.setVisible(true);
            addRaportButton.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(PracownikFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshBilety() {
        String[] columns = new String[]{"Numer", "Wiek klienta", "Czas sprzedaży", "Id sprzedawcy", "Typ biletu"};
        CacheSqlTableModel model = new CacheSqlTableModel("select * from bilety where pracownicy_id = " + Integer.toString(this.id), columns, "ORDER BY nr");
        biletyTable.setModel(model);
        biletyTable.removeColumn(biletyTable.getColumnModel().getColumn(0));
    }

    public void refreshRaporty() {
        String[] columns = new String[]{"Numer", "Czas wystawienia", "Id pracownika", "Uwagi", "Chip zwierzęcia", "Numer wybiegu"};
        CacheSqlTableModel model = new CacheSqlTableModel("select * from raporty where pracownicy_id = " + Integer.toString(this.id), columns, "ORDER BY numer");
        raportyTable.setModel(model);
        raportyTable.removeColumn(raportyTable.getColumnModel().getColumn(0));
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

        buttonsPanel = new javax.swing.JPanel();
        changeButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        idLabel = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        nazwiskoLabel = new javax.swing.JLabel();
        nazwiskoTextField = new javax.swing.JTextField();
        etatLabel = new javax.swing.JLabel();
        pensjaLabel = new javax.swing.JLabel();
        pensjaTextField = new javax.swing.JTextField();
        premiaTextField = new javax.swing.JTextField();
        premiaLabel = new javax.swing.JLabel();
        godzinyLabel = new javax.swing.JLabel();
        godzinyTextField = new javax.swing.JTextField();
        zatrudnionyLabel = new javax.swing.JLabel();
        zatrudnionyTextField = new javax.swing.JTextField();
        etatComboBox = new javax.swing.JComboBox<>();
        raportyPanel = new javax.swing.JPanel();
        raportyScrollPane = new javax.swing.JScrollPane();
        raportyTable = new javax.swing.JTable();
        buttonRaportyPanel = new javax.swing.JPanel();
        addRaportButton = new javax.swing.JButton();
        biletyPanel = new javax.swing.JPanel();
        buttonBiletyPanel = new javax.swing.JPanel();
        addBiletButton = new javax.swing.JButton();
        biletyScrollPane = new javax.swing.JScrollPane();
        biletyTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Zoo Pracownik");
        setMinimumSize(new java.awt.Dimension(650, 600));

        changeButton.setText("Zatwierdź");
        changeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(changeButton);

        deleteButton.setText("Usuń");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(deleteButton);

        getContentPane().add(buttonsPanel, java.awt.BorderLayout.PAGE_START);

        mainPanel.setLayout(new java.awt.GridBagLayout());

        idLabel.setText("Id");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(idLabel, gridBagConstraints);

        idTextField.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(idTextField, gridBagConstraints);

        nazwiskoLabel.setText("Nazwisko");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(nazwiskoLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(nazwiskoTextField, gridBagConstraints);

        etatLabel.setText("Etat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(etatLabel, gridBagConstraints);

        pensjaLabel.setText("Pensja");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(pensjaLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(pensjaTextField, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(premiaTextField, gridBagConstraints);

        premiaLabel.setText("Premia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(premiaLabel, gridBagConstraints);

        godzinyLabel.setText("Godziny tygodniowo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(godzinyLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(godzinyTextField, gridBagConstraints);

        zatrudnionyLabel.setText("Zatrudniony");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(zatrudnionyLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(zatrudnionyTextField, gridBagConstraints);

        etatComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DOZORCA", "SPRZEDAWCA", "WETERYNARZ" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(etatComboBox, gridBagConstraints);

        raportyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Raporty"));
        raportyPanel.setLayout(new java.awt.BorderLayout());

        raportyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        raportyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                raportyTableMouseClicked(evt);
            }
        });
        raportyScrollPane.setViewportView(raportyTable);

        raportyPanel.add(raportyScrollPane, java.awt.BorderLayout.CENTER);

        addRaportButton.setText("Dodaj raport");
        addRaportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRaportButtonMouseClicked(evt);
            }
        });
        buttonRaportyPanel.add(addRaportButton);

        raportyPanel.add(buttonRaportyPanel, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(raportyPanel, gridBagConstraints);

        biletyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Bilety"));
        biletyPanel.setLayout(new java.awt.BorderLayout());

        addBiletButton.setText("Sprzedaj bilet");
        addBiletButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBiletButtonMouseClicked(evt);
            }
        });
        buttonBiletyPanel.add(addBiletButton);

        biletyPanel.add(buttonBiletyPanel, java.awt.BorderLayout.PAGE_START);

        biletyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        biletyScrollPane.setViewportView(biletyTable);

        biletyPanel.add(biletyScrollPane, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(biletyPanel, gridBagConstraints);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeButtonActionPerformed

        Execute up = new Execute();

        if (this.id == -1) {
            try {
                up.ExecutePreparedQuery("INSERT INTO PRACOWNICY(NAZWISKO, PENSJA, PREMIA, ETATY_NAZWA, GODZIN_TYGODNIOWO, DATA_ZATRUDNIENIA) "
                        + "VALUES(?, ?, ?, ?, ?, ?)");
                ((PreparedStatement) up.getStatement()).setString(1, nazwiskoTextField.getText());
                ((PreparedStatement) up.getStatement()).setFloat(2, Float.parseFloat(pensjaTextField.getText()));
                ((PreparedStatement) up.getStatement()).setFloat(3, Float.parseFloat(premiaTextField.getText()));
                ((PreparedStatement) up.getStatement()).setString(4, etatComboBox.getSelectedItem().toString());
                ((PreparedStatement) up.getStatement()).setInt(5, Integer.parseInt(godzinyTextField.getText()));
                ((PreparedStatement) up.getStatement()).setDate(6, java.sql.Date.valueOf(zatrudnionyTextField.getText()));
                up.firePreparedUpdate();

                /*up.ExecuteUpdate("INSERT INTO PRACOWNICY(NAZWISKO, PENSJA, PREMIA, ETATY_NAZWA, GODZIN_TYGODNIOWO, DATA_ZATRUDNIENIA)"
                        + " VALUES('" + nazwiskoTextField.getText() + "', " + pensjaTextField.getText() + ", "
                        + premiaTextField.getText() + ", '" + etatComboBox.getSelectedItem().toString() + "', " + godzinyTextField.getText()
                        + ", DATE '" + zatrudnionyTextField.getText() + "')");*/
                Execute q = new Execute();
                q.ExecuteQuery("SELECT ID FROM PRACOWNICY ORDER BY ID DESC FETCH FIRST 1 ROW ONLY");
                q.getRs().next();
                int inserted_id = q.getRs().getInt(1);
                fill(inserted_id);
                visibleButton(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                //Logger.getLogger(PracownikFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                up.ExecutePreparedQuery("UPDATE PRACOWNICY SET NAZWISKO = ? WHERE ID = " + Integer.toString(id));
                ((PreparedStatement) up.getStatement()).setString(1, nazwiskoTextField.getText());
                up.firePreparedUpdate();
                up.ExecutePreparedQuery("UPDATE PRACOWNICY SET PENSJA = ? WHERE ID = " + Integer.toString(id));
                ((PreparedStatement) up.getStatement()).setFloat(1, Float.parseFloat(pensjaTextField.getText()));
                up.firePreparedUpdate();
                up.ExecutePreparedQuery("UPDATE PRACOWNICY SET PREMIA = ? WHERE ID = " + Integer.toString(id));
                ((PreparedStatement) up.getStatement()).setFloat(1, Float.parseFloat(premiaTextField.getText()));
                up.firePreparedUpdate();
                up.ExecutePreparedQuery("UPDATE PRACOWNICY SET ETATY_NAZWA = ? WHERE ID = " + Integer.toString(id));
                ((PreparedStatement) up.getStatement()).setString(1, etatComboBox.getSelectedItem().toString());
                up.firePreparedUpdate();
                up.ExecutePreparedQuery("UPDATE PRACOWNICY SET GODZIN_TYGODNIOWO = ? WHERE ID = " + Integer.toString(id));
                ((PreparedStatement) up.getStatement()).setInt(1, Integer.parseInt(godzinyTextField.getText()));
                up.firePreparedUpdate();
                up.ExecutePreparedQuery("UPDATE PRACOWNICY SET DATA_ZATRUDNIENIA = ? WHERE ID = " + Integer.toString(id));
                ((PreparedStatement) up.getStatement()).setDate(1, java.sql.Date.valueOf(zatrudnionyTextField.getText()));
                up.firePreparedUpdate();

                /*up.ExecuteUpdate("UPDATE PRACOWNICY SET NAZWISKO = '" + nazwiskoTextField.getText() + "' WHERE ID = " + Integer.toString(id));
                up.ExecuteUpdate("UPDATE PRACOWNICY SET PENSJA = " + pensjaTextField.getText() + " WHERE ID = " + Integer.toString(id));
                up.ExecuteUpdate("UPDATE PRACOWNICY SET PREMIA = " + premiaTextField.getText() + " WHERE ID = " + Integer.toString(id));
                up.ExecuteUpdate("UPDATE PRACOWNICY SET ETATY_NAZWA = '" + etatComboBox.getSelectedItem().toString() + "' WHERE ID = " + Integer.toString(id));
                up.ExecuteUpdate("UPDATE PRACOWNICY SET GODZIN_TYGODNIOWO = " + godzinyTextField.getText() + " WHERE ID = " + Integer.toString(id));
                up.ExecuteUpdate("UPDATE PRACOWNICY SET DATA_ZATRUDNIENIA = DATE '" + zatrudnionyTextField.getText() + "' WHERE ID = " + Integer.toString(id));*/
                fill(this.id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                //Logger.getLogger(PracownikFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        parent.refresh();

    }//GEN-LAST:event_changeButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (this.id != -1) {
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            Execute up = new Execute();
            up.ExecuteUpdate("UPDATE PRACOWNICY SET DATA_ZWOLNIENIA = DATE '" + date.toString() + "' WHERE ID = " + Integer.toString(this.id));
            //up.ExecuteUpdate("DELETE FROM PRACOWNICY WHERE ID = " + Integer.toString(id));
            parent.refresh();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void addBiletButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBiletButtonMouseClicked
        BiletDialog bilet = new BiletDialog(this, rootPaneCheckingEnabled);
        bilet.setLocation(Zoo.getShowPosition2D(bilet));
        bilet.setVisible(true);
    }//GEN-LAST:event_addBiletButtonMouseClicked

    private void addRaportButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRaportButtonMouseClicked
        RaportFrame raport = new RaportFrame(this);
        raport.setLocation(Zoo.getShowPosition2(raport));
        if (((String) etatComboBox.getSelectedItem()).compareTo("WETERYNARZ") == 0) {
            raport.setInfo(this.id, 1);
        } else {
            raport.setInfo(this.id, 0);
        }
        raport.setVisible(true);
    }//GEN-LAST:event_addRaportButtonMouseClicked

    private void raportyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_raportyTableMouseClicked
        if (evt.getClickCount() == 2) {
            int selectionIndex = raportyTable.getSelectionModel().getMinSelectionIndex();
            if (selectionIndex >= 0) {
                CacheSqlTableModel tableModel = (CacheSqlTableModel) raportyTable.getModel();
                int selectedId = tableModel.getSelectedId(raportyTable.getSelectedRow());
                RaportFrame raport = new RaportFrame(this);
                raport.setLocation(Zoo.getShowPosition2(raport));

                Execute q = new Execute();
                q.ExecuteQuery("SELECT UWAGI FROM RAPORTY WHERE NUMER = " + Integer.toString(selectedId));

                try {
                    q.getRs().next();
                    raport.fill(q.getRs().getString(1));
                    raport.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PracownikFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_raportyTableMouseClicked

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
            java.util.logging.Logger.getLogger(PracownikFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PracownikFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PracownikFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PracownikFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PracownikFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBiletButton;
    private javax.swing.JButton addRaportButton;
    private javax.swing.JPanel biletyPanel;
    private javax.swing.JScrollPane biletyScrollPane;
    private javax.swing.JTable biletyTable;
    private javax.swing.JPanel buttonBiletyPanel;
    private javax.swing.JPanel buttonRaportyPanel;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton changeButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox<String> etatComboBox;
    private javax.swing.JLabel etatLabel;
    private javax.swing.JLabel godzinyLabel;
    private javax.swing.JTextField godzinyTextField;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel nazwiskoLabel;
    private javax.swing.JTextField nazwiskoTextField;
    private javax.swing.JLabel pensjaLabel;
    private javax.swing.JTextField pensjaTextField;
    private javax.swing.JLabel premiaLabel;
    private javax.swing.JTextField premiaTextField;
    private javax.swing.JPanel raportyPanel;
    private javax.swing.JScrollPane raportyScrollPane;
    private javax.swing.JTable raportyTable;
    private javax.swing.JLabel zatrudnionyLabel;
    private javax.swing.JTextField zatrudnionyTextField;
    // End of variables declaration//GEN-END:variables

}
