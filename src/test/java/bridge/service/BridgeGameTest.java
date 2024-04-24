package bridge.service;

import static java.util.Arrays.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

	@BeforeEach
	void setUp() {
		List<String> bridge = asList("U", "U", "D", "U", "D", "D");
		bridgeGame = new BridgeGame(bridge);
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

	@DisplayName("이동 옵션 입력 오류 발생 테스트")
	@ParameterizedTest
	@ValueSource(strings = {"s", "S", "a", "1", "-99", "", "    "})
	void validateInputWrongMoveOptionTest(String input) {

		assertThatThrownBy(() -> bridgeGame.move(input))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(ErrorMessage.INPUT_MOVE_OPTION_SIGNATURE);
	}

	@DisplayName("이동 테스트")
	@ParameterizedTest
	@ValueSource(strings = {"U", "D", "UU", "UUD", "UUDU", "UUDUDD", "UUDUDU"})
	void injectBridgeTest(String input) {

		String[] inputs = input.split("");

		for (String eachInput : inputs) {
			bridgeGame.move(eachInput);
		}

		assertEquals(inputs.length, bridgeGame.getMoveCount());
	}

	@DisplayName("이동 확인 테스트")
	@Test
	void isTrueMoveSuccessTest() {

		//given
		String[] inputs = {"U", "U", "D", "U", "D", "D"};

		for (String eachInput : inputs) {
			//when
			bridgeGame.move(eachInput);
			//then
			assertTrue(bridgeGame.isMoveSuccess());
		}
	}

	@DisplayName("이동불가 상태에서 이동 메서드 호출 테스트")
	@ParameterizedTest
	@ValueSource(strings = {"D", "UUU", "UUDUDU", "UUDUDD"})
	void wrongCallMoveTest(String input) {

		//given
		String[] inputs = input.split("");

		for (String eachInput : inputs) {
			bridgeGame.move(eachInput);
		}

		//when, then
		assertAll(
			() -> assertThatThrownBy(() -> bridgeGame.move("U"))
				.isInstanceOf(IllegalStateException.class)
				.hasMessage(ErrorMessage.WRONG_CALL_MOVE),
			() -> assertThatThrownBy(() -> bridgeGame.move("D"))
				.isInstanceOf(IllegalStateException.class)
				.hasMessage(ErrorMessage.WRONG_CALL_MOVE)
		);
	}

	@DisplayName("잘못된 재시작, 종료 입력 테스트")
	@ParameterizedTest
	@ValueSource(strings = {"U", "D", "1", "", " "})
	void validateGameCommendTest(String input) {

		assertAll(
			() -> assertThatThrownBy(() -> bridgeGame.retry(input))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(ErrorMessage.GAME_COMMEND_INPUT),
			() -> assertThatThrownBy(() -> bridgeGame.isGiveUp(input))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(ErrorMessage.GAME_COMMEND_INPUT)
		);
	}

	@DisplayName("재시작 테스트")
	@Test
	void retryTest() {

		//given
		String[] inputMoveOptions = {"U", "U", "D", "U", "D", "D"};
		for (String inputMoveOption : inputMoveOptions) {
			bridgeGame.move(inputMoveOption);
		}

		//when
		String inputGameCommend = "R";
		bridgeGame.retry(inputGameCommend);

		//then
		assertAll(
			() -> assertEquals(0, bridgeGame.getMoveCount()),
			() -> assertEquals(2, bridgeGame.getTryCount()),
			() -> assertFalse(bridgeGame.isGiveUp(inputGameCommend))
		);
	}

	@DisplayName("포기 테스트")
	@Test
	void giveUpTest() {

		//given
		String[] inputMoveOptions = {"U", "U", "D"};
		for (String inputMoveOption : inputMoveOptions) {
			bridgeGame.move(inputMoveOption);
		}

		//when
		String inputGameCommend = "Q";
		bridgeGame.retry(inputGameCommend);

		//then
		assertAll(
			() -> assertEquals(3, bridgeGame.getMoveCount()),
			() -> assertEquals(1, bridgeGame.getTryCount()),
			() -> assertTrue(bridgeGame.isGiveUp(inputGameCommend))
		);
	}

	@DisplayName("건너기 성공 테스트")
	@Test
	void crossTrueBridgeTest() {

		//given
		String[] inputMoveOptions = {"U", "U", "D", "U", "D", "D"};
		//when
		for (String inputMoveOption : inputMoveOptions) {
			bridgeGame.move(inputMoveOption);
		}

		//then
		assertTrue(bridgeGame.isCrossBridge());
	}

	@DisplayName("건너기 실패 테스트")
	@Test
	void crossFalseBridgeTest() {

		//given
		String[] inputMoveOptions = {"U", "U", "D", "U", "D", "U"};
		//when
		for (String inputMoveOption : inputMoveOptions) {
			bridgeGame.move(inputMoveOption);
		}

		//then
		assertFalse(bridgeGame.isCrossBridge());
	}

}
