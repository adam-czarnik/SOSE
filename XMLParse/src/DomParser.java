import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.FileInputStream;
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
      Document document = builder.parse(ClassLoader.getSystemResourceAsStream("dblp-soc-papers.xml"));//change made to getsystemresourceasstream
      // Iterating through the nodes and extracting the data
      NodeList nodeList = document.getDocumentElement().getChildNodes();
      List<Paper> paperList = new ArrayList<Paper>();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node instanceof Element) {
          // We have encountered an <employee> tag
          Paper doc = new Paper();
          doc.authors = new ArrayList<>();
          doc.mdate = node.getAttributes().getNamedItem("mdate").getNodeValue();
          doc.key = node.getAttributes().getNamedItem("key").getNodeValue();
          NodeList childNodes = node.getChildNodes();
          for (int j = 0; j < childNodes.getLength(); j++) {
            Node cNode = childNodes.item(j);
            // Identifying the child tag of employee encountered
            if (cNode instanceof Element) {
              String content = cNode.getLastChild().getTextContent().trim();
              switch (cNode.getNodeName()) {
                case "author":
                  System.out.println(content);
                  doc.authors.add(content) ;
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
                case "note":
                  doc.note = content;
                  break;
                case "crossref":
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
                case "publnr":
                  doc.publnr = content;
                  break;
              }
            }
          }
          paperList.add(doc);
        }
      }
      // Print the Employee list
      /*for (Paper e : paperList) {
        System.out.println(e);
      }*/
      return paperList;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  /*public static void main(String[] args) {
    getParserResult();
  }*/

}


/*
SQL QUERIES

1. SELECT * FROM authors WHERE author0 == "name" OR author1 == "name" OR author2 == "name" OR author3 == "name" OR author4 == "name" OR author5 == "name" OR author6 == "name" OR author7 == "name" OR author8 == "name" OR author9 == "name";
2. SELECT * FROM papers WHERE title == "title"
3. SELECT * FROM papers WHERE title == "title" AND year == "year" AND issue == "issue"
4. SELECT * FROM papers WHERE conference == "conference" AND year == "year"

2. SELECT * FROM papers WHERE author == "author" AND year == "year"
3. SELECT * FROM papers WHERE COUNT(author == "author") > 10

 */

