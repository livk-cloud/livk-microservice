dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

gradle.gradle.settingsEvaluated {
    if (JavaVersion.current() != JavaVersion.VERSION_17) {
        throw GradleException("This build requires JDK 17. It's currently ${JavaVersion.current()}. You can ignore this check by passing '-Dorg.gradle.ignoreBuildJavaVersionCheck'.")
    }
}
