package com.murugan.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.parquet.schema.GroupType;
import org.apache.parquet.schema.PrimitiveType;
import org.apache.parquet.schema.Type;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class ParquetSourceTest {
    private static final Logger logger = LoggerFactory.getLogger(ParquetSourceTest.class);

    ObjectMapper mapper = new ObjectMapper();

    //@Test
    public void dummy() throws Exception {
        Map<String, String> address = Map.of("city","Sunnyvale", "state","ca");
        List<String> visited = List.of("India","Dubai","USA");
        Map<String, Object> result =
                Map.of("sanjeev", "mishra", "praveen", "kumar", "dob",
                        System.currentTimeMillis(),
                        "address", address,
                        "visited", visited);

        logger.info("{}", result);
        logger.info("{}", mapper.writeValueAsString(result));
    }

    @Test
    public void read() throws IOException {
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

        String filePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("data/one.parquet")).getPath();
        Result<ParquetData, IOException> result = ParquetSource.read(filePath);
        Assertions.assertTrue(result.isOK(), "ParquetData should exist");

        Map<String, Object> schema = new HashMap<>();
        result.result().schemaStream().forEach(s -> visit(s, schema));

        logger.info("{}", mapper.writeValueAsString(schema));

        Stream<String> names = result.result().schemaStream().map(Type::getName);
//        try {
//            logger.info("{}", mapper.readValue(result.result().groupStream().collect(Collectors.toList()).stream().findFirst(), Map.class));
//        } catch (IOException e) {
//        }
//        result.result().groupStream().collect(Collectors.toList()).stream().findFirst().ifPresent(e -> {
//            try {
//                logger.info("{}", mapper.writeValueAsString(e));
//            } catch (IOException ex) {
//
//            }
//        });

//        result.result().groupStream().forEach(g -> {
//            logger.info("{}", g);
//        });
    }

    void visit(Type type, Map<String,Object> node) {
        if (type.isPrimitive()) {
            PrimitiveType primitiveType = type.asPrimitiveType();

            Map<String, Object> child = new HashMap<>();

            child.put("type", primitiveType.getPrimitiveTypeName().name());
            child.put("javaType", primitiveType.getPrimitiveTypeName().javaType);
//            child.put("columnOrder", primitiveType.columnOrder().getColumnOrderName());
//            child.put("id", type.getId()!=null?type.getId().intValue():null);
//            child.put("origType", type.getOriginalType()!=null?type.getOriginalType().name():null);
            child.put("typeAnnotation", type.getLogicalTypeAnnotation()!=null?type.getLogicalTypeAnnotation().toOriginalType().name():null);
            child.put("typeAnnotationStr", String.valueOf(type.getLogicalTypeAnnotation()));

            child.put("repetition", type.getRepetition()!=null?type.getRepetition():null);
            node.put(type.getName(), child);
        }
        else {
            visit(type.asGroupType(), node);
        }
    }

    void visit(GroupType type, Map<String, Object> node) {
        Map<String, Object> child = new HashMap<>();
        type.getFields().forEach(f -> visit(f, child));
        node.put(type.getName(), child);
    }

    String print(Type s, String tabs) {
        if (s.isPrimitive())
        logger.info("{}{} primitive: {} original type: {}, logical annotation: {}, repetition: {}",
                tabs, s.getName(), s.getOriginalType(), s.isPrimitive(), s.getLogicalTypeAnnotation(), s.getRepetition());
        else print(s.asGroupType(), tabs);
        return tabs;
    }

    String print(GroupType groupType, String tabs) {
        if (groupType.getName().toLowerCase(Locale.ROOT).equals("list")) {
            //logger.info("group[list] {}", groupType.getName());
            print(groupType.getFields().get(0), tabs);
        }
        else {
            logger.info("{}group {}", tabs, groupType.getName());
            String newTabs = tabs + "\t";

            groupType.getFields().forEach(f -> print(f, newTabs));
        }
        return tabs;
    }
}

class SchemaNode {
    private String key;
    private Object value;

    @Override
    @JsonValue
    public String toString() {
        return key + " and " + value;
    }
}
