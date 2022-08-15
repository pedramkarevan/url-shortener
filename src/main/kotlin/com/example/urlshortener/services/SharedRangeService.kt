package com.example.urlshortener.services

import com.example.urlshortener.model.Range
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.retry.RetryNTimes
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class SharedRangeService  {


    var client: CuratorFramework? = null
    private var count: DistributedAtomicLong? = null

    @PostConstruct
    open fun postConstruct() {
        client = CuratorFrameworkFactory.newClient(
            SharedRangeService.zooServer,
            ExponentialBackoffRetry(1000, 3)
        )
        client!!.start()
        count = DistributedAtomicLong(
            client, SharedRangeService.counterPath,
            RetryNTimes(10, 10)
        )
    }

    @Bean("range")
    fun getUniqueRange(): Range? {
        try {
            if (client == null) postConstruct()
            val atmVal = count!!.increment()
            if (atmVal.succeeded()) {
                val start: Long = atmVal.preValue()* SharedRangeService.span!!
                return Range(start, start + SharedRangeService.span - 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        val zooServer: String? = "localhost:2181"
        val counterPath: String? = "/url-shortener/counter"
        val span: Long? = 10
    }

}

