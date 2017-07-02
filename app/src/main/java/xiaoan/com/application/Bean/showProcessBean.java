package xiaoan.com.application.Bean;

import android.graphics.drawable.Drawable;

/**
 * Created by An on 2017/7/1.
 */

public class showProcessBean {
    public  String names;
    public Drawable icon;
    public  long memsize;
    public  boolean ischeck;//是否被选中
    public  boolean isSystem;//是否被系统应用
    public  String  pkgname;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public long getMemsize() {
        return memsize;
    }

    public void setMemsize(long memsize) {
        this.memsize = memsize;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }
}
