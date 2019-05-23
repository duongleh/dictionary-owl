#!/bin/bash
rm -rf DictController/*.class
rm -rf DictMain/*.class
rm -rf DictModel/*.class
rm -rf DictView/*.class

javac DictController/DictController.java
javac DictMain/mainDict.java
javac DictModel/dictionary.java
javac DictModel/Word.java 
javac DictView/GUI.java  

java DictMain.mainDict