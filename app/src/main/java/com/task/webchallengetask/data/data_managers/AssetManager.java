package com.task.webchallengetask.data.data_managers;

import com.task.webchallengetask.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class AssetManager {

    public static File getFile(String fileName) {
        InputStream in;
        File file = new File(App.getAppContext().getCacheDir().getPath() + "/temp");
        try {

            in = App.getAppContext().getAssets().open(fileName);
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
