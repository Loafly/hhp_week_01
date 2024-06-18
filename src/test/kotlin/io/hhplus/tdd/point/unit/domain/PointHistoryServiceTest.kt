package io.hhplus.tdd.point.unit.domain

import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.PointHistoryService
import io.hhplus.tdd.point.domain.TransactionType
import io.hhplus.tdd.point.infra.PointHistoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PointHistoryServiceTest {

    @Mock
    // 가짜 객체 생성
    private lateinit var mockPointHistoryRepository: PointHistoryRepository

    @InjectMocks
    // Mock 으로 설정되어있는 객체를 가지고 객체 생성
    private lateinit var pointHistoryService: PointHistoryService

    @Test
    fun `포인트 내역 저장`() {
        //given
        val userId = 1L
        val amount = 100L
        val transactionType = TransactionType.CHARGE
        val updateMillis = System.currentTimeMillis()
        `when`(mockPointHistoryRepository.save(userId, amount, transactionType, updateMillis)).thenReturn(
            PointHistory(
                id = 1L, userId = userId, type = transactionType, amount = amount, timeMillis = System.currentTimeMillis()
            )
        )

        //when
        val result = pointHistoryService.save(userId, amount, transactionType, updateMillis)

        //then
        verify(mockPointHistoryRepository).save(userId, amount, transactionType, updateMillis)
        assertNotNull(result)
        assertEquals(userId, result.userId)
        assertEquals(amount, result.amount)
        assertEquals(transactionType, result.type)
    }

    @Test
    fun `포인트 내역 저장 실패 (포인트가 0보다 작은 경우)`() {
        //given
        val userId = 1L
        val amount = -100L
        val transactionType = TransactionType.CHARGE
        val updateMillis = System.currentTimeMillis()
        `when`(mockPointHistoryRepository.save(userId, amount, transactionType, updateMillis)).thenThrow(IllegalArgumentException())

        //when
        val exception = assertThrows<IllegalArgumentException> {
            pointHistoryService.save(userId, amount, transactionType, updateMillis)
        }

        //then
        verify(mockPointHistoryRepository).save(userId, amount, transactionType, updateMillis)
    }
}