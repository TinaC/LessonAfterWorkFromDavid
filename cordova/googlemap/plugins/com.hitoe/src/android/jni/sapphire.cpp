#include "datetime.h"
#include "gcthread.h"
#include "netcomm.h"
#include "javaclass.h"
#include "javastring.h"
#include "MyHTTP.h"
#include "datetime.h"

#include "sapphire.h"

JavaVM*					gJavaVM;
bool					gInited;

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    gInited = false;
    gJavaVM = vm;
    return JNI_VERSION_1_4;
}




void Java_com_sap_sapphiredemo_service_NativeHelp_init(JNIEnv *env, jclass obj) {
    if(!gInited) {
        gInited = true;
        Debug::Init(_T("SWLib"));
        GC::Init();
        DateTime::Init();
        GlobalAssistentThread::CreateGlobalAssistentThread();
        InitNetLibrary();
        JavaClass::Init(env);
    }
}


void Java_com_sap_sapphiredemo_service_NativeHelp_uninit(JNIEnv *env, jclass obj) {
    if(!gInited) {
        UninitNetLibrary();
        GlobalAssistentThread::DestroyGlobalAssistentThread();
        DateTime::Uninit();
        GC::Statistics();
        GC::Uninit();
        Debug::Uninit();
        JavaClass::Uninit(env);
        gInited = false;
    }
}

void Java_com_sap_sapphiredemo_service_NativeHelp_postJson(JNIEnv *env, jclass obj, jstring sURL, jstring sJson) {
    if(gInited) {
        Pointer<HTTP> http = XNew HTTP();
        http->SetRecord(true);
        JavaString surl(sURL);
        JavaString sjson(sJson);
        http->AddExtraHeader(_T("Authorization"), _T("Basic Q1RTX0xJVkU6QWExMTExMTE="));
        if(http->PostJSON((const TCHAR*)surl, (const TCHAR*)sjson)) {
//            Debug::Write(_T("%s"), (TCHAR*)s);
//            CMyStream* pstream = http->GetRecord();
//            Debug::Write(_T("%s"), (TCHAR*)(char*)*pstream);
            Debug::Write(_T("Post JSON return %d"), http->GetStatus());
        }
        http = NULL;
    }
}

jlong Java_com_sap_sapphiredemo_service_NativeHelp_getCurTimestamp(JNIEnv *env, jclass obj) {
    __uint64 t = DateTime::GetTime();
    return t / 1000;
}



jlong Java_com_sap_sapphiredemo_service_NativeHelp_openLocationFile(JNIEnv *env, jclass obj, jstring sFolder) {
    JavaString jsFolder(sFolder);
    CString s((const TCHAR*)jsFolder);
    s += _T("/location.txt");
    CFile* f = XNew CFile(s, _T("w+b"));
    return (long)f;
}

void Java_com_sap_sapphiredemo_service_NativeHelp_writeLocation(JNIEnv *env, jclass obj, jlong lFile, jdouble longitude, jdouble latitude) {
    CFile *f = (CFile*)lFile;
    TCHAR sz[1024];
    _stprintf(sz, _T("%f,%f"), longitude, latitude);
    f->WriteLine(sz);
}

void Java_com_sap_sapphiredemo_service_NativeHelp_closeLocationFile(JNIEnv *env, jclass obj, jlong lFile) {
    CFile *f = (CFile*)lFile;
    f->Close();
    XDelete f;
}