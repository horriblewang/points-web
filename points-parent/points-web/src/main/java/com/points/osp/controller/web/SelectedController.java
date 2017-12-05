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

import com.bailian.entity.Selected;
import com.bailian.utils.Page;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.service.SelectedService;
import com.points.osp.common.utils.ViewPage;

/**
 * ��ѡҳ���ѯ
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("points/selected")
public class SelectedController {
	
	private static Logger log = LoggerFactory.getLogger(SelectedController.class);

	private static final String WEB_ROOT = "modules/selected/";

	@Autowired
	private SelectedService selectedService;
	
	@Autowired
	private GoodsService goodsService;

	/**
	 * �б��ѯ
	 * 
	 * @param map
	 * @param banner
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping({ "list", "" })
	public String list(@RequestParam Map<String, Object> map, Selected selected,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ViewPage<Selected> viewPage = new ViewPage<Selected>(request, response);
		int count = selectedService.countSelected(map);
		Page<Selected> page = new Page<Selected>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		map.put("start", page.getStart() - 1);
		map.put("end", page.getPageSize());
		List<Selected> list = selectedService.selSelectedByPage(map);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("selected", selected);
		return WEB_ROOT + "selectedList";

	}

	/**
	 * �޸�
	 * 
	 * @param redirectAttributes
	 * @param banner
	 * @param model
	 * @return
	 */
	@RequestMapping("form")
	public String editBanner(RedirectAttributes redirectAttributes,
			Selected selected, Model model) {
		if (selected.getSelId() != null) {
			selected = selectedService.getSelectedById(selected.getSelId());
		}
		model.addAttribute("selected", selected);
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return WEB_ROOT + "selectedForm";
	}

	/**
	 * ����
	 * 
	 * @param redirectAttributes
	 * @param banner
	 * @return
	 */
	@RequestMapping("save")
	public String saveBanner(RedirectAttributes redirectAttributes,
			Selected selected) {
		// �������
		if (selected.getSelId() == null) {
			selectedService.addSelected(selected);
		} else {// �޸�
			selectedService.updateSelected(selected);
		}
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return "redirect:/points/selected";
	}

	/**
	 * ��ѡ����
	 * 
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param banner
	 * @return
	 */
	@RequestMapping("disable")
	public String disable(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Selected banner) {
		Long selId = banner.getSelId();
		selectedService.disableSelected(selId);
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return "redirect:/points/selected";
	}

	/**
	 * ��ѡ����
	 * 
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @param banner
	 * @return
	 */
	@RequestMapping("enable")
	public String enable(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes, Selected banner) {
		Long selId = banner.getSelId();
		selectedService.enableSelected(selId);
		redirectAttributes.addFlashAttribute("message", "�����ɹ�");
		return "redirect:/points/selected";
	}

	
	/**
	 * ͼƬ�ϴ�ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadFile")
	public String uploadFile(HttpServletRequest request) {
		
		return WEB_ROOT + "selectedPicForm";
	}
	
	
	/**
	 * ��ƷͼƬ�ϴ�ҳ��
	 * 
	 * @param inv
	 * @param file
	 * @return
	 */
	@RequestMapping("addSelectedPic")
	public String addGoodsPic(HttpServletRequest request, @RequestParam(name = "fileName") MultipartFile file,Model model) {
		//У��ͼƬ��С���ں�̨��,ǰ��JS IE��֧��
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
			log.error("ͼƬ�ϴ�ʧ��", e);
		}
		return WEB_ROOT + "selectedPicForm";
	}
}
