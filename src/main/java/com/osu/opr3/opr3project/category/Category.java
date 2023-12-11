package com.osu.opr3.opr3project.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osu.opr3.opr3project.task.Task;
import com.osu.opr3.opr3project.user.User;
import com.osu.opr3.opr3project.validation.ColorFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private String description;
    @ColorFormat
    private String color;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"category"})
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"categories"})
    private User user;
}
