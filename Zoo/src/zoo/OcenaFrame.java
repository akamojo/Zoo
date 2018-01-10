/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author akamojo
 */
public class OcenaFrame extends javax.swing.JFrame {

    private javax.swing.JFrame parent;
    private int numer;

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }
    
    /**
     * Creates new form OcenaFrame
     */
    public OcenaFrame() {
        initComponents();
    }
    
    public OcenaFrame(javax.swing.JFrame parent) {
        this.parent = parent;
        initComponents();
        setIconImage(Zoo.getIcon());
    }
    
    public void fill(String komentarz) {
        buttonPanel.setVisible(false);
        infoPanel.setVisible(false);
        komentarzTextArea.setText(komentarz);
        komentarzTextArea.setEditable(false);
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
        addButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        questionLabel = new javax.swing.JLabel();
        questionComboBox = new javax.swing.JComboBox<>();
        gwiazdkiLabel = new javax.swing.JLabel();
        numerLabel = new javax.swing.JLabel();
        numerTextField = new javax.swing.JTextField();
        gwiazdkiSpinner = new javax.swing.JSpinner();
        komentarzLabel = new javax.swing.JLabel();
        komentarzScrollPane = new javax.swing.JScrollPane();
        komentarzTextArea = new javax.swing.JTextArea();
        linkPanel = new javax.swing.JPanel();
        linkButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ocena");

        addButton.setText("Dodaj");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        buttonPanel.add(addButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);

        mainPanel.setLayout(new java.awt.GridBagLayout());

        infoPanel.setLayout(new java.awt.GridBagLayout());

        questionLabel.setText("Do czego chcesz dodać ocenę?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(questionLabel, gridBagConstraints);

        questionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zwierzę", "Wybieg" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(questionComboBox, gridBagConstraints);

        gwiazdkiLabel.setText("Liczba gwiazdek:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(gwiazdkiLabel, gridBagConstraints);

        numerLabel.setText("Numer wybiegu lub chip zwierzęcia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(numerLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(numerTextField, gridBagConstraints);

        gwiazdkiSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));
        gwiazdkiSpinner.setEditor(new javax.swing.JSpinner.NumberEditor(gwiazdkiSpinner, ""));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        infoPanel.add(gwiazdkiSpinner, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(infoPanel, gridBagConstraints);

        komentarzLabel.setText("Komentarz:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(komentarzLabel, gridBagConstraints);

        komentarzScrollPane.setMaximumSize(new java.awt.Dimension(37261, 32767));
        komentarzScrollPane.setPreferredSize(new java.awt.Dimension(250, 100));

        komentarzTextArea.setColumns(20);
        komentarzTextArea.setFont(new java.awt.Font("Consolas", 2, 14)); // NOI18N
        komentarzTextArea.setRows(5);
        komentarzTextArea.setMaximumSize(new java.awt.Dimension(1237878917, 2147483647));
        komentarzTextArea.setMinimumSize(new java.awt.Dimension(250, 20));
        komentarzTextArea.setName(""); // NOI18N
        komentarzTextArea.setPreferredSize(new java.awt.Dimension(250, 89));
        komentarzScrollPane.setViewportView(komentarzTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        mainPanel.add(komentarzScrollPane, gridBagConstraints);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        linkButton.setText("Wyświetl dostępne zwierzęta i wybiegi");
        linkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linkButtonMouseClicked(evt);
            }
        });
        linkPanel.add(linkButton);

        getContentPane().add(linkPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        Execute up = new Execute();
        if (questionComboBox.getSelectedIndex() == 0) {
            try {
                up.ExecutePreparedQuery("INSERT INTO OCENY(LICZBA_GWIAZDEK, KOMENTARZ, NUMER_BILETU, ZWIERZETA_CHIP) VALUES(?, ?, ?, ?)");
                 up.getStatement().setInt(1, Integer.parseInt(gwiazdkiSpinner.getValue().toString()));
                 up.getStatement().setString(2, komentarzTextArea.getText());
                 up.getStatement().setInt(3, this.numer);
                 up.getStatement().setInt(4, Integer.parseInt(numerTextField.getText()));
                up.firePreparedUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                up.ExecutePreparedQuery("INSERT INTO OCENY(LICZBA_GWIAZDEK, KOMENTARZ, NUMER_BILETU, WYBIEGI_NR) VALUES(?, ?, ?, ?)");
                 up.getStatement().setInt(1, Integer.parseInt(gwiazdkiSpinner.getValue().toString()));
                 up.getStatement().setString(2, komentarzTextArea.getText());
                 up.getStatement().setInt(3, this.numer);
                 up.getStatement().setInt(4, Integer.parseInt(numerTextField.getText()));
                up.firePreparedUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        ((OcenyFrame) parent).refresh();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_addButtonMouseClicked

    private void linkButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_linkButtonMouseClicked
        WybiegiFrame w = new WybiegiFrame();
        w.setLocation(Zoo.getShowPosition2(w));
        w.setVisible(true);
    }//GEN-LAST:event_linkButtonMouseClicked

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
            java.util.logging.Logger.getLogger(OcenaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OcenaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OcenaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OcenaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OcenaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel gwiazdkiLabel;
    private javax.swing.JSpinner gwiazdkiSpinner;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel komentarzLabel;
    private javax.swing.JScrollPane komentarzScrollPane;
    private javax.swing.JTextArea komentarzTextArea;
    private javax.swing.JButton linkButton;
    private javax.swing.JPanel linkPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel numerLabel;
    private javax.swing.JTextField numerTextField;
    private javax.swing.JComboBox<String> questionComboBox;
    private javax.swing.JLabel questionLabel;
    // End of variables declaration//GEN-END:variables
}
