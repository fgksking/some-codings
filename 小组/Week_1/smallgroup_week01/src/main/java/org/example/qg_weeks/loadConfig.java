package org.example.qg_weeks;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class loadConfig {
    //配置的参数


    private String user;
    private String password;
    private  String url;

    private int initialPoolSize;
    private int maxPoolSize;
    private int timeout = 5000;


       public loadConfig(){
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("C:\\Users\\fhkin\\IdeaProjects\\Hello\\src\\poolsetting.properties"));
            //用反射去获得字段的值
       /* for(Object pro:properties.keySet()){
            String fiedname=pro.toString();
            Field fied = this.getClass().getDeclaredField(fiedname);
            Method methid = this.getClass().getMethod()


        }*/
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            initialPoolSize = Integer.valueOf(properties.getProperty("initialPoolSize"));
            maxPoolSize = Integer.valueOf(properties.getProperty("maxPoolSize"));
            timeout = Integer.valueOf(properties.getProperty("timeout"));

            Class.forName("com.mysql.cj.jdbc.Driver");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

//加载配置文件


    public Connection CreateConnection(){
        try {
            Connection con = DriverManager.getConnection(url,user,password);
            return  con;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }




}
