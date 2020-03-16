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
		model.addAttribute("files", fileList); //file���
		return "file_list";
	}
	
	@RequestMapping(value= "/fileDownload.do", method= RequestMethod.GET)
	public void fileDownloader(HttpServletRequest request, HttpServletResponse response, FileVO fileVO) throws Exception {
		
		logger.info("idx ======" + fileVO.getIdx());
		
		//get������� �Ѿ�� idx ��ȣ�� ������ file������ ã�ƿ´�.
		FileVO fileinfo = fileService.getFileInfo(fileVO);
		
		//ã�ƿ� ���������� ����� ���ϸ��� �����´�.
		String fileName = fileinfo.getStoredFileName();
		
		//�ٿ�ε� ȸ���� ������Ų��.
		fileService.increaseDownCnt(fileVO);
		
		logger.info("****************** " + fileName);
		
		try {
			//���ε� �� ���� �������� ���ϸ��� ������ ������ ã�ƿ´�.
			File file = new File(UPLOAD_PATH + File.separator + fileName);
			
			//�������� ������ ���� ������ �޽����� �����ش�.
			if (!file.exists()) {
				logger.info("file not found~~");
				return ;
			}
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			/**** java.net.URLEncoder.encode�� UTF-8�� �Ͽ��� �Ѵ�. �ѱ�,space����, Ư�����ڷ� ���ؼ� �ٿ�ε� �ȵǴ� ���� ������. **/
			response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\";");
			response.setContentLength((int) file.length());
			
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			
			//���������� �����ϴ� ���� ����� ������ ��.
			FileCopyUtils.copy(fis, os);
			
			fis.close();
            os.close();
			
		} catch(IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@RequestMapping(value = "/fileupload.do", method = RequestMethod.POST)
	public String upload(MultipartFile uploadfile, Model model) throws Exception {
		logger.info("upload() POST ȣ��");
		logger.info("���� �̸�: {}", uploadfile.getOriginalFilename());
		logger.info("���� ũ��: {}", uploadfile.getSize());
		
		String result = saveFile(uploadfile);
		if(result !=null){ // ���� ���� ����
			
			FileVO fileinfo = new FileVO();
			fileinfo.setBoardIdx("1");
			fileinfo.setCreatorId("richard");
			fileinfo.setFileSize(uploadfile.getSize());
			fileinfo.setOriginalFileName(uploadfile.getOriginalFilename());
			fileinfo.setStoredFileName(result);
			fileinfo.setDownCnt(0);
			fileinfo.setDelYn("N");
			
			//���������� DB�� �����Ѵ�.
			fileService.insertFileInfo(fileinfo);
			
	        model.addAttribute("result", result);
	    } else { 
	    	// ���� ���� ����
	        model.addAttribute("result","fail");
	    }
		
		return "home";
	}
	
	@RequestMapping(value = "/multfileupload.do", method = RequestMethod.POST)
	public String upload(MultipartFile[] uploadfiles, Model model) throws Exception{

	    String result = "";
	    String savedFileName = "";
	    
	    FileVO fileinfo = new FileVO();
		fileinfo.setBoardIdx("1");       //�����ȣ�� �Է��Ѵ�.
		fileinfo.setCreatorId("richard"); //login ����ڸ� �Է��Ѵ�.
		fileinfo.setDownCnt(0);
		fileinfo.setDelYn("N");
	    
	    for(MultipartFile f : uploadfiles){
	        savedFileName = saveFile(f);
	        result += savedFileName;
	        
	        fileinfo.setFileSize(f.getSize());
			fileinfo.setOriginalFileName(f.getOriginalFilename());
			fileinfo.setStoredFileName(savedFileName);
			
			//���������� DB�� �����Ѵ�.
			fileService.insertFileInfo(fileinfo);
			
	    }
	    model.addAttribute("result",result);

	    return "home";
	}
	
	//���� write 
	private String saveFile(MultipartFile file){
	    // ���� �̸� ����
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();

	    logger.info("saveName: {}",saveName);

	    // ������ File ��ü�� ����(������ ����)
	    File saveFile = new File(UPLOAD_PATH,saveName); // ������ ���� �̸�, ������ ���� �̸�

	    try {
	        file.transferTo(saveFile); // ���ε� ���Ͽ� saveFile�̶�� ������ ����
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile
}
