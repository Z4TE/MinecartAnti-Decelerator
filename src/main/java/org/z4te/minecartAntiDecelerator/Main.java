package org.z4te.minecartAntiDecelerator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    private final HashMap<UUID, Vector> previousVelocities = new HashMap<>();

    private static final double DECELERATION_MODIFIER = 0.99;
    private static final double MIN_ACCELERATION_THRESHOLD = 0.02;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll();
    }

    @EventHandler
    public void onMinecartMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart minecart){

            UUID minecartId = minecart.getUniqueId();

            Vector currentVelocity = minecart.getVelocity();
            Vector previousVelocity = previousVelocities.getOrDefault(minecartId, new Vector(0, 0, 0));


            double acceleration = currentVelocity.distance(previousVelocity);
            Bukkit.broadcastMessage(String.valueOf(acceleration));

            if (acceleration > MIN_ACCELERATION_THRESHOLD) {
                currentVelocity.multiply(DECELERATION_MODIFIER);
                minecart.setVelocity(currentVelocity);
            }

        }
    }
}
