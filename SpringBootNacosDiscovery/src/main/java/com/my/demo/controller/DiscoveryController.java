package com.my.demo.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("discovery")
public class DiscoveryController {

    @Autowired
    RestTemplate restTemplate;

    @NacosInjected
    private NamingService namingService;

    // http://localhost:8081/discovery/get?serviceName=greeting
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        List<Instance> list =  namingService.getAllInstances(serviceName);
        return list;
    }

    // http://localhost:8081/discovery/callService?serviceName=greeting&value=ABC
    @RequestMapping(value = "/callService", method = RequestMethod.GET)
    @ResponseBody
    public String callService(@RequestParam String serviceName, @RequestParam String value) throws NacosException {
        Instance inst;

        // NacosNamingService nns = (NacosNamingService)namingService;
        // inst = nns.selectOneHealthyInstance(serviceName);

        List<Instance> list =  namingService.getAllInstances(serviceName);
        if (list.size() > 0)
        {
            System.out.println(list.get(0).getClusterName());
            System.out.println(list.get(0).getInstanceId());
            System.out.println(list.get(0).getInstanceIdGenerator());
            System.out.println(list.get(0).getIp());
            System.out.println(list.get(0).getMetadata());
            System.out.println(list.get(0).getPort());
            System.out.println(list.get(0).getServiceName());
            System.out.println(list.get(0).getWeight());

            inst = list.get(0);
            String url = "http://" + inst.getIp() + ":" + inst.getPort() + "/" + serviceName + "/?name=" + value;

            return getResult(url);
        }

        return "Unknown";

    }

    private String getResult(String url)
    {
        System.out.println("getResult, url=" + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
//        ResponseEntity<String> re = restTemplate.exchange("http://localhost:8080/greeting?name=ABC", HttpMethod.GET, entity, String.class);
        ResponseEntity<String> re = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(re.getBody());

        return re.getBody();
    }
}