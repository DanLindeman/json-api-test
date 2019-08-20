package com.example.jsonapitest.config

import io.crnk.core.engine.filter.FilterBehavior
import io.crnk.core.engine.filter.ResourceFilter
import io.crnk.core.engine.filter.ResourceFilterContext
import io.crnk.core.engine.http.HttpMethod
import io.crnk.core.engine.information.resource.ResourceField
import io.crnk.core.engine.information.resource.ResourceInformation
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class TaskResourceFilter : ResourceFilter {
    override fun filterField(filterContext: ResourceFilterContext?, field: ResourceField?, method: HttpMethod?): FilterBehavior {
        /**
         * Field level authorization
         */
        val context = SecurityContextHolder.getContext()
        if (context.authentication.authorities.find { it.authority == "ROLE_ADMIN" } != null) {
            return FilterBehavior.NONE
        }
        return FilterBehavior.IGNORED
    }

    override fun filterResource(filterContext: ResourceFilterContext?, resourceInformation: ResourceInformation?, method: HttpMethod?): FilterBehavior {
        return FilterBehavior.NONE
    }
}
