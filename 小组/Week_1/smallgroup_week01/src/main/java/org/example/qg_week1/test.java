package org.example.qg_week1;

import java.util.List;

public class test {
    public static void main(String[] args) {
        String sql ="select * from student1";
      /*  Student student =CRUD_Utils.query(sql,new MyHander<>(Student.class));*/
        List<Student> list=CRUD_Utils.query(sql,new MyHander<>(Student.class));
        System.out.println(list);

    }
}
