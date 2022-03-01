package am.task.model.dto.book;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookPreviewDto {
    private String foolName;
    private Long userId;
}
