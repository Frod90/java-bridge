package bridge.service;

import java.util.List;

public class BridgeGame {

	private final int INDEX_CONVERTER = -1;

	private final List<String> bridge;
	private int moveCount;
	private int tryCount;
	private boolean moveSuccessSign;

	public BridgeGame(List<String> bridge) {
		this.bridge = bridge;
		tryCount++;
	}

	public void move(String moveOptionSignature) {

		moveCount++;
		if (moveOptionSignature.equalsIgnoreCase(bridge.get(moveCount + INDEX_CONVERTER))) {
			moveSuccessSign = true;
			return;
		}

		moveSuccessSign = false;
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
