LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := \
    android-common \
    statsservice-lib-lockscreen \
    appupdate-lib-lockscreen \
    dxbase-lib-lockscreen

LOCAL_SRC_FILES := $(call all-subdir-java-files)
LOCAL_PACKAGE_NAME := DX-LockScreen
LOCAL_MODULE := dx-lockscreen
LOCAL_CERTIFICATE := shared

LOCAL_PROGUARD_FLAG_FILES := proguard.flags

#LOCAL_PROGUARD_ENABLED := full

#LOCAL_PROGUARD_FLAGS := -dontobfuscate
include $(BUILD_STATIC_JAVA_LIBRARY)
##################################################
include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := statsservice-lib-lockscreen:libs/DXStatsService-2.1.0-30.jar \
                        appupdate-lib-lockscreen:libs/appupdate-lib-1.8.1-2.jar \
                        dxbase-lib-lockscreen:libs/DXBase-1.7.0-23.jar
include $(BUILD_MULTI_PREBUILT)

# Use the folloing include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
