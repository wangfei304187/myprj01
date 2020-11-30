SC01_registerCenter (Eureka Server)
SC02_serviceProvider01
SC03_serviceConsumer01


### eureka server (register center)

[wf@localhost ~]$ cat /etc/hosts
127.0.0.1 server1
127.0.0.1 server2 


-Dspring.profiles.active=es1
-Dspring.profiles.active=es2


###############################################################

###Service Provider

Ref: https://www.cnblogs.com/fengzheng/p/10603672.html


2020-11-30 10:59:06.805  INFO 17844 --- [nio-8761-exec-2] c.n.e.registry.AbstractInstanceRegistry  : Registered instance SINGLE-PROVIDER/192.168.1.212:single-provider:8001 with status UP (replication=true)
2020-11-30 10:59:07.651  INFO 17844 --- [nio-8761-exec-3] c.n.e.registry.AbstractInstanceRegistry  : Registered instance SINGLE-PROVIDER/192.168.1.212:single-provider:8001 with status UP (replication=true)


http://192.168.1.212:8001/hello
http://192.168.1.212:8001/greeting


###############################################################

###Service Consumer

Ref: https://www.cnblogs.com/fengzheng/p/10603672.html


http://localhost:8002/commonRequest
http://localhost:8002/commonRequest2
http://localhost:8002/feignRequest
http://localhost:8002/feignRequest2
http://localhost:8002/feignRequest2?name=ABCD
