package head4.notify.domain.article.repository;

import head4.notify.domain.article.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query("select u from University u " +
            "where u.name like concat(:name, '%') " +
            "order by u.campus asc ")
    List<University> findUniversitiesByName(@Param("name") String name);
}
