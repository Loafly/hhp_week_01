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
import org.junit.jupiter.api.assertThrows
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
    fun `포인트 조회`() {
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
        fun `한 명의 사용자 포인트 충전`() {
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
    }

    @Nested
    @DisplayName("포인트 사용")
    inner class UseUserPointTest {
        @Test
        fun `한 명의 사용자 포인트 사용`() {
            //given
            val id = 1L
            val amount = 500L
            val point = 1000L
            val totalPoint = point - amount

            val expectedFindUserPoint = UserPoint(id = id, point = point, updateMillis = 1000)
            `when`(mockUserPointRepository.findById(id)).thenReturn(expectedFindUserPoint)

            val expectedSaveUserPoint = UserPoint(id = id, point = totalPoint, updateMillis = 1000)
            `when`(mockUserPointRepository.save(id, totalPoint)).thenReturn(expectedSaveUserPoint)

            //when
            val result = userPointService.useUserPoint(id, amount)

            //then
            assertNotNull(result)
            assertEquals(expectedSaveUserPoint, result)
        }

        @Test
        fun `사용 금액이 가지고 있는 포인트보다 큰 경우`() {
            //given
            val id = 1L
            val amount = 1000L
            val point = 500L
            val expectedUserPoint = UserPoint(id = id, point = point, updateMillis = 1000)
            `when`(mockUserPointRepository.findById(id)).thenReturn(expectedUserPoint)

            //when
            val exception = assertThrows<IllegalArgumentException>{
                userPointService.useUserPoint(id, amount)
            }

            //then
            assertEquals("사용하려는 포인트가 가지고있는 포인트보다 많습니다.", exception.message)

        }
    }

}