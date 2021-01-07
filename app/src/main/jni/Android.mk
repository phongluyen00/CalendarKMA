LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS := -llog

LOCAL_MODULE    := security
LOCAL_SRC_FILES := security.c

include $(BUILD_SHARED_LIBRARY)