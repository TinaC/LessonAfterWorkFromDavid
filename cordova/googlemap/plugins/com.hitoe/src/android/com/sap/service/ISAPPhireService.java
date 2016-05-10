/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\I307412\\Desktop\\CTSDemo\\code\\sap\\SAPPhireDemo\\app\\src\\main\\aidl\\com\\sap\\sapphiredemo\\service\\ISAPPhireService.aidl
 */
package com.sap.service;
// Declare any non-default types here with import statements

public interface ISAPPhireService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.sap.service.ISAPPhireService
{
private static final java.lang.String DESCRIPTOR = "com.sap.service.ISAPPhireService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.sap.service.ISAPPhireService interface,
 * generating a proxy if needed.
 */
public static com.sap.service.ISAPPhireService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.sap.service.ISAPPhireService))) {
return ((com.sap.service.ISAPPhireService)iin);
}
return new com.sap.service.ISAPPhireService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_testDlg:
{
data.enforceInterface(DESCRIPTOR);
this.testDlg();
reply.writeNoException();
return true;
}
case TRANSACTION_listSensor:
{
data.enforceInterface(DESCRIPTOR);
com.sap.service.IHitoeSensorListListener _arg0;
_arg0 = com.sap.service.IHitoeSensorListListener.Stub.asInterface(data.readStrongBinder());
com.sap.service.ISAPPhireGPSListener _arg1;
_arg1 = com.sap.service.ISAPPhireGPSListener.Stub.asInterface(data.readStrongBinder());
this.listSensor(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_connectSensor:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.connectSensor(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_disconnectSensor:
{
data.enforceInterface(DESCRIPTOR);
this.disconnectSensor();
reply.writeNoException();
return true;
}
case TRANSACTION_startGPS:
{
data.enforceInterface(DESCRIPTOR);
this.startGPS();
reply.writeNoException();
return true;
}
case TRANSACTION_stopGPS:
{
data.enforceInterface(DESCRIPTOR);
this.stopGPS();
reply.writeNoException();
return true;
}
case TRANSACTION_closeAlertDialog:
{
data.enforceInterface(DESCRIPTOR);
this.closeAlertDialog();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.sap.service.ISAPPhireService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void testDlg() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_testDlg, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void listSensor(com.sap.service.IHitoeSensorListListener listener, com.sap.service.ISAPPhireGPSListener gps) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
_data.writeStrongBinder((((gps!=null))?(gps.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_listSensor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void connectSensor(java.lang.String sSensor) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(sSensor);
mRemote.transact(Stub.TRANSACTION_connectSensor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void disconnectSensor() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disconnectSensor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void startGPS() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startGPS, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopGPS() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopGPS, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void closeAlertDialog() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_closeAlertDialog, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_testDlg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_listSensor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_connectSensor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_disconnectSensor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_startGPS = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_stopGPS = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_closeAlertDialog = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void testDlg() throws android.os.RemoteException;
public void listSensor(com.sap.service.IHitoeSensorListListener listener, com.sap.service.ISAPPhireGPSListener gps) throws android.os.RemoteException;
public void connectSensor(java.lang.String sSensor) throws android.os.RemoteException;
public void disconnectSensor() throws android.os.RemoteException;
public void startGPS() throws android.os.RemoteException;
public void stopGPS() throws android.os.RemoteException;
public void closeAlertDialog() throws android.os.RemoteException;
}
