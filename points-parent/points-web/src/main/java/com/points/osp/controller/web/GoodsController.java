package com.points.osp.controller.web;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bailian.dto.GoodsRecordDto;
import com.bailian.entity.Goods;
import com.bailian.entity.MemberInfo;
import com.bailian.utils.Page;
import com.bailian.utils.ResultUtil;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.utils.ViewPage;

@Controller
@RequestMapping("/points/goods")
public class GoodsController {

	private static Logger log = LoggerFactory.getLogger(GoodsController.class);

	private static final String WEB_ROOT = "modules/goods/";

	@Autowired
	private GoodsService goodsService;

	/**
	 * 商品列表
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @param model
	 * @param goods
	 * @return
	 */
	@RequestMapping({ "list", "" })
	public String goodsList(@RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			Model model, Goods goods) {
		ViewPage<Goods> viewPage = new ViewPage<Goods>(request, response);
		int count = goodsService.countGoods(map);
		Page<Goods> page = new Page<Goods>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		map.put("start", page.getStart() - 1);
		map.put("end", page.getPageSize());
		List<Goods> list = goodsService.getGoodsByPage(map);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("goods", goods);
		return WEB_ROOT + "goodsList";

	}

	/**
	 * 新增修改商品
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param goods
	 * @return
	 */
	@RequestMapping("form")
	public String goodsForm(HttpServletRequest request,
			HttpServletResponse response, Model model, Goods goods) {
		Long goodsId = goods.getGoodsId();
		if (goodsId != null) {
			goods = goodsService.getGoodsById(goodsId);
		}
		model.addAttribute("goods", goods);
		return WEB_ROOT + "goodsForm";

	}

	/**
	 * 保存商品
	 * 
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param goods
	 * @return
	 */
	@RequestMapping("save")
	public String goodsSave(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Goods goods) {
		Long goodsId = goods.getGoodsId();
		if (goodsId != null) {// 修改
			goodsService.updateGoods(goods);
		} else {// 新增
			goodsService.addGoods(goods);
		}
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/points/goods";

	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 */
	@RequestMapping("del")
	@ResponseBody
	public Map<String, Object> goodsDel(HttpServletRequest request,
			HttpServletResponse response, Goods goods) {
		goods.setIsDelete("1");
		goodsService.updateGoods(goods);
		return ResultUtil.creComSucResult();

	}
	
	/**
	 * 商品图片上传
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 */
	@RequestMapping("uploadFile")
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response, Goods goods) {
		
		return WEB_ROOT + "goodsPicForm";

	}
	
	
	/**
	 * 经纪人头像上传
	 * 
	 * @param inv
	 * @param file
	 * @return
	 */
	@RequestMapping("addGoodsPic")
	public String addGoodsPic(HttpServletRequest request, @RequestParam(name = "fileName") MultipartFile file,Model model) {
		//校验图片大小，在后台做,前端JS IE不支持
		try {
			Map<String, String> resMap = goodsService.uploadGoodsPic(file);
			if ("1".equals(resMap.get("restCode"))) {
				model.addAttribute("restCode", "1");
				model.addAttribute("url", resMap.get("url"));
			} else {
				model.addAttribute("restCode", "0");
				model.addAttribute("restMsg", resMap.get("restMsg"));
			}
		} catch (Exception e) {
			log.error("图片上传失败", e);
		}
		return WEB_ROOT + "goodsPicForm";
	}

}
