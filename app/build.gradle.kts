import com.android.build.api.dsl.VariantDimension

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.github.uemoo.android.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.uemoo.android.sample"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigStringFiled("BASE_URL", "https://cat-fact.herokuapp.com")
        }
        debug {
            isDebuggable = true
            buildConfigStringFiled("BASE_URL", "https://cat-fact.herokuapp.com")
        }
    }
    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

/**
 * BuildConfigField に文字列を設定する
 */
fun VariantDimension.buildConfigStringFiled(name: String, value: String) {
    val literal = "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
    buildConfigField("String", name, literal)
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)
    implementation(libs.hiltAndroid)
    ksp(libs.hiltCompiler)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.linesdk)
    implementation(libs.hiltNavigationCompose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
