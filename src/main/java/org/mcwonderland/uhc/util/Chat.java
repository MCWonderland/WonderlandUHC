package org.mcwonderland.uhc.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.lang.model.element.NestingKind;
import java.util.List;

@UtilityClass
public class Chat {
    public void send(CommandSender sender, List<String> messages) {
        send(sender, messages.toArray(new String[0]));
    }

    public void send(CommandSender sender, String... messages) {
        for (String message : messages) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public void sendConversing(Player sender, String... messages) {
        for (String message : messages) {
            sender.sendRawMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public void broadcast(String... messages) {
        Bukkit.getOnlinePlayers().forEach(player -> send(player, messages));
    }

    public void broadcastWithPerm(String permission, String... messages) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission(permission))
                .forEach(player -> {
                    send(player, messages);
                });
    }
}
