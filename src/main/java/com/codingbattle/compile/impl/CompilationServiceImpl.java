package com.codingbattle.compile.impl;

import com.codingbattle.compile.CompilationService;
import com.codingbattle.compile.DynamicCompiler;
import com.codingbattle.dto.TestResultDto;
import com.codingbattle.entity.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class CompilationServiceImpl implements CompilationService {

    private static final String TEMP_DIR = "src/main/temp/";
    private static final String EXTENSION_JAVA = ".java";

    @Autowired
    private DynamicCompiler dynamicCompiler;

    @Override
    public TestResultDto compile(String sourceCode, String gameName) throws Exception {
        String sourcePath = createFileFromSourceCode(sourceCode, gameName);
        return dynamicCompiler.doEvil(sourcePath, gameName);

    }

    private String createFileFromSourceCode(String sourceCode, String gameName) throws IOException {
        String sourcePath = TEMP_DIR + gameName + EXTENSION_JAVA;
        File file = new File(sourcePath);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write(sourceCode);
        writer.flush();
        writer.close();
        return sourcePath;
    }
}
