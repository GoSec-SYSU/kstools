.class public Lgosec/mylog/Writer;
.super Ljava/lang/Object;
.source "Writer.java"


# static fields
.field public static fileName:Ljava/lang/String;

.field public static packageName:Ljava/lang/String;

.field public static writerMap:Ljava/util/concurrent/ConcurrentHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/ConcurrentHashMap",
            "<",
            "Ljava/lang/String;",
            "Ljava/io/BufferedWriter;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 107
    const-string v0, "com.cns.mc.activity"

    sput-object v0, Lgosec/mylog/Writer;->packageName:Ljava/lang/String;

    .line 108
    const-string v0, "data.txt"

    sput-object v0, Lgosec/mylog/Writer;->fileName:Ljava/lang/String;

    .line 111
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lgosec/mylog/Writer;->writerMap:Ljava/util/concurrent/ConcurrentHashMap;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 102
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static write(Ljava/lang/String;Ljava/lang/String;)V
    .registers 14
    .param p0, "hash"    # Ljava/lang/String;
    .param p1, "content"    # Ljava/lang/String;

    .prologue
    const/4 v11, 0x0

    const/4 v10, 0x1

    .line 117
    const-string v7, "/data/data/%s/%s"

    const/4 v8, 0x2

    new-array v8, v8, [Ljava/lang/Object;

    sget-object v9, Lgosec/mylog/Writer;->packageName:Ljava/lang/String;

    aput-object v9, v8, v11

    sget-object v9, Lgosec/mylog/Writer;->fileName:Ljava/lang/String;

    aput-object v9, v8, v10

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    .line 118
    .local v5, "path":Ljava/lang/String;
    new-instance v3, Ljava/io/File;

    invoke-virtual {v5}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-direct {v3, v7}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 119
    .local v3, "file":Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v7

    invoke-virtual {v7}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_37

    .line 120
    invoke-virtual {v3}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v7

    invoke-virtual {v7}, Ljava/io/File;->mkdirs()Z

    move-result v6

    .line 121
    .local v6, "result":Z
    if-nez v6, :cond_37

    .line 122
    sget-object v7, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v8, "\u9352\u6d98\u7f13\u6fb6\u8fab\u89e6"

    invoke-virtual {v7, v8}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 125
    .end local v6    # "result":Z
    :cond_37
    sget-object v7, Lgosec/mylog/Writer;->writerMap:Ljava/util/concurrent/ConcurrentHashMap;

    const/4 v8, 0x0

    invoke-virtual {v7, v5, v8}, Ljava/util/concurrent/ConcurrentHashMap;->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/io/BufferedWriter;

    .line 127
    .local v0, "bufferedWriter":Ljava/io/BufferedWriter;
    if-nez v0, :cond_5a

    .line 128
    :try_start_42
    new-instance v1, Ljava/io/BufferedWriter;

    new-instance v7, Ljava/io/OutputStreamWriter;

    new-instance v8, Ljava/io/FileOutputStream;

    const/4 v9, 0x1

    invoke-direct {v8, v3, v9}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    invoke-direct {v7, v8}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;)V

    const/16 v8, 0x400

    invoke-direct {v1, v7, v8}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;I)V
    :try_end_54
    .catch Ljava/lang/Exception; {:try_start_42 .. :try_end_54} :catch_7e

    .line 130
    .end local v0    # "bufferedWriter":Ljava/io/BufferedWriter;
    .local v1, "bufferedWriter":Ljava/io/BufferedWriter;
    :try_start_54
    sget-object v7, Lgosec/mylog/Writer;->writerMap:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v7, v5, v1}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_59
    .catch Ljava/lang/Exception; {:try_start_54 .. :try_end_59} :catch_83

    move-object v0, v1

    .line 132
    .end local v1    # "bufferedWriter":Ljava/io/BufferedWriter;
    .restart local v0    # "bufferedWriter":Ljava/io/BufferedWriter;
    :cond_5a
    :try_start_5a
    const-string v7, "Hash: %s\n"

    const/4 v8, 0x1

    new-array v8, v8, [Ljava/lang/Object;

    const/4 v9, 0x0

    aput-object p0, v8, v9

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    .line 133
    .local v4, "hashHead":Ljava/lang/String;
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v0, v7}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 134
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_7d
    .catch Ljava/lang/Exception; {:try_start_5a .. :try_end_7d} :catch_7e

    .line 138
    .end local v4    # "hashHead":Ljava/lang/String;
    :goto_7d
    return-void

    .line 135
    :catch_7e
    move-exception v2

    .line 136
    .local v2, "e":Ljava/lang/Exception;
    :goto_7f
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_7d

    .line 135
    .end local v0    # "bufferedWriter":Ljava/io/BufferedWriter;
    .end local v2    # "e":Ljava/lang/Exception;
    .restart local v1    # "bufferedWriter":Ljava/io/BufferedWriter;
    :catch_83
    move-exception v2

    move-object v0, v1

    .end local v1    # "bufferedWriter":Ljava/io/BufferedWriter;
    .restart local v0    # "bufferedWriter":Ljava/io/BufferedWriter;
    goto :goto_7f
.end method
