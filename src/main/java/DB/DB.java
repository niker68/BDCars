package DB;

import Model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static Connection conn;

    //Подключение к бд
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        //Class.forName("org.sqlite.JDBC");
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:bd/Cars");
        System.out.println("DB Connect!");
    }
    //Получение макс id
    private static int getMaxId () throws SQLException, ClassNotFoundException {
        //DB.Conn();
        Statement statement0 = DB.conn.createStatement();
        ResultSet result0 = statement0.executeQuery("SELECT id from cars order by id desc limit 1");
        int maxId = result0.getInt("id");
        statement0.close();
        //DB.CloseDB();
        return maxId;
    }
    //Создание записи
    public static boolean create(Car car) throws SQLException, ClassNotFoundException {
        DB.Conn();
        Statement statement0 = DB.conn.createStatement();
        ResultSet result0 = statement0.executeQuery("SELECT id,gosnomer,model,color,year FROM cars");
        //Проверка существования по id
        boolean carExists = false;
        while (result0.next()) {
            if (car.getGosnomer().equals(result0.getString("gosnomer"))&car.getModel().equals(result0.getString("model"))&car.getColor().equals(result0.getString("color"))&car.getYear()==(result0.getInt("year"))){
                carExists = true;}
        }
        if (!carExists) {
            if (car.getId()==0) {
                car.setId(DB.getMaxId() + 1);
            }
            String sql = "INSERT INTO Cars(id,gosnomer,model,color,year) VALUES(?,?,?,?,?)";
            PreparedStatement statement = DB.conn.prepareStatement(sql);
            statement.setInt(1, car.getId());
            statement.setString(2, car.getGosnomer());
            statement.setString(3, car.getModel());
            statement.setString(4, car.getColor());
            statement.setInt(5,car.getYear());
            // Выполняем запрос
            statement.execute();
            statement0.close();
            DB.CloseDB();
            return true;
        } else{
            statement0.close();
            DB.CloseDB();
            return false;
        }


    }
    //Получение списка всех записей
    public static List<Car> readAll(String filterModel,String filterColor) throws SQLException, ClassNotFoundException {
        DB.Conn();
        ArrayList <Car> listOfAllCars = new ArrayList<Car>();
        PreparedStatement statement0;
        ResultSet result0;
        System.out.println(0);
        System.out.println("filter = "+filterColor+" "+filterModel);
        if (filterColor==null&filterModel!=null) {
            System.out.println(1);
            statement0 = DB.conn.prepareStatement("SELECT * FROM cars WHERE model LIKE ?");
            statement0.setString(1,filterModel);
            result0 = statement0.executeQuery();
        } else if (filterColor!=null&filterModel==null) {
            System.out.println(2);
            statement0 = DB.conn.prepareStatement("SELECT * FROM cars WHERE color LIKE ?");
            statement0.setString(1,filterColor);
            result0 = statement0.executeQuery();
        }else if ((filterColor.length()>0)&(filterModel.length()>0)) {
            System.out.println(3);
            statement0 = DB.conn.prepareStatement("SELECT * FROM cars WHERE model LIKE ? AND color LIKE ?");
            statement0.setString(1,filterModel);
            statement0.setString(2,filterColor);
            result0 = statement0.executeQuery();
        } else {
            System.out.println(4);
            statement0 = DB.conn.prepareStatement("SELECT * FROM cars");
            result0 = statement0.executeQuery();
        }
        while (result0.next()) {
            Car car = new Car(result0.getInt("id"));
            car.setGosnomer(result0.getString("gosnomer"));
            car.setModel(result0.getString("model"));
            car.setColor(result0.getString("color"));
            car.setYear(result0.getInt("year"));
            listOfAllCars.add(car);
        }
        DB.CloseDB();
        return listOfAllCars;
    }
    public static List<Car> readAll() throws SQLException, ClassNotFoundException {
        DB.Conn();
        ArrayList <Car> listOfAllCars = new ArrayList<Car>();
        PreparedStatement statement0;
        ResultSet result0;
        System.out.println(4);
        statement0 = DB.conn.prepareStatement("SELECT * FROM cars");
        result0 = statement0.executeQuery();

        while (result0.next()) {
            Car car = new Car(result0.getInt("id"));
            car.setGosnomer(result0.getString("gosnomer"));
            car.setModel(result0.getString("model"));
            car.setColor(result0.getString("color"));
            car.setYear(result0.getInt("year"));
            listOfAllCars.add(car);
        }
        DB.CloseDB();
        return listOfAllCars;
    }


    //Чтение одной записи
    public static Car read(int id) throws SQLException, ClassNotFoundException {
        DB.Conn();
        Car car = new Car(id);
        Statement statement0 = DB.conn.createStatement();
        ResultSet result0 = statement0.executeQuery("SELECT * FROM cars WHERE id ="+car.getId());
        while (result0.next()) {
            car.setGosnomer(result0.getString("gosnomer"));
            car.setModel(result0.getString("model"));
            car.setColor(result0.getString("color"));
            car.setYear(result0.getInt("year"));
        }
        statement0.close();
        DB.CloseDB();
        return car;
    }
    //Проверка
    public static boolean check(Car car) throws SQLException, ClassNotFoundException {
        DB.Conn();
        ResultSet result0;
        PreparedStatement statement0 =  DB.conn.prepareStatement("SELECT * FROM cars WHERE gosnomer =?&model =?&color =?&year =?");
        if (car.getGosnomer().equals("")){
            statement0.setString(1,null);
        } else {statement0.setString(1,car.getGosnomer());}
        if (car.getModel().equals("")){
            statement0.setString(2,null);
        } else {statement0.setString(2,car.getModel());}
        if (car.getColor().equals("")){
            statement0.setString(3,null);
        } else {statement0.setString(3,car.getColor());}
        {statement0.setInt(4,car.getYear());}

        result0 = statement0.executeQuery();
        if (!result0.next()){
            statement0.close();
            DB.CloseDB();
            return false;

        } else{
        statement0.close();
        DB.CloseDB();
        return true;}
    }
    //Удаление одной записи
    public static void delete(int id) throws SQLException, ClassNotFoundException {
        DB.Conn();
        PreparedStatement statement = DB.conn.prepareStatement("DELETE FROM cars WHERE id = ?");
        statement.setObject(1, id);
        statement.execute();
        DB.CloseDB();
    }
    //Закрытие соединений
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        System.out.println("Connection closed");
    }

}
