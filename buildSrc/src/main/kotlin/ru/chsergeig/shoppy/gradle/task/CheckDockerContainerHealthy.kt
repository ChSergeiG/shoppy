package ru.chsergeig.shoppy.gradle.task

import com.bmuschko.gradle.docker.tasks.container.DockerExistingContainer
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.util.concurrent.TimeoutException

open class CheckDockerContainerHealthy : DockerExistingContainer() {

    @Input
    val taskTimeout: Property<Int> = project.objects.property(Int::class.java)

    @Input
    val checkInterval: Property<Int> = project.objects.property(Int::class.java)

    @Input
    val checkLevel: Property<CheckLevel> = project.objects.property(CheckLevel::class.java)

    override fun runRemoteCommand() {
        logger.warn("To successfully pass this task with HIGH check level, container should be configured with healthcheck")
        val deaLine: Long = System.currentTimeMillis() + (taskTimeout.get() * 1000)
        val timeToSleep: Long = checkInterval.get() * 1000L
        sleep(timeToSleep)
        while (System.currentTimeMillis() < deaLine) {
            if (isHealthy()) {
                return
            }
            logger.warn("<${LocalDateTime.now()}> Container with ID '${containerId.get()}' is not ready")
            sleep(timeToSleep)
        }
        throw TimeoutException("Health check timeout expired")
    }

    private fun isHealthy(): Boolean {
        val command = dockerClient.inspectContainerCmd(containerId.get())
        val response = command.exec()
        return when (checkLevel.get()) {
            CheckLevel.HIGH -> {
                response.state.health != null && response.state.health.status.lowercase() == "healthy"
            }
            else -> {
                (response.state != null)
                        && (response.state.dead != null) && !response.state.dead
                        && (response.state.paused != null) && !response.state.paused
                        && (response.state.running != null) && response.state.running
            }
        }

    }

    enum class CheckLevel {
        HIGH, LOW;
    }

}