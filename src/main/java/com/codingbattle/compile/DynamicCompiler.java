package com.codingbattle.compile;

import com.codingbattle.compile.parser.service.ParseService;
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

    private Path saveSource(String source, String gameName) throws IOException {
        Path sourcePath = Paths.get(TEMP_DIR, gameName + EXTENSION_JAVA);
        Files.write(sourcePath, source.getBytes(UTF_8));
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

    //TODO add parameters for method.invoke()
    private String runClass(Path javaClass, String gameName)
            throws ClassNotFoundException, NoSuchMethodException, MalformedURLException,
            IllegalAccessException, InstantiationException {
        URL classUrl = javaClass.getParent().toFile().toURI().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
        Class<?> clazz = Class.forName(gameName, true, classLoader);
        Method m = clazz.getDeclaredMethod("print", typeManager.getTypes().get("int"));//TODO input parameter
        String result;
        String input = "12";//TODO input parameter
        try {
            result = m.invoke(clazz.newInstance(), (Object) parseService.parse(input, int.class/*TODO input parameter*/)).toString();
        } catch (InvocationTargetException e) {
            result = e.getMessage();
        }
        return result;
    }

    public String doEvil(String sourcePath, String gameName) throws Exception {
        String source = readCode(sourcePath);
        Path javaFile = saveSource(source, gameName);
        String str = compileSource(javaFile, gameName);
        return parseResult(str, gameName, javaFile);
    }

    private String parseResult(String input, String gameName, Path javaFile) throws Exception {
        String result;
        if (input.contains(ERROR)) {
            result = input.substring(input.indexOf(ERROR));
            result = result.replaceAll("(location: [^\n]*)", "");
        } else {
            Path classFile = Paths.get(compileSource(javaFile, gameName));
            result = runClass(classFile, gameName);
            File file = new File(classFile.toString());
            String fileNameWithoutExtension = file.getName()
                    .substring(0, file.getName().indexOf("."));
            deleteFiles(fileNameWithoutExtension);
        }
        return result;
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
