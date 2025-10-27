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

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    public User findByUsername(String username);

    public List<User> findByActiveIsTrue();

    public List<User> findByActiveIsFalse();

    @Query("SELECT u FROM User u WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:department IS NULL OR :department = '' OR u.department = :department) AND " +
            // НОВАЯ ЛОГИКА: Если showInactive=TRUE, то u.active неважно.
            // Если showInactive=FALSE, то должно выполняться u.active=TRUE.
            "(:showInactive = TRUE OR u.active = TRUE)")
    Page<User> findFilteredUsers(@Param("search") String search,
                                 @Param("department") String department,
                                 @Param("showInactive") boolean showInactive, // <--- НОВЫЙ ПАРАМЕТР
                                 Pageable pageable);

}
