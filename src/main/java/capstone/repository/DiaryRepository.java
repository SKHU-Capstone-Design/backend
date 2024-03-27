package capstone.repository;

import capstone.domain.Diary;
import capstone.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT mp FROM Diary mp WHERE mp.user = :user AND DATE(mp.date) = :date")
    List<Diary> findByUserAndCreatedAt(@Param("user") User user, @Param("date") LocalDate date);
}
