package org.heypers.mngmine.data;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerAuthManager {

    static {
        DatabaseManager.initializeDatabase();
    }

    public static boolean registerPlayer(UUID uuid, String password) {
        if (isPlayerRegistered(uuid)) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO players (uuid, password) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loginPlayer(UUID uuid, String password) {
        if (!isPlayerRegistered(uuid)) {
            return false;
        }

        String sql = "SELECT password FROM players WHERE uuid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPlayerRegistered(UUID uuid) {
        String sql = "SELECT COUNT(*) FROM players WHERE uuid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}