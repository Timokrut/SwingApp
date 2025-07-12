package org.example.io;

import java.io.*;

public class AppConfig {
    private static final String CONFIG_FILE = "last_used_file.txt";

    public static void saveLastFilePath(String path) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            writer.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadLastFilePath() {
        File file = new File(CONFIG_FILE);
        try {
            if (!file.exists()) {
                file.createNewFile();
                return null;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String path = reader.readLine();
                return (path != null && !path.isBlank()) ? path : null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
