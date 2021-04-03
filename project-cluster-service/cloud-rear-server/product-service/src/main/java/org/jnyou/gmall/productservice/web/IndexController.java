package org.jnyou.gmall.productservice.web;

import org.jnyou.gmall.productservice.entity.CategoryEntity;
import org.jnyou.gmall.productservice.service.CategoryService;
import org.jnyou.gmall.productservice.vo.web.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author JnYou
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 跳转首页面
     * @param model
     * @Author JnYou
     */
    @GetMapping({"/", "index.html"})
    public String indexPage(Model model) {
        // 查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Category();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catelog2Vo>> getCatelogJson(){
        Map<String, List<Catelog2Vo>> objectMap = categoryService.getCatelogJson();
        return objectMap;
    }

    /**
     * redisson的强大之处：（内部有一个看门狗（LockWatchdogTimeout）机制）
     *  1、锁的自动续期：如果业务处理时间超长，运行期间自动给锁续上新的30s（默认时间）。不用担心业务时间长，锁自动过期被删掉的问题。前提是自动解锁时间一定要大于业务执行时间
     *  2、解决死锁问题：加锁的业务只要运行完成，就不会给当前的锁续期，即使不手动解锁，锁默认在30s以后自动删除。
     *
     * 看门狗（LockWatchdogTimeout）实现原理：
     *      如果指定了锁的超时时间，就发送给Redis执行lua脚本执行，进行占锁，默认超时就是我们指定的时间；不会执行看门狗机制。
     *      未指定锁的超时时间。就使用30 * 1000 【LockWatchdogTimeout看门狗的默认时间30s】，只要占锁成功，就会启动一个定时任务，重新给锁设置过期时间，新的过期时间就是看门狗的默认时间
     *      啥时候蓄积？this.internalLockLeaseTime / 3L 。 当前看门狗默认时长/3 。 也就是10s蓄积一次，蓄积到满时间，也就是20s的时候就会蓄积一次到30s
     *    总结：推荐使用lock.lock(30, TimeUnit.SECONDS);这种方式，跳过看门狗机制，过期时间设置稍微大点，如果这个时间还是有异常，那就是程序有问题了。
     *
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        // 1、获取一把可重入锁（可避免死锁的锁），只要锁的名字一样。就是同一把锁
        RLock lock = redisson.getLock("my-lock");

        //  lock.lock(10, TimeUnit.SECONDS); 会报错抛出异常，在锁到期之后，不会自动续期，因为自动解锁时间一定要大于业务执行时间
        // 加锁
//        lock.lock(); // 阻塞式等待，相当于自旋方式. 默认加的锁为30s，Redisson内部提供了一个监控锁的看门狗，有自动蓄积的锁时长，执行完成后30s就释放锁
        // 看门狗（LockWatchdogTimeout） 实现原理：
        // 如果指定了锁的超时时间，就发送给Redis执行lua脚本执行，进行占锁，默认超时就是我们指定的时间；不会执行看门狗机制。
        // 未指定锁的超时时间。就使用30 * 1000 【LockWatchdogTimeout看门狗的默认时间30s】，只要占锁成功，就会启动一个定时任务，重新给锁设置过期时间，新的过期时间就是看门狗的默认时间
        // 啥时候蓄积？this.internalLockLeaseTime / 3L 。 当前看门狗默认时长/3 。 也就是10s蓄积一次，蓄积到满时间，也就是20s的时候就会蓄积一次到30s
        // 最佳实践
        // 推荐使用lock.lock(30, TimeUnit.SECONDS);这种方式，跳过看门狗机制，过期时间设置稍微大点，如果这个时间还是有异常，那就是程序有问题了。
        lock.lock(30, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功，执行业务。。。" + Thread.currentThread().getId());
            Thread.sleep(30000); // 30s业务时间
        }catch (Exception e) {

        } finally {
            // 解锁
            System.out.println("释放锁" + Thread.currentThread().getId());
            lock.unlock();
        }
        return "hello";
    }

    /**
     * 写锁：保存一定能读取到最新的数据，写锁也就是一个排它锁（互斥锁）
     * 读 + 读：相当于无锁，并发读，只会在Redis中记录好，所有当前的读锁，他们都会同时加锁
     * 写+ 读：等待写锁释放
     * 写+ 写：阻塞方式
     * 读 + 写：有读锁。写也需要等待
     * 总结：只要有写的操作，都必须等待
     *
     * @Author JnYou
     */
    @GetMapping("write")
    @ResponseBody
    public String writeValue() {
        // 创建一把读写锁
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        // 获取写锁
        RLock rLock = lock.writeLock();
        try {
            // 加写锁
            rLock.lock();
            System.out.println("写锁加锁成功" + Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("writeValue",s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("写锁释放锁成功" + Thread.currentThread().getId());
            rLock.unlock();
        }
        return s;
    }

    /**
     * 读锁：是一个共享锁，写锁没释放读锁就必须等待
     * @Author JnYou
     */
    @GetMapping("read")
    @ResponseBody
    public String readValue() {
        // 创建一把读写锁
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        RLock rLock = lock.readLock();
        try {
            rLock.lock();
            System.out.println("读锁加锁成功" + Thread.currentThread().getId());
            s = redisTemplate.opsForValue().get("writeValue");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("读锁释放锁成功" + Thread.currentThread().getId());
            rLock.unlock();
        }
        return s;
    }

    // 信号量
    /**
     * 车位停车
     * 3车位
     * 场景适应限流操作
     * @Author JnYou
     */
    @GetMapping("park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.acquire(); // 获取一个信号，也就是一个值，阻塞式等待
        boolean b = park.tryAcquire();// 尝试获取是否有车位，没有算了
        if(b){
            // 执行业务
        } else {
            return "当前流量过大，请稍后再试！";
        }
        return "ok";
    }

    @GetMapping("go")
    @ResponseBody
    public String go() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.release(); // 释放一个车位
        return "ok";
    }

    // 闭锁
    /**
     * 放假，锁门
     * 5个班级学生全部走完，才能锁大门
     *
     * @Author JnYou
     */
    @GetMapping("lockDoor")
    @ResponseBody
    public String lockDoor () throws InterruptedException {

        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);
        // 等待闭锁都完成
        door.await();
        return "放假了";
    }

    @GetMapping("gogogo/{id}")
    @ResponseBody
    public String gogogo (@PathVariable Long id) {

        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown(); // 计数减一
        return id + "班的人都走完了";
    }


}
