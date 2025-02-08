package automation.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class SeleniumJavatoPlaywrightGenerator {

    private static final String API_KEY = "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";
    private static final String LLM_API_URL = "https://api.groq.com/openai/v1/chat/completions";


    public String convertSeleniumToPlaywright(String seleniumCode) throws IOException {
     //   OkHttpClient client = new OkHttpClient();
        if (seleniumCode == null || seleniumCode.isEmpty()) {
            return "No valid API details to Playwright code from selenium code.";
        }
        String userPrompt = "Generate the playwright typescript from the Selenium java code\n"
                + seleniumCode;
        try {
            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant that generates playwright code w.r.t Selenium java code. "
                    + "Your response must contain only playwright typescript code enclosed in a single code block using triple backticks (```playwright ... ```). "
                    + "- Do not include any additional text explanations. "
                    + "- Be a complete and executable Playwright Typescript class. "
                    + "- Use only standard and correct imports "
                    + "- Also include tests "
                 //   + "- Add package as automation.tests"
                    + "- Write comments on the code"
                //    + "- Print output here
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
            payload.put("max_tokens", 3500);
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
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("query:", "Convert the following Selenium Java code to Playwright TypeScript: " + seleniumCode);
//        requestBody.put("extension:", "ts");
//
//        RequestBody body = RequestBody.create(
//                requestBody.toString(),
//                MediaType.get("application/json")
//        );
//
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .addHeader("Authorization", "Bearer " + API_KEY)
//                .addHeader("Content-Type", "application/json")
//                .post(body)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("Unexpected code " + response);
//        }
//
//        JSONObject jsonResponse = new JSONObject(response.body().string());
//        return (String) jsonResponse.getJSONArray("results").get(0);
        //  }


}

