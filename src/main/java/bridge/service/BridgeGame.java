package bridge.service;

import java.util.List;

import bridge.constant.BridgeMoveOption;

public class BridgeGame {

	private static final int INDEX_CONVERTER = -1;

	private final List<String> bridge;
	private int moveCount;
	private int tryCount;
	private boolean moveSuccessSign;

	public BridgeGame(List<String> bridge) {
		this.bridge = bridge;
		tryCount++;
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

		if(!BridgeMoveOption.UP.getSignatureOption().equalsIgnoreCase(moveOptionSignature)
		&& !BridgeMoveOption.DOWN.getSignatureOption().equalsIgnoreCase(moveOptionSignature)) {
			throw new IllegalArgumentException("지정된 이동 명령어(U, D)만 사용 가능합니다.");
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

	public int getMoveCount() {
		return moveCount;
	}
}
