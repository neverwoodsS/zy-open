package com.zy.demo.lib.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：图片处理工具 创建人：WX 创建时间：2014-1-15 下午6:48:21 修改人：WX 修改时间：2014-1-15 下午6:48:21
 * 修改备注：
 * 
 * @version
 */
public final class ImageUtil {
	/**
	 * 保存图片
	 * 
	 * @param image
	 * @param path
	 *            ：图片路径
	 * @return
	 * @author： WX
	 */
	public static Map<String, Object> saveImage(Bitmap image, String path) {
		File photoFile = new File(path);

		// 判断文件夹是否存在
		if (!photoFile.getParentFile().exists()) {
			photoFile.getParentFile().mkdirs();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(photoFile);
			if (image != null) {
				if (compressImage(image).compress(Bitmap.CompressFormat.JPEG,
						90, fileOutputStream)) {
					fileOutputStream.flush();
				}
			}
			params.put(FileAttrs.filePath.toString(), path);
			params.put(FileAttrs.fileName.toString(), photoFile.getName());
			params.put(FileAttrs.fileType.toString(),
					Bitmap.CompressFormat.JPEG.toString());
			params.put(FileAttrs.fileLength.toString(), photoFile.length());
		} catch (FileNotFoundException e) {
			photoFile.delete();
			e.printStackTrace();
		} catch (IOException e) {
			photoFile.delete();
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return params;
	}

	public enum FileAttrs {
		filePath, fileName, fileType, fileLength, fileDuration
	}

	/**
	 * 保存图片，并按照指定参数压缩
	 * 
	 * @param tempPath
	 *            : 文件读取路径
	 * @param savePath
	 *            : 文件保存路径
	 * @param size
	 *            : 指定压缩的尺寸
	 * @param type
	 *            : 压缩指定类型：按照宽度等比压缩，或者按照长度等比压缩
	 * @return
	 * @author： WX
	 */
	public static Map<String, Object> saveImage(String tempPath,
												String savePath, float size, ScaleType type) {
		Bitmap bitmap = zoomBitmap(tempPath, size, type);
		return saveImage(bitmap, savePath);
	}

	/**
	 * 缩小Bitmap
	 * 
	 * @param tempPath
	 * @param size
	 * @param type
	 * @return
	 * @author： WX
	 */
	public static Bitmap zoomBitmap(String tempPath, float size, ScaleType type) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		BitmapFactory.decodeFile(tempPath, options);
		options.inJustDecodeBounds = false; // 设为 false

		int width = options.outWidth;
		int height = options.outHeight;

		float scale = 1;
		switch (type) {
		case Width:
			scale = width / size;
			break;
		case Height:
			scale = height / size;
			break;
		}
		if (scale <= 0) {
			scale = 1;
		}
		options.inSampleSize = (int) scale;
		return BitmapFactory.decodeFile(tempPath, options);
	}

