RegisterCenter集群
SC01_registerCenter (Eureka Server)  --  -Dspring.profiles.active=es1  (port: 8761)
                                     --  -Dspring.profiles.active=es2  (port: 8762)

serviceA-provider服务提供者集群
SC02_serviceProvider01 (serviceA-provider  -- Eureka Client)  (port: 8001)
SC02_serviceProvider02 (serviceA-provider  -- Eureka Client)  (port: 8002)

服务消费者
SC03_serviceConsumer01 (serviceA-consumer  -- Eureka Client)  (port: 9001)

###############################################################################
###############################################################################


### eureka server (register center)

[wf@localhost ~]$ cat /etc/hosts
127.0.0.1 server1
127.0.0.1 server2 


-Dspring.profiles.active=es1
-Dspring.profiles.active=es2


###############################################################

###Service Provider

Ref: https://www.cnblogs.com/fengzheng/p/10603672.html

http://192.168.1.212:8001/hello
http://192.168.1.212:8001/greeting


###############################################################

###Service Consumer

Ref: https://www.cnblogs.com/fengzheng/p/10603672.html


http://localhost:9001/commonRequest
http://localhost:9001/commonRequest2
http://localhost:9001/feignRequest
http://localhost:9001/feignRequest2
http://localhost:9001/feignRequest2?name=ABCD
