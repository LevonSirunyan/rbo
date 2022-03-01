package am.task.model.dto.book;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBookDto {
    @NotNull
    private String foolName;
}
