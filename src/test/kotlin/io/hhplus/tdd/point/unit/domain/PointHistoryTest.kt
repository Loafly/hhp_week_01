package io.hhplus.tdd.point.unit.domain

import io.hhplus.tdd.exception.InvalidPointAmountException
import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.TransactionType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PointHistoryTest {

    @Test
    fun `포인트 히스토리 객체 생성 (정상)`() {
        //given
        val id = 1L
        val userId = 1L
        val type: TransactionType =  TransactionType.CHARGE
        val amount = 1000L
        val timeMillis = System.currentTimeMillis()

        //when
        val pointHistory = PointHistory(id = id, userId = userId, type = type, amount = amount, timeMillis = timeMillis)

        //then
        assertNotNull(pointHistory)
        assertEquals(id, pointHistory.id)
        assertEquals(userId, pointHistory.userId)
        assertEquals(type, pointHistory.type)
        assertEquals(amount, pointHistory.amount)
        assertEquals(timeMillis, pointHistory.timeMillis)
    }

    @Test
    fun `포인트 히스토리 객체 생성 (amount )`() {
        //given
        val id = 1L
        val userId = 1L
        val type: TransactionType =  TransactionType.CHARGE
        val amount = -1000L
        val timeMillis = System.currentTimeMillis()

        //when
        val exception = assertThrows<InvalidPointAmountException> {
            PointHistory(id = id, userId = userId, type = type, amount = amount, timeMillis = timeMillis)
        }

        assertEquals("포인트는 0 이하일 수 없습니다.", exception.message)
    }
}

