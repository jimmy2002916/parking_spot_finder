public class Entity {

    public String phoneName;
    public String online;
    public String city;
    public String loginTime;
    public String userId;

    public Entity() {
    }

    public Entity(String phoneName, String online, String city, String loginTime, String userId) {
        this.phoneName = phoneName;
        this.online = online;
        this.city = city;
        this.loginTime = loginTime;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "phoneName='" + phoneName + '\'' +
                ", online='" + online + '\'' +
                ", city='" + city + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) { this.phoneName = phoneName; }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }
}

//public class Entity {
//
//    public String ID;
//    public String CELLID;
//    public String NAME;
//    public String DAY;
//    public String HOUR;
//    public String PAY;
//    public String PAYCASH;
//    public String MEMO;
//    public String ROADID;
//    public String CELLSTATUS;
//    public String ISNOWCASH;
//    public String ParkingStatus;
//    public String lat;
//    public String lon;
//
//    public Entity() {
//    }
//
//    public Entity(String ID, String CELLID, String NAME, String DAY, String HOUR,String PAY, String PAYCASH,
//                  String MEMO,String ROADID,String CELLSTATUS,String ISNOWCASH,String ParkingStatus,String lat,String lon) {
//
//        this.ID = ID;
//        this.CELLID = CELLID;
//        this.NAME = NAME;
//        this.DAY = DAY;
//        this.HOUR = HOUR;
//        this.PAY = PAY;
//        this.PAYCASH = PAYCASH;
//        this.MEMO = MEMO;
//        this.ROADID = ROADID;
//        this.CELLSTATUS = CELLSTATUS;
//        this.ISNOWCASH = ISNOWCASH;
//        this.ParkingStatus = ParkingStatus;
//        this.lat = lat;
//        this.lon = lon;
//
//    }
//
//    @Override
//    public String toString() {
//        return "Entity{" +
//                "ID='" + ID + '\'' +
//                ", CELLID='" + CELLID + '\'' +
//                ", NAME='" + NAME + '\'' +
//                ", DAY='" + DAY + '\'' +
//                ", DAY='" + HOUR + '\'' +
//                ", DAY='" + PAY + '\'' +
//                ", DAY='" + PAYCASH + '\'' +
//                ", DAY='" + MEMO + '\'' +
//                ", DAY='" + ROADID + '\'' +
//                ", DAY='" + CELLSTATUS + '\'' +
//                ", DAY='" + ISNOWCASH + '\'' +
//                ", DAY='" + ParkingStatus + '\'' +
//                ", DAY='" + lat + '\'' +
//                ", DAY='" + lon + '\'' +
//                '}';
//    }
//
//    public String getID() {
//        return ID;
//    }
//
//    public void setID(String ID) {
//        this.ID = ID;
//    }
//
//    public String getCELLID() {
//        return CELLID;
//    }
//
//    public void setCELLID(String CELLID) {
//        this.CELLID = CELLID;
//    }
//
//    public String getNAME() {
//        return NAME;
//    }
//
//    public void setNAME(String NAME) {
//        this.NAME = NAME;
//    }
//
//    public String getDAY() {
//        return DAY;
//    }
//
//    public void setDAY(String DAY) {
//        this.DAY = DAY;
//    }
//
//    public String getHOUR() {
//        return HOUR;
//    }
//
//    public void setHOUR(String HOUR) {
//        this.HOUR = HOUR;
//    }
//
//    public String getPAY() {
//        return PAY;
//    }
//
//    public void setPAY(String PAY) {
//        this.PAY = PAY;
//    }
//
//    public String getPAYCASH() {
//        return PAYCASH;
//    }
//
//    public void setPAYCASH(String PAYCASH) {
//        this.PAYCASH = PAYCASH;
//    }
//
//    public String getMEMO() {
//        return MEMO;
//    }
//
//    public void setMEMO(String MEMO) {
//        this.MEMO = MEMO;
//    }
//
//    public String getROADID() {
//        return ROADID;
//    }
//
//    public void setROADID(String ROADID) {
//        this.ROADID = ROADID;
//    }
//
//    public String getCELLSTATUS() {
//        return CELLSTATUS;
//    }
//
//    public void setCELLSTATUS(String CELLSTATUS) {
//        this.CELLSTATUS = CELLSTATUS;
//    }
//
//    public String getISNOWCASH() {
//        return ISNOWCASH;
//    }
//
//    public void setISNOWCASH(String ISNOWCASH) {
//        this.ISNOWCASH = ISNOWCASH;
//    }
//
//    public String getParkingStatus() {
//        return ParkingStatus;
//    }
//
//    public void setParkingStatus(String parkingStatus) {
//        ParkingStatus = parkingStatus;
//    }
//
//    public String getLat() {
//        return lat;
//    }
//
//    public void setLat(String lat) {
//        this.lat = lat;
//    }
//
//    public String getLon() {
//        return lon;
//    }
//
//    public void setLon(String lon) {
//        this.lon = lon;
//    }
//}
