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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.co.richardprj.dto.FileVO;
import kr.co.richardprj.service.FileService;

@Controller
@PropertySource("classpath:/property/global.properties")
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Inject
	private FileService fileService;
	
	@Autowired
	ApplicationContext context;
	
	
	@RequestMapping(value = "/downloadFileList.do", method = RequestMethod.GET)
	public String downloadFileList(Model model) throws Exception {
		List<FileVO> fileList = fileService.getFileList();
		model.addAttribute("files", fileList);
		return "file_list";
	}
	
	@RequestMapping(value= "/fileDownload.do", method= RequestMethod.GET)
	public void fileDownloader(HttpServletRequest request, HttpServletResponse response, FileVO fileVO) throws Exception {
		
		logger.info("idx ======" + fileVO.getIdx());
		
		FileVO fileinfo = fileService.getFileInfo(fileVO);
		String fileName = fileinfo.getStoredFileName();
		fileService.increaseDownCnt(fileVO);
		
		logger.info("****************** " + fileName);
		
		Environment env = context.getEnvironment();

		String UPLOAD_PATH = env.getProperty("upload.path");
		
		try {
			File file = new File(UPLOAD_PATH + File.separator + fileName);
			
			if (!file.exists()) {
				logger.info("file not found~~");
				return ;
			}
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			/**** java.net.URLEncoder.encode , UTF-8 ,need to remove space at the end, **/
			response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\";");
			response.setContentLength((int) file.length());
			
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			
			//use file copy utils from input stream to output stream
			FileCopyUtils.copy(fis, os);
			
			fis.close();
            os.close();
			
		} catch(IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/fileupload.do", method = RequestMethod.POST)
	public String upload(MultipartFile uploadfile, Model model) throws Exception {
		logger.info("upload() POST method");
		logger.info("file name : ", uploadfile.getOriginalFilename());
		logger.info("file size : ", uploadfile.getSize());
		
		String result = saveFile(uploadfile);
		if(result !=null){ // upload file is not empty
			
			FileVO fileinfo = new FileVO();
			fileinfo.setBoardIdx("1");
			fileinfo.setCreatorId("richard");
			fileinfo.setFileSize(uploadfile.getSize());
			fileinfo.setOriginalFileName(uploadfile.getOriginalFilename());
			fileinfo.setStoredFileName(result);
			fileinfo.setDownCnt(0);
			fileinfo.setDelYn("N");
			
			//save the file info.
			fileService.insertFileInfo(fileinfo);
			
	        model.addAttribute("result", result);
	    } else { 
	    	// if failed
	        model.addAttribute("result","fail");
	    }
		
		return "home";
	}
	
	@RequestMapping(value = "/multfileupload.do", method = RequestMethod.POST)
	public String upload(MultipartFile[] uploadfiles, Model model) throws Exception{

	    String result = "";
	    String savedFileName = "";
	    
	    FileVO fileinfo = new FileVO();
		fileinfo.setBoardIdx("1");       //board no
		fileinfo.setCreatorId("richard"); //login id
		fileinfo.setDownCnt(0);
		fileinfo.setDelYn("N"); //delete yes or no
	    
	    for(MultipartFile f : uploadfiles){
	        savedFileName = saveFile(f);
	        result += savedFileName;
	        
	        fileinfo.setFileSize(f.getSize());
			fileinfo.setOriginalFileName(f.getOriginalFilename());
			fileinfo.setStoredFileName(savedFileName);
			
			//insert file info
			fileService.insertFileInfo(fileinfo);
			
	    }
	    model.addAttribute("result",result);

	    return "home";
	}
	
	//file upload into dedicated location
	private String saveFile(MultipartFile file){
	    // uuid 
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();

	    logger.info("saveName: ",saveName);
	    
	    Environment env = context.getEnvironment();
		logger.info("$$$$$$$$$$$$$$$$$$$$ upload.path=" + env.getProperty("upload.path"));
		
		String UPLOAD_PATH = env.getProperty("upload.path");
	    File saveFile = new File(UPLOAD_PATH,saveName); 

	    try {
	        file.transferTo(saveFile); 
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile
	
	
}
