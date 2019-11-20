package com.artyg.noteapp.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

import java.util.Date;

import static com.artyg.noteapp.config.Globals.DEFAULT_PRIORITY;

@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "creation_date")
    @Nullable
    private Date creationDate;

    @Column(name = "text")
    @Nullable
    private String text;

    @Column(name = "priority")
    @NotNull
    private String priority;


    public Note() {
        priority = DEFAULT_PRIORITY;
    }

    @NotNull
    public String getPriority() {
        return priority;
    }

    public void setPriority(@NotNull String priority) {
        this.priority = priority;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    @Nullable
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(@Nullable Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
