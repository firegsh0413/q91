package com.icchance.q91.dao;

import com.icchance.q91.entity.model.Gateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class FakeGatewayDB {

    private static Map<Integer, Gateway> gatewayDb = new TreeMap<>();

    public List<Gateway> getList() {
        synchronized (gatewayDb) {

            gatewayDb.put(1, Gateway.builder().id(1).type(1).name("王大明").gatewayName("中国银行").gatewayReceiptCode("1234567000066007890").build());
            gatewayDb.put(2, Gateway.builder().id(2).type(2).name("王大明").gatewayName("微信")
                    .gatewayReceiptCode("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                    .build());
        }
        return new ArrayList<>(gatewayDb.values());
    }
}
