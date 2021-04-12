package hqsc.ray.wcc.jpa.form.wechatPay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：接收前台微信统一下单请求参数
 *
 * @author yang
 * @date 2021-04-08
 */
@NoArgsConstructor
@Data
public class UnifiedOrderForm {
	
	/**
	 * time_expire : 2018-06-08T10:34:56+08:00
	 * amount : {"total":100,"currency":"CNY"}
	 * mchid : 1230000109
	 * description : Image形象店-深圳腾大-QQ公仔
	 * notify_url : https://www.weixin.qq.com/wxpay/pay.php
	 * payer : {"openid":"oUpF8uMuAJO_M2pxb1Q9zNjWeS6o"}
	 * out_trade_no : 1217752501201407033233368018
	 * goods_tag : WXG
	 * appid : wxd678efh567hg6787
	 * attach : 自定义数据说明
	 * detail : {"invoice_id":"wx123","goods_detail":[{"goods_name":"iPhoneX 256G","wechatpay_goods_id":"1001","quantity":1,"merchant_goods_id":"商品编码","unit_price":828800},{"goods_name":"iPhoneX 256G","wechatpay_goods_id":"1001","quantity":1,"merchant_goods_id":"商品编码","unit_price":828800}],"cost_price":608800}
	 * scene_info : {"store_info":{"address":"广东省深圳市南山区科技中一道10000号","area_code":"440305","name":"腾讯大厦分店","id":"0001"},"device_id":"013467007045764","payer_client_ip":"14.23.150.211"}
	 * settle_info : {"profit_sharing":false}
	 */
	
	@JSONField(name = "time_expire")
	private String time_expire;
	@JSONField(name = "amount")
	private AmountDTO amount;
	@JSONField(name = "mchid")
	private String mchid;
	@JSONField(name = "description")
	private String description;
	@JSONField(name = "notify_url")
	private String notify_url;
	@JSONField(name = "payer")
	private PayerDTO payer;
	@JSONField(name = "out_trade_no")
	private String out_trade_no;
	@JSONField(name = "goods_tag")
	private String goodgoods_tagsTag;
	@JSONField(name = "appid")
	private String appid;
	@JSONField(name = "attach")
	private String attach;
	@JSONField(name = "detail")
	private DetailDTO detail;
	@JSONField(name = "scene_info")
	private SceneInfoDTO scene_info;
	@JSONField(name = "settle_info")
	private SettleInfoDTO settle_info;
	
	@NoArgsConstructor
	@Data
	public static class AmountDTO {
		/**
		 * total : 100
		 * currency : CNY
		 */
		
		@JSONField(name = "total")
		private Integer total;
		@JSONField(name = "currency")
		private String currency;
	}
	
	@NoArgsConstructor
	@Data
	public static class PayerDTO {
		/**
		 * openid : oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
		 */
		
		@JSONField(name = "openid")
		private String openid;
	}
	
	@NoArgsConstructor
	@Data
	public static class DetailDTO {
		/**
		 * invoice_id : wx123
		 * goods_detail : [{"goods_name":"iPhoneX 256G","wechatpay_goods_id":"1001","quantity":1,"merchant_goods_id":"商品编码","unit_price":828800},{"goods_name":"iPhoneX 256G","wechatpay_goods_id":"1001","quantity":1,"merchant_goods_id":"商品编码","unit_price":828800}]
		 * cost_price : 608800
		 */
		
		@JSONField(name = "invoice_id")
		private String invoice_id;
		@JSONField(name = "goods_detail")
		private List<GoodsDetailDTO> goods_detail;
		@JSONField(name = "cost_price")
		private Integer cost_price;
		
		@NoArgsConstructor
		@Data
		public static class GoodsDetailDTO {
			/**
			 * goods_name : iPhoneX 256G
			 * wechatpay_goods_id : 1001
			 * quantity : 1
			 * merchant_goods_id : 商品编码
			 * unit_price : 828800
			 */
			
			@JSONField(name = "goods_name")
			private String goods_name;
			@JSONField(name = "wechatpay_goods_id")
			private String wechatpay_goods_id;
			@JSONField(name = "quantity")
			private Integer quantity;
			@JSONField(name = "merchant_goods_id")
			private String merchant_goods_id;
			@JSONField(name = "unit_price")
			private Integer unit_price;
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class SceneInfoDTO {
		/**
		 * store_info : {"address":"广东省深圳市南山区科技中一道10000号","area_code":"440305","name":"腾讯大厦分店","id":"0001"}
		 * device_id : 013467007045764
		 * payer_client_ip : 14.23.150.211
		 */
		
		@JSONField(name = "store_info")
		private StoreInfoDTO store_info;
		@JSONField(name = "device_id")
		private String device_id;
		@JSONField(name = "payer_client_ip")
		private String payer_client_ip;
		
		@NoArgsConstructor
		@Data
		public static class StoreInfoDTO {
			/**
			 * address : 广东省深圳市南山区科技中一道10000号
			 * area_code : 440305
			 * name : 腾讯大厦分店
			 * id : 0001
			 */
			
			@JSONField(name = "address")
			private String address;
			@JSONField(name = "area_code")
			private String area_code;
			@JSONField(name = "name")
			private String name;
			@JSONField(name = "id")
			private String id;
		}
	}
	
	@NoArgsConstructor
	@Data
	public static class SettleInfoDTO {
		/**
		 * profit_sharing : false
		 */
		
		@JSONField(name = "profit_sharing")
		private Boolean profit_sharing;
	}
}
