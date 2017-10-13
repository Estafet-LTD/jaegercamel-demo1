package com.estafet.camelboot;

import io.opentracing.propagation.TextMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectMapExtracterAdapter implements TextMap {
    private final Map<String, String> map;

    public ObjectMapExtracterAdapter(Map<String, Object> map) {
        this.map = new HashMap<>();
        for(Map.Entry entry : map.entrySet()) {
            this.map.put(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    public Iterator<Map.Entry<String, String>> iterator() {
        return (Iterator)this.map.entrySet().iterator();
    }

    public void put(String key, String value) {
        throw new UnsupportedOperationException("TextMapInjectAdapter should only be used with Tracer.extract()");
    }
}
