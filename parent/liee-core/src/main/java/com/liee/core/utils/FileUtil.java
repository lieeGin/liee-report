package com.liee.core.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.ResampleOp;

public class FileUtil {
	private static Logger log = Logger.getLogger(FileUtil.class.getName());

	public static String readTextFile(String fileName) {
		return readTextFile(fileName, null);
	}

	public static String readTextFile(String fileName, String charsetName) {
		if (fileName == null || fileName.length() == 0) {
			log.severe("文件名无效!");
			return "";
		}
		File file = new File(fileName);
		if (!file.isFile() || !file.canRead()) {
			log.severe("文件不存在!");
			return "";
		}
		if (file.length() > 1024 * 1024) {
			log.severe("文件内容长度超过1M!");
			return "";
		}

		InputStreamReader isr = null;
		StringBuffer sb = new StringBuffer();
		try {
			if (charsetName == null || charsetName.length() == 0)
				isr = new InputStreamReader(new FileInputStream(fileName));
			else
				isr = new InputStreamReader(new FileInputStream(fileName), charsetName);
			char buff[] = new char[(int) file.length()];
			int len = isr.read(buff);
			while (len > 0) {
				sb.append(buff, 0, len);
				len = isr.read(buff);
			}
			isr.close();
			isr = null;
		} catch (Exception e) {
			sb.setLength(0);
			log.severe(e.getMessage());
			if (isr != null)
				try {
					isr.close();
				} catch (IOException ex) {
				}
		}

		return sb.toString();
	}

	public static boolean writeTextFile(String fileName, String content) {
		return writeTextFile(fileName, content, null);
	}

	public static boolean writeTextFile(String fileName, String content, String charsetName) {
		if (fileName == null || fileName.length() == 0)
			return false;

		FileOutputStream fos = null;
		boolean rc = false;
		try {
			File file = new File(fileName).getParentFile();
			if (!file.isDirectory())
				file.mkdirs();
			fos = new FileOutputStream(fileName);
			if (charsetName == null || charsetName.length() == 0)
				fos.write(content.getBytes());
			else
				fos.write(content.getBytes(charsetName));
			fos.close();
			fos = null;
			rc = true;
		} catch (Exception e) {
			log.severe(e.getMessage());
			if (fos != null)
				try {
					fos.close();
				} catch (IOException ex) {
				}
		}

		return rc;
	}

