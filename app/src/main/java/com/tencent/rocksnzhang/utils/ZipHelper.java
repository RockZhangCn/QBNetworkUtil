package com.tencent.rocksnzhang.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by rock on 16-3-18.
 */
public class ZipHelper {

    private static final int BUFFER = 2048;

    private final String[] files;
    private final String zipFile;

    public ZipHelper(String[] files, String zipFile) {
        this.files = files;
        this.zipFile = zipFile;
    }

    private static void replaceWrongZipByte(File zip) throws IOException {
        // @Warnning JUST HANDLE THE SINGLE-FILE-COMPRESSED SITUATION.
        RandomAccessFile r = null;
        try {
            // 保存当前下载分段信息
            r = new RandomAccessFile(zip, "rw");
            if (r != null) {
                int flag = Integer.parseInt("00001000", 2); //wrong byte
                r.seek(7);
                int realFlags = r.read();
                if ((realFlags & flag) > 0) { // in latest versions this bug is fixed, so we're checking is bug exists.
                    r.seek(7);
                    flag = (~flag & 0xff);
                    // removing only wrong bit, other bits remains the same.
                    r.write(realFlags & flag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Zip() {
        FileOutputStream dest = null;
        ZipOutputStream out = null;
        try {
            dest = new FileOutputStream(zipFile);

            out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for (String f : files) {
                FileInputStream fi = null;
                BufferedInputStream origin = null;
                try {
                    fi = new FileInputStream(f);
                    origin = new BufferedInputStream(fi, BUFFER);
                    ZipEntry entry = new ZipEntry(f.substring(f.lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }
                    out.flush();
                    out.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //elikong,coverity cid:31633
                    if (origin != null) {
                        try {
                            origin.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fi != null) {
                        try {
                            fi.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // There is a bug for ZipOutputStream of Android:
            // http://stackoverflow.com/questions/11039079/cannot-extract-file-from-zip-archive-created-on-android-device-os-specific
            // need to replace{ 0, 0x08, 0x08, 0x08, 0 } to { 0, 0x08, 0x00, 0x08, 0 }.
            replaceWrongZipByte(new File(zipFile));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //elikong,coverity cid:31633
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dest != null) {
                try {
                    dest.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
