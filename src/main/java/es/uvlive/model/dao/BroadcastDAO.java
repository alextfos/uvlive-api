package es.uvlive.model.dao;

import es.uvlive.model.Broadcast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BroadcastDAO extends BaseDAO {
    private static final String QUERY_GET_MESSAGES = "SELECT * FROM " + BROADCAST_TABLE;

    public List<Broadcast> getBroadcasts() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = query(QUERY_GET_MESSAGES);
        ArrayList<Broadcast> broadcastList = new ArrayList<>();

        while (resultSet != null && resultSet.next()) {
            Broadcast broadcast = new Broadcast();

            broadcast.setTitle(resultSet.getString(TITLE_FIELD));
            broadcast.setText(resultSet.getString(TEXT_FIELD));

            broadcastList.add(broadcast);
        }

        return broadcastList;
    }
}
