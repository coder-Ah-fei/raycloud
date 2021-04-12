package hqsc.ray.wcc.jpa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-03-09
 */
@NoArgsConstructor
@Data
public class TengxunDili {
	
	
	@JsonProperty("status")
	private Integer status;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data_version")
	private String dataVersion;
	@JsonProperty("result")
	private List<List<ResultDTO>> result;
	
	@NoArgsConstructor
	@Data
	public static class ResultDTO {
		/**
		 * id : 110000
		 * name : 北京
		 * fullname : 北京市
		 * pinyin : ["bei","jing"]
		 * location : {"lat":39.90469,"lng":116.40717}
		 * cidx : [0,15]
		 */
		
		@JsonProperty("id")
		private String id;
		@JsonProperty("name")
		private String name;
		@JsonProperty("fullname")
		private String fullname;
		@JsonProperty("pinyin")
		private List<String> pinyin;
		@JsonProperty("location")
		private LocationDTO location;
		@JsonProperty("cidx")
		private List<Integer> cidx;
		
		@NoArgsConstructor
		@Data
		public static class LocationDTO {
			/**
			 * lat : 39.90469
			 * lng : 116.40717
			 */
			
			@JsonProperty("lat")
			private Double lat;
			@JsonProperty("lng")
			private Double lng;
		}
	}
}
