rm -rf DictController/*.class
rm -rf DictMain/*.class
rm -rf DictModel/*.class
rm -rf DictView/*.class

javac DictController/DictController.java -Xlint
javac DictMain/mainDict.java -Xlint
javac DictModel/dictionary.java -Xlint
javac DictModel/Word.java -Xlint
javac DictView/DictGUI.java -Xlint

java DictMain.mainDict