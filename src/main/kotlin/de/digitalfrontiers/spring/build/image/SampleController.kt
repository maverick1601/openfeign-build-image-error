package de.digitalfrontiers.spring.build.image

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class SampleController(val sampleFeignClient: SampleFeignClient) {

    @GetMapping("/api/sample")
    fun sampleApi(): CompletableFuture<ResponseEntity<String>> =
            CompletableFuture.supplyAsync {
                sampleFeignClient.fetchIt()
            }.thenApply { ResponseEntity.ok(it) }
}
