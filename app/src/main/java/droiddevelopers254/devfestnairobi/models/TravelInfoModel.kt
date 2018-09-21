package droiddevelopers254.devfestnairobi.models

data class TravelInfoModel (
        val shuttleInfo: String,
        val publicTransportationInfo: String,
        val carpoolingParkingInfo: String,
        val bikingInfo: String,
        val rideSharingInfo: String

){
    override fun toString(): String {
        return "TravelInfoModel{" +
                "shuttleInfo='" + shuttleInfo + '\''.toString() +
                ", publicTransportationInfo='" + publicTransportationInfo + '\''.toString() +
                ", carpoolingParkingInfo='" + carpoolingParkingInfo + '\''.toString() +
                ", bikingInfo='" + bikingInfo + '\''.toString() +
                '}'.toString()
    }
}


