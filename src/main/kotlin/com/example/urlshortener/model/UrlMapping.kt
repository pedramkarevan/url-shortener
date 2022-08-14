package com.example.urlshortener.model


import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table


@Table("url")
class UrlMapping {
    @PrimaryKey
    @Column
    var newUrl: String? = null

    @Column
    var oldUrl: String? = null

    constructor() {}

    constructor(_newUrl: String, _oldUrl: String) {
        this.newUrl = _newUrl
        this.oldUrl = _oldUrl
        println("$newUrl: $oldUrl constructor call")
    }


}