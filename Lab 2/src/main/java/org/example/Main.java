package org.example;
// Java Core
import java.sql.Connection; // Java Core - JDBC
import java.sql.DriverManager; // Java Core - JDBC
import java.sql.ResultSet; // Java Core - JDBC
import java.sql.Statement; // Java Core - JDBC
// JavaFX UI
import javafx.application.Application; // JavaFX - Application
import javafx.event.ActionEvent; // JavaFX - Event
import javafx.event.EventHandler; // JavaFX - Event
import javafx.scene.Scene; // JavaFX - Scene
import javafx.scene.control.TableColumn; // JavaFX - UI Controls
import javafx.scene.control.TableView; // JavaFX - UI Controls
import javafx.scene.control.TextField; // JavaFX - UI Controls
import javafx.scene.control.cell.PropertyValueFactory; // JavaFX - UI Controls
import javafx.scene.layout.VBox; // JavaFX - Layout
import javafx.scene.text.Text; // JavaFX - Text
import javafx.stage.Stage; // JavaFX - Stage
import javafx.scene.control.Button; // JavaFX - UI Controls
// JavaFX Collections
import javafx.collections.FXCollections; // JavaFX - Collections
import javafx.collections.ObservableList; // JavaFX - Collections

// Дочірний клас Main розширює Application та реалізує хендлер івентів:
public class Main extends Application implements EventHandler<ActionEvent>{
    int id;
    String name;
    String nationality;
    boolean existence;
    Button btnAdd;
    Button btnDelete;
    Button btnEdit;
    int idUpdate;
    String nameUpdate;
    String nationalityUpdate;
    boolean existenceUpdate;

    TableView<Country> table;
    // Вхід до мейну:
    public static void main(String[] args) {
        launch(args);
    }
    // Метод start перевизначений!
    // Створює VBox, таблицю та додає колонки, які відображатимуть наші поля об'єкту Country.
    // Додає текстові поля, де введені значення будуть присвоєні полям класу.
    // Додає кнопку btnAdd.
    // Усі створені елементи додаються до нашої сцени.
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Лабораторна №2");

        btnAdd = new Button("Додати");
        btnAdd.setOnAction(this);
        btnEdit = new Button("Змінити");
        btnEdit.setOnAction(this);
        btnDelete = new Button("Видалити");
        btnDelete.setOnAction(this);

        TableColumn<Country, Integer> idColumn = new TableColumn<Country, Integer>("ID");
        idColumn.setMinWidth(20);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Country, String> nameColumn = new TableColumn<Country, String>("Name");
        nameColumn.setMinWidth(80);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Country, String> nationalityColumn = new TableColumn<Country, String>("Nationality");
        nationalityColumn.setMinWidth(80);
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        TableColumn<Country, Boolean> existenceColumn = new TableColumn<Country, Boolean>("Existence");
        existenceColumn.setMinWidth(50);
        existenceColumn.setCellValueFactory(new PropertyValueFactory<>("existence"));

        // Створення та налаштування таблиці
        TableView<Country> table = new TableView<>();
        table.setItems(getAll());
        table.getColumns().addAll(idColumn, nameColumn, nationalityColumn, existenceColumn);

