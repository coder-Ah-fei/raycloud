package hqsc.ray.core.common.util;

import java.math.BigDecimal;


public class Abacus {
	
	
	public static final int DEFAULT_SCALE = 30;//运算保留位数
	public static final int RESULT_SCALE = 2;//结果保留位数
	
	/**
	 * 用于 天 单位 和月单位的转换 基准
	 */
	public static final int DAYS_MONTH = 30;
	
	/**
	 * 月 与 年，换算单位
	 */
	public static final int MONTH_YEAR = 12;
	
	
	public static boolean lessThanO(BigDecimal decimal) {
		if (decimal != null) {
			return compareToZERO(decimal) < 0;
		}
		return false;
	}
	
	
	public static boolean greaterThanO(BigDecimal bigDecimal) {
		if (bigDecimal != null) {
			return compareToZERO(bigDecimal) > 0;
		}
		return false;
	}
	
	public static BigDecimal min(BigDecimal b1, BigDecimal b2) {
		return b1.min(b2);
	}
	
	public static BigDecimal max(BigDecimal b1, BigDecimal b2) {
		return b1.max(b2);
	}
	
	
	/**
	 * 根据年化利率，获得月利率
	 */
	public static BigDecimal getMonthlyRate(BigDecimal rate) {
		return divide(parsePercent(rate), MONTH_YEAR);
	}
	
	/**
	 * 由天利率，获得月利率
	 */
	public static BigDecimal getDay2MonthRate(BigDecimal rate) {
		return multiply(parsePercent(rate), DAYS_MONTH);
	}
	
	
	/**
	 * 解析百分比，就是格外缩小100倍
	 *
	 * @param rate
	 * @return
	 */
	public static BigDecimal parsePercent(BigDecimal rate) {
		return divide(rate, 100);
	}
	
	/**
	 * 格式化至两位
	 *
	 * @param b
	 * @return
	 */
	public static BigDecimal format(BigDecimal b) {
		return divide(b, BigDecimal.ONE, RESULT_SCALE);
	}
	
