package com.example.jsonapitest.models

import io.crnk.core.resource.links.PagedLinksInformation
import io.crnk.core.resource.list.ResourceListBase
import io.crnk.core.resource.meta.PagedMetaInformation

class ProjectList : ResourceListBase<Project, ProjectListMeta, ProjectListLinks>()

class ProjectListMeta : PagedMetaInformation {

    private var totalResourceCount: Long? = null

    override fun getTotalResourceCount(): Long? {
        return totalResourceCount
    }

    override fun setTotalResourceCount(totalResourceCount: Long?) {
        this.totalResourceCount = totalResourceCount
    }

}

class ProjectListLinks : PagedLinksInformation {

    private var first: String? = null

    private var last: String? = null

    private var next: String? = null

    private var prev: String? = null

    override fun getFirst(): String? {
        return first
    }

    override fun setFirst(first: String) {
        this.first = first
    }

    override fun getLast(): String? {
        return last
    }

    override fun setLast(last: String) {
        this.last = last
    }

    override fun getNext(): String? {
        return next
    }

    override fun setNext(next: String) {
        this.next = next
    }

    override fun getPrev(): String? {
        return prev
    }

    override fun setPrev(prev: String) {
        this.prev = prev
    }
}

