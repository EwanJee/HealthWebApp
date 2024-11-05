package org.prize.healthapp.adapter.out.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    private var deletedAt: LocalDateTime? = null
}
