/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static zoo.Zoo.getShowPosition2;

/**
 *
 * @author akamojo
 */
public class RaportFrame extends javax.swing.JFrame {

    private int id;
    private int etat;
    private PracownikFrame parent;
    private int nr = -1;
    private int numer;
    private boolean czyZwierze;

    /**
     * Creates new form RaportFrame
     */
    public RaportFrame() {
        initComponents();
        setIconImage(Zoo.getIcon());
    }

    public void simpleMode() {
        zwierzePanel.setVisible(false);
        linkPanel.setVisible(false);
        buttonPanel.setVisible(false);
        uwagiTextArea.setEditable(false);
    }

    public RaportFrame(PracownikFrame parent) {
        this.parent = parent;
        Zoo.setIconAndCursor(this);
        initComponents();
    }

    public void setInfo(int id, int etat) {
        this.id = id;
        this.etat = etat;
        if (etat == 0) {
            zwierzePanel.setVisible(false);
            wybiegPanel.setVisible(true);
            try {
                updateNrWybiegu();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            zwierzePanel.setVisible(true);
            wybiegPanel.setVisible(false);
            try {
                updateChipZwierzecia();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updateChipZwierzecia() throws SQLException {
        chipZwierzeciaComboBox.setModel(this.loadChipZwierzecia());
    }

    public DefaultComboBoxModel<String> loadChipZwierzecia() throws SQLException {

        Execute exec = new Execute();
        exec.ExecuteQuery("SELECT COUNT(*) FROM ZWIERZETA");
        ResultSet rs = exec.getRs();

        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        String[] types = new String[count];

        exec.ExecuteQuery("SELECT TO_CHAR(CHIP) || ' (' || GATUNKI_NAZWA || ')' FROM ZWIERZETA ORDER BY CHIP ASC");
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

    public void fillen() {
        this.linkButton.setVisible(false);
    }

    public void notFillen() {
        this.linkSpecialButton.setVisible(false);
    }

    public void fill(int numer) {
        try {
            zwierzePanel.setVisible(false);
            wybiegPanel.setVisible(false);
            uwagiTextArea.setEditable(false);
            buttonPanel.setVisible(false);
            this.nr = numer;

            Execute q = new Execute();
            q.ExecuteQuery("SELECT NVL(ZWIERZETA_CHIP, -1), NVL(WYBIEGI_NR, -1), UWAGI FROM RAPORTY WHERE NUMER = " + Integer.toString(numer));
            q.getRs().next();
            int chip = q.getRs().getInt(1);
            int nr = q.getRs().getInt(2);
            String uwagi = q.getRs().getString(3);
            uwagiTextArea.setText(uwagi);

            if (chip != -1) {
                this.numer = chip;
                this.czyZwierze = true;
                this.linkSpecialButton.setText("Wyświetl zwierzę");
            } else {
                this.numer = nr;
                this.czyZwierze = false;
                this.linkSpecialButton.setText("Wyświetl wybieg");
            }

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

        linkPanel = new javax.swing.JPanel();
        linkButton = new javax.swing.JButton();
        linkSpecialButton = new javax.swing.JButton();
        zwierzePanel = new javax.swing.JPanel();
        zwierzeLabel = new javax.swing.JLabel();
        chipZwierzeciaComboBox = new javax.swing.JComboBox<>();
        wybiegPanel = new javax.swing.JPanel();
        wybiegLabel = new javax.swing.JLabel();
        nrWybieguComboBox = new javax.swing.JComboBox<>();
        buttonPanel = new javax.swing.JPanel();
        changeButton = new javax.swing.JButton();
        uwagiPanel = new javax.swing.JPanel();
        uwagiLabel = new javax.swing.JLabel();
        uwagiScrollPane = new javax.swing.JScrollPane();
        uwagiTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Raport");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        linkButton.setText("Wyświetl dostępne zwierzęta i wybiegi");
        linkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linkButtonMouseClicked(evt);
            }
        });
        linkPanel.add(linkButton);

        linkSpecialButton.setText("Wyświetl");
        linkSpecialButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linkSpecialButtonMouseClicked(evt);
            }
        });
        linkPanel.add(linkSpecialButton);

        getContentPane().add(linkPanel, new java.awt.GridBagConstraints());

        zwierzePanel.setLayout(new java.awt.GridBagLayout());

        zwierzeLabel.setText("Podaj chip zwierzęcia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zwierzePanel.add(zwierzeLabel, gridBagConstraints);

        chipZwierzeciaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO NUMBER" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        zwierzePanel.add(chipZwierzeciaComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(zwierzePanel, gridBagConstraints);

        wybiegPanel.setLayout(new java.awt.GridBagLayout());

        wybiegLabel.setText("Podaj numer wybiegu:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        wybiegPanel.add(wybiegLabel, gridBagConstraints);

        nrWybieguComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO NUMBER" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        wybiegPanel.add(nrWybieguComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(wybiegPanel, gridBagConstraints);

        changeButton.setText("Zatwierdź");
        changeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeButtonMouseClicked(evt);
            }
        });
        buttonPanel.add(changeButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(buttonPanel, gridBagConstraints);

        uwagiPanel.setLayout(new java.awt.GridBagLayout());

        uwagiLabel.setText("Uwagi:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        uwagiPanel.add(uwagiLabel, gridBagConstraints);

        uwagiScrollPane.setMaximumSize(new java.awt.Dimension(37261, 32767));
        uwagiScrollPane.setPreferredSize(new java.awt.Dimension(250, 100));

        uwagiTextArea.setColumns(20);
        uwagiTextArea.setFont(new java.awt.Font("Consolas", 2, 14)); // NOI18N
        uwagiTextArea.setLineWrap(true);
        uwagiTextArea.setRows(3);
        uwagiTextArea.setFocusTraversalPolicyProvider(true);
        uwagiTextArea.setMaximumSize(new java.awt.Dimension(1237878917, 2147483647));
        uwagiTextArea.setMinimumSize(new java.awt.Dimension(250, 20));
        uwagiTextArea.setName(""); // NOI18N
        uwagiScrollPane.setViewportView(uwagiTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        uwagiPanel.add(uwagiScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(uwagiPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeButtonMouseClicked
        Execute up = new Execute();
        if (this.nr == -1) {
            if (etat == 1) {
                try {
                    up.ExecutePreparedQuery("INSERT INTO RAPORTY(PRACOWNICY_ID, UWAGI, ZWIERZETA_CHIP) VALUES(?, ?, ?)");
                    up.getStatement().setInt(1, id);
                    up.getStatement().setString(2, uwagiTextArea.getText());
                    up.getStatement().setInt(3, new Integer(chipZwierzeciaComboBox.getSelectedItem().toString().split(" ")[0]));
                    up.firePreparedUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                try {
                    up.ExecutePreparedQuery("INSERT INTO RAPORTY(PRACOWNICY_ID, UWAGI, WYBIEGI_NR) VALUES(?, ?, ?)");
                    up.getStatement().setInt(1, id);
                    up.getStatement().setString(2, uwagiTextArea.getText());
                    up.getStatement().setInt(3, new Integer(nrWybieguComboBox.getSelectedItem().toString().split(" ")[0]));
                    up.firePreparedUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            try {
                up.ExecutePreparedQuery("UPDATE RAPORTY SET UWAGI = ? WHERE NUMER = " + Integer.toString(this.nr));
                up.getStatement().setString(1, uwagiTextArea.getText());
                up.firePreparedUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        parent.refreshRaporty();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_changeButtonMouseClicked

    private void linkButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linkButtonMouseClicked
        WybiegiFrame w = new WybiegiFrame();
        w.setLocation(getShowPosition2(w));
        w.setVisible(true);
    }//GEN-LAST:event_linkButtonMouseClicked

    private void linkSpecialButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linkSpecialButtonMouseClicked
        if (this.czyZwierze == true) {
            try {
                ZwierzeFrame z = new ZwierzeFrame(numer);
                z.setLocation(Zoo.getShowPosition2(z));
                z.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(OcenaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                WybiegFrame w = new WybiegFrame(numer);
                w.setLocation(Zoo.getShowPosition2(w));
                w.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(OcenaFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_linkSpecialButtonMouseClicked

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
            java.util.logging.Logger.getLogger(RaportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RaportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RaportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RaportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RaportFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton changeButton;
    private javax.swing.JComboBox<String> chipZwierzeciaComboBox;
    private javax.swing.JButton linkButton;
    private javax.swing.JPanel linkPanel;
    private javax.swing.JButton linkSpecialButton;
    private javax.swing.JComboBox<String> nrWybieguComboBox;
    private javax.swing.JLabel uwagiLabel;
    private javax.swing.JPanel uwagiPanel;
    private javax.swing.JScrollPane uwagiScrollPane;
    private javax.swing.JTextArea uwagiTextArea;
    private javax.swing.JLabel wybiegLabel;
    private javax.swing.JPanel wybiegPanel;
    private javax.swing.JLabel zwierzeLabel;
    private javax.swing.JPanel zwierzePanel;
    // End of variables declaration//GEN-END:variables
}
