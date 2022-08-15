description = "microservice通用核心包"

dependencies {
    optional("org.springframework:spring-webflux")
    optional("io.projectreactor.netty:reactor-netty-http")
    optional("org.springframework.boot:spring-boot-starter")
    optional("jakarta.servlet:jakarta.servlet-api")
    api("com.fasterxml.jackson.core:jackson-databind")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    api("org.apache.commons:commons-lang3")
    api("com.google.code.findbugs:annotations")
    api("com.google.guava:guava")
}
