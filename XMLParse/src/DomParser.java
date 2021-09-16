import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class DomParser {
  public static List<Paper> getParserResult() {

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
      List<Paper> paperList = new ArrayList<Paper>();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node instanceof Element) {
          // We have encountered an <employee> tag
          Paper doc = new Paper();
          doc.id = node.getAttributes().getNamedItem("id").getNodeValue();
          NodeList childNodes = node.getChildNodes();
          for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            // Identifying the child tag of employee encountered
            if (cNode instanceof Element) {
              String content = cNode.getLastChild().getTextContent().trim();
              switch (cNode.getNodeName()) {
                case "author":
                  doc.author = content;
                  break;
                case "editor":
                  doc.editor = content;
                  break;
                case "title":
                  doc.title = content;
                  break;
                case "booktitle":
                  doc.booktitle = content;
                  break;
                case "pages":
                  doc.pages = content;
                  break;
                case "year":
                  doc.year = content;
                  break;
                case "address":
                  doc.address = content;
                  break;
                case "journal":
                  doc.journal = content;
                  break;
                case "volume":
                  doc.volume = content;
                  break;
                case "number":
                  doc.number = content;
                  break;
                case "month":
                  doc.month = content;
                  break;
                case "url":
                  doc.url = content;
                  break;
                case "ee":
                  doc.ee = content;
                  break;
                case "cdrom":
                  doc.cdrom = content;
                  break;
                case "cite":
                  doc.cite = content;
                  break;
                case "publisher":
                  doc.publisher = content;
                  break;
                case"note":
                  doc.note = content;
                  break;
                case "crossref" :
                  doc.crossref = content;
                  break;
                case "isbn":
                  doc.isbn = content;
                  break;
                case "series":
                  doc.series = content;
                  break;
                case "school":
                  doc.school = content;
                  break;
                case "chapter":
                  doc.chapter = content;
                  break;
              }
            }
          }
          paperList.add(doc);
        }
      }
      // Print the Employee list
      for (Paper e : paperList) {
        System.out.println(e);
      }
      return paperList;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  public static void main(String[] args) {
  }

}

