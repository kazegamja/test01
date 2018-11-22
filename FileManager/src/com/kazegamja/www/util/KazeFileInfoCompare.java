package com.kazegamja.www.util;

import java.util.Comparator;

import com.kazegamja.www.vo.KazeFileInfoVO;

public class KazeFileInfoCompare implements Comparator<KazeFileInfoVO> {

	@Override
	public int compare(KazeFileInfoVO info01, KazeFileInfoVO info02) {
		long mil01 = info01.getLastModifiedTimeMil();
		long mil02 = info02.getLastModifiedTimeMil();
		
		if (mil01 > mil02) {
			return 1;
		} else if (mil01 < mil02) {
			return -1;
		} else {
			return 0;			
		}
	}

}
