package com.points.osp.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bailian.utils.ResultUtil;
import com.points.osp.common.utils.CodeUtils;
import com.points.osp.common.utils.Constant;
import com.points.osp.common.utils.RedisUtil;
import com.points.osp.controller.web.BaseController;

/**
 * 图形验证码生产
 * @author xyzq
 *
 */
@Controller
@RequestMapping("auth/image")
public class AuthImageController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(AuthImageController.class);
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("")
	public void genImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tString = request.getParameter("t");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = CodeUtils.generateVerifyCode(4);
		
		log.info("image code key======>" + tString);
		
		redisUtil.set(Constant.IMAGE_CODE + tString, verifyCode, 3600);
		// 生成图片
		int w = 145, h = 50;
		CodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
	
	
	/**
	 * 校验图形验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("check")
	public String checkImageCode(HttpServletRequest request, HttpServletResponse response) {

		String t = request.getParameter("t");
		String image = request.getParameter("image");
		String code = redisUtil.get(Constant.IMAGE_CODE + t, "");
		if(code.equals(image)){
			return ajaxJsonP(request, response, ResultUtil.creComSucResult());	
		}
		return ajaxJsonP(request, response, ResultUtil.creComErrorResult());
	}

}
