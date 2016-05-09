package proyectofinal.codegenerator;

import CodeGeneration.*;


import Utility.ErrorConfirmation;
import Utility.Subroutines;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import org.w3c.dom.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import com.intellij.ui.JBColor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Necho on 14/04/2016.
 */
public class FirstGeneration extends AnAction {

    boolean t=true;
    String DirRes;
    String DirSrc;
    ArrayList<ClassGeneration> Classes=new ArrayList<>();
    ArrayList<String> Forms=new ArrayList<>();
    String MainLocation;
    String Package;
    Document ManifestLocation;
    String ManifestDirection;

    @Override
    public void actionPerformed(AnActionEvent e) {
        int sw=JOptionPane.showConfirmDialog(null,"The Framework will make changes in the xml mainActivity and generate necessary resources and classes.", "Message",JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if(sw==2){
            System.out.println("Exit");
            return;
        }

        //Debe existir un proyecto abierto.
        final Project project = e.getProject();
        VirtualFile vf;
        String ProjectName;
        String ProjectDir;

        if(project!=null){
            vf=project.getBaseDir();
            String test="";
            FindMain(vf,"MainActivity.java",test);
            getPack(MainLocation,"java");
            ProjectName=vf.getName();
            ProjectDir=vf.getCanonicalPath();
            DirRes=ProjectDir+"\\app\\src\\main\\res\\";
            ManifestLocation=FindManifest(project,vf,ProjectName,ProjectDir);

            System.out.println("src "+DirSrc);
            System.out.println("res "+DirRes);
            System.out.println("Main "+MainLocation);
            System.out.println("Pack "+Package);
        }else{
            return;
        }


        if(t){//Los siguientes procedimientos se ejecutaran solo la primera vez que la accion sea ejecutada
            colorsGeneration.Start(DirRes+"values\\",project);
            stylesGeneration.Start(DirRes+"values\\",project);
            mainGeneration.Start(DirRes+"layout\\",DirSrc,project,Package,MainLocation,Package);
            auxclassGeneration.start(DirSrc,project,Package,null);
            t=false;
        }

        JFrame frame = new JFrame();
        JLabel nameLabel= new JLabel();
        JLabel alertLabel= new JLabel();
        JTextField nameTextField = new JTextField();

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //alertLabel.setForeground(Color.RED);
        Font newLabelFont=new Font(alertLabel.getFont().getName(),Font.ITALIC,alertLabel.getFont().getSize());
        alertLabel.setFont(newLabelFont);

        frame.add(nameLabel,BorderLayout.NORTH);
        frame.add(alertLabel,BorderLayout.CENTER);
        nameLabel.setText("Enter your Command : ");
        frame.add(nameTextField, BorderLayout.SOUTH);//, BorderLayout.NORTH

        KeyListener keyListener = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                char ch = e.getKeyChar();
                int ascii = (int) ch;
                if(ascii==10){ //intro
                    String text=nameTextField.getText();
                    nameTextField.setText("");
                    text=Subroutines.RemoveSpaces(text);
                    if(Subroutines.Lex(text)){
                        ErrorConfirmation err=Subroutines.Sint(text);
                        if(err.isResult()){
                            alertLabel.setForeground(JBColor.GREEN);
                            alertLabel.setText(err.getMessage());
                            String[] test=text.split(" ");
                            switch(test[0]){
                                case "exit":
                                    frame.dispose();
                                    break;
                                case "create":
                                    switch(test.length){
                                        case 2:
                                            boolean sw=false;
                                            boolean sw3=false;
                                            for(ClassGeneration cg : Classes){
                                                if(cg.getClassname().equals(test[1])){
                                                    sw=true;
                                                    if(cg.isListable()){
                                                        sw3=true;
                                                    }
                                                }
                                            }
                                            if(!sw){
                                                Attributes(project,text,DirSrc,DirRes,Package);
                                            }else{
                                                if(sw3){
                                                    Forms.add(test[1]);
                                                    auxclassGeneration.start(DirSrc,project,Package,Subroutines.FormGeneration(Forms));
                                                    System.out.println(Forms.toString());
                                                }
                                            }
                                            break;
                                        case 3:
                                            boolean sw2=false;
                                            boolean sw4=false;
                                            for(ClassGeneration cg : Classes){
                                                if(cg.getClassname().equals(test[2])){
                                                    sw2=true;
                                                    if(cg.isListable()){
                                                        sw4=true;
                                                    }
                                                }
                                            }
                                            if(!sw2){
                                                Attributes(project,text,DirSrc,DirRes,Package);
                                            }else{
                                                if(sw4){
                                                    Forms.add(test[2]);
                                                    auxclassGeneration.start(DirSrc,project,Package,Subroutines.FormGeneration(Forms));
                                                    System.out.println(Forms.toString());
                                                }
                                            }
                                            break;
                                    }
                                    break;
                                case "edit":
                                    switch(test[2]){
                                        case "add":
                                            boolean sw3=false;
                                            for(int i=0;i<Classes.size();i++){
                                                if(Classes.get(i).getClassname().equals(test[1])){
                                                    sw3=true;
                                                    Classes.set(i,Command.Add(Classes.get(i),test[3],test[4],DirSrc,Package));
                                                    boolean sw=true;
                                                    while(sw){
                                                        sw=Forms.remove(test[1]);
                                                        System.out.println(Forms);
                                                    }
                                                    auxclassGeneration.start(DirSrc,project,Package,Subroutines.FormGeneration(Forms));
                                                }
                                            }
                                            if(!sw3){
                                                JOptionPane.showMessageDialog(null, "Class "+test[1]+" not found.");
                                            }

                                            break;
                                        case "remove":
                                            boolean sw4=false;
                                            for(int i=0;i<Classes.size();i++){
                                                if(Classes.get(i).getClassname().equals(test[1])){
                                                    sw4=true;
                                                    Classes.set(i,Command.Remove(Classes.get(i),test[3],DirSrc,Package));
                                                    boolean sw=true;
                                                    while(sw){
                                                        sw=Forms.remove(test[1]);
                                                        System.out.println(Forms);
                                                    }
                                                    auxclassGeneration.start(DirSrc,project,Package,Subroutines.FormGeneration(Forms));
                                                }
                                            }
                                            if(!sw4){
                                            JOptionPane.showMessageDialog(null, "Class "+test[1]+" not found.");
                                            }

                                            break;
                                    }
                                    break;
                                case "link":
                                        Command.Link(test[1],test[3]);
                                    break;
                                case "delete":
                                        boolean sw5=false;
                                        for(ClassGeneration cg : Classes){
                                            if(cg.getClassname().equals(test[1])){
                                                sw5=true;
                                            }
                                        }
                                        if(sw5){
                                            Classes=Command.Delete(Classes,test[1],DirSrc);
                                            boolean sw=true;
                                            while(sw){
                                                sw=Forms.remove(test[1]);
                                                System.out.println(Forms);
                                            }
                                            auxclassGeneration.start(DirSrc,project,Package,Subroutines.FormGeneration(Forms));
                                            //delete activites
                                        }else{
                                            JOptionPane.showMessageDialog(null, "Class "+test[1]+" not found.");
                                        }
                                    break;
                            }
                        }else{
                            alertLabel.setForeground(JBColor.RED);
                            alertLabel.setText(err.getMessage());//<----
                        }
                    }else{
                        alertLabel.setForeground(JBColor.RED);
                        alertLabel.setText("Lexical Analysis: Failed");
                    }
                }
            }

            public void keyReleased(KeyEvent keyEvent) {

            }

            public void keyTyped(KeyEvent keyEvent) {

            }
        };
        nameTextField.addKeyListener(keyListener);

