服务注册与消费

SpringBootGreeting (:8080) ------------register ----> Nacos (192.168.1.179:8848)
                                                       |
SpringBootNacosDiscovery (:8081) <---- discovery ------|


register service [greeting] :  curl -X PUT 'http://192.168.1.179:8848/nacos/v1/ns/instance?serviceName=greeting&ip=127.0.0.1&port=8080&ephemeral=false'
consume service             :  curl http://localhost:8081/discovery/get?serviceName=greeting -i


===============================================


方式一

[wf@localhost toPACS]$ curl http://localhost:8081/discovery/get?serviceName=greeting -i

HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 30 Oct 2020 01:08:00 GMT

[{"instanceId":"127.0.0.1#8080#DEFAULT#DEFAULT_GROUP@@greeting","ip":"127.0.0.1","port":8080,"weight":1.0,"healthy":true,"enabled":true,"ephemeral":false,"clusterName":"DEFAULT","serviceName":"DEFAULT_GROUP@@greeting","metadata":{},"instanceIdGenerator":"simple","instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000,"ipDeleteTimeout":30000}]


方式二

http://localhost:8081/discovery/get?serviceName=greeting
http://localhost:8081/discovery/callService?serviceName=greeting&value=ABC
