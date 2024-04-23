package bridge.view;

import java.util.regex.Pattern;

import bridge.constant.ErrorMessage;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

	private static final String BRIDGE_SIZE_INPUT_MESSAGE = "다리의 길이를 입력해주세요.";
	private static final String MOVE_OPTION_SIGNATURE_INPUT_MESSAGE = "이동할 칸을 선택해주세요. (위: U, 아래: D)";
	private static final String RETRY_OR_GIVE_UP_GAME_COMMEND_INPUT_MESSAGE = "게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)";

	private final OutputView outputView;

	public InputView() {
		this.outputView = new OutputView();
	}

	public int readBridgeSize() {

		do {
			try {
				String inputBridgeSize = validateNumericInput();
				return Integer.parseInt(inputBridgeSize);
			} catch (IllegalArgumentException e) {
				outputView.printErrorMessage(e.getMessage());
			}
		} while (true);
	}

	private String validateNumericInput() {
		final Pattern NUMBER_PATTERN = Pattern.compile("^[\\d]*$");

		System.out.println();
		System.out.println(BRIDGE_SIZE_INPUT_MESSAGE);
		String numericInput = Console.readLine().trim();

		if (!NUMBER_PATTERN.matcher(numericInput).matches()) {
			throw new IllegalArgumentException(ErrorMessage.NUMERIC_INPUT);
		}
		return numericInput;
	}

	public String readMoving() {

		System.out.println();
		System.out.println(MOVE_OPTION_SIGNATURE_INPUT_MESSAGE);
		return Console.readLine().trim();
	}

	public String readGameCommand() {
		System.out.println();
		System.out.println(RETRY_OR_GIVE_UP_GAME_COMMEND_INPUT_MESSAGE);
		return Console.readLine().trim();
	}
}
