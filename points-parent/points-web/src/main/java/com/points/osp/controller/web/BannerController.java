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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bailian.entity.Banner;
import com.bailian.entity.Goods;
import com.bailian.utils.Page;
import com.points.osp.common.service.BannerService;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.utils.ViewPage;

/**
 * ϵͳ�ƹ�����
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("points/banner")
public class BannerController {
	
	private static Logger log = LoggerFactory.getLogger(BannerController.class);

	private static final String WEB_ROOT = "modules/banner/";

	@Autowired
	private BannerService bannerService;
	
	
	@Autowired
	private GoodsService goodsService;

	/**
	 * �б��ѯ
	 * @param map
	 * @param banner
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping({ "list", "" })
	public String list(@RequestParam Map<String, Object> map, Banner banner,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ViewPage<Banner> viewPage = new ViewPage<Banner>(request, response);
		int count = bannerService.countBanner(map);
		Page<Banner> page = new Page<Banner>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		map.put("start", page.getStart() - 1);
		map.put("end", page.getPageSize());
		List<Banner> list = bannerService.selBannerByPage(map);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("banner", banner);
		return WEB_ROOT + "bannerList";

	}
	
	/**
	 * �޸�
	 * @param redirectAttributes
	 * @param banner
	 * @param model
	 * @return
	 */
	@RequestMapping("form")
	public String editBanner(RedirectAttributes redirectAttributes,Banner banner,Model model){
		if(banner.getBanId() != null){
			banner = bannerService.getBannerById(banner.getBanId());
		}
		model.addAttribute("banner", banner);
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return WEB_ROOT + "bannerForm";
	}
	
	/**
	 * ����
	 * @param redirectAttributes
	 * @param banner
	 * @return
	 */
	@RequestMapping("save")
	public String saveBanner(RedirectAttributes redirectAttributes,Banner banner){
		//�������
		if(banner.getBanId() == null){
			bannerService.addBanner(banner);
		}else{//�޸�
			bannerService.updateBanner(banner);
		}
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return "redirect:/points/banner";
	}
	
	
	/**
	 * �ƹ����
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param banner
	 * @return
	 */
	@RequestMapping("disable")
	public String disable(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Banner banner) {
		Long banId = banner.getBanId();
		bannerService.disableBan(banId);
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return "redirect:/points/banner";
	}
	
	/**
	 * �ƹ�����
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param banner
	 * @return
	 */
	@RequestMapping("enable")
	public String enable(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Banner banner) {
		Long banId = banner.getBanId();
		bannerService.enableBan(banId);
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return "redirect:/points/banner";
	}
	
	
	/**
	 * ��ƷͼƬ�ϴ�
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 */
	@RequestMapping("uploadFile")
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response, Goods goods) {
		
		return WEB_ROOT + "bannerPicForm";

	}
	
	
	/**
	 * ������ͷ���ϴ�
	 * 
	 * @param inv
	 * @param file
	 * @return
	 */
	@RequestMapping("addBanPic")
	public String addGoodsPic(HttpServletRequest request, @RequestParam(name = "fileName") MultipartFile file,Model model) {
		//У��ͼƬ��С���ں�̨��,ǰ��JS IE��֧��
		try {
			String restType = request.getParameter("restType");
			Map<String, String> resMap = goodsService.uploadGoodsPic(file);
			if ("1".equals(resMap.get("restCode"))) {
				model.addAttribute("restCode", "1");
				model.addAttribute("url", resMap.get("url"));
			} else {
				model.addAttribute("restCode", "0");
				model.addAttribute("restMsg", resMap.get("restMsg"));
			}
			model.addAttribute("restType", restType);
		} catch (Exception e) {
			log.error("ͼƬ�ϴ�ʧ��", e);
		}
		return WEB_ROOT + "bannerPicForm";
	}

}
