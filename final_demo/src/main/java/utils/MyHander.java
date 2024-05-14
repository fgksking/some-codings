package utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyHander <T>{
    //得到泛型类的准确类型
    private Class<T> clazz;
    ArrayList<T> list =new ArrayList<T>();

    public MyHander(Class<T> clazz) {
        this.clazz = clazz;
    }
    //
    public List<T> handle(ResultSet resultSet)  {

        try {
            while(resultSet.next()){

                T obj =  clazz.newInstance();
                //从clazz获取javabean对象
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz,Object.class);
                //获取所有属性描述器
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    declaredField.setAccessible(true);
                    Object object = resultSet.getObject(declaredField.getName());
                    declaredField.set(obj,object);
                }
                /*return obj;*/
                list.add(obj);
            }
            return list;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
      /*  catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }*/

    }
    //第二种方法从result结果集中获得javabean对象，也是反射





}
