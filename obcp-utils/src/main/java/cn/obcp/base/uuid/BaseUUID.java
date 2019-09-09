package cn.obcp.base.uuid;

public class BaseUUID {

    public static Sequence sequence = new Sequence(0, 0);

    public static long get() {
	return sequence.nextId();
    }


}