        // Створення та налаштування контейнера VBox
        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);

        // Створення та налаштування текстового поля для введення імені
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setFocusTraversable(false);
        nameField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                name = nameField.getText();
            }
        });

        // Створення та налаштування текстового поля для введення національності
        TextField nationalityField = new TextField();
        nationalityField.setPromptText("Nationality");
        nationalityField.setFocusTraversable(false);
        nationalityField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nationality = nationalityField.getText();
            }
        });

        // Створення та налаштування текстового поля для введення існування (Так або Ні)
        TextField existenceField = new TextField();
        existenceField.setPromptText("Existence (Yes or No)");
        existenceField.setFocusTraversable(false);
        existenceField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                existence = Boolean.parseBoolean(existenceField.getText());
            }
        });

        // Створення текстового об'єкту для виведення повідомлення
        Text text1 = new Text("Додайте нову країну з параметрами (не забувайте про Enter)");
        vBox.getChildren().add(text1);
        // Додавання полів для введення даних у контейнер VBox
        vBox.getChildren().add(nameField); // Поле для введення імені
        vBox.getChildren().add(nationalityField); // Поле для введення національності
        vBox.getChildren().add(existenceField); // Поле для введення існування (Так або Ні)
        // Додавання кнопки btnAdd до контейнера VBox
        vBox.getChildren().add(btnAdd);
        // Створення текстового поля idField для введення ідентифікатора
        // Значення, яке буде введено у поле, буде присвоєно полю класу id
        // Додавання тексту, текстового поля та кнопки btnDelete до головного вікна
        TextField idField = new TextField();
        idField.setPromptText("ID");
        idField.setFocusTraversable(false);
        idField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                id = Integer.parseInt(idField.getText());
            }
        });
        // Створення текстового об'єкту для виведення повідомлення
        Text text2 = new Text("Видалення країни за айді");
        vBox.getChildren().add(text2);
        // Додавання полів для введення даних та кнопки btnDelete до контейнера VBox
        vBox.getChildren().add(idField); // Поле для введення ідентифікатора
        vBox.getChildren().add(btnDelete);

        // Створення текстового поля idUpdateField для введення ідентифікатора для оновлення
        // Після введення знаходиться об'єкт з відповідним ідентифікатором
        // Створення текстових полів, в які автоматично записуються дані об'єкту класу Country, введені дані (або дефолтні, якщо не було змін) записуються у поля класу Main
        // Додавання текстових полів та кнопки btnEdit до вікна
        TextField idUpdateField = new TextField();
        idUpdateField.setPromptText("ID");
        idUpdateField.setFocusTraversable(false);
        idUpdateField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                idUpdate = Integer.parseInt(idUpdateField.getText());
                Country p = new Country();
                Country country = p.getById(idUpdate);
                if (country != null) {
                    // Створення текстових полів для оновлення даних об'єкту
                    TextField nameUpdateField = new TextField(country.getName());
                    nameUpdateField.setFocusTraversable(false);
                    nameUpdateField.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            nameUpdate = nameUpdateField.getText();
                        }
                    });
                    TextField nationalityUpdateField = new TextField(country.getNationality());
                    nationalityUpdateField.setFocusTraversable(false);
                    nationalityUpdateField.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            nationalityUpdate = nationalityUpdateField.getText();
                        }
                    });
                    TextField existenceUpdateField = new TextField(Boolean.toString(country.isExistence()));
                    existenceUpdateField.setFocusTraversable(false);
                    existenceUpdateField.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            existenceUpdate = Boolean.parseBoolean(existenceUpdateField.getText());
                        }
                    });
                    // Додавання текстових полів та кнопки btnEdit до вікна
                    vBox.getChildren().add(nameUpdateField);
                    vBox.getChildren().add(nationalityUpdateField);
                    vBox.getChildren().add(existenceUpdateField);
                    vBox.getChildren().add(btnEdit);
                }
            }
        });
        Text text3 = new Text("Змінити інформацію про країну (не забувайте про Enter!)");
        vBox.getChildren().add(text3);
        vBox.getChildren().add(idUpdateField);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }
    // Метод getAll отримує всі дані з таблиці countries, для кожного запису товару створює об'єкт класу Country,
    // додає об'єкти до списку та повертає список об'єктів
    public ObservableList<Country> getAll(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop", "root", "1234");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from countries");
            while (resultSet.next()){
                Country country = new Country(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("nationality"), resultSet.getBoolean("existence"));
                allCountries.add(country);

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return allCountries;
    }
    // Метод handle використовується для обробки подій.
    // Якщо кнопка btnAdd була натиснута, виконується додавання до бази даних за допомогою методу класу Country.
    // Якщо кнопка btnDelete була натиснута, видаляється рядок з таблиці бази даних за вказаним індексом.
    // Якщо кнопка btnEdit була натиснута, дані оновлюються.
    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource()==btnAdd){
            Country country = new Country();
            country.addData(name, nationality, existence);
        }
        if(actionEvent.getSource()==btnDelete){
            Country country = new Country();
            country.deleteElement(id);
        }
        if(actionEvent.getSource()==btnEdit){
            Country country = new Country();
            country.update(idUpdate, nameUpdate, nationalityUpdate, existenceUpdate);
        }
    }
}