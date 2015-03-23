WEAR=./wear/build/outputs/apk/wear-debug.apk

wear: gradle install
	adb shell am start -a android.intent.action.MAIN -n com.davy.adrien.wildoo/.MainActivity

install:
	adb install -r $(WEAR)

gradle:
	./gradlew assembleDebug

.PHONY: wear gradle
