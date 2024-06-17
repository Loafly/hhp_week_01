package io.hhplus.tdd.point.infra

import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.point.domain.UserPoint
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserPointRepositoryImplTest {

    @Test
    @DisplayName("UserPoint 조회")
    fun findById() {
        // given
        val mockUserPointTable = mock(UserPointTable::class.java) // 모키토에서 제공해주는 mock 함수는 KClass가 아닌 JavaClass를 매개변수를 기대하기때문
        val userPointRepository = UserPointRepositoryImpl(mockUserPointTable)
        val expectedUserPoint = UserPoint(id = 1, point = 100, updateMillis = 1000)
        `when`(mockUserPointTable.selectById(expectedUserPoint.id)).thenReturn(expectedUserPoint)

        // when
        val result = userPointRepository.findById(expectedUserPoint.id)

        // then
        assertEquals(expectedUserPoint, result)
    }
}