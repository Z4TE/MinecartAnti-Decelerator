package org.z4te.minecartAntiDecelerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Main extends JavaPlugin implements Listener {

    private static final double DECELERATION_MODIFIER = 0.9;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    @EventHandler
    public void onMinecartMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart minecart){

            if (minecart.getLocation().getBlock().getType() == Material.RAIL
            || minecart.getLocation().getBlock().getType() == Material.POWERED_RAIL
            || minecart.getLocation().getBlock().getType() == Material.DETECTOR_RAIL
            || minecart.getLocation().getBlock().getType() == Material.ACTIVATOR_RAIL) {
                Vector velocity = minecart.getVelocity();

                velocity.multiply(DECELERATION_MODIFIER);
                minecart.setVelocity(velocity);
            }
        }
    }
}
