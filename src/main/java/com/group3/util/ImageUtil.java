package com.group3.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {

	private static File waterFile = new File(ImageUtil.class.getClassLoader().getResource("config/logo.png").getFile());

	//商家图片、商品图片加水印......进行切割大小，但图片质量不变
	public static String generateThumbnail(MultipartFile thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();//文件名称
		String extension = getFileExtension(thumbnail);//格式扩张名
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;//相对地址
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);//全地址

		try {
			Thumbnails.of(thumbnail.getInputStream())
					.size(700, 700)
					.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(waterFile),0.6f)
					.outputQuality(1f)
					.toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	//用户头像图片不加水印......进行切割大小，但图片质量改变
	public static String generateNormalImg(MultipartFile thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream())
					.size(300, 300)
					.outputQuality(0.7f)
					.toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
		int count = 0;
		List<String> relativeAddrList = new ArrayList<String>();
		if (imgs != null && imgs.size() > 0) {
			makeDirPath(targetAddr);
			for (CommonsMultipartFile img : imgs) {
				String realFileName = FileUtil.getRandomFileName();
				String extension = getFileExtension(img);
				String relativeAddr = targetAddr + realFileName + count + extension;
				File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
				count++;
				try {
					Thumbnails.of(img.getInputStream())
							.size(600, 300)
							.outputQuality(0.5f)
							.toFile(dest);
				} catch (IOException e) {
					throw new RuntimeException("创建图片失败：" + e.toString());
				}
				relativeAddrList.add(relativeAddr);
			}
		}
		return relativeAddrList;
	}

	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	private static String getFileExtension(MultipartFile cFile) {
		String originalFileName = cFile.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}


}
