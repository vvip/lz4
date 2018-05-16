The java command tool for compressing or uncomressing lz4 format file. 

Too many files from _https://censys.io_ are lz4 format. The tools can deal them like:

**uncompress:**

`java -jar lz4.jar -d alexa-top1m.20180330T0205.csv.lz4`

`java -jar lz4.jar -d alexa-top1m.20180330T0205.csv.lz4 alexa-top1m.20180330T0205.csv`


**compress**

`java -jar lz4.jar -e alexa-top1m.20180330T0205.csv`

`java -jar lz4.jar -e alexa-top1m.20180330T0205.csv alexa-top1m.20180330T0205.csv.lz4`

You can download it from the relative path: _classes/artifacts/lz4_jar/lz4.jar_

The source code is in a single file: _src/main/java/lz4/Lz4Tool.java_
