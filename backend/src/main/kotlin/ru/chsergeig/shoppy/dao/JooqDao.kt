package ru.chsergeig.shoppy.dao

import org.jooq.Configuration

interface JooqDao {

    fun configuration(): Configuration
}
