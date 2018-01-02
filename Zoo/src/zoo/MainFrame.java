/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.Image;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static zoo.Zoo.getShowPosition1;
import static zoo.Zoo.getShowPosition2;

/**
 *
 * @author akamojo
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainJFrame
     */
    public MainFrame() {
        initComponents();
        setIconImage(Zoo.getIcon());
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

        ZooPanel = new javax.swing.JPanel();
        ZooLabel = new javax.swing.JLabel();
        PracownicyPanel = new javax.swing.JPanel();
        pracownicyButton = new javax.swing.JButton();
        wybiegiButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zoo");
        setMinimumSize(new java.awt.Dimension(700, 500));
        setName("Zoo"); // NOI18N
        setSize(new java.awt.Dimension(700, 500));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        ZooPanel.setMinimumSize(new java.awt.Dimension(100, 100));
        ZooPanel.setLayout(new java.awt.GridBagLayout());

        ZooLabel.setFont(new java.awt.Font("Consolas", 1, 48)); // NOI18N
        ZooLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ZooLabel.setText("Zoo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 284;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        ZooPanel.add(ZooLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(ZooPanel, gridBagConstraints);

        PracownicyPanel.setMinimumSize(new java.awt.Dimension(100, 100));
        PracownicyPanel.setLayout(new java.awt.GridBagLayout());

        pracownicyButton.setText("Pracownicy");
        pracownicyButton.setMaximumSize(new java.awt.Dimension(300, 100));
        pracownicyButton.setMinimumSize(new java.awt.Dimension(300, 50));
        pracownicyButton.setPreferredSize(new java.awt.Dimension(300, 50));
        pracownicyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pracownicyButtonActionPerformed(evt);
            }
        });
        PracownicyPanel.add(pracownicyButton, new java.awt.GridBagConstraints());

        wybiegiButton.setText("Wybiegi i zwierzęta");
        wybiegiButton.setMaximumSize(new java.awt.Dimension(300, 100));
        wybiegiButton.setMinimumSize(new java.awt.Dimension(300, 50));
        wybiegiButton.setPreferredSize(new java.awt.Dimension(300, 50));
        wybiegiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wybiegiButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PracownicyPanel.add(wybiegiButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(PracownicyPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pracownicyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pracownicyButtonActionPerformed
        PracownicyFrame prac = new PracownicyFrame();
        prac.setLocation(getShowPosition2(prac));
        prac.setVisible(true);
    }//GEN-LAST:event_pracownicyButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            DBSupport.getConn().close();
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Rozłączono z bazą danych");
    }//GEN-LAST:event_formWindowClosing

    private void wybiegiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wybiegiButtonActionPerformed
        WybiegiFrame w = new WybiegiFrame();
        w.setLocation(getShowPosition2(w));
        w.setVisible(true);
    }//GEN-LAST:event_wybiegiButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PracownicyPanel;
    private javax.swing.JLabel ZooLabel;
    private javax.swing.JPanel ZooPanel;
    private javax.swing.JButton pracownicyButton;
    private javax.swing.JButton wybiegiButton;
    // End of variables declaration//GEN-END:variables
}
