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

    private final HashMap<UUID, Vector> previousSpeeds = new HashMap<>();

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

        if (!(event.getVehicle() instanceof Minecart minecart)) {
            return;
        }

        UUID minecartId = minecart.getUniqueId();
        Vector currentVelocity = minecart.getVelocity();

        Vector previousVelocity = previousSpeeds.getOrDefault(minecartId, currentVelocity);

        // 減速しているか確認
        if (currentVelocity.length() < previousVelocity.length()) {
            // 減速度を緩める
            Vector adjustedVelocity = previousVelocity.multiply(0.998).add(currentVelocity.multiply(0.002));
            minecart.setVelocity(adjustedVelocity);
        }
        previousSpeeds.put(minecartId, currentVelocity);
    }
}
