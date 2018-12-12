package me.rookie1200.Tutorial;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import me.rookie1200.Tutorial.managers.Strings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {
    private Main plugin;

    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    @Command(aliases = "cunt", desc = "for cunts about cunts")
    public void onCunt(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("mitchell.command.cunt")) {
                p.sendMessage(ChatColor.GREEN + "Cunt!");
            } else {
                p.sendMessage(Strings.noPermission);
                p.kickPlayer("You r cunt!");
            }
        } else {
            sender.sendMessage(Strings.mustBePlayer);
        }
    }


//    @Command(aliases = "cunt", desc = "Get cunt")
//    public void onCunt(CommandContext args, CommandSender sender) {
//        if (sender instanceof Player) {
//            Player p = (Player) sender;
//            if (p.hasPermission("mitchell.cunt")) {
//                p.sendMessage(ChatColor.BLUE + "You are a cunt of a being " + ChatColor.BOLD + p.getName());
//            } else {
//                p.sendMessage(Strings.noPermission);
//            }
//        } else {
//            sender.sendMessage(Strings.mustBePlayer);
//        }
//    }
}
