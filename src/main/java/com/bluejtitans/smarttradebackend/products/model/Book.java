package com.bluejtitans.smarttradebackend.products.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Book extends Product {
    @Column
    private String ISBN;

    public Book(String name, String description, String dataSheet, byte[] photo, String isbn) {
        super(
            name,
            description,
            dataSheet,
            photo,
                "book"
        );
        this.ISBN = isbn;
    }

    public Book() {

    }
}
