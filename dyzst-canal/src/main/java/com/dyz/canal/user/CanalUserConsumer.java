package com.dyz.canal.user;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.dyz.canal.handle.CanalConsumer;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：Dyz
 * @Date: 2021/7/15 14:39
 */
@Slf4j
@Component
public class CanalUserConsumer extends CanalConsumer {

    @Override
    public void handleData(List<Entry> entryList) {
        this.businessProcess(entryList);
    }

    public void businessProcess(List<Entry> entryList){
        // RowChange对象，包含了一行数据变化的所有特征
        CanalEntry.RowChange rowChange;
        for (CanalEntry.Entry entry : entryList) {
            String tableName = entry.getHeader().getTableName();
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                log.info("监听到：{}\t的数据发生变化，进行了\t{}\t操作", tableName, rowChange.getEventType());
                List<CanalEntry.RowData> datasList = rowChange.getRowDatasList();
                for (CanalEntry.RowData rowData : datasList) {
                    for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                        log.info("{}：{}", column.getName(), column.getValue());
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
    }
}
