package bridge.domain;

import java.util.List;

import bridge.constant.BridgeMoveOption;

public class BridgeGame {

	private final String START_MAP_FORMAT = "[";
	private final String END_MAP_FORMAT = "]";
	private final String SEPARATOR_MAP_FORMAT = "|";
	private final String RIGHT_MAP_SECTION_SIGN = " O ";
	private final String WRONG_MAP_SECTION_SIGN = " X ";
	private final String UNSELECTED_MAP_SECTION_SIGN = "   ";
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

	public String getMap() {
		StringBuilder upSideMap = new StringBuilder(START_MAP_FORMAT);
		StringBuilder downSideMap = new StringBuilder(START_MAP_FORMAT);

		for (int i = 0; i < moveCount - 1; i++) {
			makeUpSideSection(upSideMap, i).append(SEPARATOR_MAP_FORMAT);
			makeDownSideSection(downSideMap, i).append(SEPARATOR_MAP_FORMAT);
		}

		makeUpSideLastSection(upSideMap).append(END_MAP_FORMAT);
		makeDownSideLastSection(downSideMap).append(END_MAP_FORMAT);
		return upSideMap.append("\n").append(downSideMap).toString();
	}

	private StringBuilder makeUpSideLastSection(StringBuilder upSideMap) {

		if (moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER).equals(BridgeMoveOption.UP.getSignatureOption())) {
			return upSideMap.append(RIGHT_MAP_SECTION_SIGN);
		}

		if (!moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER).equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			return upSideMap.append(WRONG_MAP_SECTION_SIGN);
		}

		return upSideMap.append(UNSELECTED_MAP_SECTION_SIGN);
	}

	private StringBuilder makeDownSideLastSection(StringBuilder downSideMap) {

		if (moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER).equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			return downSideMap.append(RIGHT_MAP_SECTION_SIGN);
		}

		if (!moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER).equals(BridgeMoveOption.UP.getSignatureOption())) {
			return downSideMap.append(WRONG_MAP_SECTION_SIGN);
		}

		return downSideMap.append(UNSELECTED_MAP_SECTION_SIGN);
	}

	private StringBuilder makeUpSideSection(StringBuilder upSideMap, int index) {

		if (bridge.get(index).equals(BridgeMoveOption.UP.getSignatureOption())) {
			upSideMap.append(RIGHT_MAP_SECTION_SIGN);
		}

		if (bridge.get(index).equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			upSideMap.append(UNSELECTED_MAP_SECTION_SIGN);
		}

		return upSideMap;
	}

	private StringBuilder makeDownSideSection(StringBuilder downSideMap, int index) {

		if (bridge.get(index).equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			return downSideMap.append(RIGHT_MAP_SECTION_SIGN);
		}

		if (bridge.get(index).equals(BridgeMoveOption.UP.getSignatureOption())) {
			return downSideMap.append(UNSELECTED_MAP_SECTION_SIGN);
		}

		return downSideMap;
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
}
