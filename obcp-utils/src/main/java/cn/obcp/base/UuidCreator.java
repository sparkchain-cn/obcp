package cn.obcp.base;

import java.util.UUID;

import cn.obcp.base.uuid.Sequence;

public class UuidCreator {

	private boolean debug = true;
	public Sequence sequence;// = new Sequence(0, 0);

	public UuidCreator(boolean debug) {
		this.debug = debug;
		sequence = new Sequence(0, 0);
	}

	public UuidCreator(boolean debug, int workerId) {
		this.debug = debug;
		sequence = new Sequence(workerId, 0);
		System.out.println("UuidCreator:" + workerId);
	}

	/**
	 * 生成主键ID，调试模式使用BaseUUID发号<br>
	 * 正式环境通过http访问发号服务器来发号
	 * 
	 * @return
	 */
	public long id() {
		// return BaseUUID.get();
		return sequence.nextId();
	}

	public String uuid() {
		return UUID.randomUUID().toString();
	}
}
