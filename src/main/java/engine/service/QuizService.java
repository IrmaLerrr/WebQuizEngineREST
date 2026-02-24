package engine.service;

import engine.dto.QuizCreateDTO;
import engine.dto.QuizSolveDTO;
import engine.model.QuizQuestion;
import engine.repository.QuizQuestionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public QuizQuestion addQuizQuestion(QuizCreateDTO quizQuestion, String username) {
        return repository.save(mapper.createDTOtoEntity(quizQuestion, username));
    }

    public QuizSolveDTO getQuizQuestionDTO(Integer id) throws NoSuchElementException {
        return mapper.toDto(getQuizQuestion(id));
    }

    public QuizQuestion getQuizQuestion(Integer id) throws NoSuchElementException {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void deleteQuizQuestion(Integer id, String username) throws NoSuchElementException, AccessDeniedException {
        QuizQuestion question = repository.findById(id).orElseThrow(NoSuchElementException::new);
        if (!Objects.equals(question.getAuthor(), username))
            throw new AccessDeniedException("Only the quiz author can delete a quiz.");
        repository.deleteById(id);
    }

    public List<QuizSolveDTO> getAllQuizQuestion() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
