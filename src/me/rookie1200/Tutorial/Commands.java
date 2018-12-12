package me.rookie1200.Tutorial;

public class Commands {

    private static Commands instance = new Commands();
    private Main plugin;

    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    private Commands() {
    }

    public static Commands getInstance() {
        return instance;
    }


}
