# SettingViews
Android compound views for managing settings

A collection of compound views that I tend to be using in almost all of my Android projects (because I'm bored by PreferenceFragment).
The views look like the setting-groups of the AOSP settings app.

Types:
- Button Setting
- Radiogroup Setting
- Seekbar Setting
- Switch Setting

<img src="https://raw.githubusercontent.com/vokod/SettingViews/master/screenshots/Screenshot_20190215-140037.png" width="400" >

## Dependency

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Then, add the library to your module `build.gradle`
```	
dependencies {
	        implementation 'com.github.vokod:SettingViews:0.2'
	}
```
