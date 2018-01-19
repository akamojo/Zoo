/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import ca.mb.javajeff.anicursor.AniCursor;
import ca.mb.javajeff.anicursor.BadAniException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author akamojo
 */
public class Zoo {

    private static Image icon;
    private static String cursorName = "Elephant.ani";
    private static AniCursor aC;

    public Zoo() {
        URL url = Zoo.class.getResource("Elephant.ani");
        //cursorPath = url.getFile().replaceAll("%20", " ");
    }

    public static Image getIcon() {
        return icon;
    }

    public static void setIcon(Image icon) {
        Zoo.icon = icon;
    }

    public static void setIconAndCursor(javax.swing.JFrame j) {
        try {
            j.setIconImage(Zoo.getIcon());

            URL url = Zoo.class.getResource(cursorName);
            String cursorPath = url.getPath().replaceAll("%20", " ");
            AniCursor kursorek = new AniCursor(cursorPath, j);
            kursorek.start();

        } catch (BadAniException | IOException ex) {
            Logger.getLogger(Zoo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                URL url = Zoo.class.getResource("zoo.png");
                Toolkit kit = Toolkit.getDefaultToolkit();
                Zoo.icon = kit.createImage(url);

                MainFrame zoo = new MainFrame();
                zoo.setLocation(getShowPosition1(zoo));
                zoo.setVisible(true);

                try {
                    DBSupport.connect();
                } catch (SQLException ex) {
                    zoo.disableButtons();
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Nie udało się połączyć z bazą danych.", "Fatal error :c", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Zoo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Zoo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Zoo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Zoo.class.getName()).log(Level.SEVERE, null, ex);
                }
                SwingUtilities.updateComponentTreeUI(zoo);
            }
        });

    }

    public static Point getShowPosition1(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        return new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public static Point getShowPosition2(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        return new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public static Point getShowPosition2D(JDialog frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        return new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

}
