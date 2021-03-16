package hqsc.ray.component.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author
 */
public class VideoUtil {
	
	/**
	 * 截取视频截图
	 *
	 * @param filePath
	 * @param picPath
	 * @return
	 */
	public static File getVideoPic(String filePath, String picPath) {
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(filePath);
		try {
			ff.start();
			int lenght = ff.getLengthInFrames();
			int i = 0;
			Frame f = null;
			while (i < lenght) {
				// 过滤前5帧，避免出现全黑的图片，依自己情况而定
				f = ff.grabFrame();
				if ((i > 5) && (f.image != null)) {
					break;
				}
				i++;
			}
			
			// 截取的帧图片
			Java2DFrameConverter converter = new Java2DFrameConverter();
			BufferedImage srcImage = converter.getBufferedImage(f);
			int srcImageWidth = srcImage.getWidth();
			int srcImageHeight = srcImage.getHeight();
			
			// 对截图进行等比例缩放(缩略图)
			int width = 480;
			int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
			BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			
			File picFile = new File(picPath);
			ImageIO.write(thumbnailImage, "jpg", picFile);
			
			ff.stop();
			return picFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void section(String videoPath, String ffmpegPath, String videoOutPath) {
		try {
			List<String> command = new ArrayList<>();
			command.add(ffmpegPath);
			command.add("-i");
			command.add(videoPath);
			command.add("-c");
			command.add("copy");
			command.add("-map");
			command.add("0");
			command.add("-f");
			command.add("segment");
			command.add("-segment_list");
			command.add(videoOutPath + "playList.m3u8");
			command.add("-segment_time");
			command.add("5");
			command.add(videoOutPath + "abc%03d.ts");
			Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
			new PrintStream(videoProcess.getInputStream()).start();
			videoProcess.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
