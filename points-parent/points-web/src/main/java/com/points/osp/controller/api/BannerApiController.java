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
 * APP�ƹ�ͼ�ӿ�
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
	 * ʹ��jsopԶ�̵���
	 * ϵͳ�ƹ�
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
	 * �ƹ�һ���Ʒ
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
					ResultUtil.creComErrorResult("���¼��һ���Ʒ��"));

		}
		if (StringUtils.isBlank(banId)) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult("��ѡ��һ�����Ʒ��"));
		}
		//�һ�banner�ƹ���Ʒ
		Map<String, Object> resMap = bannerService.tradeBanner(memberId,banId);
		if ("0".equals((String) resMap.get("code"))) {
			return ajaxJsonP(request, response,
					ResultUtil.creComErrorResult((String) resMap.get("msg")));
		}
		
		//���ֶһ�������Ӧ�Ƽ�����
		OrderInfo orderInfo = (OrderInfo)resMap.get("orderInfo");
		orderService.addReferPoints(orderInfo);
		
		return ajaxJsonP(request, response, ResultUtil.creComSucResult());

	}

}
