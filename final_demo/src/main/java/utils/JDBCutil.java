package utils;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;


public class JDBCutil {
    private static final String user;
    private static final String password;
    private static final String url;

/*user=root
password=fhk123
url=jdbc:mysql://localhost:3306/demo2*/


// 直接得到Statement语句
    public static Connection getconnection(){
        Connection con =null;

        try {
            con= DriverManager.getConnection(url,user,password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return con;
        }
    }

    //关闭资源
    public static void close(Connection con, Statement st, ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
            if(st!=null){
                st.close();
            }
            if(con!=null){
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    static {
        try {
            //得到配置文件
            //加载配置文件
            Properties properties =new Properties();
            properties.load(new FileInputStream("C:\\Users\\fhkin\\IdeaProjects\\final_demo\\src\\main\\resources\\Mysql.properties"));
            user= properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");

            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }






}
