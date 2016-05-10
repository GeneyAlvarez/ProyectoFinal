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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Necho on 1/05/2016.
 */
public class ActivityGeneration {




    public static void start(Project project, String commando, ArrayList<String>  attrib, String pathclass, String pathxml, String pack,String ManifestLocation){
        String[] test = commando.split(" ");
        String classname="";
        switch (test.length){
            case 2:
                classname=test[1];
                break;
            case 3:
                classname=test[2];
                break;
        }

        ArrayList<String>  attrib_view=new ArrayList<>();
        ArrayList<String>  attrib_edition=new ArrayList<>();

        for(String str: attrib){
            if(str.split(" ").length==2){
                if(str.split(" ")[0].contains("v")){
                    attrib_view.add(str.split(" ")[1]);
                }
                if(str.split(" ")[0].contains("f")){
                    attrib_edition.add(str.split(" ")[1]);
                }
            }
        }
        //----------------
        ActivityClass(project,attrib_view,pathxml,"view",classname,pack);
        ActivityClass(project,attrib_edition,pathxml,"edition",classname,pack);

        ActivityClass2(project,attrib_view,pathclass,"view",classname,pack);
        ActivityClass2(project,attrib_edition,pathclass,"edition",classname,pack);

        mainGeneration.AddActivityToManifest(ManifestLocation,classname+"_"+"view");
        mainGeneration.AddActivityToManifest(ManifestLocation,classname+"_"+"edition");


    }

