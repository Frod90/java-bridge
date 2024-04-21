package bridge.constant;

public enum BridgeMoveOption {

	DOWN(0, "D"),
	UP(1, "U"),
	FAIL(-1, "F");

	private final int numberOption;
	private final String signatureOption;

	BridgeMoveOption(int numberOption, String stringOption) {
		this.numberOption = numberOption;
		this.signatureOption = stringOption;
	}

	public int getNumberOption() {
		return numberOption;
	}

	public String getSignatureOption() {
		return signatureOption;
	}
}
