package io.hhplus.tdd.point

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(PointController::class)
// API(EtoE) 테스트
class PointControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Nested
    @DisplayName("포인트 조회")
    inner class PointGetTest{

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

        @Test
        @DisplayName("Id가 존재하지 않는 경우 - 400 Bad Request")
        fun success2() {
            // given
            val id = -1;

            // when then
            mockMvc.perform(MockMvcRequestBuilders.get("/point/$id"))
                .andExpect(status().is4xxClientError)
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