package io.hhplus.tdd.point.unit.domain

import io.hhplus.tdd.exception.InvalidPointAmountException
import io.hhplus.tdd.point.domain.UserPoint
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserPointTest {
    @Test
    fun `유저 포인트 객체 생성 (정상)`() {
        //given
        val id = 1L
        val point = 1000L
        val updateMillis = System.currentTimeMillis()

        //when
        val userPoint = UserPoint(id = id, point = point, updateMillis = updateMillis)

        //then
        assertNotNull(userPoint)
        assertEquals(id, userPoint.id)
        assertEquals(point, userPoint.point)
        assertEquals(updateMillis, userPoint.updateMillis)
    }

    @Test
    fun `유저 포인트 객체 생성 (포인트 음수)`() {
        //given
        val id = 1L
        val point = -1000L
        val updateMillis = System.currentTimeMillis()

        //when & then
        val exception = assertThrows<InvalidPointAmountException> {
            UserPoint(id = id, point = point, updateMillis = updateMillis)
        }

        assertEquals("포인트는 0 이하일 수 없습니다.", exception.message)
    }
}