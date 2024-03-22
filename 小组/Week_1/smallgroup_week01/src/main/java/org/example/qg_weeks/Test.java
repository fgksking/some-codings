package org.example.qg_weeks;

import java.sql.Connection;

public class Test{

    public static void main(String[] args) {
        ConnectionPoolManager poolManager =new ConnectionPoolManager();
        MyConnection myConnection=new MyConnection();
        for (int i = 0; i < 8; i++) {
            Thread thread =new Thread(myConnection,"线程"+i);
            thread.start();
        }


        try {
            Thread.sleep(1000);
            myConnection.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }



}
class MyConnection implements Runnable{

    @Override
    public void run() {
        Connection connection = ConnectionPoolManager.getConnection();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        ConnectionPoolManager.releaseConnection(connection);

    }
    public void close(){
        ConnectionPoolManager.closeAllConnection();
    }
}

