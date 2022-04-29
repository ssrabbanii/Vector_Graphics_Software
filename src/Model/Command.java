package hk.edu.polyu.comp.comp2021.Main.Model;

/**
 * Command Interface
 */
public interface Command {

    /**
     * @param arg Command splitted into lists to be processesd
     */
    public abstract void parseInput(String[] arg);


}
