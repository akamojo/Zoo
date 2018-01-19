/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author akamojo
 */
public class GatunekFrame extends javax.swing.JFrame {

    private PracownicyFrame parent;
    private boolean newGatunek;
    private boolean showZwierzetaMode = false;

    public String[] columnsZwierzeta = {"Numer wybiegu", "Płeć", "Waga", "Chip"};

    /**
     * frame do dodania nowego gatunku
     *
     * @throws SQLException
     */
    public GatunekFrame() throws SQLException {
        initComponents();
        Zoo.setIconAndCursor(this);
        kategoriaComboBox.setModel(this.loadKategorie());
        typWybieguComboBox.setModel(this.loadWybiegiTypes());

        newGatunek = true;
        nazwaGatunkuTxtField.setEditable(true); // na momencik

        zwierzetaPanel.setVisible(false);
    }

    /**
     * load istniejący gatunek
     *
     * @param nazwa
     * @throws SQLException
     */
    public GatunekFrame(String nazwa) throws SQLException {
        initComponents();
        setIconImage(Zoo.getIcon());
        kategoriaComboBox.setModel(this.loadKategorie());
        typWybieguComboBox.setModel(this.loadWybiegiTypes());

        this.fill(nazwa);
        newGatunek = false;

        zwierzetaPanel.setVisible(false);
        gatunekComboBox.setEnabled(false);
        gatunekComboBox.setVisible(false);
    }

    public void setShowZwierzetaMode() throws SQLException {

        this.loadFirstGatunek();
        this.setShowZw();
    }

    public void setShowZw() throws SQLException {
        newGatunek = false;

        this.showZwierzetaMode = true;
        gatunekComboBox.setEnabled(true);
        gatunekComboBox.setVisible(true);

        nazwaGatunkuTxtField.setVisible(false);
        zwierzetaPanel.setVisible(true);

        gatunekComboBox.setModel(this.loadGatunki());
    }

    public void setShowZwierzetaModeWith(String gatunek) throws SQLException {

        this.fill(gatunek);
        this.setShowZw();
        gatunekComboBox.setSelectedItem(gatunek);

    }

    public void loadFirstGatunek() throws SQLException {
        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT NAZWA FROM GATUNKI ORDER BY NAZWA FETCH FIRST 1 ROW ONLY");
        ResultSet rs = exec.getRs();
        exec.getRs().next();
        String first = rs.getString(1);
        this.fill(first);
    }

    public void updateGatunki() throws SQLException {
        gatunekComboBox.setModel(this.loadGatunki());
    }

    public DefaultComboBoxModel<String> loadGatunki() throws SQLException {

        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT COUNT(*) FROM GATUNKI");
        ResultSet rs = exec.getRs();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        String[] types = new String[count];

        exec.ExecuteQuery("SELECT NAZWA FROM GATUNKI ORDER BY NAZWA");
        rs = exec.getRs();
        for (int i = 0; i < count; i++) {
            rs.next();
            types[i] = rs.getString(1);
        }
        return new javax.swing.DefaultComboBoxModel<String>(types);

    }

    public void updateKategorie() throws SQLException {
        kategoriaComboBox.setModel(this.loadKategorie());
    }

    public DefaultComboBoxModel<String> loadKategorie() throws SQLException {

        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT COUNT(*) FROM KATEGORIE");
        ResultSet rs = exec.getRs();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        String[] types = new String[count];

        exec.ExecuteQuery("SELECT NAZWA FROM KATEGORIE ORDER BY NAZWA");
        rs = exec.getRs();
        for (int i = 0; i < count; i++) {
            rs.next();
            types[i] = rs.getString(1);
        }
        return new javax.swing.DefaultComboBoxModel<String>(types);

    }

    public void updateWybiegiTypes() throws SQLException {
        typWybieguComboBox.setModel(this.loadWybiegiTypes());
    }

    public DefaultComboBoxModel<String> loadWybiegiTypes() throws SQLException {

        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT COUNT(*) FROM TYPY_WYBIEGU ORDER BY NAZWA");
        ResultSet rs = exec.getRs();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        String[] types = new String[count];

        exec.ExecuteQuery("SELECT NAZWA FROM TYPY_WYBIEGU ORDER BY NAZWA");
        rs = exec.getRs();
        for (int i = 0; i < count; i++) {
            rs.next();
            types[i] = rs.getString(1);
        }
        return new javax.swing.DefaultComboBoxModel<String>(types);

    }

