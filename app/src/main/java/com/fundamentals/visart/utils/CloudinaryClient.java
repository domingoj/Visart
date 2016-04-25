package com.fundamentals.visart.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jermiedomingo on 3/11/16.
 */
public class CloudinaryClient {

    public static void upload(File newFile, final String publicId) {
        final File file = newFile;


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Cloudinary cloudinary = new Cloudinary(Constants.CLOUDINARY_URL);

                    cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", publicId));

                } catch (IOException e) {
                    //TODO: better error handling when image uploading fails
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
    }

    public static String generateUrl(String fileName) {
        Cloudinary cloudinary = new Cloudinary(Constants.CLOUDINARY_URL);
        return cloudinary.url().generate(fileName);

    }


}
