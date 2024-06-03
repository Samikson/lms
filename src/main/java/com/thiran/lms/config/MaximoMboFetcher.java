package com.thiran.lms.config;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXException;

public class MaximoMboFetcher {

    public static void main(String[] args) {
        try {
            // To Initialize The Maximo Server Object
            MXServer mxServer = MXServer.getMXServer();

            // Get a user session for System User
            UserInfo userInfo = mxServer.getSystemUserInfo();

            // Get Session Based Upon the Particular User
            UserInfo userInfo = mxServer.getUserInfo("your_username", "your_password");


            // Fetch the Asset MboSet
            MboSetRemote assetSet = mxServer.getMboSet("ASSET", userInfo);

            // Iterate through the  for printing records.
            MboRemote asset;
            int count = 0;
            while ((asset = assetSet.getMbo(count)) != null) {
                // Print Assetnum and Description
                System.out.println("Assetnum: " + asset.getString("ASSETNUM"));
                System.out.println("Description: " + asset.getString("DESCRIPTION"));
                System.out.println("Location: " + asset.getString("LOCATION"));
                count++;
            }

            assetSet.cleanup();
            assetSet.close();

        } catch (MXException | RemoteException e) {
            e.printStackTrace();
        }
    }
}

