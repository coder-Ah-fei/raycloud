package hqsc.ray.component.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * 用户发送ssl认证的https请求
 *
 * @author yang [yiixuan@163.com]
 */
public class HttpsUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpsUtil.class);
	
	private static class TrustAnyTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}
		
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}
		
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[]{};
		}
	}
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	/**
	 * get方式请求服务器(https协议)
	 *
	 * @param url   请求地址
	 * @param clazz 要得到的对象的类型
	 * @param <T>   泛型
	 * @return 得到的对象
	 */
	public static <T> T get(String url, Class<T> clazz) {
		T t = null;
		byte[] bytes = new byte[0];
		try {
			bytes = get(url);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			
			String resultStr = new String(bytes);
			LOGGER.info("HTTPS-->GET请求地址：【" + url + "】");
			LOGGER.info("返回结果：【" + resultStr + "】");
			t = objectMapper.readValue(resultStr, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * get方式请求服务器(https协议)
	 *
	 * @param url 请求地址
	 * @return 返回
	 * @throws NoSuchAlgorithmException 异常
	 * @throws KeyManagementException   异常
	 * @throws IOException              异常
	 */
	public static byte[] get(String url)
			throws NoSuchAlgorithmException, KeyManagementException,
			IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
				new java.security.SecureRandom());
		
		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.connect();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			
			return outStream.toByteArray();
		}
		return new byte[0];
	}
	
	/**
	 * post方式请求服务器(https协议)
	 *
	 * @param url     请求地址
	 * @param content 参数
	 * @param clazz   要得到的对象的类型
	 * @param <T>     泛型
	 * @return 得到的对象
	 */
	public static <T> T post(String url, String content, Class<T> clazz) {
		T t = null;
		byte[] bytes = post(url, content);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String resultStr = new String(bytes);
			LOGGER.info("HTTPS-->POST请求地址：【" + url + "】请求参数：【" + content + "】");
			LOGGER.info("返回结果：【" + resultStr + "】");
			t = objectMapper.readValue(resultStr, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * post方式请求服务器(https协议)
	 *
	 * @param url     请求地址
	 * @param content 可穿json格式的字符串
	 * @return 结果
	 */
	public static byte[] post(String url, String content) {
		byte[] bytes = new byte[0];
		try {
			bytes = post(url, content, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	
	/**
	 * post方式请求服务器(https协议)
	 *
	 * @param url     请求地址
	 * @param content 可串JSON格式的参数
	 * @param charset 参数的编码方式
	 * @return 返回值
	 * @throws NoSuchAlgorithmException 异常
	 * @throws KeyManagementException   异常
	 * @throws IOException              异常
	 */
	public static byte[] post(String url, String content, String charset)
			throws NoSuchAlgorithmException, KeyManagementException,
			IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
				new java.security.SecureRandom());
		
		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// 刷新、关闭
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return new byte[0];
	}
	
	/**
	 * 上传图片素材到微信
	 *
	 * @param url  地址
	 * @param file 文件
	 * @return 返回值
	 * @throws NoSuchAlgorithmException 异常
	 * @throws KeyManagementException   异常
	 * @throws IOException              异常
	 */
	public static byte[] postUploadPicToWeChat(String url, MultipartFile file)
			throws NoSuchAlgorithmException, KeyManagementException,
			IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[]{new TrustAnyTrustManager()},
				new java.security.SecureRandom());
		
		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		String boundary = "----WebKitFormBoundaryaPRHAvJAgcx12zPm";
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4056.0 Safari/537.36 Edg/82.0.432.3");
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		
		StringBuilder sb = new StringBuilder();
		sb.append("--").append("----WebKitFormBoundaryaPRHAvJAgcx12zPm").append("\r\n");
		sb.append("Content-Disposition: form-data; name=\"media\"; filename=\"").append(file.getOriginalFilename()).append("\"\r\n");
		sb.append("Content-Type: image/jpeg").append("\r\n");
		sb.append("\r\n");
		out.write(sb.toString().getBytes());
		out.write(file.getBytes());
		out.write("\r\n".getBytes());
		out.write(("\r\n--" + boundary + "--\r\n").getBytes());
		
		// 刷新、关闭
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return null;
	}
	
	
	/**
	 * 上传二进制文件
	 *
	 * @param graphurl 接口地址
	 * @param file     图片文件
	 * @return
	 */
	public static String uploadFile(String graphurl, MultipartFile file) {
		String line = null;//接口返回的结果
		try {
			// 换行符
			final String newLine = "\r\n";
			final String boundaryPrefix = "--";
			// 定义数据分隔线
			String BOUNDARY = "========7d4a6d158c9";
			// 服务器的域名
			URL url = new URL(graphurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置为POST情
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求头参数
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			
			// 上传文件
			StringBuilder sb = new StringBuilder();
			sb.append(boundaryPrefix);
			sb.append(BOUNDARY);
			sb.append(newLine);
			// 文件参数,photo参数名可以随意修改
			sb.append("Content-Disposition: form-data;name=\"image\";filename=\""
					+ "https://api.weixin.qq.com" + "\"" + newLine);
			sb.append("Content-Type:application/octet-stream");
			// 参数头设置完以后需要两个换行，然后才是参数内容
			sb.append(newLine);
			sb.append(newLine);
			
			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());
			
			// 读取文件数据
			out.write(file.getBytes());
			// 最后添加换行
			out.write(newLine.getBytes());
			
			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			byte[] end_data = (newLine + boundaryPrefix + BOUNDARY
					+ boundaryPrefix + newLine).getBytes();
			// 写上结尾标识
			out.write(end_data);
			out.flush();
			out.close();
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				return line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
		}
		return line;
	}
	
	/**
	 * 上传二进制文件
	 *
	 * @param apiurl 接口地址
	 * @param file   图片文件
	 * @return
	 */
	public static String uploadFile(String apiurl, byte[] file) {
		//接口返回的结果
		String line = null;
		try {
			// 换行符
			final String newLine = "\r\n";
			final String boundaryPrefix = "--";
			// 定义数据分隔线
			String BOUNDARY = "========7d4a6d158c9";
			// 服务器的域名
			URL url = new URL(apiurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置为POST情
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求头参数
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/7.0.15(0x17000f31) NetType/WIFI Language/zh_CN");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			
			// 上传文件
			StringBuilder sb = new StringBuilder();
			sb.append(boundaryPrefix);
			sb.append(BOUNDARY);
			sb.append(newLine);
			// 文件参数,photo参数名可以随意修改
			sb.append("Content-Disposition: form-data;name=\"image\";filename=\""
					+ "https://api.weixin.qq.com" + "\"" + newLine);
			sb.append("Content-Type:application/octet-stream");
			// 参数头设置完以后需要两个换行，然后才是参数内容
			sb.append(newLine);
			sb.append(newLine);
			
			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());
			
			// 读取文件数据
			out.write(file);
			// 最后添加换行
			out.write(newLine.getBytes());
			
			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			byte[] end_data = (newLine + boundaryPrefix + BOUNDARY
					+ boundaryPrefix + newLine).getBytes();
			// 写上结尾标识
			out.write(end_data);
			out.flush();
			out.close();
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				return line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
		}
		return line;
	}
	
}
