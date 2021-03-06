package test.kotlin.spring.project.test_kotlin_spring_boot

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class AckResponse(val status: Boolean, val result: String, val message: String? = null)

@RestController
class ServiceController {
    @RequestMapping(
            path = arrayOf("/request"),
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun nameRequest(
            @RequestParam(value = "name") name: String,
            @RequestParam(value = "surname", required = false) surname: String?): AckResponse {
        return if (surname == null)
            AckResponse(status = true, result = "Hi $name", message = "surname is empty")
        else
            AckResponse(status = true, result = "Hi $surname,$name")
    }

    @RequestMapping(
            path = arrayOf("/sort_request"),
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun findMinimum(
            @RequestParam(value = "values") values: Array<String>): AckResponse {
        println("values:")
        values.forEach { println(it) }

        val minValue = values.apply { sortBy { it.length } }
                .firstOrNull()
                ?.split("_")
                ?.sorted()
                ?.joinToString(",") ?: ""

        return AckResponse(status = true, result = minValue)
    }
}
