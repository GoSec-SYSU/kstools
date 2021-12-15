.class public Lgosec/mylog/Writer;
.super Ljava/lang/Object;
.source "Writer.java"


# static fields
.field public static bufferedWriter:Ljava/io/BufferedWriter;

.field public static fileName:Ljava/lang/String;

.field public static packageName:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .registers 12

    .prologue
    const-wide/16 v2, 0x3e8

    const/4 v10, 0x1

    .line 13
    const-string v0, "com.cns.mc.activity"

    sput-object v0, Lgosec/mylog/Writer;->packageName:Ljava/lang/String;

    .line 14
    const-string v0, "data.txt"

    sput-object v0, Lgosec/mylog/Writer;->fileName:Ljava/lang/String;

    .line 15
    const-string v0, "/data/data/%s/%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v4, 0x0

    sget-object v5, Lgosec/mylog/Writer;->packageName:Ljava/lang/String;

    aput-object v5, v1, v4

    sget-object v4, Lgosec/mylog/Writer;->fileName:Ljava/lang/String;

    aput-object v4, v1, v10

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    .line 17
    .local v8, "path":Ljava/lang/String;
    new-instance v7, Ljava/io/File;

    invoke-direct {v7, v8}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 18
    .local v7, "file":Ljava/io/File;
    invoke-virtual {v7}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v0

    if-nez v0, :cond_3d

    .line 19
    invoke-virtual {v7}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    move-result v9

    .line 20
    .local v9, "result":Z
    if-nez v9, :cond_3d

    .line 21
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "\u9352\u6d98\u7f13\u6fb6\u8fab\u89e6"

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 25
    .end local v9    # "result":Z
    :cond_3d
    :try_start_3d
    new-instance v0, Ljava/io/BufferedWriter;

    new-instance v1, Ljava/io/OutputStreamWriter;

    new-instance v4, Ljava/io/FileOutputStream;

    const/4 v5, 0x1

    invoke-direct {v4, v7, v5}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    invoke-direct {v1, v4}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;)V

    const-wide/high16 v4, 0x4090000000000000L    # 1024.0

    const-wide/high16 v10, 0x4000000000000000L    # 2.0

    .line 26
    invoke-static {v4, v5, v10, v11}, Ljava/lang/Math;->pow(DD)D

    move-result-wide v4

    double-to-int v4, v4

    invoke-direct {v0, v1, v4}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;I)V

    sput-object v0, Lgosec/mylog/Writer;->bufferedWriter:Ljava/io/BufferedWriter;
    :try_end_58
    .catch Ljava/io/FileNotFoundException; {:try_start_3d .. :try_end_58} :catch_69

    .line 30
    :goto_58
    new-instance v0, Ljava/util/Timer;

    const-string v1, "writer looper"

    invoke-direct {v0, v1}, Ljava/util/Timer;-><init>(Ljava/lang/String;)V

    new-instance v1, Lgosec/mylog/Writer$1;

    invoke-direct {v1}, Lgosec/mylog/Writer$1;-><init>()V

    move-wide v4, v2

    invoke-virtual/range {v0 .. v5}, Ljava/util/Timer;->schedule(Ljava/util/TimerTask;JJ)V

    .line 40
    return-void

    .line 27
    :catch_69
    move-exception v6

    .line 28
    .local v6, "e":Ljava/io/FileNotFoundException;
    invoke-virtual {v6}, Ljava/io/FileNotFoundException;->printStackTrace()V

    goto :goto_58
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static write(Ljava/lang/String;)V
    .registers 3
    .param p0, "content"    # Ljava/lang/String;

    .prologue
    .line 46
    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {v1, p0}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 48
    :try_start_5
    sget-object v1, Lgosec/mylog/Writer;->bufferedWriter:Ljava/io/BufferedWriter;

    invoke-virtual {v1, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V
    :try_end_a
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_a} :catch_b

    .line 52
    :goto_a
    return-void

    .line 49
    :catch_b
    move-exception v0

    .line 50
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_a
.end method
