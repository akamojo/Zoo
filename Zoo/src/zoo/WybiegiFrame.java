/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoo;

import java.awt.event.KeyEvent;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static zoo.Zoo.getShowPosition2;

/**
 *
 * @author akamojo
 */
public class WybiegiFrame extends javax.swing.JFrame {

    private String[] columnsWybiegi = {"Numer wybiegu", "Typ wybiegu", "Powierzchnia (m2)"};
    private String[] columnsZwierzeta = {"Gatunek", "Plec", "Chip"};

    private String[] columnsRaporty = {"Numer", "Czas wystawienia", "Pracownik", "Uwagi"};
    private String[] columnsOceny = {"Numer", "Czas wystawienia", "Liczba gwiazdek", "Komentarz"};

    public WybiegiFrame() {
        initComponents();
        // setIconImage(Zoo.getIcon());
        Zoo.setIconAndCursor(this);
        raportyOcenyPanel.setVisible(false);

        this.refreshAll();
        wybiegiTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = wybiegiTable.getSelectedRow();
                if (row != -1) {
                    String numerWybiegu = wybiegiTable.getValueAt(row, 0).toString();
                    refreshZwierzeta(numerWybiegu);
                    refreshRaportyAndOceny(numerWybiegu, "w");
                }
            }
        });

        zwierzetaTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = zwierzetaTable.getSelectedRow();
                if (row != -1) {
                    String chip = zwierzetaTable.getValueAt(row, 2).toString();
                    refreshRaportyAndOceny(chip, "z");
                }
            }
        });

    }

    public void addRemoveAbility(boolean b) {
        addWybiegButton.setEnabled(b);
        addZwierzeButton.setEnabled(b);
        removeWybiegButton.setEnabled(b);
        removeZwierzeButton.setEnabled(b);
    }

    public void refreshRaportyAndOceny(String id, String choice) {
        if (raportyOcenyPanel.isVisible()) {
            int intId = new Integer(id);

            String rQuery = "Select numer, czas_wystawienia, nazwisko, uwagi from raporty "
                    + "join pracownicy on pracownicy_id = id where ";
            String oQuery = "Select numer_oceny, czas_wystawienia, liczba_gwiazdek, komentarz from oceny where ";
            if (choice.equals("w")) {
                rQuery += "wybiegi_nr = " + id;
                oQuery += "wybiegi_nr = " + id;
            } else if (choice.equals("z")) {
                rQuery += "zwierzeta_chip = " + id;
                oQuery += "zwierzeta_chip = " + id;
            }

            CacheSqlTableModel model = new CacheSqlTableModel(rQuery, columnsRaporty, "ORDER BY czas_wystawienia");
            raportyTable.setModel(model);
            model = new CacheSqlTableModel(oQuery, columnsOceny, "ORDER BY czas_wystawienia");
            ocenyTable.setModel(model);

        }
    }

    public void refreshAll() {
        //((CacheSqlTableModel) pracownicyTable.getModel()).fireTableDataChanged();
        CacheSqlTableModel model = new CacheSqlTableModel("select nr, typy_wybiegu_nazwa, powierzchnia from wybiegi", columnsWybiegi, "ORDER BY NR");
        wybiegiTable.setModel(model);

        model = new CacheSqlTableModel("select gatunki_nazwa, plec, chip from zwierzeta", columnsZwierzeta, "ORDER BY CHIP");
        zwierzetaTable.setModel(model);

    }

    public void refreshZwierzeta(String numerWybiegu) {
        CacheSqlTableModel model = new CacheSqlTableModel("select gatunki_nazwa, plec, chip from zwierzeta"
                + " where wybiegi_nr = " + numerWybiegu, columnsZwierzeta, "ORDER BY CHIP");
        zwierzetaTable.setModel(model);
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
        showAllButton = new javax.swing.JButton();
        addWybiegButton = new javax.swing.JButton();
        removeWybiegButton = new javax.swing.JButton();
        addZwierzeButton = new javax.swing.JButton();
        removeZwierzeButton = new javax.swing.JButton();
        searchWybiegTextField = new javax.swing.JTextField();
        searchZwierzTextField = new javax.swing.JTextField();
        tablePanel = new javax.swing.JPanel();
        tableScrollPaneWybiegi = new javax.swing.JScrollPane();
        wybiegiTable = new javax.swing.JTable();
        tableScrollPaneZwierzeta = new javax.swing.JScrollPane();
        zwierzetaTable = new javax.swing.JTable();
        moreButtonsPanel = new javax.swing.JPanel();
        showGatunkiButton = new javax.swing.JButton();
        showRaportyOcenyButton = new javax.swing.JButton();
        raportyOcenyPanel = new javax.swing.JPanel();
        raportyOcenyPane = new javax.swing.JTabbedPane();
        raportyScrollPane = new javax.swing.JScrollPane();
        raportyTable = new javax.swing.JTable();
        ocenyScrollPane = new javax.swing.JScrollPane();
        ocenyTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Zoo Wybiegi i zwierzęta");
        setMinimumSize(new java.awt.Dimension(250, 250));
        setPreferredSize(new java.awt.Dimension(914, 900));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        showAllButton.setText("Pokaż wszystkie");
        showAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(showAllButton, gridBagConstraints);

        addWybiegButton.setText("Dodaj wybieg");
        addWybiegButton.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        addWybiegButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addWybiegButtonMouseClicked(evt);
            }
        });
        addWybiegButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWybiegButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(addWybiegButton, gridBagConstraints);

        removeWybiegButton.setText("Usuń wybieg");
        removeWybiegButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeWybiegButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(removeWybiegButton, gridBagConstraints);

        addZwierzeButton.setText("Dodaj zwierzę");
        addZwierzeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addZwierzeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(addZwierzeButton, gridBagConstraints);

        removeZwierzeButton.setText("Usuń zwierzę");
        removeZwierzeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeZwierzeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(removeZwierzeButton, gridBagConstraints);

        searchWybiegTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        searchWybiegTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchWybiegTextFieldKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(searchWybiegTextField, gridBagConstraints);

        searchZwierzTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        searchZwierzTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchZwierzTextFieldKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonPanel.add(searchZwierzTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(buttonPanel, gridBagConstraints);

        tablePanel.setLayout(new java.awt.GridBagLayout());

        wybiegiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        wybiegiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wybiegiTableMouseClicked(evt);
            }
        });
        tableScrollPaneWybiegi.setViewportView(wybiegiTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        tablePanel.add(tableScrollPaneWybiegi, gridBagConstraints);

        tableScrollPaneZwierzeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableScrollPaneZwierzetaMouseClicked(evt);
            }
        });

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
        tableScrollPaneZwierzeta.setViewportView(zwierzetaTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        tablePanel.add(tableScrollPaneZwierzeta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.weighty = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(tablePanel, gridBagConstraints);

        moreButtonsPanel.setLayout(new java.awt.GridBagLayout());

        showGatunkiButton.setText("Gatunki zwierząt");
        showGatunkiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showGatunkiButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        moreButtonsPanel.add(showGatunkiButton, gridBagConstraints);

        showRaportyOcenyButton.setText("Pokaż/ukryj raporty i oceny");
        showRaportyOcenyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showRaportyOcenyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        moreButtonsPanel.add(showRaportyOcenyButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(moreButtonsPanel, gridBagConstraints);

        raportyOcenyPanel.setLayout(new java.awt.GridBagLayout());

        raportyOcenyPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        raportyOcenyPane.setMinimumSize(new java.awt.Dimension(140, 120));

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

        raportyOcenyPane.addTab("Raporty", raportyScrollPane);

        ocenyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ocenyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ocenyTableMouseClicked(evt);
            }
        });
        ocenyScrollPane.setViewportView(ocenyTable);

        raportyOcenyPane.addTab("Oceny", ocenyScrollPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        raportyOcenyPanel.add(raportyOcenyPane, gridBagConstraints);
        raportyOcenyPane.getAccessibleContext().setAccessibleName("Raporty i oceny");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        getContentPane().add(raportyOcenyPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void wybiegiTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wybiegiTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = wybiegiTable.getSelectedRow();
            if (row != -1) {
                try {
                    WybiegFrame w = new WybiegFrame(this, new Integer(wybiegiTable.getValueAt(row, 0).toString()));
                    this.addRemoveAbility(false);
                    w.setLocation(getShowPosition2(w));
                    w.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_wybiegiTableMouseClicked

    private void showAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllButtonActionPerformed
        this.refreshAll();
    }//GEN-LAST:event_showAllButtonActionPerformed

    private void zwierzetaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zwierzetaTableMouseClicked
        if (evt.getClickCount() == 2) {
            int row = zwierzetaTable.getSelectedRow();
            if (row != -1) {
                try {
                    ZwierzeFrame z = new ZwierzeFrame(this, new Integer(zwierzetaTable.getValueAt(row, 2).toString()));
                    this.addRemoveAbility(false);
                    z.setLocation(getShowPosition2(z));
                    z.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_zwierzetaTableMouseClicked

    private void addWybiegButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWybiegButtonActionPerformed
        try {
            AddWybiegFrame w = new AddWybiegFrame(this);
            this.addRemoveAbility(false);
            w.setLocation(getShowPosition2(w));
            w.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addWybiegButtonActionPerformed

    private void removeWybiegButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeWybiegButtonActionPerformed
        try {
            Execute exec = new Execute();
            exec.ExecutePreparedQuery("DELETE FROM WYBIEGI WHERE NR = ?");
            int row = wybiegiTable.getSelectedRow();
            if (row != -1) {
                exec.getStatement().setInt(1, new Integer(wybiegiTable.getValueAt(row, 0).toString()));
                int result = exec.firePreparedUpdate_getCount();
                if (result > 0) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Wybieg usunięty!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    this.refreshAll();
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removeWybiegButtonActionPerformed

    private void addZwierzeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addZwierzeButtonActionPerformed
        try {
            ZwierzeFrame z = new ZwierzeFrame(this);
            this.addRemoveAbility(false);
            z.setLocation(getShowPosition2(z));
            z.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addZwierzeButtonActionPerformed

    private void removeZwierzeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeZwierzeButtonActionPerformed
        // co z zwierzętami?
        // taki sam problem jak pracownik - zwierzę zostaje w raporcie
        // i w ocenie klienta! 
        int row = zwierzetaTable.getSelectedRow();
        if (row != -1) {
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            Execute exec = new Execute();
            String chip = zwierzetaTable.getValueAt(row, 2).toString();
            int result = exec.ExecuteUpdate_getChanges("UPDATE ZWIERZETA SET DATA_OPUSZCZENIA_ZOO = DATE '" + date.toString() + "' WHERE CHIP = " + chip);
            if (result > 0) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Zwierzę zostało usunięte z ZOO; nadal istnieje w raportach i ocenach.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_removeZwierzeButtonActionPerformed

    private void addWybiegButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addWybiegButtonMouseClicked
        // nie chcę tego i nie umiem usunąć :<
    }//GEN-LAST:event_addWybiegButtonMouseClicked

    private void tableScrollPaneZwierzetaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableScrollPaneZwierzetaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableScrollPaneZwierzetaMouseClicked

    private void showGatunkiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showGatunkiButtonActionPerformed
        try {
            GatunekFrame z = new GatunekFrame();
            z.setShowZwierzetaMode();
            z.setLocation(getShowPosition2(z));
            z.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_showGatunkiButtonActionPerformed

    private void raportyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_raportyTableMouseClicked
        if (evt.getClickCount() == 2) {
            int selectionIndex = raportyTable.getSelectionModel().getMinSelectionIndex();
            if (selectionIndex != -1) {

                CacheSqlTableModel tableModel = (CacheSqlTableModel) raportyTable.getModel();
                int selectedId = tableModel.getSelectedId(raportyTable.getSelectedRow());

                RaportFrame raport = new RaportFrame();
                raport.setLocation(Zoo.getShowPosition2(raport));

                raport.fill(selectedId);
                raport.setVisible(true);
                raport.simpleMode();
            }
        }
    }//GEN-LAST:event_raportyTableMouseClicked

    private void ocenyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ocenyTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ocenyTableMouseClicked

    private void showRaportyOcenyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showRaportyOcenyButtonActionPerformed
        if (raportyOcenyPanel.isVisible()) {
            raportyOcenyPanel.setVisible(false);
        } else {
            raportyOcenyPanel.setVisible(true);
        }
    }//GEN-LAST:event_showRaportyOcenyButtonActionPerformed

    private void searchWybiegTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchWybiegTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (searchWybiegTextField.getText().isEmpty()) {
                refreshAll();
            } else {
                try {
                    String[] columns = new String[]{"Numer wybiegu", "Typ wybiegu", "Powierzchnia (m2)"};

                    CallableStatement cstmt = DBSupport.getConn().prepareCall("{? = call GET_SEARCH_QUERY(PATTERN => ?, IN_TABLE_NAME => 'WYBIEGI')}");
                    cstmt.registerOutParameter(1, Types.VARCHAR);
                    cstmt.setString(2, searchWybiegTextField.getText().replaceAll("'", "''"));
                    cstmt.execute();
                    String wynik = cstmt.getString(1).replace("*", "nr, typy_wybiegu_nazwa, powierzchnia");

                    CacheSqlTableModel model = new CacheSqlTableModel(wynik, columns, "ORDER BY NR");
                    wybiegiTable.setModel(model);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_searchWybiegTextFieldKeyPressed

    private void searchZwierzTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchZwierzTextFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (searchZwierzTextField.getText().isEmpty()) {
                refreshAll();
            } else {
                try {
                    String[] columns = new String[]{"Gatunek", "Plec", "Chip"};

                    CallableStatement cstmt = DBSupport.getConn().prepareCall("{? = call GET_SEARCH_QUERY(PATTERN => ?, IN_TABLE_NAME => 'ZWIERZETA')}");
                    cstmt.registerOutParameter(1, Types.VARCHAR);
                    cstmt.setString(2, searchZwierzTextField.getText().replaceAll("'", "''"));
                    cstmt.execute();
                    String wynik = cstmt.getString(1).replace("*", "gatunki_nazwa, plec, chip");

                    CacheSqlTableModel model = new CacheSqlTableModel(wynik, columns, "ORDER BY CHIP");
                    zwierzetaTable.setModel(model);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex, "Smutax Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_searchZwierzTextFieldKeyPressed

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
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PracownicyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PracownicyFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addWybiegButton;
    private javax.swing.JButton addZwierzeButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel moreButtonsPanel;
    private javax.swing.JScrollPane ocenyScrollPane;
    private javax.swing.JTable ocenyTable;
    private javax.swing.JTabbedPane raportyOcenyPane;
    private javax.swing.JPanel raportyOcenyPanel;
    private javax.swing.JScrollPane raportyScrollPane;
    private javax.swing.JTable raportyTable;
    private javax.swing.JButton removeWybiegButton;
    private javax.swing.JButton removeZwierzeButton;
    private javax.swing.JTextField searchWybiegTextField;
    private javax.swing.JTextField searchZwierzTextField;
    private javax.swing.JButton showAllButton;
    private javax.swing.JButton showGatunkiButton;
    private javax.swing.JButton showRaportyOcenyButton;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JScrollPane tableScrollPaneWybiegi;
    private javax.swing.JScrollPane tableScrollPaneZwierzeta;
    private javax.swing.JTable wybiegiTable;
    private javax.swing.JTable zwierzetaTable;
    // End of variables declaration//GEN-END:variables

}
