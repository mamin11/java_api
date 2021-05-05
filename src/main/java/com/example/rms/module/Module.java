package com.example.rms.module;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table
public class Module {

    @Id
    @SequenceGenerator(
            name = "module_sequence",
            sequenceName = "module_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "module_sequence"
    )

    private Long id;
    private String title;
    private String course;
    private String moduleContentLink;
    private LocalDate startDate;

    public Module() {
    }

    public Module(String title, String course, String moduleContentLink, LocalDate startDate) {
        this.title = title;
        this.course = course;
        this.moduleContentLink = moduleContentLink;
        this.startDate = startDate;
    }

    public Module(Long id, String title, String course, String moduleContentLink, LocalDate startDate) {
        this.id = id;
        this.title = title;
        this.course = course;
        this.moduleContentLink = moduleContentLink;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCourse() {
        return course;
    }

    public Optional<String> getModuleContentLink() {
        return Optional.ofNullable(moduleContentLink) ;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", course='" + course + '\'' +
                ", moduleContentLink='" + moduleContentLink + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setModuleContentLink(String moduleContentLink) {
        this.moduleContentLink = moduleContentLink;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
