package bridge.controller;

import java.util.List;

import bridge.domain.BridgeMaker;
import bridge.domain.BridgeRandomNumberGenerator;
import bridge.service.BridgeGame;
import bridge.service.BridgeMapMaker;
import bridge.view.InputView;
import bridge.view.OutputView;

public class BridgeGameController {

	private BridgeGame bridgeGame;
	private final BridgeMapMaker bridgeMapMaker;
	private final InputView inputView;
	private final OutputView outputView;

	public BridgeGameController() {

		inputView = new InputView();
		outputView = new OutputView();

		System.out.println("다리 건너기 게임을 시작합니다.");
		BridgeRandomNumberGenerator bridgeRandomNumberGenerator = new BridgeRandomNumberGenerator();
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeRandomNumberGenerator);

		controlMakingBridge(bridgeMaker);
		bridgeMapMaker = new BridgeMapMaker();
	}

	private void controlMakingBridge(BridgeMaker bridgeMaker) {

		while (true) {
			try {
				int bridgeSize = inputView.readBridgeSize();
				List<String> bridge = bridgeMaker.makeBridge(bridgeSize);
				bridgeGame = new BridgeGame(bridge);
				return;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void play() {

		String input;
		do {
			proceedMoveRound();
			if (bridgeGame.isCrossBridge()) {
				break;
			}

			input = inputView.readGameCommand();
			if (input.equalsIgnoreCase("R")) {
				bridgeGame.retry();
			}
		} while (!input.equalsIgnoreCase("Q"));

		String map = bridgeMapMaker.make(bridgeGame.getBridge(), bridgeGame.isMoveSuccess(), bridgeGame.getMoveCount());
		outputView.printResult(bridgeGame, map);

	}

	private void proceedMoveRound() {

		for (int i = 0; i < bridgeGame.getBridgeSize(); i++) {
			controlInputMoveOption();
			String map = bridgeMapMaker.make(bridgeGame.getBridge(), bridgeGame.isMoveSuccess(),
				bridgeGame.getMoveCount());
			outputView.printMap(map);

			if (!bridgeGame.isMoveSuccess()) {
				break;
			}
		}
	}

	private void controlInputMoveOption() {
		boolean errorSign;
		do {
			try {
				String moveOptionSignature = inputView.readMoving();
				bridgeGame.move(moveOptionSignature);
				errorSign = false;
			} catch (IllegalArgumentException e) {
				errorSign = true;
				System.out.println(e.getMessage());
			}
		} while (errorSign);
	}

}
