package com.skhu.mapper;

import java.util.List;

import com.skhu.model.Gcm;

public interface GCMMapper {
	public int insertGcm(Gcm gcm);
	public int updateGcm(Gcm gcm);
	public int deleteGcm(int no);
	public Gcm getGcm(String mac);
}
