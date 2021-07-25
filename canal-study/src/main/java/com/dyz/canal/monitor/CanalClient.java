package com.dyz.canal.monitor;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.dyz.canal.handle.HandleData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CanalThread canalThread = new CanalThread();
        canalThread.setCanalHost(canalHost);
        canalThread.setCanalPort(canalPort);
        canalThread.setUsername(username);
        canalThread.setPassword(password);
        canalThread.setSubscribe(subscribe);
        canalThread.setIsOpenCanal(isOpenCanal);
        canalThread.start();
    }
}
