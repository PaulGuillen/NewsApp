package com.devpaul.emergency.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.PoliceEntity
import com.devpaul.emergency.domain.repository.EmergencyRepository
import org.koin.core.annotation.Factory

@Factory
class PoliceUC(
    private val emergencyRepository: EmergencyRepository
) {

    suspend fun policeService(type: String): Output<PoliceEntity, Throwable> {
        return try {
            val policeEntity: PoliceEntity = emergencyRepository.policeService(type)

            val expanded = policeEntity.copy(
                data = policeEntity.data.flatMap { item ->
                    val phones = splitPhones(item.phone)

                    if (phones.isEmpty()) listOf(item)
                    else {
                        phones.map { phone ->
                            item.copy(phone = phone.trim())
                        }
                    }
                }
                    .distinctBy { normalizeDigits(it.phone) + it.district + it.address }
            )

            Output.Success(data = expanded)
        } catch (ex: Exception) {
            Output.Failure(error = ex)
        }
    }

}

private val PHONE_SEPARATORS_REGEX = Regex("""[|\n,;/]+""")

fun splitPhones(raw: String?): List<String> {
    if (raw.isNullOrBlank()) return emptyList()

    return raw
        .split(PHONE_SEPARATORS_REGEX)
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .distinct()
}

fun normalizeDigits(phone: String): String = phone.filter { it.isDigit() }