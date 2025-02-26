package com.github.percivalgebashe.assignment_5_application2.service.impl;

import com.github.percivalgebashe.assignment_5_application2.dto.AuthorDTO;
import com.github.percivalgebashe.assignment_5_application2.entity.Author;
import com.github.percivalgebashe.assignment_5_application2.exception.BadRequestException;
import com.github.percivalgebashe.assignment_5_application2.exception.ConflictException;
import com.github.percivalgebashe.assignment_5_application2.exception.NoContentFoundException;
import com.github.percivalgebashe.assignment_5_application2.exception.ResourceNotFoundException;
import com.github.percivalgebashe.assignment_5_application2.mapper.DTOMapper;
import com.github.percivalgebashe.assignment_5_application2.repository.AuthorRepository;
import com.github.percivalgebashe.assignment_5_application2.service.AuthorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthorById(String id) {
        if(null == id || id.isEmpty()){
            throw new BadRequestException("Id cannot be null or empty");
        }
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with ID %s not found", id)));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> getAllAuthorsById(List<String> authorIds) {
        if(null == authorIds || authorIds.isEmpty()){
            throw new BadRequestException("Ids cannot be null or empty");
        }

        return authorIds.stream()
                .map(id -> {
                    if (authorRepository.existsById(id)) {
                            Optional<Author> author = authorRepository.findById(id);
                            if (author.isPresent()) {
                                return author.get();
                            }
                    }
                    throw new ResourceNotFoundException(String.format("Author with ID %s not found", id));
                }).toList();
    }

    @Override
    public Author addAuthor(AuthorDTO authorDTO) {
        validateAuthorDTO(authorDTO);
        if (authorRepository.existsById(authorDTO.getId())) {
            throw new ConflictException("Author with ID %s already exists");
        }
        return authorRepository.save(DTOMapper.toAuthorEntity(authorDTO));
    }

    @Override
    public Author updateAuthor(AuthorDTO authorDTO) {
        validateAuthorDTO(authorDTO);

        return authorRepository.findById(authorDTO.getId())
                .map(author -> {
                    updateAuthor(authorDTO, author);
                    return authorRepository.save(author);
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with ID %s not found", authorDTO.getId())));
    }

    @Override
    public List<Author> updateAuthors(List<AuthorDTO> authors) {
        if(null == authors || authors.isEmpty()){
            throw new BadRequestException("Ids cannot be null or empty");
        }

        return authors.stream()
                .map(authorDTO -> {
                    if(null != authorDTO.getId() && !authorDTO.getId().isEmpty()){
                        if(authorRepository.existsById(authorDTO.getId())){
                           return authorRepository.findById(authorDTO.getId())
                                   .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with ID %s not found", authorDTO.getId())));
                        }
                    }
                    throw new BadRequestException("Ids cannot be null or empty");
                }).toList();
    }

    @Override
    public void deleteAuthor(String id) {
        if(null == id || id.isEmpty()){
            throw new BadRequestException("Id cannot be null or empty");
        }
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        }
        throw new ResourceNotFoundException(String.format("Author with ID %s not found", id));
    }

    @Override
    public void deleteAuthors(List<String> authorIds) {
        if(null == authorIds || authorIds.isEmpty()){
            throw new BadRequestException("Ids cannot be null or empty");
        }

        authorIds.forEach(id -> {
            if (authorRepository.existsById(id)) {
                authorRepository.deleteById(id);
            }
            throw new ResourceNotFoundException(String.format("Author with ID %s not found", id));
        });
    }

    private void validateAuthorDTO(AuthorDTO authorDTO) {
        if(null == authorDTO){
            throw new BadRequestException("Author cannot be null");
        }
        if (null == authorDTO.getId() || authorDTO.getId().isEmpty()) {
            throw new BadRequestException("Id cannot be null or empty");
        }
        if(null == authorDTO.getName() || authorDTO.getName().isEmpty()){
            throw new BadRequestException("Name cannot be null or empty");
        }
    }

    private void updateAuthor(AuthorDTO authorDTO, Author author) {
        author.setName(authorDTO.getName());
    }
}
