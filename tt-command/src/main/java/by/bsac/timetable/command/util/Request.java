package by.bsac.timetable.command.util;

import java.util.HashMap;
import java.util.Map;

public class Request {

	private final Map<String, Object> requestParam = new HashMap<>();

	/**
	 * builder method
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Request putParam(String key, Object value) {
		requestParam.put(key, value);

		return this;
	}

	public void putAllParams(Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> elem : paramMap.entrySet()) {
			putParam(elem.getKey(), elem.getValue());
		}
	}

	public Object getValue(String key) {
		return requestParam.get(key);
	}

	public Map<String, Object> getAllParam() {
		return requestParam;
	}
}