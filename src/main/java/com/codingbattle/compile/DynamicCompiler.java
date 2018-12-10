package com.codingbattle.compile;

import com.codingbattle.compile.parser.service.ParseService;
import com.codingbattle.dto.TestResultDto;
import com.codingbattle.entity.Task;
import com.codingbattle.entity.Test;
import com.codingbattle.entity.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class DynamicCompiler {

    private static final String LINE_SEPARATOR = "line.separator";
    private static final String TEMP_DIR = "src/main/temp";
    private static final String EXTENSION_JAVA = ".java";
    private static final String EXTENSION_TXT = ".txt";
    private static final String EXTENSION_CLASS = ".class";
    private static final String ERROR = "error";

    @Autowired
    private TypeManager typeManager;

    @Autowired
    private ParseService parseService;

    @Autowired
    private ImportManager importManager;

    private String readCode(String sourcePath) throws FileNotFoundException {
        InputStream stream = new FileInputStream(sourcePath);
        String result;
        try {
            String separator = System.getProperty(LINE_SEPARATOR);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            result = reader.lines().collect(Collectors.joining(separator));
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

    private Path saveSource(String source, String gameName, Task task) throws IOException {
        Path sourcePath = Paths.get(TEMP_DIR, gameName + EXTENSION_JAVA);
        StringBuilder imports = importManager.getImports().get(task.getId());
        if(imports!=null){
            Files.write(sourcePath, importManager.getImports().get(task.getId()).toString().getBytes(UTF_8));
        }
        Files.write(sourcePath, source.getBytes(UTF_8), StandardOpenOption.APPEND);
        return sourcePath;
    }

    private String compileSource(Path javaFile, String gameName) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String fileName = javaFile.toFile().getPath();
        PrintStream out = new PrintStream(
                new FileOutputStream(
                        fileName.substring(0, fileName.indexOf(".")) + EXTENSION_TXT),
                true);
        System.setErr(out);
        int result = compiler.run(null, null, null,
                javaFile.toFile().getAbsolutePath());

        out.close();
        if (result == 0) {
            String programResult = javaFile.getParent()
                    .resolve(gameName + EXTENSION_CLASS).toString();
            System.setErr(System.err);
            return programResult;
        } else {
            return new String(Files.readAllBytes(
                    Paths.get(fileName.substring(0, fileName.indexOf(".")) + EXTENSION_TXT)));
        }
    }

    private List<TestResult> runClass(Path javaClass, String gameName, Task task)
            throws ClassNotFoundException, NoSuchMethodException, MalformedURLException {
        URL classUrl = javaClass.getParent().toFile().toURI().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
        Class<?> clazz = Class.forName(gameName, true, classLoader);
        Class inputParameterType = typeManager.getTypes().get(task.getInputType());
        Method shouldBeRanMethod = clazz.getDeclaredMethod(task.getMethodName(), inputParameterType);
        List<Test> testList = task.getTest();
        List<TestResult> testResults = new ArrayList<>();
        runTests(testList, shouldBeRanMethod, inputParameterType, clazz, testResults);

        checkResults(testList, testResults);
        return testResults;
    }

    private void runTests(List<Test> testList,
                          Method shouldBeRanMethod,
                          Class inputParameterType,
                          Class<?> clazz,
                          List<TestResult> testResults){
        for (int i = 0; i < testList.size(); i++) {
            String result;
            try {
                result = shouldBeRanMethod.invoke(clazz.newInstance(),
                        (Object) parseService.parse(testList.get(i).getInputParams(), inputParameterType)).toString();

                TestResult testResult = new TestResult(testList.get(i));
                testResult.setActualResults(result);
                testResults.add(testResult);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                result = e.getMessage();
                TestResult testResult = new TestResult(testList.get(i));
                testResult.setActualResults(result);
                testResults.add(testResult);
            }
        }
    }

    private void checkResults(List<Test> tests, List<TestResult> testResults) {
        for (int i = 0; i < tests.size(); i++) {
            String expectedResults = tests.get(i).getOutputParams();
            String actualResults = testResults.get(i).getActualResults();
            if (expectedResults.equals(actualResults)) {
                testResults.get(i).setPassed(true);
            }
        }
    }

    public TestResultDto doEvil(String sourcePath, String gameName, Task task) throws Exception {
        String source = readCode(sourcePath);
        Path javaFile = saveSource(source, gameName, task);
        String str = compileSource(javaFile, gameName);
        return parseResult(str, gameName, javaFile, task);
    }

    private TestResultDto parseResult(String input, String gameName, Path javaFile, Task task) throws Exception {
        TestResultDto dto = new TestResultDto();
        if (input.contains(ERROR)) {
            String result;
            result = input.substring(input.indexOf(ERROR));
            result = result.replaceAll("(location: [^\n]*)", "");
            dto.setStatus(result);

        } else {
            Path classFile = Paths.get(compileSource(javaFile, gameName));
            long before = System.currentTimeMillis();
            List<TestResult> testResults = runClass(classFile, gameName, task);
            long after = System.currentTimeMillis();
            File file = new File(classFile.toString());
            dto.setTestResultList(testResults);
            dto.setStatus("OK");
            dto.setExecutionTime(after-before);
            String fileNameWithoutExtension = file.getName()
                    .substring(0, file.getName().indexOf("."));
            deleteFiles(fileNameWithoutExtension);
        }
        return dto;
    }


    private void deleteFiles(String fileNameWithoutExtension) throws IOException {
        File javaFile = new File(TEMP_DIR + "/" + fileNameWithoutExtension +
                EXTENSION_JAVA);
        File classFile = new File(TEMP_DIR + "/" + fileNameWithoutExtension +
                EXTENSION_CLASS);
        File txtFile = new File(TEMP_DIR + "/" + fileNameWithoutExtension +
                EXTENSION_TXT);
        Files.delete(classFile.toPath());
        Files.delete(txtFile.toPath());
        Files.delete(javaFile.toPath());
    }
}
