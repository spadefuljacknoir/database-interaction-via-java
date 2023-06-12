package org.example;
import java.sql.*;
public class Country {
    private int id;
    private String name;
    private String nationality;
    private boolean existence;

    // Конструктор, без параметрів
    public Country() {
    }
    // Конструктор, з параметрами
    public Country(int id, String name, String nationality, boolean existence) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.existence = existence;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getNationality() {
        return nationality;
    }
    public boolean isExistence() {
        return existence;
    }
    // Метод addData використовується для додавання даних до бази даних.
    // Він приймає рядки name і nationality, а також значення existence типу boolean.
    // Метод підключається до бази даних і створює запит за допомогою оператора SQL - PreparedStatement.
    // В параметри запиту встановлюються значення, отримані з аргументів методу.
    // Зовнішній ключ у таблиці є автоматично згенерованим, тому його не потрібно передавати в запит.
    public void addData(String name, String nationality, boolean existence){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop", "root", "1234");
            Statement statement = connection.createStatement();
            PreparedStatement sql = connection.prepareStatement("insert into countries "
                    +" (`name`, `nationality`, `existence`)"
                    +" values (?, ?, ?)");
            sql.setString(1, name);
            sql.setString(2,nationality);
            sql.setBoolean(3, existence);
            sql.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    // Метод deleteElement використовується для видалення елемента з бази даних.
    // Він приймає цілочисельний аргумент id, який є первинним ключем елемента.
    // Метод підключається до бази даних і видаляє елемент з таблиці, використовуючи введений id як умову.
    public void deleteElement(int id){
                try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop", "root", "1234");
            Statement statement = connection.createStatement();
                    PreparedStatement sql = connection.prepareStatement("delete from countries"
                            +" where id=?");
                    sql.setString(1, String.valueOf(id));
                    sql.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public Country getById(int id){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop", "root", "1234");
            Statement statement = connection.createStatement();
            PreparedStatement el = connection.prepareStatement("select * from countries"
            +" where id =?");
            el.setString(1, String.valueOf(id));
            ResultSet resultSet = el.executeQuery();
            if (resultSet.next()) {
                Country country = new Country(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("nationality"), resultSet.getBoolean("existence"));
                return country;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public void update(int id, String name, String nationality, boolean existence){
                try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop", "root", "1234");
            Statement statement = connection.createStatement();
                    PreparedStatement sql = connection.prepareStatement("update countries set"
                            +" `name` = ?"
                            +", `nationality` = ?"
                            +", `existence` = ?"
                            +" where (`id`=?)");
                    sql.setString(1, name);
                    sql.setString(2, nationality);
                    sql.setBoolean(3, existence);
                    sql.setInt(4, id);
                    sql.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
