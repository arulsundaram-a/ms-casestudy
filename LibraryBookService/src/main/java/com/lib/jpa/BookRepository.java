package com.lib.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lib.model.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}