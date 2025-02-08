package automation.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SeleniumJavaParser {
    public String parseSeleniumJava(String javaFilePath) {
        String fileContent;
        try {
            // Read file content
            Path path = Paths.get(javaFilePath);
            fileContent = new String(Files.readAllBytes(path));
            System.out.println(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContent;
    }
}