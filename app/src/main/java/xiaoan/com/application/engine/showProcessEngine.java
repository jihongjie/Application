package xiaoan.com.application.engine;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xiaoan.com.application.Bean.showProcessBean;
import xiaoan.com.application.Utils.ShellUtils;

/**
 * Created by An on 2017/7/1.
 */

public class showProcessEngine  {

    private static StringBuilder[] ss;
    private static FileReader fileReader;
    private static BufferedReader bf;

    public  static  boolean haspermission(Context ctx){

    AppOpsManager systemService = (AppOpsManager) ctx.getSystemService(Context.APP_OPS_SERVICE);
    int mode=0;
    if (Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){
        mode=systemService.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,android.os.Process.myUid(),ctx.getPackageName());

    }
    return  mode==AppOpsManager.MODE_ALLOWED;

}

    public  static  StringBuilder [] mem(Context c){
        try {
            fileReader = new FileReader("/proc/meminfo");
            bf = new BufferedReader(fileReader);
            String s = bf.readLine();
            String b= bf.readLine();
            char[] chars = s.toCharArray();
            char[] chars1 = b.toCharArray();
            StringBuilder str=new StringBuilder();
            StringBuilder str1=new StringBuilder();

            for (char a:chars){
                if (a>='0'&&a<='9'){
                    str.append(a);
                }
            }
            for (char bb:chars1){
                if (bb>='0'&&bb<='9'){
                    str1.append(bb);
                }
            }

            ss = new StringBuilder[2];
            ss[0]=str;
            ss[1]=str1;


        } catch (Exception e) {

        }finally {
            if (fileReader==null&&bf==null){
                try {
                    fileReader.close();
                    bf.close();
                } catch (IOException e) {
                }
            }
        }

        return  ss;
    }

    public  static List<showProcessBean> getAllApp(Context context){
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand("ps|grep u0_", true);
        String s = commandResult.successMsg;
        Log.e("result",s);
        List<showProcessBean> arr=new ArrayList<showProcessBean>();
         PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo ls:installedPackages){
            showProcessBean showP= new showProcessBean();
            showP.setPkgname(ls.packageName);

            showP.setIcon(ls.applicationInfo.loadIcon(packageManager));
            showP.setNames(ls.applicationInfo.loadLabel(packageManager).toString());
            int flg=ls.applicationInfo.flags;
            if ((flg & ApplicationInfo.FLAG_SYSTEM)==ApplicationInfo.FLAG_SYSTEM){
                showP.setSystem(true);

            }else {
                showP.setSystem(false);
            }
            if (s.contains(ls.packageName)){
                arr.add(showP);
            }
        }
        return  arr;

    }



}
