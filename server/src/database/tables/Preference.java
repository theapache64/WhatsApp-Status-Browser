package database.tables;


import database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by theapache64 on 27/8/16.
 */
public class Preference extends BaseTable<String> {

    private static final String COLUMN_KEY = "_key";
    private static final String COLUMN_VALUE = "_value";
    public static final String KEY_GMAIL_USERNAME = "gmail_username";
    public static final String KEY_GMAIL_PASSWORD = "gmail_password";
    public static final String KEY_ADMIN_EMAIL = "admin_email";
    public static final String KEY_FILENAME_FORMAT = "filename_format";
    public static final String KEY_IS_DEBUG_DOWNLOAD = "is_debug_download";
    public static final String KEY_APK_URL = "apk_url";
    private static Preference instance = new Preference();

    private Preference() {
        super("preferences");
    }

    public static Preference getInstance() {
        return instance;
    }


    @Override
    public String get(String column, String key) {
        String value = null;
        final String query = String.format("SELECT _value FROM preference WHERE %s = ? LIMIT 1", column);
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, key);

            final ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                value = rs.getString(COLUMN_VALUE);
            }

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
        return value;
    }


    public String getString(final String key) {
        return get(COLUMN_KEY, key);
    }

}
