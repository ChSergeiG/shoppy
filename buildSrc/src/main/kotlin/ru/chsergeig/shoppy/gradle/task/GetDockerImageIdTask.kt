package ru.chsergeig.shoppy.gradle.task

import com.bmuschko.gradle.docker.tasks.AbstractDockerRemoteApiTask
import com.github.dockerjava.api.model.Image
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal

@CacheableTask
open class GetDockerImageIdTask : AbstractDockerRemoteApiTask() {

    @Input
    val filterForImageName: Property<String> = project.objects.property(String::class.java)

    @Internal
    val imageId: Property<String> = project.objects.property(String::class.java)

    init {
        onNext {
            imageId.set((it as Image).id as String)
        }
    }

    override fun runRemoteCommand() {
        val images = dockerClient.listImagesCmd()
            .withImageNameFilter(filterForImageName.get())
            .exec()

        for (image in images) {
            nextHandler.execute(image)
        }
    }
}
