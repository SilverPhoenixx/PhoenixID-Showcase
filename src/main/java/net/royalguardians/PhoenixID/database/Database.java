package net.royalguardians.PhoenixID.database;

import net.royalguardians.PhoenixID.user.LanguageEnum;
import net.royalguardians.PhoenixID.user.UserID;
import java.sql.*;
import java.util.UUID;
import java.util.function.Consumer;

public class Database {
    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;

    private Connection connection;
    private DatabaseThread thread;


    public Database(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.thread = new DatabaseThread();
        thread.start();
        createTable();
    }

    public void executeUpdate(PreparedStatement statement) {
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }


    public DatabaseThread getThread() {
        return thread;
    }

    public void addRunnable(Runnable runnable) {
        thread.getRunnables().add(runnable);
        thread.resumeThread();
    }

    public void fetchAsynchronous(String syntax, Consumer<ResultSet> comsumer) {
        try {
            PreparedStatement stmt = connection.prepareStatement(syntax);
            comsumer.accept(stmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CREATE
    public void createTable() {
        addRunnable(() -> {
            try
            {
                connect();
                Statement stm = connection.createStatement();
                stm.executeUpdate("CREATE TABLE IF NOT EXISTS PhoenixID (ID int auto_increment, `UUID` varchar(48), `Name` varchar(16), `ShortCut` varchar(4), `Language` varchar(8), Primary Key(ID), Unique(UUID))");
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        });
    }

    //INSERT
    public void getPlayer(String name, UUID uuid, Consumer<UserID> callback) {

        addRunnable(() -> {
            connect();
            updatePlayer(name, uuid);
            fetchAsynchronous("SELECT * FROM PhoenixID WHERE UUID = '" + uuid.toString() + "'", resultSet -> {
                try {
                    while (resultSet.next()) {
                        UserID id = new UserID(resultSet.getInt("ID"), resultSet.getString("ShortCut"), LanguageEnum.getEnumByLang(resultSet.getString("Language")));
                        close();
                        callback.accept(id);
                        resultSet.close();
                        break;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    callback.accept(null);
                }
            });
            close();
        });
    }

    public void updatePlayer(String name, UUID uuid) {
        try {
            // "UPDATE Worlds SET Creator = ? WHERE World = ?"
            PreparedStatement  stmt = connection.prepareStatement("INSERT INTO PhoenixID(UUID, Name, Shortcut, Language) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE Name = ?");
            stmt.setString(1, uuid.toString());
            stmt.setString(2, name);
            String names = name.replaceAll("[^A-Za-z0-9]", "");
            String s = names.substring(0, 1) + names.substring(names.length()/2, names.length()/2+1);
            stmt.setString(3, s);
            stmt.setString(4, "ENG");
            stmt.setString(5, name);
            executeUpdate(stmt);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getIdByName(String name, Consumer<Integer> callback) {
        addRunnable(() -> {
            connect();
            fetchAsynchronous("SELECT ID FROM PhoenixID WHERE Name = '" + name + "'", resultSet -> {
                try {
                    while (resultSet.next()) {
                        int i = resultSet.getInt("ID");
                        callback.accept(i);
                        resultSet.close();
                        break;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    callback.accept(null);
                }
            });
            close();
        });
    }
    public void getIdByUniqueID(String uuid, Consumer<Integer> callback) {
        addRunnable(() -> {
            connect();
            fetchAsynchronous("SELECT ID FROM PhoenixID WHERE UUID = '" + uuid + "'", resultSet -> {
                try {
                    while (resultSet.next()) {
                        int i = resultSet.getInt("ID");
                        callback.accept(i);
                        resultSet.close();
                        break;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    callback.accept(null);
                }
            });
            close();
        });
    }

    public  void getName(int id, Consumer<String> callback) {
        addRunnable(() -> {
            connect();
            fetchAsynchronous("SELECT NAME FROM PhoenixID WHERE ID = '" + id + "'", resultSet -> {
                try {
                    while (resultSet.next()) {
                        String i = resultSet.getString("NAME");
                        callback.accept(i);
                        resultSet.close();
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    callback.accept(null);
                }
            });
            close();
        });
    }

    //DELETE
    public  void removePlayer(UUID uuid) {
        addRunnable(() -> {
            try {
                connect();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM PhoenixID WHERE UUID = ?");
                stmt.setString(1, "" + uuid);
                executeUpdate(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        });
    }
    //UPDATE
    public void setShortCut(UUID id, String shortcut) {
        addRunnable(() -> {
            connect();
            try {
                PreparedStatement stmt = connection.prepareStatement("UPDATE PhoenixID SET ShortCut = ? WHERE UUID = ?");
                stmt.setString(1, shortcut);
                stmt.setString(2, id.toString());
                executeUpdate(stmt);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        });
    }

    public  void setLanguage(UUID id, String lang) {
        addRunnable(() -> {
            connect();
            try {
                PreparedStatement stmt = connection.prepareStatement("UPDATE PhoenixID SET Language = ? WHERE UUID = ?");
                stmt.setString(1, lang);
                stmt.setString(2, id.toString());
                executeUpdate(stmt);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        });
    }

    public void connect() {
        try {
            if(connection == null || connection.isClosed() || connection.isValid(0)) Connection();
        } catch (SQLException ex) {
        }
    }

    public  void Connection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException ex) {
        }
    }

}