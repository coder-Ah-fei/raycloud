package hqsc.ray.wcc.wechatModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-03-29
 */
@NoArgsConstructor
@Data
public class GetPhoneModel {
	
	/**
	 * phoneNumber : 13333333333
	 * purePhoneNumber : 13333333333
	 * countryCode : 86
	 * watermark : {"timestamp":1617008742,"appid":"wx3d1fc01c88aeb1b3"}
	 */
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	@JsonProperty("purePhoneNumber")
	private String purePhoneNumber;
	@JsonProperty("countryCode")
	private String countryCode;
	@JsonProperty("watermark")
	private WatermarkDTO watermark;
	
	@NoArgsConstructor
	@Data
	public static class WatermarkDTO {
		/**
		 * timestamp : 1617008742
		 * appid : wx3d1fc01c88aeb1b3
		 */
		
		@JsonProperty("timestamp")
		private Integer timestamp;
		@JsonProperty("appid")
		private String appid;
	}
}