	/**
	 * 图片压缩(质量):
	 * 
	 * @param image
	 * @return
	 * @author： WX
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，0压缩到最小,把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > 100) {
			// 重置baos即清空baos
			baos.reset();
			// 每次都减少10
			options -= 10;
			// 这里压缩options%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		try {
			baos.close();
			isBm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 获取视频第一帧画面，并缩放到指定大小
	 * 
	 * @param filePath
	 * @param width
	 * @param height
	 * @return
	 * @author： WX
	 */
	public static Bitmap getVideoThumbnail(String filePath, int width,
										   int height) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(filePath);
		Bitmap bitmap = retriever.getFrameAtTime(1);
		if (width < 1) {
			width = bitmap.getWidth();
		}
		if (height < 1) {
			height = bitmap.getHeight();
		}
		return zoomImage(retriever.getFrameAtTime(1), width, height);
	}

	/**
	 * 获取视频第一帧画面，并缩放到指定大小
	 * 
	 * @param filePath
	 * @param size
	 *            :长或者宽的尺寸
	 * @param type
	 *            :长类型或者宽类型
	 * @return
	 * @author： WX
	 */
	public static Bitmap getVideoThumbnail(String filePath, float size,
										   ScaleType type) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		Bitmap bitmap = null;
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime(1);
		} catch (Exception e) {
		}
		return zoomImage(bitmap, size, type);
	}

	/**
	 * 指定图片尺寸压缩图片
	 * 
	 * @param path
	 * @param zw
	 * @param zh
	 * @return
	 * @author： WX
	 */
	public static Bitmap compressImage(String path, float zw, float zh) {
		return compressImage(zoomImage(path, zw, zh));
	}

	/**
	 * 缩放图片
	 * 
	 * @param path
	 * @param zw
	 * @param zh
	 * @return
	 * @author： WX
	 */
	public static Bitmap zoomImage(String path, float zw, float zh) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		return zoomImage(bitmap, zw, zh);
	}

	/**
	 * 缩放图片
	 * 
	 * @param bit
	 * @param zw
	 * @param zh
	 * @return
	 * @author： WX
	 */
	public static Bitmap zoomImage(Bitmap bit, float zw, float zh) {
		if (bit == null) {
			return null;
		}
		int width = bit.getWidth();
		int height = bit.getHeight();
		float scaleWidth = 1;
		float scaleHeight = 1;
		if (zw > 1) {
			scaleWidth = zw / width;
		}
		if (zh > 1) {
			scaleHeight = zh / height;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bit, 0, 0, width, height, matrix, true);
	}

	/**
	 * 等比压缩图片
	 * 
	 * @param size
	 *            ：压缩比例
	 * @return
	 * @author： WX
	 */
	public static Bitmap zoomImage(String path, float size, ScaleType type) {
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		if (bitmap == null) {
			return null;
		}
		return zoomImage(bitmap, size, type);
	}

	/**
	 * 等比压缩图片
	 * 
	 * @param bitmap
	 * @param size
	 * @param type
	 * @return
	 * @author： WX
	 */
	public static Bitmap zoomImage(Bitmap bitmap, float size, ScaleType type) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scale = 1;
		switch (type) {
		case Width:
			scale = size / width;
			break;
		case Height:
			scale = size / height;
			break;
		}
		return zoomImage(bitmap, scale);
	}

	/**
	 * 等比压缩图片
	 * 
	 * @param bitmap
	 * @param scale
	 *            ：压缩比例
	 * @return
	 * @author： WX
	 */
	public static Bitmap zoomImage(Bitmap bitmap, float scale) {
		if (bitmap == null) {
			return null;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	/**
	 * 将图片 旋转一定角度
	 * 
	 * @param bitmap
	 * @param rotate
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
		if (bitmap == null) {
			return null;
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix mtx = new Matrix();
		mtx.setRotate(rotate);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}

	/**
	 * 获取图片旋转的角度
	 * 
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 获取图片缩略图
	 * 
	 * @param imagePath
	 * @param width
	 * @param height
	 * @return
	 * @author：
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width,
										   int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 根据路径获取Bitmap
	 * 
	 * @param path
	 * @param inSampleSize
	 * @return
	 * @author： WX
	 */
	public static Bitmap getBitmapByPathAndInSampleSize(String path,
														int inSampleSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 图片宽高都为原来的二分之一，即图片为原来的四分之一
		options.inSampleSize = inSampleSize;
		return BitmapFactory.decodeFile(path, options);

	}

	/**
	 * 根据路径获取Bitmap
	 * 
	 * @param inSampleSize
	 * @return
	 * @author： WX
	 */
	public static Bitmap getBitmapByPathAndInSampleSize(InputStream input,
														int inSampleSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 图片宽高都为原来的二分之一，即图片为原来的四分之一
		options.inSampleSize = inSampleSize;
		return BitmapFactory.decodeStream(input, null, options);

	}

//	/**
//	 * 删除文件（非文件夹）
//	 * 
//	 * @param path
//	 * @author： WX
//	 */
//	public static void deleteFile(String path) {
//		if (MethodUtils.stringIsNotBlank(path)) {
//			File file = new File(path);
//			if (file.exists() && file.isFile()) {
//				file.delete();
//			}
//		}
//	}

	/**
	 * 截屏（整个窗口，包括未显示出来的部分）
	 * 
	 * @param context
	 * @return
	 * @author： WX
	 */
	public static Bitmap captureScreen(Activity context) {
		View cv = context.getWindow().getDecorView();
		Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		cv.draw(canvas);
		return bmp;
	}

	/**
	 * 截取webView快照(webView加载的整个内容的大小)
	 * 
	 * @param webView
	 * @return
	 * @author： WX
	 */
	public static Bitmap captureWebView(WebView webView) {
		Picture snapShot = webView.capturePicture();
		Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),
				snapShot.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		snapShot.draw(canvas);
		return bmp;
	}

	/**
	 * 类描述：等比例缩放图片方式（Width:按照宽度等比缩放，Height:按照高度等比缩放） 创建人：WX
	 * 创建时间：2014-7-31上午9:59:38 修改人：WX 修改时间：2014-7-31 上午9:59:38 修改备注：
	 * 
	 * @version
	 */
	public enum ScaleType {
		Width, Height
	}

	// drawable转Bitmap
	public static Bitmap drawableToBitamp(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}

	public static Bitmap revitionImageSize(String path) {
		Bitmap bitmap = null;
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(new File(path)));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();
			int i = 0;

			while (true) {
				if ((options.outWidth >> i <= 1000)
						&& (options.outHeight >> i <= 1000)) {
					in = new BufferedInputStream(new FileInputStream(new File(
							path)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 获取缩放了的图片
	 * 
	 * @创建时间：2015-7-2 下午5:53:15
	 * @作者： 龙辉
	 * @param srcPath
	 * @return
	 * @retrun Bitmap
	 */
	public static Bitmap compressImageFromFile(String srcPath) {
		/**
		 * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
		 */
		int degree = readPictureDegree(srcPath);
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 240f;//
		float ww = 400f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率

		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		/**
		 * 把图片旋转为正的方向
		 */
		bitmap = rotaingImageView(degree, bitmap);
		return bitmap;
	}

	/**
	 * 旋转图片
	 * 
	 * @创建时间：2015-7-23 下午12:34:03
	 * @作者： 龙辉
	 * @param angle
	 * @param bitmap
	 * @return
	 * @retrun Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * bitmap转为base64
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
}
