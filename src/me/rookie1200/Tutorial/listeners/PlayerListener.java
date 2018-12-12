package me.rookie1200.Tutorial.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.rookie1200.Tutorial.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    public PlayerListener(Main main) {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.BLUE + "You are a lovely being, not :P " + ChatColor.BOLD + p.getName());
    }
}
