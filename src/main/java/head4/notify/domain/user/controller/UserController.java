package head4.notify.domain.user.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.user.controller.docs.UserControllerDocs;
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
public class UserController implements UserControllerDocs {

    private final UserService userService;

    // TODO: 대학교 정보 저장
    @PatchMapping("/univ/{name}")
    public BaseResponse<String> patchUniv(@PathVariable("name") String name) {
        userService.patchUnivId(1L, name);
        return BaseResponse.ok("success");
    }
}
