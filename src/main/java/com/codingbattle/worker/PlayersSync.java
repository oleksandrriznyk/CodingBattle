package com.codingbattle.worker;

import com.codingbattle.entity.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PlayersSync {

    Map<String, DeferredResult<ResponseEntity<?>>> unconnected;
    Map<String, DeferredResult<ResponseEntity<Session>>> sessionResult;

    @PostConstruct
    private void init(){
        unconnected = new HashMap<>();
        sessionResult = new HashMap<>();
    }

    public void addSession(String key, DeferredResult<ResponseEntity<?>> value){
        unconnected.put(key, value);
    }

    public DeferredResult<ResponseEntity<?>> getSession(String key){
        return unconnected.get(key);
    }

    public void deleteSession(String key){
        unconnected.remove(key);
    }

    public void addResult(String key, DeferredResult<ResponseEntity<Session>> value) {
        sessionResult.put(key, value);
    }

    public DeferredResult<ResponseEntity<Session>> getResult(String key) { return sessionResult.get(key);}

    public void deleteResult(String key){sessionResult.remove(key);}
}
