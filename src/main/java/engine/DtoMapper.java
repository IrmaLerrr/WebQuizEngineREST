package engine;

import engine.dto.QuizQuestionDTO;
import engine.model.QuizQuestion;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    private final ModelMapper modelMapper;

    public DtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    QuizQuestion toEntity(QuizQuestionDTO dto) {
//        return modelMapper.map(dto, QuizQuestion.class);
//    }

    QuizQuestionDTO toDto(QuizQuestion entity) {
        return modelMapper.map(entity, QuizQuestionDTO.class);
    }
}
