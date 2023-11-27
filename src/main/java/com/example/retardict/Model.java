package com.example.retardict;

import java.sql.*;

public class Model {

    private static Connection connection = null;
    public static boolean addUserDefinedWord(UserDefinedWord word) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/testdb.db");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO userDefinedWords VALUES (?, ?);");
            preparedStatement.setString(1, word.getWord());
            preparedStatement.setString(2, word.getMeaning());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // <-- This is important
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static void deleteUserDefinedWord(String word) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/testdb.db");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM userDefinedWords WHERE word = ?;");
            preparedStatement.setString(1, word);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // <-- This is important
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void editUserDefinedWord(String oldWord, String newWord) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/testdb.db");
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE userDefinedWords SET word = ? WHERE word = ?;");
            preparedStatement.setString(1, newWord);
            preparedStatement.setString(2, oldWord);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // <-- This is important
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int isBookmarked(Word word) {
        int isBookmarked = 0;
        try {
            String query = "SELECT isBookmarked FROM words WHERE word = " + word.getWord() + ";";
            connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
            Statement statement = connection.createStatement();
//            statement.setString(1, word.getWord());
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                isBookmarked = resultSet.getInt("isBookmarked");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        }
        return isBookmarked;
    }

    public static void bookmarkWord(Word word) {
        try {
            connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE words SET isBookmarked = 1 WHERE (word = ?);");
            preparedStatement.setString(1, word.getWord());
            System.out.println("words = " + word.getWord());
            System.out.println("preparedStatement = " + preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // <-- This is important
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void unbookmarkWord(Word word) {
        try {
            connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE words SET isBookmarked = 0 WHERE word = (?);");
            preparedStatement.setString(1, word.getWord());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // <-- This is important
                } catch (SQLException e) {
                    /* handle exception */
                }
            }
        }
    }
}
