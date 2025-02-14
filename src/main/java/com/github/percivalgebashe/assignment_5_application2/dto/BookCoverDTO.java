package com.github.percivalgebashe.assignment_5_application2.dto;

import com.github.percivalgebashe.assignment_5_application2.entity.BookCover;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCoverDTO {

    private Long id;

    private Long book_id;

    private String image_path;

    public BookCover toEntity() {
        BookCover bookCover = new BookCover();
        bookCover.setBookId(book_id);
        bookCover.setImagePath(image_path);
        return bookCover;
    }
}
