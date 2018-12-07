package com.codingbattle.compile;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TypeManager {

    private Map<String, Class> types;

    @PostConstruct
    private void init(){
        initMap();
    }

    private void initMap(){
        types = new HashMap<>();
        types.put("String", String.class);
        types.put("int", int.class);
        types.put("double", double.class);
        types.put("String[]", String[].class);
        types.put("int[]", int[].class);
        types.put("double[]", double[].class);
    }

    public Map<String, Class> getTypes() {
        return types;
    }
}
