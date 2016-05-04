package proyectofinal.codegenerator;

import CodeGeneration.ClassGeneration;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Necho on 15/04/2016.
 */
public class Command {

    public static void Link(String class1, String class2){
        JOptionPane.showMessageDialog(null, "Creando link entre clases "+class1+" y "+class2+".");
    }

    public static ArrayList<ClassGeneration> Delete(ArrayList<ClassGeneration> classes,String class1,String DirSrc){

        int piv=0;
        for(int i=0;i<classes.size();i++){
            String test=classes.get(i).getClassname();
            if(class1.equals(test)){
                piv=i;
            }
        }
        classes.remove(piv);

        try{
            File f=new File(DirSrc+"\\"+class1+".java");
            boolean success =f.delete();
            if (success) {
                System.out.println("The file has been successfully deleted");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return classes;
    }

    public static ClassGeneration Add(ClassGeneration cg, String key, String attr,String DirSrc,String pack){
        ArrayList<String> attribute=cg.getAttribute();
        ArrayList<String> type=cg.getType();
        ArrayList<Boolean> view=cg.getView();
        ArrayList<Boolean> edition=cg.getEdition();

        attribute.add(attr.split("\\.")[0]);
        type.add(attr.split("\\.")[1]);

        if(key.contains("1")){
            cg.setFirst(attr.split("\\.")[0]);
        }

        if(key.contains("2")){
            cg.setSecond(attr.split("\\.")[0]);
        }

        if(key.contains("3")){
            cg.setThird(attr.split("\\.")[0]);
        }

        if(key.contains("c")){
            cg.setCollapse(attr.split("\\.")[0]);
        }

        if(key.contains("v")){
            view.add(true);
        }else{
            view.add(false);
        }

        if(key.contains("f")){
            edition.add(true);
        }else{
            edition.add(false);
        }

        cg.setAttribute(attribute);
        cg.setType(type);
        cg.setView(view);
        cg.setEdition(edition);


        ClassGeneration.createClass(cg,DirSrc,pack);
        return cg;
    }

    public static ClassGeneration Remove(ClassGeneration cg, String attr, String DirSrc,String pack){
        int piv=0;
        attr=attr.split("\\.")[0];
        for(int i=0;i<cg.getAttribute().size();i++){
            if(cg.getAttribute().get(i).equals(attr)){
                piv=i;
            }
        }
        ArrayList<String> attribute=cg.getAttribute();
        ArrayList<String> type=cg.getType();
        ArrayList<Boolean> view=cg.getView();
        ArrayList<Boolean> edition=cg.getEdition();

        attribute.remove(attr);
        type.remove(piv);
        view.remove(piv);
        edition.remove(piv);

        cg.setAttribute(attribute);
        cg.setType(type);
        cg.setView(view);
        cg.setEdition(edition);

        ClassGeneration.createClass(cg,DirSrc,pack);
        return cg;
    }

    public static ClassGeneration Create(String command, ArrayList<String> attrib,String DirSrc,String DirRes,String pack){
        String[] command2=command.split(" ");

        //---Class flags
        boolean listable=false;
        boolean builder=false;

        String classname="";

        //---Attribute flags
        ArrayList<String> attribute=new ArrayList<>();
        ArrayList<String> type=new ArrayList<>();
        String first="";
        String second="";
        String third="";
        String collapse="";
        ArrayList<Boolean> view=new ArrayList<>();
        ArrayList<Boolean> edition=new ArrayList<>();

        switch(command2.length){
            case 2:
                classname=command2[1];
                break;
            case 3:
                String key=command2[1];
                classname=command2[2];
                if (key.contains("l")){
                    listable=true;
                }
                if (key.contains("b")){
                    builder=true;
                }
                break;
        }

        for(int i=0;i<attrib.size();i++){
            String test=attrib.get(i);
            String[] test2=test.split(" ");
            switch(test2.length){
                case 1:
                    attribute.add(""+test2[0].split("\\.")[0]);
                    type.add(""+test2[0].split("\\.")[1]);
                    view.add(false);
                    edition.add(false);

                    break;
                case 2:
                    attribute.add(""+test2[1].split("\\.")[0]);
                    type.add(""+test2[1].split("\\.")[1]);
                    String key=test2[0];

                    if(key.contains("1")){
                        first=test2[1].split("\\.")[0];
                    }

                    if(key.contains("2")){
                        second=test2[1].split("\\.")[0];
                    }

                    if(key.contains("3")){
                        third=test2[1].split("\\.")[0];
                    }

                    if(key.contains("c")){
                        collapse=test2[1].split("\\.")[0];
                    }

                    if(key.contains("v")){
                        view.add(true);
                    }else{
                        view.add(false);
                    }

                    if(key.contains("f")){
                        edition.add(true);
                    }else{
                        edition.add(false);
                    }
                    break;
            }
        }

        ClassGeneration CG=new ClassGeneration(listable,builder,classname,attribute,type,first,second,third,collapse,view,edition);
        ClassGeneration.createClass(CG,DirSrc,pack);
        return CG;
    }

    public static void CreateForm(String command){

    }

}
