package head4.notify.exceoption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Test Error
    TEST_ERROR(10000, HttpStatus.BAD_REQUEST, "테스트 에러입니다."),
    // 404 Not Found
    NOT_FOUND_END_POINT(40400, HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),
    USER_NOT_FOUND(40401, HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    // Duplicate Data Error
    DUPLICATE_DATA_ERROR(40900, HttpStatus.CONFLICT, "중복 데이터 오류입니다."),
    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    // JSON 파싱 오류
    JSON_PARSING_ERROR(50010, HttpStatus.INTERNAL_SERVER_ERROR, "JSON 파싱 오류입니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
