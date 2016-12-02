package com.fanyafeng.recreation.eventbus;

import com.squareup.otto.Bus;

import java.io.File;

/**
 * Author： fanyafeng
 * Data： 16/10/27 12:51
 * Email: fanyafeng@live.cn
 */
public class FileExploreEvents {
    private static final Bus BUS = new Bus();

    public static Bus getBus() {
        return BUS;
    }

    private FileExploreEvents() {

    }

    public static class OnClickFile {
        public File mFile;

        public OnClickFile(String path) {
            this(new File(path));
        }

        public OnClickFile(File file) {
            mFile = file;
        }
    }
}
