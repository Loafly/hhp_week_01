package io.hhplus.tdd.point.unit.domain

import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.PointHistoryService
import io.hhplus.tdd.point.domain.TransactionType
import io.hhplus.tdd.point.infra.PointHistoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PointHistoryServiceTest {

    @Mock
    // 가짜 객체 생성
    private lateinit var mockPointHistoryRepository: PointHistoryRepository

    @InjectMocks
    // Mock 으로 설정되어있는 객체를 가지고 객체 생성
    private lateinit var pointHistoryService: PointHistoryService

    @Nested
    inner class `포인트 내역 저장` {
        @Test
        fun `포인트 내역 저장 정상`() {
            //given
            val userId = 1L
            val amount = 100L
            val transactionType = TransactionType.CHARGE
            val updateMillis = System.currentTimeMillis()

            val expectedPointHistory = PointHistory(
                id = 1L, userId = userId, type = transactionType, amount = amount, timeMillis = updateMillis
            )
            given(mockPointHistoryRepository.save(userId, amount, transactionType, updateMillis))
                .willReturn(expectedPointHistory)

            //when
            val result = pointHistoryService.save(userId, amount, transactionType, updateMillis)

            //then
            then(mockPointHistoryRepository).should().save(userId, amount, transactionType, updateMillis)
            assertNotNull(result)
            assertEquals(expectedPointHistory, result)
        }

        @Test
        fun `포인트 내역 저장 실패 (포인트가 0보다 작은 경우)`() {
            //given
            val userId = 1L
            val amount = -100L
            val transactionType = TransactionType.CHARGE
            val updateMillis = System.currentTimeMillis()
            given(mockPointHistoryRepository.save(userId, amount, transactionType, updateMillis)).willThrow(IllegalArgumentException("포인트는 0 이하일 수 없습니다."))

            //when
            val exception = assertThrows<IllegalArgumentException> {
                pointHistoryService.save(userId, amount, transactionType, updateMillis)
            }

            //then
            then(mockPointHistoryRepository).should().save(userId, amount, transactionType, updateMillis)
            assertEquals("포인트는 0 이하일 수 없습니다.", exception.message)
        }
    }

    @Test
    fun `포인트 내역 리스트 조회`() {
        // given
        val userId = 1L
        val amount = 100L
        val transactionType = TransactionType.CHARGE
        val updateMillis = System.currentTimeMillis()

        val expectedPointHistoryList = listOf(
            PointHistory(1L, userId, transactionType, amount, updateMillis),
            PointHistory(2L, userId, transactionType, amount, updateMillis),
            PointHistory(3L, userId, transactionType, amount, updateMillis),
            PointHistory(4L, userId, transactionType, amount, updateMillis),
            PointHistory(5L, userId, transactionType, amount, updateMillis),
            PointHistory(6L, userId, transactionType, amount, updateMillis),
            PointHistory(7L, userId, transactionType, amount, updateMillis),
            PointHistory(8L, userId, transactionType, amount, updateMillis),
            PointHistory(9L, userId, transactionType, amount, updateMillis),
            PointHistory(10L, userId, transactionType, amount, updateMillis)
        )

        given(mockPointHistoryRepository.findAllByUserId(userId)).willReturn(expectedPointHistoryList) // mockUserPointRepository의 findById 함수 호출 시 기대객체로 반환

        // when
        val pointHistoryList = pointHistoryService.getAllByUserId(userId)

        // then
        then(mockPointHistoryRepository).should().findAllByUserId(userId)
        assertNotNull(pointHistoryList)
        assertEquals(expectedPointHistoryList, pointHistoryList)
    }

}