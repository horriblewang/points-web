package com.points.osp.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bailian.dto.GoodsRecordDto;
import com.bailian.entity.Goods;
import com.bailian.entity.GoodsRecord;
import com.bailian.entity.MemberInfo;
import com.bailian.utils.Page;
import com.bailian.utils.ResultUtil;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.service.MemberService;
import com.points.osp.common.utils.ViewPage;

/**
 * 会员管理
 * 
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("/points")
public class MemberController {

	private static Logger log = LoggerFactory.getLogger(MemberController.class);

	private static final String WEB_ROOT = "modules/member/";

	@Autowired
	private MemberService memberService;

	@Autowired
	private GoodsService goodsService;

	@RequestMapping("member")
	public String memberIndex(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, HttpServletResponse response,
			Model model, MemberInfo memberInfo) {
		ViewPage<MemberInfo> viewPage = new ViewPage<MemberInfo>(request,
				response);
		int count = memberService.countMemberInfo(paraMap);
		Page<MemberInfo> page = new Page<MemberInfo>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		paraMap.put("start", page.getStart() - 1);
		paraMap.put("end", page.getPageSize());
		List<MemberInfo> list = memberService.getMemberInfosByPage(paraMap);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		return WEB_ROOT + "memberList";

	}

	/**
	 * 增加积分
	 * 
	 * @param paraMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("addPoints")
	public String addPoints(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, RedirectAttributes redirectAttributes,
			Model model) {

		memberService.addMemPoints(paraMap);
		redirectAttributes.addFlashAttribute("message", "操作成功！");
		return "redirect:/points/member";

	}
	
	/**
	 * 积分转移
	 * @param paraMap
	 * @param request
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequestMapping("transPoints")
	public String transPoints(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, RedirectAttributes redirectAttributes,
			Model model) {

		String msg = memberService.transMemPoints(paraMap);
		if(StringUtils.isNotBlank(msg)){
			redirectAttributes.addFlashAttribute("message", "操作失败！" + msg);
		}else{
			redirectAttributes.addFlashAttribute("message", "操作成功！");
		}
		
		return "redirect:/points/member";

	}
	

	/**
	 * 积分兑换
	 * 
	 * @param paraMap
	 * @param request
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequestMapping("redeem")
	public String redeemPoints(HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) {

		String memberId = request.getParameter("memberId");
		MemberInfo mem = memberService.getMember(Long.valueOf(memberId));
		model.addAttribute("memberInfo", mem);
		List<Goods> goods = goodsService.getAllGoods();
		model.addAttribute("goodsList", goods);

		return "modules/member/memberExchange";

	}

	/**
	 * 积分兑换
	 * 
	 * @param paraMap
	 * @param request
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequestMapping("exGoods")
	public String exGoods(HttpServletRequest request,
			RedirectAttributes redirectAttributes, Model model) {

		String memberId = request.getParameter("memberId");
		String goodsList = request.getParameter("goodsList");
		List<GoodsRecord> goods = new ArrayList<GoodsRecord>();
		if (StringUtils.isNotBlank(goodsList)) {
			JSONArray jsonObject = JSONArray.fromObject(goodsList);
			goods = JSONArray.toList(jsonObject, new GoodsRecord(),
					new JsonConfig());
		}
		try {
			memberService.exchangeGoods(goods, memberId);
			redirectAttributes.addFlashAttribute("message", "操作成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			redirectAttributes.addFlashAttribute("message",
					"操作失败！" + e.getMessage());
			log.error("积分兑换失败", e);
		}
		return "redirect:/points/member";

	}

	/**
	 * 兑换商品列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("exchangeList")
	public String exchangeList(@RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			Model model,MemberInfo memberInfo) {
		ViewPage<GoodsRecordDto> viewPage = new ViewPage<GoodsRecordDto>(
				request, response);
		int count = memberService.countExchange(map);
		Page<GoodsRecordDto> page = new Page<GoodsRecordDto>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		map.put("start", page.getStart() - 1);
		map.put("end", page.getPageSize());
		List<GoodsRecordDto> list = memberService.exchangeList(map);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("memberInfo", memberInfo);
		return WEB_ROOT + "memExchangeList";

	}

}
