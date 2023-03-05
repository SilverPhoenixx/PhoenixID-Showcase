package net.royalguardians.PhoenixID.listener;
import net.royalguardians.PhoenixID.user.UserID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PhoenixRegisteredEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private UUID uuid;
    private String name;
    private UserID userID;

    public PhoenixRegisteredEvent(UUID uuid, String name, UserID userID) {
        this.userID = userID;
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UserID getUserID() {
        return userID;
    }

    public UUID getUUID() {
        return uuid;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}