package de.digitalfrontiers.spring.build.image

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("sample-feign")
interface SampleFeignClient {

    @GetMapping("/")
    fun fetchIt(): String
}
