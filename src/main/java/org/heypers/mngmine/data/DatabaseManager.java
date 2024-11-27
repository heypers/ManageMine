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

package org.heypers.mngmine.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:player_auth.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, password TEXT)";
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}