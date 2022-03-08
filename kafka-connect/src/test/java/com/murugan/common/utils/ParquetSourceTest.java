package com.murugan.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class ParquetSourceTest {
    private static final Logger logger = LoggerFactory.getLogger(ParquetSourceTest.class);
    @Test
    public void read() {
        String filePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("data/one.parquet")).getPath();
        Result<ParquetData, IOException> result = ParquetSource.read(filePath);
        Assertions.assertTrue(result.isOK(), "ParquetData should exist");
        result.result().groupStream().forEach(g -> logger.info("{}", g));
        result.result().schemaStream().forEach(s -> logger.info("{}", s));
    }
}
