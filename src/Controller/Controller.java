package hk.edu.polyu.comp.comp2021.Main.Controller;

import hk.edu.polyu.comp.comp2021.Main.Model.*;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.scene.Node;
//import Main.Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


/**
 * Controller class handling user input and providing useful  utils.
 */
public  class Controller {


    private static ArrayList<Node> nodesList=new ArrayList<>(); //change to private
    private static Stack<ArrayList<Node>> undoStack=new Stack<ArrayList<Node>>();
    private static Stack<ArrayList<Node>> redoStack=new Stack<>();




    private volatile static Boolean exitFlag =false;




    /**
     * @return Acesses exitFlag for other classes upon request
     */
    public static Boolean getExitFlag() {
        return exitFlag;
    }

    /**
     * @param exitFlag  sets exit flag from other classes upon user requirements
     */
    public static void setExitFlag(Boolean exitFlag) {

        Controller.exitFlag = exitFlag;
    }

    /**
     * @return Provides a copy of the nodeslist for model's calculation
     */
    public static ArrayList<Node> getNodesList() {
        return (ArrayList<Node>) nodesList.clone();
    }

    private HashMap<String, Command> map= new HashMap<>();

    /**
     * Clevis view instance
     */
     private Clevis clevis;


    /**
     * Constructor where hashmap of commands are initialized
     * @param clevis main view
     */
    public Controller(Clevis clevis) {
        this.clevis = clevis;
        map.put("rectangle",new RectangleShape());
        map.put("line",new LineShape());
        map.put("square",new SquareShape());
        map.put("circle",new CircleShape());
        map.put("group",new GroupShape());
        map.put("ungroup",new GroupShape());
        map.put("delete",new Delete());
        map.put("boundingbox",new BoundingBox());
        map.put("move",new Move());
        map.put("pick-and-move",new Move());
        map.put("intersect",new Intersect());
        map.put("listAll",new Info());
        map.put("list",new Info());
        map.put("quit",new exit(clevis,this));
        map.put("undo",new exit(clevis,this));
        map.put("redo",new exit(clevis,this));



    }

    /**
     * Update the nodes list upon request from other classes.
     * Current list state is saved in undostack for future reference.
     * @param nodesList new list to be updated
     */
    public static void updateNodes(ArrayList<Node> nodesList) {
        ArrayList<Node> copyList= (ArrayList<Node>) Controller.nodesList.clone();
        undoStack.push(copyList);
        Controller.nodesList=nodesList;

    }


    /**
     * Adds a single shape ( node) to the list  upon request
     * Previous list state is stored in undo stack for future use.
     * @param node Node to be added to nodelist
     */
    public static void addNodes(Node node){
        ArrayList<Node> copyList= (ArrayList<Node>) nodesList.clone();
        undoStack.push(copyList);
        nodesList.add(node);

    }

    /**
     * Replace a node of a particular id with one of same id but different instance ( object)
     * A form of cloning is performed.
     * Undostack stores previous state of nodes before the operation takes place
     * @param node insntace Node that will replace
     */
    public static void replaceNode(Node node){
        ArrayList<Node> copyList= (ArrayList<Node>) Controller.nodesList.clone();
        undoStack.push(copyList);

        for (int i=0;i< nodesList.size();i++){
            if (nodesList.get(i).getId().equals(node.getId())){
                nodesList.set(i,node);

            }
        }



    }


    /**
     * Processing primary user input and checking user error handles
     * @param argument string message to be evaluated
     */
    public  void parseInput(String argument){
        //Hashmap for O(1) lookup
        argument=argument.trim();

        Logger.log(argument);
        String[] arguments=argument.split("\\s+");
        if (this.map.containsKey(arguments[0])){

            try{map.get(arguments[0]).parseInput(arguments);}

            catch (IllegalArgumentException ignored){}

        }else {
            System.out.println(arguments[0]+" is an invalid command!");
            clevis.displayPrompt(arguments[0]+" is an invalid command!");
        }


    }


    /**
     * Checks if a node is defined in nodes list or not via its id
     * @param argument id to be searched for
     * @return weather such node ( shape) exists or not
     */
    public static Boolean isNameDefined (String argument){
        for (Node node:nodesList){
            if (argument.equals(node.getId())) return true;
        }
        return false;
    }




    /**
     * @param id note  id  (shape name) based on which searched for
     * @return node if found otherwise error
     */
    public static Node getNode(String id){
        for (Node node:nodesList){
            if (node.getId().equals(id)) return node;
        }
        throw new IllegalArgumentException();
    }


    /**
     * Performs undo operation by stack manipulation
     * @return previous nodes list ( shapes)  to be now displayed
     */
    public static ArrayList<Node> undo(){
        if (!undoStack.isEmpty()){
            ArrayList<Node> list=(ArrayList<Node>) nodesList.clone();
            redoStack.push(list);
            return nodesList=undoStack.pop();
        }
        return nodesList;

    }

    /**
     * Performs redo operation by stack manipulation
     * @return previous node state ( list of shapes) that were undo'd.
     */
    public static ArrayList<Node> redo(){

        if (!redoStack.isEmpty()){
            ArrayList<Node> list=(ArrayList<Node>) nodesList.clone();
            undoStack.push(list);

            Controller.nodesList=redoStack.pop();
            return Controller.nodesList;
        }
        return nodesList;

    }






















}
