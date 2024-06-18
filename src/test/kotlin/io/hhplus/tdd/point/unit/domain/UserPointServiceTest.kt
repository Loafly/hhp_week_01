package io.hhplus.tdd.point.unit.domain

import io.hhplus.tdd.point.domain.PointHistoryService
import io.hhplus.tdd.point.domain.UserPoint
import io.hhplus.tdd.point.domain.UserPointService
import io.hhplus.tdd.point.infra.UserPointRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserPointServiceTest {

    @Mock
    // 가짜 객체 생성
    private lateinit var mockUserPointRepository: UserPointRepository

    @Mock
    private lateinit var pointHistoryService: PointHistoryService

    @InjectMocks
    // Mock 으로 설정되어있는 객체를 가지고 객체 생성
    private lateinit var userPointService: UserPointService

    @Test
    @DisplayName("포인트 조회")
    fun getUserPoint() {
        // given
        val expectedUserPoint = UserPoint(id = 1, point = 100, updateMillis = 1000) //기대 객체 생성
        `when`(mockUserPointRepository.findById(expectedUserPoint.id)).thenReturn(expectedUserPoint) // mockUserPointRepository의 findById 함수 호출 시 기대객체로 반환

        // when
        val userPoint = userPointService.getUserPointById(expectedUserPoint.id)

        // then
        assertNotNull(userPoint)
        assertEquals(expectedUserPoint, userPoint)
    }

    @Nested
    @DisplayName("포인트 충전")
    inner class ChargeUserPointTest {
        @Test
        @DisplayName("한 명의 사용자 업데이트")
        fun success() {
            // given
            val expectedFindUserPoint = UserPoint(id = 1, point = 100, updateMillis = 1000) //기대 객체 생성
            val amount: Long = 50
            val expectedSaveUserPoint = UserPoint(id = 1, point = expectedFindUserPoint.point + amount, updateMillis = 1000) //기대 객체 생성
            `when`(mockUserPointRepository.findById(expectedFindUserPoint.id)).thenReturn(expectedFindUserPoint) // mockUserPointRepository의 findById 함수 호출 시 기대객체로 반환
            `when`(mockUserPointRepository.save(expectedSaveUserPoint.id, expectedSaveUserPoint.point)).thenReturn(expectedSaveUserPoint) // mockUserPointRepository의 save 함수 호출 시 기대객체로 반환

            // when
            val userPoint = userPointService.chargeUserPoint(expectedFindUserPoint.id, amount)

            // then
            assertNotNull(userPoint)
            assertEquals(expectedSaveUserPoint, userPoint)
        }

        @Test
        @DisplayName("동시에 한 명의 사용자 업데이트")
        fun successIfConcurrencyCase() {
            // TODO :
        }
    }

}