package com.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.skhu.model.Alarm;

public interface AlarmMapper {
	public int insertAlarm(Alarm alarm);
	public int updateAlarm(Alarm alarm);
	public int deleteAlarm(long no);
	public Alarm getAlarm(@Param("mac") String mac, @Param("srcNo") String srcNo, @Param("cateNo") int cateNo, @Param("filter") String filter);
	public List<Alarm> getAlarms(String mac);
	public List<Alarm> findFilters(@Param("filter") String filter, @Param("cateNo") int cateNo);
	public List<Alarm> findSrcFilters(@Param("srcNo") String srcNo, @Param("cateNo") int cateNo);
	public List<Alarm> getAllAlarms();
}
