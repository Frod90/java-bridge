package bridge.view;

import bridge.service.BridgeGame;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

	private static final String START_GAME_MESSAGE = "다리 건너기 게임을 시작합니다.";
	private static final String GAME_RESULT_MESSAGE = "최종 게임 결과";
	private static final String SUCCESS_CONFIRM_MESSAGE = "게임 성공 여부: ";
	private static final String SUCCESS_MESSAGE = "성공";
	private static final String FAIL_MESSAGE = "실패";
	private static final String TOTAL_TRY_COUNT_CONFIRM_MESSAGE = "총 시도한 횟수: ";



	public void printStartGame() {
		System.out.println(START_GAME_MESSAGE);
	}

	public void printMap(String map) {
		System.out.println(map);
	}

	public void printResult(BridgeGame bridgeGame, String map) {

		System.out.println(GAME_RESULT_MESSAGE);
		printMap(map);
		System.out.println();

		printSuccessOrFail(bridgeGame);
		System.out.println(TOTAL_TRY_COUNT_CONFIRM_MESSAGE + bridgeGame.getTryCount());
	}

	private void printSuccessOrFail(BridgeGame bridgeGame) {

		if(bridgeGame.isCrossBridge()) {
			System.out.println(SUCCESS_CONFIRM_MESSAGE + SUCCESS_MESSAGE);
		}

		if(!bridgeGame.isCrossBridge()) {
			System.out.println(SUCCESS_CONFIRM_MESSAGE + FAIL_MESSAGE);
		}
	}

	public void printErrorMessage(String errorMessage) {
		System.out.println(errorMessage);
	}
}
