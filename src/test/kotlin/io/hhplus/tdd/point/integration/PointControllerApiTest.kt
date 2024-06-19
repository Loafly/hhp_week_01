package io.hhplus.tdd.point.integration

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
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

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Nested
    @DisplayName("포인트 조회")
    inner class PointGetTest {

        @Test
        fun `200 OK`() {
            // given
            val id = 0;

            // when then
            mockMvc.perform(MockMvcRequestBuilders.get("/point/$id"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(id))
        }
    }

    @Nested
    @DisplayName("포인트 내역 조회")
    inner class PointHistoryGetAllTest {

        @Test
        fun `200 OK`() {
            // given
            val id = 0;

            // when then
            mockMvc.perform(MockMvcRequestBuilders.get("/point/$id/histories"))
                .andExpect(status().isOk)

        }
    }

    @Nested
    @DisplayName("포인트 충전")
    inner class PointChargeTest {

        @Test
        @DisplayName("200 OK")
        fun success() {
            // given
            val id: Long = 0
            val updatePoint: Long = 1000
            val requestBody = objectMapper.writeValueAsString(updatePoint)

            // when then
            mockMvc.perform(
                MockMvcRequestBuilders.patch("/point/$id/charge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.point").value(updatePoint))
        }

        @Test
        @DisplayName("포인트가 0이하인 경우 ")
        fun failIfPointIsUnderZero() {
            // given
            val id: Long = 0
            val updatePoint: Long = -1000
            val requestBody = objectMapper.writeValueAsString(updatePoint)

            // when then
            mockMvc.perform(
                MockMvcRequestBuilders.patch("/point/$id/charge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
                .andExpect(status().is4xxClientError)
        }
    }

    @Nested
    @DisplayName("포인트 사용")
    inner class PointUseTest {
        @Test
        fun `포인트 정상 사용`() {
            val id: Long = 0
            val amount: Long = 0
            val requestBody = objectMapper.writeValueAsString(amount)

            // when then
            mockMvc.perform(
                MockMvcRequestBuilders.patch("/point/$id/use")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.point").value(amount))
        }

        @Test
        fun `포인트 사용 금액 이 0원 미만일 경우`() {
            val id: Long = 0
            val amount: Long = -1000
            val requestBody = objectMapper.writeValueAsString(amount)

            // when then
            mockMvc.perform(
                MockMvcRequestBuilders.patch("/point/$id/use")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
                .andExpect(status().is4xxClientError)
        }

        @Test
        fun `포인트 사용 금액이 가지고 있는 포인트보다 큰 경우`() {
            val id: Long = 0
            val amount: Long = 1000
            val requestBody = objectMapper.writeValueAsString(amount)

            // when then
            mockMvc.perform(
                MockMvcRequestBuilders.patch("/point/$id/use")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            )
                .andExpect(status().is4xxClientError)
        }
    }
}