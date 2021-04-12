package hqsc.ray.wcc.jpa.dto.wechatPay;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：接收前台
 *
 * @author yang
 * @date 2021-04-09
 */
@NoArgsConstructor
@Data
public class NotifyDto {
	
	
	/**
	 * id : EV-2018022511223320873
	 * create_time : 2015-05-20T13:29:35+08:00
	 * resource_type : encrypt-resource
	 * event_type : TRANSACTION.SUCCESS
	 * resource : {"algorithm":"AEAD_AES_256_GCM","ciphertext":"...","nonce":"...","original_type":"transaction","associated_data":""}
	 * summary : 支付成功
	 */
	
	private String id;
	private String create_time;
	private String resource_type;
	private String event_type;
	private ResourceDTO resource;
	private String summary;
	
	@NoArgsConstructor
	@Data
	public static class ResourceDTO {
		/**
		 * algorithm : AEAD_AES_256_GCM
		 * ciphertext : ...
		 * nonce : ...
		 * original_type : transaction
		 * associated_data :
		 */
		
		private String algorithm;
		private String ciphertext;
		private String nonce;
		private String original_type;
		private String associated_data;
	}
	
	@NoArgsConstructor
	@Data
	public static class Resource {
		
		/**
		 * transaction_id : 1217752501201407033233368018
		 * amount : {"payer_total":100,"total":100,"currency":"CNY","payer_currency":"CNY"}
		 * mchid : 1230000109
		 * trade_state : SUCCESS
		 * bank_type : CMC
		 * promotion_detail : [{"amount":100,"wechatpay_contribute":0,"coupon_id":"109519","scope":"GLOBAL","merchant_contribute":0,"name":"单品惠-6","other_contribute":0,"currency":"CNY","stock_id":"931386","goods_detail":[{"goods_remark":"商品备注信息","quantity":1,"discount_amount":1,"goods_id":"M1006","unit_price":100},{"goods_remark":"商品备注信息","quantity":1,"discount_amount":1,"goods_id":"M1006","unit_price":100}]},{"amount":100,"wechatpay_contribute":0,"coupon_id":"109519","scope":"GLOBAL","merchant_contribute":0,"name":"单品惠-6","other_contribute":0,"currency":"CNY","stock_id":"931386","goods_detail":[{"goods_remark":"商品备注信息","quantity":1,"discount_amount":1,"goods_id":"M1006","unit_price":100},{"goods_remark":"商品备注信息","quantity":1,"discount_amount":1,"goods_id":"M1006","unit_price":100}]}]
		 * success_time : 2018-06-08T10:34:56+08:00
		 * payer : {"openid":"oUpF8uMuAJO_M2pxb1Q9zNjWeS6o"}
		 * out_trade_no : 1217752501201407033233368018
		 * appid : wxd678efh567hg6787
		 * trade_state_desc : 支付成功
		 * trade_type : MICROPAY
		 * attach : 自定义数据
		 * scene_info : {"device_id":"013467007045764"}
		 */
		
		private String transaction_id;
		private AmountDTO amount;
		private String mchid;
		private String trade_state;
		private String bank_type;
		private List<PromotionDetailDTO> promotion_detail;
		private String success_time;
		private PayerDTO payer;
		private String out_trade_no;
		private String appid;
		private String trade_state_desc;
		private String trade_type;
		private String attach;
		private SceneInfoDTO scene_info;
		
		@NoArgsConstructor
		@Data
		public static class AmountDTO {
			/**
			 * payer_total : 100
			 * total : 100
			 * currency : CNY
			 * payer_currency : CNY
			 */
			
			private Integer payer_total;
			private Integer total;
			private String currency;
			private String payer_currency;
		}
		
		@NoArgsConstructor
		@Data
		public static class PayerDTO {
			/**
			 * openid : oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
			 */
			
			private String openid;
		}
		
		@NoArgsConstructor
		@Data
		public static class SceneInfoDTO {
			/**
			 * device_id : 013467007045764
			 */
			
			private String device_id;
		}
		
		@NoArgsConstructor
		@Data
		public static class PromotionDetailDTO {
			/**
			 * amount : 100
			 * wechatpay_contribute : 0
			 * coupon_id : 109519
			 * scope : GLOBAL
			 * merchant_contribute : 0
			 * name : 单品惠-6
			 * other_contribute : 0
			 * currency : CNY
			 * stock_id : 931386
			 * goods_detail : [{"goods_remark":"商品备注信息","quantity":1,"discount_amount":1,"goods_id":"M1006","unit_price":100},{"goods_remark":"商品备注信息","quantity":1,"discount_amount":1,"goods_id":"M1006","unit_price":100}]
			 */
			
			private Integer amount;
			private Integer wechatpay_contribute;
			private String coupon_id;
			private String scope;
			private Integer merchant_contribute;
			private String name;
			private Integer other_contribute;
			private String currency;
			private String stock_id;
			private List<GoodsDetailDTO> goods_detail;
			
			@NoArgsConstructor
			@Data
			public static class GoodsDetailDTO {
				/**
				 * goods_remark : 商品备注信息
				 * quantity : 1
				 * discount_amount : 1
				 * goods_id : M1006
				 * unit_price : 100
				 */
				
				private String goods_remark;
				private Integer quantity;
				private Integer discount_amount;
				private String goods_id;
				private Integer unit_price;
			}
		}
	}
}
