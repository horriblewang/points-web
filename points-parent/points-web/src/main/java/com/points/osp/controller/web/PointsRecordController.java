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
import com.bailian.entity.PointsRecord;
import com.bailian.utils.Page;
import com.bailian.utils.ResultUtil;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.service.MemberService;
import com.points.osp.common.service.PointsRecordService;
import com.points.osp.common.utils.ViewPage;

/**
 * 会员管理
 * 
 * @author wbwangsh
 *
 */
@Controller
@RequestMapping("/points")
public class PointsRecordController {

	private static Logger log = LoggerFactory.getLogger(PointsRecordController.class);

	private static final String WEB_ROOT = "modules/points/";
	
	@Autowired
	private PointsRecordService pointsRecordService;
	 


	/**
	 * 积分转让记录查询
	 * @param paraMap
	 * @param request
	 * @param response
	 * @param model
	 * @param record
	 * @return
	 */
	@RequestMapping("record")
	public String memberIndex(@RequestParam Map<String, Object> paraMap,
			HttpServletRequest request, HttpServletResponse response,
			Model model, PointsRecord record) {
		ViewPage<PointsRecord> viewPage = new ViewPage<PointsRecord>(request,
				response);
		int count = pointsRecordService.countPointsRecord(paraMap);
		Page<PointsRecord> page = new Page<PointsRecord>();
		page.setCurrentPage(viewPage.getPageNo());
		page.setPageSize(viewPage.getPageSize());
		page.setCount(count);
		paraMap.put("start", page.getStart() - 1);
		paraMap.put("end", page.getPageSize());
		List<PointsRecord> list = pointsRecordService.getRcordByPage(paraMap);
		viewPage.getList().addAll(list);
		viewPage.setCount(count);
		model.addAttribute("page", viewPage);
		model.addAttribute("record", record);
		return WEB_ROOT + "recordList";

	}
	

}
