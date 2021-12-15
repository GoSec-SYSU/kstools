package cn.wjdiankong.jw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.wjdiankong.kstools.AnalysisApk;
import cn.wjdiankong.kstools.ApkSign;

public class DoWorkUtils {

    public static ArrayList<String> allDexList = new ArrayList<String>();
    public static ArrayList<String> errorDexList = new ArrayList<String>();


    public static int dexId = 1;

    public static String dexFileNameGetter() {
        String res = "classes" + (dexId == 1 ? "" : Integer.toString(dexId));
        res += ".dex";
        dexId++;
        return res;
    }

    public static String smaliFileNameGetter(int i) {
        String ans = "smali";
        ans += i == 0 ? "" : "_classes" + Integer.toString(i);
        return ans;
    }


    /**
     * 获取应用签名信息
     *
     * @param srcApkFile
     * @return
     */
    public static boolean getAppSign(File srcApkFile) {
        try {
            long time = System.currentTimeMillis();
            System.out.println("第一步==> 获取apk文件签名信息");
            String sign = ApkSign.getApkSignInfo(srcApkFile.getAbsolutePath());
            Const.appSign = sign;
            System.out.println("signed:" + sign);
            System.out.println("获取apk签名信息成功===耗时:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Exception e) {
            System.out.println("获取apk签名信息失败，退出！:" + e.toString());
            return false;
        }
    }

    /**
     * 获取应用入口类
     */
    public static boolean getAppEnter(File srcApkFile) {
        try {
            long time = System.currentTimeMillis();
            System.out.println("第二步==> 获取apk文件入口信息");
            String enter = AnalysisApk.getAppEnterApplication(srcApkFile.getAbsolutePath());
            Const.entryClassName = enter.replace(".", "/");
            System.out.println("应用入口类:" + enter);
            System.out.println("获取apk入口类信息成功===耗时:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Exception e) {
            System.out.println("获取apk入口类信息失败，退出！:" + e.toString());
            FileUtils.printException(e);
            return false;
        }
    }

    /**
     * 解压apk
     */
    public static boolean zipApkWork(File srcApkFile, String unZipDir) {
        try {
            long time = System.currentTimeMillis();
            System.out.println("第三步==> 解压apk文件:" + srcApkFile.getAbsolutePath());
            FileUtils.decompressDexFile(srcApkFile.getAbsolutePath(), unZipDir);
            System.out.println("解压apk文件结束===耗时:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("解压apk文件失败，退出！:" + e.toString());
            return false;
        }
    }

    // 替换掉检测微信的
    public static void main(String[] args) {
        String path = "E:\\研究生计网助教工作";
        removeAllCheckWechat(path);
    }

    public static boolean removeAllCheckWechat(String fileDir) {
        File file = new File(fileDir);
        if (!file.exists()) {
            System.out.println(String.format("文件：%s，不存在", fileDir));
            return false;
        }
        removeAllCheckWechat(file);
        return true;
    }

    private static void removeAllCheckWechat(File file) {
        if (file == null) return;
        if (file.isFile()) {
            removeWechatCheck(file);
            return;
        }
        File[] files = file.listFiles();
        for (File childFile : files) {
            removeAllCheckWechat(childFile);
        }
    }
    ///////////////////////////////////////////////////////////

    private static void removeWechatCheck(File file) {
        System.out.println(file);
    }

    /**
     * 删除签名文件
     */
    public static boolean deleteMetaInf(String unZipDir, String aaptCmdDir, String srcApkPath) {
        try {
            long time = System.currentTimeMillis();
            File metaFile = new File(unZipDir + Const.METAINFO);
            System.out.println("第四步==> 删除签名文件");
            if (metaFile.exists()) {
                File[] metaFileList = metaFile.listFiles();
                File aaptFile = new File(aaptCmdDir);
                String cmd = aaptFile.getAbsolutePath() + " remove " + new File(srcApkPath).getAbsolutePath();
                for (File f : metaFileList) {
                    cmd = cmd + " " + Const.METAINFO + f.getName();
                }
                System.out.println("删除签名文件命令:" + cmd);
                execCmd(cmd, true);
            }
            System.out.println("删除签名文件结束===耗时:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("删除签名文件失败，退出！:" + e.toString());
            return false;
        }
    }

    /**
     * 将dex转化成smali
     */
    public static boolean dexToSmali(String unZipPath, String smaliDir) {
        File smaliDirF = new File(smaliDir);
        if (smaliDirF.exists()) {
            smaliDirF.delete();
        }
        smaliDirF.mkdirs();
        System.out.println("第五步==> 将dex转化成smali");

        File unZipFile = new File(unZipPath);
        if (!unZipFile.exists()) {
            System.out.println(String.format("文件路径: %s, 不存在", unZipFile));
            return false;
        }
        File[] unZipFileList = unZipFile.listFiles();
        List<String> dexFileList = new ArrayList<>();
        for (File file : unZipFileList) {
            if (file.getName().endsWith(".dex")) {
                dexFileList.add(file.getName());
            }
        }
        System.out.println(String.format("共%d个dex文件", dexFileList.size()));
        // 为包存储，防止方法数超过65535
        for (int i = 0; i < dexFileList.size(); i++) {
            String dexFileName = dexFileList.get(i);
            String curSmaliDir = smaliDir + File.separator + smaliFileNameGetter(i);
            String curDexFile = unZipPath + dexFileName;
            String javaCmd = "java -jar libs" + File.separator + "baksmali.jar -o " + curSmaliDir + " " + curDexFile;
            System.out.println(javaCmd);
            long startTime = System.currentTimeMillis();
            try {
                Process pro = Runtime.getRuntime().exec(javaCmd);
                int status = pro.waitFor();
                if (status == 0) {
                    System.out.println("转化第" + i + "个dex文件为smali成功===耗时:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
                }
            } catch (Exception e) {
                System.out.println("转化第" + i + "个dex文件为smali失败:" + e.toString());
                return false;
            }
        }
        // 直接将所有smali放在一个文件的版本
//        for (int i = 0; i < dexFileList.size(); i++) {
//            String dexFileName = dexFileList.get(i);
//            String javaCmd = "java -jar libs" + File.separator + "baksmali.jar -o " + smaliDir + " " + unZipPath + dexFileName;
//            System.out.println(javaCmd);
//            long startTime = System.currentTimeMillis();
//            try {
//                Process pro = Runtime.getRuntime().exec(javaCmd);
//                int status = pro.waitFor();
//                if (status == 0) {
//                    System.out.println("转化第" + i + "个dex文件为smali成功===耗时:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
//                }
//            } catch (Exception e) {
//                System.out.println("转化第" + i + "个dex文件为smali失败:" + e.toString());
//                return false;
//            }
//        }
        return true;
    }

    /**
     * 替换原始签名和包名
     */
    public static boolean setSignAndPkgName() {
        System.out.println("第六步==> 代码中替换原始签名和包名信息");
        String smaliTmpPath = JWMain.rootPath + Const.smaliTmpDir;
        File smaliTmpFile = new File(smaliTmpPath);
        if (!smaliTmpFile.exists()) {
            System.out.println(String.format("dex文件：%s, 不存在", smaliTmpPath));
            return false;
        }
        int dexFileNum = Objects.requireNonNull(smaliTmpFile.listFiles()).length;
        File pmsSmaliDirF = new File(smaliTmpPath + File.separator + smaliFileNameGetter(dexFileNum) + File.separator + Const.pmsSmaliDir);

        if (!pmsSmaliDirF.exists()) {
            pmsSmaliDirF.mkdirs();
        }
        FileReader reader = null;
        BufferedReader br = null;
        FileWriter writer = null;
        try {
            long startTime = System.currentTimeMillis();
            FileUtils.fileCopy(JWMain.rootPath + File.separator + Const.smaliFileHandler, pmsSmaliDirF.getAbsolutePath() + File.separator + Const.smaliFileHandler);
            writer = new FileWriter(pmsSmaliDirF.getAbsolutePath() + File.separator + Const.smaliFilePMS);
            reader = new FileReader(JWMain.rootPath + File.separator + Const.smaliFilePMS);
            br = new BufferedReader(reader);
            String str = null;
            while ((str = br.readLine()) != null) {
                if (str.contains(Const.signLineTag)) {
                    writer.write(str + "\n");
                    String signStr = "\tconst-string v0, \"" + Const.appSign + "\"";
                    writer.write(signStr + "\n");
                    br.readLine();
                }
                if (str.contains(Const.pkgNameLineTag)) {
                    String pkgNameStr = "\tconst-string v1, \"" + Const.appPkgName + "\"";
                    writer.write(pkgNameStr + "\n");
                    br.readLine();
                } else {
                    writer.write(str + "\n");
                }
            }
            System.out.println("设置签名和包名成功===耗时:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
            return true;
        } catch (Exception e) {
            System.out.println("设置签名和包名失败:" + e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * 插入hook代码
     */
    public static boolean insertHookCode() {
        System.out.println("第七步==> 添加hook代码");
        long startTime = System.currentTimeMillis();

        String smaliClassDir = JWMain.rootPath + Const.smaliTmpDir + File.separator;
        File smaliClassDirFile = new File(smaliClassDir);
        if (!smaliClassDirFile.exists()) {
            System.out.println(String.format("文件路径：%s， 不存在", smaliClassDir));
            return false;
        }
        String enterFile = "";
        String enterFileTmp = "";
        boolean hasEntry = false;

        for (File smaliClassFile : smaliClassDirFile.listFiles()) {
            enterFile = smaliClassDir + smaliClassFile.getName() + File.separator + Const.entryClassName.replace(".", File.separator) + ".smali";
            System.out.println(enterFile);
            enterFileTmp = smaliClassDir + smaliClassFile.getName() + File.separator + Const.entryClassName.replace(".", File.separator) + "_tmp.smali";
            File tmpEnterFile = new File(enterFile);
            if (tmpEnterFile.exists()) {
                hasEntry = true;
                break;
            }
        }
        if (!hasEntry) {
            System.out.println(String.format("没有找到入口smali文件: %s", Const.entryClassName));
            return false;
        }
        FileReader reader = null;
        BufferedReader br = null;
        FileWriter writer = null;
        boolean isWorkSucc = false;
        try {
            reader = new FileReader(enterFile);
            br = new BufferedReader(reader);
            writer = new FileWriter(enterFileTmp);
            String str = null;
            boolean isSucc = false;
            int isEntryMethod = -1;
            while ((str = br.readLine()) != null) {
                if (isSucc) {
                    writer.write(str + "\n");
                    continue;
                }
                if (Const.isApplicationEntry) {
                    if (str.contains(Const.applicationAttachLineTag)) {
                        isEntryMethod = 0;
                    } else if (str.contains(Const.applicationCreateLineTag)) {
                        isEntryMethod = 1;
                    }
                } else {
                    if (str.contains(Const.activityCreateLineTag)) {
                        isEntryMethod = 2;
                    }
                }
                if (str.contains(Const.methodEndStr)) {
                    isEntryMethod = -1;
                }

                writer.write(str + "\n");

                if (isEntryMethod == 0) {
                    writer.write(Const.hookAttachCodeStr);
                    isSucc = true;
                } else if (isEntryMethod == 1) {
                    writer.write(Const.hookCreateCodeStr);
                    isSucc = true;
                } else if (isEntryMethod == 2) {
                    writer.write(Const.hookCreateCodeStr);
                    isSucc = true;
                }
            }
            System.out.println("插入hook代码成功===耗时" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
            isWorkSucc = true;
        } catch (Exception e) {
            System.out.println("插入hook代码失败:" + e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                }
            }
        }

        File entryFile = new File(enterFile);
        entryFile.delete();
        File entryFileTmp = new File(enterFileTmp);
        entryFileTmp.renameTo(new File(enterFile));

        return isWorkSucc;
    }

    /**
     * 将smali转化成dex
     */
    public static boolean smaliToDex(String smaliDir, String tmpDexDir) {
        System.out.println("第八步==> 将smali转化成dex");
        if (!FileUtils.fileIsExist(smaliDir)) {
            System.out.println(String.format("文件%s，不存在", smaliDir));
            return false;
        }
        if (!FileUtils.fileIsExist(tmpDexDir)) {
            File file = new File(tmpDexDir);
            file.mkdirs();
        }
        List<String> childSmaliFileList = FileUtils.getChildFileName(smaliDir);
        System.out.println(String.format("共%d个smali文件", childSmaliFileList.size()));
        for (String childSmaliFile : childSmaliFileList) {
            String dexFile = tmpDexDir + File.separator + dexFileNameGetter();
            childSmaliFile = smaliDir + File.separator + childSmaliFile;
            File dexFileF = new File(dexFile);
            if (dexFileF.exists()) {
                dexFileF.delete();
            }
            String javaCmd = "java -jar libs" + File.separator + "smali.jar " + childSmaliFile + " -o " + dexFile;
            System.out.println(javaCmd);
            long startTime = System.currentTimeMillis();
            try {
                Process pro = Runtime.getRuntime().exec(javaCmd);
                int status = pro.waitFor();
                if (status == 0) {
                    System.out.println("smali转化dex成功===耗时:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
                }
            } catch (Exception e) {
                System.out.println("smali转化dex失败:" + e.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * 使用aapt命令添加dex文件到apk中
     */
    public static boolean addDexToApk(String aaptCmdDir, String tmpDexDir, String srcApkPath) {
        try {
            System.out.println("第九步==> 将dex文件添加到源apk中");
            long time = System.currentTimeMillis();
            File aaptFile = new File(aaptCmdDir);
            String cmd = aaptFile.getAbsolutePath() + " remove " + new File(srcApkPath).getAbsolutePath();
            File classDir = new File(tmpDexDir);
            File[] classListFile = classDir.listFiles();
            // 最后一个是新加的动态代理dex，所以最后一个不处理
            for (int i = 0; i < classListFile.length - 1; i++) {
                File file = classListFile[i];
                if (file.getName().endsWith(".dex")) {
                    cmd = cmd + " " + file.getName();
                }
            }
            System.out.println("cmd:" + cmd);
            if (!execCmd(cmd, true)) {
                System.out.println("删除apk中的dex文件失败，退出！");
                return false;
            }

            String addCmd = aaptFile.getAbsolutePath() + " add " + new File(srcApkPath).getAbsolutePath();

            // 复制到当前目录，因为aapt命令太傻逼了....
            for (File file : classListFile) {
                if (file.getName().endsWith(".dex")) {
                    FileUtils.fileCopy(tmpDexDir + File.separator + file.getName(), file.getName());
                }
            }

            for (File file : classListFile) {
                if (file.getName().endsWith(".dex")) {
                    addCmd = addCmd + " " + file.getName();
                }
            }

            System.out.println("cmd:" + addCmd);
            if (!execCmd(addCmd, true)) {
                System.out.println("添加dex文件到apk中失败，退出！");
                return false;
            }
            // 然后在删除
            for (File file : classListFile) {
                if (file.getName().endsWith(".dex")) {
                    FileUtils.deleteFile(file.getName());
                }
            }
            System.out.println("添加dex文件到apk中结束===耗时:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("添加dex文件到apk中失败，退出！:" + e.toString());
            return false;
        }
    }

    /**
     * 签名apk文件
     */
    public static boolean signApk(String srcApkPath, String rootPath) {
        try {
            System.out.println("第十步==> 开始签名apk文件:" + srcApkPath);
            long time = System.currentTimeMillis();
            String keystore = "cyy_game.keystore";
            File signFile = new File(rootPath + File.separator + keystore);
            if (!signFile.exists()) {
                System.out.println("签名文件:" + signFile.getAbsolutePath() + " 不存在，需要自己手动签名");
                return false;
            }
            String storePass = "cyy1888";
            StringBuilder signCmd = new StringBuilder("jarsigner.exe");
            signCmd.append(" -verbose -keystore ");
            signCmd.append(keystore);
            signCmd.append(" -storepass ");
            signCmd.append(storePass);
            signCmd.append(" -signedjar ");
            signCmd.append("signed.apk ");
            signCmd.append(srcApkPath + " ");
            signCmd.append(keystore + " ");
            signCmd.append("-digestalg SHA1 -sigalg MD5withRSA");
            execCmd(signCmd.toString(), false);
            System.out.println("签名apk文件结束===耗时:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("重新签名apk文件失败，退出！:" + e.toString());
            return false;
        }
    }

    /**
     * 清理删除工作
     */
    public static void deleteTmpFile(String rootPath) {
        //删除解压之后的目录
        FileUtils.deleteDirectory(rootPath + Const.unZipDir);
        //删除smali目录
        FileUtils.deleteDirectory(rootPath + Const.smaliTmpDir);
        //删除临时dex文件目录
        FileUtils.deleteDirectory(rootPath + File.separator + Const.tmpdexPath);
//        //删除临时dex文件
//        FileUtils.deleteFile(rootPath+File.separator+"classes.dex");
    }

    /**
     * 执行命令
     *
     * @param cmd
     * @param isOutputLog
     * @return
     */
    public static boolean execCmd(String cmd, boolean isOutputLog) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (isOutputLog)
                    System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("cmd error:" + e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}