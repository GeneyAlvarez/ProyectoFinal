package CodeGeneration;


import Utility.Resources;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Necho on 24/04/2016.
 */
public class ClassGeneration {
    //---Class flags
    boolean listable;
    boolean builder;
    boolean json;

    String classname;

    //---Attribute flags
    ArrayList<String> attribute;
    ArrayList<String> type;
    String first;
    String second;
    String third;
    String collapse;
    ArrayList<Boolean> view;
    ArrayList<Boolean> edition;

    public ClassGeneration(boolean listable, boolean builder,boolean json, String classname, ArrayList<String> attribute, ArrayList<String> type, String first, String second, String third, String collapse, ArrayList<Boolean> view, ArrayList<Boolean> edition) {
        this.listable = listable;
        this.builder = builder;
        this.json=json;
        this.classname = classname;
        this.attribute = attribute;
        this.type = type;
        this.first = first;
        this.second = second;
        this.third = third;
        this.collapse = collapse;
        this.view = view;
        this.edition = edition;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }

    public boolean isListable() {
        return listable;
    }

    public void setListable(boolean listable) {
        this.listable = listable;
    }

    public boolean isBuilder() {
        return builder;
    }

    public void setBuilder(boolean builder) {
        this.builder = builder;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public ArrayList<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(ArrayList<String> attribute) {
        this.attribute = attribute;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getCollapse() {
        return collapse;
    }

    public void setCollapse(String collapse) {
        this.collapse = collapse;
    }

    public ArrayList<Boolean> getView() {
        return view;
    }

    public void setView(ArrayList<Boolean> view) {
        this.view = view;
    }

    public ArrayList<Boolean> getEdition() {
        return edition;
    }

    public void setEdition(ArrayList<Boolean> edition) {
        this.edition = edition;
    }

    public static void createClass(ClassGeneration c, String dir, String pack){

        File f=new File(dir+"\\"+c.getClassname()+".java");
        ArrayList<String> test=new ArrayList<>();
        test.add(String.format("package %s;",pack));
        test.add("");
        test.add(String.format("import %s;","android.media.Image"));
        test.add("import org.json.JSONObject;");
        test.add("import org.json.JSONException;");
        test.add("");
        test.add(String.format("public class %s {",c.getClassname()));
        String constructor_atrib="";
        for(int i=0;i<c.getAttribute().size();i++){
            ArrayList<String> type=c.getType();
            ArrayList<String> att=c.getAttribute();
            String type2=type.get(i);
            if(type2.equals("string")||type2.equals("file")){
                type2="String";
            }

            if(i==0){
                constructor_atrib+=""+type2+" "+att.get(i);
            }else{
                constructor_atrib+=", "+type2+" "+att.get(i);
            }
            test.add(String.format("\t%s %s;",type2,att.get(i)));
        }
        test.add("");
        if(c.isBuilder()){
            test.add(String.format("\tpublic %s(%s) {", c.getClassname(),constructor_atrib));
            for(int i=0;i<c.getAttribute().size();i++){
                ArrayList<String> att=c.getAttribute();
                test.add(String.format("\t\tthis.%s = %s;",att.get(i),att.get(i)));
            }
            test.add("\t}");
        }
        test.add("");

        if(c.isJson()){
            test.add(String.format("\tpublic %s(%s) throws JSONException {", c.getClassname(),"String Json"));
            test.add("\t\tJSONObject json = new JSONObject(Json);");
            for(int i=0;i<c.getAttribute().size();i++){
                ArrayList<String> type=c.getType();
                ArrayList<String> att=c.getAttribute();
                String type2=type.get(i);
                if(type2.equals("string")||type2.equals("file")){
                    test.add(String.format("\t\tthis.%s = %s;",att.get(i),"json.getString(\""+att.get(i)+"\")"));
                }else{
                    test.add(String.format("\t\tthis.%s = %s;",att.get(i),"json.getInt(\""+att.get(i)+"\")"));
                }
            }
            test.add("\t}");
        }
        test.add("");

        test.add(String.format("\tpublic %s() {", c.getClassname()));
        String module="\"\"";
        String module2="0";
        for(int i=0;i<c.getAttribute().size();i++){
            ArrayList<String> type=c.getType();
            ArrayList<String> att=c.getAttribute();
            String type2=type.get(i);
            if(type2.equals("string")||type2.equals("file")){
                test.add(String.format("\t\tthis.%s = %s;",att.get(i),module));
            }else{
                test.add(String.format("\t\tthis.%s = %s;",att.get(i),module2));
            }
        }
        test.add("\t}");
        test.add("");
        String out;
            if(c.getFirst().equals("")){
                out="null";
            }else{
                out="\"\"+"+c.getFirst();
            }
        test.add(String.format("\tpublic String getFirst() {  return %s;  }",out));
        test.add("");
        if(c.getSecond().equals("")){
            out="null";
        }else{
            out="\"\"+"+c.getSecond();
        }
        test.add(String.format("\tpublic String getSecond() {  return %s;  }",out));
        test.add("");
        if(c.getThird().equals("")){
            out="null";
        }else{
            out="\"\"+"+c.getThird();
        }
        test.add(String.format("\tpublic String getThird() {  return %s;  }",out));
        test.add("");
        if(c.getCollapse().equals("")){
            out="null";
        }else{
            out="\"\"+"+c.getCollapse();
        }
        test.add(String.format("\tpublic String getIcon() {  return %s;  }",out));
        test.add("");
        //getters n setter
        for(int i=0;i<c.getAttribute().size();i++){
            String p_type=c.getType().get(i);
            String p_atrib=c.getAttribute().get(i);
            p_atrib=p_atrib.substring(0,1).toUpperCase()+p_atrib.substring(1);
            if(p_type.equals("string")||p_type.equals("file")){
                p_type="String";
            }
            test.add(String.format("\tpublic %s get%s() { return %s;  }",p_type,p_atrib,c.getAttribute().get(i)));
            test.add("");
            test.add(String.format("\tpublic void set%s(%s %s){  this.%s=%s;  }",p_atrib,p_type,c.getAttribute().get(i),c.getAttribute().get(i),c.getAttribute().get(i)));
            test.add("");
        }
        test.add("}");

        int tam=test.size();

        try {
            PrintWriter writer = new PrintWriter(f, "UTF-8");
            for(int i=0;i<tam;i++){
                writer.println(test.get(i));
            }
            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



}
