package com.devpaul.home.data.datasource.mock

import com.devpaul.home.domain.entity.DollarQuoteDataEntity
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeDataEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.PriceItemEntity
import com.devpaul.home.domain.entity.SectionDataEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITDataEntity
import com.devpaul.home.domain.entity.UITEntity

data class DollarQuoteMock(
    val dollarQuoteMock: DollarQuoteEntity = DollarQuoteEntity(
        status = 200,
        message = "Success",
        data = DollarQuoteDataEntity(
            service = "Mock Service",
            site = "Mock Site",
            link = "https:example.com",
            prices = listOf(
                PriceItemEntity(
                    buy = 3.61,
                    sell = 3.72
                )
            ),
            note = "Mock Note",
            usdToEuro = "1.23",
            date = "2021-10-10",
        )
    )
)

data class UITMock(
    val uitMock: UITEntity = UITEntity(
        status = 200,
        message = "Success",
        data = UITDataEntity(
            value = 123.45,
            year = 1,
            service = "Mock Service"
        )
    )
)

data class GratitudeMock(
    val gratitudeMock: GratitudeEntity = GratitudeEntity(
        status = 200,
        message = "Success",
        data = listOf(
            GratitudeDataEntity(
                id = "1",
                imageUrl = "https:via.placeholder.com/150",
                title = "Mock Title 1",
                link = "https:example.com"
            ),
            GratitudeDataEntity(
                id = "2",
                imageUrl = "https:via.placeholder.com/150",
                title = "Mock Title 2",
                link = "https:example.com"
            )
        )
    )
)

data class SectionMock(
    val sectionMock: SectionEntity = SectionEntity(
        status = 200,
        message = "Success",
        data = listOf(
            SectionDataEntity(id = "1", title = "Noticias", type = "news"),
            SectionDataEntity(id = "2", title = "Distritos", type = "districts")
        )
    )
)