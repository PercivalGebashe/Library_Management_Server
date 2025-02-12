package com.github.percivalgebashe.assignment_5_application2.repository;

import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCoverRepository extends JpaRepository<BookCover, Long> {
}
