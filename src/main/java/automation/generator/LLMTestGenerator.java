package automation.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class LLMTestGenerator {

    private static final String LLM_API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String API_KEY = "<Enter the OPENAPI/ GROQ KEY>";

    public String generateTestCases(String apiDetails) {
        if (apiDetails == null || apiDetails.isEmpty()) {
            return "No valid API details to generate test cases.";
        }
        String userPrompt = "Generate REST API test cases using Rest Assured (Java, TestNG) for the following API specification:\n"
                            + apiDetails;
        try {
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant that generates Java code for API tests. "
            		+ "Your response must contain only Java code enclosed in a single code block using triple backticks (```java ... ```). "
            		+ "- Do not include any additional text explanations. "
                    + "- Be a complete and executable Java class. "
                    + "- Use only standard and correct imports (e.g., org.testng.Assert, io.restassured.RestAssured). "
            		+ "- Also do not include any additional tests other than positive test"
            		+ "- Add package as automation.tests"
            		+ "- Write comments on the code"
            		+ "- Print output of the API response"
            		+ "- Use BeforeMethod of Testng with hardcoded baseURI");
   
            messages.add(systemMessage);
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", userPrompt);
            messages.add(userMessage);
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", "deepseek-r1-distill-llama-70b");
            payload.put("messages", messages);
            payload.put("temperature", 0.2);
            payload.put("max_tokens", 1500);
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(payload);
            return callLLMApi(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error building JSON payload: " + e.getMessage();
        }
    }

    private String callLLMApi(String requestBody) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(LLM_API_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + API_KEY);
            request.setEntity(new StringEntity(requestBody));
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling LLM API: " + e.getMessage();
        }
    }
}
