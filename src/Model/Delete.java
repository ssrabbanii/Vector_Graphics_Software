package hk.edu.polyu.comp.comp2021.Main.Model;

import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.scene.Node;
import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;

import java.util.ArrayList;

/**
 * Processes delete specific request and executes deletion of shapes/groups
 */
public class Delete implements Command {

    private void delete(Node node) {
        ArrayList<Node> nodesList= (ArrayList<Node>) Controller.getNodesList().clone();

        GroupShape.removeNode(node.getId(), nodesList);
        Controller.updateNodes(nodesList);

        Clevis.displayPrompt(node.getId()+"  Deleted");

    }

    @Override
    public void parseInput(String[] arg) {
        if (arg.length!=2) throw new IllegalArgumentException();
        if (!Controller.isNameDefined(arg[1])){
            Clevis.displayPrompt(arg[1]+" not defined!");
            throw  new IllegalArgumentException();
        }
        System.out.println("Deleting shape...");
        delete(Controller.getNode(arg[1]));

    }


}
