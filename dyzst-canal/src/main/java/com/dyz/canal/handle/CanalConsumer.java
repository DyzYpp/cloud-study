package com.dyz.canal.handle;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class CanalConsumer {

    public abstract void handleData(List<Entry> entryList);
}