    public static void ActivityClass(Project project, ArrayList<String> attrib,String pathxml,String Type,String Name,String Pack){
        final ErrorHandler errorHandler = new ErrorHandler();

        Menu_Generation.generate(pathxml+"\\menu\\",Name+"_"+Type);

        ArrayList<String> Coordinator_name=new ArrayList<>();
        ArrayList<String> Coordinator_atrib=new ArrayList<>();
        Coordinator_name.add("xmlns:android");                  Coordinator_atrib.add("http://schemas.android.com/apk/res/android");
        Coordinator_name.add("xmlns:app");                      Coordinator_atrib.add("http://schemas.android.com/apk/res-auto");
        Coordinator_name.add("android:layout_width");           Coordinator_atrib.add("match_parent");
        Coordinator_name.add("android:layout_height");          Coordinator_atrib.add("match_parent");
        Coordinator_name.add("xmlns:tools");                    Coordinator_atrib.add("http://schemas.android.com/tools");
        Coordinator_name.add("android:fitsSystemWindows");      Coordinator_atrib.add("true");
        Coordinator_name.add("android:background");             Coordinator_atrib.add("@colors/PrimaryLight");
        Coordinator_name.add("tools:context");                  Coordinator_atrib.add(Pack+"."+Name+"_"+Type);

        ArrayList<String> Appbar_name=new ArrayList<>();
        ArrayList<String> Appbar_atrib=new ArrayList<>();
        Appbar_name.add("android:id");                             Appbar_atrib.add("@+id/app_bar_layout");
        Appbar_name.add("android:layout_width");                   Appbar_atrib.add("match_parent");
        Appbar_name.add("android:layout_height");                  Appbar_atrib.add("wrap_content");
        Appbar_name.add("android:theme");                          Appbar_atrib.add("@style/ThemeOverlay.AppCompat.Dark.ActionBar");
        Appbar_name.add("android:fitsSystemWindows");              Appbar_atrib.add("true");

        ArrayList<String> Collapse_name=new ArrayList<>();
        ArrayList<String> Collapse_atrib=new ArrayList<>();
        Collapse_name.add("android:id");                           Collapse_atrib.add("@+id/collapsing_toolbar");
        Collapse_name.add("android:layout_width");                 Collapse_atrib.add("match_parent");
        Collapse_name.add("android:layout_height");                Collapse_atrib.add("match_parent");
        Collapse_name.add("app:layout_scrollFlags");               Collapse_atrib.add("scroll|exitUntilCollapsed");
        Collapse_name.add("app:contentScrim");                     Collapse_atrib.add("?attr/colorPrimary");
        Collapse_name.add("app:expandedTitleMarginStart");         Collapse_atrib.add("48dp");
        Collapse_name.add("app:expandedTitleMarginEnd");           Collapse_atrib.add("64dp");
        Collapse_name.add("android:fitsSystemWindows");            Collapse_atrib.add("true");

        ArrayList<String> Collapse_img_name=new ArrayList<>();
        ArrayList<String> Collapse_img_atrib=new ArrayList<>();
        Collapse_img_name.add("android:id");                           Collapse_img_atrib.add("@+id/header");
        Collapse_img_name.add("android:layout_width");                 Collapse_img_atrib.add("match_parent");
        Collapse_img_name.add("android:layout_height");                Collapse_img_atrib.add("300sp");
        Collapse_img_name.add("android:fitsSystemWindows");            Collapse_img_atrib.add("true");
        Collapse_img_name.add("android:scaleType");                    Collapse_img_atrib.add("centerCrop");
        Collapse_img_name.add("app:layout_collapseMode");              Collapse_img_atrib.add("parallax");

        ArrayList<String> Toolbar_name=new ArrayList<>();
        ArrayList<String> Toolbar_atrib=new ArrayList<>();
        Toolbar_name.add("android:id");                                    Toolbar_atrib.add("@+id/toolbar2");
        Toolbar_name.add("android:layout_width");                          Toolbar_atrib.add("match_parent");
        Toolbar_name.add("android:layout_height");                         Toolbar_atrib.add("?attr/actionBarSize");
        Toolbar_name.add("app:popupTheme");                                Toolbar_atrib.add("@style/ThemeOverlay.AppCompat.Light");
        Toolbar_name.add("app:layout_collapseMode");                       Toolbar_atrib.add("pin");

        ArrayList<String> Nest_name=new ArrayList<>();
        ArrayList<String> Nest_atrib=new ArrayList<>();
        Nest_name.add("android:id");                                       Nest_atrib.add("@+id/scroll");
        Nest_name.add("android:layout_width");                             Nest_atrib.add("match_parent");
        Nest_name.add("android:layout_height");                            Nest_atrib.add("wrap_content");
        Nest_name.add("app:layout_behavior");                              Nest_atrib.add("@string/appbar_scrolling_view_behavior");
        Nest_name.add("android:clipToPadding");                            Nest_atrib.add("false");

        ArrayList<String> Layout_name=new ArrayList<>();
        ArrayList<String> Layout_atrib=new ArrayList<>();
        Layout_name.add("android:layout_width");                              Layout_atrib.add("match_parent");
        Layout_name.add("android:layout_height");                             Layout_atrib.add("wrap_content");
        Layout_name.add("android:orientation");                               Layout_atrib.add("vertical");


        ArrayList<String> fab_name=new ArrayList<>();
        ArrayList<String> fab_atrib=new ArrayList<>();
        fab_name.add("android:id");                                            fab_atrib.add("@+id/fab");
        fab_name.add("android:layout_width");                                  fab_atrib.add("wrap_content");
        fab_name.add("android:layout_height");                                 fab_atrib.add("wrap_content");
        fab_name.add("android:onClick");                                       fab_atrib.add("FAB_Click");
        fab_name.add("android:layout_margin");                                 fab_atrib.add("16dp");
        fab_name.add("app:layout_anchor");                                     fab_atrib.add("@id/app_bar_layout");
        fab_name.add("app:layout_anchorGravity");                              fab_atrib.add("bottom|right|end");
        fab_name.add("android:clickable");                                     fab_atrib.add("true");
        fab_name.add("app:elevation");                                         fab_atrib.add("6dp");
        fab_name.add("app:rippleColor");                                       fab_atrib.add("@android:color/white");

        //-----------Text Components--------------
        ArrayList<String> text_field_name=new ArrayList<>();                    ArrayList<String> text_field_atrib=new ArrayList<>();
        text_field_name.add("android:layout_width");                            text_field_atrib.add("wrap_content");
        text_field_name.add("android:layout_height");                           text_field_atrib.add("wrap_content");
        text_field_name.add("android:layout_marginTop");                        text_field_atrib.add("20sp");
        text_field_name.add("android:paddingLeft");                             text_field_atrib.add("10sp");
        text_field_name.add("android:paddingRight");                            text_field_atrib.add("10sp");
        text_field_name.add("android:fontFamily");                              text_field_atrib.add("sans-serif-condensed");
        text_field_name.add("android:textColor");                               text_field_atrib.add("#000000");
        text_field_name.add("android:textSize");                                text_field_atrib.add("30sp");
        text_field_name.add("android:textStyle");                               text_field_atrib.add("bold");
        //----------------------------------------

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement("android.support.design.widget.CoordinatorLayout");//root element
            for(int i=0;i<Coordinator_name.size();i++){
                element.setAttribute(Coordinator_name.get(i), Coordinator_atrib.get(i));
            }
            document.appendChild(element);

            Element Appbar = document.createElement("android.support.design.widget.AppBarLayout");
            for(int i=0;i<Appbar_name.size();i++){
                Appbar.setAttribute(Appbar_name.get(i), Appbar_atrib.get(i));
            }
            element.appendChild(Appbar);

            Element Collapse = document.createElement("android.support.design.widget.CollapsingToolbarLayout");
            for(int i=0;i<Collapse_name.size();i++){
                Collapse.setAttribute(Collapse_name.get(i), Collapse_atrib.get(i));
            }
            Appbar.appendChild(Collapse);

            Element Collapse_img = document.createElement("ImageView");
            for(int i=0;i<Collapse_img_name.size();i++){
                Collapse_img.setAttribute(Collapse_img_name.get(i), Collapse_img_atrib.get(i));
            }
            Collapse.appendChild(Collapse_img);

            Element Toolbar = document.createElement("android.support.v7.widget.Toolbar");
            for(int i=0;i<Toolbar_name.size();i++){
                Toolbar.setAttribute(Toolbar_name.get(i), Toolbar_atrib.get(i));
            }
            Collapse.appendChild(Toolbar);

            Element Nest = document.createElement("android.support.v4.widget.NestedScrollView");
            for(int i=0;i<Nest_name.size();i++){
                Nest.setAttribute(Nest_name.get(i), Nest_atrib.get(i));
            }
            element.appendChild(Nest);

            Element Layout = document.createElement("RelativeLayout");
            for(int i=0;i<Layout_name.size();i++){
                Layout.setAttribute(Layout_name.get(i), Layout_atrib.get(i));
            }
            Nest.appendChild(Layout);

            Element fab = document.createElement("android.support.design.widget.FloatingActionButton");
            for(int i=0;i<fab_name.size();i++){
                fab.setAttribute(fab_name.get(i), fab_atrib.get(i));
            }
            element.appendChild(fab);

            //----------------------------------------------------------------
                int tam=attrib.size();
                //switch(Type){

                    for(int i=0;i<tam;i++){
                        String attr_name=attrib.get(i).split("\\.")[0];
                        attr_name=attr_name.substring(0, 1).toUpperCase()+attr_name.substring(1);
                        String attr_type=attrib.get(i).split("\\.")[1];

                        Element ATTRIBUTE= document.createElement("TextView");
                        ATTRIBUTE.setAttribute("android:text",attr_name);
                        for(int j=0;j<text_field_name.size();j++){
                            ATTRIBUTE.setAttribute(text_field_name.get(j),text_field_atrib.get(j));
                        }
                        Element VALUE = null;
                        if(attr_type.equals("file")){
                            switch(Type){
                                case "view":
                                    VALUE= document.createElement("ImageView");
                                    break;
                                case "edition":
                                    VALUE= document.createElement("ImageButton");
                                    VALUE.setAttribute("android:onClick","onClick_"+attr_name);
                                    break;
                            }
                        }else{
                            switch(Type){//android:inputType
                                case "view":
                                    VALUE= document.createElement("TextView");
                                    break;
                                case "edition":
                                    VALUE= document.createElement("EditText");
                                    if(attr_type.equals("string")){
                                        VALUE.setAttribute("android:inputType","text");
                                    }else{
                                        if(attr_type.equals("int")){
                                            VALUE.setAttribute("android:inputType","number");
                                        }
                                    }
                                    break;
                            }
                        }

                        for(int j=0;j<text_field_name.size();j++){
                            if (VALUE != null) {
                                VALUE.setAttribute(text_field_name.get(j),text_field_atrib.get(j));
                            }
                        }
                        ATTRIBUTE.setAttribute("android:id","@+id/text"+i);
                        if (VALUE != null) {
                            VALUE.setAttribute("android:id","@+id/value"+i);
                        }
                        if(i!=0){
                            ATTRIBUTE.setAttribute("android:layout_below","@+id/value"+(i-1));
                        }
                        if (VALUE != null) {
                            VALUE.setAttribute("android:layout_below","@+id/text"+i);
                        }

                        Layout.appendChild(ATTRIBUTE);
                        Layout.appendChild(VALUE);
                    }

            //----------------------------------------------------------------

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            document.setXmlStandalone(true);

            Result output = new StreamResult(new File(pathxml+"layout\\activity_"+Name+"_"+Type+".xml"));
            Source input = new DOMSource(document);

            transformer.transform(input, output);

        }catch(Exception e) {
            errorHandler.handleError(project, e);
        }
    }

    public static void ActivityClass2(Project project, ArrayList<String> attrib,String path,String Type,String Name,String Pack){
        String classname=Name+"_"+Type;

        String pack="package "+Pack+";";

        ArrayList<String> imports=new ArrayList<>();
        switch(Type){
            case "view":
                imports.add("import android.content.Context;");
                imports.add("import android.content.SharedPreferences;");
                imports.add("import android.graphics.BitmapFactory;");
                imports.add("import android.net.Uri;");
                imports.add("import android.support.design.widget.CollapsingToolbarLayout;");
                imports.add("import android.support.v7.app.AppCompatActivity;");
                imports.add("import android.os.Bundle;");
                imports.add("import android.widget.ImageButton;");
                imports.add("import android.support.v7.widget.Toolbar;");
                imports.add("import android.view.Menu;");
                imports.add("import android.view.MenuItem;");
                imports.add("import android.view.View;");
                imports.add("import android.widget.EditText;");
                imports.add("import android.widget.ImageView;");
                imports.add("import android.widget.TextView;");
                imports.add("import android.widget.Toast;");
                break;
            case "edition":
                imports.add("import android.app.AlertDialog;");
                imports.add("import android.content.Context;");
                imports.add("import android.content.DialogInterface;");
                imports.add("import android.content.Intent;");
                imports.add("import android.content.SharedPreferences;");
                imports.add("import android.database.Cursor;");
                imports.add("import android.graphics.Bitmap;");
                imports.add("import android.graphics.BitmapFactory;");
                imports.add("import android.media.Image;");
                imports.add("import android.net.Uri;");
                imports.add("import android.provider.MediaStore;");
                imports.add("import android.support.design.widget.CollapsingToolbarLayout;");
                imports.add("import android.support.v7.app.AppCompatActivity;");
                imports.add("import android.os.Bundle;");
                imports.add("import android.support.v7.widget.Toolbar;");
                imports.add("import android.util.Base64;");
                imports.add("import android.view.Menu;");
                imports.add("import android.view.MenuItem;");
                imports.add("import android.view.View;");
                imports.add("import android.widget.EditText;");
                imports.add("import android.widget.ImageView;");
                imports.add("import android.widget.Toast;");
                imports.add("import java.io.ByteArrayOutputStream;");
                break;
        }

        ArrayList<String> attributes=new ArrayList<>();
        attributes.add("\tIntent mIntent = getIntent();");
        attributes.add("\tint intValue = mIntent.getIntExtra(\"INDEX\", 0);");
        attributes.add("\tint index=intValue;");
        attributes.add("\tDataStructure De=DataStructure.getInstance();");
        attributes.add("\tData dat=De.Arraytest.get(index);");
        attributes.add("\t"+Name+" obj = ("+Name+") dat.getOb();");
        attributes.add("\tprivate Toolbar toolbar;");
        attributes.add("\tprivate CollapsingToolbarLayout collapsingToolbarLayout;");
        /*if(Type.equals("edition")){
            attributes.add("\tString imgDecodableString;");
        }*/
        for(int i=0;i<attrib.size();i++){
            String type=attrib.get(i).split("\\.")[1];
            String name=attrib.get(i).split("\\.")[0];

            attributes.add("");

            switch(Type){
                case "view":
                    if(type.equals("file")){
                        attributes.add("\tprivate ImageView imgv"+i+";");
                    }else{
                        attributes.add("\tprivate TextView tv"+i+";");
                    }
                    break;
                case "edition":
                    if(type.equals("file")){
                        attributes.add("\tImageButton imgv"+i+";");
                    }else{
                        attributes.add("\tprivate EditText tv"+i+";");
                    }
                    break;
            }

            attributes.add("\tString val_"+name+";");
        }

        ArrayList<String> oncreate=new ArrayList<>();
        oncreate.add("\t@Override");
        oncreate.add("\tprotected void onCreate(Bundle savedInstanceState) {");
        oncreate.add("\t\tsuper.onCreate(savedInstanceState);");
        oncreate.add(String.format("\t\tsetContentView(R.layout.activity_%s);",classname));
        oncreate.add("");
        oncreate.add("\t\ttoolbar = (Toolbar) findViewById(R.id.toolbar2);");
        oncreate.add("\t\tsetSupportActionBar(toolbar);");
        oncreate.add("");
        oncreate.add("\t\tcollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);");
        oncreate.add(String.format("\t\tcollapsingToolbarLayout.setTitle(\"%s\");",classname));
        oncreate.add("\t\tcollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));");
        oncreate.add("\t}");

        ArrayList<String> fab=new ArrayList<>();
        fab.add("\tpublic void FAB_Click(View v){");
        fab.add("\t\tCharSequence text = \" Toast \";");
        fab.add("\t\tint duration = Toast.LENGTH_SHORT;");
        fab.add("\t\tToast toast = Toast.makeText(getApplicationContext(), text, duration);");
        fab.add("\t\ttoast.show();");
        fab.add("\t}");

        ArrayList<String> oncreateoptionsmenu=new ArrayList<>();
        oncreateoptionsmenu.add("\t@Override");
        oncreateoptionsmenu.add("\tpublic boolean onCreateOptionsMenu(Menu menu) {");
        oncreateoptionsmenu.add(String.format("\t\tgetMenuInflater().inflate(R.menu.menu_%s, menu);",classname));//TODO : MAKE THE MENUS IN THE RES/MENU
        oncreateoptionsmenu.add("\t\treturn true;");
        oncreateoptionsmenu.add("\t}");

        ArrayList<String> onoptionselected=new ArrayList<>();
        onoptionselected.add("\t@Override");
        onoptionselected.add("\tpublic boolean onOptionsItemSelected(MenuItem item) {");
        onoptionselected.add("\t\tint id = item.getItemId();");
        onoptionselected.add("\t\tContext context = getApplicationContext();");
        onoptionselected.add("\t\tCharSequence text = \"Option ID : \";");
        onoptionselected.add("\t\tint duration = Toast.LENGTH_SHORT;");
        onoptionselected.add("\t\tToast toast = Toast.makeText(context, text + \"\" + id, duration);");
        onoptionselected.add("\t\ttoast.show();");
        onoptionselected.add("\t\tif (id == R.id.action_settings) {");
        onoptionselected.add("\t\t\treturn true;");
        onoptionselected.add("\t\t}");
        onoptionselected.add("\t\treturn super.onOptionsItemSelected(item);");
        onoptionselected.add("\t}");

        ArrayList<String> SAVE = new ArrayList<>();
        if(Type.equals("edition")){
            SAVE.add("\tpublic void SAVEALL(View v){");
            SAVE.add("\t\t//De.Arraytest.get(index).setOb(inf);");
            SAVE.add("\t\t//De.Arraytest.get(index).setRow(new Row(inf.getFirst(),inf.getSecond(),inf.getThird(),inf.getIcon()));");
            SAVE.add("\t}");
        }

        ArrayList<String> output=new ArrayList<>();
        output.add(pack);
        output.add("");
        for(String st : imports){
            output.add(st);
        }
        output.add("");
        output.add(String.format("public class %s extends AppCompatActivity {",classname));
        output.add("");
        for(String st : attributes){
            output.add(st);
        }
        output.add("");
        for(String st : oncreate){
            output.add(st);
        }
        output.add("");
        for(String st : fab){
            output.add(st);
        }
        output.add("");
        for(String st : oncreateoptionsmenu){
            output.add(st);
        }
        output.add("");
        for(String st : onoptionselected){
            output.add(st);
        }
        output.add("");
        if(Type.equals("edition")){
            for(String st : SAVE){
                output.add(st);
            }
            output.add("");
        }
        output.add("}");


        File f=new File(path+"\\"+classname+".java");

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


}
