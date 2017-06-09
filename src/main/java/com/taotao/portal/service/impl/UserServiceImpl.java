package com.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL ;
	
	@Value("${SSO_TOKEN_URL}")
	private String SSO_TOKEN_URL ;
	
	@Override
	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 从cookie中取token
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			System.out.println("userServiceImpl: token=" + token );
			if (StringUtils.isBlank(token)) {
				return null ;
			}
			// 调用服务,看'session'是否过期
			String result = HttpClientUtil.doGet(SSO_BASE_URL + SSO_TOKEN_URL + token);
			System.out.println("userServiceImpl: result=" + result );
			if (StringUtils.isBlank(result)) {
				return null ;
			}
			// 判断是否成功
			if(TaotaoResult.format(result).getStatus() != 200 ){
				return null;
			}
			// 取出pojo
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, TbUser.class);
			
			TbUser user = (TbUser) taotaoResult.getData();
			
			return user ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
			return null ;
		}
	}

}
