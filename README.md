# Read me Mytunes

Husk at adde jeres lokale JAVAFX Library til projektet så i kan kører det.
Jeg har brugt den nyeste version (15.0.1) så det kan være i også skal opdatere jeres

Der udover er der også nogle nye VM options i skal indsætte, da den bruger lidt fler dependencies som i kan se i "Lib" folderen. Men det gennemgår vi under "installation" punktet.
### Installation
det her er VM options i skal kører med.
Hvor "${PATH_TO_FX}" er en global path variable som i kan sætte under *file -> settings -> path variables* hvor i så bare skal sætte name til at være "PATH_TO_FX" og value til jeres lib folder i javaFX mappen. f.eks ser min ud således: *C:\Users\MIKEH\Documents\JavaFX\javafx-sdk-15.0.1\lib*
Ved ikke lige hvordan det ser ud på mac, så det må i lige overleve med.
```java
--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml,javafx.media
--add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED 
--add-opens "javafx.graphics/javafx.css=ALL-UNNAMED"
```
