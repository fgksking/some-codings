package Dao.UserDaoImpl;

import Dao.ComDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.Company;
import utils.CRUD_Utils;
import utils.JDBCutil;
import utils.MyHander;

import java.util.List;

public class ComDaoImpl implements ComDao {

    public Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void create_Com(String ComName, String ComSize, String ComDirection,boolean ComIspublic) {
        try {
            String mysql="insert into company (ComName,ComSize,ComDirection,ComIspublic) values (?,?,?,?)";
            int result = CRUD_Utils.executeUpdate(mysql,ComName,ComSize,ComDirection,ComIspublic);
        } catch (Exception e) {
            logger.debug("插入公司出错",e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean create_Com_relation(String ComName, String username, String role) {
        String mysql = "insert into com_relation (ComName,username,role) values(?,?,?)";
        int i = CRUD_Utils.executeUpdate(mysql, ComName, username, role);
        if(i==1)
        { return true;}
        else {
            logger.debug("公司负责人表插入出错");
            return false;
        }



    }

    @Override
    public boolean Is_Com_exist(String ComName) {
        String mysql="select * from company where ComName=?";
        List<Company> query = CRUD_Utils.query(mysql, new MyHander<>(Company.class), ComName);
        if(query.isEmpty()){
            logger.info("公司创建成功");
            return true;
        }else {
            logger.info("公司名信息已存在");
            return false;

        }
    }

    @Override
    public List<Company> getCom() {
        String mysql = "select * from company";
        List<Company> query = CRUD_Utils.query(mysql,new MyHander<>(Company.class));
        if(query.isEmpty()){

            return null;
        }else {
           // logger.info("公司名信息已存在");
            return query;

        }
    }

    @Override
    public List<Company> getCom(String params) {
        String mysql = "select * from company where comName LIKE '%"+params+"%'";
        List<Company> query = CRUD_Utils.query(mysql,new MyHander<>(Company.class),params);
        if(query.isEmpty()){

            return null;
        }else {
            return query;

        }
    }
}
