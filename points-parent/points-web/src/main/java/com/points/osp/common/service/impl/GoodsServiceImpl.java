package com.points.osp.common.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bailian.entity.Goods;
import com.bailian.entity.GoodsExample;
import com.bailian.persistence.GoodsMapper;
import com.points.osp.common.config.Global;
import com.points.osp.common.service.GoodsService;
import com.points.osp.common.service.PointsService;
import com.points.osp.common.utils.SFTPUtils;

@Service
public class GoodsServiceImpl implements GoodsService {
	
	private static Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);
	
	private static final String HOST_NAME = "points_SFTP_hostName";
	
	private static final String USER_NAME = "points_SFTP_username";
	
	private static final String PASS_KEY = "points_SFTP_password";
	
	private static final String UPLOAD_PATH  = "points_SFTP_upload_dir";

	private static final String PIC_URL_PRE = "HOME.POINTS.IMAGES";
	
	@Autowired
	private GoodsMapper goodsMapper;

	public Integer countGoods(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		GoodsExample example = new GoodsExample();
		example.createCriteria().andIsDeleteNotEqualTo("1");
		return goodsMapper.countByExample(example);
	}

	public List<Goods> getGoodsByPage(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return goodsMapper.selGoodsPage(paraMap);
	}

	public List<Goods> getAllGoods() {
		// TODO Auto-generated method stub
		GoodsExample example = new GoodsExample();
		example.createCriteria().andIsDeleteNotEqualTo("1");
		return goodsMapper.selectByExample(example);
	}

	public Goods getGoodsById(Long goodsId) {
		// TODO Auto-generated method stub
		return goodsMapper.selectByPrimaryKey(goodsId);
	}

	public void addGoods(Goods goods) {
		// TODO Auto-generated method stub
		goods.setIsDelete("0");
		goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
		goodsMapper.insert(goods);
	}

	public void updateGoods(Goods goods) {
		// TODO Auto-generated method stub
		goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		goodsMapper.updateByPrimaryKeySelective(goods);
	}

	public Map<String, String> uploadGoodsPic(MultipartFile file) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("restCode", "0");
		String name = file.getOriginalFilename();
		SFTPUtils sFTPUtils = new SFTPUtils(Global.getConfig(HOST_NAME), 22,
				Global.getConfig(USER_NAME), Global.getConfig(PASS_KEY));
		try {
			sFTPUtils.connect();
			sFTPUtils.upload(Global.getConfig(UPLOAD_PATH), file.getInputStream(),name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			map.put("restMsg", e.getMessage());
			log.error("上传失败", e);
		}
		String serverUrl = Global.getConfig(PIC_URL_PRE);
		map.put("restCode", "1");
		map.put("url", serverUrl + name);
		try {
			sFTPUtils.disconnect();
		} catch (Exception e) {
			map.put("restMsg", e.getMessage());
			log.error("关闭链接失败", e);
		}
		return map;
	}

}
