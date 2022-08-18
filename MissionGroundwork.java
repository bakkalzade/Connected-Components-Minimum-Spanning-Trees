import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class MissionGroundwork {

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project project: projectList)
            project.printSchedule(project.getEarliestSchedule());

    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        // TODO: YOUR CODE HERE
        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Project");
// nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    String projectName = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    List<Task> taskList = new ArrayList<>();

                    NodeList tasks = eElement.getElementsByTagName("Task");

                    for (int i = 0; i < tasks.getLength(); i++) {
                        Node taskNode = tasks.item(i);
                        if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element taskElement = (Element) taskNode;

                            int taskId = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
                            int duration = Integer.parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());
                            String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();

                            NodeList dependenciesNodes = taskElement.getElementsByTagName("Dependencies");

                            List<Integer> dependencies = new ArrayList<>();
                            for (int j = 0; j < dependenciesNodes.getLength(); j++) {
                                Node dependecyNode = dependenciesNodes.item(j);
                                if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element dependencyId = (Element) dependecyNode;
                                    NodeList dependencyIds = dependencyId.getElementsByTagName("DependsOnTaskID");

                                    for (int k = 0; k < dependencyIds.getLength(); k++) {

                                        dependencies.add(Integer.valueOf(dependencyIds.item(k).getTextContent()));
                                    }
                                }
                            }
                            Task task = new Task(taskId,description,duration, dependencies);

                            taskList.add(task);


                        }
                    }

                    projectList.add(new Project(projectName,taskList));
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }

        return projectList;
    }


}
