package head4.notify.domain.notification.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.notification.controller.docs.NotifyControllerDocs;
import head4.notify.domain.notification.entity.dto.AddKeywordsRequest;
import head4.notify.domain.notification.entity.dto.NotifyCreateRequest;
import head4.notify.domain.notification.service.NotifyService;
import head4.notify.security.custom.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")

public class NotifyController implements NotifyControllerDocs {

    private final NotifyService notifyService;

    // TODO: 수정 필요
    @PostMapping("/create")
    public void create(@RequestBody NotifyCreateRequest request) {
        notifyService.create(request.getUserId(), request.getKeyword());
    }

    // TODO: 공지 키워드 리스트 형식으로 받기
    @PostMapping("/add/keywords")
    public BaseResponse<String> addKeywords(@RequestBody AddKeywordsRequest request,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        notifyService.addKeywords(userDetails.getUserId(), request);
        return BaseResponse.ok("success");
    }
}
