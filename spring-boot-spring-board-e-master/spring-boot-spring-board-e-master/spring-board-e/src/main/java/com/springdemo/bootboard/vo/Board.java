package com.springdemo.bootboard.vo;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Board {

	private int boardIdx;
	private String title;
	private String contents;
	private int hitCount;
	private String creatorId;
	private Date createdDatetime;
	private String updaterId;
	private Date updatedDatetime;
	private String deletedYn;
	
	//Board : BoardFile의 1 : Many 관계를 구현하는 필드(변수)
	private List<BoardFile> fileList;
	
}
