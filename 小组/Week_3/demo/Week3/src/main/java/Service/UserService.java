package Service;

import Dao.CRUD_Utils;
import Dao.MyHander;
import singleClass.User;

import java.util.List;

public class UserService {
    public static User login(String username,String password){
        String mysql="select * from user where username=? and password=?";
        List<User> users = CRUD_Utils.query(mysql,new MyHander<>(User.class),username,password);
        System.out.println("User的信息："+users);
        if(users.size()==0){

            return null;
        }
        return users.get(0);

    }



}
