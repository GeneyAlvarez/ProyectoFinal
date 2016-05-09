package CodeGeneration;

import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Utility.ErrorHandler;
import Utility.Resources;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Created by Necho on 16/04/2016.
 */
public class mainGeneration {

    public static void Start(String dirRes,String dirSrc, Project project,String pack,String Main,String Package){
        final ErrorHandler errorHandler = new ErrorHandler();

        xml(dirRes,errorHandler,project);
        custom_row(dirRes,errorHandler,project);
        mainclass(dirSrc,Main,project,Package);
        auxiliarclass(dirSrc,pack,project);

    }

    public static void xml(String dir,ErrorHandler errorHandler,Project project){
        String item="android.support.v7.widget.RecyclerView";

        ArrayList<String> names=new ArrayList<>();
        names.add("xmlns:android");
        names.add("xmlns:tools");
        names.add("android:layout_width");
        names.add("android:layout_height");
        names.add("android:paddingLeft");
        names.add("android:paddingRight");
        names.add("android:paddingTop");
        names.add("android:paddingBottom");
        names.add("android:background");
        names.add("tools:context");

        ArrayList<String> values=new ArrayList<>();
        values.add("http://schemas.android.com/apk/res/android");
        values.add("http://schemas.android.com/tools");
        values.add("match_parent");
        values.add("match_parent");
        values.add("@dimen/activity_horizontal_margin");
        values.add("@dimen/activity_horizontal_margin");
        values.add("@dimen/activity_vertical_margin");
        values.add("@dimen/activity_vertical_margin");
        values.add("@color/PrimaryLight");
        //conseguir el context del main activity!!!!!!!!!!!!!!!!!!!
        values.add(".MainActivity");

        ArrayList<String> names2=new ArrayList<>();
        names2.add("android:id");
        names2.add("android:layout_width");
        names2.add("android:layout_height");

        ArrayList<String> values2=new ArrayList<>();
        values2.add("@+id/recycle");
        values2.add("match_parent");
        values2.add("wrap_content");

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement("RelativeLayout");//root element

            for(int i=0;i<10;i++){
                element.setAttribute(names.get(i), values.get(i));
            }
            document.appendChild(element);

            Element childElement = document.createElement(item);
            for(int i=0;i<3;i++){
                childElement.setAttribute(names2.get(i), values2.get(i));
            }
            element.appendChild(childElement);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            Result output = new StreamResult(new File(dir+"activity_main.xml"));
            Source input = new DOMSource(document);
            transformer.transform(input, output);

        }catch(Exception e) {
            errorHandler.handleError(project, e);
        }

    }

    public static void custom_row(String dir,ErrorHandler errorHandler,Project project){

        String card="android.support.v7.widget.CardView";

        ArrayList<String> rootname=new ArrayList<>();
        rootname.add("xmlns:android");
        rootname.add("android:orientation");
        rootname.add("android:layout_width");
        rootname.add("xmlns:app");
        rootname.add("android:layout_height");
        ArrayList<String> rootattrib=new ArrayList<>();
        rootattrib.add("http://schemas.android.com/apk/res/android");
        rootattrib.add("vertical");
        rootattrib.add("match_parent");
        rootattrib.add("http://schemas.android.com/apk/res-auto");
        rootattrib.add("match_parent");

        ArrayList<String> cardname=new ArrayList<>();
        cardname.add("android:id");
        cardname.add("android:elevation");
        cardname.add("android:layout_margin");
        cardname.add("android:layout_width");
        cardname.add("app:cardCornerRadius");
        cardname.add("android:layout_height");
        ArrayList<String> cardattrib=new ArrayList<>();
        cardattrib.add("@+id/cv");
        cardattrib.add("5dp");
        cardattrib.add("10dp");
        cardattrib.add("match_parent");
        cardattrib.add("2dp");
        cardattrib.add("wrap_content");

        ArrayList<String> layoutname=new ArrayList<>();
        layoutname.add("android:layout_width");
        layoutname.add("android:layout_height");
        layoutname.add("android:padding");
        layoutname.add("android:focusableInTouchMode");
        layoutname.add("android:elevation");
        ArrayList<String> layoutattrib=new ArrayList<>();
        layoutattrib.add("match_parent");
        layoutattrib.add("wrap_content");
        layoutattrib.add("16dp");
        layoutattrib.add("false");
        layoutattrib.add("20dp");

        //Contenido del cardview-------------
        ArrayList<String> ImgName=new ArrayList<>();
        ArrayList<String> ImgValue=new ArrayList<>();

        ImgName.add("android:layout_width");ImgValue.add("100dp");
        ImgName.add("android:layout_height");ImgValue.add("100dp");
        ImgName.add("android:id");ImgValue.add("@+id/img");
         //-----------------
        ArrayList<String> TextName=new ArrayList<>();
        ArrayList<String> TextValue=new ArrayList<>();

        TextName.add("android:id");TextValue.add("@+id/texto1");
        TextName.add("android:layout_width");TextValue.add("wrap_content");
        TextName.add("android:layout_height");TextValue.add("wrap_content");
        TextName.add("android:layout_toRightOf");TextValue.add("@+id/img");
        TextName.add("android:layout_toEndOf");TextValue.add("@+id/img");
        TextName.add("android:padding");TextValue.add("10dp");
        TextName.add("android:textSize");TextValue.add("15sp");


        //-----------------
        ArrayList<String> Text2Name=new ArrayList<>();
        ArrayList<String> Text2Value=new ArrayList<>();

        Text2Name.add("android:id");Text2Value.add("@+id/texto2");
        Text2Name.add("android:layout_width");Text2Value.add("wrap_content");
        Text2Name.add("android:layout_height");Text2Value.add("wrap_content");
        Text2Name.add("android:layout_below");Text2Value.add("@+id/texto1");
        Text2Name.add("android:layout_toRightOf");Text2Value.add("@+id/img");
        Text2Name.add("android:textSize");Text2Value.add("15sp");
        Text2Name.add("android:padding");Text2Value.add("10dp");


        //-----------------
        ArrayList<String> Text3Name=new ArrayList<>();
        ArrayList<String> Text3Value=new ArrayList<>();

        Text3Name.add("android:layout_width");Text3Value.add("wrap_content");
        Text3Name.add("android:layout_height");Text3Value.add("wrap_content");
        Text3Name.add("android:id");Text3Value.add("@+id/texto3");
        Text3Name.add("android:textSize");Text3Value.add("15sp");
        Text3Name.add("android:padding");Text3Value.add("10dp");
        Text3Name.add("android:layout_toRightOf");Text3Value.add("@+id/img");
        Text3Name.add("android:layout_toEndOf");Text3Value.add("@+id/texto2");
        Text3Name.add("android:layout_below");Text3Value.add("@+id/texto2");


        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement("LinearLayout");//root element
            for(int i=0;i<rootname.size();i++){
                element.setAttribute(rootname.get(i), rootattrib.get(i));
            }
            document.appendChild(element);

            Element card_element= document.createElement(card);
            for(int i=0;i<cardname.size();i++){
                card_element.setAttribute(cardname.get(i), cardattrib.get(i));
            }
            element.appendChild(card_element);

            Element layout=document.createElement("RelativeLayout");
            for(int i=0;i<layoutname.size();i++){
                layout.setAttribute(layoutname.get(i),layoutattrib.get(i));
            }
            card_element.appendChild(layout);

            Element img=document.createElement("ImageView");
            for(int i=0;i<ImgName.size();i++){
                img.setAttribute(ImgName.get(i),ImgValue.get(i));
            }
            layout.appendChild(img);

            Element txt1=document.createElement("TextView");
            for(int i=0;i<TextName.size();i++){
                txt1.setAttribute(TextName.get(i),TextValue.get(i));
            }
            layout.appendChild(txt1);

            Element txt2=document.createElement("TextView");
            for(int i=0;i<Text2Name.size();i++){
                txt2.setAttribute(Text2Name.get(i),Text2Value.get(i));
            }
            layout.appendChild(txt2);

            Element txt3=document.createElement("TextView");
            for(int i=0;i<Text3Name.size();i++){
               txt3.setAttribute(Text3Name.get(i),Text3Value.get(i));
            }
            layout.appendChild(txt3);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            document.setXmlStandalone(true);


            Result output = new StreamResult(new File(dir+"custom_row.xml"));
            Source input = new DOMSource(document);
            transformer.transform(input, output);


        }catch(Exception e) {
            errorHandler.handleError(project, e);
        }

    }

    public static void mainclass(String dir,String MainLocation,Project project,String Package){
        ArrayList<String> output=new ArrayList<>();

        String pack="package "+Package+";";

        ArrayList<String> imports=new ArrayList<>();
        imports.add("import android.support.v7.app.AppCompatActivity;");
        imports.add("import android.os.Bundle;");
        imports.add("import android.view.Menu;");
        imports.add("import android.view.MenuItem;");
        imports.add("import android.app.AlertDialog;");
        imports.add("import android.content.Context;");
        imports.add("import android.content.DialogInterface;");
        imports.add("import android.content.Intent;");
        imports.add("import android.support.v7.widget.LinearLayoutManager;");
        imports.add("import android.support.v7.widget.RecyclerView;");
        imports.add("import android.view.View;");
        imports.add("import android.widget.Toast;");

        ArrayList<String> attributes=new ArrayList<>();
        attributes.add("\tfinal Context context = this;");
        attributes.add("\tDataStructure De=DataStructure.getInstance();");
        attributes.add("\tprivate ViewAdapter viewAdapter;");
        attributes.add("\tprivate RecyclerView mRecyclerView;");


        ArrayList<String> oncreate=new ArrayList<>();
        oncreate.add("\t@Override");
        oncreate.add("\tprotected void onCreate(Bundle savedInstanceState) {");
        oncreate.add("\t\tsuper.onCreate(savedInstanceState);");
        oncreate.add("\t\tsetContentView(R.layout.activity_main);");
        oncreate.add("\t\tmRecyclerView=(RecyclerView)findViewById(R.id.recycle);");
        oncreate.add("\t\tviewAdapter= new ViewAdapter(this);");
        oncreate.add("\t\tviewAdapter.setRecyclerClickListner(this);");
        oncreate.add("\t\tmRecyclerView.setAdapter(viewAdapter);");
        oncreate.add("\t\tmRecyclerView.setLayoutManager(new LinearLayoutManager(this));");
        oncreate.add("\t}");

        ArrayList<String> onresume=new ArrayList<>();
        onresume.add("\t@Override");
        onresume.add("\tpublic void onResume(){");
        onresume.add("\t\tsuper.onResume();");
        onresume.add("\t\tviewAdapter= new ViewAdapter(this);");
        onresume.add("\t\tviewAdapter.setRecyclerClickListner(this);");
        onresume.add("\t\tmRecyclerView.setAdapter(viewAdapter);");
        onresume.add("\t\tmRecyclerView.setLayoutManager(new LinearLayoutManager(this));");
        onresume.add("\t\tContext context = getApplicationContext();");
        onresume.add("\t\tCharSequence text = \"Resume\";");
        onresume.add("\t\tint duration = Toast.LENGTH_SHORT;");
        onresume.add("\t\tToast toast = Toast.makeText(context,text, duration);");
        onresume.add("\t\ttoast.show();");
        onresume.add("\t}");

        // TODO Fix this sheet
        ArrayList<String> onjump=new ArrayList<>();
        onjump.add("\tpublic void saltar(final int position){");
        onjump.add("\t\tAlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Lista.this);");
        onjump.add("\t\talertDialogBuilder.setTitle(\"Selección de vista\");");
        onjump.add("\t\talertDialogBuilder.setMessage(\"Seleccione el view que desea ejecutar\");");
        onjump.add("\t\talertDialogBuilder.setPositiveButton(\"Vista\", new DialogInterface.OnClickListener() {");
        onjump.add("\t\t\tpublic void onClick(DialogInterface dialogo1, int id) {");
        onjump.add("\t\t\t\tIntent i;");
        onjump.add("\t\t\t\tswitch(position){");
        onjump.add("\t\t\t\t\tcase 0:");
        onjump.add("\t\t\t\t\t\ti = new Intent(context,Vista0.class );");
        onjump.add("\t\t\t\t\t\tstartActivity(i);");
        onjump.add("\t\t\t\t\t\tbreak;");
        onjump.add("\t\t\t\t\tdefault:");
        onjump.add("\t\t\t\t\t\tContext context = getApplicationContext();");
        onjump.add("\t\t\t\t\t\tCharSequence text = \"Vista no implementada\";");
        onjump.add("\t\t\t\t\t\tint duration = Toast.LENGTH_SHORT;");
        onjump.add("\t\t\t\t\t\tToast toast = Toast.makeText(context,text, duration);");
        onjump.add("\t\t\t\t\t\ttoast.show();");
        onjump.add("\t\t\t\t\t\tbreak;");
        onjump.add("\t\t\t\t}");
        onjump.add("\t\t\t}");
        onjump.add("\t\t});");
        onjump.add("\t\talertDialogBuilder.setNegativeButton(\"Edicion\", new DialogInterface.OnClickListener() {");
        onjump.add("\t\t\tpublic void onClick(DialogInterface dialogo1, int id) {");
        onjump.add("\t\t\t\tIntent i;");
        onjump.add("\t\t\t\tswitch (position) {");
        onjump.add("\t\t\t\t\tcase 0:");
        onjump.add("\t\t\t\t\t\ti = new Intent(context, Edicion0.class);");
        onjump.add("\t\t\t\t\t\tstartActivity(i);");
        onjump.add("\t\t\t\t\t\tbreak;");
        onjump.add("\t\t\t\t\tdefault:");
        onjump.add("\t\t\t\t\t\tContext context = getApplicationContext();");
        onjump.add("\t\t\t\t\t\tCharSequence text = \"Formulario no implementado\";");
        onjump.add("\t\t\t\t\t\tint duration = Toast.LENGTH_SHORT;");
        onjump.add("\t\t\t\t\t\tToast toast = Toast.makeText(context,text, duration);");
        onjump.add("\t\t\t\t\t\ttoast.show();");
        onjump.add("\t\t\t\t\t\tbreak;");
        onjump.add("\t\t\t\t}");
        onjump.add("\t\t\t}");
        onjump.add("\t\t});");
        onjump.add("\t\talertDialogBuilder.show();");
        onjump.add("\t}");

        ArrayList<String> oncreateoptionsmenu=new ArrayList<>();
        oncreateoptionsmenu.add("\t@Override");
        oncreateoptionsmenu.add("\tpublic boolean onCreateOptionsMenu(Menu menu) {");
        oncreateoptionsmenu.add("\t\tgetMenuInflater().inflate(R.menu.menu_main, menu);");
        oncreateoptionsmenu.add("\t\treturn true;");
        oncreateoptionsmenu.add("\t}");

        ArrayList<String> onoptionselected=new ArrayList<>();
        onoptionselected.add("\t@Override");
        onoptionselected.add("\tpublic boolean onOptionsItemSelected(MenuItem item) {");
        onoptionselected.add("\t\tint id = item.getItemId();");
        onoptionselected.add("\t\tif (id == R.id.action_settings) {");
        onoptionselected.add("\t\t\treturn true;");
        onoptionselected.add("\t\t}");
        onoptionselected.add("\t\treturn super.onOptionsItemSelected(item);");
        onoptionselected.add("\t}");

        ArrayList<String> itemclick=new ArrayList<>();
        itemclick.add("\t@Override");
        itemclick.add("\tpublic void itemClick(View view, int position) {");
        itemclick.add("\t\tContext context = getApplicationContext();");
        itemclick.add("\t\tCharSequence text = \"Option ID : \";");
        itemclick.add("\t\tint duration = Toast.LENGTH_SHORT;");
        itemclick.add("\t\tToast toast = Toast.makeText(context, text + \"\" + position, duration);");
        itemclick.add("\t\ttoast.show();");
        itemclick.add("\t\tsaltar(position);");
        itemclick.add("\t}");

        //------------
        output.add(pack);
        output.add("");
        for(String st : imports){
            output.add(st);
        }
        output.add("");
        output.add("public class MainActivity extends AppCompatActivity implements ViewAdapter.RecyclerClickListner {");
        output.add("");
        for(String st : attributes){
            output.add(st);
        }
        output.add("");
        for(String st : oncreate){
            output.add(st);
        }
        output.add("");
        for(String st : onresume){
            output.add(st);
        }
        output.add("");
        for(String st : onjump){
            output.add(st);
        }
        output.add("");
        for(String st : onoptionselected){
            output.add(st);
        }
        output.add("");
        for(String st : oncreateoptionsmenu){
            output.add(st);
        }
        output.add("");
        for(String st : itemclick){
            output.add(st);
        }
        output.add("}");

        File f=new File(MainLocation);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(f, "UTF-8");
            for (String anOutput : output) {
                writer.println(anOutput);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public static void auxiliarclass(String dir, String pack,Project project){
        Resources r=new Resources(pack,null);

        File f=new File(dir+"\\ViewAdapter.java");
        try {
            Resources.WriteClass(f,r.getAdapter());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            ErrorHandler.handleError(project,e);
        }
    }

    public static Document manifest(String path) {
        //byte[] myByteArray;
        File f=new File(path+"\\AndroidManifest.xml");
            //myByteArray=vf.contentsToByteArray();
            //FileOutputStream fos = new FileOutputStream("C:\\Users\\Necho\\Desktop"+"\\AndroidManifest.xml");
            //fos.write(myByteArray);
            //fos.close();
        Document document = null;
        DocumentBuilder builder = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            builder = docFactory.newDocumentBuilder();
            document = builder.parse(f);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            document.setXmlStandalone(true);

            Node man = document.getElementsByTagName("manifest").item(0);

            NodeList nod=man.getChildNodes();

            Element permission1=document.createElement("uses-permission");
            permission1.setAttribute("android:name","android.permission.WRITE_EXTERNAL_STORAGE");

            Element permission2=document.createElement("uses-permission");
            permission2.setAttribute("android:name","android.permission.CAMERA");

            Element feature1=document.createElement("uses-feature");
            feature1.setAttribute("android:name","android.hardware.camera");

            Element feature2=document.createElement("uses-feature");
            feature2.setAttribute("android:name","android.hardware.camera.autofocus");

            File check=new File(path+"\\AndroidManifest.xml");
            BufferedReader br = new BufferedReader(new FileReader(check));
            boolean contains1=false;
            boolean contains2=false;
            boolean contains3=false;
            boolean contains4=false;

            try {
                String line = br.readLine();
                while (line != null) {
                    if(line.contains("<uses-permission android:name=\"android.permission.WRITE_EXTERNAL_STORAGE\"/>")){
                        contains1=true;
                    }
                    if(line.contains("<uses-permission android:name=\"android.permission.CAMERA\"/>")){
                        contains2=true;
                    }
                    if(line.contains("<uses-feature android:name=\"android.hardware.camera\"/>")){
                        contains3=true;
                    }
                    if(line.contains("<uses-feature android:name=\"android.hardware.camera.autofocus\"/>")){
                        contains4=true;
                    }
                    line = br.readLine();
                }
            } finally {
                br.close();
            }

            if(!contains1){
                ((Element)man).appendChild(permission1);
            }
            if(!contains2){
                ((Element)man).appendChild(permission2);
            }
            if(!contains3){
                ((Element)man).appendChild(feature1);
            }
            if(!contains4){
                ((Element)man).appendChild(feature2);
            }

            Node app = document.getElementsByTagName("application").item(0);
            ((Element)app).setAttribute("android:largeHeap","true");

            Result output = new StreamResult(new File(path+"\\AndroidManifest.xml"));
            Source input = new DOMSource(document);
            transformer.transform(input, output);

            return document;

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void AddActivityToManifest(String path,String activity_name){
        File f=new File(path+"\\AndroidManifest.xml");

        Document document = null;
        DocumentBuilder builder = null;

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            builder = docFactory.newDocumentBuilder();
            document = builder.parse(f);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            document.setXmlStandalone(true);

            Element man = (Element) document.getElementsByTagName("manifest").item(0);
            man=(Element) man.getElementsByTagName("application").item(0);

            Element activity_added=document.createElement("activity");
            activity_added.setAttribute("android:name","."+activity_name);
            activity_added.setAttribute("android:label",""+activity_name);
            man.appendChild(activity_added);

            Result output = new StreamResult(new File(path+"\\AndroidManifest.xml"));
            Source input = new DOMSource(document);
            transformer.transform(input, output);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void RemoveActivityFromManifest(String path,String activity_name){
        File f=new File(path+"\\AndroidManifest.xml");

        Document document = null;
        DocumentBuilder builder = null;

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            builder = docFactory.newDocumentBuilder();
            document = builder.parse(f);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            document.setXmlStandalone(true);

            Element man = (Element) document.getElementsByTagName("manifest").item(0);
            man=(Element) man.getElementsByTagName("application").item(0);

            NodeList activities= man.getElementsByTagName("activity");
            for(int i=0;i<activities.getLength();i++){
                NamedNodeMap test=activities.item(i).getAttributes();
                String name="";
                String value="";

                for(int j=0;j<test.getLength();j++){//TODO : FIX THIS
                    name=test.item(j).getNodeName();
                    value=test.item(j).getTextContent();
                }
            }

            Element activity_added=document.createElement("activity");
            activity_added.setAttribute("android:name","."+activity_name);
            activity_added.setAttribute("android:label",""+activity_name);
            man.appendChild(activity_added);

            Result output = new StreamResult(new File(path+"\\AndroidManifest.xml"));
            Source input = new DOMSource(document);
            transformer.transform(input, output);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

}
