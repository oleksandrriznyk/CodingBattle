package com.codingbattle.worker;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PlayersSync {

    Map<String, DeferredResult<ResponseEntity<?>>> unconnected;

    @PostConstruct
    private void init(){
        unconnected = new HashMap<>();
    }

    public void add(String key, DeferredResult<ResponseEntity<?>> value){
        unconnected.put(key, value);
    }

    public DeferredResult<ResponseEntity<?>> get(String key){
        return unconnected.get(key);
    }

    public void delete(String key){
        unconnected.remove(key);
    }
}
