package com.points.osp.common.service;

import com.bailian.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
	
	/**
	 * ��ѯ����
	 * @param map
	 * @return
	 */
	public int countBanner(Map<String,Object> map);
	
	/**
	 * ��ҳ
	 * @param map
	 * @return
	 */
	public List<Banner> selBannerByPage(Map<String,Object> map);

	/**
	 * ������ѯ
	 * @param banId
	 * @return
	 */
	public Banner getBannerById(Long banId);

	/**
	 * ����
	 * @param banner
	 */
	public void addBanner(Banner banner);

	/**
	 * �޸�
	 * @param banner
	 */
	public void updateBanner(Banner banner);

	/**
	 * �ƹ�����
	 * @param banId
	 */
	public void enableBan(Long banId);

	/**
	 * �ƹ����
	 * @param banId
	 */
	public void disableBan(Long banId);

	
	/**
	 * ���ظ����ƹ�����
	 * @return
	 */
	public List<Banner> getBannerForApp();
	
	

	
	public Map<String, Object> tradeBanner(String memberId, String banId);

}
