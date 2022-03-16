package ru.chsergeig.shoppy.gradle.task

import com.bmuschko.gradle.docker.domain.LivenessProbe
import com.bmuschko.gradle.docker.internal.IOUtils.getProgressLogger
import com.bmuschko.gradle.docker.tasks.container.DockerLogsContainer
import com.github.dockerjava.api.command.InspectContainerResponse
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.internal.logging.progress.ProgressLogger
import java.io.StringWriter
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Based on
 * {@link https://github.com/bmuschko/gradle-docker-plugin/blob/v6.3.0/src/main/groovy/com/bmuschko/gradle/docker/tasks/container/extras/DockerLivenessContainer.groovy}
 */
open class CheckDockerContainerHealthy : DockerLogsContainer() {

    @Input
    @Optional
    val probe: Property<LivenessProbe> = project.objects.property(LivenessProbe::class.java)

    override fun runRemoteCommand() {
        logger.quiet("Starting liveness probe on container with ID '${containerId.get()}'.")
        if (probe.isPresent) {
            logger.quiet("Started. Using probe '${probe.get()}'")
            val matchFound = AtomicBoolean(false)
            val deadline: LocalDateTime = LocalDateTime.now().plus(probe.get().pollTime, ChronoUnit.MILLIS)
            sink = StringWriter()
            var counter = 0
            while (!matchFound.get() || deadline.isAfter(LocalDateTime.now())) {
                logger.quiet("[${++counter}] Probing...")
                logAndProcessResponse(dockerClient)
                val logLine: String = sink.toString()
                if (logLine.isNotBlank() && logLine.contains(probe.get().logContains)) {
                    logger.quiet("Successfully launched")
                    matchFound.set(true)
                    break
                } else {
                    try {
                        (sink as StringWriter).buffer.setLength(0)
                        sleep(probe.get().pollInterval)
                    } catch (ignore: Exception) {
                    }
                }
            }
            if (!matchFound.get()) {
                logger.quiet("Liveness probe failed to find a match: ${probe.get().logContains}")
            }
        } else {
            logger.quiet("Probe is not defined. Just checking state")
            val response = inspectContainer()
            if (!response.state.running) {
                logger.quiet("Container with ID '${containerId.get()}' is not running")
            }
        }
    }

    private fun inspectContainer(): InspectContainerResponse {
        return dockerClient.inspectContainerCmd(containerId.get()).exec()
    }

    fun livenessProbe(pollTime: Long = 30_000, pollInterval: Long = 2_500, logContains: String) {
        this.probe.set(LivenessProbe(pollTime, pollInterval, logContains))
    }

}