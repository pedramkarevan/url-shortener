package com.example.urlshortener.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption
import java.util.*


@Configuration
@EnableAutoConfiguration
@PropertySource(*["classpath:/configuration/cassandra.properties"])
class CassandraConfig : AbstractCassandraConfiguration() {

    @Autowired
    private val env: Environment? = null

    protected override fun getKeyspaceName(): String {
        return env?.getProperty("spring.data.cassandra.keyspace-name").toString()
    }

    override fun getEntityBasePackages(): Array<String?> {
        return arrayOf("com.example.urlshortener")
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }


    override fun getContactPoints(): String {
        return env?.getProperty("spring.data.cassandra.contact-points").toString()
    }

            protected override fun getKeyspaceCreations(): List<CreateKeyspaceSpecification> {
        if (CreateKeyspaceSpecification.createKeyspace(keyspaceName.toString()).ifNotExists)
            return Arrays.asList(
                CreateKeyspaceSpecification.createKeyspace(keyspaceName.toString()).with(KeyspaceOption.DURABLE_WRITES, true))
        return Arrays.asList((CreateKeyspaceSpecification.createKeyspace(keyspaceName.toString()).ifNotExists()))
    }


}