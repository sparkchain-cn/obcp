package cn.obcp.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.obcp.base.vo.KeyValue;

public class RetData<T> implements Serializable {
	private static final long serialVersionUID = 7790279432592205605L;

	public final static Logger logger = LoggerFactory.getLogger(RetData.class);

	private String code;
	private boolean success;

	private String message;
	private T data;

	public String getCode() {
		return code;
	}

	public RetData<T> setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public RetData<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public RetData<T> setData(T data) {
		this.data = data;
		return this;
	}

	public static RetData<?> succuess() {
		RetData<?> data = new RetData<>();
		data.setSuccess(true);
		data.setCode(200 + "");
		return data;
	}

	public static RetData<?> succuess(KeyValue... kvs) {
		RetData<Object> ret = new RetData<>();
		Map<String, Object> map = new HashMap<>();
		for (KeyValue kv : kvs) {
			map.put(kv.getKey(), kv.getVal());
		}
		ret.setCode(200 + "");
		ret.setSuccess(true);
		ret.setData(map);
		return ret;
	}

	public static RetData<?> succuess(Object data) {
		RetData<Object> retData = new RetData<>();
		retData.setCode(200 + "");
		retData.setSuccess(true);
		if (data instanceof KeyValue) {
			Map<String, Object> map = new HashMap<>();
			KeyValue kv = (KeyValue) data;
			map.put(kv.getKey(), kv.getVal());
			retData.setData(map);
		} else {
			retData.setData(data);
		}
		return retData;
	}

	public static RetData<?> succuess(Object data, String msg) {
		RetData<Object> retData = new RetData<>();
		retData.setCode(200 + "");
		retData.setSuccess(true);
		retData.setMessage(msg);
		if (data instanceof KeyValue) {
			Map<String, Object> map = new HashMap<>();
			KeyValue kv = (KeyValue) data;
			map.put(kv.getKey(), kv.getVal());
			retData.setData(map);
		} else {
			retData.setData(data);
		}
		return retData;
	}

	public static <T> RetData<?> error(String msg) {
		RetData<T> data = new RetData<>();
		data.setSuccess(false);
		data.setCode(505 + "");
		data.setMessage(msg);

		logger.error(msg);
		return data;
	}

	public static <T> RetData<?> error(String code, String msg, T data) {
		RetData<T> retData = new RetData<T>();
		retData.setSuccess(false);
		retData.setCode(code);
		retData.setMessage(msg);
		retData.setData(data);
		logger.error(msg);
		return retData;
	}

	public static <T> RetData<T> error(String code, String msg) {
		RetData<T> retData = new RetData<>();
		retData.setSuccess(false);
		retData.setCode(code);
		retData.setMessage(msg);
		// retData.setData(data);
		logger.error(msg);
		return retData;
	}

	/**
	 * retData 转换成通用类型的 layui 列表数据
	 *
	 * @param retData
	 * @return
	 */
	public static LayUiRetData toListLayUiRetData(RetData<?> retData) {
		if (!retData.isSuccess()) {
			return LayUiRetData.error(retData.getCode(), retData.getMessage());
		}
		if (retData.getData() instanceof Page) {
			Page<?> p = (Page<?>) retData.getData();
			return LayUiRetData.success(p.getResult(), p.getTotal());
		} else {
			Object data = retData.getData();
			return LayUiRetData.success(data, null == data ? 0l : 1l);
		}
	}
}
