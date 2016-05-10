LOCAL_PATH:= $(call my-dir)


MY_LIB_DIR = ../../../../../../lib/



include $(CLEAR_VARS)
LOCAL_ARM_MODE := arm
LOCAL_CPPFLAGS += -lpthread
LOCAL_CPPFLAGS += -D_LINUX
LOCAL_CPPFLAGS += -fexceptions 
LOCAL_CPPFLAGS += -frtti 
LOCAL_CPPFLAGS += -ggdb
# LOCAL_CFLAGS := -g
# LOCAL_CFLAGS += -D_LINUX
LOCAL_MODULE := MemoryPool
LOCAL_WHOLE_STATIC_LIBRARIES += android_support
LOCAL_SRC_FILES := $(MY_LIB_DIR)MemoryPool/memory.cpp
include $(BUILD_STATIC_LIBRARY)








include $(CLEAR_VARS)
LOCAL_ARM_MODE := arm
LOCAL_CPPFLAGS += -lpthread
LOCAL_CPPFLAGS += -D_LINUX
LOCAL_CPPFLAGS += -fexceptions 
LOCAL_CPPFLAGS += -frtti 
LOCAL_CPPFLAGS += -ggdb
# LOCAL_CFLAGS := -g
# LOCAL_CFLAGS += -D_LINUX
LOCAL_MODULE := StdLibrary
LOCAL_C_INCLUDES = $(LOCAL_PATH)/$(MY_LIB_DIR)MemoryPool/
LOCAL_WHOLE_STATIC_LIBRARIES += android_support
LOCAL_SRC_FILES := $(MY_LIB_DIR)StdLibrary/base64.cpp \
					$(MY_LIB_DIR)StdLibrary/cachefile.cpp \
					$(MY_LIB_DIR)StdLibrary/datetime.cpp \
					$(MY_LIB_DIR)StdLibrary/des.cpp \
					$(MY_LIB_DIR)StdLibrary/exception.cpp \
					$(MY_LIB_DIR)StdLibrary/file.cpp \
					$(MY_LIB_DIR)StdLibrary/garbagecollection.cpp \
					$(MY_LIB_DIR)StdLibrary/gcmutex.cpp \
					$(MY_LIB_DIR)StdLibrary/gcstring.cpp \
					$(MY_LIB_DIR)StdLibrary/gcthread.cpp \
					$(MY_LIB_DIR)StdLibrary/gcvariant.cpp \
					$(MY_LIB_DIR)StdLibrary/guidhelper.cpp \
					$(MY_LIB_DIR)StdLibrary/javaclass.cpp \
					$(MY_LIB_DIR)StdLibrary/javaexception.cpp \
					$(MY_LIB_DIR)StdLibrary/javastring.cpp \
					$(MY_LIB_DIR)StdLibrary/json.cpp \
					$(MY_LIB_DIR)StdLibrary/logfile.cpp \
					$(MY_LIB_DIR)StdLibrary/md5.cpp \
					$(MY_LIB_DIR)StdLibrary/mutex.cpp \
					$(MY_LIB_DIR)StdLibrary/mymath.cpp \
					$(MY_LIB_DIR)StdLibrary/mystring.cpp \
					$(MY_LIB_DIR)StdLibrary/sha1.cpp \
					$(MY_LIB_DIR)StdLibrary/stream.cpp \
					$(MY_LIB_DIR)StdLibrary/thread.cpp \
					$(MY_LIB_DIR)StdLibrary/threadpool.cpp \
					$(MY_LIB_DIR)StdLibrary/variant.cpp \
					$(MY_LIB_DIR)StdLibrary/xml.cpp \
					$(MY_LIB_DIR)StdLibrary/xmlbase.cpp
					
include $(BUILD_STATIC_LIBRARY)






















include $(CLEAR_VARS)
LOCAL_ARM_MODE := arm
LOCAL_C_INCLUDES = $(LOCAL_PATH)/$(MY_LIB_DIR)StdLibrary/ \
			$(LOCAL_PATH)/$(MY_LIB_DIR)MemoryPool/
LOCAL_WHOLE_STATIC_LIBRARIES += android_support
LOCAL_CPPFLAGS += -lpthread
LOCAL_CPPFLAGS += -D_LINUX
LOCAL_CPPFLAGS += -fexceptions 
LOCAL_CPPFLAGS += -frtti 
LOCAL_CPPFLAGS += -ggdb
# LOCAL_CFLAGS := -g
# LOCAL_CFLAGS += -D_LINUX
LOCAL_MODULE := NetLibrary

LOCAL_SRC_FILES := $(MY_LIB_DIR)NetLibrary/HTTPDynamic.cpp \
					$(MY_LIB_DIR)NetLibrary/HTTPEPoll.cpp \
					$(MY_LIB_DIR)NetLibrary/HTTPHelper.cpp \
					$(MY_LIB_DIR)NetLibrary/HTTPRecvProc.cpp \
					$(MY_LIB_DIR)NetLibrary/MyEPollClient.cpp \
					$(MY_LIB_DIR)NetLibrary/MyHTTP.cpp \
					$(MY_LIB_DIR)NetLibrary/NetLibrary.cpp \
					$(MY_LIB_DIR)NetLibrary/NetRecvThread.cpp \
					$(MY_LIB_DIR)NetLibrary/NetSendThread.cpp \
					$(MY_LIB_DIR)NetLibrary/RTP.cpp \
					$(MY_LIB_DIR)NetLibrary/SocketHelper.cpp \
					$(MY_LIB_DIR)NetLibrary/WebSocket.cpp
include $(BUILD_STATIC_LIBRARY)










include $(CLEAR_VARS)
LOCAL_MODULE := hitoe
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libHitoeSDK.so
include $(PREBUILT_SHARED_LIBRARY)







include $(CLEAR_VARS)
LOCAL_MODULE := hitoeeai
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libHitoeSdkEAI.so
include $(PREBUILT_SHARED_LIBRARY)










include $(CLEAR_VARS)
include $(all-subdir-makefiles)
LOCAL_ARM_MODE := arm
LOCAL_C_INCLUDES = $(LOCAL_PATH)/$(MY_LIB_DIR)MemoryPool/ \
					$(LOCAL_PATH)/$(MY_LIB_DIR)StdLibrary/ \
					$(LOCAL_PATH)/$(MY_LIB_DIR)NetLibrary/
					
LOCAL_WHOLE_STATIC_LIBRARIES += android_support
LOCAL_LDLIBS := -lm -llog -lGLESv1_CM -landroid
LOCAL_CPPFLAGS += -lpthread
LOCAL_CPPFLAGS += -D_LINUX
LOCAL_CPPFLAGS += -fexceptions 
LOCAL_CPPFLAGS += -frtti 
LOCAL_CPPFLAGS += -ggdb
# LOCAL_CFLAGS := -g
# LOCAL_CFLAGS += -D_LINUX
LOCAL_MODULE := swlib

LOCAL_SRC_FILES := sapphire.cpp
					
LOCAL_STATIC_LIBRARIES := NetLibrary \
				StdLibrary \
				MemoryPool
							
include $(BUILD_SHARED_LIBRARY)

$(call import-module, android/support)