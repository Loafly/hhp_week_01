package io.hhplus.tdd.point.domain

import org.springframework.stereotype.Service

@Service
class UserPointService {

    // 유저 포인트 조회
    fun getUserPointById(id: Long): UserPoint {
        return UserPoint(0, 0, 0);
    }
}