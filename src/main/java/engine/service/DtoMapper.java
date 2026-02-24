package engine.service;


import engine.dto.QuizCreateDTO;
import engine.dto.QuizSolveDTO;
import engine.model.QuizQuestion;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    private final ModelMapper modelMapper;

    public DtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    QuizQuestion createDTOtoEntity(QuizCreateDTO dto, String username) {
        QuizQuestion question = modelMapper.map(dto, QuizQuestion.class);
        question.setAuthor(username);
        return question;
    }

    QuizSolveDTO toDto(QuizQuestion entity) {
        return modelMapper.map(entity, QuizSolveDTO.class);
    }
}
