package com.example.jsonapitest.repositories

import com.example.jsonapitest.models.Task
import io.crnk.core.repository.ResourceRepository
import io.crnk.core.queryspec.QuerySpec
import io.crnk.core.resource.list.ResourceList
import io.crnk.core.exception.ResourceNotFoundException
import io.crnk.core.exception.BadRequestException
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.ConcurrentHashMap
import io.crnk.core.repository.ResourceRepositoryBase
import org.springframework.stereotype.Component
import java.util.UUID
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContext



@Component
class TaskRepository : ResourceRepositoryBase<Task, Long>(Task::class.java) {

    override fun <S : Task> save(entity: S): S {
        if (entity.id == null) {
            entity.id = ID_GENERATOR.getAndIncrement()
        }
        tasks[entity.id!!] = entity
        return entity
    }

    override fun <S : Task> create(entity: S): S {
        if (entity.id != null && tasks.containsKey(entity.id!!)) {
            throw BadRequestException("Task already exists")
        }
        return save(entity)
    }

    override fun getResourceClass(): Class<Task> {
        return Task::class.java
    }

    override fun findOne(taskId: Long?, querySpec: QuerySpec): Task {
        return tasks[taskId] ?: throw ResourceNotFoundException("Task not found!")
    }

    override fun findAll(querySpec: QuerySpec): ResourceList<Task> {
        /**
         * Access the user object within the repositories
         */
        val context = SecurityContextHolder.getContext()
        println("USER '${context.authentication.name}' queried all tasks")
        return querySpec.apply(tasks.values)
    }


    override fun delete(taskId: Long?) {
        tasks.remove(taskId)
    }

    companion object {

        // for simplicity we make use of static, should not be used in real applications
        private val tasks = ConcurrentHashMap<Long, Task>()

        private val ID_GENERATOR = AtomicLong(4)
    }
}
