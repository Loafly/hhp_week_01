package io.hhplus.tdd.point.integration

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
// API 테스트
class PointControllerApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Nested
    @DisplayName("포인트 조회")
    inner class PointGetTest {

        @Test
        @DisplayName("200 OK")
        fun success() {
            // given
            val id = 0;

            // when then
            mockMvc.perform(MockMvcRequestBuilders.get("/point/$id"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(id))
        }
    }



    @Test
    fun history() {
    }

    @Test
    fun charge() {
    }

    @Test
    fun use() {
    }
}