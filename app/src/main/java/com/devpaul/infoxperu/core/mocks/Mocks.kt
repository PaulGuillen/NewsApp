package com.devpaul.infoxperu.core.mocks

import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.models.res.Service

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
