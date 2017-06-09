package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

// 购物车
@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,Integer num,
				HttpServletRequest request,HttpServletResponse response){
		TaotaoResult result = cartService.addCart(itemId, num, request, response);
		
		return "cart-success" ;
	}
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, Model model) {
		
		List<CartItem> list = cartService.getCartItems(request);
		//把商品列表传递给jsp
		model.addAttribute("cartList", list);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
			HttpServletRequest request,HttpServletResponse response ){
		TaotaoResult result = cartService.updateCartNum(itemId, num, request, response) ;
		return result ;
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartByItemId(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
		TaotaoResult result = cartService.deleteCartByItemId(itemId, request, response) ;
		return "redirect:/cart/cart.html" ;
	}
	
	
}