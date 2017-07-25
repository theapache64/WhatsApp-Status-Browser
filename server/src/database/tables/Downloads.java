package database.tables;

import database.Connection;
import models.Download;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by theapache64 on 25/7/17.
 */
public class Downloads extends BaseTable<Download> {

    private static final Downloads instance = new Downloads();
    public static final String COLUMN_TYPE = "type";

    private Downloads() {
        super("downloads");
    }

    public static Downloads getInstance() {
        return instance;
    }

    @Override
    public boolean add(Download download) throws SQLException {
        String error = null;
        final String query = "INSERT INTO downloads (user_id, type) VALUES (?,?);";
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, download.getUserId());
            ps.setString(2, download.getType());
            final boolean isAdded = ps.executeUpdate() == 1;
            if (!isAdded) {
                error = "Failed to add new download";
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            error = e.getMessage();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (error != null) {
            throw new SQLException(error);
        }

        return true;
    }
}
