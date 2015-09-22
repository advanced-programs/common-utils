package zx.soft.utils.threads;

public class StringPool extends ObjectFactory<String> {
	private int i = 0;

	public StringPool() {
		super();
	}

	public StringPool(int num, int tryNum) {
		super(num, tryNum);
	}

	@Override
	protected String create() {
		return "" + (i++);
	}
}
