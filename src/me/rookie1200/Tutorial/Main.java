package me.rookie1200.Tutorial;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import me.rookie1200.Tutorial.listeners.PlayerListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {
    private static Main instance;
    private static Main plugin;
    protected ArrayList<Class> cmdClasses;
    public static Economy econ = null;
    private CommandsManager<CommandSender> commands;

    public static Main getInstance() {
        return instance;
    }

    public static void log(String message) {
        System.out.println("[Tutorial] " + message);
    }

    public void onEnable() {
        plugin = this;
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
        registerCommandClass(Commands.class);
        registerCommands();
        log("Enabled successfully!");
        log("Mitchell is a Cunt");
        log(ChatColor.BLUE + "Mitchell is a Cunt");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "You need to enter a number!");
            } else {
                sender.sendMessage(ChatColor.RED + "Error occurred, contact developer. [Rookie1200]");
                sender.sendMessage(ChatColor.RED + "Message: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    protected void registerCommandClass(Class cmdClass) {
        if (cmdClasses == null)
            cmdClasses = new ArrayList<Class>();

        cmdClasses.add(cmdClass);
    }

    protected void registerCommands() {
        if (cmdClasses == null || cmdClasses.size() < 1) {

            log("Could not register commands! Perhaps you registered no classes?");
            return;
        }

        // Register the commands that we want to use
        commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender player, String perm) {
                return getInstance().hasPerm(player, perm);
            }


        };
        commands.setInjector(new SimpleInjector(this));
        final CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);

        for (Class cmdClass : cmdClasses)
            cmdRegister.register(cmdClass);
    }

    public boolean hasPerm(CommandSender sender, String perm) {
        return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
    }
}
