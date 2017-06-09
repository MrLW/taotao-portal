package com.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL; 
	
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL; 
	
	// 将orderInfo转成json调用order服务创建订单
	@Override
	public String submitForm(OrderInfo orderInfo) {
		String order = JsonUtils.object2Json(orderInfo) ;
		//调用服务
		String result = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, order);
		TaotaoResult taotaoResult = TaotaoResult.format(result);
		String orderId = taotaoResult.getData().toString();
		return orderId;
	}

	
}
