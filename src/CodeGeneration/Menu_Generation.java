package CodeGeneration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Necho on 2/05/2016.
 */
public class Menu_Generation {

    public static void generate(String dir, String name){
        ArrayList<String> menu_test=new ArrayList<>();
        menu_test.add("<menu xmlns:android=\"http://schemas.android.com/apk/res/android\"");
        menu_test.add("\txmlns:app=\"http://schemas.android.com/apk/res-auto\"");
        menu_test.add("\txmlns:tools=\"http://schemas.android.com/tools\"");
        menu_test.add(String.format("\ttools:context=\".%s\">",name));
        menu_test.add("\t<item android:id=\"@+id/action_settings\"");
        menu_test.add("\t\tandroid:title=\"@string/action_settings\"");
        menu_test.add("\t\tandroid:orderInCategory=\"100\"");
        menu_test.add("\t\tapp:showAsAction=\"never\"/>");
        menu_test.add("</menu>");

        File f=new File(dir+"menu_"+name+".xml");

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
