package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.Selected;

/**
 * 精选商品配置
 * @author wbwangsh
 *
 */
public interface SelectedService {
	
	/**
	 * 查询数量
	 * @param map
	 * @return
	 */
	public int countSelected(Map<String,Object> map);
	
	/**
	 * 翻页
	 * @param map
	 * @return
	 */
	public List<Selected> selSelectedByPage(Map<String,Object> map);

	/**
	 * 主键查询
	 * @param banId
	 * @return
	 */
	public Selected getSelectedById(Long banId);

	/**
	 * 新增
	 * @param banner
	 */
	public void addSelected(Selected selected);

	/**
	 * 修改
	 * @param banner
	 */
	public void updateSelected(Selected selected);

	/**
	 * 推广启用
	 * @param banId
	 */
	public void enableSelected(Long banId);

	/**
	 * 推广禁用
	 * @param banId
	 */
	public void disableSelected(Long banId);

	
	/**
	 * api接口
	 * @param param
	 * @return
	 */
	public List<Selected> getSelectedForApp(Map<String, String> param);

	
	/**
	 * 精选商品兑换
	 * @param memberId
	 * @param selId
	 * @param num
	 * @return
	 */
	public Map<String, Object> tradeSelected(String memberId, String selId, Long num);


}
