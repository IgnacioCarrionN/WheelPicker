# WheelPicker
Endless loop scroll picker for Android.

![WheelPicker](https://user-images.githubusercontent.com/5654013/62576131-88244c80-b89c-11e9-8008-479015e0ba64.png)

TargetSdkVersion = 29
MinSdkVersion = 11

## Install

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency
```groovy
dependencies {
  implementation 'com.github.IgnacioCarrionN:WheelPicker:0.0.1-Alpha02'
}
```

## Usage

### XML
```xml
<dev.carrion.wheelpicker.WheelPicker
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
```

### Int Range
```kotlin
wheelPicker.init(0..23)
```

### List
```kotlin
wheelPicker.init(listOf(1,2,3,4,5))
```

### Custom adapter
- Create an Adapter extending BaseWheelAdapter.
- Create a ViewHolder extending BaseWheelViewHolder.

```kotlin
wheelPicker.init(adapter)
```
