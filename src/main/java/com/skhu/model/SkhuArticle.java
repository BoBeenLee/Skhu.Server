package com.skhu.model;

import java.sql.Timestamp;

public class SkhuArticle {
	public long skhuNo;
	public String brdNo;
	public String name;
	public String id;
	public String subject;
	public String url;
	public byte[] content;
	public int cateNo;
	public String cateId;
	public String img;
	public String attach;
	public int replyState;
	public int commentCnt;
	public Timestamp created;
	
	public String cateNm;

	public String getCateId() {
		return cateId;
	}

	public int getReplyState() {
		return replyState;
	}

	public void setReplyState(int replyState) {
		this.replyState = replyState;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public long getSkhuNo() {
		return skhuNo;
	}

	public String getBrdNo() {
		return brdNo;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public String getUrl() {
		return url;
	}

	public byte[] getContent() {
		return content;
	}

	public int getCateNo() {
		return cateNo;
	}

	public String getImg() {
		return img;
	}

	public String getAttach() {
		return attach;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public Timestamp getCreated() {
		return created;
	}

	public String getCateNm() {
		return cateNm;
	}

	public void setSkhuNo(long skhuNo) {
		this.skhuNo = skhuNo;
	}

	public void setBrdNo(String brdNo) {
		this.brdNo = brdNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void setCateNo(int cateNo) {
		this.cateNo = cateNo;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public void setCateNm(String cateNm) {
		this.cateNm = cateNm;
	}
}
