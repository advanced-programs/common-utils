package zx.soft.utils.threads;

public class StringPool extends ObjectFactory<String> {
	private int i = 0;

	public StringPool() {
		super();
	}

	public StringPool(int num) {
		super(num, 5);
	}

	@Override
	protected String create() {
		return "" + (i++);
	}
}
