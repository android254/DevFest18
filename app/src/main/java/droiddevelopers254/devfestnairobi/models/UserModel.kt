package droiddevelopers254.devfestnairobi.models

data class UserModel (
        var user_id: String,
        var refresh_token: String?,
        var email: String,
        var user_name: String,
        var photo_url: String
)

