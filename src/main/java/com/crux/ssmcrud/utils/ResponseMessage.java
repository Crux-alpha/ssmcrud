package com.crux.ssmcrud.utils;

import static java.util.Collections.singletonMap;
import java.util.Map;

/**
 * 请求信息
 * 记录了响应状态码，响应信息，响应数据
 */
public record ResponseMessage<K,V>(int code, String msg, Map<K,V> data){
	private static final String SUCCESS_MESSAGE = "处理成功";
	private static final String FAILURE_MESSAGE = "处理失败";

	public static ResponseMessage<?,?> success(String msg){
		return success(msg, null);
	}

	public static <K,V> ResponseMessage<K,V> success(String msg, Map<K,V> data){
		return new ResponseMessage<>(200, msg, data);
	}

	public static <K,V> ResponseMessage<K,V> success(K name, V data){
		return success(SUCCESS_MESSAGE, singletonMap(name, data));
	}

	public static <K,V> ResponseMessage<K,V> success(String msg, K name, V data){
		return success(msg, singletonMap(name, data));
	}

	public static ResponseMessage<?,?> failure(String msg){
		return failure(msg, null);
	}

	public static <K,V> ResponseMessage<K,V> failure(String msg, Map<K,V> data){
		return new ResponseMessage<>(400, msg, data);
	}

	public static <K,V> ResponseMessage<K,V> failure(K name, V data){
		return failure(FAILURE_MESSAGE, name, data);
	}

	public static <K,V> ResponseMessage<K,V> failure(String msg, K name, V data){
		return failure(msg, singletonMap(name, data));
	}
}