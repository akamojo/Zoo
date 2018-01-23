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
import static zoo.Zoo.getShowPosition2;

/**
 *
 * @author julka
 */
public class ZwierzeFrame extends javax.swing.JFrame {

    private WybiegiFrame parent;

    /*
    PROBLEM:
    Jeśli
    1. dodajemy zwierzę
    2. przypomniało się żeby dodać wybieg
    3. chcemy wybrać numer wybiegu
    ... to nie ma tego nowego numeru wybiegu, bo nie poszło info z WybiegiFrame :(
     */
    /**
     * Creates new form AddZwerzeFrame
     */
    public ZwierzeFrame() throws SQLException {
        initComponents();
        gatunekComboBox.setModel(this.loadGatunki());
        nrWybieguComboBox.setModel(this.loadNrWybiegu());
        Zoo.setIconAndCursor(this);
    }

    public ZwierzeFrame(WybiegiFrame p) throws SQLException {
        this.parent = p;
        initComponents();
        gatunekComboBox.setModel(this.loadGatunki());
        nrWybieguComboBox.setModel(this.loadNrWybiegu());
        Zoo.setIconAndCursor(this);
    }

    public ZwierzeFrame(WybiegiFrame p, Integer chip) throws SQLException {
        this.parent = p;
        initComponents();
        gatunekComboBox.setModel(this.loadGatunki());
        nrWybieguComboBox.setModel(this.loadNrWybiegu());
        this.fill(chip);
        Zoo.setIconAndCursor(this);
    }

    public ZwierzeFrame(Integer chip) throws SQLException {
        this.parent = null;
        initComponents();
        gatunekComboBox.setModel(this.loadGatunki());
        nrWybieguComboBox.setModel(this.loadNrWybiegu());
        this.fill(chip);
        Zoo.setIconAndCursor(this);
    }

    public void updateGatunki() throws SQLException {
        gatunekComboBox.setModel(this.loadGatunki());
    }

    public DefaultComboBoxModel<String> loadGatunki() throws SQLException {

        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT COUNT(*) FROM GATUNKI_ZOO ORDER BY NAZWA");
        ResultSet rs = exec.getRs();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        String[] types = new String[count];

        exec.ExecuteQuery("SELECT NAZWA FROM GATUNKI_ZOO ORDER BY NAZWA");
        rs = exec.getRs();
        for (int i = 0; i < count; i++) {
            rs.next();
            types[i] = rs.getString(1);
        }
        return new javax.swing.DefaultComboBoxModel<String>(types);

    }

    public void updateNrWybiegu() throws SQLException {
        nrWybieguComboBox.setModel(this.loadNrWybiegu());
    }

    public DefaultComboBoxModel<String> loadNrWybiegu() throws SQLException {

        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT COUNT(*) FROM WYBIEGI");
        ResultSet rs = exec.getRs();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        String[] types = new String[count];

        exec.ExecuteQuery("SELECT TO_CHAR(NR) || ' (' || TYPY_WYBIEGU_NAZWA || ')' FROM WYBIEGI ORDER BY NR ASC");
        rs = exec.getRs();
        for (int i = 0; i < count; i++) {
            rs.next();
            types[i] = rs.getString(1);
        }
        return new javax.swing.DefaultComboBoxModel<String>(types);

    }

