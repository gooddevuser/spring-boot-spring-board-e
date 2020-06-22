package com.springdemo.bootboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springdemo.bootboard.mapper.BoardMapper;
import com.springdemo.bootboard.vo.Board;
import com.springdemo.bootboard.vo.BoardFile;

@Service("boardService") // == @Component("boardService")
                         // == <bean id="boardService" class="...BoardServiceImpl"
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;
	
	@Transactional
	@Override
	public void writeBoard(Board board) {		
		boardMapper.insertBoard(board);
		for (BoardFile f : board.getFileList()) {
			//자동 증가로 만들어진 글번호를 파일 VO에 적용
			f.setBoardIdx(board.getBoardIdx());
		}
		boardMapper.insertBoardFileList(board.getFileList());
	}

	@Override
	public List<Board> findBoardList() {
		List<Board> boards = boardMapper.selectBoardList();
		return boards;
	}

	@Override
	public Board findBoardByBoardIdx(int boardIdx) {
		Board board = boardMapper.selectBoardByBoardIdx(boardIdx);
		return board;
	}

	@Override
	public void updateBoard(Board board) {
		boardMapper.updateBoard(board);
		
	}

	@Override
	public void deleteBoard(Board board) {

		boardMapper.deleteBoard(board);
		
	}

	@Override
	public BoardFile findBoardFileByIdx(int idx) {

		return boardMapper.selectBoardFileByIdx(idx);

	}

}
