package com.dyz.canal.handle;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
public abstract class HandleData {

    public void handleData(Message message) throws InvalidProtocolBufferException {
        CanalEntry.RowChange rowChange;
        List<CanalEntry.Entry> entries = message.getEntries();
        for (CanalEntry.Entry entry : entries) {
            String tableName = entry.getHeader().getTableName();
            log.info("监听到：{}\t的数据发生变化", tableName);
            rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            List<CanalEntry.RowData> datasList = rowChange.getRowDatasList();
            for (CanalEntry.RowData rowData : datasList) {
                for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                    log.info("{}：{}", column.getName(), column.getValue());
                }
            }
        }
    }
}