        frame.setSize(800, 100);
        frame.setVisible(true);


    }

    public void Attributes(Project project,String text,String pathclass,String pathxml,String pack){
        String commando=text;

        JFrame frame2 = new JFrame();
        JLabel alertLabel= new JLabel();
        JLabel nameLabel= new JLabel();
        JTextField nameTextField2 = new JTextField();
        ArrayList<String> attrib=new ArrayList<>();

        frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Font newLabelFont=new Font(alertLabel.getFont().getName(),Font.ITALIC,alertLabel.getFont().getSize());
        alertLabel.setFont(newLabelFont);

        frame2.add(nameLabel,BorderLayout.NORTH);
        frame2.add(alertLabel,BorderLayout.CENTER);
        nameLabel.setText("Enter an attribute : ");
        frame2.add(nameTextField2, BorderLayout.SOUTH);

        KeyListener keyListener = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                char ch = e.getKeyChar();
                int ascii = (int) ch;
                if(ascii==10){ //intro
                    String text=nameTextField2.getText();
                    nameTextField2.setText("");
                    text=Subroutines.RemoveSpaces(text);
                    if(Subroutines.Lex(text)){
                        String[] test=text.split(" ");
                        int tam=test.length;
                        switch(tam){
                            case 1:
                                if(test[0].equals("exit")){
                                    Classes.add(Command.Create(commando,attrib,pathclass,pathxml,pack));
                                    ActivityGeneration.start(project,commando,attrib,pathclass,pathxml,pack,ManifestDirection);

                                    frame2.dispose();
                                }else{
                                    if(Subroutines.isAtribute(test[0])){
                                        alertLabel.setForeground(JBColor.GREEN);
                                        alertLabel.setText("Success");
                                        attrib.add(text);
                                    }else{
                                        alertLabel.setForeground(JBColor.RED);
                                        alertLabel.setText("Error");
                                    }
                                }
                                break;
                            case 2:
                                if(Subroutines.isKey(test[0]) && Subroutines.isAtribute(test[1])){
                                    alertLabel.setForeground(JBColor.GREEN);
                                    alertLabel.setText("Success");
                                    attrib.add(text);
                                }else{
                                    alertLabel.setForeground(JBColor.RED);
                                    alertLabel.setText("Error");
                                }
                                break;
                            default:
                                alertLabel.setForeground(JBColor.RED);
                                alertLabel.setText("Error");
                        }
                    }else{
                        alertLabel.setForeground(JBColor.RED);
                        alertLabel.setText("Lexical Analysis: Failed");
                    }
                }
            }

            public void keyReleased(KeyEvent keyEvent) {

            }

            public void keyTyped(KeyEvent keyEvent) {

            }
        };
        nameTextField2.addKeyListener(keyListener);
        frame2.setSize(800, 100);
        frame2.setLocation(100,100);
        frame2.setVisible(true);

    }


    public void FindMain(VirtualFile vf,String find,String main){
        if(!vf.isDirectory()){
            if(vf.getName().equals(find)){
                MainLocation=vf.getCanonicalPath();
            }
        }else{
            for(VirtualFile child : vf.getChildren()){
                FindMain(child,find,main);
            }
        }
    }

    public void getPack(String dir, String pivot){
        String[] test=dir.split("/");
        String out="";
        String out2="";

        boolean sw=false;
        for(int i=0;i<test.length-1;i++){
            if(i<test.length-2){
                out2+=test[i]+"/";
            }else{
                out2+=test[i];
            }
            if(sw){
                if(i==test.length-2){
                    out+=test[i];
                }else{
                    out+=test[i]+".";
                }
            }
            if(test[i].equals(pivot)){
                sw=true;
            }
        }

        Package=out;
        DirSrc=out2;

    }

    public Document FindManifest(Project project,VirtualFile vf,String ProjectName,String ProjectDir){
        VirtualFile vf2;
        Document Manifest=null;
        vf=vf.findChild("app");
        assert vf != null;
        for (VirtualFile test : vf.getChildren()){
            if(test.getName().equals("AndroidManifest.xml")){
                ManifestDirection=ProjectDir+"\\app";
                Manifest= mainGeneration.manifest(ProjectDir+"\\app");
            }
        }
        vf2=vf.findChild("manifests");
        if(vf2!=null){
            for (VirtualFile test : vf2.getChildren()){
                if(test.getName().equals("AndroidManifest.xml")){
                    ManifestDirection=ProjectDir+"\\app\\manifests";
                    Manifest= mainGeneration.manifest(ProjectDir+"\\app\\manifests");
                }
            }
        }
        vf=vf.findChild("src");
        assert vf != null;
        for (VirtualFile test : vf.getChildren()){
            if(test.getName().equals("AndroidManifest.xml")){
                ManifestDirection=ProjectDir+"\\app\\src";
                Manifest= mainGeneration.manifest(ProjectDir+"\\app\\src");
            }
        }
        vf=vf.findChild("main");
        assert vf != null;
        for (VirtualFile test : vf.getChildren()){
            if(test.getName().equals("AndroidManifest.xml")){
                ManifestDirection=ProjectDir+"\\app\\src\\main";
                Manifest= mainGeneration.manifest(ProjectDir+"\\app\\src\\main");
            }
        }

        return Manifest;

    }

}
