package hk.edu.polyu.comp.comp2021.Main.Model;

import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Processes List/ListAll specific command and lists info in a relevant way
 */
public class Info implements Command {


    private String listNode(String id){
        Node node=Controller.getNode(id);
        if (node.getClass().equals(Line.class)) return lineInfo((Line) node);
        else if (node instanceof Circle) return circleInfo((Circle) node);
        else if (node instanceof  Group) return groupInfo((Group) node);
        else return rectangleInfo((Rectangle) node);


    }


    private String circleInfo(Circle circle){
        DecimalFormat f = new DecimalFormat("##.00");

        String output="\n-----------\n"+"Name: "+circle.getId()+"\nShape: Circle"+"\nCenter: "+f.format(circle.getCenterX())+", "+f.format(circle.getCenterY())+
                "\nRadius: "+f.format(circle.getRadius());

        return output;
    }

    private String lineInfo(Line line){
        DecimalFormat f = new DecimalFormat("##.00");

        String output="\n-----------\n"+"Name: "+line.getId()+"\nShape: Line"+"\nStarting Point: "+f.format(line.getStartX())+", "+f.format(line.getStartY())+
                "\nEnding Point: "+f.format(line.getEndX())+", "+f.format(line.getEndY());

        return output;
    }

    private String listAll(){

        ArrayList<Node> nodesList= Controller.getNodesList();
        String message="***\n";
        message=message+"Total "+nodesList.size()+" components present: \n";
        for (Node node:nodesList){
            message=message+listNode(node.getId());
        }

        return message+"\n***\n";
    }

    private String rectangleInfo(Rectangle rectangle){
        String output;
        DecimalFormat f = new DecimalFormat("##.00");

        if (rectangle.getWidth()==rectangle.getHeight()) {
            output="\n-----------\n"+"Name: "+rectangle.getId()+"\nShape: Square"+"\nStarting Point: "+f.format(rectangle.getX())+", "+f.format(rectangle.getY())+
                    "\nLength of side: "+f.format(rectangle.getHeight());

            return output;
        }
        output ="\n-----------\n"+"Name: "+rectangle.getId()+"\nShape: Rectangle"+"\nStarting Point: "+f.format(rectangle.getX())+", "+f.format(rectangle.getY())+
                "\nHeight: "+f.format(rectangle.getHeight())+",\nWidth: "+f.format(rectangle.getWidth());

        return output;
    }

    private String groupInfo(Group group){
        String message="Group Name: "+group.getId()+ " \nChildren of this group are: \n " +
                "////////////////////////////////////////\n";
        for (Node node: group.getChildren()){
            if (node.getClass().equals(Line.class)) message= message + "\n\n" + lineInfo((Line) node);
            else if (node instanceof Circle) message= message + "\n\n" + circleInfo((Circle) node);
            else if (node instanceof Group) message= message + "\n\n" + groupInfo((Group) node);
            else message= message + "\n\n" + rectangleInfo((Rectangle) node);
        }
        message=message+"\n////////////////////////////////////////\n";

        return  message;
    }





    @Override
    public void parseInput(String[] arg) {
        if (arg[0].equals("list")){
            if (arg.length!=2) throw new IllegalArgumentException();
            if (!Controller.isNameDefined(arg[1])){
                Clevis.displayPrompt(arg[1]+" not defined!");
                throw  new IllegalArgumentException();
            }
            String response=listNode(arg[1]);
            if (response!=null) {
                System.out.println(response);
                Clevis.infoScene(response);
            }




        }else{
            if (arg.length!=1) throw new IllegalArgumentException();
            String response=listAll();
            if (response!=null) {
                System.out.println(response);
                Clevis.infoScene(response);
            }
        }
    }
}
