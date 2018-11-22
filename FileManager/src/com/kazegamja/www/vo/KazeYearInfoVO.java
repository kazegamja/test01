package com.kazegamja.www.vo;

import java.util.ArrayList;
import java.util.HashMap;

public class KazeYearInfoVO {
	
	private KazePicrureManagerVO kazePicrureManagerVO;
	
	public KazeYearInfoVO(KazePicrureManagerVO managerInfoVO) {
		this.kazePicrureManagerVO = managerInfoVO;
	}
	
	// 1¿ù~12¿ù
	private HashMap<String, KazeMonthInfoVO> monthMap = new HashMap<>();
	
	private ArrayList<KazeFileInfoVO> movieList = new ArrayList<>();
	private ArrayList<KazeFileInfoVO> etcList = new ArrayList<>();
	private ArrayList<KazeFileInfoVO> sameList = new ArrayList<>();

	public KazePicrureManagerVO getKazePicrureManagerVO() {
		return kazePicrureManagerVO;
	}
	public void setKazePicrureManagerVO(KazePicrureManagerVO kazePicrureManagerVO) {
		this.kazePicrureManagerVO = kazePicrureManagerVO;
	}
	public HashMap<String, KazeMonthInfoVO> getMonthMap() {
		return monthMap;
	}
	public void setMonthMap(HashMap<String, KazeMonthInfoVO> monthMap) {
		this.monthMap = monthMap;
	}
	public ArrayList<KazeFileInfoVO> getMovieList() {
		return movieList;
	}
	public void setMovieList(ArrayList<KazeFileInfoVO> movieList) {
		this.movieList = movieList;
	}
	public ArrayList<KazeFileInfoVO> getEtcList() {
		return etcList;
	}
	public void setEtcList(ArrayList<KazeFileInfoVO> etcList) {
		this.etcList = etcList;
	}
	public ArrayList<KazeFileInfoVO> getSameList() {
		return sameList;
	}
	public void setSameList(ArrayList<KazeFileInfoVO> sameList) {
		this.sameList = sameList;
	}
}
