package engine.controller;

import engine.dto.QuizAnswer;
import engine.dto.QuizCreateDTO;
import engine.dto.QuizResultDTO;
import engine.dto.QuizSolveDTO;
import engine.model.QuizQuestion;
import engine.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.NoSuchElementException;

@RestController
public class QuizController {
    @Autowired
    QuizService quizService;

    @GetMapping("api/health")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/quizzes")
    public ResponseEntity<QuizQuestion> createQuiz(@AuthenticationPrincipal UserDetails details,
                                                   @RequestBody @Valid QuizCreateDTO quizQuestion) {
        String username = details.getUsername();
        return ResponseEntity.ok().body(quizService.addQuizQuestion(quizQuestion, username));
    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<QuizSolveDTO> getQuiz(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(quizService.getQuizQuestionDTO(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("api/quizzes")
    public ResponseEntity<Page<QuizSolveDTO>> getAllQuizzes(@RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok().body(quizService.getAllQuizQuestion(page));
    }

    @GetMapping("api/quizzes/completed")
    public ResponseEntity<Page<QuizResultDTO>> getCompletedQuizzes(@AuthenticationPrincipal UserDetails details,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page) {
        String username = details.getUsername();
        return ResponseEntity.ok().body(quizService.getQuizResult(page, username));
    }

    @PostMapping("api/quizzes/{id}/solve")
    public ResponseEntity<SolveResponse> solveQuiz(@AuthenticationPrincipal UserDetails details,
                                                   @PathVariable("id") Integer id,
                                                   @RequestBody QuizAnswer answer) {
        try {
            String username = details.getUsername();
            return ResponseEntity.ok().body(new SolveResponse(quizService.solveQuizQuestion(id, username, answer)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public record SolveResponse(boolean success, String feedback) {
        public SolveResponse(boolean success) {
            this(success,
                    success ? "Congratulations, you're right!" :
                            "Wrong answer! Please, try again.");
        }
    }

    @DeleteMapping("api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@AuthenticationPrincipal UserDetails details, @PathVariable("id") Integer id) {
        try {
            String username = details.getUsername();
            quizService.deleteQuizQuestion(id, username);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).build();
        }
    }
}

