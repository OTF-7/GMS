# GMS
Gas Management System Android application 
## small note 
 the password for all Activity is the same 123
 the user name is mgr , age , rep , aqe

### for qr scanner code   , I have used this :
#### in gradle.app I will add these : 

 implementation 'com.google.zxing:core:3.4.1'
 implementation 'com.journeyapps:zxing-android-embedded:4.2.0'
 
 #### for java code I will use these instructions :
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("scan");
                integrator.setBeepEnabled(false);
                integrator.initiateScan();
##### note : these do check permission also.

#### and I will use onActivityResult function 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode , resultCode , data);
        if(result !=null)
        {
            if(result.getContents() ==null)
            {
                Toast.makeText(getBaseContext() , "you canceled the scan" , Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext() , result.getContents()  ,Toast.LENGTH_SHORT).show();
            }
        }
       
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
##### notice that the code we have used it for that is the  code int if statement block  and do it before  calling super method
