package io.hhplus.tdd.point

import io.hhplus.tdd.point.domain.UserPointService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class UserPointServiceTest {

    // 포인트 조회
    @Test
    fun getUserPoint() {
        // given
        val userPointService = UserPointService()
        val id: Long = 1;

        // when
        val userPoint = userPointService.getUserPointById(id);

        // then
        assertNotNull(userPoint)
        assertEquals(id, userPoint.id)
    }

}