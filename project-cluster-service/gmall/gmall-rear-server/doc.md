feign调用流程：

* 1、构造请求数据，将对象转为json
       
       RequestTemplate template = this.buildTemplateFromArgs.create(argv);
* 2、发送请求进行执行（执行成功会解码响应数据）

        return this.executeAndDecode(template, options);
* 3、执行请求会有重试机制
           
         while(true) {
                     try {
                         return this.executeAndDecode(template, options);
                     } catch (RetryableException var9) {
                         RetryableException e = var9;
         
                         try {
                              // 重试机制
                             retryer.continueOrPropagate(e);
                         } catch (RetryableException var8) {
                             Throwable cause = var8.getCause();
                             if (this.propagationPolicy == ExceptionPropagationPolicy.UNWRAP && cause != null) {
                                 throw cause;
                             }
         
                             throw var8;
                         }
                     }
                 }

看门狗：LockWatchdogTimeout原理：
     
         public String hello(){
             // 1、获取一把可重入锁（可避免死锁的锁），只要锁的名字一样。就是同一把锁
             RLock lock = redisson.getLock("my-lock");
     
             // 加锁
     //        lock.lock(); // 阻塞式等待，相当于自旋方式. 默认加的锁为30s，Redisson内部提供了一个监控锁的看门狗，有自动蓄积的锁时长，执行完成后30s就释放锁
             // 看门狗（LockWatchdogTimeout） 实现原理：
             // 如果指定了锁的超时时间，就发送给Redis执行lua脚本执行，进行占锁，默认超时就是我们指定的时间
             // 没有指定时间。就使用30 * 1000 【LockWatchdogTimeout看门狗的默认时间30000L】，只要占锁成功，就会启动一个定时任务，重新给锁设置过期时间，新的过期时间就是看门狗的默认时间
             // 啥时候蓄积？this.internalLockLeaseTime / 3L 。 当前看门狗默认时长/3 。 也就是10s蓄积一次，蓄积到满时间，也就是20s的时候就会蓄积一次到30s
             // 最佳实践
             lock.lock(30, TimeUnit.SECONDS);
             try {
                 System.out.println("加锁成功，执行业务。。。" + Thread.currentThread().getId());
                 Thread.sleep(30000);
             }catch (Exception e) {
     
             } finally {
                 // 解锁
                 System.out.println("释放锁" + Thread.currentThread().getId());
                 lock.unlock();
             }
             return "hello";
         }