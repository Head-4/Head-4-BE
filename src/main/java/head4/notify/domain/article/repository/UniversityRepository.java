package head4.notify.domain.article.repository;

import head4.notify.domain.article.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query("select u from University u " +
            "where u.name like concat(:name, '%') " +
            "order by u.campus asc ")
    List<University> findUniversitiesByName(@Param("name") String name);


    @Query("select u.id from University u " +
            "where u.name = :name")
    Optional<Integer> getUnivId(@Param("name") String name);
}
