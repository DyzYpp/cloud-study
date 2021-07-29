package com.dyz.canal.monitor;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.Message;
import com.dyz.canal.handle.CanalConsumer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class CanalThread extends Thread {

    private String canalHost;

    private Integer canalPort;

    private String username;

    private String password;

    private String subscribe;

    private Boolean isOpenCanal;
    private List<CanalConsumer> canalConsumers;

    @Override
    public void run() {
        if (!isOpenCanal) {
            log.info("------------------Canal已经关闭!!!--------------------");
            return;
        }
        log.info("初始化canal监听");
        CanalConnector connection = getConnection();
        // 打开连接
        connection.connect();
        // 订阅数据库表,全部表(.*\\..*)
        connection.subscribe(subscribe);
        // 回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
        connection.rollback();
        log.info("启动CanalThread");
        try {
            while (true) {
                // 获取指定数量的数据
                int BATCH_SIZE = 1000;
                Message message = connection.getWithoutAck(BATCH_SIZE);
                // 获取批量ID
                long batchId = message.getId();
                // 获取批量的数量
                int size = message.getEntries().size();
                // 如果没有数据
                if (batchId == -1 || size == 0) {
                    // 线程休眠2秒
                    Thread.sleep(2000);
                } else {
                    log.info("有数据发生变化");
                    // 如果有数据,处理数据
                   this.consumerMessage(message.getEntries());
                }
                // 进行 batch id 的确认。确认之后，小于等于此 batchId 的 Message 都会被确认。
                connection.ack(batchId);
            }
        } catch (Exception e) {
//            log.error("canal数据库监听启动失败！", e);
            // TODO 重启
        } finally {
            connection.disconnect();
        }
    }

    /**
     * 连接Canal服务器
     *
     * @return
     */
    private CanalConnector getConnection() {
        return CanalConnectors.newSingleConnector(new InetSocketAddress(canalHost, canalPort), "example", username, password);
    }

    public void consumerMessage(List<Entry> entryList) {
        List<Entry> filteredEntries = entryList.stream().filter(entry -> entry.getEntryType() != CanalEntry.EntryType.TRANSACTIONBEGIN && entry.getEntryType() != CanalEntry.EntryType.TRANSACTIONEND).collect(Collectors.toList());
        canalConsumers.forEach(canalConsumer -> canalConsumer.handleData(filteredEntries));
    }
}
