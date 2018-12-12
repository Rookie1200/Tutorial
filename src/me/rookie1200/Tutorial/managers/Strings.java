package me.rookie1200.Tutorial.managers;

import com.sk89q.minecraft.util.commands.ChatColor;

public class Strings {

    public static String prefix = (ChatColor.BLACK + "[" + ChatColor.AQUA + "Server" + ChatColor.BLACK + "] " + ChatColor.GREEN);
    public static String noPermission = (prefix + ChatColor.DARK_RED + "You do not have permission to do that.");
    public static String mustBePlayer = (ChatColor.RED + "You must be a player");

}
