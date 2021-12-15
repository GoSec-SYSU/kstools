.class public Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;
.super Ljava/lang/Object;
.source "PmsHookBinderInvocationHandler.java"

# interfaces
.implements Ljava/lang/reflect/InvocationHandler;


# static fields
.field private static final wechatPkgName:Ljava/lang/String; = "com.tencent.mm"

.field private static final wechatSIGN:Ljava/lang/String; = "308202eb30820254a00302010202044d36f7a4300d06092a864886f70d01010505003081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e74301e170d3131303131393134333933325a170d3431303131313134333933325a3081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e7430819f300d06092a864886f70d010101050003818d0030818902818100c05f34b231b083fb1323670bfbe7bdab40c0c0a6efc87ef2072a1ff0d60cc67c8edb0d0847f210bea6cbfaa241be70c86daf56be08b723c859e52428a064555d80db448cdcacc1aea2501eba06f8bad12a4fa49d85cacd7abeb68945a5cb5e061629b52e3254c373550ee4e40cb7c8ae6f7a8151ccd8df582d446f39ae0c5e930203010001300d06092a864886f70d0101050500038181009c8d9d7f2f908c42081b4c764c377109a8b2c70582422125ce545842d5f520aea69550b6bd8bfd94e987b75a3077eb04ad341f481aac266e89d3864456e69fba13df018acdc168b9a19dfd7ad9d9cc6f6ace57c746515f71234df3a053e33ba93ece5cd0fc15f3e389a3f365588a9fcb439e069d3629cd7732a13fff7b891499"


# instance fields
.field private SIGN:Ljava/lang/String;

.field private appPkgName:Ljava/lang/String;

.field private base:Ljava/lang/Object;


# direct methods
.method public constructor <init>(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;I)V
    .registers 9
    .param p1, "base"    # Ljava/lang/Object;
    .param p2, "sign"    # Ljava/lang/String;
    .param p3, "appPkgName"    # Ljava/lang/String;
    .param p4, "hashCode"    # I

    .prologue
    .line 39
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 20
    const-string v1, ""

    iput-object v1, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->appPkgName:Ljava/lang/String;

    .line 41
    :try_start_7
    iput-object p1, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->base:Ljava/lang/Object;

    .line 42
    iput-object p2, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->SIGN:Ljava/lang/String;

    .line 43
    iput-object p3, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->appPkgName:Ljava/lang/String;
    :try_end_d
    .catch Ljava/lang/Exception; {:try_start_7 .. :try_end_d} :catch_e

    .line 47
    :goto_d
    return-void

    .line 44
    :catch_e
    move-exception v0

    .line 45
    .local v0, "e":Ljava/lang/Exception;
    const-string v1, "jw"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "error:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {v0}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_d
.end method


# virtual methods
.method public invoke(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;
    .registers 11
    .param p1, "proxy"    # Ljava/lang/Object;
    .param p2, "method"    # Ljava/lang/reflect/Method;
    .param p3, "args"    # [Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    .prologue
    const/4 v6, 0x0

    .line 51
    const-string v4, "jw"

    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 52
    const-string v4, "getPackageInfo"

    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_64

    .line 53
    aget-object v2, p3, v6

    check-cast v2, Ljava/lang/String;

    .line 54
    .local v2, "pkgName":Ljava/lang/String;
    const/4 v4, 0x1

    aget-object v0, p3, v4

    check-cast v0, Ljava/lang/Integer;

    .line 55
    .local v0, "flag":Ljava/lang/Integer;
    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v4

    const/16 v5, 0x40

    if-ne v4, v5, :cond_64

    .line 56
    const-string v4, "pkgName: "

    invoke-static {v4, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 57
    iget-object v4, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->appPkgName:Ljava/lang/String;

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_48

    .line 58
    new-instance v3, Landroid/content/pm/Signature;

    iget-object v4, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->SIGN:Ljava/lang/String;

    invoke-direct {v3, v4}, Landroid/content/pm/Signature;-><init>(Ljava/lang/String;)V

    .line 59
    .local v3, "sign":Landroid/content/pm/Signature;
    iget-object v4, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->base:Ljava/lang/Object;

    invoke-virtual {p2, v4, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/PackageInfo;

    .line 60
    .local v1, "info":Landroid/content/pm/PackageInfo;
    iget-object v4, v1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    aput-object v3, v4, v6

    .line 71
    .end local v0    # "flag":Ljava/lang/Integer;
    .end local v1    # "info":Landroid/content/pm/PackageInfo;
    .end local v2    # "pkgName":Ljava/lang/String;
    .end local v3    # "sign":Landroid/content/pm/Signature;
    :goto_47
    return-object v1

    .line 63
    .restart local v0    # "flag":Ljava/lang/Integer;
    .restart local v2    # "pkgName":Ljava/lang/String;
    :cond_48
    const-string v4, "com.tencent.mm"

    invoke-virtual {v2, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_64

    .line 64
    new-instance v3, Landroid/content/pm/Signature;

    const-string v4, "308202eb30820254a00302010202044d36f7a4300d06092a864886f70d01010505003081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e74301e170d3131303131393134333933325a170d3431303131313134333933325a3081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e7430819f300d06092a864886f70d010101050003818d0030818902818100c05f34b231b083fb1323670bfbe7bdab40c0c0a6efc87ef2072a1ff0d60cc67c8edb0d0847f210bea6cbfaa241be70c86daf56be08b723c859e52428a064555d80db448cdcacc1aea2501eba06f8bad12a4fa49d85cacd7abeb68945a5cb5e061629b52e3254c373550ee4e40cb7c8ae6f7a8151ccd8df582d446f39ae0c5e930203010001300d06092a864886f70d0101050500038181009c8d9d7f2f908c42081b4c764c377109a8b2c70582422125ce545842d5f520aea69550b6bd8bfd94e987b75a3077eb04ad341f481aac266e89d3864456e69fba13df018acdc168b9a19dfd7ad9d9cc6f6ace57c746515f71234df3a053e33ba93ece5cd0fc15f3e389a3f365588a9fcb439e069d3629cd7732a13fff7b891499"

    invoke-direct {v3, v4}, Landroid/content/pm/Signature;-><init>(Ljava/lang/String;)V

    .line 65
    .restart local v3    # "sign":Landroid/content/pm/Signature;
    iget-object v4, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->base:Ljava/lang/Object;

    invoke-virtual {p2, v4, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/PackageInfo;

    .line 66
    .restart local v1    # "info":Landroid/content/pm/PackageInfo;
    iget-object v4, v1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    aput-object v3, v4, v6

    goto :goto_47

    .line 71
    .end local v0    # "flag":Ljava/lang/Integer;
    .end local v1    # "info":Landroid/content/pm/PackageInfo;
    .end local v2    # "pkgName":Ljava/lang/String;
    .end local v3    # "sign":Landroid/content/pm/Signature;
    :cond_64
    iget-object v4, p0, Lcn/wjdiankong/hookpms/PmsHookBinderInvocationHandler;->base:Ljava/lang/Object;

    invoke-virtual {p2, v4, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    goto :goto_47
.end method
