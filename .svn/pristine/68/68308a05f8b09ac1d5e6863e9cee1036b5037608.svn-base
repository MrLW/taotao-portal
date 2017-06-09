package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;
	
	// 订单确定
	@RequestMapping("/order-cart")
	public String showOrderCart(Model mode, HttpServletRequest request){
		List<CartItem> cartItems = cartService.getCartItems(request);
		mode.addAttribute("cartList", cartItems) ;
		return "order-cart" ;
	}
	
	// 提交订单
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String submitForm(OrderInfo orderInfo,HttpServletRequest request,Model model){
		System.out.println("提交订单啦~~~");
		// 1、从reque域中取出登陆用户
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		String orderId = orderService.submitForm(orderInfo);
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		//返回逻辑视图
		return "success";
	}
}
