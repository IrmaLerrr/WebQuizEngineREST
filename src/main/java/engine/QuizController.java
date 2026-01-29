package engine;

import engine.dto.QuizAnswer;
import engine.dto.QuizQuestionRequest;
import engine.dto.QuizQuestionResponse;
import engine.dto.QuizResult;
import engine.model.QuizQuestion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
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
    public ResponseEntity<QuizQuestion> createQuiz(@RequestBody @Valid QuizQuestionRequest request) {
        return ResponseEntity.ok().body(quizService.addQuizQuestion(request));
    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<QuizQuestionResponse> getQuiz(@PathVariable int id) {
        QuizQuestion question = quizService.getQuizQuestion(id);
        if (question == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(new QuizQuestionResponse(question));
    }

    @GetMapping("api/quizzes")
    public ResponseEntity<List<QuizQuestion>> getAllQuizzes() {
        return ResponseEntity.ok().body(quizService.getAllQuizQuestion());
    }

    @PostMapping("api/quizzes/{id}/solve")
    public ResponseEntity<QuizResult> solveQuiz(@PathVariable("id") Integer id, @RequestBody QuizAnswer answer) {
        QuizQuestion question = quizService.getQuizQuestion(id);
        if (question == null) return ResponseEntity.notFound().build();
        else {
            Set<Integer> set1 = new HashSet<>(question.answer());
            Set<Integer> set2 = new HashSet<>(answer.answer());
            if (set1.equals(set2)) return ResponseEntity.ok().body(new QuizResult(true));
            else return ResponseEntity.ok().body(new QuizResult(false));
        }
    }
}
