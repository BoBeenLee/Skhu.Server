package com.skhu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


public class FacebookForm implements Serializable {
	@JsonProperty("data")
	public Facebook[] data;
	@JsonProperty("paging")
	public Paging paging;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Paging implements Serializable {
		@JsonProperty("previous")
		public String previous;
		@JsonProperty("next")
		public String next;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Facebook implements Serializable {
		@JsonProperty("id")
		public String id;
		@JsonProperty("from")
		public From from;
		@JsonProperty("picture")
		public String picture;
		@JsonProperty("message")
		public String message;
		@JsonProperty("link")
		public String link;
		@JsonProperty("updated_time")
		public String updatedTime;
		@JsonProperty("created_time")
		public String createdTime;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class From implements Serializable {
		@JsonProperty("category")
		public String category;
		@JsonProperty("name")
		public String name;
		@JsonProperty("id")
		public String id;
	}
}
