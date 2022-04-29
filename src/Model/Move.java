package hk.edu.polyu.comp.comp2021.Main.Model;

import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Implements Move and pick-and-move specific commands and processes elligible inputs
 */
public class Move implements Command {

    private void move(String n, double dx, double dy) {
        Node node= cloneNode(Controller.getNode(n));
        if((node.getClass().equals(Line.class))) moveLine((javafx.scene.shape.Line) node,dx,dy);
        else if (node instanceof Circle) moveCircle((javafx.scene.shape.Circle) node,dx,dy);
        else if(node instanceof Group) moveGroup((Group)node,dx,dy);
        else moveRectangle((Rectangle)node,dx,dy);
        Controller.replaceNode(node);
        Clevis.displayPrompt("Moved "+n+" successfully");
        System.out.println("Moved "+n+" successfully");


    }

    private String identify (ArrayList<Node> list,double x, double y){

        for (int i=list.size()-1;i>=0;i--){
            Node node=list.get(i);
            if(node.getClass().equals(Line.class)){
                if (node.contains(new Point2D(x,y))) return node.getId();
            }else if (node instanceof Circle) {
                if (Intersect.ifInCircle(((Circle) node).getCenterX(),((Circle) node).getCenterY(),
                        ((Circle) node).getRadius(),x,y)) return node.getId();
            } else if (node instanceof  Group){
                ArrayList<Node> list1=new ArrayList<>();
                for (Node child: ((Group) node).getChildren()) list1.add(child);
                if (identify(list1,x,y) != null){
                    return node.getId();
                }

            } else {
                double x2=((Rectangle) node).getX()+((Rectangle) node).getWidth();
                double y2= ((Rectangle) node).getY()+((Rectangle) node).getHeight();
                if (Intersect.ifInRectangle(((Rectangle) node).getX(),((Rectangle) node).getY(),
                        x2,y2,x,y)) return node.getId();
            }
        }
        return null;
    }







    //==================================================

    private void moveLine(Line node, double dx, double dy) {
        node.setStartX(node.getStartX()+dx);
        node.setStartY(node.getStartY()+dy);

        node.setEndX(node.getEndX()+dx);
        node.setEndY(node.getEndY()+dy);
    }

    private void moveCircle(Circle node, double dx, double dy) {
        node.setCenterX(node.getCenterX()+dx);
        node.setCenterY(node.getCenterY()+dy);

    }

    private void moveGroup(Group group, double dx, double dy) {
        for (Node node:group.getChildren()){
            if (node.getClass().equals(Line.class)) moveLine((Line) node,dx,dy);
            else if (node instanceof Circle) moveCircle((javafx.scene.shape.Circle) node,dx,dy);
            else if (node instanceof Group) moveGroup(((Group)node),dx,dy);
            else moveRectangle((Rectangle)node,dx,dy);
        }

    }

    private void moveRectangle(Rectangle node, double dx, double dy) {
        node.setX(node.getX()+dx);
        node.setY(node.getY()+dy);
    }


    @Override
    public void parseInput(String[] arg) {

        if (arg[0].equals("move")){
            if (arg.length!=4) {
                Clevis.displayPrompt("Invalid Input Length!");
                System.out.println("Invalid Input Length!");
                throw  new IllegalArgumentException();
            }
            if (!Controller.isNameDefined(arg[1])){
                Clevis.displayPrompt(arg[1]+" not defined");
                System.out.println(arg[1]+" not defined");
                throw  new IllegalArgumentException();
            }
            double dx,dy;
            try{
                dx=Double.parseDouble(arg[2]);
                dy=Double.parseDouble(arg[3]);
            }catch(IllegalArgumentException e){
                System.out.println("Illegal Command");
                Clevis.displayPrompt("Illegal Command");
                throw e;
            }
            if(dx>Double.MAX_VALUE || dy>Double.MAX_VALUE){
                Clevis.displayPrompt("Max value of Double exceeded");
                System.out.println("Max value of Double exceeded");
                throw new IllegalArgumentException();
            }



            if ((dx==0 && dy==0)){
                Clevis.displayPrompt(" 0 0 doesn't move the shape");
                System.out.println(" 0 0 doesn't move the shape");
                throw  new IllegalArgumentException();
            }



            System.out.println("Moving Shape");
            move(arg[1],dx,dy);
        } else{
            if (arg.length!=5) {
                Clevis.displayPrompt("Invalid Input Length!");
                throw  new IllegalArgumentException();
            }
            double x,y,dx,dy;
            try{
                x=Double.parseDouble(arg[1]);
                y=Double.parseDouble(arg[2]);
                dx=Double.parseDouble(arg[3]);
                dy=Double.parseDouble(arg[4]);
            }catch(IllegalArgumentException e){
                System.out.println("Illegal Command");
                Clevis.displayPrompt("Illegal Command");
                throw e;
            }
            if(x>Double.MAX_VALUE || y>Double.MAX_VALUE|| dx>Double.MAX_VALUE || dy>Double.MAX_VALUE){
                Clevis.displayPrompt("Max value of Double exceeded");
                System.out.println("Max value of Double exceeded");
                throw new IllegalArgumentException();
            }

            ArrayList<Node> list=Controller.getNodesList();
            String response=identify(list,x,y);
            if (response!=null) move(response,dx,dy);
            else Clevis.displayPrompt("No Nearby shape");
        }








    }


    private Node cloneNode(Node node){
        if(node.getClass().equals(Line.class))  return cloneLine((Line)node);

        else if (node instanceof Circle) return cloneCircle((javafx.scene.shape.Circle) node);
        else if(node instanceof Group) {
            Group group=new Group();
            group.setId(node.getId());
            for (Node og:((Group) node).getChildren()){
                if(og.getClass().equals(Line.class)) group.getChildren().add(cloneLine((javafx.scene.shape.Line) og));
                else if (og instanceof Circle) group.getChildren().add(cloneCircle((javafx.scene.shape.Circle) og) );
                else if (og instanceof Group) group.getChildren().add(cloneNode(og));
                else group.getChildren().add(cloneRectangle((Rectangle)og));
            }
            return group;
        }
        else return cloneRectangle((Rectangle)node);

    }

    private Rectangle cloneRectangle(Rectangle node) {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
        rectangle.setX(node.getX());
        rectangle.setY(node.getY());
        rectangle.setWidth(node.getWidth());
        rectangle.setHeight(node.getHeight());
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setId(node.getId());

        return rectangle;
    }

    private Circle cloneCircle(Circle node) {
        javafx.scene.shape.Circle circle= new javafx.scene.shape.Circle();
        circle.setCenterX(node.getCenterX());
        circle.setCenterY(node.getCenterY());
        circle.setRadius(node.getRadius());

        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.RED);
        circle.setId(node.getId());
        return circle;
    }

    private Line cloneLine(Line node) {
        javafx.scene.shape.Line line=new javafx.scene.shape.Line();
        line.setStartX(((Line) node).getStartX());
        line.setStartY(((Line) node).getStartY());
        line.setEndX(((Line) node).getEndX());
        line.setEndY(((Line) node).getEndY());




        line.setFill(Color.TRANSPARENT);
        line.setStroke(Color.BLACK);
        line.setId(node.getId());

        return line;
    }


}
