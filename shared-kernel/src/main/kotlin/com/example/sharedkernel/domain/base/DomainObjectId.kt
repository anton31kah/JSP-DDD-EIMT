package com.example.sharedkernel.domain.base

import java.util.*
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class DomainObjectId(val id: String) : ValueObject {
    companion object {
        fun <ID : DomainObjectId> randomId(idClass: Class<ID>): ID {
            try {
                return idClass.getConstructor(String::class.java).newInstance(UUID.randomUUID().toString())
            } catch (ex: Exception) {
                throw RuntimeException("Could not create new instance of $idClass", ex)
            }
        }
    }
}
