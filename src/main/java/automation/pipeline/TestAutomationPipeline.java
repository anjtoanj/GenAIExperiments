package automation.pipeline;

import automation.parser.SwaggerParser;
import automation.generator.LLMTestGenerator;
import automation.generator.TestCodeGenerator;
import automation.util.FileUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAutomationPipeline {

    public static void main(String[] args) {
        String swaggerPath = "src/main/resources/pet.yaml";

        // 1. Parse the Swagger/OpenAPI spec
        SwaggerParser parser = new SwaggerParser();
        String apiDetails = parser.parseSwagger(swaggerPath);

        // 2. Generate test case outline using LLM
        LLMTestGenerator llmGen = new LLMTestGenerator();
        String llmRawOutput = llmGen.generateTestCases(apiDetails);

        // 3. Extract only the Java code from the LLM response
        TestCodeGenerator codeGen = new TestCodeGenerator();
        String finalTestCode = codeGen.extractJavaCode(llmRawOutput);

        // 4. Extract the class name from the generated Java code
        String className = extractClassName(finalTestCode);
        String outputFileName = (className != null && !className.isEmpty()) 
                                ? className + ".java" 
                                : "GeneratedAPITest.java";

        // 5. Write generated code to a Java file with a name matching the class name
        FileUtils.writeToFile(finalTestCode, "src/main/java/automation/tests/"+outputFileName);

        System.out.println("Generated test code written to " + outputFileName);
    }

    /**
     * Extracts the Java class name from the given code.
     * It searches for a pattern matching "public class <ClassName>".
     */
    private static String extractClassName(String javaCode) {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(javaCode);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
