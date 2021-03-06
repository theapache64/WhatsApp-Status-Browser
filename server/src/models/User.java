package models;

/**
 * Created by theapache64 on 15/10/16.
 */
public class User {

    private final String id, name, email, imei, apiKey, deviceHash;
    private final boolean isActive;
    private final int totalDownloads, totalVideosDownloaded, totalPhotosDownloaded;
    private final int totalViews, totalVideosViewed, totalPhotosViewed;
    private final String lastHit;

    public User(String id, String name, String email, String imei, String apiKey, String deviceHash, boolean isActive, int totalDownloads, int totalVideosDownloaded, int totalPhotosDownloaded, int totalViews, int totalVideosViewed, int totalPhotosViewed, String lastHit) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imei = imei;
        this.apiKey = apiKey;
        this.deviceHash = deviceHash;
        this.isActive = isActive;
        this.totalDownloads = totalDownloads;
        this.totalVideosDownloaded = totalVideosDownloaded;
        this.totalPhotosDownloaded = totalPhotosDownloaded;
        this.totalViews = totalViews;
        this.totalVideosViewed = totalVideosViewed;
        this.totalPhotosViewed = totalPhotosViewed;
        this.lastHit = lastHit;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public int getTotalVideosViewed() {
        return totalVideosViewed;
    }

    public int getTotalPhotosViewed() {
        return totalPhotosViewed;
    }

    public int getTotalDownloads() {
        return totalDownloads;
    }

    public int getTotalVideosDownloaded() {
        return totalVideosDownloaded;
    }

    public int getTotalPhotosDownloaded() {
        return totalPhotosDownloaded;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDeviceHash() {
        return deviceHash;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getIMEI() {
        return imei;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imei='" + imei + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", deviceHash='" + deviceHash + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public String getLastHit() {
        return lastHit;
    }
}