    public void fill(int chip) {
        try {
            Execute exec = new Execute();
            exec.ExecutePreparedQuery("select chip, waga, data_urodzin, data_przyjecia,"
                    + "gatunki_nazwa, TO_CHAR(wybiegi.NR) || ' (' || wybiegi.TYPY_WYBIEGU_NAZWA || ')', plec, data_opuszczenia_zoo "
                    + "from zwierzeta join wybiegi on wybiegi_nr = nr where chip = ?");
            exec.getStatement().setInt(1, chip);
            exec.firePreparedQuery();
            exec.getRs().next();

            chipTextField.setText(exec.getRs().getString(1));
            wagaTextField.setText(exec.getRs().getString(2));

            String dataUr = exec.getRs().getString(3);
            if (dataUr != null) {
                dataUrTextField.setText(exec.getRs().getDate(3).toString());
            } else {
                dataUrTextField.setText("");
            }

            String dataZoo = exec.getRs().getString(4);
            if (dataZoo != null) {
                dataZooTextField.setText(exec.getRs().getDate(4).toString());
            } else {
                dataZooTextField.setText("");
            }

            gatunekComboBox.setSelectedItem(exec.getRs().getString(5));
            nrWybieguComboBox.setSelectedItem(exec.getRs().getString(6));
            plecComboBox.setSelectedItem(exec.getRs().getString(7));

            try {
                leaveZooTextField.setText(exec.getRs().getDate(8).toString());
            } catch (NullPointerException ex) {
                leaveZooTextField.setText("");
            }

            gatunekComboBox.setEnabled(false);
            leaveZooTextField.setEnabled(false);

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
        mainPanel = new javax.swing.JPanel();
        chipLabel = new javax.swing.JLabel();
        chipTextField = new javax.swing.JTextField();
        gatunekLabel = new javax.swing.JLabel();
        wagaTextField = new javax.swing.JTextField();
        wagaLabel = new javax.swing.JLabel();
        dataUrLabel = new javax.swing.JLabel();
        dataZooLabel = new javax.swing.JLabel();
        plecLabel = new javax.swing.JLabel();
        leaveZooLabel = new javax.swing.JLabel();
        leaveZooTextField = new javax.swing.JTextField();
        gatunekComboBox = new javax.swing.JComboBox<>();
        newGatunekButton = new javax.swing.JButton();
        wybiegLabel = new javax.swing.JLabel();
        nrWybieguComboBox = new javax.swing.JComboBox<>();
        plecComboBox = new javax.swing.JComboBox<>();
        dataUrTextField = new javax.swing.JFormattedTextField();
        dataZooTextField = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                tellParent(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        changeButton.setText("Zatwierdź");
        changeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonsPanel.add(changeButton, gridBagConstraints);

        deleteButton.setText("Usuń");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonsPanel.add(deleteButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.3;
        getContentPane().add(buttonsPanel, gridBagConstraints);

        mainPanel.setLayout(new java.awt.GridBagLayout());

        chipLabel.setText("Numer CHIP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(chipLabel, gridBagConstraints);

        chipTextField.setEditable(false);
        chipTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(chipTextField, gridBagConstraints);

        gatunekLabel.setText("Gatunek");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(gatunekLabel, gridBagConstraints);

        wagaTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(wagaTextField, gridBagConstraints);

        wagaLabel.setText("Waga");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(wagaLabel, gridBagConstraints);

        dataUrLabel.setText("Data urodzin (YYYY-MM-DD)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(dataUrLabel, gridBagConstraints);

        dataZooLabel.setText("Data przyjęcia do ZOO (YYYY-MM-DD)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(dataZooLabel, gridBagConstraints);

        plecLabel.setText("Płeć");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(plecLabel, gridBagConstraints);

        leaveZooLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        leaveZooLabel.setText("Data opuszczenia ZOO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(leaveZooLabel, gridBagConstraints);

        leaveZooTextField.setEditable(false);
        leaveZooTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(leaveZooTextField, gridBagConstraints);

        gatunekComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRAK GATUNKÓW" }));
        gatunekComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gatunekComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(gatunekComboBox, gridBagConstraints);

        newGatunekButton.setText("Nowy gatunek...");
        newGatunekButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGatunekButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        mainPanel.add(newGatunekButton, gridBagConstraints);

        wybiegLabel.setText("Numer wybiegu");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(wybiegLabel, gridBagConstraints);

        nrWybieguComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRAK NUMERÓW" }));
        nrWybieguComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nrWybieguComboBoxMouseClicked(evt);
            }
        });
        nrWybieguComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nrWybieguComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(nrWybieguComboBox, gridBagConstraints);

        plecComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "f", "m", "-" }));
        plecComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plecComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(plecComboBox, gridBagConstraints);

        dataUrTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataUrTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataUrTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(dataUrTextField, gridBagConstraints);

        dataZooTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataZooTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(dataZooTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.weighty = 0.9;
        getContentPane().add(mainPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeButtonActionPerformed
        Execute exec = new Execute();
        if ("".equals(chipTextField.getText().trim())) {
            // no chip = add new
            try {
                exec.ExecutePreparedQuery("INSERT INTO ZWIERZETA(WAGA, DATA_URODZIN, DATA_PRZYJECIA, GATUNKI_NAZWA, WYBIEGI_NR, PLEC) "
                        + "VALUES(?, ?, ?, ?, ?, ?)");
                exec.getStatement().setFloat(1, Float.parseFloat(wagaTextField.getText()));

                if ("".equals(dataUrTextField.getText().toString().trim())) {
                    exec.getStatement().setNull(2, java.sql.Types.DATE);
                } else {
                    exec.getStatement().setDate(2, java.sql.Date.valueOf(dataUrTextField.getText()));
                }

                if ("".equals(dataZooTextField.getText().toString().trim())) {
                    exec.getStatement().setNull(3, java.sql.Types.DATE);
                } else {
                    exec.getStatement().setDate(3, java.sql.Date.valueOf(dataZooTextField.getText()));
                }

                //  exec.getStatement().setDate(3, java.sql.Date.valueOf(dataZooTextField.getText()));
                exec.getStatement().setString(4, gatunekComboBox.getSelectedItem().toString());
                exec.getStatement().setInt(5, Integer.parseInt(nrWybieguComboBox.getSelectedItem().toString().split(" ")[0]));
                exec.getStatement().setString(6, plecComboBox.getSelectedItem().toString());
                exec.firePreparedUpdate();

                exec.ExecuteQuery("SELECT CHIP FROM ZWIERZETA ORDER BY CHIP DESC FETCH FIRST 1 ROW ONLY");
                exec.getRs().next();
                int inserted_chip = exec.getRs().getInt(1);
                this.fill(inserted_chip);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error from changeButtonActionPerformed", JOptionPane.ERROR_MESSAGE);
                //Logger.getLogger(PracownikFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            //try {
            String chip = chipTextField.getText();
            exec.ExecutePreparedQuery("UPDATE ZWIERZETA SET PLEC = ? WHERE CHIP = " + chip);
            try {
                exec.getStatement().setString(1, plecComboBox.getSelectedItem().toString());
                exec.firePreparedUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error from changeButtonActionPerformed", JOptionPane.ERROR_MESSAGE);
            }

            exec.ExecutePreparedQuery("UPDATE ZWIERZETA SET WAGA = ? WHERE CHIP = " + chip);
            try {
                exec.getStatement().setFloat(1, Float.parseFloat(wagaTextField.getText()));
                exec.firePreparedUpdate();
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error from changeButtonActionPerformed", JOptionPane.ERROR_MESSAGE);
            }

            exec.ExecutePreparedQuery("UPDATE ZWIERZETA SET WYBIEGI_NR = ? WHERE CHIP = " + chip);
            try {
                exec.getStatement().setInt(1, new Integer(nrWybieguComboBox.getSelectedItem().toString().split(" ")[0]));
                exec.firePreparedUpdate();
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error from changeButtonActionPerformed", JOptionPane.ERROR_MESSAGE);
            }

            if (!"".equals(dataUrTextField.getText())) {
                exec.ExecutePreparedQuery("UPDATE ZWIERZETA SET DATA_URODZIN = ? WHERE CHIP = " + chip);
                try {
                    exec.getStatement().setDate(1, java.sql.Date.valueOf(dataUrTextField.getText()));
                    exec.firePreparedUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error from changeButtonActionPerformed", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (!"".equals(dataZooTextField.getText())) {
                exec.ExecutePreparedQuery("UPDATE ZWIERZETA SET DATA_PRZYJECIA = ? WHERE CHIP = " + chip);
                try {
                    exec.getStatement().setDate(1, java.sql.Date.valueOf(dataZooTextField.getText()));
                    exec.firePreparedUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error from changeButtonActionPerformed", JOptionPane.ERROR_MESSAGE);
                }
            }

            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Zmiany wprowadzone! :)", "Zatwierdź - rezultat", JOptionPane.INFORMATION_MESSAGE);

            /*} catch (Exception ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error from changeButtonActionPerformed", JOptionPane.ERROR_MESSAGE);
                // JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Nie udało się wprowadzić wszystkich zmian.", "Zatwierdź - ...", JOptionPane.INFORMATION_MESSAGE);
            }*/
            this.fill(new Integer(chipTextField.getText()));
        }
    }//GEN-LAST:event_changeButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Execute exec = new Execute();
        String chip = chipTextField.getText();
        int result = exec.ExecuteUpdate_getChanges("UPDATE ZWIERZETA SET DATA_OPUSZCZENIA_ZOO = DATE '" + date.toString() + "' WHERE CHIP = " + chip);
        if (result > 0) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Zwierzę zostało usunięte z ZOO; nadal istnieje w raportach i ocenach.",
                    "Sukces", JOptionPane.INFORMATION_MESSAGE);
            this.fill(new Integer(chip));
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void gatunekComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gatunekComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gatunekComboBoxActionPerformed

    private void nrWybieguComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nrWybieguComboBoxActionPerformed

    }//GEN-LAST:event_nrWybieguComboBoxActionPerformed

    private void nrWybieguComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nrWybieguComboBoxMouseClicked
        try {
            this.updateNrWybiegu();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_nrWybieguComboBoxMouseClicked

    private void plecComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plecComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plecComboBoxActionPerformed

    private void newGatunekButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGatunekButtonActionPerformed
        try {
            GatunekFrame w = new GatunekFrame(this);
            w.setLocation(getShowPosition2(w));
            w.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(ZwierzeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_newGatunekButtonActionPerformed

    private void tellParent(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_tellParent
        if (this.parent != null) {
            this.parent.addRemoveAbility(true);
        }
    }//GEN-LAST:event_tellParent

    private void dataUrTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataUrTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataUrTextFieldActionPerformed

    private void dataZooTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataZooTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataZooTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(ZwierzeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ZwierzeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ZwierzeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ZwierzeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ZwierzeFrame().setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                    //Logger.getLogger(ZwierzeFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton changeButton;
    private javax.swing.JLabel chipLabel;
    private javax.swing.JTextField chipTextField;
    private javax.swing.JLabel dataUrLabel;
    private javax.swing.JFormattedTextField dataUrTextField;
    private javax.swing.JLabel dataZooLabel;
    private javax.swing.JFormattedTextField dataZooTextField;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox<String> gatunekComboBox;
    private javax.swing.JLabel gatunekLabel;
    private javax.swing.JLabel leaveZooLabel;
    private javax.swing.JTextField leaveZooTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton newGatunekButton;
    private javax.swing.JComboBox<String> nrWybieguComboBox;
    private javax.swing.JComboBox<String> plecComboBox;
    private javax.swing.JLabel plecLabel;
    private javax.swing.JLabel wagaLabel;
    private javax.swing.JTextField wagaTextField;
    private javax.swing.JLabel wybiegLabel;
    // End of variables declaration//GEN-END:variables
}
