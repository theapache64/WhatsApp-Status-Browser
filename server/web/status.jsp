
<%@ page import="java.util.List" %>
<%@ page import="models.User" %>
<%@ page import="database.tables.Users" %><%--
  Created by IntelliJ IDEA.
  User: theapache64
  Date: 8/12/16
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>API Status</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body><%--
final String id = rs.getString(COLUMN_ID);
final String name = rs.getString(COLUMN_NAME);
final String imei = rs.getString(COLUMN_IMEI);
final String email = rs.getString(COLUMN_EMAIL);
final long totalRequests = rs.getLong(COLUMN_AS_TOTAL_REQUESTS);
final long totalDownloads = rs.getLong(COLUMN_AS_TOTAL_DOWNLOADS);
final long totalTracks = rs.getLong(COLUMN_AS_TOTAL_TRACKS);
final String lastHit = rs.getString(COLUMN_AS_LAST_HIT);
final boolean isActive = rs.getBoolean(COLUMN_IS_ACTIVE);--%>
<div class="container">
    <h2>SoundCloudDownloader</h2>
    <p>User statistics</p>
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>Name</td>
            <td>IMEI</td>
            <td>EMail</td>
            <td>Total downloads</td>
            <td>Videos downloaded</td>
            <td>Photos photos</td>
            <td>Last hit</td>
        </tr>
        </thead>
        <tbody>

        <%
            final List<User> users = Users.getInstance().getAll();
            if (users != null) {
                for (final User user : users) {
        %>
        <tr>
            <td><%=user.getId()%>
            </td>
            <td><%=user.getName()%>
            </td>
            <td><%=user.getIMEI()%>
            </td>
            <td><%=user.getEmail()%>
            </td>
            <td><%=user.getTotalRequests()%>
            </td>
            <td><%=user.getTotalDownloads()%>
            </td>
            <td><%=user.getTotalTracks()%>
            </td>
            <td><a target="_blank" href="<%=user.getLastHit()%>">Click here</a>
            </td>
            <td><%=user.isActive() ? "YES" : "NO"%>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

    <p>Client statistics</p>
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <td>ID</td>
            <td>Name</td>
            <td>Total hits</td>
            <td>isActive</td>
        </tr>
        </thead>
        <tbody>

        <%
            final List<SCClient> clients = SCClients.getInstance().getAll();
            if (clients != null) {
                for (final SCClient scClient : clients) {
        %>
        <tr>
            <td>
                <%=scClient.getId()%>
            </td>

            <td>
                <%=scClient.getName()%>
            </td>

            <td>
                <%=scClient.getTotalHits()%>
            </td>

            <td>
                <%=scClient.isActive()%>
            </td>

        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

</div>
</body>
</html>
