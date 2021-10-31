package com.oqurystudio.karanel.android.model

enum class UserType(val value: String) {
    POSYANDU("posyandu"),
    PARENT("parent");

    companion object {
        fun getEnum(name: String): UserType {
            for (enum in values()) {
                if (enum.value == name) {
                    return enum
                }
            }
            return POSYANDU
        }
    }
}