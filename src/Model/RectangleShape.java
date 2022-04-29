package hk.edu.polyu.comp.comp2021.Main.Model;

import javafx.scene.paint.Color;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;

/**
 * Processes Rectangle creation command and creates rectangle shape
 */
public class RectangleShape implements Command {
    /**
     * Creates rectangle
     */





    private void create(String n,double x, double y, double w, double h){

        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setWidth(w);
        rectangle.setHeight(h);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setId(n);




        String message="Rectangle " + rectangle.getId()+ " Successfully Created!";
        Controller.addNodes(rectangle);
        Clevis.displayPrompt(message);
        System.out.println(message);



    }




    @Override
    public  void parseInput(String[] arg) {
        if (arg.length!=6) {
            Clevis.displayPrompt("Invalid Input Length!");
            System.out.println("Invalid Input Length!");
            throw  new IllegalArgumentException();
        }
        if (Controller.isNameDefined(arg[1])){
            Clevis.displayPrompt(arg[1]+" already defined");
            System.out.println(arg[1]+" already defined");
            throw  new IllegalArgumentException();
        }
        double x,y,w,h;
        try{
             x=Double.parseDouble(arg[2]);
             y=Double.parseDouble(arg[3]);
             w=Double.parseDouble(arg[4]);
             h=Double.parseDouble(arg[5]);
        }catch (IllegalArgumentException e){
            Clevis.displayPrompt("Illegal Command");
            System.out.println("Illegal Command");
            throw new IllegalArgumentException();
        }

        if(x>Double.MAX_VALUE || y>Double.MAX_VALUE|| w>Double.MAX_VALUE || h>Double.MAX_VALUE){
            Clevis.displayPrompt("Max value of Double exceeded");
            System.out.println("Max value of Double exceeded");
            throw new IllegalArgumentException();
        }


        if(x<0 || y<0) { //Axis can't be negative
            Clevis.displayPrompt("x or y can't be negative!");
            System.out.println("x or y can't be negative!");
            throw new IllegalArgumentException();
        } else if (w<=0 || h<=0){
            Clevis.displayPrompt("Width or height can't be "+Math.min(w,h));
            System.out.println("Width or height can't be "+Math.min(w,h));
            throw new IllegalArgumentException();
        }

        System.out.println("Creating Rectangle");
        create(arg[1],x,y,w,h);





    }
}
