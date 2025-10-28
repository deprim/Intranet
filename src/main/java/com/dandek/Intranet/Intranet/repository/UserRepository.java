package com.dandek.Intranet.Intranet.repository;

import com.dandek.Intranet.Intranet.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    public Optional<User> findByUsername(String username);

    public List<User> findByActiveIsTrue();

    public List<User> findByActiveIsFalse();

    public List<User> findByOutOfOfficeIsTrue();



    @Query("SELECT u FROM User u WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            // 🚀 КЛЮЧЕВОЕ ИЗМЕНЕНИЕ: Используем u.department.id для сравнения с Integer ID
            "(:departmentId IS NULL OR u.department.id = :departmentId) AND " +
            "(:showInactive = TRUE OR u.active = TRUE)")
    Page<User> findFilteredUsers(@Param("search") String search,
                                 @Param("departmentId") Integer departmentId, // ⬅️ ПАРАМЕТР Integer
                                 @Param("showInactive") boolean showInactive,
                                 Pageable pageable);

}
