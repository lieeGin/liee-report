package com.liee.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liee.core.exception.BaseException;


public class ArrayUtil {
	
  public final static ObjectMapper mapper = new ObjectMapper(); 
	
	public static int[] stringToIntArray(String s, String division) {
		if (s == null || s.length() == 0)
			return new int[0];
		String tmpData[] = s.split(division);
		int rc[] = new int[tmpData.length];
		for (int i = 0; i < tmpData.length; i++)
			rc[i] = StringUtil.stringToInt(tmpData[i], 0);

		return rc;
	}

	public static String intArrayToString(int data[], String division) {
		if (data == null || data.length == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append(data[0]);
		for (int i = 1; i < data.length; i++) {
			sb.append(division);
			sb.append(data[i]);
		}

		return sb.toString();
	}

	public static String intArrayToString(int data[][], int pos, String division) {
		if (data == null || data.length == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		if (data[0].length < pos + 1)
			return "";
		sb.append(data[0][pos]);
		for (int i = 1; i < data.length; i++) {
			if (data[i].length >= pos + 1) {
				sb.append(division);
				sb.append(data[i][pos]);
			}
		}

		return sb.toString();
	}

	public static String[] stringToArray(String s, String division) {
		if (s == null || s.length() == 0)
			return new String[0];
		return s.split(division, -1);
	}

	public static String arrayToString(String data[], String division) {
		if (data == null || data.length == 0)
			return "";
		StringBuffer sb = new StringBuffer(data[0]);
		for (int i = 1; i < data.length; i++) {
			sb.append(division);
			sb.append(data[i]);
		}

		return sb.toString();
	}

	public static String arrayToString(String data[][], int pos, String division) {
		if (data == null || data.length == 0)
			return "";
		if (data[0].length < pos + 1)
			return "";
		StringBuffer sb = new StringBuffer(data[0][pos]);
		for (int i = 1; i < data.length; i++) {
			if (data[i].length >= pos + 1) {
				sb.append(division);
				sb.append(data[i][pos]);
			}
		}

		return sb.toString();
	}

	public static String[] quoteStringToArray(String s, String division, String quote) {
		if (s == null || s.length() == 0)
			return new String[0];
		ArrayList<String> list = new ArrayList<String>();
		String tmp[] = s.split(division, -1);
		for (int i = 0; i < tmp.length; i++) {
			if (!tmp[i].startsWith(quote)) {
				list.add(tmp[i]);
			} else {
				String value = tmp[i];
				while (i + 1 < tmp.length) {
					int rc[] = countQuote(value, quote);
					if ((rc[0] - rc[1]) % 2 == 0 && (rc[0] != value.length() || rc[0] % 2 == 0)) {
						break;
					}
					value += division + tmp[++i];
				}
				if (value.length() >= 2 * quote.length())
					value = value.substring(quote.length(), value.length() - quote.length()).replaceAll(quote + quote, quote);
				else
					value = "";
				list.add(value);
			}
		}

		return list.toArray(new String[0]);
	}

	private static int[] countQuote(String value, String quote) {
		int rc[] = { 0, 0 };
		if (value == null || value.length() < quote.length())
			return rc;
		for (int j = 0; j <= value.length() - quote.length(); j += quote.length()) {
			if (!value.substring(j, j + quote.length()).equals(quote)) {
				break;
			}
			rc[0]++;
		}
		for (int j = value.length() - quote.length(); j >= 0; j -= quote.length()) {
			if (!value.substring(j, j + quote.length()).equals(quote)) {
				break;
			}
			rc[1]++;
		}

		return rc;
	}

	public static String arrayToQuoteString(String data[], String division, String quote) {
		if (data == null || data.length == 0)
			return "";
		StringBuffer sb = new StringBuffer(quote + data[0] + quote);
		for (int i = 1; i < data.length; i++) {
			sb.append(division);
			sb.append(quote);
			sb.append(data[i]);
			sb.append(quote);
		}

		return sb.toString();
	}

	public static String arrayToSingleQuoteString(String data[][], int pos, String division, String quote) {
		if (data == null || data.length == 0)
			return "";
		if (data[0].length < pos + 1)
			return "";
		StringBuffer sb = new StringBuffer(quote + data[0][pos] + quote);
		for (int i = 1; i < data.length; i++) {
			if (data[i].length >= pos + 1) {
				sb.append(division);
				sb.append(quote);
				sb.append(data[i][pos]);
				sb.append(quote);
			}
		}

		return sb.toString();
	}

	public static int[] columnArray(int data[][], int pos, int defaultValue) {
		if (data == null || data.length == 0) {
			return new int[0];
		}
		int[] rc = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null || pos >= data[i].length)
				rc[i] = defaultValue;
			else
				rc[i] = data[i][pos];
		}

		return rc;
	}

	public static String[] columnArray(String data[][], int pos, String defaultValue) {
		if (data == null || data.length == 0) {
			return new String[0];
		}
		String[] rc = new String[data.length];
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null || pos >= data[i].length)
				rc[i] = defaultValue;
			else
				rc[i] = data[i][pos];
		}

		return rc;
	}

	public static String[] camelToDash(String data[], boolean keepOldName) {
		if (data == null || data.length == 0) {
			return new String[0];
		}

		String rc[] = new String[data.length];
		if (keepOldName) {
			for (int i = 0; i < rc.length; i++) {
				rc[i] = data[i] + "->" + StringUtil.camel2Dash(data[i]);
			}
		} else {
			for (int i = 0; i < rc.length; i++) {
				rc[i] = StringUtil.camel2Dash(data[i]);
			}
		}

		return rc;
	}

	public static String[] dashToCamel(String data[], boolean keepOldName) {
		if (data == null || data.length == 0) {
			return new String[0];
		}

		String rc[] = new String[data.length];
		if (keepOldName) {
			for (int i = 0; i < rc.length; i++) {
				rc[i] = data[i] + "->" + StringUtil.dash2Camel(data[i]);
			}
		} else {
			for (int i = 0; i < rc.length; i++) {
				rc[i] = StringUtil.dash2Camel(data[i]);
			}
		}

		return rc;
	}

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static List getListFromJson(String jsonString,  Class<?>... elementClasses) {
		
		JavaType javaType = mapper.getTypeFactory().constructParametricType( ArrayList.class , elementClasses); 
		List lst = new ArrayList();
		try {
			if(StringUtil.isEmpty(jsonString)){
				return lst;
			}
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  // 未知属性不转
			lst = (List) mapper.readValue(jsonString, javaType);
		} catch (JsonParseException e) {
			throw new BaseException(e);
		} catch (JsonMappingException e) {
			throw new BaseException(e);
		} catch (IOException e) {
			throw new BaseException(e);
		} 
		return lst;
	}
	
	

}
