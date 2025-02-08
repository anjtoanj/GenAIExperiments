package automation.util;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static void writeToFile(String content, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//private static final String API_KEY = "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"; // Store
