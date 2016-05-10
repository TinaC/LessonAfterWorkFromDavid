# Build both ARMv5TE and ARMv7-A machine code.
APP_ABI := armeabi armeabi-v7a #x86
APP_OPTIM := debug
APP_STL := gnustl_static
APP_CFLAGS := -g -lm -llog -D_ANDROID  -frtti
APP_PLATFORM := android-14
# APP_CFLAGS += -malign-double
