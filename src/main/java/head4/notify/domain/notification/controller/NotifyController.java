package head4.notify.domain.notification.controller;

import head4.notify.customResponse.BaseResponse;
import head4.notify.domain.notification.entity.dto.AddKeywordsRequest;
import head4.notify.domain.notification.entity.dto.NotifyCreateRequest;
import head4.notify.domain.notification.service.NotifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
@Tag(name = "Notify API",  description = "알림 관련 기능 API")
public class NotifyController {

    private final NotifyService notifyService;

    // TODO: 수정 필요
    @PostMapping("/create")
    public void create(@RequestBody NotifyCreateRequest request) {
        notifyService.create(request.getUserId(), request.getKeyword());
    }

    // TODO: 공지 키워드 리스트 형식으로 받기
    @PostMapping("/add/keywords")
    @Operation(summary = "키워드 추가", description = "사용자가 지정한 키워드들을 등록하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json")),
    })
    public BaseResponse<String> addKeywords(@RequestBody AddKeywordsRequest request) {
        notifyService.addKeywords(1L, request);
        return BaseResponse.ok("success");
    }

    // TODO: 사용자가 받은 알림 목록 커서 페이징 구현
}
