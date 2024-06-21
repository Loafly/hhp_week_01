package io.hhplus.tdd.point.integration.domain

import io.hhplus.tdd.point.domain.UserPointService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class UserPointServiceIntegrationTest {

    @Autowired
    private lateinit var userPointService: UserPointService

    @Test
    fun `포인트 충전 동시성 테스트`() {
        //given
        val id = 1L
        val amount = 100L
        val numberOfThreads = 10

        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(numberOfThreads)

        //when
        for (i in 0 until numberOfThreads) {
            executor.submit {
                try {
                    userPointService.chargeUserPoint(id, amount)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        executor.shutdown()

        //then
        val userPoint = userPointService.getUserPointById(id)
        assertEquals(amount * numberOfThreads, userPoint.point)
    }

    @Test
    fun `포인트 사용 동시성 테스트`() {
        //given
        val id = 2L
        val point = 1000000L
        val amount = 100L
        val numberOfThreads = 1000 // 동기처리를 하지 않았을 경우 높은 확률로 테스트 케이스가 실패하는 Thread 갯수

        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(numberOfThreads)

        userPointService.chargeUserPoint(id, point)

        //when
        for (i in 0 until numberOfThreads) {
            executor.submit {
                try {
                    userPointService.useUserPoint(id, amount)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        executor.shutdown()

        //then
        val userPoint = userPointService.getUserPointById(id)
        assertEquals(point -(amount * numberOfThreads), userPoint.point)
    }

    @Test
    fun `포인트 사용, 충전 동시성 테스트`() {
        //given
        val id = 3L
        val amount = 100L
        val numberOfThreads = 1000 // 동기처리를 하지 않았을 경우 높은 확률로 테스트 케이스가 실패하는 Thread 갯수

        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(numberOfThreads)

        //when
        for (i in 0 until numberOfThreads) {
            executor.submit {
                try {
                    userPointService.chargeUserPoint(id, amount)
                    userPointService.useUserPoint(id, amount)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executor.shutdown()

        //then
        val userPoint = userPointService.getUserPointById(id)
        assertEquals(0L, userPoint.point)
    }
}