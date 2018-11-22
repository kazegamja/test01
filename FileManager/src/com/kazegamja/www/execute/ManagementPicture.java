package com.kazegamja.www.execute;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.kazegamja.www.util.KazeFileInfoCompare;
import com.kazegamja.www.vo.KazeFileInfoVO;
import com.kazegamja.www.vo.KazePicrureManagerVO;
import com.kazegamja.www.vo.KazeYearInfoVO;

public class ManagementPicture {
	
	private KazePicrureManagerVO managerVO;
	private SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void readFileInfo(String targetFileFullPath) {
		
		managerVO = new KazePicrureManagerVO();
		
		try {
			List<Path> filesInFolder = Files.walk(Paths.get(targetFileFullPath))
			        .filter(Files::isRegularFile)			        
			        .collect(Collectors.toList());
			
			int fileCnt = 0;
			
			for (Path p : filesInFolder) {				
				
				Path filePath = p;				
				long crc32 = 0;
				long size = Files.size(p);

				FileTime lastModifiedTime = Files.getLastModifiedTime(p); 	
				long lastModifiedTimeMil = lastModifiedTime.toMillis();
				String lastModifiedDate = lastModifiedTime.toString().substring(0, 10);

				String lastModifiedYear = lastModifiedTime.toString().substring(0, 4);
				String lastModifiedMonth = lastModifiedTime.toString().substring(5, 7);
				
				int pos = p.toFile().getAbsolutePath().lastIndexOf( "." );
				String fileExt = p.toFile().getAbsolutePath().substring( pos + 1 ).toUpperCase();
				
				if ("2011년도 03월 013.JPG".equals(p.toFile().getName())) {
					System.out.println("STOP!!");					
				}
					
				
				
				if ("JPG".equals(fileExt) || "HEIC".equals(fileExt)) {					
					// exif 정보 읽기
					try {
						
						Metadata metadata = ImageMetadataReader.readMetadata(new FileInputStream(p.toFile()));
						ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
						Date date = directory.getDateOriginal(TimeZone.getDefault());
						
						if (date != null) {
							lastModifiedTimeMil = date.getTime();							
							lastModifiedDate = simpleDate.format(date).substring(0, 10);
							lastModifiedYear = simpleDate.format(date).substring(0, 4);
							lastModifiedMonth = simpleDate.format(date).substring(5, 7);							
						}
						
					} catch (NoSuchMethodError e) {
						System.out.println("확인필요 >> " + filePath);
					} catch (Exception e) {				
						// e.printStackTrace();
						System.out.println("확인필요 >> " + filePath);
					}
				}
				
				
				KazeFileInfoVO infoVO = new KazeFileInfoVO();
				
				infoVO.setCrc32(crc32);
				infoVO.setFileExt(fileExt);
				infoVO.setFilePath(filePath);
				infoVO.setLastModifiedDate(lastModifiedDate);
				infoVO.setLastModifiedMonth(lastModifiedMonth);
				infoVO.setLastModifiedTime(lastModifiedTime);
				infoVO.setLastModifiedTimeMil(lastModifiedTimeMil);
				infoVO.setLastModifiedYear(lastModifiedYear);
				infoVO.setSize(size);				
				
				managerVO.addFileInfo(managerVO, infoVO);
				
				fileCnt++;
				
				if (fileCnt % 1000 == 0) {
					System.out.println(fileCnt);
				}
			}
			
			
			
			System.out.println("READ END!!");
			System.out.println("Total File Cnt >> " + fileCnt);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeNewFile(String baseFilePath) throws Exception {
		
		Iterator<String> yKeyIt = managerVO.getYearMap().keySet().iterator();
		
		String targetFolderPath = "";
		
		while (yKeyIt.hasNext()) {
			
			String year = yKeyIt.next();
			targetFolderPath = baseFilePath + "/" + year + "년";
			
			File yTarFolder = new File(targetFolderPath);
			
			if (!yTarFolder.exists()) {
					
				if (yTarFolder.mkdirs()) {
					
					KazeYearInfoVO yearInfoVO = managerVO.getYearMap().get(year);
					Iterator<String> mKeyIt = yearInfoVO.getMonthMap().keySet().iterator();					
					
					
					// 1. 사진 파일 복사
					while (mKeyIt.hasNext()) {
						
						String month = mKeyIt.next();
						String mFolderPath = targetFolderPath + "/" + month + "월";
						
						System.out.println("Process >> " + mFolderPath);
						
						File mTarFolder = new File(mFolderPath);						
						if (!mTarFolder.exists()) {							
							if (mTarFolder.mkdirs()) {
								List<KazeFileInfoVO> imageList = yearInfoVO.getMonthMap().get(month).getImageInfoList();								
								copyFileByList(mFolderPath, imageList);
							}							
						}						
					}
					
					// 2. 동영상 파일 복사
					List<KazeFileInfoVO> movieList = yearInfoVO.getMovieList();
					copyFileByList(targetFolderPath + "/" + "20. 동영상", movieList);
					
					List<KazeFileInfoVO> etcList = yearInfoVO.getEtcList();
					copyFileByList(targetFolderPath + "/" + "30. 기타파일", etcList);
					
					List<KazeFileInfoVO> sameList = yearInfoVO.getSameList();
					copyFileByList(targetFolderPath + "/" + "40. 중복파일", sameList);
					
				}	
			}
		}
		
		
		System.out.println("File Write End!! >> " + newWriteFilecnt);
	}

	private KazeFileInfoCompare com = new KazeFileInfoCompare();
	private int newWriteFilecnt = 0;
	
	private void copyFileByList(String basePath, List<KazeFileInfoVO> list) {
		
		if (list != null && list.size() > 0) {
			
			Collections.sort(list, com);
			
			File folder = new File(basePath);
			folder.mkdirs();		
			
			int fileCnt = 1;
			
			for (KazeFileInfoVO invo : list) {
				
				try {				
//					FileOutputStream fos = new FileOutputStream(new File(basePath + "/" + invo.getLastModifiedDate() + "_" + fileCnt + "." + invo.getFileExt()));									
//					Files.copy(invo.getFilePath(), fos);
					
					Path targetPath = Paths.get(basePath + "/" + invo.getLastModifiedDate() + "_" + fileCnt + "." + invo.getFileExt());
					
					Files.move(invo.getFilePath(), targetPath, StandardCopyOption.ATOMIC_MOVE);					
														
					fileCnt++;					
					newWriteFilecnt++;
					
				} catch (FileSystemException e1) {
					
					
					try {
						
						FileOutputStream fos = new FileOutputStream(new File(basePath + "/" + invo.getLastModifiedDate() + "_" + fileCnt + "." + invo.getFileExt()));
						Files.copy(invo.getFilePath(), fos);
						fos.close();
						
						fileCnt++;					
						newWriteFilecnt++;
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}									
					
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
		}
	}


	
	public static void main(String[] args) {
		
		ManagementPicture mp = new ManagementPicture();
		mp.readFileInfo("D:\\image_target_out_1");
//		mp.readFileInfo("D:\\test\\111");
		
		try {
			mp.writeNewFile("D:\\image_target_out_2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
