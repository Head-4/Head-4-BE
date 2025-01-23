package head4.notify.exceoption;

import head4.notify.customResponse.BaseResponse;
import head4.notify.discord.DiscordSender;
import head4.notify.domain.article.controller.ArticleController;
import head4.notify.domain.notification.controller.NotifyController;
import head4.notify.domain.user.controller.UserController;
import head4.notify.oauth.controller.LoginController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@RestControllerAdvice(
        annotations = {RestController.class},
        basePackageClasses = {
                LoginController.class,
                ArticleController.class,
                NotifyController.class,
                UserController.class
        }
)
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final DiscordSender discordSender;

    // 존재하지 않는 요청에 대한 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public BaseResponse<?> handleNoPageFoundException(Exception e, WebRequest request) {
        discordSender.sendWebhookMessage(e.toString(), e.getMessage(), request);
        log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
        return BaseResponse.fail(new CustomException(ErrorCode.NOT_FOUND_END_POINT));
    }

    // 커스텀 예외
    @ExceptionHandler(value = {CustomException.class})
    public BaseResponse<?> handleCustomException(CustomException e, WebRequest request) {
        discordSender.sendWebhookMessage(e.getErrorCode().name(), e.getMessage(), request);
        log.error("handleCustomException() in GlobalExceptionHandler throw CustomException : {}", e.getMessage());
        return BaseResponse.fail(e);
    }

    // 기본 예외
    @ExceptionHandler(value = {Exception.class})
    public BaseResponse<?> handleException(Exception e, WebRequest request) {
        discordSender.sendWebhookMessage(e.toString(), e.getMessage(), request);
        log.error("handleException() in GlobalExceptionHandler throw Exception : {}", e.getMessage());
        e.printStackTrace();
        return BaseResponse.fail(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
    }


}
