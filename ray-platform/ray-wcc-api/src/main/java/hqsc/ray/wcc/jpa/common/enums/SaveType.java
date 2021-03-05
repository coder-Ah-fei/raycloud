package hqsc.ray.wcc.jpa.common.enums;

/**
 * @author yangy
 */

public enum SaveType {
	
	/**
	 *
	 */
	add("新增"),
	edit("更新"),
	;
	
	
	private String text;
	
	private SaveType(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
