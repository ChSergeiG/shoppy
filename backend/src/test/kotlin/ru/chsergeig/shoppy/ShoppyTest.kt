package ru.chsergeig.shoppy

import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = ["classpath:application-test.properties"])
@Import(TestConfigurationContext::class)
@EnableTransactionManagement
annotation class ShoppyTest
