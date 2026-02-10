package engine;

import engine.dto.QuizAnswer;
import engine.dto.QuizQuestionDTO;
import engine.dto.QuizResult;
import engine.model.QuizQuestion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
public class QuizController {
    @Autowired
    QuizService quizService;

    @GetMapping("api/health")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/quizzes")
    public ResponseEntity<QuizQuestion> createQuiz(@RequestBody @Valid QuizQuestion quizQuestion) {
        return ResponseEntity.ok().body(quizService.addQuizQuestion(quizQuestion));
    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<QuizQuestionDTO> getQuiz(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(quizService.getQuizQuestionDTO(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("api/quizzes")
    public ResponseEntity<List<QuizQuestionDTO>> getAllQuizzes() {
        return ResponseEntity.ok().body(quizService.getAllQuizQuestion());
    }

    @PostMapping("api/quizzes/{id}/solve")
    public ResponseEntity<QuizResult> solveQuiz(@PathVariable("id") Integer id, @RequestBody QuizAnswer answer) {
        try {
            QuizQuestion question = quizService.getQuizQuestion(id);
            Set<Integer> set1 = new HashSet<>(question.getAnswer());
            Set<Integer> set2 = new HashSet<>(answer.answer());
            if (set1.equals(set2)) return ResponseEntity.ok().body(new QuizResult(true));
            else return ResponseEntity.ok().body(new QuizResult(false));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

