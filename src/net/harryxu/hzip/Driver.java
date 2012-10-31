package net.harryxu.hzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.harryxu.hzip.util.*;

public class Driver {
	public static String Compress(String src, String dst) {
		return Compress(new File(src), new File(dst));
	}

	public static String Compress(File src, File dst) {
		throw new RuntimeException("Unfinished method");
	}

	public static String Compress(File[] src, String dst, String name) {
		File pack = null;
		try {
			pack = File.createTempFile(name, ".tar");
			TarUtil.archive(src, pack);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ret = dst + File.separatorChar + name + ".tar.hz";
		Encode en = new Encode();
		try {
			BitReader r = new BitReader(new FileInputStream(pack));
			BitWriter w = new BitWriter(new FileOutputStream(new File(ret)));
			en.encode(r, w);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static boolean Decompress(File src, File dst) {
		File unpack = null;
		Decode de = new Decode();
		try {
			unpack = File.createTempFile("temp", ".tar");
			BitReader r = new BitReader(new FileInputStream(src));
			BitWriter w = new BitWriter(new FileOutputStream(unpack));
			de.decode(r, w);
			TarUtil.dearchive(unpack, dst);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static int byte2int(byte[] res) {
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);
		targets[1] = (byte) ((res >> 8) & 0xff);
		targets[2] = (byte) ((res >> 16) & 0xff);
		targets[3] = (byte) (res >>> 24);
		return targets;
	}

}
