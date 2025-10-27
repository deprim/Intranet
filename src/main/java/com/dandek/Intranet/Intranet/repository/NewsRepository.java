package com.dandek.Intranet.Intranet.repository;

import com.dandek.Intranet.Intranet.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository  extends JpaRepository<News, Long> {
}
