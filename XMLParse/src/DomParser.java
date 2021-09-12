import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class DomParser {
  public static List<Employee> getParserResult() {

    try {
      // Get the DOM Builder Factory
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      // Get the DOM Builder
      DocumentBuilder builder = factory.newDocumentBuilder();
      // Load and Parse the XML document
      // document contains the complete XML as a Tree
      Document document = builder.parse(ClassLoader.getSystemResourceAsStream("Employee.xml"));
      // Iterating through the nodes and extracting the data
      NodeList nodeList = document.getDocumentElement().getChildNodes();
      List<Employee> empList = new ArrayList<Employee>();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node instanceof Element) {
          // We have encountered an <employee> tag
          Employee emp = new Employee();
          emp.id = node.getAttributes().getNamedItem("id").getNodeValue();
          NodeList childNodes = node.getChildNodes();
          for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            // Identifying the child tag of employee encountered
            if (cNode instanceof Element) {
              String content = cNode.getLastChild().getTextContent().trim();
              switch (cNode.getNodeName()) {
                case "firstName":
                  emp.firstName = content;
                  break;
                case "lastName":
                  emp.lastName = content;
                  break;
                case "location":
                  emp.location = content;
                  break;
              }
            }
          }
          empList.add(emp);
        }
      }
      // Print the Employee list
      for (Employee e : empList) {
        System.out.println(e);
      }
      return empList;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  public static void main(String[] args) {
  }

}
