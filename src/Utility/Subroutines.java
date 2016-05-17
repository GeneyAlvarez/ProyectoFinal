package Utility;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Necho on 15/04/2016.
 */
public class Subroutines {

    public static boolean isAction(String string) {
        Pattern p = Pattern.compile("(json|create|edit|add|remove|delete|exit)");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    public static boolean isVariable(String string){
        Pattern c=Pattern.compile("[a-z]([a-z]|[0-9])+");//vble
        Matcher test=c.matcher(string);
        return test.matches();
    }

    public static boolean isKey(String string){
        Pattern c=Pattern.compile("\\-(l)?(b)?(j)?(1|2|3)?(c)?(v)?(f)?");//key
        Matcher test=c.matcher(string);
        return test.matches();
    }

    public static boolean isAtribute(String string){
        Pattern c=Pattern.compile("[a-z]([a-z]|[0-9])+\\.(string|int|float|file)");//atrib
        Matcher test=c.matcher(string);
        return test.matches();
    }

    public static String RemoveSpaces(String command){
        while(command.startsWith(" ")){
            command=command.substring(1);
        }

        while(command.endsWith(" ")){
            int t=command.length();
            command=command.substring(0, t-1);
        }

        char[] test2=command.toCharArray();
        boolean sw=true;
        String result="";

        for(int i=0;i<test2.length;i++){
            char p=test2[i];
            if(p!=' '){
                if(!sw){
                    sw=true;
                }
                result+=p;
            }else{
                if(sw){
                    result+=p;
                    sw=false;
                }
            }
        }

        return result;
    }

    public static boolean Lex(String command){
        command=command.toLowerCase();
        String[] temp=command.split(" ");
        boolean sw=false;

        //chequea token por token
        for(int j=0;j<temp.length;j++){
            sw=false;
            boolean b = isAction(temp[j]);
            boolean d = isVariable(temp[j]);
            boolean z=isAtribute(temp[j]);
            boolean t=isKey(temp[j]);

            if(b||d||z||t){
                sw=true;
            }else{
                return sw;
            }
        }
        return sw;
    }

    public static ErrorConfirmation Sint(String command){
        command=command.toLowerCase();
        String[] temp=command.split(" ");
        String temp2=temp[0];//Tomo la primera palabra del comando

        switch(temp2){
            case "json":
                if(temp.length!=2){
                    return new ErrorConfirmation(false,"Error: invalid number of parameters.");
                }else{
                    if(!isVariable(temp[1])){
                        return new ErrorConfirmation(false,"Error: "+temp[1]+" is not a valid class name.");
                    }
                }
                break;
            case "create":
                String test=temp[1];
                switch(temp.length){
                    case 2:
                        if(!isVariable(test)){
                            return new ErrorConfirmation(false,"Error: "+test+" is not a valid class name.");
                        }
                        break;
                    case 3:
                        if(!(isKey(test) && isVariable(temp[2]))){
                            if(!isKey(test)){
                                return new ErrorConfirmation(false,"Error: "+test+" is not a valid key.");
                            }
                            if(!isVariable(temp[2])){
                                return new ErrorConfirmation(false,"Error: "+temp[2]+" is not a valid class name.");
                            }
                        }
                        break;
                    default:
                        return new ErrorConfirmation(false,"Error: invalid number of parameters.");
                }
                break;
            case "edit":
                String action=temp[2];
                if(action.equals("add") || action.equals("remove")){
                    if(action.equals("remove")){
                        if(!isAtribute(temp[3]) || temp.length!=4){
                            return new ErrorConfirmation(false,"Error: "+temp[3]+" is not a valid attribute.");
                        }
                    }else{
                        if(action.equals("add") || temp.length!=5){
                            if(isKey(temp[3])){
                                if(!isAtribute(temp[4])){
                                    return new ErrorConfirmation(false,"Error: "+temp[4]+" is not a valid attribute.");
                                }
                            }
                            else{
                                return new ErrorConfirmation(false,"Error: "+temp[3]+" is not a valid key.");
                            }
                        }
                    }
                }else{
                    return new ErrorConfirmation(false,"Error: "+action+" is not a valid action.");
                }
                break;
            case "exit":
                if(temp.length!=1){
                    return new ErrorConfirmation(false,"Error: invalid number of parameters");
                }
                break;
            case "delete":
                if(temp.length==2){
                    boolean t=isVariable(temp[1]);
                    if(!t){
                        return new ErrorConfirmation(false,"Error: "+temp[1]+" is not a valid class name.");
                    }
                }else{
                    return new ErrorConfirmation(false,"Error: invalid number of parameters");
                }
                break;
            default:
                return new ErrorConfirmation(false,"Error: "+temp2+" is not an action");
        }
        return new ErrorConfirmation(true,"Syntactical Analysis: Success");
    }

    public static ArrayList<String> FormGeneration(ArrayList<String> forms){
        ArrayList<String> result=new ArrayList<>();
        int i=0;

        for(String test : forms){
            result.add("\t"+test+" info"+i+" = new "+test+"();");
            result.add("\tRow row"+i+" = new Row(info"+i+".getFirst(),info"+i+".getSecond(),info"+i+".getThird(),info"+i+".getIcon());");
            result.add("\tData dat"+i+"= new Data(info"+i+",row"+i+");");
            result.add("\tArraytest.add(dat"+i+");");
            i++;
        }

        return result;
    }

    public static void PopUp(String src,Project p){
        ArrayList<String> menu_test=new ArrayList<>();
        menu_test.add("<menu xmlns:android=\"http://schemas.android.com/apk/res/android\" >");
        menu_test.add("<item");
        menu_test.add("\tandroid:id=\"@+id/one\"");
        menu_test.add("\tandroid:title=\"Edit\"/>");
        menu_test.add("<item");
        menu_test.add("\tandroid:id=\"@+id/two\"");
        menu_test.add("\tandroid:title=\"Delete\"/>");
        menu_test.add("<item");
        menu_test.add("\tandroid:id=\"@+id/three\"");
        menu_test.add("\tandroid:title=\"Cancel\"/>");
        menu_test.add("</menu>");

        File f=new File(src+"pop_up_menu.xml");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(f, "UTF-8");
            for (String anOutput : menu_test) {
                writer.println(anOutput);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
