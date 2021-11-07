package com.oqurystudio.karanel.android.model

enum class Gender(var value: String) {
    LAKI_LAKI("L"),
    PEREMPUAN("P");

    companion object {
        fun getEnum(value: String): Gender {
            for (enum in values()) {
                if (enum.value == value) {
                    return enum
                }
            }
            return LAKI_LAKI
        }

        fun getGenderString(value: String): String {
            for (enum in values()) {
                if (enum.value == value) {
                    return when (enum) {
                        LAKI_LAKI -> "Laki-laki"
                        PEREMPUAN -> "Perempuan"
                    }
                }
            }
            return "Laki-laki"
        }
    }
}