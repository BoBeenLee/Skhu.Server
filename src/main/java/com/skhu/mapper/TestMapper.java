package com.skhu.mapper;

import java.util.List;

import com.skhu.model.JavaBean;

public interface TestMapper {
	public int deleteTest(int id);
	public int insertTest(JavaBean jb);
	public List<JavaBean> getTests();
}
