package net.harryxu.hzip.util;

import java.io.*;

import org.apache.commons.compress.archivers.tar.*;

/**
 * Get some operation on tar files
 * 
 * @author Harry Xu
 * 
 */
public class TarUtil {

	private static final String BASE_DIR = "";

	// ����"/"������ΪĿ¼��ʶ�жϷ�
	private static final String PATH = "/";
	private static final int BUFFER = 1024;

	private static final String EXT = ".tar";

	/**
	 * �鵵
	 * 
	 * @param srcPath
	 * @param destPath
	 * @throws Exception
	 */
	public static void archive(String srcPath, String destPath)
			throws Exception {

		File srcFile = new File(srcPath);

		archive(srcFile, destPath);

	}

	/**
	 * �鵵
	 * 
	 * @param srcFile
	 *            Դ·��
	 * @param destPath
	 *            Ŀ��·��
	 * @throws Exception
	 */
	public static void archive(File srcFile, File destFile) throws Exception {

		TarArchiveOutputStream taos = new TarArchiveOutputStream(
				new FileOutputStream(destFile));

		archive(srcFile, taos, BASE_DIR);

		taos.flush();
		taos.close();
	}

	/**
	 * �鵵
	 * 
	 * @param srcFile
	 * @throws Exception
	 */
	public static void archive(File srcFile) throws Exception {
		String name = srcFile.getName();
		String basePath = srcFile.getAbsolutePath();
		basePath = basePath.substring(0, basePath.length() - name.length());
		String destPath = basePath
				+ name.substring(0, name.lastIndexOf(".") == -1 ? name.length()
						: name.lastIndexOf(".")) + EXT;
		archive(srcFile, destPath);
	}

	/**
	 * �鵵�ļ�
	 * 
	 * @param srcFile
	 * @param destPath
	 * @throws Exception
	 */
	public static void archive(File srcFile, String destPath) throws Exception {
		archive(srcFile, new File(destPath));
	}

	/**
	 * �鵵
	 * 
	 * @param srcPath
	 * @throws Exception
	 */
	public static void archive(String srcPath) throws Exception {
		File srcFile = new File(srcPath);

		archive(srcFile);
	}

	/**
	 * �鵵
	 * 
	 * @param srcFile
	 *            Դ·��
	 * @param taos
	 *            TarArchiveOutputStream
	 * @param basePath
	 *            �鵵�������·��
	 * @throws Exception
	 */
	private static void archive(File srcFile, TarArchiveOutputStream taos,
			String basePath) throws Exception {
		if (srcFile.isDirectory()) {
			archiveDir(srcFile, taos, basePath);
		} else {
			archiveFile(srcFile, taos, basePath);
		}
	}
	/**
	 * �鵵
	 * 
	 * @param srcPath
	 * @throws Exception
	 */
	public static void archive(String[] srcPath) throws Exception {
		File srcFile[] = new File[srcPath.length];
		
		for(int i =0;i<srcPath.length;i++)
			srcFile[i] = new File(srcPath[i]);

		archive(srcFile);
	}
	/**
	 * �鵵
	 * 
	 * @param srcFile
	 *            Դ�ļ�[����]
	 * @throws Exception
	 */
	public static void archive(File[] srcFile) throws Exception {
		String name = srcFile[0].getName();
		String basePath = srcFile[0].getAbsolutePath();
		basePath = basePath.substring(0, basePath.length() - name.length());
		name = basePath.substring(basePath.substring(0, basePath.length()-1).lastIndexOf("\\"),basePath.length()-1);//Maybe Only For Windows
		String destPath = basePath + name + EXT;
		archive(srcFile, destPath);
	}

	/**
	 * �鵵
	 * 
	 * @param srcFile
	 *            Դ�ļ�[����]
	 * @param destPath
	 *            Ŀ��·��
	 * @throws Exception
	 */
	public static void archive(File[] srcFile, String destPath)
			throws Exception {
		archive(srcFile, new File(destPath));
	}

	/**
	 * �鵵
	 * 
	 * @param srcFile
	 *            Դ·��
	 * @param destPath
	 *            Ŀ��·��
	 * @throws Exception
	 */
	public static void archive(File[] srcFile, File destFile) throws Exception {

		TarArchiveOutputStream taos = new TarArchiveOutputStream(
				new FileOutputStream(destFile));

		for (File x : srcFile)
			archive(x, taos, BASE_DIR);

		taos.flush();
		taos.close();
	}

