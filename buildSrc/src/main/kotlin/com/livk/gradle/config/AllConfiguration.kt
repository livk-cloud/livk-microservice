package com.livk.gradle.config

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

/**
 * <p>
 * AllConfig
 * </p>
 *
 * @author livk
 * @date 2022/7/11
 */
abstract class AllConfiguration : Plugin<Project> {
    override fun apply(project: Project) {
        val springBootVersion = project.rootProject
            .extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
            .findVersion("springBoot")
            .get()
            .displayName
        project.configurations.all { config ->
            config.resolutionStrategy { strategy ->
                strategy.dependencySubstitution { dependency ->
                    dependency.substitute(dependency.module("org.springframework.boot:spring-boot-starter-tomcat"))
                        .using(dependency.module("org.springframework.boot:spring-boot-starter-undertow:" + springBootVersion))
                }
            }
        }

        project.rootProject.allprojects.forEach {
            it.repositories.maven { it.setUrl("https://maven.aliyun.com/repository/public") }
            it.repositories.maven { it.setUrl("https://plugins.gradle.org/m2/") }
            it.repositories.maven { it.setUrl("https://repo.spring.io/release") }
        }
    }
}
