cd %~dp0
set aapt_path=C:\Users\xinxin\AppData\Local\Android\Sdk\build-tools\30.0.2\aapt.exe
java -Xmx1024m -XX:-UseParallelGC -XX:MinHeapFreeRatio=15 -jar kstools.jar ++hook %~dp0 src.apk %aapt_path% 1338303158
adb install -r signed.apk
pause..
