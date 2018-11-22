package com.kazegamja.www.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class KazeCRCChecker {
	
	public static long getCRC32Value(String filename) {

		Checksum checksum = new CRC32();

		try {
			
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
			byte buffer[] = new byte[32768];
//			byte buffer[] = new byte[102400];
			int length = 0;

			while ((length = in.read(buffer)) >= 0) {
				checksum.update(buffer, 0, length);
			}

			in.close();

		} catch (IOException e) {
			System.err.println(e);
			System.exit(2);
		}

		return checksum.getValue();
	}

}
