package com.codingbattle.compile;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class ImportManager {

    private Map<String, StringBuilder> imports;

    @PostConstruct
    private void initImports(){
        imports = new HashMap<>();
        imports.put("1aca38c4-f7a4-4780-b841-6cfa89e2226e", initStream());
    }

    public Map<String, StringBuilder> getImports(){
        return imports;
    }

    private StringBuilder initStream(){
        StringBuilder sb = new StringBuilder();
        sb.append("import java.util.ArrayList;")
                .append("\n")
                .append("import java.util.List;")
                .append("\n")
                .append("import java.util.stream.Collectors;")
                .append("\n");
        return sb;
    }

    public void addImport(String taskId, StringBuilder imports){
        this.imports.put(taskId, imports);
    }
}
