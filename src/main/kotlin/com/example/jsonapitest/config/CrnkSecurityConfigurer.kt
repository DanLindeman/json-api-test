package com.example.jsonapitest.config

import com.example.jsonapitest.models.Project
import com.example.jsonapitest.models.Task
import io.crnk.security.ResourcePermission
import io.crnk.security.SecurityConfig
import io.crnk.spring.setup.boot.security.SecurityModuleConfigurer
import org.springframework.stereotype.Component

@Component
class CrnkSecurityConfigurer : SecurityModuleConfigurer {

    override fun configure(config: SecurityConfig.Builder) {
        /**
         * Resource level authorization
         */
        config.permitAll(Task::class.java, ResourcePermission.ALL)
        config.permitRole("ADMIN", Project::class.java, ResourcePermission.ALL)
    }
}
