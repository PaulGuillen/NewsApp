package com.devpaul.navigation.core

import com.devpaul.navigation.core.Constants.REPLACE_ARG_NAME
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer

class ModularDestination(
    internal val kSerializer: KSerializer<*>,
) {

    @OptIn(ExperimentalSerializationApi::class)
    private val baseRoute = kSerializer.descriptor.serialName.replace(".", "_")
    internal val route = "$baseRoute/$REPLACE_ARG_NAME"
}