	/**
	 * Ŀ¼�鵵
	 * 
	 * @param dir
	 * @param taos
	 *            TarArchiveOutputStream
	 * @param basePath
	 * @throws Exception
	 */
	private static void archiveDir(File dir, TarArchiveOutputStream taos,
			String basePath) throws Exception {

		File[] files = dir.listFiles();

		if (files.length < 1) {
			TarArchiveEntry entry = new TarArchiveEntry(basePath
					+ dir.getName() + PATH);

			taos.putArchiveEntry(entry);
			taos.closeArchiveEntry();
		}

		for (File file : files) {

			// �ݹ�鵵
			archive(file, taos, basePath + dir.getName() + PATH);

		}
	}

	/**
	 * ���ݹ鵵
	 * 
	 * @param data
	 *            ���鵵����
	 * @param path
	 *            �鵵���ݵĵ�ǰ·��
	 * @param name
	 *            �鵵�ļ���
	 * @param taos
	 *            TarArchiveOutputStream
	 * @throws Exception
	 */
	private static void archiveFile(File file, TarArchiveOutputStream taos,
			String dir) throws Exception {

		/**
		 * �鵵���ļ�������
		 * 
		 */
		TarArchiveEntry entry = new TarArchiveEntry(dir + file.getName());

		entry.setSize(file.length());

		taos.putArchiveEntry(entry);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			taos.write(data, 0, count);
		}

		bis.close();

		taos.closeArchiveEntry();
	}

	/**
	 * ��鵵
	 * 
	 * @param srcFile
	 * @throws Exception
	 */
	public static void dearchive(File srcFile) throws Exception {
		String basePath = srcFile.getParent();
		dearchive(srcFile, basePath);
	}

	/**
	 * ��鵵
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws Exception
	 */
	public static void dearchive(File srcFile, File destFile) throws Exception {

		TarArchiveInputStream tais = new TarArchiveInputStream(
				new FileInputStream(srcFile));
		dearchive(destFile, tais);

		tais.close();

	}

	/**
	 * ��鵵
	 * 
	 * @param srcFile
	 * @param destPath
	 * @throws Exception
	 */
	public static void dearchive(File srcFile, String destPath)
			throws Exception {
		dearchive(srcFile, new File(destPath));

	}

	/**
	 * �ļ� ��鵵
	 * 
	 * @param destFile
	 *            Ŀ���ļ�
	 * @param tais
	 *            ZipInputStream
	 * @throws Exception
	 */
	private static void dearchive(File destFile, TarArchiveInputStream tais)
			throws Exception {
		fileProber(destFile);
		TarArchiveEntry entry = null;
		while ((entry = tais.getNextTarEntry()) != null) {

			// �ļ�
			String dir = destFile.getPath() + File.separator + entry.getName();

			File dirFile = new File(dir);

			// �ļ����
			fileProber(dirFile);

			if (entry.isDirectory()) {
				dirFile.mkdirs();
			} else {
				dearchiveFile(dirFile, tais);
			}

		}
	}

	/**
	 * �ļ� ��鵵
	 * 
	 * @param srcPath
	 *            Դ�ļ�·��
	 * 
	 * @throws Exception
	 */
	public static void dearchive(String srcPath) throws Exception {
		File srcFile = new File(srcPath);

		dearchive(srcFile);
	}

	/**
	 * �ļ� ��鵵
	 * 
	 * @param srcPath
	 *            Դ�ļ�·��
	 * @param destPath
	 *            Ŀ���ļ�·��
	 * @throws Exception
	 */
	public static void dearchive(String srcPath, String destPath)
			throws Exception {

		File srcFile = new File(srcPath);
		dearchive(srcFile, destPath);
	}

	/**
	 * �ļ���鵵
	 * 
	 * @param destFile
	 *            Ŀ���ļ�
	 * @param tais
	 *            TarArchiveInputStream
	 * @throws Exception
	 */
	private static void dearchiveFile(File destFile, TarArchiveInputStream tais)
			throws Exception {

		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(destFile));

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = tais.read(data, 0, BUFFER)) != -1) {
			bos.write(data, 0, count);
		}

		bos.close();
	}

	/**
	 * �ļ�̽��
	 * 
	 * <pre>
	 * ����Ŀ¼������ʱ������Ŀ¼��
	 * </pre>
	 * 
	 * @param dirFile
	 */
	private static void fileProber(File dirFile) {

		File parentFile = dirFile.getParentFile();
		if(parentFile == null)
			return;
		if (!parentFile.exists()) {

			fileProber(parentFile);

			parentFile.mkdir();
		}

	}

	public static void main(String[] args) {
		try {
			archive(new String[]{"src","bin",".project"});
			dearchive("Hzip.tar","D:\\aaaTest");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
