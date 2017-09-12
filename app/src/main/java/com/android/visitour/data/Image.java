package com.android.visitour.data;

/**
 * Created by Miguel Aicrag on 8/11/2017.
 */

public class Image
{

    public String filesname;
    public String uri;


    public Image()
    {

    }
    public Image(String filesname, String uri) {
        this.filesname = filesname;
        this.uri = uri;
    }

    public String getFilesname() {
        return filesname;
    }

    public String getUri() {
        return uri;
    }
}
