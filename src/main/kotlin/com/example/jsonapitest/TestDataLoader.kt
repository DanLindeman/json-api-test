package com.example.jsonapitest

import com.example.jsonapitest.models.Project
import com.example.jsonapitest.models.Task
import com.example.jsonapitest.repositories.ProjectRepository
import com.example.jsonapitest.repositories.TaskRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@Configuration
class TestDataLoader {
    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private lateinit var projectRepository: ProjectRepository

    @Autowired
    private lateinit var taskRepository: TaskRepository

    @PostConstruct
    fun setup() {
        val project121 = projectRepository.save(Project(121L, "Great Project"))
        val project122 = projectRepository.save(Project(122L, "Crnk Project"))
        val project123 = projectRepository.save(Project(123L, "Some Project"))
        projectRepository.save(Project(124L, "JSON API Project"))

        var task = Task(1L, "Create tasks")
        task.setProject(project121)
        taskRepository.save(task)
        task = Task(2L, "Make coffee")
        task.setProject(project122)
        taskRepository.save(task)
        task = Task(3L, "Do things")
        task.setProject(project123)
        taskRepository.save(task)
    }

    @PostConstruct
    fun configureJackson() {
        objectMapper!!.enable(SerializationFeature.INDENT_OUTPUT)
    }
}
