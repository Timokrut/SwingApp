package org.example.io;

import org.example.model.Task;
import org.example.model.TaskManager;

import javax.xml.parsers.*; 
import javax.xml.transform.*; 
import javax.xml.transform.dom.*; 
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*; 
import java.io.*; 
import java.time.LocalDateTime; 
import java.util.List;

public class XMLStorage {
    private static final String FILE_NAME = "tasks.xml";

    public static void saveTasks(List<Task> tasks) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element root = doc.createElement("tasks");
            doc.appendChild(root);

            for (Task t : tasks) {
                Element taskElem = doc.createElement("task");

                appendText(doc, taskElem, "title", t.getTitle());
                appendText(doc, taskElem, "start", t.getStartDate().toString());
                appendText(doc, taskElem, "end", t.getEndDate().toString());
                appendText(doc, taskElem, "color", t.getColor());
                appendText(doc, taskElem, "desc", t.getDescription());

                root.appendChild(taskElem);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_NAME));
            transformer.transform(source, result);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadTasks(TaskManager manager) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;
    
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList taskNodes = doc.getElementsByTagName("task");
            for (int i = 0; i < taskNodes.getLength(); i++) {
                Element taskElem = (Element) taskNodes.item(i);

                String title = getTagValue("title", taskElem);
                String start = getTagValue("start", taskElem);
                String end = getTagValue("end", taskElem);
                String color = getTagValue("color", taskElem);
                String desc = getTagValue("desc", taskElem);

                Task task = new Task(
                    title, 
                    LocalDateTime.parse(start), 
                    LocalDateTime.parse(end),
                    color, 
                    desc != null ? desc : ""
                );

                manager.addTask(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    private static void appendText(Document doc, Element parent, String tag, String text) {
        Element el = doc.createElement(tag);
        el.appendChild(doc.createTextNode(text));
        parent.appendChild(el);
    }

    private static String getTagValue(String tag, Element elem) {
        NodeList nodes = elem.getElementsByTagName(tag);
        return nodes.getLength() > 0 ? nodes.item(0).getTextContent() : null;
    }

}

