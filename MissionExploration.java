import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionExploration {

    /**
     * Given a Galaxy object, prints the solar systems within that galaxy.
     * Uses exploreSolarSystems() and printSolarSystems() methods of the Galaxy object.
     *
     * @param galaxy a Galaxy object
     */
    public void printSolarSystems(Galaxy galaxy) {

        galaxy.printSolarSystems(galaxy.exploreSolarSystems());

    }

    /**
     * TODO: Parse the input XML file and return a list of Planet objects.
     *
     * @param filename the input XML file
     * @return a list of Planet objects
     */
    public Galaxy readXML(String filename) {
        List<Planet> planetList = new ArrayList<>();

        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Planet");
// nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    String id = eElement.getElementsByTagName("ID").item(0).getTextContent();
                    int technologyLevel = Integer.parseInt(eElement.getElementsByTagName("TechnologyLevel").item(0).getTextContent());

                    List<String> neighborList = new ArrayList<>();

                    NodeList neighbors = eElement.getElementsByTagName("Neighbors");

                    for (int j = 0; j < neighbors.getLength(); j++) {
                        Node neighborNode = neighbors.item(j);
                        if (neighborNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element neighborId = (Element) neighborNode;
                            NodeList neighborIds = neighborId.getElementsByTagName("PlanetID");

                            for (int k = 0; k < neighborIds.getLength(); k++) {

                                neighborList.add(neighborIds.item(k).getTextContent());
                            }
                        }
                    }

                    planetList.add(new Planet(id,technologyLevel,neighborList));
                }
            }


        } catch (
                Exception e) {
            e.printStackTrace();
        }

        return new Galaxy(planetList);

    }
}
