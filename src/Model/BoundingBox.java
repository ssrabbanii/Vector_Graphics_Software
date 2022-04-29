package hk.edu.polyu.comp.comp2021.Main.Model;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;

import java.text.DecimalFormat;

/**
 * Calculates bounding box of shape
 */
public class BoundingBox implements Command {

    private void create(String n) {
        Node node=Controller.getNode(n);

        if (node.getClass().equals(Circle.class)) circleBbox((Circle) node);
        else if (node.getClass().equals(Line.class)) lineBbox((Line) node);
        else if (node.getClass().equals(Group.class)) groupBbox((Group) node);
        else rectangleBbox((Rectangle) node);

    }


    private void  groupBbox(Group group){
        double min_x=Double.POSITIVE_INFINITY;
        double min_y=Double.POSITIVE_INFINITY;
        double max_x=Double.NEGATIVE_INFINITY;
        double max_y=Double.NEGATIVE_INFINITY;

        for (Node node:group.getChildren()){
            if ((node.getClass().equals(Line.class))){
                min_x= Math.min(min_x, ((Line) node).getStartX());
                min_x= Math.min(min_x, ((Line) node).getEndX());


                max_x= Math.max(max_x, ((Line) node).getStartX());
                max_x= Math.max(max_x, ((Line) node).getEndX());



                min_y= Math.min(min_y, ((Line) node).getStartY());
                min_y= Math.min(min_y, ((Line) node).getEndY());


                max_y= Math.max(max_y, ((Line) node).getStartY());
                max_y= Math.max(max_y, ((Line) node).getEndY());
            } else if (node instanceof Circle){
                min_x= Math.min(min_x, (((Circle) node).getCenterX()-((Circle) node).getRadius()));
                max_x= Math.max(max_x, (((Circle) node).getCenterX()+((Circle) node).getRadius()));

                min_y= Math.min(min_y, (((Circle) node).getCenterY()- ((Circle) node).getRadius()));
                max_y= Math.max(max_y, (((Circle) node).getCenterY()+ ((Circle) node).getRadius()));
            }else if (node instanceof Rectangle){
                min_x= Math.min(min_x, (((Rectangle) node).getX()));
                max_x=Math.max(max_x,((Rectangle) node).getX()+ ((Rectangle) node).getWidth());


                min_y= Math.min(min_y, (((Rectangle) node).getY()));
                max_y=Math.max(max_y,((Rectangle) node).getY()+ ((Rectangle) node).getHeight());
            }
//            if (flag) {


//            }
//            else{
//                ArrayList<Double> answer= new ArrayList<>();
//                answer.add(min_x);
//                answer.add(min_y);
//                answer.add((max_x-min_x)); //w
//                answer.add((max_x-min_y)); //h
//                return answer;
//            }



        }
        Clevis.displayPrompt("x: "+min_x+"| y: "+min_y+"| w: "+(max_x-min_x)+"| h: "+(max_y-min_y));
        System.out.println("x: "+min_x+"| y: "+min_y+"| w: "+(max_x-min_x)+"| h: "+(max_y-min_y));

    }



    private void rectangleBbox(Rectangle rectangle){
        Clevis.displayPrompt("x: "+rectangle.getX()
                +"| y: "+rectangle.getY()
                +"| w: "+rectangle.getWidth()
                +"| h: "+rectangle.getHeight());
        System.out.println("x: "+rectangle.getX()
                +"| y: "+rectangle.getY()
                +"| w: "+rectangle.getWidth()
                +"| h: "+rectangle.getHeight());
    }
    private void circleBbox(Circle circle){
        double x=circle.getCenterX()-circle.getRadius();
        double y=circle.getCenterY()-circle.getRadius();
        double r=circle.getRadius();
        Clevis.displayPrompt("x: "+x+"| y: "+y+"| w: "+2*r+"| h: "+2*r);
        System.out.println("x: "+x+"| y: "+y+"| w: "+2*r+"| h: "+2*r);
    }
    private void lineBbox(Line line){
        double x1=line.getStartX();
        double x2=line.getEndX();
        double y1=line.getStartY();
        double y2=line.getEndY();

        double x_min=Math.min(x1,x2);
        double y_min=Math.min(y1,y2);

        double x_max=Math.max(x1,x2);
        double y_max=Math.max(y1,y2);



        double h=line.getStrokeWidth();
        double w=Math.sqrt(Math.pow(( x2 - x1 ),2)+Math.pow(( y2 - y1 ),2));
        DecimalFormat f = new DecimalFormat("##.00"); //Paste this line Sakib
        Clevis.displayPrompt("x: "+f.format(x_min)+"| y: "+f.format(y_min)+"| w: "+f.format(x_max-x_min)+"| h: "+f.format(y_max-y_min));
        System.out.println("x: "+f.format(x_min)+"| y: "+f.format(y_min)+"| w: "+f.format(x_max-x_min)+"| h: "+f.format(y_max-y_min));

    }


    @Override
    public void parseInput(String[] arg) {
        if (arg.length!=2) {
            Clevis.displayPrompt("Invalid Output Length!");
            System.out.println("Invalid Output Length!");
            throw  new IllegalArgumentException();
        }
        if (!Controller.isNameDefined(arg[1])){
            Clevis.displayPrompt(arg[1]+" is not defined");
            System.out.println(arg[1]+" is not defined");
            throw  new IllegalArgumentException();
        }

        System.out.println("Calculating BoundingBox");
        create(arg[1]);
    }


}
