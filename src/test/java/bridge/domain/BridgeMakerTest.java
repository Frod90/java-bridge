package bridge.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import bridge.constant.BridgeMoveOption;
import camp.nextstep.edu.missionutils.Randoms;

public class BridgeMakerTest {

	private static BridgeNumberGenerator bridgeExceedNumberGenerator;
	private static BridgeNumberGenerator bridgeUnderNumberGenerator;
	private static BridgeNumberGenerator bridgeRandomNumberGenerator;

	@BeforeAll
	public static void setUp() {

		bridgeExceedNumberGenerator = () -> Randoms.pickNumberInRange(2, 10);
		bridgeUnderNumberGenerator = () -> Randoms.pickNumberInRange(-10, -1);
		bridgeRandomNumberGenerator = new BridgeRandomNumberGenerator();

	}

	@DisplayName("초과 숫자로 다리 만들기 테스트")
	@Test
	void makeBridgeByExceedNumberTest() {

		//given
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeExceedNumberGenerator);
		//when
		List<String> bridge = bridgeMaker.makeBridge(5);
		//then
		for (String moveOptionSignature : bridge) {
			assertEquals(moveOptionSignature, BridgeMoveOption.FAIL.getSignatureOption());
		}

	}

	@DisplayName("음수로 다리 만들기 테스트")
	@Test
	void makeBridgeByUnderNumberTest() {

		//given
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeUnderNumberGenerator);
		//when
		List<String> bridge = bridgeMaker.makeBridge(10);
		//then
		for (String moveOptionSignature : bridge) {
			assertEquals(moveOptionSignature, BridgeMoveOption.FAIL.getSignatureOption());
		}

	}

	@DisplayName("범위 내의 수로 다리 만들기 테스트")
	@Test
	void makeBridgeInRangeNumberTest() {

		//given
		BridgeMaker bridgeMaker = new BridgeMaker(bridgeRandomNumberGenerator);
		//when
		List<String> bridge = bridgeMaker.makeBridge(10);
		//then
		for (String moveOptionSignature : bridge) {
			assertThat(
				moveOptionSignature.equals(BridgeMoveOption.UP.getSignatureOption())
					|| moveOptionSignature.equals(BridgeMoveOption.DOWN.getSignatureOption())
			).isEqualTo(true);

		}
	}

}
