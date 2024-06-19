package io.hhplus.tdd.point.controller

import io.hhplus.tdd.point.domain.PointHistory
import io.hhplus.tdd.point.domain.PointHistoryService
import io.hhplus.tdd.point.domain.UserPoint
import io.hhplus.tdd.point.domain.UserPointService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/point")
class PointController(private val userPointService: UserPointService,
                      private val pointHistoryService: PointHistoryService) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    fun point(
        @PathVariable id: Long,
    ): UserPoint {
        logger.info("Get point by id: $id")
        return userPointService.getUserPointById(id)
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    fun history(
        @PathVariable id: Long,
    ): List<PointHistory> {
        logger.info("Get point List by userId: $id")
        return pointHistoryService.getAllByUserId(id)
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    fun charge(
        @PathVariable id: Long,
        @RequestBody amount: Long
    ): UserPoint {
        logger.info("포인트 업데이트 id : $id, amount : $amount")

        if(amount < 0) {
            val message = "충전 금액은 최소 1원 이상 부터 가능합니다. 현재 충전 요청 금액 : $amount"
            throw IllegalArgumentException(message)
        }

        return userPointService.chargeUserPoint(id, amount)
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    fun use(
        @PathVariable id: Long,
        @RequestBody amount: Long,
    ): UserPoint {
        logger.info("포인트 사용 id : $id, amount : $amount")

        if(amount < 0) {
            val message = "사용 금액은 최소 0원 이상 부터 가능합니다. 현재 사용 요청 금액 : $amount"
            throw IllegalArgumentException(message)
        }

        return userPointService.useUserPoint(id, amount);
    }
}