package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import com.bailian.entity.Selected;

/**
 * ��ѡ��Ʒ����
 * @author wbwangsh
 *
 */
public interface SelectedService {
	
	/**
	 * ��ѯ����
	 * @param map
	 * @return
	 */
	public int countSelected(Map<String,Object> map);
	
	/**
	 * ��ҳ
	 * @param map
	 * @return
	 */
	public List<Selected> selSelectedByPage(Map<String,Object> map);

	/**
	 * ������ѯ
	 * @param banId
	 * @return
	 */
	public Selected getSelectedById(Long banId);

	/**
	 * ����
	 * @param banner
	 */
	public void addSelected(Selected selected);

	/**
	 * �޸�
	 * @param banner
	 */
	public void updateSelected(Selected selected);

	/**
	 * �ƹ�����
	 * @param banId
	 */
	public void enableSelected(Long banId);

	/**
	 * �ƹ����
	 * @param banId
	 */
	public void disableSelected(Long banId);

	
	/**
	 * api�ӿ�
	 * @param param
	 * @return
	 */
	public List<Selected> getSelectedForApp(Map<String, String> param);

	
	/**
	 * ��ѡ��Ʒ�һ�
	 * @param memberId
	 * @param selId
	 * @param num
	 * @return
	 */
	public Map<String, Object> tradeSelected(String memberId, String selId, Long num);


}
