package com.compiler.model;

import java.io.*;

import com.compiler.view.MainFrame;

public class FileManager {

    private MainFrame viewMainFrame;

    public FileManager(MainFrame mainFrame) {
        viewMainFrame = mainFrame;
    }

    private String OBJECT_CODE_FILE_EXTENSION = ".il";
    
    public String readFile(File file) throws IOException {
        if (!isTxtFile(file)) {
            throw new IllegalArgumentException("Apenas arquivos .txt sï¿½o suportados.");
        }
        
        var content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    public void writeFile(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(content);
        }
    }
        
    public boolean isTxtFile(File file) {
        return file.isFile() && file.getName().endsWith(".txt");
    }

    public void createObjectCodeFile(String objectCode, File sourceCode) {
        String filePathAndName = removeFileExtension(sourceCode.getAbsolutePath());
        var objectCodeFile = new File(filePathAndName + OBJECT_CODE_FILE_EXTENSION);
        try {
            writeFile(objectCodeFile, objectCode);
        } catch (IOException e) {
            viewMainFrame.showError("Error while creating object code file");
        }
    }

    public String removeFileExtension(String filePathAndName) {
        if (!filePathAndName.contains(".")) return filePathAndName;

        var lastDotIndex = filePathAndName.lastIndexOf(".");
        return filePathAndName.substring(0, lastDotIndex);
    }
}
