package bridge.controller;

import java.util.List;

import bridge.BridgeRandomNumberGenerator;
import bridge.domain.BridgeGame;
import bridge.domain.BridgeMaker;
import bridge.view.InputView;
import bridge.view.OutputView;

public class BridgeGameController {

	public void play() {
		InputView inputView = new InputView();
		OutputView outputView = new OutputView();

		int bridgeSize = inputView.readBridgeSize();

		BridgeRandomNumberGenerator bridgeRandomNumberGenerator = new BridgeRandomNumberGenerator();
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeRandomNumberGenerator);
		List<String> bridge = bridgeMaker.makeBridge(bridgeSize);
		BridgeGame bridgeGame = new BridgeGame(bridge);

		System.out.println("다리 건너기 게임을 시작합니다.");

		String input;
		do {

			for (int i = 0; i < bridgeSize; i++){

				String moveOptionSignature = inputView.readMoving();
				bridgeGame.move(moveOptionSignature);
				outputView.printMap(bridgeGame);

				if(!bridgeGame.isMoveSuccess()) {
					break;
				}

			}

			if (bridgeGame.isCrossBridge()) {
				break;
			}

			input = inputView.readGameCommand();

			if (input.equalsIgnoreCase("R")) {
				bridgeGame.retry();
			}

		} while (!input.equalsIgnoreCase("Q"));

		outputView.printResult(bridgeGame);

	}
}
