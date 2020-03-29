package kr.co.richardprj.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.richardprj.dto.GameVO;
import kr.co.richardprj.service.ExcelService;

@Controller
public class ExcelController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
	
	@Inject
	private ExcelService excelService;

	@RequestMapping(value = "/excelDownloadUpload.do", method=RequestMethod.GET)
	public String excelDownloadUpload(Model model) throws Exception {
		
		return "excelDownloadView";
	}
	
	@RequestMapping(value = "/downloadExcelFile.do", method=RequestMethod.POST)
	public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		List<GameVO> list = excelService.makeGameResultList();
		
		SXSSFWorkbook workbook = excelService.excelFileDownloadProcess(list);

		////////////////////////////////
        
        Locale locale = Locale.KOREA;
        OutputStream os = null;
        
        try {
        	
        	// 겹치는 파일 이름 중복을 피하기 위해 시간을 이용해서 파일 이름에 추가
        	// to avoid dup file name
            Date date = new Date();
            SimpleDateFormat dayformat = new SimpleDateFormat("yyyyMMdd", locale);
            SimpleDateFormat hourformat = new SimpleDateFormat("hhmmss", locale);
            String day = dayformat.format(date);
            String hour = hourformat.format(date);
            String fileName = "GameResult" + "_" + day + "_" + hour + ".xlsx";         
            
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(fileName, "utf-8") + "\";");
			
			os = response.getOutputStream();
			
			workbook.write(os);
			
			os.close();
			
		} catch(IOException e) {
			logger.error(e.getMessage());
		}finally {
            if(workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            if(os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //////////////////////////////
        
	}
	
	@RequestMapping(value = "/uploadExcelFile.do", method = RequestMethod.POST)
    public String uploadExcelFile(MultipartHttpServletRequest request, Model model) {
        MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if(iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
        int result = excelService.uploadExcelFile(file);
        
        return "excelDownloadView";
    }
}
