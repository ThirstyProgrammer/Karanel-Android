package com.oqurystudio.karanel.android.model

class FormProgress {

    data class Payload(
        var parentId: String = "",
        var childId: String = "",
        var record: Record = Record()
    )
}