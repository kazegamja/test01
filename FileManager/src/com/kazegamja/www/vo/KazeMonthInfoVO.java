package com.kazegamja.www.vo;

import java.util.ArrayList;
import java.util.List;

import com.kazegamja.www.util.KazeCRCChecker;

public class KazeMonthInfoVO {
	
	private KazeYearInfoVO kazeYearInfoVO;
	
	public KazeMonthInfoVO(KazeYearInfoVO yearInfoVO) {
		this.kazeYearInfoVO = yearInfoVO;
	}
	
	// 이미지 정보 리스트
	private List<KazeFileInfoVO> imageInfoList = new ArrayList<>();

	public List<KazeFileInfoVO> getImageInfoList() {
		return imageInfoList;
	}

	public void setImageInfoList(List<KazeFileInfoVO> imageInfoList) {
		this.imageInfoList = imageInfoList;
	}

	public void addKazeFileInfoVO(KazeFileInfoVO infoVO) {
		
		long outModifyMil = infoVO.getLastModifiedTimeMil();
		long outFileSieze = infoVO.getSize();
		
		boolean isSameFile = false;
		
		String originFilePath = "";
		List<KazeFileInfoVO> targetList = null;
		
		if (",JPG,HEIC,".indexOf(infoVO.getFileExt()) > 0) {			
			targetList = getImageInfoList();
		} else if (",MOV,MP4,MTS,".indexOf(infoVO.getFileExt()) > 0) {
			targetList = kazeYearInfoVO.getMovieList();
		} else {
			targetList = kazeYearInfoVO.getEtcList();
		}
		
		for (KazeFileInfoVO inVO : targetList) {			
			
			long inModifyMil = inVO.getLastModifiedTimeMil();
			long inFileSieze = inVO.getSize();
			
			if (outModifyMil == inModifyMil) {
				
				long outInfoCRC32 = KazeCRCChecker.getCRC32Value(infoVO.getFilePath().toString());
				long inInfoCRC32 = KazeCRCChecker.getCRC32Value(inVO.getFilePath().toString());
				
				if (outInfoCRC32 == inInfoCRC32) {					
					isSameFile = true;
					originFilePath = inVO.getFilePath().toString();
					break;				
				}
			}			
		}
		
		if (isSameFile) {
			kazeYearInfoVO.getSameList().add(infoVO);
			kazeYearInfoVO.getKazePicrureManagerVO().toSameLog(originFilePath, infoVO.getFilePath().toString());
		} else {
			targetList.add(infoVO);
		}
	}
	
	
}
