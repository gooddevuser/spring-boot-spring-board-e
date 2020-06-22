package com.springdemo.bootboard.service;

import java.util.List;

import com.springdemo.bootboard.vo.Board;
import com.springdemo.bootboard.vo.BoardFile;

public interface BoardService {
	
	void writeBoard(Board board);

	List<Board> findBoardList();

	Board findBoardByBoardIdx(int boardIdx);

	void updateBoard(Board board);

	void deleteBoard(Board board);

	BoardFile findBoardFileByIdx(int idx);

}
