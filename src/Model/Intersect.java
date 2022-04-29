package hk.edu.polyu.comp.comp2021.Main.Model;

import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;
import javafx.scene.Node;

/**
 * REMAINING
 */
public class Intersect implements Command {


    /**
     * Boundary buffer on top top of shape border as specified by project requirement by Dr. XU
     */
    public static final double boundaryBuffer = 0.05;

    /**
     * Determines if a point intersects a rectangle
     * @param x1 top left x coordinate
     * @param y1 top left y coordinate
     * @param x2 bottom right x coordinate
     * @param y2 bottom right y coordinate
     * @param x target point x coordinate
     * @param y target point y coordinate
     * @return weather x,y is in rectangle
     */
    public static boolean ifInRectangle(double x1, double y1, double x2,
                             double y2, double x, double y)
    {
        return x >= (x1- boundaryBuffer) && x <= (x2+boundaryBuffer) && y >= (y1-boundaryBuffer) && y <= (y2+boundaryBuffer);
    }

    /**
     * @param c_x Circle centre x coordinate
     * @param c_y Circle centre y coordinate
     * @param r radius of circle
     * @param x target point x coordinate
     * @param y target point y coordinate
     * @return Wether target point interesects circle
     */
    public static boolean ifInCircle(double c_x,double c_y, double r,double x, double y){
        return x >= (c_x - r+boundaryBuffer) && x <= (c_x + r+boundaryBuffer) && y >= (c_y - r+boundaryBuffer) && y <= (c_y + r+boundaryBuffer);

    }




    /**
     * Determines if a target point intersects a line
     * @param x start x coordinate
     * @param x2  end x coordinate
     * @param y start y coordinate
     * @param y2 end y coordinate
     * @return if points intersects line
     */
    public static double length(double x, double x2, double y, double y2){
        return Math.sqrt(Math.pow(( x2 - x ),2)+Math.pow(( y2 - y ),2));
    }

    private void check(String n1, String n2) {
        Node node1= Controller.getNode(n1);
        Node node2=Controller.getNode(n2);
        if (node1.intersects(node2.getBoundsInLocal())){
            Clevis.displayPrompt("Intersection Found!");
            System.out.println("Intersection Found!");
        }else {
            Clevis.displayPrompt("No Intersection!");
            System.out.println("No Intersection!");
        }
    }


    @Override
    public void parseInput(String[] arg) {
        if (arg.length!=3) {
            Clevis.displayPrompt("Invalid Output Length!");
            throw  new IllegalArgumentException();
        }
        if (!Controller.isNameDefined(arg[1])){
            Clevis.displayPrompt(arg[1]+" not defined");
            throw  new IllegalArgumentException();
        } else if (!Controller.isNameDefined(arg[2])){
            Clevis.displayPrompt(arg[2]+" not defined");
            throw  new IllegalArgumentException();
        }
        check(arg[1],arg[2]);


}
}


