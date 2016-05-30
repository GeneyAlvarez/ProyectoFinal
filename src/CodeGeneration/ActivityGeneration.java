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

        String CV="android.support.v7.widget.CardView";
        ArrayList<String> CV_name=new ArrayList<>();    ArrayList<String> CV_atrib=new ArrayList<>();
        CV_name.add("android:id");                      CV_atrib.add("@+id/cv");
        CV_name.add("android:layout_height");           CV_atrib.add("wrap_content");
        CV_name.add("android:layout_margin");           CV_atrib.add("5dp");
        CV_name.add("android:layout_width");            CV_atrib.add("match_parent");
        CV_name.add("card_view:cardCornerRadius");      CV_atrib.add("26dp");
        CV_name.add("card_view:contentPadding");        CV_atrib.add("10dp");
        CV_name.add("card_view:cardElevation");         CV_atrib.add("4dp");
        CV_name.add("card_view:cardBackgroundColor");   CV_atrib.add("#FFFFFF");

        ArrayList<String> Coordinator_name=new ArrayList<>();
        ArrayList<String> Coordinator_atrib=new ArrayList<>();
        Coordinator_name.add("xmlns:android");                  Coordinator_atrib.add("http://schemas.android.com/apk/res/android");
        Coordinator_name.add("xmlns:app");                      Coordinator_atrib.add("http://schemas.android.com/apk/res-auto");
        Coordinator_name.add("android:layout_width");           Coordinator_atrib.add("match_parent");
        Coordinator_name.add("android:layout_height");          Coordinator_atrib.add("match_parent");
        Coordinator_name.add("xmlns:tools");                    Coordinator_atrib.add("http://schemas.android.com/tools");
        Coordinator_name.add("android:fitsSystemWindows");      Coordinator_atrib.add("true");
        Coordinator_name.add("android:background");             Coordinator_atrib.add("@color/PrimaryLight");
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
        Collapse_img_name.add("android:layout_height");                Collapse_img_atrib.add("250sp");
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
        Nest_name.add("xmlns:card_view");                                  Nest_atrib.add("http://schemas.android.com/apk/res-auto");

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
        if(Type.equals("view")){
            fab_name.add("android:src");                                fab_atrib.add("@drawable/ic_create");
        }else{
            fab_name.add("android:src");                                fab_atrib.add("@drawable/ic_backspace");
        }


        //---------------SAVE ALL-------------------------
        ArrayList<String> save_name=new ArrayList<>();
        ArrayList<String> save_atrib=new ArrayList<>();
        save_name.add("android:layout_width");                                    save_atrib.add("wrap_content");
        save_name.add("android:layout_height");                                   save_atrib.add("wrap_content");
        save_name.add("android:id");                                              save_atrib.add("@+id/saveAll");
        save_name.add("android:text");                                            save_atrib.add("Guardar todo");
        save_name.add("android:onClick");                                         save_atrib.add("SAVEALL");
        save_name.add("android:fontFamily");                                      save_atrib.add("sans-serif-condensed");
        save_name.add("android:textSize");                                        save_atrib.add("25sp");
        save_name.add("android:layout_centerHorizontal");                         save_atrib.add("true");
        save_name.add("android:layout_marginTop");                                save_atrib.add("30sp");
        //android:layout_below="@+id/imagen"

        //-----------Text Components--------------
        ArrayList<String> text_field_name=new ArrayList<>();                    ArrayList<String> text_field_atrib=new ArrayList<>();
        text_field_name.add("android:layout_width");                            text_field_atrib.add("match_parent");
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

            Element CARD = document.createElement(CV);
            for(int i=0;i<CV_name.size();i++){
                CARD.setAttribute(CV_name.get(i), CV_atrib.get(i));
            }
            Nest.appendChild(CARD);

            Element Layout = document.createElement("RelativeLayout");
            for(int i=0;i<Layout_name.size();i++){
                Layout.setAttribute(Layout_name.get(i), Layout_atrib.get(i));
            }
            CARD.appendChild(Layout);

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
                                    VALUE.setAttribute("android:scaleType","centerCrop");
                                    VALUE.setAttribute("android:layout_centerHorizontal","true");
                                    VALUE.setAttribute("android:layout_width","200dp");
                                    VALUE.setAttribute("android:layout_height","200dp");
                                    break;
                                case "edition":
                                    VALUE= document.createElement("ImageButton");
                                    VALUE.setAttribute("android:onClick","onClick");
                                    VALUE.setAttribute("android:scaleType","centerCrop");
                                    VALUE.setAttribute("android:layout_centerHorizontal","true");
                                    VALUE.setAttribute("android:layout_width","200dp");
                                    VALUE.setAttribute("android:layout_height","200dp");
                                    break;
                            }
                        }else{
                            switch(Type){//android:inputType
                                case "view":
                                    VALUE= document.createElement("TextView");
                                    VALUE.setAttribute("android:gravity","center");
                                    break;
                                case "edition":
                                    VALUE= document.createElement("EditText");
                                    VALUE.setAttribute("android:gravity","center");
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

            if(Type.equals("edition")) {
                Element boton = document.createElement("Button");
                boton.setAttribute("android:layout_below", "@+id/value" + (tam - 1));
                for (int j = 0; j < save_name.size(); j++) {
                    boton.setAttribute(save_name.get(j), save_atrib.get(j));
                }
                Layout.appendChild(boton);
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
                imports.add("import android.content.Intent;");
                imports.add("import android.graphics.BitmapFactory;");
                imports.add("import android.net.Uri;");
                imports.add("import android.content.SharedPreferences;");
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
                imports.add("import android.content.SharedPreferences;");
                imports.add("import android.graphics.drawable.BitmapDrawable;");
                imports.add("import android.content.DialogInterface;");
                imports.add("import android.content.Intent;");
                imports.add("import android.widget.ImageButton;");
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
        attributes.add("\tint intValue;");
        attributes.add("\tint index;");
        attributes.add("\tDataStructure De;");
        attributes.add("\tData dat;");
        attributes.add("\t"+Name+" obj;");

        attributes.add("\tprivate Toolbar toolbar;");
        attributes.add("\tprivate CollapsingToolbarLayout collapsingToolbarLayout;");
        attributes.add("\tint actual_id;");
        attributes.add("");
        attributes.add("\tImageView header;");
        attributes.add("\tString val_header;");
        attributes.add("");

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
        oncreate.add("\t\tintValue = loadSavedPreferences();");
        oncreate.add("\t\tindex=intValue;");
        oncreate.add("\t\tDe=DataStructure.getInstance();");
        oncreate.add("\t\tdat=De.Arraytest.get(index);");
        oncreate.add("\t\tobj = ("+Name+") dat.getOb();");
        oncreate.add("");
        oncreate.add("\t\theader=(ImageView) findViewById(R.id.header);");
        oncreate.add("\t\tval_header= obj.getIcon();");
        oncreate.add("\t\tif(val_header!=null){header.setImageURI(Uri.parse(val_header));}");


        for(int i=0;i<attrib.size();i++) {
            String type = attrib.get(i).split("\\.")[1];
            String name = attrib.get(i).split("\\.")[0];
            String nameMayus=name.substring(0,1).toUpperCase()+name.substring(1);
            oncreate.add("");

            switch(Type){
                case "view":
                    if(type.equals("file")){
                        oncreate.add("\t\timgv"+i+"=(ImageView) findViewById(R.id.value"+i+");");
                        oncreate.add("\t\tval_"+name+" = obj.get"+nameMayus+"();");
                        oncreate.add("\t\timgv"+i+".setImageURI(Uri.parse(val_"+name+"));");
                    }else{
                        oncreate.add("\t\ttv"+i+"=(TextView) findViewById(R.id.value"+i+");");
                        oncreate.add("\t\tval_"+name+" = \"\"+obj.get"+nameMayus+"();");
                        oncreate.add("\t\ttv"+i+".setText(val_"+name+");");
                    }
                    break;
                case "edition":
                    if(type.equals("file")){
                        oncreate.add("\t\timgv"+i+"=(ImageButton) findViewById(R.id.value"+i+");");
                        oncreate.add("\t\tval_"+name+" = obj.get"+nameMayus+"();");
                        oncreate.add("\t\timgv"+i+".setImageURI(Uri.parse(val_"+name+"));");
                    }else{
                        oncreate.add("\t\ttv"+i+"=(EditText) findViewById(R.id.value"+i+");");
                        oncreate.add("\t\tval_"+name+" = \"\"+obj.get"+nameMayus+"();");
                        oncreate.add("\t\ttv"+i+".setText(val_"+name+");");
                    }
                    break;
            }
        }

        oncreate.add("");
        oncreate.add("\t\tcollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);");
        oncreate.add(String.format("\t\tcollapsingToolbarLayout.setTitle(\"%s\");",classname));
        oncreate.add("\t\tcollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));");
        oncreate.add("\t}");

        ArrayList<String> onresume=new ArrayList<>();
        onresume.add("\t@Override");
        onresume.add("\tpublic void onResume() {");
        onresume.add("\t\tsuper.onResume();");
        onresume.add("\t\tval_header= obj.getIcon();");
        onresume.add("\t\tif(val_header!=null){header.setImageURI(Uri.parse(val_header));}");
        for(int i=0;i<attrib.size();i++) {
            String type = attrib.get(i).split("\\.")[1];
            String name = attrib.get(i).split("\\.")[0];
            String nameMayus=name.substring(0,1).toUpperCase()+name.substring(1);
            onresume.add("");
            if(type.equals("file")){
                onresume.add("\t\tval_"+name+" = obj.get"+nameMayus+"();");
                onresume.add("\t\timgv"+i+".setImageURI(Uri.parse(val_"+name+"));");
            }else{
                onresume.add("\t\tval_"+name+" = \"\"+obj.get"+nameMayus+"();");
                onresume.add("\t\ttv"+i+".setText(val_"+name+");");
            }

        }
        onresume.add("\t}");


        ArrayList<String> fab=new ArrayList<>();
        fab.add("\tpublic void FAB_Click(View v){");
        switch(Type){
            case "view":
                fab.add("\t\tObject inf=dat.getOb();");
                fab.add("\t\tString class_object=\"\"+inf.getClass();");
                fab.add("\t\tint piv=class_object.split(\"\\\\.\").length;");
                fab.add("\t\tclass_object=class_object.split(\"\\\\.\")[piv-1];");
                fab.add("\t\tIntent i;");
                fab.add("\t\tString c_name=\""+Pack+".\"+class_object+\"_edition\";");
                fab.add("\t\tClass<?> c = null;");
                fab.add("\t\ttry {");
                fab.add("\t\t\tc = Class.forName(c_name);");
                fab.add("\t\t} catch (ClassNotFoundException e) {");
                fab.add("\t\t\te.printStackTrace();}");
                fab.add("\t\ti = new Intent(this,c);");
                fab.add("\t\tstartActivity(i);");
                break;
            case "edition":
                fab.add("\t\tthis.finish();");
                break;
        }
        fab.add("\t}");

        ArrayList<String> oncreateoptionsmenu=new ArrayList<>();
        oncreateoptionsmenu.add("\t@Override");
        oncreateoptionsmenu.add("\tpublic boolean onCreateOptionsMenu(Menu menu) {");
        oncreateoptionsmenu.add(String.format("\t\tgetMenuInflater().inflate(R.menu.menu_%s, menu);",classname));
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

        ArrayList<String> onclicks = new ArrayList<>();
        if(Type.equals("edition")){
            onclicks.add("\tpublic void onClick(View v){");
            onclicks.add("\t\tswitch(v.getId()){");
            for(int i=0;i<attrib.size();i++) {
                String type = attrib.get(i).split("\\.")[1];
                String name = attrib.get(i).split("\\.")[0];
                String nameMayus=name.substring(0,1).toUpperCase()+name.substring(1);
                if(type.equals("file")){
                    onclicks.add("\t\t\tcase R.id.value"+i+":");
                    onclicks.add("\t\t\t\tactual_id = v.getId();");
                    onclicks.add("\t\t\t\timagenEdit((ImageButton) findViewById(v.getId()));");
                    onclicks.add("\t\t\tbreak;");
                }
            }

            onclicks.add("\t\t}");
            onclicks.add("\t}");
            onclicks.add("");
        }

        ArrayList<String> uri = new ArrayList<>();
        uri.add("\tpublic Uri getImageUri(Context inContext, Bitmap inImage) {");
        uri.add("\t\tByteArrayOutputStream bytes = new ByteArrayOutputStream();");
        uri.add("\t\tinImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);");
        uri.add("\t\tString path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, \"Title\", null);");
        uri.add("\t\treturn Uri.parse(path);");
        uri.add("\t}");

        ArrayList<String> imageEdit= new ArrayList<>();
        imageEdit.add("\tpublic void imagenEdit(final ImageView ivw){");
        imageEdit.add("\t\tAlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);");
        imageEdit.add("\t\talertDialogBuilder.setTitle(\"Imagen\");");
        imageEdit.add("\t\talertDialogBuilder.setMessage(\"Selección de Imagen\");");
        imageEdit.add("\t\talertDialogBuilder.setPositiveButton(\"Tomar una foto\", new DialogInterface.OnClickListener() {");
        imageEdit.add("\t\t\tpublic void onClick(DialogInterface dialogo1, int id) {");
        imageEdit.add("\t\t\t\tivw.setImageBitmap(null);");
        imageEdit.add("\t\t\t\tivw.destroyDrawingCache();");
        imageEdit.add("\t\t\t\tIntent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);");
        imageEdit.add("\t\t\t\tstartActivityForResult(cameraIntent, 0);");
        imageEdit.add("\t\t\t}");
        imageEdit.add("\t\t});");
        imageEdit.add("\t\talertDialogBuilder.setNegativeButton(\"Buscar en galeria\", new DialogInterface.OnClickListener() {");
        imageEdit.add("\t\t\tpublic void onClick(DialogInterface dialogo1, int id) {");
        imageEdit.add("\t\t\t\tivw.setImageBitmap(null);");
        imageEdit.add("\t\t\t\tivw.destroyDrawingCache();");
        imageEdit.add("\t\t\t\tIntent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);");
        imageEdit.add("\t\t\t\tstartActivityForResult(galleryIntent, 1);");
        imageEdit.add("\t\t\t}");
        imageEdit.add("\t\t});");
        imageEdit.add("\t\talertDialogBuilder.show();");
        imageEdit.add("\t}");

        ArrayList<String> activityResult=new ArrayList<>();
        activityResult.add("\t@Override");
        activityResult.add("\tprotected void onActivityResult(int requestCode, int resultCode, Intent data) {");
        activityResult.add("\t\tsuper.onActivityResult(requestCode, resultCode, data);");
        activityResult.add("\t\tImageView imgView=(ImageView) findViewById(actual_id);");
        activityResult.add("\t\tString imgDecodableString;");
        activityResult.add("\t\ttry {");
        activityResult.add("\t\t\tif (requestCode == 1 && resultCode == RESULT_OK && null != data) {");
        activityResult.add("\t\t\t\tUri selectedImage = data.getData();");
        activityResult.add("\t\t\t\tString[] filePathColumn = { MediaStore.Images.Media.DATA };");
        activityResult.add("\t\t\t\tCursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);");
        activityResult.add("\t\t\t\tcursor.moveToFirst();");
        activityResult.add("\t\t\t\tint columnIndex = cursor.getColumnIndex(filePathColumn[0]);");
        activityResult.add("\t\t\t\timgDecodableString = cursor.getString(columnIndex);");
        activityResult.add("\t\t\t\tcursor.close();");
        activityResult.add("\t\t\t\timgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));");
        activityResult.add("\t\t\t} else {");
        activityResult.add("\t\t\t\tif (requestCode == 0 && resultCode == RESULT_OK) {");
        activityResult.add("\t\t\t\t\tBitmap photo = (Bitmap) data.getExtras().get(\"data\");");
        activityResult.add("\t\t\t\t\timgView.setImageBitmap(photo);");
        activityResult.add("\t\t\t\t}else{");
        activityResult.add("\t\t\t\t\tToast.makeText(this, \"You haven't picked Image\", Toast.LENGTH_LONG).show();");
        activityResult.add("\t\t\t\t}");
        activityResult.add("\t\t\t}");
        activityResult.add("\t\t} catch (Exception e) {");
        activityResult.add("\t\t\tToast.makeText(this, \"Something went wrong\", Toast.LENGTH_LONG).show();");
        activityResult.add("\t\t}");
        activityResult.add("\t}");


        ArrayList<String> SAVE = new ArrayList<>();
        if(Type.equals("edition")){
            SAVE.add("\tpublic void SAVEALL(View v){");
            for(int i=0; i<attrib.size();i++){
                String type = attrib.get(i).split("\\.")[1];
                String name = attrib.get(i).split("\\.")[0];
                String nameMayus=name.substring(0,1).toUpperCase()+name.substring(1);

                switch (type){
                    case "string":
                        SAVE.add("\t\tobj.set"+nameMayus+"(tv"+i+".getText().toString());");
                        break;
                    case "file":
                        SAVE.add("\t\tif(imgv"+i+".getDrawable()!=null){");
                        SAVE.add("\t\t\tobj.set"+nameMayus+"((getImageUri(this,((BitmapDrawable) imgv"+i+".getDrawable()).getBitmap())).toString());");
                        SAVE.add("\t\t}");
                        break;
                    case "int":
                        SAVE.add("\t\tobj.set"+nameMayus+"(Integer.parseInt(tv"+i+".getText().toString()));");
                        break;
                }
            }
            SAVE.add("\t\tDe.Arraytest.get(index).setOb(obj);");
            SAVE.add("\t\tDe.Arraytest.get(index).setRow(new Row(obj.getFirst(),obj.getSecond(),obj.getThird(),obj.getIcon()));");
            SAVE.add("\t}");
        }

        ArrayList<String> Shared=new ArrayList<>();
        Shared.add("\tprivate int loadSavedPreferences() {");
        Shared.add("\t\tSharedPreferences sharedPreferences = getSharedPreferences(\"MisPreferencias\", Context.MODE_PRIVATE);");
        Shared.add("\t\tint number = sharedPreferences.getInt(\"INDEX\",0);");
        Shared.add("\t\treturn number;");
        Shared.add("\t}");

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
        if(Type.equals("view")){
            for(String st : onresume){
                output.add(st);
            }
            output.add("");
        }
        for(String st : fab){
            output.add(st);
        }
        output.add("");
        for(String st : oncreateoptionsmenu){
            output.add(st);
        }
        output.add("");
        for(String st : Shared){
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
            for(String st : uri){
                output.add(st);
            }
            output.add("");
            for(String st : onclicks){
                output.add(st);
            }
            output.add("");
            for(String st : imageEdit){
                output.add(st);
            }
            output.add("");
            for(String st : activityResult){
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
