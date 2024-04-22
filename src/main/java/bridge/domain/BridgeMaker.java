package bridge.domain;

import java.util.ArrayList;
import java.util.List;

import bridge.constant.BridgeMoveOption;
import bridge.constant.ErrorMessage;

public class BridgeMaker {

	private final BridgeNumberGenerator bridgeNumberGenerator;

	public BridgeMaker(BridgeNumberGenerator bridgeNumberGenerator) {
		this.bridgeNumberGenerator = bridgeNumberGenerator;
	}

	public List<String> makeBridge(int size) {

		List<String> bridge = new ArrayList<>();

		for (int i = 0; i < size; i++) {

			String section = makeBridgeSection();
			bridge.add(section);
		}

		return bridge;
	}

	private String makeBridgeSection() {

		int moveOptionNumber = bridgeNumberGenerator.generate();
		validateMoveOptionRange(moveOptionNumber);

		String moveOptionSignature = toStringMoveOption(moveOptionNumber);
		validateMoveOptionSignature(moveOptionSignature);

		return moveOptionSignature;
	}

	private void validateMoveOptionSignature(String moveStringOption) {

		if (moveStringOption.equals(BridgeMoveOption.FAIL.getSignatureOption())) {
			throw new IllegalStateException(ErrorMessage.MOVE_OPTION_SIGNATURE);
		}
	}

	private String toStringMoveOption(int moveNumberOption) {

		if (moveNumberOption == BridgeMoveOption.UP.getNumberOption()) {
			return BridgeMoveOption.UP.getSignatureOption();
		}

		if (moveNumberOption == BridgeMoveOption.DOWN.getNumberOption()) {
			return BridgeMoveOption.DOWN.getSignatureOption();
		}

		return BridgeMoveOption.FAIL.getSignatureOption();
	}

	private void validateMoveOptionRange(int moveOption) {

		if (moveOption != BridgeMoveOption.UP.getNumberOption()
			&& moveOption != BridgeMoveOption.DOWN.getNumberOption()) {
			throw new IllegalStateException(ErrorMessage.MOVE_OPTION_NUMBER);
		}
	}
}