    public void fill(String nazwaGatunku) {
        try {
            Execute exec = new Execute();
            exec.ExecutePreparedQuery("select ilosc_pozywienia, rodzaj_pozywienia, potrzebna_przestrzen_m2,"
                    + "kategorie_nazwa, min_licznosc_stada, typ_wybiegu "
                    + "from gatunki where nazwa = ?");
            exec.getStatement().setString(1, nazwaGatunku);
            exec.firePreparedQuery();
            exec.getRs().next();

            nazwaGatunkuTxtField.setText(nazwaGatunku);
            ilPozTextField.setText(exec.getRs().getString(1));
            rodzPozTextField.setText(exec.getRs().getString(2));
            przestrzenTextField.setText(exec.getRs().getString(3));
            kategoriaComboBox.setSelectedItem(exec.getRs().getString(4));

            String stado = exec.getRs().getString(5);
            if (stado != null) {
                stadoTextField.setText(stado);
            } else {
                stadoTextField.setText("");
            }

            typWybieguComboBox.setSelectedItem(exec.getRs().getString(6));

            kategoriaComboBox.setEnabled(false);
            typWybieguComboBox.setEnabled(false);
            nazwaGatunkuTxtField.setEditable(false);

            CacheSqlTableModel model = new CacheSqlTableModel("select wybiegi_nr, plec, waga, chip from zwierzeta "
                    + "where gatunki_nazwa = '" + nazwaGatunku + "'", columnsZwierzeta, "order by wybiegi_nr");
            zwierzetaTable.setModel(model);

            gatunekComboBox.setSelectedItem(nazwaGatunku);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }

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
        addGatunekButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        nazwaGatunkuLabel = new javax.swing.JLabel();
        nazwaGatunkuTxtField = new javax.swing.JTextField();
        kategoriaLabel = new javax.swing.JLabel();
        rodzPozLabel = new javax.swing.JLabel();
        rodzPozTextField = new javax.swing.JTextField();
        ilPozLabel = new javax.swing.JLabel();
        ilPozTextField = new javax.swing.JTextField();
        kategoriaComboBox = new javax.swing.JComboBox<>();
        zwierzetaPanel = new javax.swing.JPanel();
        zwierzetaScrollPane = new javax.swing.JScrollPane();
        zwierzetaTable = new javax.swing.JTable();
        przestrzenTextField = new javax.swing.JTextField();
        przestrzenLabel = new javax.swing.JLabel();
        stadoTextField = new javax.swing.JTextField();
        stadoLabel = new javax.swing.JLabel();
        typWybieguLabel = new javax.swing.JLabel();
        typWybieguComboBox = new javax.swing.JComboBox<>();
        gatunekComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gatunek");
        setMinimumSize(new java.awt.Dimension(350, 300));
        getContentPane().setLayout(new java.awt.GridBagLayout());

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

        addGatunekButton.setText("Nowy...");
        addGatunekButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGatunekButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(addGatunekButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        getContentPane().add(buttonsPanel, gridBagConstraints);

        mainPanel.setLayout(new java.awt.GridBagLayout());

        nazwaGatunkuLabel.setText("Nazwa gatunku");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(nazwaGatunkuLabel, gridBagConstraints);

        nazwaGatunkuTxtField.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(nazwaGatunkuTxtField, gridBagConstraints);

        kategoriaLabel.setText("Kategoria");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(kategoriaLabel, gridBagConstraints);

        rodzPozLabel.setText("Rodzaj pożywienia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(rodzPozLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(rodzPozTextField, gridBagConstraints);

        ilPozLabel.setText("Ilość pożywienia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(ilPozLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(ilPozTextField, gridBagConstraints);

        kategoriaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRAK KATEGORII" }));
        kategoriaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kategoriaComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(kategoriaComboBox, gridBagConstraints);

        zwierzetaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Zwierzęta"));
        zwierzetaPanel.setLayout(new java.awt.BorderLayout());

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
        zwierzetaScrollPane.setViewportView(zwierzetaTable);

        zwierzetaPanel.add(zwierzetaScrollPane, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.weighty = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(zwierzetaPanel, gridBagConstraints);
        zwierzetaPanel.getAccessibleContext().setAccessibleName("Zwierzęta");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(przestrzenTextField, gridBagConstraints);

        przestrzenLabel.setText("Potrzebna przestrzeń (m2)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(przestrzenLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(stadoTextField, gridBagConstraints);

        stadoLabel.setText("Minimalna liczność stada:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(stadoLabel, gridBagConstraints);

        typWybieguLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typWybieguLabel.setText("Typ wybiegu");
        typWybieguLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(typWybieguLabel, gridBagConstraints);

        typWybieguComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRAK TYPÓW WYBIEGU" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(typWybieguComboBox, gridBagConstraints);

        gatunekComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRAK GATUNKÓW" }));
        gatunekComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gatunekComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(gatunekComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 0.8;
        getContentPane().add(mainPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeButtonActionPerformed
        Execute exec = new Execute();
        try {
            if (newGatunek) {
                // insert
                exec.ExecutePreparedQuery("INSERT INTO GATUNKI(NAZWA, ILOSC_POZYWIENIA, RODZAJ_POZYWIENIA,"
                        + "POTRZEBNA_PRZESTRZEN_M2, KATEGORIE_NAZWA, MIN_LICZNOSC_STADA, TYP_WYBIEGU) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)");
                exec.getStatement().setString(1, nazwaGatunkuTxtField.getText().toString());
                exec.getStatement().setInt(2, new Integer(ilPozTextField.getText().toString()));
                exec.getStatement().setString(3, rodzPozTextField.getText().toString());
                exec.getStatement().setInt(4, new Integer(przestrzenTextField.getText().toString()));
                exec.getStatement().setString(5, kategoriaComboBox.getSelectedItem().toString());

                if ("".equals(stadoTextField.getText().toString().trim())) {
                    exec.getStatement().setNull(6, java.sql.Types.INTEGER);
                } else {
                    exec.getStatement().setInt(6, new Integer(stadoTextField.getText().toString()));
                }

                exec.getStatement().setString(7, typWybieguComboBox.getSelectedItem().toString());
                exec.firePreparedUpdate();
                nazwaGatunkuTxtField.setEditable(false);
                newGatunek = false;

                this.setShowZwierzetaModeWith(nazwaGatunkuTxtField.getText().toString());
            } else {
                String nazwa = "'" + nazwaGatunkuTxtField.getText().toString() + "'";

                exec.ExecutePreparedQuery("UPDATE GATUNKI SET ILOSC_POZYWIENIA = ? WHERE NAZWA = " + nazwa);
                exec.getStatement().setInt(1, new Integer(ilPozTextField.getText().toString()));
                exec.firePreparedUpdate();

                exec.ExecutePreparedQuery("UPDATE GATUNKI SET RODZAJ_POZYWIENIA = ? WHERE NAZWA = " + nazwa);
                exec.getStatement().setString(1, rodzPozTextField.getText().toString());
                exec.firePreparedUpdate();

                exec.ExecutePreparedQuery("UPDATE GATUNKI SET POTRZEBNA_PRZESTRZEN_M2 = ? WHERE NAZWA = " + nazwa);
                exec.getStatement().setInt(1, new Integer(przestrzenTextField.getText().toString()));
                exec.firePreparedUpdate();

                if (!"".equals(stadoTextField.getText().toString())) {
                    exec.ExecutePreparedQuery("UPDATE GATUNKI SET MIN_LICZNOSC_STADA = ? WHERE NAZWA = " + nazwa);
                    exec.getStatement().setInt(1, new Integer(stadoTextField.getText().toString()));
                    exec.firePreparedUpdate();
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_changeButtonActionPerformed

    private void zwierzetaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zwierzetaTableMouseClicked

    }//GEN-LAST:event_zwierzetaTableMouseClicked

    private void kategoriaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kategoriaComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kategoriaComboBoxActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {
            Execute up = new Execute();
            up.ExecuteUpdate("DELETE FROM GATUNKI WHERE NAZWA = '" + gatunekComboBox.getSelectedItem().toString() + "'");
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Gatunek usunięty!", "DELETE", JOptionPane.INFORMATION_MESSAGE);
            this.loadFirstGatunek();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void gatunekComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gatunekComboBoxActionPerformed
        // TODO add your handling code here:
        this.fill(gatunekComboBox.getSelectedItem().toString());
        // CacheSqlTableModel model = new CacheSqlTableModel("select gatunki_nazwa, plec, chip from zwierzeta where pracownicy_id = " + Integer.toString(id), columns, "ORDER BY nr");

    }//GEN-LAST:event_gatunekComboBoxActionPerformed

    private void addGatunekButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGatunekButtonActionPerformed
        try {

            kategoriaComboBox.setModel(this.loadKategorie());
            typWybieguComboBox.setModel(this.loadWybiegiTypes());

            nazwaGatunkuTxtField.setEditable(true); // na momencik
            newGatunek = true;

            this.showZwierzetaMode = true;
            gatunekComboBox.setEnabled(false);
            gatunekComboBox.setVisible(false);

            nazwaGatunkuTxtField.setVisible(true);
            zwierzetaPanel.setVisible(false);

            kategoriaComboBox.setEnabled(true);
            typWybieguComboBox.setEnabled(true);

            nazwaGatunkuTxtField.setText("");
            ilPozTextField.setText("");
            rodzPozTextField.setText("");
            przestrzenTextField.setText("");
            stadoTextField.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addGatunekButtonActionPerformed

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
            java.util.logging.Logger.getLogger(GatunekFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GatunekFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GatunekFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GatunekFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GatunekFrame().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GatunekFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addGatunekButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton changeButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox<String> gatunekComboBox;
    private javax.swing.JLabel ilPozLabel;
    private javax.swing.JTextField ilPozTextField;
    private javax.swing.JComboBox<String> kategoriaComboBox;
    private javax.swing.JLabel kategoriaLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel nazwaGatunkuLabel;
    private javax.swing.JTextField nazwaGatunkuTxtField;
    private javax.swing.JLabel przestrzenLabel;
    private javax.swing.JTextField przestrzenTextField;
    private javax.swing.JLabel rodzPozLabel;
    private javax.swing.JTextField rodzPozTextField;
    private javax.swing.JLabel stadoLabel;
    private javax.swing.JTextField stadoTextField;
    private javax.swing.JComboBox<String> typWybieguComboBox;
    private javax.swing.JLabel typWybieguLabel;
    private javax.swing.JPanel zwierzetaPanel;
    private javax.swing.JScrollPane zwierzetaScrollPane;
    private javax.swing.JTable zwierzetaTable;
    // End of variables declaration//GEN-END:variables

}
