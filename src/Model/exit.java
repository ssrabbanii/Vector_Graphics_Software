package hk.edu.polyu.comp.comp2021.Main.Model;

import hk.edu.polyu.comp.comp2021.Main.Controller.Controller;
import hk.edu.polyu.comp.comp2021.Main.View.Clevis;

/**
 * Processes exit commands and undo/redo request
 */
public class exit implements Command {
    /**
     * Clevis & Controller class
     */
    private Clevis clevis;
    private Controller controller;


    /**
     * @param clevis Main view
     * @param controller Controller instance
     */
    public exit(Clevis clevis,Controller controller) {
        this.clevis=clevis;
        this.controller=controller;

    }

    @Override
    public void parseInput(String[] arg) {
        if (arg.length!=1){
            System.out.println("Illegal Input Length");
            Clevis.displayPrompt("Illegal Input Length");
            throw new IllegalArgumentException();
        }if (arg[0].equals("quit")){
            clevis.exitRequest();
            Controller.setExitFlag(true);
        }
        if (arg[0].equals("undo")){
            Controller.undo();
            Clevis.displayPrompt("Undo performed");
            System.out.println("Undo performed");
        }else {
            Controller.redo();
            Clevis.displayPrompt("Undo performed");
            System.out.println("Undo performed");
        }
    }
}
