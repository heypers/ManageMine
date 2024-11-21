package org.heypers.heypers.data;

import java.util.HashMap;
import java.util.UUID;

public class PlayerAuthManager {
    private static final HashMap<UUID, String> playerAuthData = new HashMap<>(); // зачейджить в бд или что там бывает
    private static final HashMap<UUID, Boolean> loggedInPlayers = new HashMap<>();

    public static boolean registerPlayer(UUID uuid, String password) {
        if (playerAuthData.containsKey(uuid)) {
            return false;
        }
        playerAuthData.put(uuid, password);
        loggedInPlayers.put(uuid, false);
        return true;
    }

    public static boolean loginPlayer(UUID uuid, String password) {
        if (!playerAuthData.containsKey(uuid)) {
            return false;
        }

        if (playerAuthData.get(uuid).equals(password)) {
            loggedInPlayers.put(uuid, true);
            return true;
        }

        return false;
    }

    public static boolean isPlayerLoggedIn(UUID uuid) {
        return loggedInPlayers.getOrDefault(uuid, false);
    }
}
