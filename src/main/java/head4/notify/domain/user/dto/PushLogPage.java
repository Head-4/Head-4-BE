package head4.notify.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PushLogPage {
    private Long curosr;

    private Boolean hasNext;

    private List<PushLog> pushLogs = new ArrayList<>();
}
