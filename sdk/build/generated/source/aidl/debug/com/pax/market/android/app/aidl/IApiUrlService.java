/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Users\\faizm\\Documents\\GitHub\\Artajasa_Launcher\\sdk\\src\\main\\aidl\\com\\pax\\market\\android\\app\\aidl\\IApiUrlService.aidl
 */
package com.pax.market.android.app.aidl;
public interface IApiUrlService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.pax.market.android.app.aidl.IApiUrlService
{
private static final java.lang.String DESCRIPTOR = "com.pax.market.android.app.aidl.IApiUrlService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.pax.market.android.app.aidl.IApiUrlService interface,
 * generating a proxy if needed.
 */
public static com.pax.market.android.app.aidl.IApiUrlService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.pax.market.android.app.aidl.IApiUrlService))) {
return ((com.pax.market.android.app.aidl.IApiUrlService)iin);
}
return new com.pax.market.android.app.aidl.IApiUrlService.Stub.Proxy(obj);
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
case TRANSACTION_getApiUrl:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getApiUrl();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getStoreProxyInfo:
{
data.enforceInterface(DESCRIPTOR);
com.pax.market.android.app.sdk.dto.StoreProxyInfo _result = this.getStoreProxyInfo();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.pax.market.android.app.aidl.IApiUrlService
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
@Override public java.lang.String getApiUrl() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getApiUrl, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.pax.market.android.app.sdk.dto.StoreProxyInfo getStoreProxyInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.pax.market.android.app.sdk.dto.StoreProxyInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getStoreProxyInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.pax.market.android.app.sdk.dto.StoreProxyInfo.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getApiUrl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getStoreProxyInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public java.lang.String getApiUrl() throws android.os.RemoteException;
public com.pax.market.android.app.sdk.dto.StoreProxyInfo getStoreProxyInfo() throws android.os.RemoteException;
}
