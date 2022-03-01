package am.task.model.dto.book;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditBookDto {
    @NotNull
    private String foolName;
    @NotNull
    private Long id;
}
