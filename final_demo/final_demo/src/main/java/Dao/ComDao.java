package Dao;

import po.Company;

import java.util.List;

public interface ComDao {
     void create_Com(String ComName, String ComSize, String ComDirection, boolean ComIspublic);

     boolean Is_Com_exist(String ComName);
     //创建Com_relation表，公司负责人
     boolean create_Com_relation(String ComName,String username,String role);
     //

     //展示所有公开企业
    List<Company> getCom();
    //搜索公司
    List<Company> getCom(String params);


}
