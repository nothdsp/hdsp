@echo off

mvn -B -Prelease -DskipTests clean deploy
echo Release completed