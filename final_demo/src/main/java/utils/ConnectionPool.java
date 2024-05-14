package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/*
* 连接池
* 存放连接线程的数量
* */
public class ConnectionPool {

    private Boolean isAllClose=false;
    //加载配置文件
    loadConfig config;
    //记录已存在的连接数
    private Integer createdConnection;

    public void setCreatedConnection() {
        this.createdConnection = config.getInitialPoolSize();
    }

    //加载配置类
    public ConnectionPool(loadConfig config) {
        this.config = config;
        init();
        setCreatedConnection();
    }

//预热（初始化连接池）

  //  ArrayList<Connection> freePools =new ArrayList<>();
    private List<Connection> freePools = Collections.synchronizedList(new ArrayList<>());
 //   ArrayList<ConnectionEntity> usePool = new ArrayList<>();
    private Vector<ConnectionEntity> usePool =new Vector<>();

    //用数组的形式记录连接是否被在占用
    //可以考虑在数组封装一个包含connection字段、isbusy、以及运行时间的类
   // Connection[] connection = new Connection[config.getMaxPoolSize()];
    //0表示空闲  1表示被占用
   // int[]  isbusy= new int[config.getMaxPoolSize()];

    public void init(){
        for (int i = 0; i < config.getInitialPoolSize(); i++) {

            Connection con = config.CreateConnection();
            freePools.add(con);

        }

    }



    //同步方法
    //从连接池获得连接的方法（判断线程的占用情况）
    public synchronized Connection getConnection(){
        if(usePool==null||freePools==null)
            return null;
        Connection connection =null;
        check();
        //判断连接池里的链接是否空闲
        try {
            if(!freePools.isEmpty()){
                //连接池非空
                connection= freePools.get(0);
                freePools.remove(0);
                if(connection==null||connection.isClosed())
                {
                    connection=getConnection();
                }
            }else//连接池空
            {
                if(createdConnection<=config.getInitialPoolSize()){
                    //创建新连接
                    connection= config.CreateConnection();
                    createdConnection++;
                }else{

                        System.out.println( Thread.currentThread().getName() + ",连接池最大连接数为:"+config.getMaxPoolSize()+"已经满了，需要等待..." );
                        wait( 2000 );
                        //回调看连接池是否有空闲连接
                        connection=getConnection();
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        ConnectionEntity connectionEntity=new ConnectionEntity(connection,System.currentTimeMillis());
        usePool.add(connectionEntity);
        System.out.println(Thread.currentThread().getName()+",获取并使用连接:"+connection+",空闲线程数："+freePools.size()+","+
                "再使用线程数:"+usePool.size()+",总的线程数:"+createdConnection);
        return connection;




    }




    //
    //释放链接到连接池

    public synchronized void releaseConnection(Connection con){
        try {
            if(usePool==null||freePools==null)
                return;
            if(con==null)
            {
                return;
            }
            if(!con.isClosed()&&con!=null && !freePools.contains(con)){
                freePools.add(con);
                for (int i = 0; i < usePool.size(); i++) {
                    if(usePool.get(i).getConnection()==con){
                        usePool.remove(i);
                    }
                }
                System.out.println("回收了一个链接"+con+"空闲连接数为"+freePools.size());
                notifyAll();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    //检查链接是否超时

    private synchronized void check(){
        ConnectionEntity connection=null;
        if(usePool==null||freePools==null)
        return;

        for (int i = 0; i < usePool.size(); i++) {
            connection=usePool.get(i);
            long currentTime=System.currentTimeMillis();
            //连接超时
            if(currentTime- connection.getUseStartTime()> config.getTimeout()){
                try {

                    //移除并关闭操作
                    usePool.remove(i).getConnection().close();
                    createdConnection--;
                    System.out.println("使用地连接已超时  连接总数"+createdConnection);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }
    //销毁链接池,,强制性关闭所有连接
    public synchronized void closeAllConnection(){
        Connection con=null;
        try {
            for (int i = 0; i < usePool.size(); i++) {
                con=usePool.get(i).getConnection();
                if(con==null)
                    break;
                con.close();
                System.out.println("使用中的连接"+i+"关闭");
            }
            for (Connection connection : freePools){
                if(connection==null)
                    break;
                connection.close();
                System.out.println("空闲地连接关闭");
            }

            usePool.removeAll(usePool);
            freePools.removeAll(freePools);
            usePool=null;
            freePools=null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }










}