	public static boolean saveFile(String fileName, String path, String content) {
		File f = new File(path + "/" + fileName);
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(f);// 初始化输出流
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8")); // new
																								// BufferedWriter(fw);//
																								// 初始化输出字符流
			bw.write(content);// 写文件
			bw.flush();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void saveImg(String imgSrc, String savePath, String fileName) throws Exception {
		// new一个URL对象
		URL url = new URL(imgSrc);
		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");
		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();
		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = readInputStream(inStream);
		// new一个文件对象用来保存图片，默认保存当前工程根目录
		File imageFile = new File(savePath + "/" + fileName);
		// 创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		// 写入数据
		outStream.write(data);
		// 关闭输出流
		outStream.close();
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	public static boolean copyFile(String sFile, String dFile) {
		File file1 = new File(sFile);
		File file2 = new File(dFile);
		if (!file1.isFile())
			return false;
		try {
			if (!file2.getParentFile().isDirectory())
				file2.getParentFile().mkdirs();
			int nReads;
			byte buff[] = new byte[4096];
			FileInputStream in = new FileInputStream(file1);
			FileOutputStream out = new FileOutputStream(file2);
			while ((nReads = in.read(buff, 0, buff.length)) != -1)
				out.write(buff, 0, nReads);
			out.close();
			in.close();
			return true;
		} catch (Exception e) {
			log.severe(e.getMessage());
			return false;
		}
	}

	public static String getFormatName(File file) {
		String format = "";
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(file);
			Iterator<?> iter = ImageIO.getImageReaders(iis);
			if (iter.hasNext()) {
				ImageReader reader = (ImageReader) iter.next();
				format = reader.getFormatName();
			}
			iis.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return format;
	}

	public static String getFormatName(byte[] content) {
		String format = "";
		try {
			ImageInputStream iis = new MemoryCacheImageInputStream(new ByteArrayInputStream(content));
			Iterator<?> iter = ImageIO.getImageReaders(iis);
			if (iter.hasNext()) {
				ImageReader reader = (ImageReader) iter.next();
				format = reader.getFormatName();
			}
			iis.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return format;
	}

	public static int[] getImageSize(File file) {
		try {
			BufferedImage img = ImageIO.read(file);
			return new int[] { img.getWidth(), img.getHeight() };
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static int[] getImageSize(byte[] content) {
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(content));
			return new int[] { img.getWidth(), img.getHeight() };
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public static boolean isNormalPicture(String filePath, String fileName) {
		File file = new File(filePath, fileName);
		if (file.isFile() && file.canRead()) {
			int pos = fileName.lastIndexOf('.');
			if (pos > 0) {
				String baseName = fileName.substring(0, pos);
				String extName = fileName.substring(pos + 1).toLowerCase();
				if (baseName.lastIndexOf('_') != baseName.length() - 2 && (extName.equals("jpg") || extName.equals("png"))) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean deletePicture(String filePath, String fileName) {
		if (filePath == null || filePath.length() == 0 || fileName == null || fileName.length() == 0) {
			return false;
		}
		File file = new File(filePath, fileName);
		if (isNormalPicture(filePath, fileName)) {
			int pos = fileName.lastIndexOf('.');
			String extName = fileName.substring(pos + 1);
			String baseName = fileName.substring(0, pos);
			new File(filePath, baseName + "_s." + extName).delete();
			new File(filePath, baseName + "_l." + extName).delete();
		}
		boolean rc = file.delete();

		return rc;
	}

	public static boolean imageCompress(File srcFile, String filePath, String baseName, String extName, int width, int height) {
		try {
			if (filePath == null || filePath.length() == 0 || baseName == null || baseName.length() == 0) {
				return false;
			}
			if (baseName.length() >= 4 && baseName.charAt(baseName.length() - 2) == '_' && baseName.charAt(baseName.length() - 4) == '_') {
				return false;
			}
			File toPath = new File(filePath);
			if (!toPath.isDirectory()) {
				toPath.mkdirs();
			}
			File toFile = new File(filePath, baseName + "." + extName);
			BufferedImage img = ImageIO.read(srcFile);
			if (img.getWidth() < img.getHeight() && width > height) {
				int tmp = height;
				height = width;
				width = tmp;
			}
			float wRate = img.getWidth() / (width * 1.0f);
			float hRate = img.getHeight() / (height * 1.0f);
			if (wRate > 1.0 || hRate > 1.0) {
				float rate = Math.max(wRate, hRate);
				int newWidth = (int) (img.getWidth() / rate);
				int newHeight = (int) (img.getHeight() / rate);
				ResampleOp resampleOp = new ResampleOp(newWidth, newHeight);
				BufferedImage smallImg = resampleOp.filter(img, null);
				img = smallImg;
			}
			if (ImageIO.write(img, extName, toFile)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static byte[] imageCompress(byte[] content, String extName, int width, int height) {
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(content));
			if (img.getWidth() < img.getHeight() && width > height) {
				int tmp = height;
				height = width;
				width = tmp;
			}
			float wRate = img.getWidth() / (width * 1.0f);
			float hRate = img.getHeight() / (height * 1.0f);
			if (wRate > 1.0 || hRate > 1.0) {
				float rate = Math.max(wRate, hRate);
				int newWidth = (int) (img.getWidth() / rate);
				int newHeight = (int) (img.getHeight() / rate);
				ResampleOp resampleOp = new ResampleOp(newWidth, newHeight);
				BufferedImage smallImg = resampleOp.filter(img, null);
				img = smallImg;
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			if (ImageIO.write(img, extName, bos)) {
				return bos.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean cropImage(File srcFile, File destFile, int displayWidth, int displayHeight, int destX, int destY, int destWidth, int destHeight) {
		try {
			if (!destFile.isDirectory()) {
				destFile.mkdirs();
			}
			BufferedImage img = ImageIO.read(srcFile);
			float wRate = img.getWidth() * 1.0f / displayWidth;
			float hRate = img.getHeight() * 1.0f / displayHeight;
			int x = Math.round(destX * wRate);
			int y = Math.round(destY * hRate);
			int w = Math.round(destWidth * wRate);
			int h = Math.round(destHeight * hRate);
			if (x + w > img.getWidth()) {
				w = img.getWidth() - w;
			}
			if (y + h > img.getHeight()) {
				h = img.getHeight() - h;
			}
			if (w < 0 || h < 0) {
				System.out.println("裁剪尺寸超出原图片大小");
				return false;
			}
			String extName = FileUtil.getFormatName(srcFile).equalsIgnoreCase("JPEG") ? "jpg" : "png";
			return ImageIO.write(img.getSubimage(x, y, w, h), extName, destFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取指定目录下的所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getFilesFromDirectory(String path) {
		File file = new File(path);
		File[] tempList = file.listFiles();
		List<File> files = new ArrayList<File>();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i]);
			}
		}
		return files;
	}

	public static String inputStream2String(InputStream is) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	public static void inputStream2File(InputStream ins, File file) throws Exception {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
	}

}
