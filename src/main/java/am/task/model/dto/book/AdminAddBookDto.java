package am.task.model.dto.book;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddBookDto {
    @NotNull
    private String foolName;
    private Long userId;
}
