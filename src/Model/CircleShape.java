package hk.edu.polyu.comp.comp2021.Main.Model;

import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.scene.paint.Color;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;

/**
 * Processes Circle creation command and creates circle shape
 */
public class CircleShape implements Command {
    /**
     * Creates circle
     */

    private void create(String n, double x, double y, double r) {
        javafx.scene.shape.Circle circle= new javafx.scene.shape.Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(r);

        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.RED);
        circle.setId(n);

        String message="Circle " + circle.getId()+ " Successfully Created!";
        Controller.addNodes(circle);
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
        double x,y,r;
        try{
           x=Double.parseDouble(arg[2]);
           y=Double.parseDouble(arg[3]);
           r=Double.parseDouble(arg[4]);
        }catch (IllegalArgumentException e){
            Clevis.displayPrompt("Illegal Command");
            System.out.println("Illegal Command");
            throw e;
        }
        if(x>Double.MAX_VALUE || y>Double.MAX_VALUE|| r>Double.MAX_VALUE ){
            Clevis.displayPrompt("Max value of Double exceeded");
            System.out.println("Max value of Double exceeded");
            throw new IllegalArgumentException();
        }


        if(x-r<0 || y-r<0 ) {
            System.out.println("Found issue");
            Clevis.displayPrompt("Circle goes will go out of border!");
            System.out.println("Circle will go out of border!");
            throw new IllegalArgumentException();
        }else if (r<=0 ){
            Clevis.displayPrompt("r can't be "+r);
            System.out.println("r can't be"+r);
            throw new IllegalArgumentException();
        }

        System.out.println("Creating Circle");
        create(arg[1],x,y,r);




    }


}
