package com.example.demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;

@Service
public class GitHubService {

    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String OWNER = "anvitpawar"; // Change this
    private static final String REPO = "git-spring-checkin"; // Change this
    private static final String BRANCH = "main"; // Change if using a different branch
    private static final String TOKEN = "";
    private final RestTemplate restTemplate = new RestTemplate();

    public String pushFileToRepo(String filePath, String fileContent, String commitMessage) {
        String url = GITHUB_API_URL + "/repos/" + OWNER + "/" + REPO + "/contents/" + filePath;

        // Encode file content in Base64 (required by GitHub API)
        String encodedContent = Base64.getEncoder().encodeToString(fileContent.getBytes());

        // Check if the file already exists to get the 'sha' (GitHub requires it for updates)
        String sha = getFileSha(filePath);

        // Prepare request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("message", commitMessage);
        requestBody.put("content", encodedContent);
        if (sha != null) {
            requestBody.put("sha", sha); // Required if updating an existing file
        }

        // Create HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        return response.getBody();
    }

    private String getFileSha(String filePath) {
        String url = GITHUB_API_URL + "/repos/" + OWNER + "/" + REPO + "/contents/" + filePath;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            return (String) response.getBody().get("sha");
        } catch (Exception e) {
            return null; // File doesn't exist
        }
    }
}