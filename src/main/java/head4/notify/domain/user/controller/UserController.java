package head4.notify.domain.user.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.user.controller.docs.UserControllerDocs;
import head4.notify.domain.user.dto.PushLogPageRes;
import head4.notify.domain.user.dto.UserKeywordsRes;
import head4.notify.domain.user.service.PushDetailService;
import head4.notify.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public BaseResponse<String> patchUniv(@PathVariable("name") String name) {
        userService.patchUnivId(1L, name);
        return BaseResponse.ok("success");
    }

    //TODO: 사용자 fcm token 받기
    @PatchMapping("/fcm/{token}")
    public BaseResponse<String> patchToken(@PathVariable("token") String token) {
        userService.patchFcmToken(1L, token);
        return BaseResponse.ok("success");
    }

    @PatchMapping("/notify/{allow}")
    public BaseResponse<String> patchAllow(@PathVariable("allow") Boolean allow) {
        userService.patchAllow(1L, allow);
        return BaseResponse.ok("success");
    }

    // TODO: 사용자가 받은 알림 목록 페이징 구현
    @GetMapping("/notify/log/{cursor}")
    public BaseResponse<PushLogPageRes> showPushLogs(@PathVariable("cursor") Long cursor) {
        PushLogPageRes page = pushDetailService.getArticleList(cursor, 1L);
        return BaseResponse.ok(page);
    }

    // TODO: 사용자가 추가한 키워드 조회
    @GetMapping("/keywords")
    public BaseResponse<List<UserKeywordsRes>> userKeywords() {
        List<UserKeywordsRes> userKeywords = userService.getKeywords(1L);
        return BaseResponse.ok(userKeywords);
    }

}
