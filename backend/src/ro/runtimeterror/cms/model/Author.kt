package ro.runtimeterror.cms.model

data class Author(
    val name: String,
    val email: String
)

fun User.toAuthor(): Author
{
    return Author(name, email)
}