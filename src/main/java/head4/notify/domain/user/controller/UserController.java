package head4.notify.domain.user.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User API",  description = "사용자 관련 기능 API")
public class UserController {

    private final UserService userService;

    // TODO: 대학교 정보 저장
    @PatchMapping("/univ/{name}")
    public BaseResponse<String> univ(@PathVariable("name") String name) {
        userService.patchUnivId(1L, name);
        return BaseResponse.ok("success");
    }
}
