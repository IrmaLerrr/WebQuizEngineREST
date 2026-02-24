package engine.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizCreateDTO {
    @NotEmpty
    String title;

    @NotEmpty
    String text;

    @NotEmpty
    @Size(min = 2)
    List<String> options;

    List<Integer> answer;
}


