package com.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.skhu.model.Category;

public interface CateMapper {
	public int insertCategory(Category cate);
	public int updateCategory(Category cate);
	public int deleteCategory(int no);
	public Category getCategory(int cateNo);
	public List<Category> getCategories();
	public List<Category> getCategoriesByParentNo(@Param("parentCateNo") int parentNo, @Param("dbType") int dbType);
	public List<Category> getCategoriesByDepth(@Param("depth") int depth, @Param("dbType") int dbType);
}
