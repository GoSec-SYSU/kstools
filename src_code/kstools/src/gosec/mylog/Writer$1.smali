.class final Lgosec/mylog/Writer$1;
.super Ljava/util/TimerTask;
.source "Writer.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lgosec/mylog/Writer;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .registers 1

    .prologue
    .line 30
    invoke-direct {p0}, Ljava/util/TimerTask;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 3

    .prologue
    .line 34
    :try_start_0
    sget-object v1, Lgosec/mylog/Writer;->bufferedWriter:Ljava/io/BufferedWriter;

    invoke-virtual {v1}, Ljava/io/BufferedWriter;->flush()V
    :try_end_5
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_5} :catch_6

    .line 38
    :goto_5
    return-void

    .line 35
    :catch_6
    move-exception v0

    .line 36
    .local v0, "e":Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_5
.end method
