package io.hhplus.tdd.point.unit.infra

import io.hhplus.tdd.database.UserPointTable
import io.hhplus.tdd.point.domain.UserPoint
import io.hhplus.tdd.point.infra.UserPointRepositoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserPointRepositoryImplTest {

    @Mock
    // 가짜 객체 생성
    private lateinit var mockUserPointTable: UserPointTable

    @InjectMocks
    // Mock 으로 설정되어있는 객체를 가지고 객체 생성
    private lateinit var userPointRepository: UserPointRepositoryImpl

    @Test
    fun `UserPoint 조회`() {
        // given
        val expectedUserPoint = UserPoint(id = 1, point = 100, updateMillis = 1000)
        given(mockUserPointTable.selectById(expectedUserPoint.id)).willReturn(expectedUserPoint)

        // when
        val result = userPointRepository.findById(expectedUserPoint.id)

        // then
        then(mockUserPointTable).should().selectById(expectedUserPoint.id)
        assertEquals(expectedUserPoint, result)
    }

    @Test
    fun `UserPoint 업데이트`() {
        // given
        val expectedUserPoint = UserPoint(id = 1, point = 100, updateMillis = 1000)
        given(mockUserPointTable.insertOrUpdate(expectedUserPoint.id, expectedUserPoint.point))
            .willReturn(expectedUserPoint)

        // when
        val result = userPointRepository.save(expectedUserPoint.id, expectedUserPoint.point)

        // then
        then(mockUserPointTable).should().insertOrUpdate(expectedUserPoint.id, expectedUserPoint.point)
        assertEquals(expectedUserPoint, result)
    }

}