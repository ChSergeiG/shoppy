package ru.chsergeig.shoppy

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.web.server.LocalServerPort

@ShoppyTest
abstract class ApiTest {

    @LocalServerPort
    var port: Int = -1

    var spec: RequestSpecification? = null

    @BeforeEach
    fun setup() {
        spec = RequestSpecBuilder()
            .setBaseUri("http://localhost:${port}")
            .setContentType(ContentType.JSON)
            .build()
    }

}