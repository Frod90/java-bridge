package bridge.constant;

public class ErrorMessage {

	private static final String ERROR_INDICATION = "[ERROR]";
	public static final String MOVE_OPTION_SIGNATURE = ERROR_INDICATION + " 지정된 다리의 이동 옵션 표현이 아닙니다.";
	public static final String INPUT_MOVE_OPTION_SIGNATURE = ERROR_INDICATION + " 지정된 이동 명령어(U, D)만 사용 가능합니다.";
	public static final String  NUMERIC_INPUT = ERROR_INDICATION + " 숫자만 입력 가능합니다.";
	public static final String  BRIDGE_SIZE_INPUT = ERROR_INDICATION + " 다리의 길이는 3이상 20이하만 가능합니다.";
	public static final String  GAME_COMMEND_INPUT = ERROR_INDICATION + " 재시작(R), 종료(Q) 명령어만 입력 가능합니다.";

}
