package com.dandek.Intranet.Intranet.repository;


import com.dandek.Intranet.Intranet.model.Events;
import jdk.jfr.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {




}
