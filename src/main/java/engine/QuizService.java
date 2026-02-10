package engine;

import engine.dto.QuizQuestionDTO;
import engine.model.QuizQuestion;
import engine.repository.QuizQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;


@Service
public class QuizService {
    private final QuizQuestionRepository repository;
    private final DtoMapper mapper;

    public QuizService(QuizQuestionRepository repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public QuizQuestion addQuizQuestion(QuizQuestion quizQuestion) {
        return repository.save(quizQuestion);
    }

    public QuizQuestionDTO getQuizQuestionDTO(Integer id) throws NoSuchElementException{
        return mapper.toDto(getQuizQuestion(id));
    }

    public QuizQuestion getQuizQuestion(Integer id) throws NoSuchElementException{
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<QuizQuestionDTO> getAllQuizQuestion() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
