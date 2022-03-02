package am.task.model.dto.book;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookListPreviewDto {
    private Long id;
    private String foolName;
    private Long userId;
}
