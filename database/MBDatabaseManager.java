package database;


import models.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MBDatabaseManager {
    private final ConnectionManager connectionManager;

    public MBDatabaseManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Connection getConnection() throws SQLException {
        return connectionManager.getConnection();
    }

    public List<MusicBand> loadMusicBands() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM musicbands");
        ResultSet result = statement.executeQuery();

        List<MusicBand> musicBands = new LinkedList<>();
        while (result.next()) {
            int sales = result.getInt("sales");
            Label label = new Label(sales);

            Integer numberofParticipants = result.getInt("number_of_participants");
            if (result.wasNull()) numberofParticipants = null;
            Integer singlecount = result.getInt("single_count");
            if (result.wasNull()) singlecount = null;


            MusicBand musicBand = new MusicBand(result.getInt("id"), result.getString("name"),
                    new Coordinates(result.getInt("x"), result.getInt("y")),
                    result.getTimestamp("creation_date").toLocalDateTime(),
                    numberofParticipants, singlecount,
                    result.getString("genre") == null ? null : MusicGenre.valueOf(result.getString("genre")), label);
            musicBand.setCreatorId(result.getInt("creator_id"));
            musicBands.add(musicBand);
        }

        connection.close();
        return musicBands;
    }

    public int addMusicBand(User user, MusicBand musicBand) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO musicbands(name, x, y, single_count, sales, number_of_participants, genre," +
                        "creator_id)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?::genre," +
                        "(SELECT id FROM users WHERE users.name=?)) " +
                        "RETURNING id"
        );

        statement.setString(1, musicBand.getName());
        statement.setInt(2, musicBand.getCoordinates().getX());
        statement.setInt(3, musicBand.getCoordinates().getY());

        statement.setInt(4, musicBand.getSingleCount());
        int sales = musicBand.getLabel().getSales();

        statement.setInt(5, sales);
        statement.setInt(6, musicBand.getNumberOfParticipants());


        if (musicBand.getGenre() == null) statement.setNull(7, Types.VARCHAR);
        else statement.setString(7, musicBand.getGenre().toString());

        statement.setString(8, user.getName());


        ResultSet result = statement.executeQuery();

        connection.close();

        result.next();

        return result.getInt(1);
    }

    public int updateMusicBand(User user, int musicbandId, MusicBand newMusicBand) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE musicbands SET name = ?, x = ?, y = ?, single_count = ?, " +
                        "sales = ?, number_of_participants = ?, genre = ?::genre " +
                        "WHERE id = ? AND creator_id = ?"
        );//name, x, y, single_count, sales, number_of_participants, genre

        statement.setString(1, newMusicBand.getName());
        statement.setInt(2, newMusicBand.getCoordinates().getX());
        statement.setInt(3, newMusicBand.getCoordinates().getY());

        statement.setInt(4, newMusicBand.getSingleCount());
        int sales = newMusicBand.getLabel().getSales();

        statement.setInt(5, sales);
        statement.setInt(6, newMusicBand.getNumberOfParticipants());


        if (newMusicBand.getGenre() == null) statement.setNull(7, Types.VARCHAR);
        else statement.setString(7, newMusicBand.getGenre().toString());
        statement.setInt(8, musicbandId);
        statement.setInt(9, user.getId());
        int res = statement.executeUpdate();
        connection.close();
        return res;
    }

    public int clearMusicBand(User user) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement_musicbands = connection.prepareStatement(
                "DELETE FROM musicbands WHERE creator_id = ?"
        );
        statement_musicbands.setInt(1, user.getId());
        int res = statement_musicbands.executeUpdate();

        connection.close();
        return res;
    }

    public int removeMusicBand(User user, MusicBand musicBand) throws SQLException {
        Connection connection = getConnection();

        PreparedStatement statement_musicband = connection.prepareStatement(
                "DELETE FROM musicbands WHERE creator_id = ? AND id = ?"
        );

        statement_musicband.setInt(1, user.getId());
        statement_musicband.setInt(2, musicBand.getId());
        int res = statement_musicband.executeUpdate();

        connection.close();
        return res;
    }
}