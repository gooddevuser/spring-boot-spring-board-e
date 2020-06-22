package com.springdemo.bootboard.vo;

import java.util.Date;

import lombok.Data;

@Data
public class BoardFile {
	
	private int idx;
	private int boardIdx;
	private String userFileName;
	private String savedFileName;
	private long fileSize;
	private String creatorId;
	private Date createdDatetime;
}
