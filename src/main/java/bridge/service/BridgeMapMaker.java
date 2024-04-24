package bridge.service;

import java.util.List;
import java.util.StringJoiner;

import bridge.constant.BridgeMoveOption;

public class BridgeMapMaker {

	private static final String START_MAP_FORMAT = "[";
	private static final String END_MAP_FORMAT = "]";
	private static final String SEPARATOR_MAP_FORMAT = "|";
	private static final String RIGHT_MAP_SECTION_SIGN = " O ";
	private static final String WRONG_MAP_SECTION_SIGN = " X ";
	private static final String UNSELECTED_MAP_SECTION_SIGN = "   ";
	private static final int INDEX_CONVERTER = -1;

	public String make(List<String> bridge, boolean moveSuccessSign, int moveCount) {
		StringJoiner upSideMap = new StringJoiner(SEPARATOR_MAP_FORMAT, START_MAP_FORMAT, END_MAP_FORMAT);
		StringJoiner downSideMap = new StringJoiner(SEPARATOR_MAP_FORMAT, START_MAP_FORMAT, END_MAP_FORMAT);

		for (int i = 0; i < moveCount + INDEX_CONVERTER; i++) {
			upSideMap.add(makeUpSideSection(bridge, i));
			downSideMap.add(makeDownSideSection(bridge, i));
		}

		upSideMap.add(makeUpSideLastSection(bridge, moveSuccessSign, moveCount));
		downSideMap.add(makeDownSideLastSection(bridge, moveSuccessSign, moveCount));
		return upSideMap + "\n" + downSideMap;
	}

	private String makeUpSideLastSection(List<String> bridge, boolean moveSuccessSign, int moveCount) {
		if (moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER)
			.equals(BridgeMoveOption.UP.getSignatureOption())) {
			return RIGHT_MAP_SECTION_SIGN;
		}

		if (!moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER)
			.equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			return WRONG_MAP_SECTION_SIGN;
		}

		return UNSELECTED_MAP_SECTION_SIGN;
	}

	private String makeDownSideLastSection(List<String> bridge, boolean moveSuccessSign, int moveCount) {
		if (moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER)
			.equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			return RIGHT_MAP_SECTION_SIGN;
		}

		if (!moveSuccessSign && bridge.get(moveCount + INDEX_CONVERTER)
			.equals(BridgeMoveOption.UP.getSignatureOption())) {
			return WRONG_MAP_SECTION_SIGN;
		}

		return UNSELECTED_MAP_SECTION_SIGN;
	}

	private String makeUpSideSection(List<String> bridge, int index) {

		if (bridge.get(index).equals(BridgeMoveOption.UP.getSignatureOption())) {
			return RIGHT_MAP_SECTION_SIGN;
		}

		if (bridge.get(index).equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			return UNSELECTED_MAP_SECTION_SIGN;
		}

		return WRONG_MAP_SECTION_SIGN;
	}

	private String makeDownSideSection(List<String> bridge, int index) {

		if (bridge.get(index).equals(BridgeMoveOption.DOWN.getSignatureOption())) {
			return RIGHT_MAP_SECTION_SIGN;
		}

		if (bridge.get(index).equals(BridgeMoveOption.UP.getSignatureOption())) {
			return UNSELECTED_MAP_SECTION_SIGN;
		}

		return WRONG_MAP_SECTION_SIGN;
	}
}
