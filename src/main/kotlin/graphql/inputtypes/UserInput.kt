package graphql.inputtypes

import org.hibernate.validator.constraints.Length
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotEmpty

data class UserInput(
    @field:NotNull
    @field:NotEmpty
    @field:Length(min = 3)
    val username: String,
    @field:NotNull
    @field:NotEmpty
    @field:Length(min = 3)
    val password: String
)
