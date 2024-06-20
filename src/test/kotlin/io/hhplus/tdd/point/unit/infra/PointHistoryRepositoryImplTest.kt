package io.hhplus.tdd.point.unit.infra

import io.hhplus.tdd.database.PointHistoryTable
import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.TransactionType
import io.hhplus.tdd.point.infra.PointHistoryRepositoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PointHistoryRepositoryImplTest {

    @Mock // Spy로 하는게 맞을까? Repository 테스트를 진행하는데, Table 의 로직이 영향을 주게 된다.
    private lateinit var mockPointHistoryTable: PointHistoryTable

    @InjectMocks
    // Mock 으로 설정되어있는 객체를 가지고 객체 생성
    private lateinit var pointHistoryRepository: PointHistoryRepositoryImpl

    @Test
    fun `포인트 내역 저장`() {
        // given
        val id = 1L
        val userId = 1L
        val amount = 100L
        val transactionType = TransactionType.CHARGE
        val updateMillis = System.currentTimeMillis()
        val expectedPointHistory = PointHistory(id, userId, transactionType, amount, updateMillis)

        given(mockPointHistoryTable.insert(userId, amount, transactionType, updateMillis))
            .willReturn(expectedPointHistory)

        // when
        val result = pointHistoryRepository.save(
            userId, amount, transactionType, updateMillis
        )

        // then
        then(mockPointHistoryTable).should().insert(userId, amount, transactionType, updateMillis)
        assertEquals(result, expectedPointHistory)
    }

    @Test
    fun `포인트 내역 리스트 조회`() {
        // given
        val userId = 1L
        val size = 10;
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

        given(mockPointHistoryTable.selectAllByUserId(userId))
            .willReturn(expectedPointHistoryList)
        //when
        val result = pointHistoryRepository.findAllByUserId(userId)

        //then
        then(mockPointHistoryTable).should().selectAllByUserId(userId)
        assertEquals(size, result.size)
    }
}