package service;

import po.Company;

import java.util.List;

public interface ComService {

    void create_Com(String ComName,String ComSize,String ComDirection,boolean  ComIspublic);
    boolean Is_Com_exist(String ComName);
    boolean  create_Com_relation(String ComName,String username,String role);
    List<Company> getCom();

    List<Company> getCom(String params);
}
