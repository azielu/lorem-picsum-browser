package com.azielu.lorempicsumbrowser.extensions

import androidx.fragment.app.Fragment

inline fun <reified Listener> Fragment.requireListener(): Listener = when (activity) {
    is Listener -> activity as Listener
    else -> throw ClassCastException(
        "Activity [$activity] must implement ${Listener::class.java.simpleName}"
    )
}
