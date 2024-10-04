import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.itssinghankit.stockex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.itssinghankit.stockex"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //      For protecting api keys from version controls systems
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream());

        buildConfigField("String", "RAPID_API_KEY", "\"${properties.getProperty("RAPID_API_KEY")}\"");
        buildConfigField("String", "RAPID_API_HOST", "\"${properties.getProperty("RAPID_API_HOST")}\"");
        buildConfigField("String","BASE_URL","\"${properties.getProperty("BASE_URL")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig=true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //hilt
    implementation(libs.hilt.android)
    ksp (libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.symbol.processing.api)

    //coroutines
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)

    //OkHttp
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    //retrofit
    implementation (libs.squareup.retrofit)
    implementation (libs.converter.gson)

    //timber
    implementation( libs.timber)

    //compose preview
    debugImplementation (libs.ui.tooling.preview)

    //coil
    implementation (libs.coil.compose)

    //material library and icons
    implementation (libs.material3)
    implementation (libs.androidx.material.icons.extended)

    //AndroidX Navigation
    implementation (libs.androidx.navigation.compose)

    //collectAsStateWithLifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    //kotlinx serialization
    implementation (libs.kotlinx.serialization.json)

    //vico
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
}