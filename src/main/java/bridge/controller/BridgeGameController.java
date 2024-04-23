package bridge.controller;

import java.util.List;

import bridge.domain.BridgeMaker;
import bridge.domain.BridgeRandomNumberGenerator;
import bridge.service.BridgeGame;
import bridge.service.BridgeMapMaker;
import bridge.view.InputView;
import bridge.view.OutputView;

public class BridgeGameController {

	private final BridgeMapMaker bridgeMapMaker;
	private final InputView inputView;
	private final OutputView outputView;
	private BridgeGame bridgeGame;

	public BridgeGameController() {

		inputView = new InputView();
		outputView = new OutputView();

		outputView.printStartGame();
		BridgeRandomNumberGenerator bridgeRandomNumberGenerator = new BridgeRandomNumberGenerator();
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeRandomNumberGenerator);

		controlMakingBridge(bridgeMaker);
		bridgeMapMaker = new BridgeMapMaker();
	}

	private void controlMakingBridge(BridgeMaker bridgeMaker) {
		boolean errorSign = true;
		while (errorSign) {
			try {
				int bridgeSize = inputView.readBridgeSize();
				List<String> bridge = bridgeMaker.makeBridge(bridgeSize);
				bridgeGame = new BridgeGame(bridge);
				errorSign = false;
			} catch (IllegalArgumentException | IllegalStateException e) {
				outputView.printErrorMessage(e.getMessage());
			}
		}
	}

	public void play() {
		do {
			proceedMoveRound();

			if (bridgeGame.isCrossBridge()) {
				break;
			}
		} while (!isRetryOrGiveUp());

		String map = bridgeMapMaker.make(bridgeGame.getBridge(), bridgeGame.isMoveSuccess(), bridgeGame.getMoveCount());
		outputView.printResult(bridgeGame, map);
	}

	private boolean isRetryOrGiveUp() {
		String input = "";
		while (input.isBlank()) {
			try {
				input = inputView.readGameCommand();
				bridgeGame.retry(input);
				return bridgeGame.isGiveUp(input);
			} catch (IllegalArgumentException e) {
				outputView.printErrorMessage(e.getMessage());
			}
		}
		return bridgeGame.isGiveUp(input);
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
				outputView.printErrorMessage(e.getMessage());
			}
		} while (errorSign);
	}

}