	/**
	 * 格式化小数位，且不进行四舍五入
	 *
	 * @param b
	 * @return
	 */
	public static BigDecimal format_ROUND_DOWN(BigDecimal b, int scale) {
		return b.setScale(scale, BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * 格式化数字，保留小数点后两位，且不进行四舍五入
	 */
	public static BigDecimal format_roundDown(BigDecimal b) {
		return b.setScale(2, BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * 格式化至两位
	 *
	 * @param b
	 * @return
	 */
	public static String formatString(BigDecimal b) {
		
		if (b == null) {
			return "0.00";
		}
		
		if (b.compareTo(BigDecimal.ZERO) == 0) {
			return "0.00";
		}
		
		return divide(b, BigDecimal.ONE, RESULT_SCALE).toString();
	}
	
	/**
	 * 加法运算
	 *
	 * @param p1
	 * @param p2
	 * @return p1+p2
	 */
	public static BigDecimal add(BigDecimal p1, BigDecimal p2) {
		
		return p1.add(p2);
	}
	
	public static BigDecimal add(BigDecimal p1, double p2) {
		
		return add(p1, new BigDecimal(p2));
	}
	
	
	public static BigDecimal add(BigDecimal p1, String p2) {
		
		return add(p1, new BigDecimal(p2));
	}
	
	/**
	 * 减法运算
	 *
	 * @param p1
	 * @param p2
	 * @return p1-p2
	 */
	public static BigDecimal subtract(BigDecimal p1, BigDecimal p2) {
		
		return p1.subtract(p2);
	}
	
	public static BigDecimal subtract(BigDecimal p1, int p2) {
		return p1.subtract(new BigDecimal(p2));
	}
	
	public static BigDecimal subtract(BigDecimal p1, String p2) {
		
		return subtract(p1, new BigDecimal(p2));
	}
	
	
	/**
	 * 两数相乘
	 *
	 * @param p1
	 * @param p2
	 * @return p1*p2
	 */
	public static BigDecimal multiply(BigDecimal p1, BigDecimal p2) {
		return p1.multiply(p2);
	}
	
	public static BigDecimal multiply(BigDecimal p1, String p2) {
		return multiply(p1, new BigDecimal(p2));
	}
	
	public static BigDecimal multiply(BigDecimal p1, int p2) {
		return multiply(p1, String.valueOf(p2));
	}
	
	/**
	 * 两数相除
	 *
	 * @param p1
	 * @param p2
	 * @param scale 小数点后的标度
	 * @return p1/p2
	 */
	public static BigDecimal divide(BigDecimal p1, BigDecimal p2, int scale) {
		if (scale < 0) {
			scale = DEFAULT_SCALE;
		}
		
		return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal divide(BigDecimal p1, BigDecimal p2) {
		return divide(p1, p2, DEFAULT_SCALE);
	}
	
	public static BigDecimal divide(BigDecimal p1, String p2) {
		return divide(p1, new BigDecimal(p2), DEFAULT_SCALE);
	}
	
	public static BigDecimal divide(BigDecimal p1, int p2) {
		return divide(p1, new BigDecimal(p2), DEFAULT_SCALE);
	}
	
	public static BigDecimal divide(BigDecimal p1, double p2) {
		return divide(p1, new BigDecimal(p2), DEFAULT_SCALE);
	}
	
	
	/**
	 * 取余数
	 *
	 * @return 整除倍数，剩余余数
	 */
	public static BigDecimal[] divideAndRemainder(BigDecimal p1, BigDecimal p2) {
		return p1.divideAndRemainder(p2);
	}
	
	/**
	 * 比较两数大小
	 *
	 * @param p1
	 * @param p2
	 * @return -1 小于  0 等于  1 大于
	 */
	public static int compare(BigDecimal p1, BigDecimal p2) {
		return p1.compareTo(p2);
	}
	
	public static int compare(BigDecimal p1, int p2) {
		return compare(p1, new BigDecimal(String.valueOf(p2)));
	}
	
	public static int compareToZERO(BigDecimal p1) {
		return compare(p1, BigDecimal.ZERO);
	}
	
	public static int compare(BigDecimal p1, double p2) {
		return compare(p1, new BigDecimal(String.valueOf(p2)));
	}
	
	public static int compare(BigDecimal p1, String p2) {
		return compare(p1, new BigDecimal(p2));
	}
	
	/**
	 * 四舍五入
	 *
	 * @param value
	 * @param scale 小数点后的标度
	 * @return
	 */
	public static BigDecimal round(BigDecimal value, int scale) {
		if (scale < 0) {
			scale = DEFAULT_SCALE;
		}
		return value.divide(BigDecimal.ONE, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	
	public static int overlookThousand(BigDecimal money) {
		int moneyLimit = money.intValue();
		if (moneyLimit >= 2000) {
			moneyLimit = moneyLimit / 1000;
			moneyLimit = moneyLimit * 1000;
		} else {
			moneyLimit = 1000;
		}
		return moneyLimit;
	}
	
	
	public static BigDecimal turnToDecimal(Object obj) {
		return obj == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(obj));
	}
	
	
	/**
	 * 计算管理费
	 *
	 * @param rate 为百分数
	 */
	public static BigDecimal getFeeForEach(BigDecimal total, BigDecimal rate, int amount) {
		
		return Abacus.multiply(Abacus.multiply(total, parsePercent(rate)), amount);
	}


//	/**
//	 * 根据还款计划，及标的计息方式，计算管理费和服务费
//	 * */
//	public static void forCurrentlyPlan(RepayPlans repayPlans, RepayRecord repayRecord) {
//		//新旧系统判定
//		if(HtmlUtils.isNewSystemBid(repayRecord.getBid())){
//			boolean isDayTypeMold = repayRecord.getBid().isDayTypeMold(); // 判断是否天标
//			BidBook bidBook = repayRecord.getBid().getBidBook();
//
//			int borrowTimeLimit = 1;
//			BigDecimal manageCostNorm = new BigDecimal(0);
//			BigDecimal serviceCostNorm = new BigDecimal(0);
//			BigDecimal manageCost = new BigDecimal(0);
//			BigDecimal serviceCost = new BigDecimal(0);
//
//			if(isDayTypeMold){ //按天计息情况
//				borrowTimeLimit = repayRecord.getBid().getBorrowTimeLimit();
//				manageCostNorm = bidBook.getDayManagementCostNorm();
//				serviceCostNorm = bidBook.getDaySiteServiceCostNorm();
//
//				// 本金 * 费率 * 期限 = 费用
//				manageCost = Abacus.getFeeForEach(bidBook.getCurrentlyLimit(), manageCostNorm, borrowTimeLimit);
//				serviceCost = Abacus.getFeeForEach(bidBook.getCurrentlyLimit(), serviceCostNorm, borrowTimeLimit);
//
//			}else{ //按月计息情况
//
//				borrowTimeLimit = 1;
//				manageCostNorm = bidBook.getManagementCostNorm();
//				serviceCostNorm = bidBook.getSiteServiceCostNorm();
//
//				// 本金 * 费率 * 期限 = 费用
//				manageCost = Abacus.getFeeForEach(bidBook.getCurrentlyLimit(), manageCostNorm, borrowTimeLimit);
//				serviceCost = Abacus.getFeeForEach(bidBook.getCurrentlyLimit(), serviceCostNorm, borrowTimeLimit);
//			}
//
//			//月标，一次性还本付息
//			if(!isDayTypeMold && repayRecord.getBid().getRefundWay() == RefundWay.PayTogetherInterest){
//				manageCost = Abacus.multiply(manageCost, repayRecord.getBid().getBorrowTimeLimit());
//				serviceCost = Abacus.multiply(serviceCost, repayRecord.getBid().getBorrowTimeLimit());
//			}
//
//			repayPlans.setManageCost(Abacus.formatString(manageCost));	//管理费
//			repayPlans.setServiceCost(Abacus.formatString(serviceCost));//平台服务费
//			BigDecimal sumCost = Abacus.add(manageCost, serviceCost);
//			repayPlans.setSumCost(Abacus.formatString(sumCost));		//合计费用
//
//		}else{
//			repayPlans.setManageCost("0");
//		}
//
//	}

}
