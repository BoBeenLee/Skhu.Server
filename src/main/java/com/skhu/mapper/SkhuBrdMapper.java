package com.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skhu.model.SkhuArticle;

public interface SkhuBrdMapper {
	public int insertSkhuBrd(SkhuArticle skhu);
	public int insertQnABrd(SkhuArticle skhu);
	public int updateSkhuBrd(SkhuArticle skhu);
	public int updateQnABrd(SkhuArticle skhu);
	public int deleteSkhuBrd(@Param("sBrdNo") String sBrdNo, @Param("lBrdNo") String lBrdNo, @Param("cateNo") int cateNo);
	public int deleteQnABrd(@Param("sBrdNo") String sBrdNo, @Param("lBrdNo") String lBrdNo, @Param("cateNo") int cateNo);
	public List<SkhuArticle> getSkhuBrds(@Param("cates") List<Integer> cateNo, @Param("startNum") int startNum, @Param("pageNum")  int pageNum);
	public List<SkhuArticle> getQnABrds(@Param("cates") List<Integer> cateNo, @Param("startNum") int startNum, @Param("pageNum")  int pageNum);
	public SkhuArticle getSkhuBrd(@Param("brdNo") String brdNo, @Param("cateNo") int cateNo);
	public SkhuArticle getQnABrd(@Param("brdNo") String brdNo, @Param("cateNo") int cateNo);
	public List<SkhuArticle> getSkhuBrdsByFilter(@Param("cateNo") int cateNo, @Param("filter") String filter);
	
	
	
}
