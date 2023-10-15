package ru.steelwave.steelwave.utils

import ru.steelwave.steelwave.domain.entity.user.UserModel

fun formatName(user: UserModel) = "${user.lastName} ${user.firstName[0]}.${user.middleName[0]}."
