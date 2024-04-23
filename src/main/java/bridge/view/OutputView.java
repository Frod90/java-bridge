package bridge.view;

import bridge.service.BridgeGame;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

	public void printMap(String map) {
		System.out.println(map);
	}

	public void printResult(BridgeGame bridgeGame, String map) {

		System.out.println();
		System.out.println("최종 게임 결과");
		printMap(map);
		System.out.println();

		System.out.println("게임 성공 여부: 성공");
		System.out.println("총 시도한 횟수: " + bridgeGame.getTryCount());
	}
}
