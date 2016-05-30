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

    public static void Start(String dirRes,String dirSrc, Project project,String pack,String Main, ArrayList<String> clases){
        final ErrorHandler errorHandler = new ErrorHandler();

        Menu_Generation.generate(dirRes.replace("layout","menu"),"formlist");
        xml(dirRes,errorHandler,project,pack);
        custom_row(dirRes,errorHandler,project);
        mainclass(dirSrc,Main,project,pack,clases);
        auxiliarclass(dirSrc,pack,project);
    }

    public static void xml(String dir,ErrorHandler errorHandler,Project project, String pack){
        createMainXML(dir,errorHandler,project);
        createFormListXML(dir,errorHandler,project,pack);
    }

    public static void createMainXML(String dir,ErrorHandler errorHandler,Project project){
        String LL="LinearLayout";
        ArrayList<String> LL_name=new ArrayList<>();              ArrayList<String> LL_atrib=new ArrayList<>();
        LL_name.add("xmlns:android");                             LL_atrib.add("http://schemas.android.com/apk/res/android");
        LL_name.add("xmlns:tools");                               LL_atrib.add("http://schemas.android.com/tools");
        LL_name.add("android:layout_width");                      LL_atrib.add("match_parent");
        LL_name.add("android:orientation");                       LL_atrib.add("vertical");
        LL_name.add("android:paddingLeft");                       LL_atrib.add("@dimen/activity_horizontal_margin");
        LL_name.add("android:paddingRight");                      LL_atrib.add("@dimen/activity_horizontal_margin");
        LL_name.add("android:paddingTop");                        LL_atrib.add("@dimen/activity_vertical_margin");
        LL_name.add("android:paddingBottom");                     LL_atrib.add("@dimen/activity_vertical_margin");
        LL_name.add("android:layout_height");                     LL_atrib.add("match_parent");
        LL_name.add("android:background");                        LL_atrib.add("@color/PrimaryLight");
        LL_name.add("xmlns:card_view");                           LL_atrib.add("http://schemas.android.com/apk/res-auto");
        LL_name.add("tools:context");                             LL_atrib.add(".MainActivity");

        String CV="android.support.v7.widget.CardView";
        ArrayList<String> CV_name=new ArrayList<>();              ArrayList<String> CV_atrib=new ArrayList<>();
        CV_name.add("android:id");                                CV_atrib.add("@+id/cv");
        CV_name.add("android:layout_height");                     CV_atrib.add("wrap_content");
        CV_name.add("android:layout_margin");                     CV_atrib.add("5dp");
        CV_name.add("android:layout_width");                      CV_atrib.add("match_parent");
        CV_name.add("card_view:cardCornerRadius");                CV_atrib.add("26dp");
        CV_name.add("card_view:contentPadding");                  CV_atrib.add("10dp");
        CV_name.add("card_view:cardElevation");                   CV_atrib.add("4dp");
        CV_name.add("card_view:cardBackgroundColor");             CV_atrib.add("#FFFFFF");

        String TV="TextView";
        ArrayList<String> TV_name=new ArrayList<>();              ArrayList<String> TV_atrib=new ArrayList<>();
        TV_name.add("android:id");                                TV_atrib.add("@+id/tv");
        TV_name.add("android:fontFamily");                        TV_atrib.add("sans-serif-condensed");
        TV_name.add("android:layout_width");                      TV_atrib.add("match_parent");
        TV_name.add("android:layout_height");                     TV_atrib.add("wrap_content");
        TV_name.add("android:textSize");                          TV_atrib.add("30dp");
        TV_name.add("android:textColor");                         TV_atrib.add("#000000");
        TV_name.add("android:text");                              TV_atrib.add("Select a class");

        String LV="ListView";
        ArrayList<String> LV_name=new ArrayList<>();              ArrayList<String> LV_atrib=new ArrayList<>();
        LV_name.add("android:textColor");                         LV_atrib.add("#ffffff");
        LV_name.add("android:layout_width");                      LV_atrib.add("match_parent");
        LV_name.add("android:layout_height");                     LV_atrib.add("match_parent");
        LV_name.add("android:id");                                LV_atrib.add("@+id/lv");
        LV_name.add("android:layout_below");                      LV_atrib.add("@+id/tv");
        LV_name.add("android:layout_marginTop");                  LV_atrib.add("40dp");
        LV_name.add("android:textSize");                          LV_atrib.add("20dp");
        LV_name.add("android:layout_centerHorizontal");           LV_atrib.add("true");

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement(LL);
            for(int i=0;i<LL_name.size();i++){
                element.setAttribute(LL_name.get(i), LL_atrib.get(i));
            }
            document.appendChild(element);

            Element element2 = document.createElement(CV);
            for(int i=0;i<CV_name.size();i++){
                element2.setAttribute(CV_name.get(i), CV_atrib.get(i));
            }
            element.appendChild(element2);

            Element element3 = document.createElement(TV);
            for(int i=0;i<TV_name.size();i++){
                element3.setAttribute(TV_name.get(i), TV_atrib.get(i));
            }
            element2.appendChild(element3);

            Element element4 = document.createElement(LV);
            for(int i=0;i<LV_name.size();i++){
                element4.setAttribute(LV_name.get(i), LV_atrib.get(i));
            }
            element2.appendChild(element4);

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

    public static void createFormListXML(String dir,ErrorHandler errorHandler,Project project, String Package){
        String RL="RelativeLayout";
        ArrayList<String> RL_name=new ArrayList<>();              ArrayList<String> RL_atrib=new ArrayList<>();
        RL_name.add("xmlns:app");                                 RL_atrib.add("http://schemas.android.com/apk/res-auto");
        RL_name.add("xmlns:android");                             RL_atrib.add("http://schemas.android.com/apk/res/android");
        RL_name.add("xmlns:tools");                               RL_atrib.add("http://schemas.android.com/tools");
        RL_name.add("android:background");                        RL_atrib.add("@color/PrimaryLight");
        RL_name.add("android:layout_height");                     RL_atrib.add("match_parent");
        RL_name.add("android:layout_width");                      RL_atrib.add("match_parent");
        RL_name.add("android:paddingBottom");                     RL_atrib.add("@dimen/activity_vertical_margin");
        RL_name.add("android:paddingLeft");                       RL_atrib.add("@dimen/activity_horizontal_margin");
        RL_name.add("android:paddingRight");                      RL_atrib.add("@dimen/activity_horizontal_margin");
        RL_name.add("android:paddingTop");                        RL_atrib.add("@dimen/activity_vertical_margin");
        RL_name.add("tools:context");                             RL_atrib.add(Package+".formlist");

        String RV="android.support.v7.widget.RecyclerView";
        ArrayList<String> RV_name=new ArrayList<>();              ArrayList<String> RV_atrib=new ArrayList<>();
        RV_name.add("android:id");                                RV_atrib.add("@+id/recycle");
        RV_name.add("android:layout_height");                     RV_atrib.add("wrap_content");
        RV_name.add("android:layout_width");                      RV_atrib.add("match_parent");

        String FAB="android.support.design.widget.FloatingActionButton";
        ArrayList<String> FAB_name=new ArrayList<>();              ArrayList<String> FAB_atrib=new ArrayList<>();
        FAB_name.add("android:clickable");                              FAB_atrib.add("true");
        FAB_name.add("android:id");                                     FAB_atrib.add("@+id/fab");
        FAB_name.add("android:layout_height");                          FAB_atrib.add("wrap_content");
        FAB_name.add("android:layout_width");                           FAB_atrib.add("wrap_content");
        FAB_name.add("android:onClick");                                FAB_atrib.add("FAB_Click");
        FAB_name.add("app:elevation");                                  FAB_atrib.add("6dp");
        FAB_name.add("app:layout_anchorGravity");                       FAB_atrib.add("bottom|right|end");
        FAB_name.add("app:rippleColor");                                FAB_atrib.add("@android:color/white");
        FAB_name.add("android:layout_alignParentBottom");               FAB_atrib.add("true");
        FAB_name.add("android:layout_alignRight");                      FAB_atrib.add("@+id/recycle");
        FAB_name.add("android:layout_alignEnd");                        FAB_atrib.add("@+id/recycle");


        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element element = document.createElement(RL);
            for(int i=0;i<RL_name.size();i++){
                element.setAttribute(RL_name.get(i), RL_atrib.get(i));
            }
            document.appendChild(element);

            Element element2 = document.createElement(RV);
            for(int i=0;i<RV_name.size();i++){
                element2.setAttribute(RV_name.get(i), RV_atrib.get(i));
            }
            element.appendChild(element2);

            Element element3 = document.createElement(FAB);
            for(int i=0;i<FAB_name.size();i++){
                element3.setAttribute(FAB_name.get(i), FAB_atrib.get(i));
            }
            element.appendChild(element3);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            Result output = new StreamResult(new File(dir+"activity_formlist.xml"));
            Source input = new DOMSource(document);
            transformer.transform(input, output);

        }catch(Exception e) {
            errorHandler.handleError(project, e);
        }
    }

    public static void custom_row(String dir,ErrorHandler errorHandler,Project project){

        String card="android.support.v7.widget.CardView";

        ArrayList<String> rootname=new ArrayList<>();       ArrayList<String> rootattrib=new ArrayList<>();
        rootname.add("xmlns:android");                      rootattrib.add("http://schemas.android.com/apk/res/android");
        rootname.add("android:layout_height");              rootattrib.add("match_parent");
        rootname.add("android:layout_width");               rootattrib.add("match_parent");
        rootname.add("android:orientation");                rootattrib.add("vertical");
        rootname.add("xmlns:card_view");                    rootattrib.add("http://schemas.android.com/apk/res-auto");
        rootname.add("android:padding");                    rootattrib.add("8dp");


        ArrayList<String> cardname=new ArrayList<>();       ArrayList<String> cardattrib=new ArrayList<>();
        cardname.add("android:id");                         cardattrib.add("@+id/cv");
        cardname.add("android:layout_height");              cardattrib.add("wrap_content");
        cardname.add("card_view:cardCornerRadius");         cardattrib.add("15dp");
        cardname.add("android:layout_width");               cardattrib.add("match_parent");
        cardname.add("card_view:cardElevation");            cardattrib.add("10dp");
        cardname.add("card_view:cardBackgroundColor");      cardattrib.add("#FFFFFF");



        ArrayList<String> layoutname=new ArrayList<>();        ArrayList<String> layoutattrib=new ArrayList<>();
        layoutname.add("android:elevation");                   layoutattrib.add("20dp");
        layoutname.add("android:focusableInTouchMode");        layoutattrib.add("false");
        layoutname.add("android:layout_height");               layoutattrib.add("wrap_content");
        layoutname.add("android:layout_width");                layoutattrib.add("match_parent");


        //Contenido del cardview-------------
        ArrayList<String> ImgName=new ArrayList<>();ArrayList<String> ImgValue=new ArrayList<>();
        ImgName.add("android:layout_width");ImgValue.add("100dp");
        ImgName.add("android:layout_height");ImgValue.add("100dp");
        ImgName.add("android:id");ImgValue.add("@+id/img");
        ImgName.add("android:layout_alignParentLeft");ImgValue.add("true");
        ImgName.add("android:layout_alignParentTop");ImgValue.add("true");
        ImgName.add("android:layout_marginRight");ImgValue.add("10dp");
        ImgName.add("android:scaleType");ImgValue.add("centerCrop");

        //-----------------
        ArrayList<String> TextName=new ArrayList<>();
        ArrayList<String> TextValue=new ArrayList<>();

        TextName.add("android:id");TextValue.add("@+id/texto1");
        TextName.add("android:layout_width");TextValue.add("wrap_content");
        TextName.add("android:layout_height");TextValue.add("wrap_content");
        TextName.add("android:layout_toRightOf");TextValue.add("@+id/img");
        TextName.add("android:layout_alignParentTop");TextValue.add("true");
        TextName.add("android:textSize");TextValue.add("20sp");


        //-----------------
        ArrayList<String> Text2Name=new ArrayList<>();
        ArrayList<String> Text2Value=new ArrayList<>();

        Text2Name.add("android:id");Text2Value.add("@+id/texto2");
        Text2Name.add("android:layout_width");Text2Value.add("wrap_content");
        Text2Name.add("android:layout_height");Text2Value.add("wrap_content");
        Text2Name.add("android:layout_below");Text2Value.add("@+id/texto1");
        Text2Name.add("android:layout_toRightOf");Text2Value.add("@+id/img");
        Text2Name.add("android:textSize");Text2Value.add("12sp");


        //-----------------
        ArrayList<String> Text3Name=new ArrayList<>();
        ArrayList<String> Text3Value=new ArrayList<>();

        Text3Name.add("android:layout_width");Text3Value.add("wrap_content");
        Text3Name.add("android:layout_height");Text3Value.add("wrap_content");
        Text3Name.add("android:id");Text3Value.add("@+id/texto3");
        Text3Name.add("android:textSize");Text3Value.add("12sp");
        Text3Name.add("android:layout_toRightOf");Text3Value.add("@+id/img");
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

    public static void mainclass(String dir,String MainLocation,Project project,String Package,ArrayList<String> clases){
        createMainCLASS(dir,MainLocation,project,Package,clases);
        createFromCLASS(dir,MainLocation,project,Package,clases);
    }

    public static void createMainCLASS(String dir,String MainLocation,Project project,String Package,ArrayList<String> clases){
        ArrayList<String> output=new ArrayList<>();

        String arraycito="";
        for(int i=0;i<clases.size();i++){
            if(i==0){
                arraycito+="\""+clases.get(i)+"\"";
            }else{
                arraycito+=",\""+clases.get(i)+"\"";
            }
        }

        output.add("package "+Package+";");
        output.add("");
        output.add("import android.content.Intent;");
        output.add("import android.support.v7.app.AppCompatActivity;");
        output.add("import android.os.Bundle;");
        output.add("import android.view.Menu;");
        output.add("import android.view.MenuItem;");
        output.add("import android.view.View;");
        output.add("import android.widget.AdapterView;");
        output.add("import android.widget.ArrayAdapter;");
        output.add("import android.widget.ListView;");
        output.add("");
        output.add("public class MainActivity extends AppCompatActivity {");

        output.add("\tListView listView;");
        output.add("");

        output.add("\t@Override");
        output.add("\tprotected void onCreate(Bundle savedInstanceState) {");
        output.add("\t\tsuper.onCreate(savedInstanceState);");
        output.add("\t\tsetContentView(R.layout.activity_main);");
        output.add("\t\tlistView = (ListView) findViewById(R.id.lv);");
        output.add("\t\tString[] values = new String[] { "+arraycito+" };");
        output.add("\t\tArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);");
        output.add("\t\tlistView.setAdapter(adapter);");
        output.add("\t\tlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {");
        output.add("\t\t\t@Override");
        output.add("\t\t\tpublic void onItemClick(AdapterView<?> parent, View view, int position, long id) {");
        output.add("\t\t\t\tString  content  = (String) listView.getItemAtPosition(position);");
        output.add("\t\t\t\tIntent intent = new Intent(MainActivity.this, formlist.class);");
        output.add("\t\t\t\tintent.putExtra(\"CLASSNAMESELECTED\", content);");
        output.add("\t\t\t\tstartActivity(intent);}");
        output.add("\t\t});");
        output.add("\t}");

        output.add("");
        output.add("\t@Override");
        output.add("\tpublic boolean onOptionsItemSelected(MenuItem item) {");
        output.add("\t\tint id = item.getItemId();");
        output.add("\t\tif (id == R.id.action_settings) {");
        output.add("\t\t\treturn true;}");
        output.add("\t\treturn super.onOptionsItemSelected(item);");
        output.add("\t}");

        output.add("");
        output.add("\t@Override");
        output.add("\tpublic boolean onCreateOptionsMenu(Menu menu) {");
        output.add("\t\tgetMenuInflater().inflate(R.menu.menu_main, menu);");
        output.add("\t\treturn true;");
        output.add("\t}");

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

    public static void createFromCLASS(String dir,String MainLocation,Project project,String Package,ArrayList<String> clases){
        ArrayList<String> output=new ArrayList<>();

        String pack="package "+Package+";";

        ArrayList<String> imports=new ArrayList<>();
        imports.add("import android.support.v7.app.AppCompatActivity;");
        imports.add("import android.os.Bundle;");
        imports.add("import android.content.SharedPreferences;");
        imports.add("import android.content.SharedPreferences.Editor;");
        imports.add("import android.view.Menu;");
        imports.add("import java.util.ArrayList;");
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
        attributes.add("\tString classname;");

        ArrayList<String> oncreate=new ArrayList<>();
        oncreate.add("\t@Override");
        oncreate.add("\tprotected void onCreate(Bundle savedInstanceState) {");
        oncreate.add("\tsuper.onCreate(savedInstanceState);");
        oncreate.add("\tsetContentView(R.layout.activity_formlist);");
        oncreate.add("\tIntent startingIntent = getIntent();");
        oncreate.add("\tclassname = startingIntent.getStringExtra(\"CLASSNAMESELECTED\");");
        oncreate.add("\tDe.PULL(classname);");
        oncreate.add("\tmRecyclerView=(RecyclerView)findViewById(R.id.recycle);");
        oncreate.add("\tviewAdapter= new ViewAdapter(this);");
        oncreate.add("\tviewAdapter.setRecyclerClickListner(this);");
        oncreate.add("\tviewAdapter.setRecyclerLClickListner(this);");
        oncreate.add("\tmRecyclerView.setAdapter(viewAdapter);");
        oncreate.add("\tmRecyclerView.setLayoutManager(new LinearLayoutManager(this));");
        oncreate.add("\t}");

        ArrayList<String> subs=new ArrayList<>();
        subs.add("\t@Override");
        subs.add("\tpublic void onResume(){");
        subs.add("\t\tsuper.onResume();");
        subs.add("\t\tviewAdapter.notifyDataSetChanged();}");
        subs.add("");
        subs.add("\t@Override");
        subs.add("\tpublic void onDestroy() {");
        subs.add("\t\tsuper.onDestroy();");
        subs.add("\t\tDe.PUSH();}");
        subs.add("");
        subs.add("\t@Override");
        subs.add("\tpublic boolean onOptionsItemSelected(MenuItem item) {");
        subs.add("\t\tint id = item.getItemId();");
        subs.add("\t\tif (id == R.id.action_settings) {");
        subs.add("\t\t\treturn true;}");
        subs.add("\t\treturn super.onOptionsItemSelected(item);}");
        subs.add("");
        subs.add("\t@Override");
        subs.add("\tpublic boolean onCreateOptionsMenu(Menu menu) {");
        subs.add("\t\tgetMenuInflater().inflate(R.menu.menu_formlist, menu);");
        subs.add("\t\treturn true;}");
        subs.add("");
        subs.add("\t@Override");
        subs.add("\tpublic void itemClick(View view, int position) {");
        subs.add("\t\tsaltar(position);}");
        subs.add("");
        subs.add("\t@Override");
        subs.add("\tpublic void itemLClick(View view, int position) {");
        subs.add("\t\tsaltar2(position, view);}");

        ArrayList<String> onjump=new ArrayList<>();
        onjump.add("\tpublic void saltar(final int position){");
        onjump.add("\t\tData dat=De.Arraytest.get(position);");
        onjump.add("\t\tObject inf=dat.getOb();");
        onjump.add("\t\tString class_object=\"\"+inf.getClass();");
        onjump.add("\t\tint piv=class_object.split(\"\\\\.\").length;");
        onjump.add("\t\tclass_object=class_object.split(\"\\\\.\")[piv-1];");
        onjump.add("\t\tIntent i;");
        onjump.add("\t\tString c_name=\""+Package+".\"+class_object+\"_view\";");
        onjump.add("\t\tClass<?> c = null;");
        onjump.add("\t\ttry {");
        onjump.add("\t\t\tc = Class.forName(c_name);");
        onjump.add("\t\t} catch (ClassNotFoundException e) {");
        onjump.add("\t\t\te.printStackTrace();}");
        onjump.add("\t\ti = new Intent(context,c);");
        onjump.add("\t\tsavePreferences(\"INDEX\",position);");
        onjump.add("\t\tstartActivity(i);");
        onjump.add("\t}");
        onjump.add("");
        onjump.add("\tpublic void saltar2(final int position, View view){");
        onjump.add("\t\tAlertDialog.Builder builder = new AlertDialog.Builder(this);");
        onjump.add("\t\tbuilder.setTitle(\"Select Action\");");
        onjump.add("\t\tbuilder.setItems(new CharSequence[] {\"Edit\", \"Remove\", \"Cancel\"},");
        onjump.add("\t\t\tnew DialogInterface.OnClickListener() {");
        onjump.add("\t\t\t\tpublic void onClick(DialogInterface dialog, int which) {");
        onjump.add("\t\t\t\t\tswitch (which) {");
        onjump.add("\t\t\t\t\t\tcase 0:");
        onjump.add("\t\t\t\t\t\t\t\tData dat = De.Arraytest.get(position);");
        onjump.add("\t\t\t\t\t\t\t\tObject inf = dat.getOb();");
        onjump.add("\t\t\t\t\t\t\t\tString class_object = \"\" + inf.getClass();");
        onjump.add("\t\t\t\t\t\t\t\tint piv = class_object.split(\"\\\\.\").length;");
        onjump.add("\t\t\t\t\t\t\t\tclass_object = class_object.split(\"\\\\.\")[piv - 1];");
        onjump.add("\t\t\t\t\t\t\t\tIntent i;");
        onjump.add("\t\t\t\t\t\t\t\tString c_name = \""+Package+".\" + class_object + \"_edition\";");
        onjump.add("\t\t\t\t\t\t\t\tClass<?> c = null;");
        onjump.add("\t\t\t\t\t\t\t\ttry {");
        onjump.add("\t\t\t\t\t\t\t\t\tc = Class.forName(c_name);");
        onjump.add("\t\t\t\t\t\t\t\t} catch (ClassNotFoundException e) {");
        onjump.add("\t\t\t\t\t\t\t\t\te.printStackTrace();");
        onjump.add("\t\t\t\t\t\t\t\t}");
        onjump.add("\t\t\t\t\t\t\t\ti = new Intent(context, c);");
        onjump.add("\t\t\t\t\t\t\t\tsavePreferences(\"INDEX\", position);");
        onjump.add("\t\t\t\t\t\t\t\tstartActivity(i);");
        onjump.add("\t\t\t\t\t\t\t\tbreak;");
        onjump.add("\t\t\t\t\t\tcase 1:");
        onjump.add("\t\t\t\t\t\t\tDe.Arraytest.remove(position);");
        onjump.add("\t\t\t\t\t\t\tmRecyclerView.setAdapter(viewAdapter);");
        onjump.add("\t\t\t\t\t\t\tmRecyclerView.setLayoutManager(new LinearLayoutManager(context));");
        onjump.add("\t\t\t\t\t\t\tbreak;");
        onjump.add("\t\t\t\t\t\tcase 2:");
        onjump.add("\t\t\t\t\t\t\tbreak;");
        onjump.add("\t\t\t\t\t}");
        onjump.add("\t\t\t\t}");
        onjump.add("\t\t\t});");
        onjump.add("\t\tbuilder.create().show();");
        onjump.add("\t}");

        ArrayList<String> Share=new ArrayList<>();
        Share.add("\tprivate void savePreferences(String key, int value) {");
        Share.add("\t\tSharedPreferences sharedPreferences = getSharedPreferences(\"MisPreferencias\",Context.MODE_PRIVATE);");
        Share.add("\t\tEditor editor = sharedPreferences.edit();");
        Share.add("\t\teditor.putInt(key, value);");
        Share.add("\t\teditor.commit();");
        Share.add("\t}");

        ArrayList<String> FAB=new ArrayList<>();
        FAB.add("\tpublic void FAB_Click(View v){");
        FAB.add("\t\tswitch (classname) {");
        for(int i=0;i<clases.size();i++){
            FAB.add("\t\t\tcase \""+clases.get(i)+"\":");
            FAB.add("\t\t\t\t"+clases.get(i)+" info"+i+" = new "+clases.get(i)+"();");
            FAB.add("\t\t\t\tRow row"+i+" new Row(info"+i+".getFirst(), info"+i+".getSecond(), info"+i+".getThird(), info"+i+".getIcon();");
            FAB.add("\t\t\t\tData dat"+i+" = new Data(info"+i+", row"+i+");");
            FAB.add("\t\t\t\tDe.Arraytest.add(dat"+i+")");
            FAB.add("\t\t\t\tbreak;");
        }
        FAB.add("\t\t}");
        FAB.add("\t\tviewAdapter.notifyDataSetChanged();");
        FAB.add("\t}");

//------------
        output.add(pack);
        output.add("");
        for(String st : imports){
            output.add(st);
        }
        output.add("");
        output.add("public class formlist extends AppCompatActivity implements ViewAdapter.RecyclerClickListner, ViewAdapter.RecyclerLClickListner {");
        output.add("");

        for(String st : attributes){
            output.add(st);
        }
        output.add("");
        for(String st : oncreate){
            output.add(st);
        }

        for(String st :subs){
            output.add(st);
        }
        output.add("");
        for(String st : onjump){
            output.add(st);
        }

        for(String st : Share){
            output.add(st);
        }
        output.add("");
        for(String st : FAB){
            output.add(st);
        }
        output.add("}");

        File f=new File(dir+"\\"+"formlist.java");
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
            File check=new File(path+"\\AndroidManifest.xml");
            BufferedReader br = new BufferedReader(new FileReader(check));

            File temp=File.createTempFile("check", ".txt", check.getParentFile());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp)));

            try {
                String line = br.readLine();
                while (line != null) {
                    if(line.contains(String.format("<activity android:label=\"%s_edition\" android:name=\".%s_edition\"/>",activity_name,activity_name))){
                        line="";
                    }
                    if(line.contains(String.format("<activity android:label=\"%s_view\" android:name=\".%s_view\"/>",activity_name,activity_name))){
                        line="";
                    }

                    writer.println(line);
                    line = br.readLine();
                }
            } finally {
                br.close();
                writer.close();
            }

            check.delete();
            temp.renameTo(check);

        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

}
