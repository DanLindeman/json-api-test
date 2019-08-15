package com.example.jsonapitest.repositories

import com.example.jsonapitest.models.Project
import com.example.jsonapitest.models.ProjectList
import com.example.jsonapitest.models.ProjectListLinks
import com.example.jsonapitest.models.ProjectListMeta
import io.crnk.core.queryspec.QuerySpec
import io.crnk.core.repository.ResourceRepositoryBase
import org.springframework.stereotype.Component
import java.util.HashMap
import java.util.concurrent.atomic.AtomicLong

@Component
class ProjectRepository : ResourceRepositoryBase<Project, Long>(Project::class.java) {

    private val projects = HashMap<Long, Project>()

    @Synchronized
    override fun delete(id: Long?) {
        projects.remove(id)
    }

    @Synchronized
    override fun <S : Project> save(project: S): S {
        if (project.id == null) {
            project.id = ID_GENERATOR.getAndIncrement()
        }
        projects.put(project.id!!, project)
        return project
    }

    @Synchronized
    override fun findAll(querySpec: QuerySpec): ProjectList {
        val list = ProjectList()
        list.meta = ProjectListMeta()
        list.links = ProjectListLinks()
        querySpec.apply(projects.values, list)
        return list
    }

    companion object {
        private val ID_GENERATOR = AtomicLong(124)
    }

}
