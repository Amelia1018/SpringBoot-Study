package com.example.demo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.demo.distributeLockCache.Redis_Timeout;

/**
 * @description 简单的基于数据库的分布式锁的示例
 * @date 2024/11/6  23:09
 * 获取锁(在locks表中插入一条记录)->释放锁（删除对应一条记录）
 *要考虑锁的超时机制和并发机制
 */
public class  distributeLockDB{

    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public  static boolean acquireLock(String lockname, int timeout) {
        long startTime = System.currentTimeMillis();
        while(true){
            try(Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
                PreparedStatement  stmt = connection.prepareStatement("INSERT INTO `LOCKS`(`name`,`holder`) VALUES (?,?)");

                stmt.setString(1,lockname);
                stmt.setString(2,"1");
                stmt.executeUpdate();
            } catch (SQLException e) {
               if(System.currentTimeMillis()-startTime > timeout){
                   //超时
                   return false;
               }
               try {
                   Thread.sleep(100);
               }catch (InterruptedException ie) {
                   //线程中断
                   Thread.currentThread().interrupt();
               }
            }
        }

    }

    public static void releaseLock(String lockname, int redis_Timeout) {
        try(Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM `LOCKS` WHERE `name` = ?");
            stmt.setString(1, lockname);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void DoSometingWithLock() {
        String lockname = "lock";
        if(acquireLock(lockname,5000)){
            try{
                //执行加锁操作
                System.out.println("锁请求-->");
                Thread.sleep(5000);
                System.out.println(("结束"));
            }catch(InterruptedException e){
                //线程中断
                Thread.currentThread().interrupt();
            }finally{
                releaseLock(lockname, Redis_Timeout);
            }
        }else{
            System.out.println("失败获取锁...");
        }
    }

    public static void main(String[] args) {
        DoSometingWithLock();
    }

}


