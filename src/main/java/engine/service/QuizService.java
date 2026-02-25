package engine.service;

import engine.dto.QuizAnswer;
import engine.dto.QuizCreateDTO;
import engine.dto.QuizResultDTO;
import engine.dto.QuizSolveDTO;
import engine.model.QuizQuestion;
import engine.model.QuizResult;
import engine.repository.QuizQuestionRepository;
import engine.repository.QuizResultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class QuizService {
    private final QuizQuestionRepository repository;
    private final QuizResultRepository resultRepository;
    private final DtoMapper mapper;

    public QuizService(QuizQuestionRepository repository, QuizResultRepository resultRepository, DtoMapper mapper) {
        this.repository = repository;
        this.resultRepository = resultRepository;
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
    public boolean solveQuizQuestion(Integer id, String author, QuizAnswer answer) throws NoSuchElementException {
        QuizQuestion question = repository.findById(id).orElseThrow(NoSuchElementException::new);
        Set<Integer> set1 = new HashSet<>(question.getAnswer());
        Set<Integer> set2 = new HashSet<>(answer.answer());
        boolean success = set1.equals(set2);
        QuizResult quizResult = new QuizResult(id, success, author, LocalDateTime.now());
        resultRepository.save(quizResult);
        return success;
    }

    public void deleteQuizQuestion(Integer id, String username) throws NoSuchElementException, AccessDeniedException {
        QuizQuestion question = repository.findById(id).orElseThrow(NoSuchElementException::new);
        if (!Objects.equals(question.getAuthor(), username))
            throw new AccessDeniedException("Only the quiz author can delete a quiz.");
        repository.deleteById(id);
    }

    public Page<QuizSolveDTO> getAllQuizQuestion(int page) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public Page<QuizResultDTO> getQuizResult(int page, String author) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC,"completedAt");
        return resultRepository.findByAuthor(author, pageable).map(mapper::toDto);
    }

}
