package com.example.jsonapitest.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.crnk.core.resource.annotations.*

@JsonApiResource(type = "projects")
class Project {

    @JsonApiId
    var id: Long? = null

    @JsonProperty
//    @Facet
    var name: String? = null

    @JsonApiRelation(opposite = "project", lookUp = LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL, repositoryBehavior = RelationshipRepositoryBehavior.FORWARD_OPPOSITE)
    var tasks: List<Task>? = null

    constructor() {}

    constructor(id: Long?, name: String) {
        this.id = id
        this.name = name
    }
}
