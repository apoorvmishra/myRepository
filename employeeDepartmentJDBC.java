
import java.io.File;
import java.sql.*;
import java.util.*;

public class employeeDepartmentJDBC {

    private static Connection connection = null;

    public static void main(String args[]) throws NumberFormatException {

        String url = "jdbc:mysql://localhost/homework4db";
        String user = "root";
        String password = "Apoorv_93";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);
            createTables();
            File file = new File("C:\\Users\\apoor\\Desktop\\transfile.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
               String transaction = scanner.nextLine();
               int transactionType = Integer.parseInt(transaction.split(" +")[0]);
               switch (transactionType){
                   case 1:
                       deleteExistingEmployee(transaction);
                       break;
                   case 2:
                       insertNewEmployee(transaction);
                       break;
                   case 3:
                       insertInDepartment(transaction);
                       break;
                   case 4:
                       getAverageSalaryOfAllEmployees();
                       break;
                   case 5:
                       getEmployeeNamesForManager(transaction);
                       break;
                   case 6:
                       getAverageSalaryForEmployeesUnderAManager(transaction);
                       break;
                   default:
                       System.out.println("Invalid Transaction Type");
               }
            }
            dropTables();
            connection.close();
        } catch (Exception Exception){
            System.out.println("Exception" + Exception);
        }
    }

    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("create table employee" +
                "(eid INT NOT NULL," +
                "name VARCHAR(255) NOT NULL, " +
                "salary INT," +
                "did INT," +
                "PRIMARY KEY (eid))");
        statement.execute("create table department" +
                "(did INT NOT NULL," +
                "mid INT, " +
                "PRIMARY KEY (did))");
        statement.close();
    }

    public static void deleteExistingEmployee(String transaction) throws SQLException, NumberFormatException {
        int eid = Integer.parseInt(transaction.split(" +")[1]);
        PreparedStatement statement = connection.prepareStatement("select * from employee where eid = ?");
        statement.setInt(1, eid);
        ResultSet resultset = statement.executeQuery();
        if(!resultset.next()){
            System.out.println("ERROR! Employee doesn't exist");
        }
        else {
            statement = connection.prepareStatement("delete from employee where eid = ?");
            statement.setInt(1, eid);
            statement.executeUpdate();
            statement = connection.prepareStatement("update department set mid = NULL where mid = ?");
            statement.setInt(1, eid);
            statement.executeUpdate();
            statement.close();
            System.out.println("Employee with mid : " + eid + " removed successfully.");
            printTables();
        }
    }

    public static void insertNewEmployee(String transaction) throws SQLException, NumberFormatException {
        int eid = Integer.parseInt(transaction.split(" +")[1]);
        String name = transaction.split(" +")[2];
        int salary = Integer.parseInt(transaction.split(" +")[3]);
        int did = Integer.parseInt(transaction.split(" +")[4]);
        PreparedStatement prepareStatement = connection.prepareStatement("select * from department where did = ?");
        prepareStatement.setInt(1, did);
        ResultSet resultSet = prepareStatement.executeQuery();
        prepareStatement = connection.prepareStatement("select * from employee where eid = ?");
        prepareStatement.setInt(1, eid);
        ResultSet duplicateResultSet = prepareStatement.executeQuery();
        if(duplicateResultSet.next()){
            System.out.println("ERROR! Employee already exists");
        }
        else {
            if (!resultSet.next()) {
                System.out.println("Department ID does not exist");
            } else {
                prepareStatement = connection.prepareStatement("insert into employee values (?, ?, ?, ?)");
                prepareStatement.setInt(1, eid);
                prepareStatement.setString(2, name);
                prepareStatement.setInt(3, salary);
                prepareStatement.setInt(4, did);
                prepareStatement.executeUpdate();
                System.out.println("SUCCESS!! Employee added successfully.");
            }
        }
        prepareStatement.close();
        printTables();
    }

    public static void insertInDepartment(String transaction) throws SQLException, NumberFormatException {
        int did = Integer.parseInt(transaction.split(" +")[1]);
        PreparedStatement prepareStatement = connection.prepareStatement("select * from department where did = ?");
        prepareStatement.setInt(1, did);
        ResultSet resultSet = prepareStatement.executeQuery();
        if(resultSet.next()){
            if (transaction.split(" +").length > 2) {
                int mid = Integer.parseInt(transaction.split(" +")[2]);
                prepareStatement = connection.prepareStatement("update department set mid = ? where did = ?");
                prepareStatement.setInt(1, mid);
                prepareStatement.setInt(2, did);
            }
            else {
                prepareStatement = connection.prepareStatement("update department set mid = NULL where did = ?");
                prepareStatement.setInt(1, did);
            }
        }
        else {
            if (transaction.split(" +").length > 2) {
                int mid = Integer.parseInt(transaction.split(" +")[2]);
                prepareStatement = connection.prepareStatement("insert into department values (?, ?)");
                prepareStatement.setInt(1, did);
                prepareStatement.setInt(2, mid);
            } else {
                prepareStatement = connection.prepareStatement("insert into department values (?, NULL)");
                prepareStatement.setInt(1, did);
            }
        }
        prepareStatement.executeUpdate();
        prepareStatement.close();
        printTables();
    }

    public static void getAverageSalaryOfAllEmployees() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select round(avg(salary)) as Average_Salary from employee");
        resultSet.absolute(1);
        System.out.println("Average Salary Of All Employees: " + resultSet.getInt(1));
        statement.close();
    }

    public static void getEmployeeNamesForManager(String transaction) throws SQLException, NumberFormatException {
        int mid = Integer.parseInt(transaction.split(" +")[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("select name from employee as e " +
                                                                                "natural inner join department " +
                                                                                "where mid = ? And e.eid != mid");
        preparedStatement.setInt(1, mid);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Employee Names under Manager with mid: " + mid);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        preparedStatement.close();
    }

    public static void getAverageSalaryForEmployeesUnderAManager(String transaction) throws SQLException, NumberFormatException {
        int mid = Integer.parseInt(transaction.split(" +")[1]);
        PreparedStatement preparedStatement = connection.prepareStatement("select round(avg(salary)) as AVERAGE_SALARY from employee as e " +
                                                                               "natural inner join department " +
                                                                               "where mid = ? and e.eid != mid");
        preparedStatement.setInt(1, mid);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next()){
            System.out.println("ERROR! No employees found under manager with mid: " + mid + ". So, AVERAGE = 0.");
        }
        resultSet.absolute(1);
        System.out.println("Average Salary Of Employees under Manager with mid: " + mid + " is " + resultSet.getInt(1));
        preparedStatement.close();
    }

    public static void printTables() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("\nEmployee Table: \n");
        ResultSet resultSet = statement.executeQuery("select * from employee");
        while(resultSet.next()) {
            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) +  " " +
                    resultSet.getInt(3) + " " + resultSet.getInt(4));
        }
        System.out.println("\nDepartment Table: \n");
        resultSet = statement.executeQuery("select * from department");
        while(resultSet.next()) {
            System.out.println(resultSet.getInt(1) + " " + resultSet.getInt(2));
        }
        statement.close();
    }

    public static void dropTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table employee");
        statement.execute("drop table department");
        statement.close();
    }
}
