package kr.co.richardprj.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.richardprj.dao.GameDAO;
import kr.co.richardprj.dto.GameVO;

/**
 * 엑셀파일로 만들 리스트 생성
 * @return 엑셀파일 리스트
 */

@Service
public class ExcelService {

	@Inject
	private GameDAO dao;
	
	public ArrayList<GameVO> makeGameResultList() throws Exception{
		
		return (ArrayList<GameVO>) dao.selectGameList();
	}
	
	
	/**
     * 과일 리스트를 간단한 엑셀 워크북 객체로 생성
     * 
     */
    public SXSSFWorkbook makeGameResultExcelWorkbook(List<GameVO> list) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        
        // 시트 생성
        SXSSFSheet sheet = workbook.createSheet("게임결과");
        
        //시트 열 너비 설정
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(0, 3000);
        
        // 헤더 행 생
        Row headerRow = sheet.createRow(0);
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("GameID");
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("게임구분");
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Player1");
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("상대팀선수1");
        
        // 과일표 내용 행 및 셀 생성
        Row bodyRow = null;
        Cell bodyCell = null;
        for(int i=0; i<list.size(); i++) {
            GameVO game = (GameVO) list.get(i);
            
            // 행 생성
            bodyRow = sheet.createRow(i+1);
            // 데이터 번호 표시
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(i + 1);
            // 데이터 이름 표시
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(game.getGameID());
            // 데이터 가격 표시
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(game.getsDCd());
            // 데이터 수량 표시
            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(game.getTeamPlayerNm());
            
            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(game.getoTeamPlayerNm1());
        }
        
        return workbook;
    }
    
	
	/**
     * 생성한 엑셀 워크북을 컨트롤레에서 받게해줄 메소
     * @param list
     * @return
     */
    public SXSSFWorkbook excelFileDownloadProcess(List<GameVO> list) {
        return this.makeGameResultExcelWorkbook(list);
    }

    
    /**
     *업로드한 엑셀파일을 게임 리스트로 만들기
     */
    public List<GameVO> uploadExcelFile(MultipartFile excelFile){
        List<GameVO> list = new ArrayList<GameVO>();
        try {
            OPCPackage opcPackage = OPCPackage.open(excelFile.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
            
            // 첫번째 시트 불러오기
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            for(int i=1; i<sheet.getLastRowNum() + 1; i++) {
                GameVO game = new GameVO();
                XSSFRow row = sheet.getRow(i);
                
                // 행이 존재하기 않으면 패스
                if(null == row) {
                    continue;
                }
                
                // 행의 두번째 열(이름부터 받아오기) 
                XSSFCell cell = row.getCell(1);
                if(null != cell) game.setGameID((int)cell.getNumericCellValue());
                // 행의 세번째 열 받아오기
                cell = row.getCell(2);
                if(null != cell) game.setsDCd(cell.getStringCellValue());
                // 행의 네번째 열 받아오기
                cell = row.getCell(3);
                if(null != cell) game.setMbrId(cell.getStringCellValue());
                
                list.add(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
