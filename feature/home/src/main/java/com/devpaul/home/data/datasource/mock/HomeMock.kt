package com.devpaul.home.data.datasource.mock

import com.devpaul.core_data.model.Gratitude
import com.devpaul.core_data.model.SectionItem
import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.DollarQuoteData
import com.devpaul.home.data.datasource.dto.response.PriceItem
import com.devpaul.home.data.datasource.dto.response.UITData
import com.devpaul.home.data.datasource.dto.response.UITResponse

data class DollarQuoteMock(
    val dollarQuoteMock: DollarQuoteResponse = DollarQuoteResponse(
        status = 200,
        message = "Success",
        data = DollarQuoteData(
            service = "Mock Service",
            site = "Mock Site",
            link = "https:example.com",
            prices = listOf(
                PriceItem(
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
    val uitMock: UITResponse = UITResponse(
        status = 200,
        message = "Success",
        data = UITData(
            value = 123.45,
            year = 1,
            service = "Mock Service"
        )
    )
)

data class GratitudeMock(
    val gratitudeMock: List<Gratitude> = listOf(
        Gratitude(
            image = "https:via.placeholder.com/150",
            title = "Mock Title 1",
            url = "https:example.com",
        ),
        Gratitude(
            image = "https:via.placeholder.com/150",
            title = "Mock Title 2",
            url = "https:example.com",
        )
    )
)

data class SectionMock(
    val sectionMock: List<SectionItem> = listOf(
        SectionItem(title = "Noticias", type = "news"),
        SectionItem(title = "Distritos", type = "districts")
    )
)