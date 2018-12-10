package com.codingbattle.compile;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        types.put("List<String>", getArrayListOfStringClass());
    }

    public static Class getArrayListOfStringClass(){
        List<String> strings = new ArrayList<>();
        return strings.getClass();
    }

    public Map<String, Class> getTypes() {
        return types;
    }
}
