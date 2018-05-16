The java command tools for compressing or uncomressing lz4 format file. 

Too many files from _https://censys.io_ are lz4 format. the tools can deal them like:

**uncompress:**

`java -jar lz4.jar -d alexa-top1m.20180330T0205.csv.lz4`
`java -jar lz4.jar -d alexa-top1m.20180330T0205.csv.lz4 alexa-top1m.20180330T0205.csv`

**compress**
`java -jar lz4.jar -e alexa-top1m.20180330T0205.csv`
`java -jar lz4.jar -e alexa-top1m.20180330T0205.csv alexa-top1m.20180330T0205.csv.lz4`
