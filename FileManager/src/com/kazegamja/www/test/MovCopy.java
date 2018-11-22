package com.kazegamja.www.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.kazegamja.www.util.KazeCRCChecker;
import com.kazegamja.www.vo.KazeFileInfoVO;
import com.kazegamja.www.vo.KazePicrureManagerVO;

public class MovCopy {
	
	public String readFileExtention() {
		
		ArrayList<String> fileExt = new ArrayList<>();
		
		try {
			List<Path> filesInFolder = Files.walk(Paths.get("D:\\00.back up\\00. 사진\\최상위 폴더"))
			        .filter(Files::isRegularFile)			        
			        .collect(Collectors.toList());
			
			for (Path p : filesInFolder) {
//				System.out.println(p.toFile().getAbsolutePath());
//				System.out.println(Files.getLastModifiedTime(p).toMillis());
//				System.out.println(Files.getLastModifiedTime(p).toString().substring(0, 10));
//				System.out.println(Files.getLastModifiedTime(p));
				
				int pos = p.toFile().getAbsolutePath().lastIndexOf( "." );
				String ext = p.toFile().getAbsolutePath().substring( pos + 1 );

				if (!fileExt.contains(ext.toUpperCase())) {
					fileExt.add(ext.toUpperCase());
				}
			}
			
			for (String s : fileExt) {
				System.out.println(s);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
		
	}
	
	public void readFileInfo(String targetFileFullPath) {
		
		
		
		try {
			
			List<Path> filesInFolder = Files.walk(Paths.get(targetFileFullPath))
			        .filter(Files::isRegularFile)			        
			        .collect(Collectors.toList());
			
			int fileCnt = 0;
			
			for (Path p : filesInFolder) {				
				
				Path filePath = p;				
				long crc32 = 0;
				long size = Files.size(p);
				long tagTimeMil = 0;

				FileTime lastModifiedTime = Files.getLastModifiedTime(p); 	
				long lastModifiedTimeMil = lastModifiedTime.toMillis();
				String lastModifiedDate = lastModifiedTime.toString().substring(0, 10);

				String lastModifiedYear = lastModifiedTime.toString().substring(0, 4);
				String lastModifiedMonth = lastModifiedTime.toString().substring(5, 7);
				
				int pos = p.toFile().getAbsolutePath().lastIndexOf( "." );
				String fileExt = p.toFile().getAbsolutePath().substring( pos + 1 ).toUpperCase();
				
//				Metadata metadata = ImageMetadataReader.readMetadata(new FileInputStream(p.toFile()));
//				ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
//				Date date = directory.getDateOriginal(TimeZone.getDefault());
//				
//				if (date != null) {							
//					tagTimeMil = date.getTime();				
//				}
				
				System.out.println(filePath.toString());			
				System.out.println(lastModifiedTimeMil);			
				System.out.println(KazeCRCChecker.getCRC32Value(filePath.toString()));
				System.out.println(size);
				System.out.println("");
				
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void readExif(String file) throws Exception {
		
		Metadata metadata = ImageMetadataReader.readMetadata(new FileInputStream(new File(file)));
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {		 
		    	System.out.println("[" + directory.getName() + "] - " +  tag.getTagName() + " = " + tag.getDescription());
		    }
		    if (directory.hasErrors()) {
		        for (String error : directory.getErrors()) {
		            System.err.println("ERROR : " + error);
		        }
		    }
		}
		
//		Metadata metadata = ImageMetadataReader.readMetadata(new FileInputStream(new File(file)));
//		ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
//		Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
//		System.out.println(date);
		
		
	}
	
	public static void main(String[] args) {
		MovCopy mc = new MovCopy();
		
		mc.readFileInfo("D:/test/03/");
		
//		try {
//			mc.readExif("D:\\target\\2010년도 12월 216.JPG");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
