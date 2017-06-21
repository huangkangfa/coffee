package com.hkf.coffee.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static com.hkf.coffee.others.data.DataMethodUtil.intIP2IPString;


/**
 * 网络辅助类
 * <p>
 * 判断网络是否连接
 * 判断是否是WIFI连接
 * 打开网络设置界面
 * 获取当前wifi名字
 * 获取当前手机的ip地址
 * 将得到的int类型的IP转换为String类型
 * <p>
 * Created by Administrator on 2016/11/8 0008.
 */
public class NetUtil {
    private NetUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否是WIFI连接
     *
     * @param context
     * @return
     */
    public static boolean isWIFI(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT > 10) {
            //3.0以上打开设置界面
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        } else {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        }
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 判断该ip地址是否ping的通
     */
    public static boolean startPing(String ip) {
        boolean success = false;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ping -c 1 -i 0.2 -W 1 " + ip);
            int status = p.waitFor();
            if (status == 0) {
                success = true;
            } else {
                success = false;
            }
        } catch (IOException e) {
            success = false;
        } catch (InterruptedException e) {
            success = false;
        } finally {
            p.destroy();
        }
        return success;
    }

    /**
     * 获取当前wifi名字
     */
    public static String getWifiName(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int wifiState = wifiMgr.getWifiState();
        WifiInfo info = wifiMgr.getConnectionInfo();
        return info != null ? info.getSSID() : null;
    }

    /**
     * 获取当前手机的ip地址
     */
    public static String getLocalIpAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2IPString(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            return "0.0.0.0";
        }
        return null;
    }
}
