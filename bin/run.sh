export FX_PATH=/usr/share/openjfx/lib

cd ../src
javac -d ../bin --module-path $FX_PATH --add-modules javafx.controls com/orangomango/cpstest/CPSTest.java
cd ../bin

java --module-path $FX_PATH --add-modules javafx.controls com.orangomango.cpstest.CPSTest

jar -cvfe CPSTest.jar com.orangomango.cpstest.CPSTest com
