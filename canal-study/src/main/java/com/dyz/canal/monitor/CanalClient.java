package com.dyz.canal.monitor;

import com.dyz.canal.handle.CanalConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Component
@Slf4j
public class CanalClient implements ApplicationContextAware {

    @Value("${canal.client.host}")
    private String canalHost;

    @Value("${canal.client.port}")
    private Integer canalPort;

    @Value("${canal.client.username}")
    private String username;

    @Value("${canal.client.password}")
    private String password;

    @Value("${canal.client.subscribe}")
    private String subscribe;

    @Value("${canal.client.isOpenCanal}")
    private Boolean isOpenCanal;

    @Value("${canal.client.example}")
    private String example;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CanalThread canalThread = new CanalThread();
        // 守护线程是程序运行的时候在后台提供一种通用服务的线程。所有用户线程停止，进程会停掉所有守护线程，退出程序。
        canalThread.setDaemon(true);
        canalThread.setCanalHost(canalHost);
        canalThread.setCanalPort(canalPort);
        canalThread.setUsername(username);
        canalThread.setPassword(password);
        canalThread.setSubscribe(subscribe);
        canalThread.setIsOpenCanal(isOpenCanal);
        canalThread.setExample(example);
        //注册HandleData
        Map<String, CanalConsumer> canalConsumers = applicationContext.getBeansOfType(CanalConsumer.class);
        List<CanalConsumer> list = new ArrayList(canalConsumers.values());
        canalThread.setCanalConsumers(list);
        canalThread.start();
    }
}
