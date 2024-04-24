package bridge.service;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import bridge.constant.ErrorMessage;
import bridge.domain.BridgeMaker;
import bridge.domain.BridgeNumberGenerator;
import bridge.domain.BridgeRandomNumberGenerator;
import camp.nextstep.edu.missionutils.Randoms;

public class BridgeGameTest {

	static BridgeGame bridgeGame;

	@BeforeAll
	static void setUp() {
		List<String> bridge = asList("U", "U", "D", "U", "D", "D");
		bridgeGame = new BridgeGame(bridge);
	}

	@AfterEach
	void clear() {
		bridgeGame.retry("R");
	}

	@DisplayName("지정된 크기를 벗어난 다리가 주입될 경우 오류 발생 테스트")
	@ParameterizedTest
	@ValueSource(ints = {-100, -1, 0, 1, 2, 21, 50})
	void injectOutOfSizeBridgeTest(int inputBridgeSize) {

		//given
		BridgeNumberGenerator bridgeNumberGenerator = new BridgeRandomNumberGenerator();
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeNumberGenerator);
		List<String> bridge = bridgeMaker.makeBridge(inputBridgeSize);

		//when, then
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
			() -> new BridgeGame(bridge));
		//then
		assertEquals(e.getMessage(), ErrorMessage.BRIDGE_SIZE_INPUT);
	}

	@DisplayName("지정된 이동 옵션 외의 옵션이 들어간 다리가 주입될 경우 오류 발생 테스트")
	@Test
	void injectWrongBridgeTest() {

		//given
		BridgeNumberGenerator bridgeNumberGenerator = () -> Randoms.pickNumberInRange(2, 10);
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeNumberGenerator);
		List<String> bridge = bridgeMaker.makeBridge(10);

		//when, then
		IllegalStateException e = assertThrows(IllegalStateException.class,
			() -> new BridgeGame(bridge));
		//then
		assertEquals(e.getMessage(), ErrorMessage.MOVE_OPTION_SIGNATURE);
	}

}