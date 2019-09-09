package cn.obcp.base.interfaces;

public interface IRetryParamsTime {

	public default long exec(int size, int i, long time) throws Exception {
		return time * (i + 1);
	}

}
