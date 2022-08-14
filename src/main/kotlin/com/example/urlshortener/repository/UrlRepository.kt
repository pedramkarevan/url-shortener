package com.example.urlshortener.repository

import com.example.urlshortener.model.UrlMapping
import org.springframework.data.cassandra.repository.AllowFiltering
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UrlRepository : CrudRepository<UrlMapping, String> {

    @AllowFiltering
    @Query("Select * from url where oldUrl=?0")
    fun findByOldUrl(sOldUrl: String?): Optional<UrlMapping>
}