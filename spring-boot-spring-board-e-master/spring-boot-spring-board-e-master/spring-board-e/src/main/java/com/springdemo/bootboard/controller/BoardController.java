package com.springdemo.bootboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.springboard.common.Util;
import com.springdemo.bootboard.service.BoardService;
import com.springdemo.bootboard.vo.Board;
import com.springdemo.bootboard.vo.BoardFile;

@Controller
@RequestMapping(path = { "/board" })
public class BoardController {
	
	@Autowired
	@Qualifier("boardService")
	BoardService boardService;
	
	@GetMapping(path = { "/list" })
	public String showList(Model model) {
		
		List<Board> boards = boardService.findBoardList();
		//View에서 읽을 수 있도록 저장 (실제로는 Request객체에 저장)
		model.addAttribute("boards", boards); 
		
		return "board/list";		
	}
	
	@GetMapping(path = { "/write" })
	public String showWriteForm() {		
		return "board/write";		
	}
	
	@PostMapping(path = { "/write" })
	//public String doWrite(Board board, MultipartFile[] files) {
	public String doWrite(Board board, MultipartHttpServletRequest req) {
		//MultipartFile file = req.getFile("file");
		
		List<BoardFile> files = parseAndSaveUploadFiles(req);
		board.setFileList(files);
		
		try {
			boardService.writeBoard(board);
			//boardService.writeBoardFiles(files);
			System.out.println(board.getBoardIdx()); // 자동 증가 값 확인 코드
		} catch (Exception ex) {
			System.out.println("등록 실패");
			ex.printStackTrace();			
		}
				
		return "redirect:list";		
	}
	
	@GetMapping(path = { "/detail" })
	public String showDetail(
			@RequestParam("board_idx")int boardIdx, Model model) {
		
		Board board = boardService.findBoardByBoardIdx(boardIdx);
		if (board == null) {
			return "redirect:list";
		}
		
		model.addAttribute("board", board);
		
		return "board/detail";
	}
	
	@PostMapping(path = { "/update" })
	public String updateBoard(Board board) {
				
		boardService.updateBoard(board);
		
		return "redirect:detail?board_idx=" + board.getBoardIdx();
	}
	
	@PostMapping(path = { "/delete" })
	public String deleteBoard(Board board) {
				
		boardService.deleteBoard(board);
		
		return "redirect:list";
	}
	
	@GetMapping("/download/{idx}")
	public void download(@PathVariable int idx, 
						 HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BoardFile file = boardService.findBoardFileByIdx(idx);
		
		response.setContentType("application/octet-stream");
		
		response.addHeader(
				"Content-Disposition", 
				String.format("Attachment;Filename=\"%s\"", 
					new String(file.getUserFileName().getBytes("utf-8"), "ISO-8859-1")));
		
		ServletContext application = request.getServletContext();
		String file2 = application.getRealPath("/upload-files/" + file.getSavedFileName());
				
		byte[] fileContents = FileUtils.readFileToByteArray(new File(file2));
		
		ServletOutputStream sos = response.getOutputStream();
		sos.write(fileContents);
		sos.flush();
		sos.close();
	}
	
	//////////////////////////////////////////////////////
	// Util
	List<BoardFile> parseAndSaveUploadFiles(MultipartHttpServletRequest req) {
		
		ArrayList<BoardFile> boardFiles = new ArrayList<>();
		
		if (!ObjectUtils.isEmpty(req)) {

			String dirPath = req.getServletContext().getRealPath("/upload-files/");

			System.out.println(dirPath);
			
			Iterator<String> iter = req.getFileNames();
			while(iter.hasNext()) { // 다음 항목이 있는지 확인
				String name = iter.next(); // 다음 항목 반환
				List<MultipartFile> files = req.getFiles(name); // 파일 들의 이름을 리스트 꼴로 여러가지 담음.
				
				
				for(MultipartFile file : files) {
					String originalFileName = file.getOriginalFilename();
					String uniqueFileName = Util.makeUniqueFileName(originalFileName);
					try {
						
						//파일을 Disk에 저장
						file.transferTo(new File(dirPath, uniqueFileName));
						
						//파일 정보를 VO에 저장하고 목록에 추가 ( -> DB에 저장 )
						BoardFile boardFile = new BoardFile();
						boardFile.setUserFileName(originalFileName);
						boardFile.setSavedFileName(uniqueFileName);
						boardFile.setFileSize(file.getSize());
						boardFile.setCreatedDatetime(new Date());
						boardFile.setCreatorId("");
						boardFiles.add(boardFile);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return boardFiles;
	}

}















