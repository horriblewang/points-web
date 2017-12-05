package com.bailian.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bailian.dto.DayMemPointsDto;

public interface DayAddPointsMapper {
	
	/**
	 * 每天批处理会员积分
	 * @return
	 */
	public List<DayMemPointsDto> getDayAddMemberInfo();
	
	
	/**
	 * 增加积分
	 * @param list
	 */
	public void updateMemPoints(@Param("members")List<DayMemPointsDto> list);

}
