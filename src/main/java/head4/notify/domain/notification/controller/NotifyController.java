package head4.notify.domain.notification.controller;

import head4.notify.domain.notification.entity.dto.NotifyCreateRequest;
import head4.notify.domain.notification.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
public class NotifyController {

    private final NotifyService notifyService;

    // TODO: 수정 필요
    @PostMapping("/create")
    public void create(@RequestBody NotifyCreateRequest request) {
        notifyService.create(request.getUserId(), request.getKeyword());
    }
}
