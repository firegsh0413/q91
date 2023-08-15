package com.icchance.q91.dao;

import com.icchance.q91.entity.model.*;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.parsing.BeanEntry;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Repository
public class FakeTransactionDB {

    private static Map<Integer, PendingOrderVO> pendingOrderDb = new TreeMap<>();
    private static Map<Integer, OrderVO> orderDb = new TreeMap<>();

    public List<PendingOrderVO> getPendingOrderList() {
        List<PendingOrderVO> result = new ArrayList<>();
        PendingOrder pendingOrder = PendingOrder.builder()
                .id(12)
                .status(2)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .tradeTime(LocalDateTime.now())
                .buyerId(5)
                .amount(new BigDecimal("99.99"))
                .orderNumber("go20230524131440511")
                .build();
        PendingOrderVO pendingOrderVO = new PendingOrderVO();
        BeanUtils.copyProperties(pendingOrder, pendingOrderVO);
        pendingOrderVO.setBuyerUsername("johndoe");
        result.add(pendingOrderVO);
        pendingOrder = PendingOrder.builder()
                .id(15)
                .status(5)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .tradeTime(LocalDateTime.now())
                .buyerId(0)
                .amount(new BigDecimal("10.0"))
                .orderNumber("go20230610180998765")
                .build();
        BeanUtils.copyProperties(pendingOrder, pendingOrderVO);
        pendingOrderVO.setBuyerUsername("");
        result.add(pendingOrderVO);
        return result;

    }

    public List<OrderVO> getOrderList() {
        List<OrderVO> result = new ArrayList<>();
        Order order = Order.builder()
                .id(12)
                .status(2)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .tradeTime(LocalDateTime.now())
                .sellerId(5)
                .amount(new BigDecimal("99.99"))
                .orderNumber("go20230524131440511")
                .build();
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        orderVO.setSellerUsername("johndoe");
        result.add(orderVO);
        order = Order.builder()
                .id(15)
                .status(3)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .tradeTime(LocalDateTime.now())
                .sellerId(5)
                .amount(new BigDecimal("10.0"))
                .orderNumber("go20230610180998765")
                .build();
        BeanUtils.copyProperties(order, orderVO);
        orderVO.setSellerUsername("johndoe");
        result.add(orderVO);
        return result;
    }

    public List<OrderRecord> getOrderRecordList() {
        List<OrderRecord> result = new ArrayList<>();
        OrderRecord orderRecord = OrderRecord.builder()
                .id(1)
                .status(1)
                .amount(new BigDecimal("99.99"))
                .orderNumber("go20230524131440511")
                .createTime(LocalDateTime.now())
                .build();
        result.add(orderRecord);
        orderRecord = OrderRecord.builder()
                .id(2)
                .status(2)
                .amount(new BigDecimal("-99.99"))
                .orderNumber("go20230524131440511")
                .createTime(LocalDateTime.now())
                .build();
        result.add(orderRecord);
        return result;

    }

    public List<Gateway> getGatewayList() {
        List<Gateway> result = new ArrayList<>();
        Gateway gateway = Gateway.builder()
                .id(1)
                .type(1)
                .name("王大明")
                .gatewayName("中国银行")
                .gatewayReceiptCode("1234567000066007890")
                .gatewayAccount("")
                .build();
        result.add(gateway);
        gateway = Gateway.builder()
                .id(2)
                .type(2)
                .name("王大明")
                .gatewayName("微信")
                .gatewayReceiptCode("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .gatewayAccount("")
                .build();
        result.add(gateway);
        return result;
    }

    public List<MarketVO> getMarketPendingOrderList() {
        List<MarketVO> result = new ArrayList<>();
        Market market = Market.builder()
                .id(12)
                .sellerId(5)
                .sellerUsername("johndoe")
                .sellerAvatar("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .amount(new BigDecimal("99.99"))
                .orderNumber("go20230524131440511")
                .build();
        MarketVO marketVO = new MarketVO();
        BeanUtils.copyProperties(market, marketVO);
        marketVO.setAvailableGateway(new HashSet<>(Arrays.asList(1, 2)));
        result.add(marketVO);
        market = Market.builder()
                .id(15)
                .sellerId(7)
                .sellerUsername("janedoe")
                .sellerAvatar("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .amount(new BigDecimal("999.99"))
                .orderNumber("go20230624131441098")
                .build();
        marketVO = new MarketVO();
        BeanUtils.copyProperties(market, marketVO);
        marketVO.setAvailableGateway(new HashSet<>(Collections.singletonList(3)));
        result.add(marketVO);
        return result;
    }

    public MarketVO getPendingOrder() {
        Market market = Market.builder()
                .id(12)
                .sellerId(5)
                .sellerUsername("johndoe")
                .sellerAvatar("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .amount(new BigDecimal("99.99"))
                .orderNumber("go20230524131440511")
                .build();
        MarketVO marketVO = new MarketVO();
        BeanUtils.copyProperties(market, marketVO);
        marketVO.setAvailableGateway(new HashSet<>(Arrays.asList(1, 2)));
        return marketVO;
    }

    public OrderVO getOrderDetail(){
        Order order = Order.builder()
                .id(12)
                .status(2)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .tradeTime(LocalDateTime.now())
                .cutOffTime(LocalDateTime.now())
                .sellerId(5)
                .amount(new BigDecimal("99.99"))
                .orderNumber("go20230524131440511")
                .cert("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .build();
        Gateway sellerInfo = Gateway.builder()
                .id(3)
                .type(1)
                .name("刘小华")
                .gatewayName("中国银行")
                .gatewayReceiptCode("1234567000066001234")
                .build();
        Gateway buyerInfo = Gateway.builder()
                .id(1)
                .type(1)
                .name("王大明")
                .gatewayName("中国银行")
                .gatewayReceiptCode("1234567000066007890")
                .build();
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        orderVO.setSellerInfo(sellerInfo);
        orderVO.setBuyerInfo(buyerInfo);
        orderVO.setSellerUsername("johndoe");
        orderVO.setAvailableGateway(new HashSet<>(Arrays.asList(1, 2)));
        return orderVO;
    }

    public PendingOrderVO getPendingOrderDetail() {
        PendingOrder pendingOrder = PendingOrder.builder()
                .id(12)
                .status(2)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .tradeTime(LocalDateTime.now())
                .buyerId(5)
                .amount(new BigDecimal("99.99"))
                .orderNumber("go20230524131440511")
                .cert("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .build();
        PendingOrderVO pendingOrderVO = new PendingOrderVO();
        BeanUtils.copyProperties(pendingOrder, pendingOrderVO);
        Gateway buyerInfo = Gateway.builder()
                .id(3)
                .type(1)
                .name("刘小华")
                .gatewayName("中国银行")
                .gatewayReceiptCode("1234567000066001234")
                .build();
        Gateway sellerInfo = Gateway.builder()
                .id(1)
                .type(1)
                .name("王大明")
                .gatewayName("中国银行")
                .gatewayReceiptCode("1234567000066007890")
                .build();
        pendingOrderVO.setBuyerUsername("johndoe");
        pendingOrderVO.setBuyerInfo(buyerInfo);
        pendingOrderVO.setSellerInfo(sellerInfo);
        return pendingOrderVO;
    }

}
