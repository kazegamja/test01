package com.kazegamja.www.vo;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class KazeFileInfoVO {
	
	/**
	 * 파일비교 원칙
	 * 1) CRC32
	 * 2) 파일 수정한 날짜
	 * 3) 파일 사이즈
	 */	
	private Path filePath;
	private long crc32;
	private long size;
	
	private FileTime lastModifiedTime;	
	private long lastModifiedTimeMil;
	private String lastModifiedDate;
	
	private String lastModifiedYear;
	private String lastModifiedMonth;	
	private String fileExt;
	
	public Path getFilePath() {
		return filePath;
	}
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}
	public long getCrc32() {
		return crc32;
	}
	public void setCrc32(long crc32) {
		this.crc32 = crc32;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public FileTime getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(FileTime lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public long getLastModifiedTimeMil() {
		return lastModifiedTimeMil;
	}
	public void setLastModifiedTimeMil(long lastModifiedTimeMil) {
		this.lastModifiedTimeMil = lastModifiedTimeMil;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getLastModifiedYear() {
		return lastModifiedYear;
	}
	public void setLastModifiedYear(String lastModifiedYear) {
		this.lastModifiedYear = lastModifiedYear;
	}
	public String getLastModifiedMonth() {
		return lastModifiedMonth;
	}
	public void setLastModifiedMonth(String lastModifiedMonth) {
		this.lastModifiedMonth = lastModifiedMonth;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}	
}
