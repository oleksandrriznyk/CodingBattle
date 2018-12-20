package com.codingbattle.compile.impl;

import com.codingbattle.compile.CompilationService;
import com.codingbattle.compile.DynamicCompiler;
import com.codingbattle.dto.TestResultDto;
import com.codingbattle.entity.Task;
import com.codingbattle.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Service
public class CompilationServiceImpl implements CompilationService {

    private static final String TEMP_DIR = "src/main/temp/";
    private static final String EXTENSION_JAVA = ".java";

    @Autowired
    private DynamicCompiler dynamicCompiler;

    @Autowired
    private SecurityService securityService;

    @Override
    public TestResultDto compile(String sourceCode, String gameName, Task task, String sessionId) throws Exception {
        createFileFromSourceCode(gameName);
        return dynamicCompiler.doEvil(sourceCode, gameName, task, sessionId);

    }

    private void createFileFromSourceCode(String gameName) throws IOException {
        String currentUserLogin = securityService.getCurrentUser().getLogin();
        String sourcePath = TEMP_DIR + gameName+currentUserLogin + EXTENSION_JAVA;
        File file = new File(sourcePath);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.flush();
        writer.close();
    }
}
