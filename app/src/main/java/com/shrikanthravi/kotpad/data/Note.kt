package com.shrikanthravi.kotpad.data

import java.io.Serializable
import java.util.*

data class Note(
    var id: Int = -1,
    var title: String? = null,
    var text: String? = null,
    var isPinned: Boolean = false,
    var createdAt: Date = Date(),
    var updatedAt: Date? = null
) :Serializable