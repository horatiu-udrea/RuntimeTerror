package ro.runtimeterror.cms.networking.dto

import ro.runtimeterror.cms.model.User

data class MemberDTO(val userId: Int, val name: String)

fun User.toMemberDTO(): MemberDTO
{
    return MemberDTO(userId, name)
}