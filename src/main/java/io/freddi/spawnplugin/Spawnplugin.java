package io.freddi.spawnplugin;

import io.freddi.spawnplugin.events.PlayerTeleportSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * this is just for the luls
 */
public final class Spawnplugin extends org.bukkit.plugin.java.JavaPlugin {
    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 19244);

        // Optional: Add custom charts
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        org.bukkit.Bukkit.getCommandMap().register("spawn", new org.bukkit.command.Command("spawn") {
            @Override
            public boolean execute(@org.jetbrains.annotations.NotNull org.bukkit.command.CommandSender sender, @org.jetbrains.annotations.NotNull String commandLabel, @org.jetbrains.annotations.NotNull String[] args) {
                if(sender instanceof org.bukkit.entity.Player player) spawn(player);
                return true;
            }
        });
        org.bukkit.Bukkit.getCommandMap().register("setspawn", new org.bukkit.command.Command("setspawn") {
            @Override
            public boolean execute(@org.jetbrains.annotations.NotNull org.bukkit.command.CommandSender sender, @org.jetbrains.annotations.NotNull String commandLabel, @org.jetbrains.annotations.NotNull String[] args) {
                if(sender instanceof org.bukkit.entity.Player player){
                    player.getWorld().setSpawnLocation(player.getLocation());
                    player.sendMessage("Spawn set!");
                    return true;
                }
                return false;
            }
        });
        org.bukkit.Bukkit.getCommandMap().getCommand("setspawn").setPermission("minecraft.command.setworldspawn");
    }

    private void spawn(Player player){
        PlayerTeleportSpawnEvent event = new PlayerTeleportSpawnEvent(player, player.getWorld().getSpawnLocation());
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) player.teleport(event.getSpawnLocation());
        else player.sendMessage("Teleport cancelled!");
    }
}
