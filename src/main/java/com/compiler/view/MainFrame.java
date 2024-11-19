package com.compiler.view;

import com.compiler.model.Compiler;

import javax.swing.*;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainFrame extends javax.swing.JFrame {
    
    private File currentFile;
    
    public MainFrame() {
        initComponents();
        
        textArea.setBorder(new NumberedBorder());
        
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        terminalScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        terminalScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPanel, terminalScrollPanel);
        splitPane.setDividerLocation(400);

        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(fileStatusLabel, BorderLayout.SOUTH);
        configureShortcuts();
        
        toolBar.add(newFileBtn);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(openFileBtn);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(saveFileBtn);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(copyTextBtn);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(pasteTextBtn);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(cutTextBtn);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(compileCodeBtn);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(showTeamBtn);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolBar = new javax.swing.JToolBar();
        newFileBtn = new javax.swing.JButton();
        openFileBtn = new javax.swing.JButton();
        saveFileBtn = new javax.swing.JButton();
        copyTextBtn = new javax.swing.JButton();
        pasteTextBtn = new javax.swing.JButton();
        cutTextBtn = new javax.swing.JButton();
        compileCodeBtn = new javax.swing.JButton();
        showTeamBtn = new javax.swing.JButton();
        scrollPanel = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        terminalScrollPanel = new javax.swing.JScrollPane();
        terminalTextArea = new javax.swing.JTextArea();
        fileStatusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(910, 600));
        setPreferredSize(new java.awt.Dimension(910, 600));

        toolBar.setBorder(null);
        toolBar.setRollover(true);
        toolBar.setBorderPainted(false);
        toolBar.setMaximumSize(new java.awt.Dimension(1800, 70));
        toolBar.setMinimumSize(new java.awt.Dimension(900, 70));
        toolBar.setPreferredSize(new java.awt.Dimension(900, 70));

        newFileBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        newFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/new-file.png"))); // NOI18N
        newFileBtn.setText("novo [ctrl-n]");
        newFileBtn.setFocusable(false);
        newFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newFileBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        newFileBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        newFileBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        newFileBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileBtnActionPerformed(evt);
            }
        });
        toolBar.add(newFileBtn);

        openFileBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        openFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/open-file.png"))); // NOI18N
        openFileBtn.setText("abrir [ctrl-o]");
        openFileBtn.setFocusable(false);
        openFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openFileBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        openFileBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        openFileBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        openFileBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileBtnActionPerformed(evt);
            }
        });
        toolBar.add(openFileBtn);

        saveFileBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        saveFileBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/save-file.png"))); // NOI18N
        saveFileBtn.setText("salvar [ctrl-s]");
        saveFileBtn.setFocusable(false);
        saveFileBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveFileBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        saveFileBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        saveFileBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        saveFileBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileBtnActionPerformed(evt);
            }
        });
        toolBar.add(saveFileBtn);

        copyTextBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        copyTextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/copy.png"))); // NOI18N
        copyTextBtn.setText("copiar [ctrl-c]");
        copyTextBtn.setFocusable(false);
        copyTextBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        copyTextBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        copyTextBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        copyTextBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        copyTextBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        copyTextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyTextBtnActionPerformed(evt);
            }
        });
        toolBar.add(copyTextBtn);

        pasteTextBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        pasteTextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/paste.png"))); // NOI18N
        pasteTextBtn.setText("colar [ctrl-v]");
        pasteTextBtn.setFocusable(false);
        pasteTextBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pasteTextBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        pasteTextBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        pasteTextBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        pasteTextBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pasteTextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteTextBtnActionPerformed(evt);
            }
        });
        toolBar.add(pasteTextBtn);

        cutTextBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        cutTextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/cut.png"))); // NOI18N
        cutTextBtn.setText("recortar [ctrl-x]");
        cutTextBtn.setFocusable(false);
        cutTextBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cutTextBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        cutTextBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        cutTextBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        cutTextBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cutTextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutTextBtnActionPerformed(evt);
            }
        });
        toolBar.add(cutTextBtn);

        compileCodeBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        compileCodeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/play.png"))); // NOI18N
        compileCodeBtn.setText("compilar [F7]");
        compileCodeBtn.setFocusable(false);
        compileCodeBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        compileCodeBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        compileCodeBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        compileCodeBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        compileCodeBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        compileCodeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compileCodeBtnActionPerformed(evt);
            }
        });
        toolBar.add(compileCodeBtn);

        showTeamBtn.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        showTeamBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/compiler/ui/images/team.png"))); // NOI18N
        showTeamBtn.setText("equipe [F1]");
        showTeamBtn.setFocusable(false);
        showTeamBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showTeamBtn.setMaximumSize(new java.awt.Dimension(200, 70));
        showTeamBtn.setMinimumSize(new java.awt.Dimension(110, 70));
        showTeamBtn.setPreferredSize(new java.awt.Dimension(110, 70));
        showTeamBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showTeamBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTeamBtnActionPerformed(evt);
            }
        });
        toolBar.add(showTeamBtn);

        textArea.setColumns(20);
        textArea.setRows(5);
        scrollPanel.setViewportView(textArea);

        terminalTextArea.setEditable(false);
        terminalTextArea.setColumns(20);
        terminalTextArea.setRows(5);
        terminalScrollPanel.setViewportView(terminalTextArea);

        fileStatusLabel.setMaximumSize(new java.awt.Dimension(900, 25));
        fileStatusLabel.setMinimumSize(new java.awt.Dimension(900, 25));
        fileStatusLabel.setPreferredSize(new java.awt.Dimension(900, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(terminalScrollPanel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(fileStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE))
                    .addComponent(scrollPanel)
                    .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(terminalScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fileStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileBtnActionPerformed
        textArea.setText("");
        terminalTextArea.setText("");
        fileStatusLabel.setText("");
        currentFile = null;
    }//GEN-LAST:event_newFileBtnActionPerformed

    private void openFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileBtnActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        var returnedValue = fileChooser.showOpenDialog(this);
        
        if (returnedValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            setTextFromFile(selectedFile);
            return;
        } 

        System.out.println("Nenhum arquivo selecionado.");
    }//GEN-LAST:event_openFileBtnActionPerformed

    private void compileCodeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compileCodeBtnActionPerformed
        var compiler = new Compiler(textArea.getText());
        terminalTextArea.setText(compiler.getResult());
    }//GEN-LAST:event_compileCodeBtnActionPerformed

    private void saveFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileBtnActionPerformed
        if (currentFile != null) {
            saveFileContent();
            return;
        }

        var fileChooser = new JFileChooser();
        var returnValue = askSaveHow(fileChooser);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            getFileAndUpdateFileStatus(fileChooser);
        }
        terminalTextArea.setText("");
    }//GEN-LAST:event_saveFileBtnActionPerformed

    private void showTeamBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showTeamBtnActionPerformed
        terminalTextArea.setText("Pedro Virissimo Scarelli e Vitor da Silva");
    }//GEN-LAST:event_showTeamBtnActionPerformed

    private void copyTextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyTextBtnActionPerformed
       textArea.copy();
    }//GEN-LAST:event_copyTextBtnActionPerformed

    private void pasteTextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteTextBtnActionPerformed
        textArea.paste();
    }//GEN-LAST:event_pasteTextBtnActionPerformed

    private void cutTextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutTextBtnActionPerformed
        textArea.cut();
    }//GEN-LAST:event_cutTextBtnActionPerformed

    public static void main(String[] args) {
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compileCodeBtn;
    private javax.swing.JButton copyTextBtn;
    private javax.swing.JButton cutTextBtn;
    private javax.swing.JLabel fileStatusLabel;
    private javax.swing.JButton newFileBtn;
    private javax.swing.JButton openFileBtn;
    private javax.swing.JButton pasteTextBtn;
    private javax.swing.JButton saveFileBtn;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JButton showTeamBtn;
    private javax.swing.JScrollPane terminalScrollPanel;
    private javax.swing.JTextArea terminalTextArea;
    private javax.swing.JTextArea textArea;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables

    private void getFileAndUpdateFileStatus(JFileChooser fileChooser) {
        currentFile = fileChooser.getSelectedFile();

        if (currentFile.exists()) {
            replaceFileOrCancelSave();
        } 
    }

    private void replaceFileOrCancelSave() {
        var confirm = JOptionPane.showConfirmDialog(this,
        "O arquivo ja existe. Deseja substituir?", "Substituir Arquivo",
        JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.NO_OPTION) return;

        fileStatusLabel.setText(currentFile.getAbsolutePath());
        saveFileContent();
    }

    private void saveFileContent() {
        if (currentFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso.",
                                              "Salvar Arquivo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + e.getMessage(),
                                              "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public int askSaveHow(JFileChooser fileChooser) {
        fileChooser.setDialogTitle("Salvar Como");
        return fileChooser.showSaveDialog(this);
    }
    
    private void configureShortcuts() {
        configureButtonShortcut(newFileBtn, "NewFile", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        configureButtonShortcut(openFileBtn, "OpenFile", KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        configureButtonShortcut(saveFileBtn, "SaveFile", KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        configureButtonShortcut(copyTextBtn, "CopyText", KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        configureButtonShortcut(pasteTextBtn, "PasteText", KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        configureButtonShortcut(cutTextBtn, "CutText", KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK)); 
        configureButtonShortcut(compileCodeBtn, "CompileCode", KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
        configureButtonShortcut(showTeamBtn, "ShowTeam", KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    }

    private void configureButtonShortcut(javax.swing.JButton button, String actionName, KeyStroke keyStroke) {
        Action action;
        action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        };
        
        InputMap inputMap = button.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = button.getActionMap();
        
        inputMap.put(keyStroke, actionName);
        actionMap.put(actionName, action);
    }
    
    private void setTextFromFile(File selectedFile) {
        if(verifyIfFileIsTxtFile(selectedFile)) {
            textArea.setText(convertTxtFileToString(selectedFile));
            clearTerminal();
            changeCurrentFile(selectedFile);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um arquivo de texto.",
                                            "Arquivo Invalido", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearTerminal() {
        terminalTextArea.setText("");
    }
    
    private void changeCurrentFile(File selectedFile){
        currentFile = selectedFile;
        var filePath = selectedFile.getAbsolutePath();
        fileStatusLabel.setText(filePath);
    }
    
    private String convertTxtFileToString(File selectedFile) {
        var content = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo: " + e.getMessage(),
                                             "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return content.toString();
    }
        
    private boolean verifyIfFileIsTxtFile(File selectedFile) {
        return selectedFile.isFile() && selectedFile.getName().endsWith(".txt");
    }
}