package com.example.demo.controller;

import com.example.demo.service.GitHubService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/github")
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @PostMapping("/push")
    public String pushFile(
            @RequestParam String filePath,
            @RequestParam String content,
            @RequestParam String commitMessage) {
        return gitHubService.pushFileToRepo(filePath, content, commitMessage);
    }
}