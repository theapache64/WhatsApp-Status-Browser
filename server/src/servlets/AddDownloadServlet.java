package servlets;

import database.tables.Downloads;
import models.Download;
import org.json.JSONException;
import utils.APIResponse;
import utils.Request;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by theapache64 on 25/7/17.
 */
@WebServlet(urlPatterns = {AdvancedBaseServlet.VERSION_CODE + "/add_download"})
public class AddDownloadServlet extends AdvancedBaseServlet {

    @Override
    public boolean isBinaryServlet() {
        return false;
    }

    @Override
    protected boolean isSecureServlet() {
        return true;
    }

    @Override
    protected String[] getRequiredParameters() {
        return new String[]{
                Downloads.COLUMN_TYPE
        };
    }

    @Override
    protected void doAdvancedPost() throws JSONException, Request.RequestException, IOException, SQLException {
        final String userId = getHeaderSecurity().getUserId();
        Downloads.getInstance().add(new Download(null, userId, getStringParameter(Downloads.COLUMN_TYPE)));
        getWriter().write(new APIResponse("Added download", null).getResponse());
    }
}
