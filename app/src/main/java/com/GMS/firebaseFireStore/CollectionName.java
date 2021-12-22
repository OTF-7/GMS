package com.GMS.firebaseFireStore;

public enum CollectionName {

    COMPANY,
    Notifications,
    Employees,
    USERS,
    CHATTING,
    CITIZENS,
    NATIONS,
    NEIGHBORHOODS,
    STATIONS,
    ACTIONS,
    ACTION_DETAILS,
    CITIZEN_ACTION_DETAILS,
    DAILY_ACTIONS,

    STATION_PURCHASING,
    ADDITION_REQUESTS;

    public enum StorageFolder {
        CITIZENPICSOFDOCUMENT
    }

    public enum Fields {
        id,
        name,
        neighborhood,
        hireDate,
        telephones,
        address,
        district,
        quarter,
        userName,
        password,
        socialAccounts,
        userType,
        email,
        state,
        regret,
        firstName ,
        secondName ,
        lastName ,
        serialNumber,

        // citizen document ;

        familyMember,
        numberOfCylinders,
        numberOfFamilies,
        aqelAddition,
        stationName,
        repConfirmation,
        dateOfConfirmation,
        document,
        stateOfConfirmation,
        details,


        numberOfDelivered, purchasingPrice, purchasedQuantity, latitude,
        longitude,
        representativeCertain,
        dateCertain,
        aqelVerified,
        repVerified,
        delivered,
        received,
        deliveringDate,
        receivingDate,
        deliveredQuantity,
        total,
        agentName,
        purchasingDate,
        facebook,
        google,
        actionDate,
        fullName,
        deliveredState,
        neighborhoodDetails,
        sellingPrice,
        /*
         *NOTICE THIS NOTE BELOW FOR MORE INFO FOR CONTENT OF EACH DOCUMENT
         ** EACH EMPLOYEE DOCUMENT CONTAINS THESE :
         * {
         * ID , NAME , TELEPHONE[ARRAY], ADDRESS { DISTRICT ,  NEIGHBORHOOD , QUARTER}
         *  , USER_NAME  , PASSWORD ,SOCIAL_ACCOUNTS , TYPE_OF_JOB , HIRE_DATE
         * }
         *
         ** EACH NEIGHBORHOOD DOCUMENT CONTAINS THESE :
         * {
         * ID , NAME ,TELEPHONES[ARRAY] , AQEL_DETAILS { NAME , PICTURE ,USER_NAME , PASSWORD ,TELEPHONES[ARRAY] , SOCIAL_ACCOUNTS[ARRAY] , HIRE_DATE}
         * NUMBER_OF_FAMILIES ,LOCATION {LATITUDE , LONGITUDE}
         * }
         ** EACH STATIONS DOCUMENT CONTAINS THESE :
         * {
         * ID ,NAME ,ICON , TELEPHONES[ARRAY] , LOCATION{LATITUDE , LONGITUDE}
         * }
         ** EACH DAILY_ACTIONS DOCUMENT CONTAINS THESE :
         * {
         * ID ,AQEL_NAME , REP_NAME , AGENT_NAME ,SELLING_PRICE ,DELIVERED_QUANTITY ,ACTION_DATE
         *  ,NEIGHBORHOOD_DETAILS { NEIGHBORHOOD_NAME ,NUMBER_OF_DELIVERS }
         *  ,STATION_DETAILS { NAME ,PURCHASING_PRICE , PURCHASED_QUANTITY}
         * }
         ** ACH STATION_PURCHASING_DETAILS DOCUMENT CONTAINS THESE :
         *  {
         *  ID , PURCHASING_PRICE , PURCHASED_QUANTITY ,AGENT_NAME ,PURCHASING_DATE
         * }
         ** EACH CITIZEN_ACTION_DETAILS DOCUMENT CONTAINS THESE :
         *  {
         *  ID , NAME , DELIVERED_QUANTITY ,TOTAL ,
         *  DELIVERED_STATE { AQEL_VERIFIED , REP_VERIFIED , DELIVERED , RECEIVED }
         * , DELIVERING_DATE , RECEIVING_DATE
         * }
         *
         ** EACH CITIZEN DOCUMENT CONTAINS THESE :
         * { NAME , NEIGHBORHOOD , NUMBER_OF_FAMILY , NUMBER_OF_CYLINDER , HIRE_DATE ,
         * DOCUMENT_PIC  , AQEL_ADDITION , STATE_OF_CONFIRMATION ,REP_CONFIRMATION
         *  , DATA_OF_CONFIRMATION}
         */


    }
}
