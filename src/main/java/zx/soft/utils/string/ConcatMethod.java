package zx.soft.utils.string;

/**
 * 字符串连接策略
 *
 * @author donglei
 *
 */
public enum ConcatMethod {

	AND() {

		@Override
		public StringBuilder concat(StringBuilder left, String right) {
			if (StringUtils.isEmpty(right)) {
				return left;
			}
			if (left.length() == 0) {
				left.append(right);
			} else {
				left.append(" AND " + right);
			}
			return left;
		}

	},

	OR() {

		@Override
		public StringBuilder concat(StringBuilder left, String right) {
			if (StringUtils.isEmpty(right)) {
				return left;
			}
			if (left.length() == 0) {
				left.append(right);
			} else {
				left.append(" OR " + right);
			}
			return left;
		}

	};

	private ConcatMethod() {
	}

	public abstract StringBuilder concat(StringBuilder left, String right);

}
