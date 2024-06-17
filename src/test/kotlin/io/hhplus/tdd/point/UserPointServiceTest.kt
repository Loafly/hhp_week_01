package io.hhplus.tdd.point

import io.hhplus.tdd.point.domain.UserPoint
import io.hhplus.tdd.point.domain.UserPointService
import io.hhplus.tdd.point.infra.UserPointRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserPointServiceTest {

    @Test
    @DisplayName("포인트 조회")
    fun getUserPoint() {
        // given
        val mockUserPointRepository = mock(UserPointRepository::class.java) // 모키토에서 제공해주는 mock 함수는 KClass가 아닌 JavaClass를 매개변수를 기대하기때문
        val userPointService = UserPointService(mockUserPointRepository)

        val expectedUserPoint = UserPoint(id = 1, point = 100, updateMillis = 1000) //기대 객체 생성
        `when`(mockUserPointRepository.findById(expectedUserPoint.id)).thenReturn(expectedUserPoint) // mockUserPointRepository의 findById 함수 호출 시 기대객체로 반환

        // when
        val userPoint = userPointService.getUserPointById(expectedUserPoint.id)

        // then
        assertNotNull(userPoint)
        assertEquals(expectedUserPoint, userPoint)
    }

}