package com.devpaul.core_platform.fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.devpaul.core_platform.entity.Communicable
import com.devpaul.core_platform.entity.ResultAction

/**
 * Clave utilizada para almacenar el valor de resultado en un conjunto de datos (Bundle) al enviar resultados
 * entre fragmentos o actividades.
 */
const val RESULT_FRAGMENT_VALUE_KEY = "result_fragment_value"

/**
 * Envía un resultado desde una FragmentActivity al fragmento receptor, junto con un [Communicable], un valor genérico [T],
 * una acción opcional y una etiqueta opcional.
 *
 * @param result El par [Communicable] y valor [T] que se envía como resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 */
inline fun <reified T> FragmentActivity.sendResult(
    result: Pair<Communicable, T>,
    action: ResultAction? = null,
    tag: String = ""
) {
    val data = try {
        bundleOf(RESULT_FRAGMENT_VALUE_KEY to result.second)
    } catch (_: Exception){
        bundleOf()
    }
    val finalKey = createTag(result.first, action, tag)
    supportFragmentManager.setFragmentResult(finalKey, data)
}

/**
 * Envía un resultado desde un fragmento al fragmento receptor de la FragmentActivity,
 * junto con un [Communicable], un valor genérico [T], una acción opcional y una etiqueta opcional.
 *
 * @param communicable El objeto [Communicable] que identifica el tipo de resultado.
 * @param value El valor genérico [T] que se envía como resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 */
inline fun <reified T> Fragment.sendResult(
    communicable: Communicable,
    value: T,
    action: ResultAction? = null,
    tag: String = ""
) {
    requireActivity().sendResult(communicable to value, action, tag)
}

/**
 * Envía un resultado vacío desde un fragmento al fragmento receptor de la FragmentActivity,
 * junto con un [Communicable], una acción opcional y una etiqueta opcional.
 *
 * @param communicable El objeto [Communicable] que identifica el tipo de resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 */
fun Fragment.sendResult(
    communicable: Communicable,
    action: ResultAction? = null,
    tag: String = ""
) {
    this@sendResult.sendResult(communicable, Unit, tag = tag, action = action)
}

/**
 * Escucha un resultado enviado por un fragmento receptor de la FragmentActivity,
 * correspondiente a un [Communicable] y una acción opcionales, y ejecuta un bloque con el valor resultante [T].
 *
 * @param communicable El objeto [Communicable] que identifica el tipo de resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 * @param block El bloque que se ejecuta con el valor resultante [T].
 */
@Suppress("DEPRECATION")
inline fun <reified T> FragmentActivity.onResult(
    communicable: Communicable,
    action: ResultAction? = null,
    tag: String = "",
    crossinline block: (T) -> Unit
){
    try {
        val finalKey = createTag(communicable, action, tag)
        supportFragmentManager.setFragmentResultListener(finalKey, this) { _, bundle ->
            if(Unit is T){
                block(Unit)
            } else {
                bundle.get(RESULT_FRAGMENT_VALUE_KEY).let {
                    if(it is T){
                        block(it)
                    }
                }
            }
        }
    }catch (_: Exception) {}
}

/**
 * Escucha un resultado vacío enviado por un fragmento receptor de la FragmentActivity,
 * correspondiente a un [Communicable] y una acción opcionales, y ejecuta un bloque sin valor resultante.
 *
 * @param communicable El objeto [Communicable] que identifica el tipo de resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 * @param block El bloque que se ejecuta cuando se recibe el resultado vacío.
 */
inline fun FragmentActivity.onResult(
    communicable: Communicable,
    action: ResultAction? = null,
    tag: String = "",
    crossinline block: () -> Unit
){
    onResult<Unit>(communicable, tag = tag, action = action) {
        block()
    }
}

/**
 * Escucha un resultado enviado por un fragmento receptor de la FragmentActivity,
 * correspondiente a un [Communicable] y una acción opcionales, y ejecuta un bloque con el valor resultante [T].
 *
 * @param communicable El objeto [Communicable] que identifica el tipo de resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 * @param block El bloque que se ejecuta con el valor resultante [T].
 */
inline fun <reified T> Fragment.onResult(
    communicable: Communicable,
    action: ResultAction? = null,
    tag: String = "",
    crossinline block: (T) -> Unit
){
    requireActivity().onResult(communicable, action, tag, block)
}

/**
 * Escucha un resultado vacío enviado por un fragmento receptor de la FragmentActivity,
 * correspondiente a un [Communicable] y una acción opcionales, y ejecuta un bloque sin valor resultante.
 *
 * @param communicable El objeto [Communicable] que identifica el tipo de resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 * @param block El bloque que se ejecuta cuando se recibe el resultado vacío.
 */
inline fun Fragment.onResult(
    communicable: Communicable,
    action: ResultAction? = null,
    tag: String = "",
    crossinline block: () -> Unit
){
    onResult<Unit>(communicable, tag = tag, action = action) {
        block()
    }
}

/**
 * Crea una etiqueta única para identificar un resultado utilizando un [Communicable], una acción opcional y una etiqueta opcional.
 *
 * @param communicable El objeto [Communicable] que identifica el tipo de resultado.
 * @param action La acción opcional que especifica el tipo de resultado.
 * @param tag La etiqueta opcional que se agrega al resultado.
 * @return Una etiqueta única generada para identificar el resultado.
 */
fun createTag(
    communicable: Communicable,
    action: ResultAction?,
    tag: String
): String {
    return "${communicable.tag}$tag${action?.tag ?: ""}"
}

/**
 * Crea un objeto [Communicable] utilizando una etiqueta específica.
 *
 * @param tag La etiqueta utilizada para el objeto [Communicable].
 * @return Un objeto [Communicable] con la etiqueta especificada.
 */
fun communicableTag(tag: String): Communicable {
    return object: Communicable {
        override val tag: String = tag
    }
}