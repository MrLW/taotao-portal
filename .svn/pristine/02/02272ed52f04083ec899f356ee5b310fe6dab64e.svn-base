package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private ItemService itemService;
	
	@Value("${CART_ITEM_MAXAGE}")
	private Integer CART_ITEM_MAXAGE ;
	
	@Value("${CART_ITEM_COOKIE_NAME}")
	private String CART_ITEM_COOKIE_NAME;
	
	@Override
	public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中获取列表
		List<CartItem> itemList = getCartItemList(request);
		boolean flag = false; // 用来记录cookie中是否有对应itemId记录
		for (CartItem item : itemList) {
			if (item.getId().longValue() == itemId.longValue()) {
				item.setNum(item.getNum()+num); // 添加到购物车
				flag = true ;
				break ;
			}
		}
		// cookie中没有对应的记录
		if (!flag) {
			// 根据对应的商品id查找商品
			// 可以调用rest服务，这里也可以通过itemService
			TbItem item = itemService.getItemById(itemId);
			// 创建购物车item
			CartItem cartItem = new CartItem() ;
			cartItem.setId(itemId);
			cartItem.setNum(num);
			cartItem.setTitle(item.getTitle());
			cartItem.setPrice(item.getPrice());
			if (StringUtils.isNotBlank(item.getImage())) {
				String image = item.getImage();
				String[] strings = image.split(",");
				cartItem.setImage(strings[0]);
			}	
			// 添加到列表
			itemList.add(cartItem); 
		}
		// 写入cookie
		CookieUtils.setCookie(request, response, CART_ITEM_COOKIE_NAME, JsonUtils.object2Json(itemList), CART_ITEM_MAXAGE, true);
		
		return TaotaoResult.ok();
	}
	
	// 根据request获取集合
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		String cookie = CookieUtils.getCookieValue(request, CART_ITEM_COOKIE_NAME, true);
		if (StringUtils.isNotBlank(cookie)) {
			// 将cookie转成List
			List<CartItem> itemList = JsonUtils.jsonToList(cookie, CartItem.class);
			if (itemList == null) {
				return new ArrayList<>() ;
			}
			return itemList;
		}
		return new ArrayList<>() ;
	}

	// 获取购物车列表
	@Override
	public List<CartItem> getCartItems(HttpServletRequest request) {
		List<CartItem> list = getCartItemList(request);
		return list;
	}

	// 更新购物车商品的数量
	@Override
	public TaotaoResult updateCartNum(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		String cookie = CookieUtils.getCookieValue(request, CART_ITEM_COOKIE_NAME, true);
		// 将cookie转成list
		if(StringUtils.isNotBlank(cookie)){
			List<CartItem> itemList = JsonUtils.jsonToList(cookie, CartItem.class);
			if (itemList != null) {
				for (CartItem cartItem : itemList) {
					if (itemId.longValue() == cartItem.getId()) {
						cartItem.setNum( num );
						break ;
					}
				}
			}
			// 重新将list写入cookie
			CookieUtils.setCookie(request, response, CART_ITEM_COOKIE_NAME, JsonUtils.object2Json(itemList));
			return TaotaoResult.ok() ;
		}
		return null;
	}
	// 移除在cookie对应id商品
	@Override
	public TaotaoResult deleteCartByItemId(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 查找对应记录
		List<CartItem> itemList = getCartItemList(request);
		// 创建删除后的记录
		List<CartItem> afterDeleteList = new ArrayList<>() ;
		// 遍历
		for (CartItem cartItem : itemList) {
			if (cartItem.getId().longValue() == itemId ) {
				break ;
			}
			afterDeleteList.add(cartItem) ;
		}
		// 将删除后的集合的记录写入cookie
		CookieUtils.setCookie(request, response, CART_ITEM_COOKIE_NAME, JsonUtils.object2Json(afterDeleteList),true);
		
		return TaotaoResult.ok();
	}

}
