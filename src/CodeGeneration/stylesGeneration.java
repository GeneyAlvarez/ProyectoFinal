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
public class stylesGeneration {

    public static void Start(String dir,Project project){

        final ErrorHandler errorHandler = new ErrorHandler();

        String item="style";
        String subitem="item";

        ArrayList<String> value=new ArrayList<>();
        value.add("colorPrimary");
        value.add("colorPrimaryDark");
        //value.add("primaryLight");
        value.add("colorAccent");

        ArrayList<String> value2=new ArrayList<>();
        value2.add("ColorPrimary");
        value2.add("ColorPrimaryDark");
        //value2.add("PrimaryLight");
        value2.add("ColorAccent");

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement("resources");//root element
            document.appendChild(element);

            Element childElement = document.createElement(item);
            childElement.setAttribute("name", "AppTheme");
            childElement.setAttribute("parent","Theme.AppCompat.Light.NoActionBar");
            //childElement.appendChild(document.createTextNode(data.get(i)));
            element.appendChild(childElement);

            for(int i=0;i<3;i++){
                Element subchildElement = document.createElement(subitem);
                subchildElement.setAttribute("name", value.get(i));
                subchildElement.appendChild(document.createTextNode("@color/"+value2.get(i)));
                childElement.appendChild(subchildElement);
            }

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            Result output = new StreamResult(new File(dir+"styles.xml"));
            Source input = new DOMSource(document);
            transformer.transform(input, output);

        }catch(Exception e) {
            errorHandler.handleError(project, e);
        }


    }

}
