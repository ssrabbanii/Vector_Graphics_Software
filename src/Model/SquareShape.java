package hk.edu.polyu.comp.comp2021.Main.Model;

import javafx.scene.paint.Color;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;

/**
 * Processes Square creation command and creates square shape
 */
public class SquareShape implements Command {
    /**
     * Creates a square as per user defined coordinates and dimensions
     */


    private void create(String n,double x, double y, double l){

        javafx.scene.shape.Rectangle square = new javafx.scene.shape.Rectangle();
        square.setX(x);
        square.setY(y);
        square.setWidth(l);
        square.setHeight(l);
        square.setFill(Color.TRANSPARENT);
        square.setStroke(Color.BLUE);
        square.setId(n);
        String message="Square " + square.getId()+ " Successfully Created!";
        Controller.addNodes(square);
        Clevis.displayPrompt(message);
        System.out.println(message);



    }

    @Override
    public void parseInput(String[] arg) {
        if (arg.length!=5) {
            Clevis.displayPrompt("Invalid Input Length!");
            System.out.println("Invalid Input Length!");
            throw  new IllegalArgumentException();
        }
        if (Controller.isNameDefined(arg[1])){
            Clevis.displayPrompt(arg[1]+" already defined");
            System.out.println(arg[1]+" already defined");
            throw  new IllegalArgumentException();
        }
        double x,y,l;
        try{
            x=Double.parseDouble(arg[2]);
            y=Double.parseDouble(arg[3]);
            l=Double.parseDouble(arg[4]);
        }catch (IllegalArgumentException e){
            Clevis.displayPrompt("Illegal Command");
            System.out.println("Illegal Command");
            throw e;
        }
        if(x>Double.MAX_VALUE || y>Double.MAX_VALUE|| l>Double.MAX_VALUE ){
            Clevis.displayPrompt("Max value of Double exceeded");
            System.out.println("Max value of Double exceeded");
            throw new IllegalArgumentException();
        }

        if(x<0 || y<0) {
            Clevis.displayPrompt("x or y can't be negative!");
            System.out.println("x or y can't be negative!");
            throw new IllegalArgumentException();
        }else if (l<=0 ){
            Clevis.displayPrompt("l can't be "+l);
            System.out.println("l can't be "+l);
            throw new IllegalArgumentException();
        }

        System.out.println("Creating Square");
        create(arg[1],x,y,l);

    }
    
    
}
