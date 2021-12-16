package com.GMS.firebaseFireStore;

import androidx.annotation.NonNull;

import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FireBaseConnection {

    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Collections
    protected CollectionReference citizenCollection = db.collection(CollectionName.CITIZENS.name());
    protected CollectionReference actionCollection = db.collection(CollectionName.ACTIONS.name());
    protected CollectionReference usersCollection = db.collection(CollectionName.USERS.name());


    public class Addition {
        ArrayList<CitizenItemOfAqel> items = new ArrayList<>();
        CollectionReference mActionDetails;
        private boolean state;

        Addition() {
            state = false;
            // empty constructor
        }
        /*
        public boolean additionRequestAdd(CitizenCollection mDocument)
        {

            Map<String ,Object> data = new HashMap<>();
            data.put(CollectionName.Fields.name.name(),mDocument.getName() );
            data.put(CollectionName.Fields.neighborhood.name(),mDocument.getNeighborhood() );
            data.put(CollectionName.Fields.hireDate.name(), mDocument.getHireDate());
            data.put(CollectionName.Fields.familyMember.name(),mDocument.getFamilyMember() );
            data.put(CollectionName.Fields.aqelAddition.name(),mDocument.getAqelAddition() );
            data.put(CollectionName.Fields.numberOfCylinders.name(),mDocument.getNumberOfCylinders() );
            data.put(CollectionName.Fields.repConfirmation.name(),mDocument.getRepConfirmation() );
            data.put(CollectionName.Fields.dateOfConfirmation.name(),mDocument.getDateOfConfirmation() );
            data.put(CollectionName.Fields.stateOfConfirmation.name(),mDocument.isStateOfConfirmation());

            CollectionsConnect.citizenCollection.add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    state =true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   state = false;
                }
            });

           return state;
        }

         */

        public boolean addUser(EmployeesCollection mEmployeesCollection) {
            usersCollection.add(mEmployeesCollection).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    state = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    state = false;
                }
            });
            return false;
        }

        public boolean addAction(ActionCollection mActionCollection) {
            actionCollection.add(mActionCollection).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    state = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    state = false;
                }
            });
            return state;
        }
    }


}

