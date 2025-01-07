package head4.notify.notification.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NotifyId implements Serializable {
    private Long univId; // MapsId("univId") 로 매핑

    @Column(name = "keyword")
    private String keyword;
}
