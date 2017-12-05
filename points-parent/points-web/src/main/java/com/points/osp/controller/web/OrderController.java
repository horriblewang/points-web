package com.points.osp.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bailian.dto.ActivityDetailDto;
import com.bailian.dto.ActivityDto;
import com.bailian.dto.OrderInfoDto;
import com.bailian.entity.Activity;
import com.bailian.entity.Goods;
import com.bailian.entity.MemberInfo;
import com.bailian.entity.OrderDetail;
import com.bailian.entity.OrderInfo;
import com.bailian.entity.RuleInfo;
import com.bailian.utils.Page;
import com.google.common.collect.Maps;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.service.MemberService;
import com.points.osp.common.service.OrderService;
import com.points.osp.common.service.RuleService;
import com.points.osp.common.utils.ViewPage;

@Controller
@RequestMapping("points/order")
public class OrderController {

	private static Logger log = LoggerFactory.getLogger(OrderController.class);

	private static final String WEB_ROOT = "modules/order/";

	@Autowired
	private OrderService orderService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private MemberService memberService;
	
	
	@Autowired
	private RuleService ruleService;

	@RequestMapping({ "list", "" })
	public String list(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, HttpServletResponse response,
			Model model, OrderInfoDto orderInfo) {
		ViewPage<OrderInfoDto> viewPage = new ViewPage<OrderInfoDto>(request,
				response);
		int count = orderService.countOrder(paraMap);
		Page<OrderInfoDto> page = new Page<OrderInfoDto>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		paraMap.put("start", page.getStart() - 1);
		paraMap.put("end", page.getPageSize());
		List<OrderInfoDto> list = orderService.getOrderByPage(paraMap);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("orderInfo", orderInfo);
		return WEB_ROOT + "orderList";

	}

	/**
	 * 订单编辑
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param activity
	 * @return
	 */
	@RequestMapping("form")
	public String actForm(HttpServletRequest request,
			HttpServletResponse response, Model model, MemberInfo memberInfo) {
		Long memberId = memberInfo.getMemberId();
		memberInfo = memberService.getMember(memberId);
		model.addAttribute("memberInfo", memberInfo);
		//商品列表
		List<Goods> goodsList = goodsService.getAllGoods();
		model.addAttribute("goodsList", goodsList);
		//规则列表
		List<RuleInfo> rules = ruleService.getAllEffectInfos();
		model.addAttribute("rules", rules);
		return WEB_ROOT + "orderForm";

	}

	/**
	 * 保存订单
	 * 
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param activity
	 * @return
	 */
	@RequestMapping("save")
	public String orderSave(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, OrderInfo orderInfo) {

		String orderDetail = request.getParameter("detailList");
		orderService.addOrderInfo(orderInfo, orderDetail);//保存订单信息
		orderService.addReferPoints(orderInfo);//增加推荐奖励
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/points/order";

	}

	@RequestMapping("detail")
	public String orderDetail(HttpServletRequest request, Model model,
			OrderInfo orderInfo) {
		Long orderId = orderInfo.getOrderId();
		Map<String, Object> paraMap = Maps.newHashMap();
		paraMap.put("start", 0);
		paraMap.put("end", 10);
		paraMap.put("orderId", orderId);
		List<OrderInfoDto> list = orderService.getOrderByPage(paraMap);
		List<OrderDetail> detailDtos = orderService.getOrderDetail(orderId);
		model.addAttribute("orderInfo", list.get(0));
		model.addAttribute("orderDetails", detailDtos);
		return WEB_ROOT + "orderDetail";

	}

}
