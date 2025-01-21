package head4.notify.domain.user.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.user.controller.docs.UserControllerDocs;
import head4.notify.domain.user.dto.PushLogPageRes;
import head4.notify.domain.user.dto.UserKeywordsRes;
import head4.notify.domain.user.service.PushDetailService;
import head4.notify.domain.user.service.UserService;
import head4.notify.security.custom.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    private final PushDetailService pushDetailService;

    // TODO: 대학교 정보 저장
    @PatchMapping("/univ/{name}")
    public BaseResponse<String> patchUniv(@PathVariable("name") String name,
                                          @AuthenticationPrincipal CustomUserDetails userDetails,
                                          HttpServletResponse response) {
        userService.patchUnivId(userDetails.getUserId(), name, response);
        return BaseResponse.ok("success");
    }

    //TODO: 사용자 fcm token 받기
    @PatchMapping("/fcm/{token}")
    public BaseResponse<String> patchToken(@PathVariable("token") String token,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.patchFcmToken(userDetails.getUserId(), token);
        return BaseResponse.ok("success");
    }

    // TODO: 알림 허용 여부 변경
    @PatchMapping("/notify/{allow}")
    public BaseResponse<String> patchAllow(@PathVariable("allow") Boolean allow,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.patchAllow(userDetails.getUserId(), allow);
        return BaseResponse.ok("success");
    }

    // TODO: 사용자가 받은 알림 목록 페이징 구현
    @GetMapping("/notify/page/{cursor}")
    public BaseResponse<PushLogPageRes> showPushLogs(@PathVariable("cursor") Long cursor,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        PushLogPageRes page = pushDetailService.getArticleList(cursor, userDetails.getUserId());
        return BaseResponse.ok(page);
    }

    // TODO: 키워드 추가하기
    @PostMapping("/add/keyword/{keyword}")
    public BaseResponse<String> addKeyword(@PathVariable("keyword") String keyword,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.addKeyword(userDetails.getUserId(), keyword);
        return BaseResponse.ok("success");
    }

    // TODO: 사용자가 추가한 키워드 조회
    @GetMapping("/keywords")
    public BaseResponse<List<UserKeywordsRes>> userKeywords(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<UserKeywordsRes> userKeywords = userService.getKeywords(userDetails.getUserId());
        return BaseResponse.ok(userKeywords);
    }

    // TODO: 사용자가 추가한 키워드 삭제
    @DeleteMapping("/delete/keyword/{notifyId}")
    public BaseResponse<String> deleteKeyword(@PathVariable("notifyId") Long notifyId,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteKeyword(userDetails.getUserId(), notifyId);
        return BaseResponse.ok("success");
    }
}
