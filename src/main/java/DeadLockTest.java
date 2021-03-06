/**
 * 〈一句话功能简述〉<br>
 * 〈死锁〉
 *
 * @author lixuanfeng
 * @create 2019/1/28
 * @since 1.0.0
 */
public class DeadLockTest {

    /**
     * 功能描述: 死锁的产生
     * 1、互斥条件：只线程对已经获取到的资源进行排它性使用，即该资源同时只由一个线程
     * 占用。如果此时还有其他线程请求资源获取该资源，则请求这只能等待，
     * 直至占有资源的线程释放该资源。
     * 2、请求并持有条件：只一个线程已经持有了至少一个资源，但又提出了新的资源请求，而
     * 新资源已被其他线程占有，所以当前线程会被阻塞，但阻塞的同时并
     * 不释放自己已获取的资源。
     * 3、不可剥夺条件：指线程获取到的资源在自己使用完之前不能被其他线程抢占，只有在自
     * 己使用完毕后才由自己释放该资源。
     * 4、环路等待条件：指在发生死锁时，必然存在一个线程---资源的环形链，即线程集合{
     * T0,T1,T2,···,Tn}中的T0正在等待一个T1占用资源，T1占用的资源
     * ，T1正在等待T2占用的资源，····Tn正在等待已被T0占用的资源。
     */

    //创建资源
    private static Object resourceA = new Object();
    private static Object resourceB = new Object();

    public static void main1(String[] args) {
        //创建线程A
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread() + "get ResourceA");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread() + "waiting get ResourceB");
                    synchronized (resourceB) {
                        System.out.println(Thread.currentThread() + "get ResourceB");
                    }
                }
            }
        });

        //创建线程B
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + "get ResourceB");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread() + "waiting get ResourceA");
                    synchronized (resourceA) {
                        System.out.println(Thread.currentThread() + "get ResourceA");
                    }
                }
            }
        });

        threadA.start();
        threadB.start();

    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("main is over");
    }
}