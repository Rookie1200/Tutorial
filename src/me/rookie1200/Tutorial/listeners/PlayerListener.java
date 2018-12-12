package me.rookie1200.Tutorial.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.rookie1200.Tutorial.Main;
import me.rookie1200.Tutorial.managers.Strings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Random;

public class PlayerListener implements Listener {
    public PlayerListener(Main main) {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("mitchell.cool")) {
            p.sendMessage(ChatColor.BLUE + "You are a lovely being, not :P " + ChatColor.BOLD + p.getName());
        } else {
            p.sendMessage(Strings.noPermission);
        }
    }

    @EventHandler
    public void onProjectileThrow(ProjectileLaunchEvent e) {
        if (e.getEntity().getType().equals(EntityType.ARROW)) {
            Projectile proj = e.getEntity();
            final Arrow arrow = (Arrow) proj;
            ProjectileSource source = arrow.getShooter();
            if (source instanceof Player) {
                final Player p = (Player) source;
                arrow.setPassenger(p);
                arrow.setVelocity(arrow.getVelocity().multiply(10));
                p.sendMessage(Strings.prefix + "You're riding an arrow cunt");
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getType().equals(EntityType.ARROW)) {
            Projectile proj = e.getEntity();
            final Arrow arrow = (Arrow) proj;
            ProjectileSource source = arrow.getShooter();
            if (source instanceof Player) {
                Player p = (Player) source;

                arrow.removePassenger(p);
                arrow.remove();
                p.sendMessage(Strings.prefix + "Dumbass, you hit somethin' ya gigantic fucktard.");
            }
        }
    }

    @EventHandler
    public void inInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType().equals(Material.STICK)) {
            p.launchProjectile(Arrow.class);
            return;
        }
        if (p.getItemInHand().getType().equals(Material.BLAZE_ROD)) {
            Vector v = p.getLocation().getDirection().multiply(2.0);
            p.getVehicle().remove();
            Minecart m = p.getLocation().getWorld().spawn(p.getLocation().add(v.getX(), v.getY(), v.getZ()), Minecart.class);
            m.setPassenger(p);
            m.setVelocity(p.getEyeLocation().getDirection().multiply(5.0));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                e.setCancelled(true);
                e.getEntity().sendMessage(Strings.prefix + "You avoided an owwy! :D");
            }
        }
    }

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent e) {
        Vehicle v = e.getVehicle();
        if (v.getPassenger() != null && (v instanceof Minecart)) {
            Entity p = v.getPassenger();
            Minecart cart = (Minecart) v;
            Location loc = cart.getLocation();
            Vector vel = cart.getVelocity();
            if (loc.getBlock().getType().equals(Material.POWERED_RAIL)) {
                cart.setMaxSpeed(10.0D);
                Random rand = new Random();
                int randInt = rand.nextInt();
                if (randInt == 0){
                    randInt = 10;
                }
                vel.multiply(rand.nextInt(randInt));
                cart.setVelocity(vel);
                p.sendMessage(Strings.prefix + "Fukin ZOOM!");
            }
        }
    }
}
