package hk.edu.polyu.comp.comp2021.Main.Model;


import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.scene.Node;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;

import java.util.ArrayList;

/**
 * Processes group specific command request and groups & ungroups shapes.
 */
public class GroupShape implements Command {



    private void group(String[] arg){
        javafx.scene.Group group= new javafx.scene.Group();
        group.setId(arg[1]);
        ArrayList<Node> nodesList= (ArrayList<Node>) Controller.getNodesList().clone();
        for (int i=2;i< arg.length;i++){

            group.getChildren().add(Controller.getNode(arg[i]));
            removeNode(arg[i],nodesList);
        }
        nodesList.add(group);
        Controller.updateNodes(nodesList);

        Clevis.displayPrompt("Group "+group.getId()+" successfully created");
        System.out.println("Group "+group.getId()+" successfully created");


    }

    /**
     * Removes a node of an id from a nodes list
     * @param id node to be removed
     * @param nodesList from the list from which it is to be removed
     */
    public static void removeNode(String id, ArrayList<Node>nodesList){
        for (Node node:nodesList){
            if (node.getId().equals(id)){ nodesList.remove(node);return;}
        }
        throw new IllegalArgumentException();
    }

    /**
     * Ungroups a group shape and extracts its members back to nodeslist
     * @param group group that is to be ungrouped
     */
    private void ungroup(javafx.scene.Group group){

        ArrayList<Node> nodesList= (ArrayList<Node>) Controller.getNodesList().clone();
        for (Node node: group.getChildren()){
            nodesList.add(node);
        }
        removeNode(group.getId(), nodesList);
        Controller.updateNodes(nodesList);

        Clevis.displayPrompt("Group "+group.getId()+"  ungrouped");


    }





    @Override
    public void parseInput(String[] arg) {
        if (arg[0].equals("group")){
            if (arg.length<4) {
                Clevis.displayPrompt("Invalid Input");
                System.out.println("Invalid Input");
                throw new IllegalArgumentException();
            }

            if (Controller.isNameDefined(arg[1])){
                Clevis.displayPrompt(arg[1]+" already defined");
                throw  new IllegalArgumentException();
            }
            for (int i=2;i< arg.length;i++){
                if(!Controller.isNameDefined(arg[i])){
                    Clevis.displayPrompt(arg[i]+" not defined");
                    throw  new IllegalArgumentException();
                }
            }
            System.out.println("Creating Group");
            group(arg);
        } else{
            if (arg.length!=2) {
                Clevis.displayPrompt("Invalid Input");
                System.out.println("Invalid Input");
                throw new IllegalArgumentException();
            }
            if (!Controller.isNameDefined(arg[1])){
                Clevis.displayPrompt(arg[1]+" not defined!");
                throw  new IllegalArgumentException();
            }
            System.out.println("Ungrouping...");
            ungroup((javafx.scene.Group) Controller.getNode(arg[1]));
        }







    }
}
