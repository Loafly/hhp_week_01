package io.hhplus.tdd.point.domain

import io.hhplus.tdd.point.infra.UserPointRepository
import org.springframework.stereotype.Service

@Service
class UserPointService(private val userPointRepository: UserPointRepository) {

    // 유저 포인트 조회
    fun getUserPointById(id: Long): UserPoint {
        return userPointRepository.findById(id)
    }
}