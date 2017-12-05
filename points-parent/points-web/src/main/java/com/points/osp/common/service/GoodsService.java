package com.points.osp.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bailian.entity.Goods;

public interface GoodsService {
	
	
	public Integer countGoods(Map<String, Object> paraMap);
	
	public List<Goods> getGoodsByPage(Map<String, Object> paraMap);

	public List<Goods> getAllGoods();
	
	
	/**
	 * ��ȡ������Ʒ
	 * @param goodsId
	 * @return
	 */
	public Goods getGoodsById(Long goodsId);
	
	
    /**
     * ������Ʒ
     * @param goods
     * @return
     */
	public void addGoods(Goods goods);

	 /**
     * �޸���Ʒ
     * @param goods
     * @return
     */
	public void updateGoods(Goods goods);
	
	/**
	 * �ϴ���ƷͼƬ
	 * @return
	 */
	public Map<String, String> uploadGoodsPic(MultipartFile file);

}
