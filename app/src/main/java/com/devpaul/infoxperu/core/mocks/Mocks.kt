package com.devpaul.infoxperu.core.mocks

import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.models.res.CotizacionItem
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.domain.models.res.Service
import com.devpaul.infoxperu.domain.models.res.UITResponse

data class ContactMock(
    val contactListMock: List<Contact> = listOf(
        Contact(title = "Policía", type = "policia", imageUrl = ""),
        Contact(title = "Bomberos", type = "bombero", imageUrl = ""),
        Contact(title = "Serenazgo", type = "serenazgo", imageUrl = "")
    ),
    val singleContactMock: Contact = Contact(title = "Policía", type = "policia", imageUrl = "")
)

data class ServiceMock(
    val serviceListMock: List<Service> = listOf(
        Service(
            title = "Policia de Ancon",
            numberOne = "2872621",
            numberTwo = "2872620",
            description = "Descripcion de la policia de ancon...",
            imageOne = "",
            imageTwo = "",
            imageThree = "",
        ),
        Service(
            title = "Policía Ancon 2",
            numberOne = "2159595",
            numberTwo = "2926262",
            description = "Descripcion de la policia de ancon 2...",
            imageOne = "",
            imageTwo = "",
            imageThree = "",
        ),
    ),
    val singleServiceMock: Service = Service(
        title = "Policía Ancon",
        numberOne = "2159595",
        numberTwo = "2926262",
        description = "Descripcion de la policia de ancon 2...",
        imageOne = "",
        imageTwo = "",
        imageThree = "",
    ),
)

data class SectionMock(
    val sectionMock: List<SectionItem> = listOf(
        SectionItem(title = "Noticias", type = "news"),
        SectionItem(title = "Distritos", type = "districts")
    )
)

data class GratitudeMock(
    val gratitudeMock: List<Gratitude> = listOf(
        Gratitude(
            image = "https://via.placeholder.com/150",
            title = "Mock Title 1",
            url = "https://example.com",
        ),
        Gratitude(
            image = "https://via.placeholder.com/150",
            title = "Mock Title 2",
            url = "https://example.com",
        )
    )
)

data class DollarQuoteMock(
    val dollarQuoteMock: DollarQuoteResponse = DollarQuoteResponse(
        cotizacion = listOf(
            CotizacionItem(
                compra = 3.61,
                venta = 3.72
            )
        ),
        fecha = "2021-10-10",
    )
)

data class UITMock(
    val uitMock: UITResponse = UITResponse(
        UIT = 123.45,
        periodo = 1,
        servicio = "Mock Service"
    )
)