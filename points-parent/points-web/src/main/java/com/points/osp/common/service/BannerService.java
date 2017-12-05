package com.points.osp.common.service;

import com.bailian.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
	
	/**
	 * 查询数量
	 * @param map
	 * @return
	 */
	public int countBanner(Map<String,Object> map);
	
	/**
	 * 翻页
	 * @param map
	 * @return
	 */
	public List<Banner> selBannerByPage(Map<String,Object> map);

	/**
	 * 主键查询
	 * @param banId
	 * @return
	 */
	public Banner getBannerById(Long banId);

	/**
	 * 新增
	 * @param banner
	 */
	public void addBanner(Banner banner);

	/**
	 * 修改
	 * @param banner
	 */
	public void updateBanner(Banner banner);

	/**
	 * 推广启用
	 * @param banId
	 */
	public void enableBan(Long banId);

	/**
	 * 推广禁用
	 * @param banId
	 */
	public void disableBan(Long banId);

	
	/**
	 * 返回给端推广配置
	 * @return
	 */
	public List<Banner> getBannerForApp();
	
	

	
	public Map<String, Object> tradeBanner(String memberId, String banId);

}
