package head4.notify.customResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import head4.notify.exceoption.CustomException;
import head4.notify.exceoption.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
public record BaseResponse<T>(
        HttpStatus httpStatus,
        boolean success,
        @Nullable T data,
        @Nullable ExceptionDto error
) {
        public static <T> BaseResponse<T> ok(@Nullable final T data) {
                return new BaseResponse<>(HttpStatus.OK, true, data, null);
        }

        public static <T> BaseResponse<T> created(@Nullable final T data) {
                return new BaseResponse<>(HttpStatus.CREATED, true, data, null);
        }

        public static <T> BaseResponse<T> fail(final CustomException e) {
                return new BaseResponse<>(e.getErrorCode().getHttpStatus(), false, null, ExceptionDto.of(e.getErrorCode()));
        }
}