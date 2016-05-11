package proyectofinal.codegenerator;

import java.util.ArrayList;

/**
 * Created by Necho on 10/05/2016.
 */
public class ClassData {
    String classname;
    String command;
    ArrayList<String> attributes;

    public ClassData(String command, ArrayList<String> attributes) {
        String[] tokens=command.split(" ");
        switch(tokens.length){
            case 2:
                this.classname = tokens[1];
                break;
            case 3:
                this.classname = tokens[2];
                break;
        }
        this.command=command;
        this.attributes = attributes;
    }

    public String getClassname() {
        return classname;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }
}
