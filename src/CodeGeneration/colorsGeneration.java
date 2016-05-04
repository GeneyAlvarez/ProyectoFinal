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
        String name="name";

        ArrayList<String> value=new ArrayList<>();
        value.add("ColorPrimary");
        value.add("ColorPrimaryDark");
        value.add("PrimaryLight");
        value.add("ColorAccent");

        ArrayList<String> data=new ArrayList<>();
        data.add("#3F51B5");
        data.add("#3F51B5");
        data.add("#C5CAE9");
        data.add("#03A9F4");

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement("resources");//root element
            document.appendChild(element);

            for (int i = 0; i < 4; i++) {
                Element childElement = document.createElement(elements);
                childElement.setAttribute(name, value.get(i));
                childElement.appendChild(document.createTextNode(data.get(i)));
                element.appendChild(childElement);
            }
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            document.setXmlStandalone(true);

            Result output = new StreamResult(new File(dir+"colors.xml"));
            Source input = new DOMSource(document);


            transformer.transform(input, output);

        }catch(Exception e) {
            errorHandler.handleError(project, e);
        }
    }


}
