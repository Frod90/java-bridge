package bridge.domain;

import java.util.ArrayList;
import java.util.List;

import bridge.constant.BridgeMoveOption;

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
		return toStringMoveOption(moveOptionNumber);
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

}
