package database.tables;


import database.Connection;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 15/10/16.
 */
public class Users extends BaseTable<User> {

    public static final String COLUMN_API_KEY = "api_key";
    private static final Users instance = new Users();
    public static final String COLUMN_IMEI = "imei";
    public static final String COLUMN_DEVICE_HASH = "device_hash";
    public static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_AS_TOTAL_DOWNLOADS = "total_downloads";
    private static final String COLUMN_AS_TOTAL_VIDEOS_DOWNLOADED = "total_videos_downloaded";
    private static final String COLUMN_AS_TOTAL_PHOTOS_DOWNLOADED = "total_photos_downloaded";
    private static final String COLUMN_AS_LAST_HIT = "last_hit";

    private Users() {
        super("users");
    }

    public static Users getInstance() {
        return instance;
    }

    public List<User> getAll() {
        List<User> users = null;
        final String query = "SELECT u.is_active, u.id, u.name, u.email, u.imei, COUNT(d.id) AS total_downloads, ( SELECT COUNT(id) FROM downloads WHERE user_id = u.id AND TYPE = 'VIDEO' ) AS total_videos_downloaded, ( SELECT COUNT(id) FROM downloads WHERE user_id = u.id AND TYPE = 'PHOTO' ) AS total_photos_downloaded, (SELECT created_at FROM downloads WHERE user_id = u.id ORDER BY id DESC LIMIT 1) AS last_hit FROM users u LEFT JOIN downloads d ON d.user_id = u.id GROUP BY u.id";
        final java.sql.Connection con = Connection.getConnection();
        try {
            final Statement stmt = con.createStatement();
            final ResultSet rs = stmt.executeQuery(query);

            if (rs.first()) {
                users = new ArrayList<>();
                do {
                    final String id = rs.getString(COLUMN_ID);
                    final String name = rs.getString(COLUMN_NAME);
                    final String imei = rs.getString(COLUMN_IMEI);
                    final String email = rs.getString(COLUMN_EMAIL);
                    final int totalDownloads = rs.getInt(COLUMN_AS_TOTAL_DOWNLOADS);
                    final int totalVideosDownloads = rs.getInt(COLUMN_AS_TOTAL_VIDEOS_DOWNLOADED);
                    final int totalPhotosDownloaded = rs.getInt(COLUMN_AS_TOTAL_PHOTOS_DOWNLOADED);
                    final String lastHit = rs.getString(COLUMN_AS_LAST_HIT);
                    final boolean isActive = rs.getBoolean(COLUMN_IS_ACTIVE);

                    users.add(new User(id, name, email, imei, null, null, isActive, totalDownloads, totalVideosDownloads, totalPhotosDownloaded, lastHit));
                } while (rs.next());
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    @Override
    public boolean add(User newUser) throws SQLException {

        boolean isUserAdded = false;
        final String query = "INSERT INTO users (name,imei,device_hash,api_key,email) VALUES (?,?,?,?,?);";
        final java.sql.Connection con = Connection.getConnection();
        try {

            final PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, newUser.getName());
            ps.setString(2, newUser.getIMEI());
            ps.setString(3, newUser.getDeviceHash());
            ps.setString(4, newUser.getApiKey());
            ps.setString(5, newUser.getEmail());

            isUserAdded = ps.executeUpdate() == 1;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!isUserAdded) {
            throw new SQLException("Failed to add new user, please try again.");
        }

        return true;
    }

    @Override
    public User get(String column, String value) {

        User user = null;

        final String query = String.format("SELECT id,name,email, imei,device_hash,api_key,is_active FROM users WHERE %s = ? LIMIT 1 ", column);

        final java.sql.Connection con = Connection.getConnection();
        try {

            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, value);
            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                final String id = rs.getString(COLUMN_ID);
                final String name = rs.getString(COLUMN_NAME);
                final String email = rs.getString(COLUMN_EMAIL);
                final String imei = rs.getString(COLUMN_IMEI);
                final String deviceHash = rs.getString(COLUMN_DEVICE_HASH);
                final String apiKey = rs.getString(COLUMN_API_KEY);
                final boolean isActive = rs.getBoolean(COLUMN_IS_ACTIVE);

                user = new User(id, name, email, imei, apiKey, deviceHash, isActive, -1, -1, -1, null);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    @Override
    public boolean update(User user) throws SQLException {
        boolean isUpdated = false;
        final String query = "UPDATE users SET name = ? , email = ?, imei = ?, device_hash = ?, api_key = ?, updated_at = NOW() WHERE id = ?";
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getIMEI());
            ps.setString(4, user.getDeviceHash());
            ps.setString(5, user.getApiKey());
            ps.setString(6, user.getId());

            isUpdated = ps.executeUpdate() == 1;
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!isUpdated) {
            throw new SQLException("Failed to update user");
        }

        return true;
    }

}
