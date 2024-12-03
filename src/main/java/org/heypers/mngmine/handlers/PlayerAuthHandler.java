/*
 * Copyright (c) 2024. Heypers Project, mr_fortuna, Heypers Team.
 *
 * Данный файл является частью интеллектуальной собственности проекта Heypers Project.
 *
 * Использование, модификация и распространение разрешены только в соответствии с официальной лицензией проекта.
 *
 * Любое копирование, изменение или распространение без явного письменного согласия правообладателей запрещено.
 * Обязательно указывать ссылку на официальные ресурсы Heypers Project при любом использовании или распространении информации.
 *
 * Для получения дополнительной информации о лицензии и условиях использования, посетите официальный сайт Проекта или свяжитесь с правообладателями.
 *
 * Официальный ресурс: https://heypers.pythonanywhere.com/license
 */

package org.heypers.mngmine.handlers;

import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.HashSet;
import java.util.UUID;

public class PlayerAuthHandler {
    private static final HashSet<UUID> loggedInPlayers = new HashSet<>();

    private static void checkMovementAndBlock(ServerPlayerEntity player) {
        if (!isAuthorized(player)) {
            player.sendMessage(Text.literal("Вы не можете двигаться, пока не войдете в систему."), true);
            player.teleport(player.getServerWorld(), player.prevX, player.prevY, player.prevZ, player.getYaw(), player.getPitch());
        }
    }

    public static void registerHandlers() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                checkMovementAndBlock(player);
            }
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> blockUnauthorized(player));
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> blockUnauthorized(player));
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> blockUnauthorized(player));
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> blockUnauthorized(player));
    }

    private static ActionResult blockUnauthorized(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (!isAuthorized(serverPlayer)) {
                serverPlayer.sendMessage(Text.literal("Вам нужно войти или зарегистрироваться с помощью /login или /reg"), true);
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    private static boolean isAuthorized(ServerPlayerEntity player) {
        return loggedInPlayers.contains(player.getUuid());
    }

    public static void authorizePlayer(UUID uuid) {
        loggedInPlayers.add(uuid);
    }

    public static void deauthorizePlayer(UUID uuid) {
        loggedInPlayers.remove(uuid);
    }
}
