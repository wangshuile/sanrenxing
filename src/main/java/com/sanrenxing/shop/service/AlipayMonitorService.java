package com.sanrenxing.shop.service;


import com.alipay.api.response.MonitorHeartbeatSynResponse;
import com.sanrenxing.shop.model.builder.AlipayHeartbeatSynRequestBuilder;

/**
 * Created on 18/3/2.
 * @author tony
 */
public interface AlipayMonitorService {

    // 交易保障接口 https://openhome.alipay.com/platform/document.htm#mobileApp-barcodePay-API-heartBeat

    // 可以提供给系统商/pos厂商使用
    MonitorHeartbeatSynResponse heartbeatSyn(AlipayHeartbeatSynRequestBuilder builder);
}
