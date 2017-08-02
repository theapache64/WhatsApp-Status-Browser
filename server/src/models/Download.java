package models;

/**
 * Created by theapache64 on 25/7/17.
 */
public class Download {

    private final String id, userId, type, actionType;

    public Download(String id, String userId, String type, String actionType) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.actionType = actionType;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getActionType() {
        return actionType;
    }
}
