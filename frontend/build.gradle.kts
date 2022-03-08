plugins {
    base
    id("com.github.node-gradle.node") version "3.2.1"
}

node {
    download.set(true)
    version.set("16.14.0")
    workDir.set(file("${project.projectDir}/.node/nodejs"))
    npmWorkDir.set(file("${project.projectDir}/.node/npm"))
}

tasks {
    "npm_start" {
        dependsOn(npmInstall)
    }
}