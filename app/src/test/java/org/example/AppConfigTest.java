package org.example;

import org.example.io.AppConfig; 
import org.junit.Test;

import java.io.File; 

import static org.junit.Assert.*;

public class AppConfigTest {
 
    @Test 
    public void testSaveAndLoadLastFilePath() {
        String path = "test_data.xml";

        new File("last_used_file.txt").delete();

        AppConfig.saveLastFilePath(path);
        String loaded = AppConfig.loadLastFilePath();

        assertEquals(path, loaded);
    }

    @Test 
    public void testLoadWhenFileDoesNotExist() {
        new File("last_used_file.txt").delete();
        String result = AppConfig.loadLastFilePath();
        assertNull(result);
    }
}
