package com.bailian.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bailian.dto.DayMemPointsDto;

public interface DayAddPointsMapper {
	
	/**
	 * ÿ���������Ա����
	 * @return
	 */
	public List<DayMemPointsDto> getDayAddMemberInfo();
	
	
	/**
	 * ���ӻ���
	 * @param list
	 */
	public void updateMemPoints(@Param("members")List<DayMemPointsDto> list);

}
