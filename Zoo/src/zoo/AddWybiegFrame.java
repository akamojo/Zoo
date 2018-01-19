/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author julka
 */
public class AddWybiegFrame extends javax.swing.JFrame {

    private WybiegiFrame parent;
    
    public AddWybiegFrame() throws SQLException {
        initComponents();
        typComboBox.setModel(this.loadWybiegiTypes());
        Zoo.setIconAndCursor(this);
    }
    /**
     * Creates new form AddWybiegFrame
     */
    public AddWybiegFrame(WybiegiFrame p) throws SQLException {
        this.parent = p;
        initComponents();
        typComboBox.setModel(this.loadWybiegiTypes());
        Zoo.setIconAndCursor(this);
    }
    
    public void updateWybiegiTypes() throws SQLException {
         typComboBox.setModel(this.loadWybiegiTypes());
    }
    
    public DefaultComboBoxModel<String> loadWybiegiTypes() throws SQLException {
        
        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT COUNT(*) FROM TYPY_WYBIEGU ORDER BY NAZWA");
        ResultSet rs = exec.getRs();
        
        int count = 0;
        if (rs.next())
            count = rs.getInt(1);
        String[] types = new String[count];
        
        exec.ExecuteQuery("SELECT NAZWA FROM TYPY_WYBIEGU ORDER BY NAZWA");
        rs = exec.getRs();
        for (int i = 0; i < count; i++) {
            rs.next();
            types[i] = rs.getString(1);
        }
        return new javax.swing.DefaultComboBoxModel<String>(types);
        
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

        typWybieguPanel = new javax.swing.JPanel();
        typComboBox = new javax.swing.JComboBox<>();
        typWybieguLabel = new javax.swing.JLabel();
        addTypButton = new javax.swing.JButton();
        powPanel = new javax.swing.JPanel();
        powLabel = new javax.swing.JLabel();
        powTextField = new javax.swing.JTextField();
        opisPanel = new javax.swing.JPanel();
        descLabel = new javax.swing.JLabel();
        opisScrollPane = new javax.swing.JScrollPane();
        opisTextArea = new javax.swing.JTextArea();
        buttonPanel = new javax.swing.JPanel();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(311, 284));
        setPreferredSize(new java.awt.Dimension(350, 350));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                tellParent(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        typWybieguPanel.setLayout(new java.awt.GridBagLayout());

        typComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRAK TYPÓW" }));
        typComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        typWybieguPanel.add(typComboBox, gridBagConstraints);

        typWybieguLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typWybieguLabel.setText("Typ wybiegu:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        typWybieguPanel.add(typWybieguLabel, gridBagConstraints);

        addTypButton.setText("Dodaj typ...");
        addTypButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTypButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        typWybieguPanel.add(addTypButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(typWybieguPanel, gridBagConstraints);

        powPanel.setLayout(new java.awt.GridBagLayout());

        powLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        powLabel.setText("Powierzchnia [m2]:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        powPanel.add(powLabel, gridBagConstraints);

        powTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                powTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        powPanel.add(powTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(powPanel, gridBagConstraints);

        opisPanel.setLayout(new java.awt.GridBagLayout());

        descLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descLabel.setText("Opis wybiegu:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        opisPanel.add(descLabel, gridBagConstraints);

        opisScrollPane.setMaximumSize(new java.awt.Dimension(37261, 32767));
        opisScrollPane.setPreferredSize(new java.awt.Dimension(250, 100));

        opisTextArea.setColumns(20);
        opisTextArea.setFont(new java.awt.Font("Consolas", 2, 14)); // NOI18N
        opisTextArea.setRows(5);
        opisTextArea.setMaximumSize(new java.awt.Dimension(1237878917, 2147483647));
        opisTextArea.setMinimumSize(new java.awt.Dimension(250, 20));
        opisTextArea.setName(""); // NOI18N
        opisTextArea.setPreferredSize(new java.awt.Dimension(250, 89));
        opisScrollPane.setViewportView(opisTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        opisPanel.add(opisScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(opisPanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        confirmButton.setText("Zatwierdź");
        confirmButton.setPreferredSize(new java.awt.Dimension(150, 29));
        confirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmButtonMouseClicked(evt);
            }
        });
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(confirmButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(buttonPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typComboBoxActionPerformed

    private void confirmButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmButtonMouseClicked
        Execute exec = new Execute();
        try {
            exec.ExecutePreparedQuery("INSERT INTO WYBIEGI(POWIERZCHNIA, TYPY_WYBIEGU_NAZWA, OPIS_WYBIEGU) VALUES(?, ?, ?)");
             exec.getStatement().setInt(1, new Integer(powTextField.getText()));
             exec.getStatement().setString(2, typComboBox.getSelectedItem().toString());
             exec.getStatement().setString(3, opisTextArea.getText().toString());
            exec.firePreparedUpdate();
            this.parent.refreshAll();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_confirmButtonMouseClicked

    private void addTypButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTypButtonActionPerformed
        AddTypWybieguDialog dialog = new AddTypWybieguDialog(this, rootPaneCheckingEnabled);
        dialog.setLocation(Zoo.getShowPosition2D(dialog));
        dialog.setVisible(true);
    }//GEN-LAST:event_addTypButtonActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void powTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_powTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_powTextFieldActionPerformed

    private void tellParent(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_tellParent
        if (this.parent != null) {
            this.parent.addRemoveAbility(true);
        }
    }//GEN-LAST:event_tellParent

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
            java.util.logging.Logger.getLogger(AddWybiegFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddWybiegFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddWybiegFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddWybiegFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddWybiegFrame().setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Nie udało się pobrać danych o wybiegach z bazy danych.", "SQL error :c", JOptionPane.ERROR_MESSAGE);
                    // Logger.getLogger(AddWybiegFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTypButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel descLabel;
    private javax.swing.JPanel opisPanel;
    private javax.swing.JScrollPane opisScrollPane;
    private javax.swing.JTextArea opisTextArea;
    private javax.swing.JLabel powLabel;
    private javax.swing.JPanel powPanel;
    private javax.swing.JTextField powTextField;
    private javax.swing.JComboBox<String> typComboBox;
    private javax.swing.JLabel typWybieguLabel;
    private javax.swing.JPanel typWybieguPanel;
    // End of variables declaration//GEN-END:variables
}
