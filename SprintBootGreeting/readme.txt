服务注册与消费

SpringBootGreeting (:8080) ------------register ----> Nacos (192.168.1.179:8848)
                                                       |
SpringBootNacosDiscovery (:8081) <---- discovery ------|


register service [greeting] :  curl -X PUT 'http://192.168.1.179:8848/nacos/v1/ns/instance?serviceName=greeting&ip=127.0.0.1&port=8080&ephemeral=false'
consume service             :  curl http://localhost:8081/discovery/get?serviceName=greeting -i


===============================================


http://localhost:8080/greeting

http://localhost:8080/greeting?name=User

===============================================

服务注册

方式一

ref: https://nacos.io/zh-cn/docs/open-api.html

## 临时实例
curl -X PUT 'http://192.168.1.179:8848/nacos/v1/ns/instance?serviceName=greeting&ip=127.0.0.1&port=8080'

## 永久实例
## ephemeral boolean 是否临时实例
curl -X PUT 'http://192.168.1.179:8848/nacos/v1/ns/instance?serviceName=greeting&ip=127.0.0.1&port=8080&ephemeral=false'


方式二

@NacosInjected // 使用nacos client的NacosInjected注解注入服务
private NamingService namingService;

@RequestMapping(value = "/set", method = RequestMethod.GET)
@ResponseBody
public String set(@RequestParam String serviceName) {
    try {
        namingService.registerInstance(serviceName, "192.168.1.179", 8848); // 注册中心的地址
        return "OK";
    } catch (NacosException e) {
        e.printStackTrace();
        return "ERROR";
    }
}


调用 http://127.0.0.1:8080/discovery/set?serviceName=greeting，注册服务