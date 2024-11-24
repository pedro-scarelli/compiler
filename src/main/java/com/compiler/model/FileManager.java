package com.compiler.model;

import java.io.*;

public class FileManager {

    public static String readFile(File file) throws IOException {
        if (!isTxtFile(file)) {
            throw new IllegalArgumentException("Apenas arquivos .txt são suportados.");
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
    
    public static void writeFile(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }
        
    public static boolean isTxtFile(File file) {
        return file.isFile() && file.getName().endsWith(".txt");
    }
}
