package com.group3.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {
	private static String seperator = System.getProperty("file.separator");
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss"); // 时间格式化的格式
	private static final Random r = new Random();

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/download/o2o/images";
		} else {
			basePath = "/root/download/o2o/images";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}

	public static String getHeadLineImagePath() {
		String headLineImagePath = "/headtitle/";
		headLineImagePath = headLineImagePath.replace("/", seperator);
		return headLineImagePath;
	}

	public static String getShopCategoryImagePath() {
		String shopCategoryImagePath = "/shopcategory/";
		shopCategoryImagePath = shopCategoryImagePath.replace("/", seperator);
		return shopCategoryImagePath;
	}

	public static String getGoodsInfoImagePath(long goodsId) {
		StringBuilder goodsImagePathBuilder = new StringBuilder();
		goodsImagePathBuilder.append("/goods/");
		goodsImagePathBuilder.append(goodsId);
		goodsImagePathBuilder.append("/");
		String userImagePath = goodsImagePathBuilder.toString().replace("/", seperator);
		return userImagePath;
	}
	
	public static String getUserInfoImagePath(String userId) {
		StringBuilder userImagePathBuilder = new StringBuilder();
		userImagePathBuilder.append("/userinfo/");
		userImagePathBuilder.append(userId);
		userImagePathBuilder.append("/");
		String userImagePath = userImagePathBuilder.toString().replace("/", seperator);
		return userImagePath;
	}

	public static String getShopImagePath(long shopId) {
		StringBuilder shopImagePathBuilder = new StringBuilder();
		shopImagePathBuilder.append("/shop/");
		shopImagePathBuilder.append(shopId);
		shopImagePathBuilder.append("/");
		String shopImagePath = shopImagePathBuilder.toString().replace("/", seperator);
		return shopImagePath;
	}

	public static String getRandomFileName() {
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		String nowTimeStr = sDateFormat.format(new Date()); // 当前时间
		return nowTimeStr + rannum;
	}

	public static void deleteFile(String storePath) {
		File file = new File(getImgBasePath() + storePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			file.delete();
		}
	}
}
