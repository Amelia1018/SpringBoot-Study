package com.example.demo;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;

import static com.example.demo.distributeLockDB.releaseLock;

/*在Redis中，可以使用SETNX命令来实现基本的分布式锁。
SETNX命令会尝试在缓存中设置一个键值对，如果键不存在，则设置成功并返回1；
如果键已存在，则设置失败并返回0。
还需要考虑：
缓存的持久性: 确保锁在系统重启或缓存失效时能够正确恢复。
缓存的过期时间: 可以为锁设置了一个过期时间，以防止节点在获取锁后崩溃或异常退出，导致锁一直无法释放。设置合理的过期时间可以避免锁长时间占用而导致的资源浪费。
锁的安全释放:在使用Redis的DEL命令来删除锁时，需要确保操作的原子性，避免误删其他节点持有的锁，避免造成多线程或多节点情况下的并发问题。我们可以引入一个计数器，记录某个线程获取锁的次数。当线程再次尝试获取锁时，只有计数器为0时才会真正获取锁，否则只会增加计数器。释放锁时，计数器减少，直到为0才真正释放锁。
缓存的一致性：在使用多个缓存节点时，确保它们之间存储的数据保持一致性。由于缓存可能会存储临时数据，因此在多个节点同时访问和修改数据时，保持数据的准确性和一致性变得尤为重要。可以选择使用Redis的集群模式或者使用分布式锁库，确保锁在多个节点间的一致性。*/
public class distributeLockCache {
    private static  final  String Redis_Host="127.0.0.1";
    private static  final  int Redis_Port = 6379;
    static  final  int Redis_Timeout = 60000;

    //获取锁
    public static boolean acquireLock(String lockName,int timeout) {
        Jedis jedis = new Jedis();
        long startTime = System.currentTimeMillis();
        while (true) {
            //在使用 Jedis 操作 Redis 之前，我们需要先连接到 Redis 服务器。这里没有连接redis
            String result = jedis.set(lockName, "NX", "EX", timeout);
            if (result != null && result.equals("OK")) {
                //成功获取锁
                return true;
            } else if (System.currentTimeMillis() - startTime > timeout) {
                //超时
                return false;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //线程中断
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    //释放锁
    public static boolean releaseLock(String lockName,int timeout) {
        Jedis jedis = new Jedis();
        //删除
        jedis.del(lockName);
    }

    private static void doSometingWithLock() {
        String lockName = "lock";
        if(acquireLock(lockName,Redis_Timeout)) {
            try{
                System.out.println("请求锁");
                Thread.sleep(5000);
                System.out.println("请求结束");
            }catch(InterruptedException e){
                //线程中断
                Thread.currentThread().interrupt();
            }finally{
                //释放锁
                releaseLock(lockName,Redis_Timeout);
            }
        }else {
            System.out.println("失败请求锁");
        }
    }
    public static void main(String[] args) {
        doSometingWithLock();
    }


}
