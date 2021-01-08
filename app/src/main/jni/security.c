#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <stdint.h>
#include "malloc.h"

#define DEBUG_TAG "NDK_MainActivity"

void Java_com_example_retrofitrxjava_retrofit_helloLog(JNIEnv *env, jobject this, jstring logThis) {
    jboolean isCopy;
    const char *szLogThis = (*env)->GetStringUTFChars(env, logThis, &isCopy);

    __android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NDK:LC: [%s]", szLogThis);

    (*env)->ReleaseStringUTFChars(env, logThis, szLogThis);
}

jstring Java_com_example_androidndk_MainActivity_getString(JNIEnv *env, jobject this, jint value1,
                                                           jint value2) {
    char *szFormat = "The sum of the two numbers is: %i";
    char *szResult;

    // add the two values
    jlong sum = value1 + value2;

    // malloc room for the resulting string
    szResult = malloc(sizeof(szFormat) + 20);

    // standard sprintf
    sprintf(szResult, szFormat, sum);

    // get an object string
    jstring result = (*env)->NewStringUTF(env, szResult);

    // cleanup
    free(szResult);

    return result;
}

JNIEXPORT jstring JNICALL
Java_com_example_retrofitrxjava_utils_AppUtils_getAPI(JNIEnv *env, jobject instance) {

    return (*env)->  NewStringUTF(env, "http://171.244.133.160/api/Values/");
}

JNIEXPORT jstring JNICALL
Java_com_example_retrofitrxjava_utils_AppUtils_getKeyAES(JNIEnv *env, jobject instance) {

    return (*env)->  NewStringUTF(env, "hocvienkythuatmatma2020");
}

