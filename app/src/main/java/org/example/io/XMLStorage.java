package org.example.io;

import org.example.model.Task;
import org.example.model.TaskManager;

import javax.xml.parsers.*; 
import javax.xml.transform.*; 
import javax.xml.transform.dom.*; 
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*; 
import java.io.*; 
import java.time.LocalDate; 
import java.util.List;

public class XMLStorage {
    private static final String FILE_NAME = "tasks.xml";

    public static void saveTasks(List<Task> tasks) {
        // TODO: save to XML
    }

    public static void loadTasks(TaskManager manager) {
        // TODO: load fom XML
    }
}
