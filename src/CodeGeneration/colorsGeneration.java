package CodeGeneration;

import Utility.ErrorHandler;
import com.intellij.openapi.project.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Necho on 16/04/2016.
 */
public class colorsGeneration {

    public static void Start(String dir, Project project){

        final ErrorHandler errorHandler = new ErrorHandler();

        String elements="color";
        String name="\nname";

        ArrayList<String> value=new ArrayList<>();          ArrayList<String> data=new ArrayList<>();
        value.add("ColorPrimary");                          data.add("#009688");
        value.add("ColorPrimaryDark");                      data.add("#00796B");
        value.add("PrimaryLight");                          data.add("#B2DFDB");
        value.add("ColorAccent");                           data.add("#00BCD4");
        value.add("secondary_text");                        data.add("#727272");
        value.add("icons");                                 data.add("#FFFFFF");
        value.add("divider");                               data.add("#B6B6B6");
        value.add("primary_text");                          data.add("#212121");

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement("resources");//root element
            document.appendChild(element);

            for (int i = 0; i < value.size(); i++) {
                Element childElement = document.createElement(elements);
                childElement.setAttribute(name, value.get(i));
                childElement.appendChild(document.createTextNode(data.get(i)));
                element.appendChild(childElement);
            }
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            document.setXmlStandalone(true);

            Result output = new StreamResult(new File(dir+"colors.xml"));
            Source input = new DOMSource(document);


            transformer.transform(input, output);

        }catch(Exception e) {
            errorHandler.handleError(project, e);
        }
    }


}
