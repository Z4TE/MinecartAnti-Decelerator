package org.z4te.minecartAntiDecelerator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Main extends JavaPlugin implements Listener {

    private static final double DECELERATION_MODIFIER = 0.98;
    private static final double MIN_ACCELERATION_THRESHOLD = 0.05;

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

            Vector currentVelocity = minecart.getVelocity();
            Vector previousVelocity = new Vector(
                    event.getFrom().getX() - event.getTo().getX(),
                    event.getFrom().getY() - event.getTo().getY(),
                    event.getFrom().getZ() - event.getTo().getZ()
            );

            double acceleration = currentVelocity.distance(previousVelocity);

            if (acceleration < MIN_ACCELERATION_THRESHOLD) {
                currentVelocity.multiply(DECELERATION_MODIFIER);
                minecart.setVelocity(currentVelocity);
            }
        }
    }
}
