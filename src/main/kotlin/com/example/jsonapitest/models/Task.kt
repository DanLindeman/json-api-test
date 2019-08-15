package com.example.jsonapitest.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.crnk.core.resource.annotations.*
import javax.validation.constraints.Size

@JsonApiResource(type = "tasks")
class Task {
    @JsonApiId
    var id: Long? = null

    @JsonProperty("name")
    var name: String? = null

    @Size(max = 20, message = "Description may not exceed {max} characters.")
    var description: String? = null

    @JsonApiRelationId
    private var projectId: Long? = null

    @JsonApiRelation(opposite = "tasks", lookUp = LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL, repositoryBehavior = RelationshipRepositoryBehavior.FORWARD_OWNER, serialize = SerializeType.ONLY_ID)
    private var project: Project? = null

    constructor() {}

    constructor(id: Long?, name: String) {
        this.id = id
        this.name = name
    }

    fun getProject(): Project? {
        return project
    }

    fun setProject(project: Project?) {
        this.project = project
        this.projectId = project?.id!!.toLong()
    }

    fun getProjectId(): Long? {
        return projectId
    }

    fun setProjectId(projectId: Long?) {
        this.projectId = projectId
        this.project = null
    }
}
