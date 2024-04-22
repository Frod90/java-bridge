package bridge.service;

import java.util.List;

import bridge.constant.BridgeMoveOption;
import bridge.constant.ErrorMessage;

public class BridgeGame {

	private static final int INDEX_CONVERTER = -1;

	private final List<String> bridge;
	private int moveCount;
	private int tryCount;
	private boolean moveSuccessSign;

	public BridgeGame(List<String> bridge) {

		validateBridgeSize(bridge.size());

		for (String moveOptionSignature : bridge) {
			validateInjectedMoveOptionSignature(moveOptionSignature);
		}

		this.bridge = bridge;
		tryCount++;
	}

	private void validateBridgeSize(int bridgeSize) {

		final int MIN_BRIDGE_SIZE = 3;
		final int MAX_BRIDGE_SIZE = 20;

		if (bridgeSize < MIN_BRIDGE_SIZE || MAX_BRIDGE_SIZE < bridgeSize) {
			throw new IllegalArgumentException(ErrorMessage.BRIDGE_SIZE_INPUT);
		}
	}

	private void validateInjectedMoveOptionSignature(String moveStringOption) {

		if (!moveStringOption.equals(BridgeMoveOption.UP.getSignatureOption())
			&& !moveStringOption.equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			throw new IllegalStateException(ErrorMessage.MOVE_OPTION_SIGNATURE);
		}
	}

	public void move(String moveOptionSignature) {

		validateInputMoveOptionSignature(moveOptionSignature);
		moveCount++;

		if (moveOptionSignature.equalsIgnoreCase(bridge.get(moveCount + INDEX_CONVERTER))) {
			moveSuccessSign = true;
			return;
		}

		moveSuccessSign = false;
	}

	private void validateInputMoveOptionSignature(String moveOptionSignature) {

		if (!BridgeMoveOption.UP.getSignatureOption().equalsIgnoreCase(moveOptionSignature)
			&& !BridgeMoveOption.DOWN.getSignatureOption().equalsIgnoreCase(moveOptionSignature)) {
			throw new IllegalArgumentException(ErrorMessage.INPUT_MOVE_OPTION_SIGNATURE);
		}
	}

	public boolean isMoveSuccess() {
		return moveSuccessSign;
	}

	public void retry() {

		moveCount = 0;
		tryCount++;
	}

	public boolean isCrossBridge() {
		return moveCount == bridge.size();
	}

	public int getTryCount() {
		return tryCount;
	}

	public List<String> getBridge() {
		return bridge;
	}

	public int getBridgeSize() {
		return bridge.size();
	}

	public int getMoveCount() {
		return moveCount;
	}
}
