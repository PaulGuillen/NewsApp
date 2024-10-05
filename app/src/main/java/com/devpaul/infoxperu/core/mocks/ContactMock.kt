package com.devpaul.infoxperu.core.mocks

import com.devpaul.infoxperu.domain.models.res.Contact

data class ContactMock(
    val contactListMock: List<Contact> = listOf(
        Contact(title = "Policía", type = "policia", imageUrl = ""),
        Contact(title = "Bomberos", type = "bombero", imageUrl = ""),
        Contact(title = "Serenazgo", type = "serenazgo", imageUrl = "")
    ),
    val singleContactMock: Contact = Contact(title = "Policía", type = "policia", imageUrl = "")
)
