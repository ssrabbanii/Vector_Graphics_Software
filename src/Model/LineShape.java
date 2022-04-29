package hk.edu.polyu.comp.comp2021.Main.Model;

import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.scene.paint.Color;

/**
 * Processes line specific command and creates line shape
 */
public class LineShape implements Command {
    /**
     * Creates a line of coordinates specified by user
     */


    private void create(String n,double x, double y, double p, double q){

        javafx.scene.shape.Line line=new javafx.scene.shape.Line();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(p);
        line.setEndY(q);




        line.setFill(Color.TRANSPARENT);
        line.setStroke(Color.BLACK);
        line.setId(n);
        String message="Line " + line.getId()+ " Successfully Created!";
        Controller.addNodes(line);
        Clevis.displayPrompt(message);
        System.out.println(message);



    }


    @Override
    public void parseInput(String[] arg) {
        if (arg.length!=6) {
            Clevis.displayPrompt("Invalid Input Length!");
            System.out.println("Invalid Input Length!");
            throw  new IllegalArgumentException();
        }
        if (Controller.isNameDefined(arg[1])){
            Clevis.displayPrompt(arg[1]+" already defined");
            System.out.println((arg[1]+" already defined"));
            throw  new IllegalArgumentException();
        }
        double x,y,x1,y1;
        try{
            x=Double.parseDouble(arg[2]);
            y=Double.parseDouble(arg[3]);
            x1=Double.parseDouble(arg[4]);
            y1=Double.parseDouble(arg[5]);
        }catch ( IllegalArgumentException e){
            System.out.println("Invalid Input");
            Clevis.displayPrompt("Invalid Input");
            throw e;
        }
        if(x>Double.MAX_VALUE || y>Double.MAX_VALUE|| x1>Double.MAX_VALUE || y1>Double.MAX_VALUE){
            Clevis.displayPrompt("Max value of Double exceeded");
            System.out.println("Max value of Double exceeded");
            throw new IllegalArgumentException();
        }


        if(x<0 || y<0 ||x1<0 || y1<0 ) { //Values can't be out of axis
            Clevis.displayPrompt("Coordinates can't be negative!");
            System.out.println("Coordinates can't be negative!");
            throw new IllegalArgumentException();
        } else if (x==x1 && y==y1) { //Line of length 0 can't exist
            Clevis.displayPrompt("Line of 0 length can't be displayed!");
            System.out.println("Line of 0 length can't be displayed!");
            throw new IllegalArgumentException();
        }

        System.out.println("Creating line");
        create(arg[1],x,y,x1,y1);
    }
}
