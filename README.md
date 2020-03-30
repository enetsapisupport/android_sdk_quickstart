# ENETS SDK Android Quickstart

### This is a sample project to demonstrate how to use the ENETS SDK to make a payment.

See the ENETS Merchant Integration Guide at the [ENETS Developer Portal](https://developer.nets.com.sg) for full integration details, as well as to download the UAT and Production versions of the SDK.  

**Note:** If you want to receive the response from the ENETS backend, you need to replace the Dummy URLs in ther Txn Request with your own urls.

### Common Questions
**Q: What permissions do I need to include in my manifest to enable the SDK to function properly?**  
*A: You should not need to declare any permissions. During the build process, the permissions declared in the AAR manifest should be merged into the Merged Manifest automatically. If you would like to declare them anyway for clarity or other purposes, they are:*  
- *`android.permission.INTERNET`*  
- *`android.permission.READ_PHONE_STATE`*  
- *`android.permission.ACCESS_NETWORK_STATE`*  

**Q: Do i need to declare any dependencies in my gradle file?**  
*A: Most likely, you will already be using these dependencies, but ensure that the following **3** support library modules are declared.*  
- *`'com.android.support:appcompat-v7:28.0.0'`*  
- *`'com.android.support.constraint:constraint-layout:1.1.3'`*  
- *`'com.android.support:design:28.0.0'`*  

**Q: Why am I encountering errors with the dexer when building my project/syncing gradle?**  
*A: Our SDK bundles some 3rd party libraries inside as jars. If your project has declared dependencies on one or more of the same libraries, there will be a namespace conflict which will appear as an error while dexing*  

**Q: How do i resolve the dex errors?**  
*A: You can configure gradle to exclude the offending dependency(which will mean you will end up using the bundled library jar), or you can remove the jar from the ENETS aar file in order to use your preferred version. See the end of the page on details of how to do both*  
  
**Q: What library jars are included inside the SDK?**  
*A: okhttp, okio, gson, guava, spongycastle, bolt-android*

**Q: What is the minumum supported Android SDK Version?**  
*A: The minSdkVersion is set to 18(Android 4.3/Jellybean)*

### How to exclude dependencies from gradle
Assuming we are using `Retrofit` in our project and encountering dex errors with `Okhttp`.  
We can exclude OkHttp as a gradle dependency and use the version packaged in the ENETS aar by adding the following to our app module build.gradle file.  
```groovy
configurations.all{
    exclude group: 'com.squareup.okhttp3', module: 'okhttp'
}
```

### How to remove a jar from the ENETS SDK aar file
Unpack the aar, remove the unwanted class files and then pack the aar(Example below).  
Assuming we are encountering dex errors with `Guava`, which uses the package `com.google.common`.  
First, open a terminal window and cd to the containing folder.  
Then, run the following commands to create a modified jar without the Guava jar.
```bash
unzip enetslib-release_1.2.1.aar -d tmpenets
cd tmpeents
unzip classes.jar -d tmp
cd tmp
rm -rf com/google/common
rm -rf ../classes.jar
zip -r ../classes.jar *
cd ..
zip -r ../enetslib-release_1.2.1-noguava.aar *
cd ..
rm -rf tmpenets/
```
