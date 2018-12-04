package com.codingbattle.compile.impl;

import com.codingbattle.compile.CompilationService;
import com.codingbattle.compile.DynamicCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Service
public class CompilationServiceImpl implements CompilationService {

    @Autowired
    private DynamicCompiler dynamicCompiler;



    @Override
    public String compile(String sourceCode) throws Exception{
        String sourcePath = createFileFromSourceCode(sourceCode);
        return dynamicCompiler.doEvil(sourcePath);

    }

    private String createFileFromSourceCode(String sourceCode) throws IOException {
        String sourcePath = "src/main/temp/Test.java";
        File file = new File(sourcePath);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write(sourceCode);
        writer.flush();
        writer.close();
        return sourcePath;
    }
}
