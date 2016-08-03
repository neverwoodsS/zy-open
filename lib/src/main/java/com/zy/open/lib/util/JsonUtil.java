package com.zy.open.lib.util;

/**
 *  Create by zhangll on 2016.07.31
 */
public class JsonUtil {
	/**
	 * 处理 json 数据中不规范的引号
	 * @param in
	 * @return
	 */
	public static String quotationMarkFilter(String in) {
		if (in == null) {
			return "";
		}
		return replaceQuotationMarkInValue(deleteQuotationMarkOutOfObject(in));
	}

	/**
	 * 删除object、array 外部不应存在的引号
	 * @param in
	 * @return
     */
	private static String deleteQuotationMarkOutOfObject(String in) {
		return in.replaceAll("\"\\[", "[").replaceAll("\\]\"", "]")
				.replaceAll("\"\\{", "{").replaceAll("\\}\"", "}")
				.replaceAll("\\\\\"", "\"");
	}
	
	/**
	 * 替换value中的英文引号-->中文引号
	 * @param in
	 * @return
	 */
	private static String replaceQuotationMarkInValue(String in) {
		char[] temp = in.toCharArray();
		int n = temp.length;
		for (int i = 0; i < n; i++) {
			if (temp[i] == ':' && temp[i + 1] == '"') {
				for (int j = i + 2; j < n; j++) {
					if (temp[j] == '"') {
						if (temp[j + 1] != ',' && temp[j + 1] != '}') {
							temp[j] = '”';
						} else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
							break;
						}
					}
				}
			}
		}
		return new String(temp);
	}
}