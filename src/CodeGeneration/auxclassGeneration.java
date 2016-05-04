package CodeGeneration;

import Utility.ErrorHandler;
import Utility.Resources;
import com.intellij.openapi.project.Project;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Necho on 24/04/2016.
 */
public class auxclassGeneration {

    public static void start(String dir, Project project, String pack,ArrayList<String> forms){
        Resources r=new Resources(pack,forms);

        File f=new File(dir+"\\Row.java");
        File g=new File(dir+"\\Data.java");
        File h=new File(dir+"\\DataStructure.java");

        try {
            Resources.WriteClass(f,r.getRow());
            Resources.WriteClass(g,r.getData());
            Resources.WriteClass(h,r.getDataStructure());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            ErrorHandler.handleError(project,e);
        }


    }



}
