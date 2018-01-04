/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.Component;
import java.awt.Image;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
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
        setCuteImageInBackground();
    }
    
    public void disableButtons() {
        this.biletyButton.setEnabled(false);
        this.pracownicyButton.setEnabled(false);
        this.wybiegiButton.setEnabled(false);
    }
    
    public void setCuteImageInBackground() {
        java.awt.GridBagConstraints gridBagConstraints;
        ImagePanel cute = new ImagePanel();
        cute.setMinimumSize(new java.awt.Dimension(500, 300));
        cute.setPreferredSize(new java.awt.Dimension(500, 300));
        cute.setLayout(new java.awt.GridBagLayout());
        JLabel CuteLabel = new javax.swing.JLabel();
        
        CuteLabel.setFont(new java.awt.Font("Consolas", 1, 48)); // NOI18N
        CuteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CuteLabel.setText("Zoo");
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 284;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        cute.add(CuteLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(cute, gridBagConstraints);
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
        pracownicyButton = new javax.swing.JButton();
        wybiegiButton = new javax.swing.JButton();
        biletyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zoo");
        setMinimumSize(new java.awt.Dimension(500, 600));
        setName("Zoo"); // NOI18N
        setPreferredSize(new java.awt.Dimension(500, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        buttonsPanel.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        pracownicyButton.setText("Pracownicy");
        pracownicyButton.setMaximumSize(new java.awt.Dimension(300, 100));
        pracownicyButton.setMinimumSize(new java.awt.Dimension(300, 50));
        pracownicyButton.setPreferredSize(new java.awt.Dimension(300, 50));
        pracownicyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pracownicyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonsPanel.add(pracownicyButton, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonsPanel.add(wybiegiButton, gridBagConstraints);

        biletyButton.setText("Bilety");
        biletyButton.setMaximumSize(new java.awt.Dimension(300, 100));
        biletyButton.setMinimumSize(new java.awt.Dimension(300, 50));
        biletyButton.setPreferredSize(new java.awt.Dimension(300, 50));
        biletyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                biletyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonsPanel.add(biletyButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(buttonsPanel, gridBagConstraints);

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
            System.out.println("Rozłączono z bazą danych");
        } catch (Exception ex) {
            // Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void wybiegiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wybiegiButtonActionPerformed
        WybiegiFrame w = new WybiegiFrame();
        w.setLocation(getShowPosition2(w));
        w.setVisible(true);
    }//GEN-LAST:event_wybiegiButtonActionPerformed

    private void biletyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_biletyButtonActionPerformed
        BiletyFrame bilet = new BiletyFrame();
        bilet.setLocation(getShowPosition2(bilet));
        bilet.setVisible(true);
    }//GEN-LAST:event_biletyButtonActionPerformed

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
    private javax.swing.JButton biletyButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton pracownicyButton;
    private javax.swing.JButton wybiegiButton;
    // End of variables declaration//GEN-END:variables
}
