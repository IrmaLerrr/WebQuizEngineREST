package engine.repository;

import engine.model.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuizResultRepository extends CrudRepository<QuizResult, Integer> {
    @Query("SELECT r FROM QuizResult r WHERE r.author = :author AND r.success = TRUE")
    Page<QuizResult> findByAuthor(@Param("author") String author, Pageable pageable);
}
