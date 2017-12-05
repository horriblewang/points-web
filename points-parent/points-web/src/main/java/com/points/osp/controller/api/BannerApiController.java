package com.points.osp.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bailian.entity.Banner;
import com.bailian.entity.OrderInfo;
import com.bailian.utils.ResultUtil;
import com.google.common.collect.Maps;
import com.points.osp.common.service.BannerService;
import com.points.osp.common.service.OrderService;
import com.points.osp.controller.web.BaseController;

/**
 * APP推广图接口
 * 
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("api/banner")
public class BannerApiController extends BaseController {

	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private OrderService orderService;

	/**
	 * 使用jsop远程调用
	 * 系统推广
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("banList")
	public String getBannerPic(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> banMap = Maps.newHashMap();
		List<Banner> bans = bannerService.getBannerForApp();
		banMap.put("banners", bans);
		banMap.put("count", bans.size());
		return ajaxJsonP(request, response, ResultUtil.creObjSucResult(banMap));
	}
	
	
	
	/**
	 * 推广兑换礼品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("trade")
	public String tradeBanner(HttpServletRequest request,
			HttpServletResponse response) {
		String memberId = request.getParameter("memberId");
		String banId = request.getParameter("banId");
		if (StringUtils.isBlank(memberId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("请登录后兑换礼品！"));

		}
		if (StringUtils.isBlank(banId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("请选择兑换的商品！"));
		}
		//兑换banner推广商品
		Map<String, Object> resMap = bannerService.tradeBanner(memberId,banId);
		if ("0".equals((String) resMap.get("code"))) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult((String) resMap.get("msg")));
		}
		
		//积分兑换增加相应推荐积分
		OrderInfo orderInfo = (OrderInfo)resMap.get("orderInfo");
		orderService.addReferPoints(orderInfo);
		
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

}
