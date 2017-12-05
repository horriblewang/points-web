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
	 * 获取单个商品
	 * @param goodsId
	 * @return
	 */
	public Goods getGoodsById(Long goodsId);
	
	
    /**
     * 新增商品
     * @param goods
     * @return
     */
	public void addGoods(Goods goods);

	 /**
     * 修改商品
     * @param goods
     * @return
     */
	public void updateGoods(Goods goods);
	
	/**
	 * 上传商品图片
	 * @return
	 */
	public Map<String, String> uploadGoodsPic(MultipartFile file);

}
