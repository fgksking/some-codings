package org.example.qg_week1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


//
public class CRUD_Utils {

    /*
    @params sql传入sql语句
    @params params 可变参数
    * */

    public static int executeUpdate(String sql, Object... params) {
        Connection con = null;
        PreparedStatement stm = null;
        int result;
        try {
            con = JDBCutil.getconnection();
            stm = con.prepareStatement(sql);
            //给可变参数送入sql命令
            for (int i = 1; i <= params.length; i++) {
                stm.setObject(i, params[i]);
            }
             result = stm.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCutil.close(con,stm,null);
        }
        return result;
    }
/*
@params hander 处理的结果集
@params <T> 具体操作的实体类
要返回一个T类型的实例
* */
    public static <T> List<T> query(String sql,MyHander<T> hander,Object...params) {

        Connection con =null;
        PreparedStatement stm =null;
        ResultSet resultSet =null;

        try {
            con=JDBCutil.getconnection();
            stm=con.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                stm.setObject(i,params[i]);
            }
            resultSet = stm.executeQuery();
            return hander.handle(resultSet);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            JDBCutil.close(con,stm,resultSet);


        }


    }
/*
考虑到结果集有多个实类对象数据
返回一个实类的集合//
* *//*     难点：如何从外部泛型类获取泛型对应的实例
   public static <T> List<T> queryList(String sql, MyHander<T> hander, Object...params) {
        Connection con = null;
        PreparedStatement stm = null;
        Class<T> cls;
        try {
            con=JDBCutil.getconnection();
            stm=con.prepareStatement(sql);
            for (int i = 1; i <=params.length ; i++) {
                stm.setObject(i,params[i]);
            }
            ResultSet resultSet = stm.executeQuery();
            //虽然泛型会在字节码编译过程中被擦除，
            // 但是Class对象会通过java.lang.reflect.Type 记录其实现的接口和继承的父类信息


            while(resultSet.next()){


            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }


    }
*/

}