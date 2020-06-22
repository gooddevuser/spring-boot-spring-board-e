package com.springdemo.bootboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springdemo.bootboard.vo.Board;
import com.springdemo.bootboard.vo.BoardFile;

@Mapper // 현재 인터페이스의 패키지명과 인터페이스 이름이 일치하는 
        // .xml 파일과 매핑
public interface BoardMapper {
	
	void insertBoard(Board board);

	List<Board> selectBoardList();

	Board selectBoardByBoardIdx(int boardIdx);

	void updateBoard(Board board);

	void deleteBoard(Board board);
	
	void insertBoardFileList(List<BoardFile> files);

	BoardFile selectBoardFileByIdx(int idx);

}
