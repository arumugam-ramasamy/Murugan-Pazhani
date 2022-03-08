package com.murugan.common.utils;

import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.schema.Type;

import java.util.List;
import java.util.stream.Stream;

//public record ParquetData(List<SimpleGroup> data, List<Type> schema) {}

public class ParquetData {
    private final List<SimpleGroup> data;
    private final List<Type> schema;

    public ParquetData(List<SimpleGroup> data, List<Type> schema) {
        this.data = data;
        this.schema = schema;
    }

    public Stream<SimpleGroup> groupStream() {
        return data.stream();
    }

    public Stream<Type> schemaStream() {
        return schema.stream();
    }
}
