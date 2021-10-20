package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public final class CollectionsConnect {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Collections
    static CollectionReference citizenCollection = db.collection(CollectionName.CITIZENS.name());
    static CollectionReference actionCollection = db.collection(CollectionName.ACTIONS.name());
    static CollectionReference usersCollection = db.collection(CollectionName.USERS.name());
    static CollectionReference citizenActionDetailsCollection = db.collection(CollectionName.CITIZEN_ACTION_DETAILS.name());
    static CollectionReference neighborhoodCollection = db.collection(CollectionName.NEIGHBORHOODS.name());
    static CollectionReference stationCollection = db.collection(CollectionName.STATIONS.name());
    static CollectionReference stationPurchasingCollection = db.collection(CollectionName.STATION_PURCHASING.name());


}
