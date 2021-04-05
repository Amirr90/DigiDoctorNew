package com.getmedcheck.lib.model;

import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.getmedcheck.lib.constant.Constants;

import java.util.Objects;

public class BleDevice implements Parcelable {

    private BluetoothDevice device;
    private String displayName = "";
    private String deviceName = "";
    private String macAddress = "";
    private String status = Constants.BLE_STATUS_NONE;

    public BleDevice() {
    }

    public BleDevice(BluetoothDevice device) {
        this.device = device;
        deviceName = device.getName();
        macAddress = device.getAddress();
    }

    public BleDevice(BluetoothDevice device, String status) {
        this.device = device;
        deviceName = device.getName();
        macAddress = device.getAddress();
        this.status = status;
    }

    protected BleDevice(Parcel in) {
        device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        displayName = in.readString();
        deviceName = in.readString();
        macAddress = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(device, flags);
        dest.writeString(displayName);
        dest.writeString(deviceName);
        dest.writeString(macAddress);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BleDevice> CREATOR = new Creator<BleDevice>() {
        @Override
        public BleDevice createFromParcel(Parcel in) {
            return new BleDevice(in);
        }

        @Override
        public BleDevice[] newArray(int size) {
            return new BleDevice[size];
        }
    };

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
        deviceName = device.getName();
        macAddress = device.getAddress();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BleDevice bleDevice = (BleDevice) o;

        if (!Objects.equals(device, bleDevice.device))
            return false;
        if (!Objects.equals(displayName, bleDevice.displayName))
            return false;
        if (!Objects.equals(deviceName, bleDevice.deviceName))
            return false;
        if (!Objects.equals(macAddress, bleDevice.macAddress))
            return false;
        return Objects.equals(status, bleDevice.status);
    }

    @Override
    public int hashCode() {
        int result = device != null ? device.hashCode() : 0;
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        result = 31 * result + (macAddress != null ? macAddress.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BleDevice{" +
                "device=" + device +
                ", displayName='" + displayName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
