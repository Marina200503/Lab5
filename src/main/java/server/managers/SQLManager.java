package server.managers;

import lib.models.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.ZoneId;
import java.util.Base64;
import java.util.LinkedList;

public class SQLManager {
     public static Connection getConnectWhithPostgreSQL(){
        //try (Connection conn = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs","s409114", "TuT90Qil1aGBpdNz");)
         try {
             return DriverManager.getConnection("jdbc:postgresql://localhost:5432/LABA7","postgres", "123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //insert
    public static void createLabWork(LinkedList<LabWork> labWorks) {
        String sql = "INSERT INTO LABWORKS (NAME, CREATION_DATE, MINIMAL_POINT, DIFFICULTY, COORDINATES_X, COORDINATES_Y, AUTHOR_NAME, AUTHOR_BIRTHDAY, AUTHOR_HEIGHT, AUTHOR_WEIGHT, LOCATION_X, LOCATION_Y, LOCATION_Z, LOCATION_NAME, USER_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnectWhithPostgreSQL();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Обход списка labWorks и добавление каждого LabWork в базу данных
            for (LabWork labWork : labWorks) {
                pstmt.setString(1, labWork.getName());
                pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(labWork.getCreationDate()));
                pstmt.setLong(3, labWork.getMinimalPoint());
                pstmt.setInt(4, labWork.getDifficulty().getValue());
                pstmt.setInt(5, labWork.getCoordinates().getX());
                pstmt.setLong(6, labWork.getCoordinates().getY());
                pstmt.setString(7, labWork.getAuthor().getName());
                pstmt.setTimestamp(8, java.sql.Timestamp.valueOf(labWork.getAuthor().getBirthday().toLocalDateTime()));
                pstmt.setLong(9, labWork.getAuthor().getHeight());
                pstmt.setFloat(10, labWork.getAuthor().getWeight());
                pstmt.setFloat(11, labWork.getAuthor().getLocation().getX());
                pstmt.setFloat(12, labWork.getAuthor().getLocation().getY());
                pstmt.setLong(13, labWork.getAuthor().getLocation().getZ());
                pstmt.setString(14, labWork.getAuthor().getLocation().getName());
                pstmt.setInt(15, labWork.getUser_id());
                pstmt.addBatch(); // Добавляем запрос в пакет
            }

            pstmt.executeBatch(); // Выполняем все запросы пакетом

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении лабораторных работ: " + e.getMessage());
        }
    }
    //delete
    public static int deleteElement(int id) {
        String SQL = "DELETE FROM LABWORKS WHERE ID = ?";

        int affectedrows = 0;

        try (Connection conn = getConnectWhithPostgreSQL();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }
    //select
    public static LinkedList<LabWork> selectAllData() {
        String SQL = "SELECT * FROM LabWorks"; // Исправленный SQL-запрос, чтобы выбрать все поля
        LinkedList<LabWork> labWorks = new LinkedList<>(); // Создаем список для хранения объектов LabWork

        try (Connection conn = getConnectWhithPostgreSQL();
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Создаем объект Coordinates
                Coordinates coordinates = new Coordinates(
                        rs.getInt("COORDINATES_X"),
                        rs.getLong("COORDINATES_Y"));

                // Создаем объект Location
                Location location = new Location(
                        rs.getFloat("LOCATION_X"),
                        rs.getFloat("LOCATION_Y"),
                        rs.getLong("LOCATION_Z"),
                        rs.getString("LOCATION_NAME"));

                // Создаем объект Person
                Person person = new Person(
                        rs.getString("AUTHOR_NAME"),
                        ((Timestamp) rs.getObject("AUTHOR_BIRTHDAY")).toInstant().atZone(ZoneId.systemDefault()),
                        rs.getLong("AUTHOR_HEIGHT"),
                        rs.getFloat("AUTHOR_WEIGHT"),
                        location);

                // Создаем объект LabWork
                LabWork labWork = new LabWork(
                        rs.getLong("ID"),
                        rs.getString("NAME"),
                        coordinates,
                        rs.getLong("MINIMAL_POINT"),
                        Difficulty.getEnum(rs.getInt("DIFFICULTY")),
                        rs.getTimestamp("CREATION_DATE").toLocalDateTime(),
                        person,
                        rs.getInt("user_id"));

                labWorks.add(labWork); // Добавляем созданный объект LabWork в список
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return labWorks; // Возвращаем список объектов LabWork
    }
    // Метод для регистрации нового пользователя
    public static boolean registerUser(String username, String password) {
        String hashedPassword = hashPassword(password); // Хэширование пароля

        String sql = "INSERT INTO USERS (LOGIN, PASSWORD) VALUES (?, ?)";
        try (Connection conn = getConnectWhithPostgreSQL(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();//выполнение запроса
            return true; // Регистрация успешна

        } catch (SQLException e) {
            System.out.println("Ошибка при регистрации пользователя: " + e.getMessage());
            return false; // Регистрация не удалась
        }
    }
    // Метод для авторизации пользователя
    public static int authenticateUser(String username, String password) {
        String SQL = "SELECT user_id, password FROM users WHERE login = ?";

        try (Connection conn = getConnectWhithPostgreSQL();
             PreparedStatement pstmt = conn.prepareStatement(SQL)){

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Извлечение хэша пароля из базы данных
                String storedHash = rs.getString("password");


                String hashedInputPassword = hashPassword(password);

                // Сравнение хэшей
                if (storedHash.equals(hashedInputPassword))
                    return rs.getInt("user_id");
                else
                    return 0;
            } else {
                return 0; // Пользователь не найден
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при авторизации пользователя: " + e.getMessage());
            return 0; // Ошибка при авторизации
        }
    }

    public static String hashPassword(String password) {
        try {
            // Создаем экземпляр MessageDigest с алгоритмом SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // Преобразуем пароль в байты и передаем в MessageDigest
            byte[] hashedBytes = md.digest(password.getBytes());

            // Преобразуем результат в строку с помощью Base64
            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хэширования пароля: алгоритм SHA-1 не найден", e);
        }
    }
}

