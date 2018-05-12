package com.em.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.ResampleOp;
import com.mortennobel.imagescaling.AdvancedResizeOp;

public class ImageUtil {

	//缩放图片
	public static void createThumb(File file, String formatName, String path, int width, int height) {
		try {
			BufferedImage src = ImageIO.read(file);
			//src.setRGB(0, 0, BufferedImage.TYPE_INT_ARGB);
			ResampleOp resampleOp = new ResampleOp(width, height);
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
			//BufferedImage rescaled = resampleOp.filter(src, null);
			double[] size = zoomSize(src, width, height);
			BufferedImage rescaled = resampleOp.doFilter(src, null, (int)size[0], (int)size[1]);
			ImageIO.write(rescaled, formatName, new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static double[] zoomSize(BufferedImage image, int width, int height) {
		double[] zoomSize = new double[2];
		double srcWidth = image.getWidth();
		double srcHeight = image.getHeight();
		if (srcWidth > width) {
			zoomSize[0] = width;
			zoomSize[1] = srcHeight / (srcWidth / width); 
		} else if (srcHeight > height) {
			zoomSize[0] = srcWidth / (srcHeight / height);
			zoomSize[1] = height;
		} else {
			zoomSize[0] = srcWidth;
			zoomSize[1] = srcHeight;
		}
		return zoomSize;
	}
	
	//水印位置
	public enum MARK_LOCATION {
		TOPLEFT, TOPRIGHT, TOPCENTER, CENTER, BOTTOMLEFT, BOTTOMCENTER, BOTTOMRIGHT
	}
	public static final Map<String, String> MARK_LOCATIONS = new HashMap<String, String>();
	static {
		MARK_LOCATIONS.put(MARK_LOCATION.TOPLEFT.toString(), "左上角");
		MARK_LOCATIONS.put(MARK_LOCATION.TOPCENTER.toString(), "靠上居中");
		MARK_LOCATIONS.put(MARK_LOCATION.TOPRIGHT.toString(), "右上角");
		MARK_LOCATIONS.put(MARK_LOCATION.CENTER.toString(), "居中");
		MARK_LOCATIONS.put(MARK_LOCATION.BOTTOMLEFT.toString(), "左下角");
		MARK_LOCATIONS.put(MARK_LOCATION.BOTTOMCENTER.toString(), "靠下居中");
		MARK_LOCATIONS.put(MARK_LOCATION.BOTTOMRIGHT.toString(), "右下角");
	}
	private static MARK_LOCATION toLocation(String location) {
		return MARK_LOCATION.valueOf(location);
	}
	
	/**
	 * 把图片印刷到图片上
	 * @param pressImg    -- 水印文件
	 * @param targetImg   -- 目标文件
	 * @param location    -- 位置
	 */
	public final static void pressImage(String pressImg, String targetImg, String location, String formatName) {
		try {
			// 目标文件
			File file = new File(targetImg);
			Image src  = ImageIO.read(file);
			int width  = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			if (formatName.equals("JPG"))
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 水印文件
			File file_mark  = new File(pressImg);
			Image src_mark  = ImageIO.read(file_mark);
			int width_mark  = src_mark.getWidth(null);
			int height_mark = src_mark.getHeight(null);
			MARK_LOCATION mark_location = toLocation(location);
			/*根据位置生成水印*/
			switch (mark_location) {
			case TOPLEFT:
				g.drawImage(src_mark, 0, 0, width_mark, height_mark, null);
				break;
			case TOPCENTER:
				g.drawImage(src_mark, (width - width_mark) / 2, 0, width_mark, height_mark, null);
				break;
			case TOPRIGHT:
				g.drawImage(src_mark, width - width_mark, 0, width_mark, height_mark, null);
				break;
			case CENTER:
				g.drawImage(src_mark, (width - width_mark) / 2, (height - height_mark) / 2, width_mark, height_mark, null);
				break;
			case BOTTOMLEFT:
				g.drawImage(src_mark, 0, height - height_mark, width_mark, height_mark, null);
				break;
			case BOTTOMCENTER:
				g.drawImage(src_mark, (width - width_mark) / 2, height - height_mark, width_mark, height_mark, null);
				break;
			case BOTTOMRIGHT:
				g.drawImage(src_mark, width - width_mark, height - height_mark, width_mark, height_mark, null);
				break;
			default:
				break;
			}
			// 水印文件结束
			g.dispose();
			ImageIO.write(image, formatName, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印文字水印图片
	 * @param pressText -- 文字
	 * @param targetImg -- 目标图片
	 * @param font  	-- 字体
	 * @param color  	-- 字体颜色
	 * @param fontSize  -- 字体大小
	 * @param location  -- 位置
	 * @param alpha		-- 透明度(0.1-1)
	 */
	public static void pressText(String pressText, String targetImg, Font font, Color color, int fontSize, String location, float alpha, String formatName) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			if (formatName.toUpperCase().equals("JPG"))
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(font);
			/*消除java.awt.Font字体的锯齿*/
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			MARK_LOCATION mark_location = toLocation(location);
			int fontwidth = g.getFontMetrics().stringWidth(pressText);
			int fontheight = g.getFontMetrics().getHeight() / 2;
			int x = width - fontwidth;
			int y = height - fontheight;
			switch (mark_location) {
			case TOPLEFT:
				x = 0;
				y = fontheight;
				break;
			case TOPCENTER:
				x = (width - fontwidth) / 2;
				y = fontheight;
				break;
			case TOPRIGHT:
				x = width - fontwidth;
				y = fontheight;
				break;
			case CENTER:
				x = (width - fontwidth) / 2;
				y = (height - fontheight) / 2;
				break;
			case BOTTOMLEFT:
				x = 0;
				y = height - fontheight;
				break;
			case BOTTOMCENTER:
				x = (width - fontwidth) / 2;
				y = height - fontheight;
				break;
			case BOTTOMRIGHT:
				x = width - fontwidth;
				y = height - fontheight;
				break;
			default:
				break;
			}
			g.drawString(pressText, x, y);
			g.dispose();
			ImageIO.write((BufferedImage) image, formatName, img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**水印测试**/
	public static void main(String[] args) {
		//pressImage("E:/mark.png", "E:/target.jpg", MARK_LOCATION.BOTTOMCENTER.toString());
		
//		Font font = LoadFont.FontZT(LoadFont.fontName.雅黑.toString(), Font.TRUETYPE_FONT, 30f);
		
//		Font font2 = new Font("宋体", font.BOLD, 80);
//		Color c = new Color(0xff00Ff);
//		pressText("这里是水印文字!", "E:/target.jpg", font, Color.white, 40, MARK_LOCATION.BOTTOMLEFT.toString());
		
		//pressText("!!这里是水印文字!!", "E:/target.jpg", font, c, 30, MARK_LOCATION.BOTTOMRIGHT.toString(), 0.8f, "PNG");
		
	}
}
