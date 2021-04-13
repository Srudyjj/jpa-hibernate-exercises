package com.bobocode.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * todo:
 * - implement no argument constructor
 * - implement getters and setters
 * - make a setter for field {@link Photo#comments} {@code private}
 * - implement equals() and hashCode() based on identifier field
 * <p>
 * - configure JPA entity
 * - specify table name: "photo"
 * - configure auto generated identifier
 * - configure not nullable and unique column: url
 * <p>
 * - initialize field comments
 * - map relation between Photo and PhotoComment on the child side
 * - implement helper methods {@link Photo#addComment(PhotoComment)} and {@link Photo#removeComment(PhotoComment)}
 * - enable cascade type {@link javax.persistence.CascadeType#ALL} for field {@link Photo#comments}
 * - enable orphan removal
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String url;
    private String description;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoComment> comments = new ArrayList<>();

    public void addComment(PhotoComment comment) {
        this.getComments().add(comment);
        comment.setPhoto(this);
    }

    public void removeComment(PhotoComment comment) {
        this.getComments().remove(comment);
//        comment.setPhoto(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id.equals(photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
