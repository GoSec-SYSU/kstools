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
     * ��ȡӦ��ǩ����Ϣ
     *
     * @param srcApkFile
     * @return
     */
    public static boolean getAppSign(File srcApkFile) {
        try {
            long time = System.currentTimeMillis();
            System.out.println("��һ��==> ��ȡapk�ļ�ǩ����Ϣ");
            String sign = ApkSign.getApkSignInfo(srcApkFile.getAbsolutePath());
            Const.appSign = sign;
            System.out.println("signed:" + sign);
            System.out.println("��ȡapkǩ����Ϣ�ɹ�===��ʱ:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Exception e) {
            System.out.println("��ȡapkǩ����Ϣʧ�ܣ��˳���:" + e.toString());
            return false;
        }
    }

    /**
     * ��ȡӦ�������
     */
    public static boolean getAppEnter(File srcApkFile) {
        try {
            long time = System.currentTimeMillis();
            System.out.println("�ڶ���==> ��ȡapk�ļ������Ϣ");
            String enter = AnalysisApk.getAppEnterApplication(srcApkFile.getAbsolutePath());
            Const.entryClassName = enter.replace(".", "/");
            System.out.println("Ӧ�������:" + enter);
            System.out.println("��ȡapk�������Ϣ�ɹ�===��ʱ:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Exception e) {
            System.out.println("��ȡapk�������Ϣʧ�ܣ��˳���:" + e.toString());
            FileUtils.printException(e);
            return false;
        }
    }

    /**
     * ��ѹapk
     */
    public static boolean zipApkWork(File srcApkFile, String unZipDir) {
        try {
            long time = System.currentTimeMillis();
            System.out.println("������==> ��ѹapk�ļ�:" + srcApkFile.getAbsolutePath());
            FileUtils.decompressDexFile(srcApkFile.getAbsolutePath(), unZipDir);
            System.out.println("��ѹapk�ļ�����===��ʱ:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("��ѹapk�ļ�ʧ�ܣ��˳���:" + e.toString());
            return false;
        }
    }

    // �滻�����΢�ŵ�
    public static void main(String[] args) {
        String path = "E:\\�о����������̹���";
        removeAllCheckWechat(path);
    }

    public static boolean removeAllCheckWechat(String fileDir) {
        File file = new File(fileDir);
        if (!file.exists()) {
            System.out.println(String.format("�ļ���%s��������", fileDir));
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
     * ɾ��ǩ���ļ�
     */
    public static boolean deleteMetaInf(String unZipDir, String aaptCmdDir, String srcApkPath) {
        try {
            long time = System.currentTimeMillis();
            File metaFile = new File(unZipDir + Const.METAINFO);
            System.out.println("���Ĳ�==> ɾ��ǩ���ļ�");
            if (metaFile.exists()) {
                File[] metaFileList = metaFile.listFiles();
                File aaptFile = new File(aaptCmdDir);
                String cmd = aaptFile.getAbsolutePath() + " remove " + new File(srcApkPath).getAbsolutePath();
                for (File f : metaFileList) {
                    cmd = cmd + " " + Const.METAINFO + f.getName();
                }
                System.out.println("ɾ��ǩ���ļ�����:" + cmd);
                execCmd(cmd, true);
            }
            System.out.println("ɾ��ǩ���ļ�����===��ʱ:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("ɾ��ǩ���ļ�ʧ�ܣ��˳���:" + e.toString());
            return false;
        }
    }

    /**
     * ��dexת����smali
     */
    public static boolean dexToSmali(String unZipPath, String smaliDir) {
        File smaliDirF = new File(smaliDir);
        if (smaliDirF.exists()) {
            smaliDirF.delete();
        }
        smaliDirF.mkdirs();
        System.out.println("���岽==> ��dexת����smali");

        File unZipFile = new File(unZipPath);
        if (!unZipFile.exists()) {
            System.out.println(String.format("�ļ�·��: %s, ������", unZipFile));
            return false;
        }
        File[] unZipFileList = unZipFile.listFiles();
        List<String> dexFileList = new ArrayList<>();
        for (File file : unZipFileList) {
            if (file.getName().endsWith(".dex")) {
                dexFileList.add(file.getName());
            }
        }
        System.out.println(String.format("��%d��dex�ļ�", dexFileList.size()));
        // Ϊ���洢����ֹ����������65535
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
                    System.out.println("ת����" + i + "��dex�ļ�Ϊsmali�ɹ�===��ʱ:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
                }
            } catch (Exception e) {
                System.out.println("ת����" + i + "��dex�ļ�Ϊsmaliʧ��:" + e.toString());
                return false;
            }
        }
        // ֱ�ӽ�����smali����һ���ļ��İ汾
//        for (int i = 0; i < dexFileList.size(); i++) {
//            String dexFileName = dexFileList.get(i);
//            String javaCmd = "java -jar libs" + File.separator + "baksmali.jar -o " + smaliDir + " " + unZipPath + dexFileName;
//            System.out.println(javaCmd);
//            long startTime = System.currentTimeMillis();
//            try {
//                Process pro = Runtime.getRuntime().exec(javaCmd);
//                int status = pro.waitFor();
//                if (status == 0) {
//                    System.out.println("ת����" + i + "��dex�ļ�Ϊsmali�ɹ�===��ʱ:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
//                }
//            } catch (Exception e) {
//                System.out.println("ת����" + i + "��dex�ļ�Ϊsmaliʧ��:" + e.toString());
//                return false;
//            }
//        }
        return true;
    }

    /**
     * �滻ԭʼǩ���Ͱ���
     */
    public static boolean setSignAndPkgName() {
        System.out.println("������==> �������滻ԭʼǩ���Ͱ�����Ϣ");
        String smaliTmpPath = JWMain.rootPath + Const.smaliTmpDir;
        File smaliTmpFile = new File(smaliTmpPath);
        if (!smaliTmpFile.exists()) {
            System.out.println(String.format("dex�ļ���%s, ������", smaliTmpPath));
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
            System.out.println("����ǩ���Ͱ����ɹ�===��ʱ:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
            return true;
        } catch (Exception e) {
            System.out.println("����ǩ���Ͱ���ʧ��:" + e.toString());
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
     * ����hook����
     */
    public static boolean insertHookCode() {
        System.out.println("���߲�==> ���hook����");
        long startTime = System.currentTimeMillis();

        String smaliClassDir = JWMain.rootPath + Const.smaliTmpDir + File.separator;
        File smaliClassDirFile = new File(smaliClassDir);
        if (!smaliClassDirFile.exists()) {
            System.out.println(String.format("�ļ�·����%s�� ������", smaliClassDir));
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
            System.out.println(String.format("û���ҵ����smali�ļ�: %s", Const.entryClassName));
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
            System.out.println("����hook����ɹ�===��ʱ" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
            isWorkSucc = true;
        } catch (Exception e) {
            System.out.println("����hook����ʧ��:" + e.toString());
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
     * ��smaliת����dex
     */
    public static boolean smaliToDex(String smaliDir, String tmpDexDir) {
        System.out.println("�ڰ˲�==> ��smaliת����dex");
        if (!FileUtils.fileIsExist(smaliDir)) {
            System.out.println(String.format("�ļ�%s��������", smaliDir));
            return false;
        }
        if (!FileUtils.fileIsExist(tmpDexDir)) {
            File file = new File(tmpDexDir);
            file.mkdirs();
        }
        List<String> childSmaliFileList = FileUtils.getChildFileName(smaliDir);
        System.out.println(String.format("��%d��smali�ļ�", childSmaliFileList.size()));
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
                    System.out.println("smaliת��dex�ɹ�===��ʱ:" + ((System.currentTimeMillis() - startTime) / 1000) + "s\n\n");
                }
            } catch (Exception e) {
                System.out.println("smaliת��dexʧ��:" + e.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * ʹ��aapt�������dex�ļ���apk��
     */
    public static boolean addDexToApk(String aaptCmdDir, String tmpDexDir, String srcApkPath) {
        try {
            System.out.println("�ھŲ�==> ��dex�ļ���ӵ�Դapk��");
            long time = System.currentTimeMillis();
            File aaptFile = new File(aaptCmdDir);
            String cmd = aaptFile.getAbsolutePath() + " remove " + new File(srcApkPath).getAbsolutePath();
            File classDir = new File(tmpDexDir);
            File[] classListFile = classDir.listFiles();
            // ���һ�����¼ӵĶ�̬����dex���������һ��������
            for (int i = 0; i < classListFile.length - 1; i++) {
                File file = classListFile[i];
                if (file.getName().endsWith(".dex")) {
                    cmd = cmd + " " + file.getName();
                }
            }
            System.out.println("cmd:" + cmd);
            if (!execCmd(cmd, true)) {
                System.out.println("ɾ��apk�е�dex�ļ�ʧ�ܣ��˳���");
                return false;
            }

            String addCmd = aaptFile.getAbsolutePath() + " add " + new File(srcApkPath).getAbsolutePath();

            // ���Ƶ���ǰĿ¼����Ϊaapt����̫ɵ����....
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
                System.out.println("���dex�ļ���apk��ʧ�ܣ��˳���");
                return false;
            }
            // Ȼ����ɾ��
            for (File file : classListFile) {
                if (file.getName().endsWith(".dex")) {
                    FileUtils.deleteFile(file.getName());
                }
            }
            System.out.println("���dex�ļ���apk�н���===��ʱ:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("���dex�ļ���apk��ʧ�ܣ��˳���:" + e.toString());
            return false;
        }
    }

    /**
     * ǩ��apk�ļ�
     */
    public static boolean signApk(String srcApkPath, String rootPath) {
        try {
            System.out.println("��ʮ��==> ��ʼǩ��apk�ļ�:" + srcApkPath);
            long time = System.currentTimeMillis();
            String keystore = "cyy_game.keystore";
            File signFile = new File(rootPath + File.separator + keystore);
            if (!signFile.exists()) {
                System.out.println("ǩ���ļ�:" + signFile.getAbsolutePath() + " �����ڣ���Ҫ�Լ��ֶ�ǩ��");
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
            System.out.println("ǩ��apk�ļ�����===��ʱ:" + ((System.currentTimeMillis() - time) / 1000) + "s\n\n");
            return true;
        } catch (Throwable e) {
            System.out.println("����ǩ��apk�ļ�ʧ�ܣ��˳���:" + e.toString());
            return false;
        }
    }

    /**
     * ����ɾ������
     */
    public static void deleteTmpFile(String rootPath) {
        //ɾ����ѹ֮���Ŀ¼
        FileUtils.deleteDirectory(rootPath + Const.unZipDir);
        //ɾ��smaliĿ¼
        FileUtils.deleteDirectory(rootPath + Const.smaliTmpDir);
        //ɾ����ʱdex�ļ�Ŀ¼
        FileUtils.deleteDirectory(rootPath + File.separator + Const.tmpdexPath);
//        //ɾ����ʱdex�ļ�
//        FileUtils.deleteFile(rootPath+File.separator+"classes.dex");
    }

    /**
     * ִ������
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