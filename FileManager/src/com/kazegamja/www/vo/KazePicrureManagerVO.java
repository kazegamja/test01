package com.kazegamja.www.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KazePicrureManagerVO {
	
	// 2010³â
	private HashMap<String, KazeYearInfoVO> yearMap = new HashMap<>();
	private HashMap<String, List<String>> sameFileLogMap = new HashMap<>();

	public HashMap<String, KazeYearInfoVO> getYearMap() {
		return yearMap;
	}

	public void setYearMap(HashMap<String, KazeYearInfoVO> yearMap) {
		this.yearMap = yearMap;
	}
	
	public void addFileInfo(KazePicrureManagerVO managerVO, KazeFileInfoVO fileInfoVO) {
		
		String year = fileInfoVO.getLastModifiedYear();
		String month = fileInfoVO.getLastModifiedMonth();		
		
		if (yearMap.containsKey(year)) {
			
			KazeYearInfoVO yearInfoVO = yearMap.get(year);
			
			if (yearInfoVO.getMonthMap().containsKey(month)) {
				
				KazeMonthInfoVO monthInfoVO = yearInfoVO.getMonthMap().get(month);				
				monthInfoVO.addKazeFileInfoVO(fileInfoVO);
				
			} else {
				
				KazeMonthInfoVO monthInfoVO = new KazeMonthInfoVO(yearInfoVO);
				monthInfoVO.addKazeFileInfoVO(fileInfoVO);
				
				yearInfoVO.getMonthMap().put(month, monthInfoVO);
			}
			
		} else {
			
			KazeYearInfoVO yearInfoVO = new KazeYearInfoVO(managerVO);
			KazeMonthInfoVO monthInfoVO = new KazeMonthInfoVO(yearInfoVO);			
			monthInfoVO.addKazeFileInfoVO(fileInfoVO);
			
			yearInfoVO.getMonthMap().put(month, monthInfoVO);
			yearMap.put(year, yearInfoVO);
		}
	}
	
	public void toSameLog(String originFilePath, String targetFilePath) {
		
		if (sameFileLogMap.containsKey(originFilePath)) {
			
			sameFileLogMap.get(originFilePath).add(targetFilePath);
			
		} else {
			
			List<String> sameList = new ArrayList<>();
			sameList.add(targetFilePath);
			
			sameFileLogMap.put(originFilePath, sameList);
		}
		
	}

}
