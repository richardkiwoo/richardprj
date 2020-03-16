package kr.co.richardprj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.example.controller.HomeController;

import kr.co.richardprj.dto.FileVO;
import kr.co.richardprj.service.FileService;

@Controller
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private static final String UPLOAD_PATH = "D:\\Study\\fileupload";
	
	@Inject
	private FileService fileService;
	
	@RequestMapping(value = "/downloadFileList.do", method = RequestMethod.GET)
	public String downloadFileList(Model model) throws Exception {
		List<FileVO> fileList = fileService.getFileList();
		model.addAttribute("files", fileList); //file목록
		return "file_list";
	}
	
	@RequestMapping(value= "/fileDownload.do", method= RequestMethod.GET)
	public void fileDownloader(HttpServletRequest request, HttpServletResponse response, FileVO fileVO) throws Exception {
		
		logger.info("idx ======" + fileVO.getIdx());
		
		//get방식으로 넘어온 idx 번호를 가지고 file정보를 찾아온다.
		FileVO fileinfo = fileService.getFileInfo(fileVO);
		
		//찾아온 파일정보의 저장된 파일명을 가져온다.
		String fileName = fileinfo.getStoredFileName();
		
		//다운로드 회수를 증가시킨다.
		fileService.increaseDownCnt(fileVO);
		
		logger.info("****************** " + fileName);
		
		try {
			//업로드 한 곳을 기준으로 파일명을 가지고 파일을 찾아온다.
			File file = new File(UPLOAD_PATH + File.separator + fileName);
			
			//존재하지 않으면 파일 미존재 메시지를 던져준다.
			if (!file.exists()) {
				logger.info("file not found~~");
				return ;
			}
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			/**** java.net.URLEncoder.encode를 UTF-8로 하여야 한다. 한글,space문자, 특수문자로 인해서 다운로드 안되는 것을 방지함. **/
			response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\";");
			response.setContentLength((int) file.length());
			
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			
			//스프링에서 제공하는 좋은 기능을 가져다 씀.
			FileCopyUtils.copy(fis, os);
			
			fis.close();
            os.close();
			
		} catch(IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/fileupload.do", method = RequestMethod.POST)
	public String upload(MultipartFile uploadfile, Model model) throws Exception {
		logger.info("upload() POST 호출");
		logger.info("파일 이름: {}", uploadfile.getOriginalFilename());
		logger.info("파일 크기: {}", uploadfile.getSize());
		
		String result = saveFile(uploadfile);
		if(result !=null){ // 파일 저장 성공
			
			FileVO fileinfo = new FileVO();
			fileinfo.setBoardIdx("1");
			fileinfo.setCreatorId("richard");
			fileinfo.setFileSize(uploadfile.getSize());
			fileinfo.setOriginalFileName(uploadfile.getOriginalFilename());
			fileinfo.setStoredFileName(result);
			fileinfo.setDownCnt(0);
			fileinfo.setDelYn("N");
			
			//파일정보를 DB에 저장한다.
			fileService.insertFileInfo(fileinfo);
			
	        model.addAttribute("result", result);
	    } else { 
	    	// 파일 저장 실패
	        model.addAttribute("result","fail");
	    }
		
		return "home";
	}
	
	@RequestMapping(value = "/multfileupload.do", method = RequestMethod.POST)
	public String upload(MultipartFile[] uploadfiles, Model model) throws Exception{

	    String result = "";
	    String savedFileName = "";
	    
	    FileVO fileinfo = new FileVO();
		fileinfo.setBoardIdx("1");       //보드번호를 입력한다.
		fileinfo.setCreatorId("richard"); //login 사용자를 입력한다.
		fileinfo.setDownCnt(0);
		fileinfo.setDelYn("N");
	    
	    for(MultipartFile f : uploadfiles){
	        savedFileName = saveFile(f);
	        result += savedFileName;
	        
	        fileinfo.setFileSize(f.getSize());
			fileinfo.setOriginalFileName(f.getOriginalFilename());
			fileinfo.setStoredFileName(savedFileName);
			
			//파일정보를 DB에 저장한다.
			fileService.insertFileInfo(fileinfo);
			
	    }
	    model.addAttribute("result",result);

	    return "home";
	}
	
	//파일 write 
	private String saveFile(MultipartFile file){
	    // 파일 이름 변경
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();

	    logger.info("saveName: {}",saveName);

	    // 저장할 File 객체를 생성(껍데기 파일)
	    File saveFile = new File(UPLOAD_PATH,saveName); // 저장할 폴더 이름, 저장할 파일 이름

	    try {
	        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile
}
